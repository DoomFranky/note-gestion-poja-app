package hei.school.gestion.endpoint.rest.controller;

import hei.school.gestion.file.bucket.BucketComponent;
import hei.school.gestion.service.TranscriptMailService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class TranscriptPdfController {

  private final BucketComponent bucketComponent;

  @GetMapping("/transcript/{studentId}/pdf")
  public ResponseEntity<byte[]> downloadPdf(@PathVariable String studentId) {
    String bucketKey = TranscriptMailService.BUCKET_KEY_PATTERN.formatted(studentId);
    File file = bucketComponent.download(bucketKey);
    try {
      byte[] content = Files.readAllBytes(file.toPath());
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"releve-de-notes.pdf\"")
          .contentType(MediaType.APPLICATION_PDF)
          .body(content);
    } catch (IOException e) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la lecture du relevé");
    } finally {
      file.delete();
    }
  }
}
