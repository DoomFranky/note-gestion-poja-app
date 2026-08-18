package hei.school.gestion.service;

import hei.school.gestion.endpoint.event.EventProducer;
import hei.school.gestion.endpoint.event.model.TranscriptMailRequested;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranscriptMailService {

  private final EventProducer<TranscriptMailRequested> eventProducer;

  public void sendTranscript(String studentId, String recipientEmail) {
    eventProducer.accept(List.of(new TranscriptMailRequested(studentId, recipientEmail)));
  }
}
