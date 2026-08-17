package hei.school.gestion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hei.school.gestion.endpoint.event.model.TranscriptMailRequested;
import hei.school.gestion.entity.model.Bulletin;
import hei.school.gestion.entity.model.User;
import hei.school.gestion.mail.Email;
import hei.school.gestion.mail.Mailer;
import hei.school.gestion.service.event.TranscriptMailRequestedService;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class TranscriptMailRequestedServiceTest {

  private GradeCalculationService gradeCalculationService;
  private TranscriptPdfGenerator transcriptPdfGenerator;
  private Mailer mailer;
  private TranscriptMailRequestedService service;

  @BeforeEach
  void setUp() {
    gradeCalculationService = mock(GradeCalculationService.class);
    transcriptPdfGenerator = mock(TranscriptPdfGenerator.class);
    mailer = mock(Mailer.class);
    service =
        new TranscriptMailRequestedService(gradeCalculationService, transcriptPdfGenerator, mailer);
  }

  @Test
  void sendsPdfAttachmentToProvidedRecipient() throws Exception {
    User student =
        User.builder().id("s1").ref("STD24001").firstName("Jean").lastName("Rakoto").build();
    Bulletin bulletin = Bulletin.builder().student(student).build();
    when(gradeCalculationService.computeBulletin("s1")).thenReturn(bulletin);
    when(transcriptPdfGenerator.generate(bulletin))
        .thenReturn("pdf-content".getBytes(StandardCharsets.US_ASCII));

    String[] capturedContent = new String[1];
    doAnswer(
            invocation -> {
              Email email = invocation.getArgument(0);
              capturedContent[0] =
                  new String(
                      Files.readAllBytes(email.attachments().get(0).toPath()),
                      StandardCharsets.US_ASCII);
              return null;
            })
        .when(mailer)
        .accept(any(Email.class));

    service.accept(new TranscriptMailRequested("s1", "destinataire@hei.school"));

    assertEquals("pdf-content", capturedContent[0]);
    ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);
    verify(mailer).accept(emailCaptor.capture());
    Email email = emailCaptor.getValue();
    assertEquals("destinataire@hei.school", email.to().getAddress());
    assertTrue(email.subject().toLowerCase().contains("relevé"));
    assertTrue(email.htmlBody().contains("ci-joint"));
    assertEquals(1, email.attachments().size());
    File attachment = email.attachments().get(0);
    assertTrue(attachment.getName().startsWith("releve-s1-"));
    assertTrue(attachment.getName().endsWith(".pdf"));
    assertTrue(!attachment.exists());
  }

  @Test
  void throwsWhenRecipientEmailIsInvalid() {
    User student =
        User.builder().id("s1").ref("STD24001").firstName("Jean").lastName("Rakoto").build();
    when(gradeCalculationService.computeBulletin("s1"))
        .thenReturn(Bulletin.builder().student(student).build());
    when(transcriptPdfGenerator.generate(any(Bulletin.class)))
        .thenReturn("pdf-content".getBytes(StandardCharsets.US_ASCII));

    assertThrows(
        RuntimeException.class,
        () -> service.accept(new TranscriptMailRequested("s1", "not-an-email")));
  }

  @Test
  void eventExposesFieldsAndConsumerDurations() {
    TranscriptMailRequested event = new TranscriptMailRequested();
    event.setStudentId("s1");
    event.setRecipientEmail("to@hei.school");

    assertEquals("s1", event.getStudentId());
    assertEquals("to@hei.school", event.getRecipientEmail());
    assertEquals(Duration.ofSeconds(10), event.maxConsumerDuration());
    assertEquals(Duration.ofSeconds(30), event.maxConsumerBackoffBetweenRetries());
  }
}
