package hei.school.gestion.endpoint.rest.controller;

import hei.school.gestion.service.TranscriptMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TranscriptMailController {

  private final TranscriptMailService transcriptMailService;

  @PostMapping("/transcript/{studentId}/send")
  public ResponseEntity<Void> sendTranscript(@PathVariable String studentId) {
    transcriptMailService.sendTranscriptAsync(studentId);
    return ResponseEntity.accepted().build();
  }
}
