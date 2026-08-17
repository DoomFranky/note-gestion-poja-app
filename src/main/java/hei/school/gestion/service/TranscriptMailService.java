package hei.school.gestion.service;

import hei.school.gestion.entity.model.Bulletin;
import hei.school.gestion.entity.model.User;
import hei.school.gestion.file.bucket.BucketComponent;
import hei.school.gestion.mail.Email;
import hei.school.gestion.mail.Mailer;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranscriptMailService {

  public static final String BUCKET_KEY_PATTERN = "transcripts/%s/releve-de-notes.pdf";
  public static final String PDF_PATH_PATTERN = "/transcript/%s/pdf";

  private final GradeCalculationService gradeCalculationService;
  private final TranscriptPdfGenerator transcriptPdfGenerator;
  private final BucketComponent bucketComponent;
  private final Mailer mailer;

  @Value("${app.base.url}")
  private String baseUrl;

  @Async("transcriptExecutor")
  public CompletableFuture<Void> sendTranscriptAsync(String studentId) {
    sendTranscript(studentId);
    return CompletableFuture.completedFuture(null);
  }

  public void sendTranscript(String studentId) {
    Bulletin bulletin = gradeCalculationService.computeBulletin(studentId);
    byte[] pdf = transcriptPdfGenerator.generate(bulletin);
    File file = writeToTempFile(studentId, pdf);
    try {
      bucketComponent.upload(file, BUCKET_KEY_PATTERN.formatted(studentId));
      String link = baseUrl + PDF_PATH_PATTERN.formatted(studentId);
      mailer.accept(buildEmail(bulletin.getStudent(), link));
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

  private Email buildEmail(User student, String link) {
    try {
      InternetAddress recipient = new InternetAddress(student.getEmail(), true);
      String body =
          "<p>Bonjour "
              + student.getFirstName()
              + ",</p>"
              + "<p>Votre relevé de notes est disponible au téléchargement :</p>"
              + "<p><a href=\""
              + link
              + "\">Télécharger mon relevé de notes</a></p>";
      return new Email(recipient, List.of(), List.of(), "Votre relevé de notes", body, List.of());
    } catch (AddressException e) {
      throw new RuntimeException("Adresse email invalide pour " + student.getRef(), e);
    }
  }
}
