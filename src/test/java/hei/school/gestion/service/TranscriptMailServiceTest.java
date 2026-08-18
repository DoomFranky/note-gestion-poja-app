package hei.school.gestion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import hei.school.gestion.endpoint.event.EventProducer;
import hei.school.gestion.endpoint.event.model.TranscriptMailRequested;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class TranscriptMailServiceTest {

  private EventProducer<TranscriptMailRequested> eventProducer;
  private TranscriptMailService service;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() {
    eventProducer = mock(EventProducer.class);
    service = new TranscriptMailService(eventProducer);
  }

  @Test
  void publishesTranscriptMailRequestedEvent() {
    service.sendTranscript("s1", "destinataire@hei.school");

    @SuppressWarnings("unchecked")
    ArgumentCaptor<Collection<TranscriptMailRequested>> captor =
        ArgumentCaptor.forClass(Collection.class);
    verify(eventProducer).accept(captor.capture());
    TranscriptMailRequested event = captor.getValue().iterator().next();
    assertEquals("s1", event.getStudentId());
    assertEquals("destinataire@hei.school", event.getRecipientEmail());
  }
}
