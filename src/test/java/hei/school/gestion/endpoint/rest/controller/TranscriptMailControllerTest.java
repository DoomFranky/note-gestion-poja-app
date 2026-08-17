package hei.school.gestion.endpoint.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hei.school.gestion.endpoint.event.EventProducer;
import hei.school.gestion.endpoint.event.model.TranscriptMailRequested;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class TranscriptMailControllerTest {

  private EventProducer<TranscriptMailRequested> eventProducer;
  private MockMvc mockMvc;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() {
    eventProducer = mock(EventProducer.class);
    mockMvc = MockMvcBuilders.standaloneSetup(new TranscriptMailController(eventProducer)).build();
  }

  @Test
  void publishesEventAndReturnsAccepted() throws Exception {
    mockMvc
        .perform(
            post("/transcript/s1/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"destinataire@hei.school\"}"))
        .andExpect(status().isAccepted());

    @SuppressWarnings("unchecked")
    ArgumentCaptor<Collection<TranscriptMailRequested>> captor =
        ArgumentCaptor.forClass(Collection.class);
    verify(eventProducer).accept(captor.capture());
    TranscriptMailRequested event = captor.getValue().iterator().next();
    assertEquals("s1", event.getStudentId());
    assertEquals("destinataire@hei.school", event.getRecipientEmail());
  }
}
