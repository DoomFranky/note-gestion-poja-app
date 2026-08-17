package hei.school.gestion.service.event;

import hei.school.gestion.endpoint.event.model.TranscriptMailRequested;
import hei.school.gestion.entity.model.Bulletin;
import hei.school.gestion.entity.model.User;
import hei.school.gestion.mail.Email;
import hei.school.gestion.mail.Mailer;
import hei.school.gestion.service.GradeCalculationService;
import hei.school.gestion.service.TranscriptPdfGenerator;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranscriptMailRequestedService implements Consumer<TranscriptMailRequested> {

  private final GradeCalculationService gradeCalculationService;
  private final TranscriptPdfGenerator transcriptPdfGenerator;
  private final Mailer mailer;

  @Override
  public void accept(TranscriptMailRequested event) {
    Bulletin bulletin = gradeCalculationService.computeBulletin(event.getStudentId());
    byte[] pdf = transcriptPdfGenerator.generate(bulletin);
    File file = writeToTempFile(event.getStudentId(), pdf);
    try {
      mailer.accept(buildEmail(bulletin.getStudent(), event.getRecipientEmail(), file));
    } finally {
      file.delete();
    }
  }

  private File writeToTempFile(String studentId, byte[] content) {
    try {
      Path path = Files.createTempFile("releve-" + studentId + "-", ".pdf");
      Files.write(path, content);
      return path.toFile();
    } catch (IOException e) {
      throw new RuntimeException("Erreur lors de l'écriture du relevé temporaire", e);
    }
  }

  private Email buildEmail(User student, String recipientEmail, File pdfFile) {
    try {
      InternetAddress recipient = new InternetAddress(recipientEmail, true);
      String body =
          "<p>Bonjour "
              + student.getFirstName()
              + ",</p>"
              + "<p>Veuillez trouver ci-joint votre relevé de notes.</p>";
      return new Email(
          recipient, List.of(), List.of(), "Votre relevé de notes", body, List.of(pdfFile));
    } catch (AddressException e) {
      throw new RuntimeException("Adresse email invalide : " + recipientEmail, e);
    }
  }
}
