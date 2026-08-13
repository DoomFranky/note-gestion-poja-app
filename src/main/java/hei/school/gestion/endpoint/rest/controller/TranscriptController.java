package hei.school.gestion.endpoint.rest.controller;

import hei.school.gestion.endpoint.rest.dto.TranscriptResponse;
import hei.school.gestion.service.TranscriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TranscriptController {

  private final TranscriptService transcriptService;

  @GetMapping("/transcript/{studentId}")
  public TranscriptResponse getTranscript(@PathVariable String studentId) {
    return transcriptService.getTranscript(studentId);
  }
}
