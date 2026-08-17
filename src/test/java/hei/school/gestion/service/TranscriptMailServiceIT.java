package hei.school.gestion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hei.school.gestion.conf.FacadeIT;
import hei.school.gestion.file.bucket.BucketComponent;
import hei.school.gestion.file.hash.FileHash;
import hei.school.gestion.file.hash.FileHashAlgorithm;
import hei.school.gestion.mail.Email;
import hei.school.gestion.mail.Mailer;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Transactional
@AutoConfigureMockMvc
class TranscriptMailServiceIT extends FacadeIT {

  @Autowired TranscriptMailService transcriptMailService;
  @Autowired MockMvc mockMvc;
  @MockBean BucketComponent bucketComponent;
  @MockBean Mailer mailer;

  private static final String BUCKET_KEY = "transcripts/s1/releve-de-notes.pdf";
  private static final String BASE_URL =
      "https://mea7vdk4ln7cceuftujaythjja0odpvs.lambda-url.eu-west-3.on.aws";

  private void stubUploadAndReturn() {
    when(bucketComponent.upload(any(File.class), anyString()))
        .thenReturn(new FileHash(FileHashAlgorithm.SHA256, "checksum"));
  }

  @Test
  void sendsTranscriptByEmailWithAppLink() {
    stubUploadAndReturn();

    transcriptMailService.sendTranscriptAsync("s1").join();

    verify(bucketComponent).upload(any(File.class), eq(BUCKET_KEY));

    ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);
    verify(mailer).accept(emailCaptor.capture());
    Email email = emailCaptor.getValue();
    assertEquals("jean.rakoto@hei.school", email.to().getAddress());
    assertTrue(email.subject().toLowerCase().contains("relevé"));
    assertTrue(email.htmlBody().contains(BASE_URL + "/transcript/s1/pdf"));
  }

  @Test
  void throwsNotFoundForUnknownStudent() {
    assertThrows(
        ResponseStatusException.class, () -> transcriptMailService.sendTranscript("unknown"));
  }

  @Test
  void postEndpointReturnsAcceptedAndSendsAsynchronously() throws Exception {
    stubUploadAndReturn();

    mockMvc.perform(post("/transcript/s1/send")).andExpect(status().isAccepted());

    verify(mailer, timeout(10000)).accept(any(Email.class));
  }

  @Test
  void getTranscriptEndpointReturnsBulletin() throws Exception {
    mockMvc
        .perform(get("/transcript/s1"))
        .andExpect(status().isOk())
        .andExpect(
            org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.ref")
                .value("STD24001"));
  }

  @Test
  void getTranscriptPdfEndpointReturnsPdfFromS3() throws Exception {
    File pdfFile = Files.createTempFile("releve-test-", ".pdf").toFile();
    Files.write(pdfFile.toPath(), "%PDF-test-content".getBytes(StandardCharsets.US_ASCII));
    when(bucketComponent.download(eq(BUCKET_KEY))).thenReturn(pdfFile);

    mockMvc
        .perform(get("/transcript/s1/pdf"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_PDF))
        .andExpect(content().bytes("%PDF-test-content".getBytes(StandardCharsets.US_ASCII)));
  }
}
