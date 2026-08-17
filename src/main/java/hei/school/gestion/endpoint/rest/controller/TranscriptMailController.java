package hei.school.gestion.endpoint.rest.controller;

import hei.school.gestion.endpoint.event.EventProducer;
import hei.school.gestion.endpoint.event.model.TranscriptMailRequested;
import hei.school.gestion.endpoint.rest.dto.SendTranscriptRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TranscriptMailController {

  private final EventProducer<TranscriptMailRequested> eventProducer;

  @PostMapping("/transcript/{studentId}/send")
  public ResponseEntity<Void> sendTranscript(
      @PathVariable String studentId, @RequestBody SendTranscriptRequest request) {
    eventProducer.accept(List.of(new TranscriptMailRequested(studentId, request.email())));
    return ResponseEntity.accepted().build();
  }
}
