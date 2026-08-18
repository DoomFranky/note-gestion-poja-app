package hei.school.gestion.endpoint.rest.controller;

import hei.school.gestion.endpoint.rest.dto.SendTranscriptRequest;
import hei.school.gestion.service.TranscriptMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TranscriptMailController {

  private final TranscriptMailService transcriptMailService;

  @PostMapping("/transcript/{studentId}/send")
  public ResponseEntity<Void> sendTranscript(
      @PathVariable String studentId, @RequestBody SendTranscriptRequest request) {
    transcriptMailService.sendTranscript(studentId, request.email());
    return ResponseEntity.accepted().build();
  }
}
