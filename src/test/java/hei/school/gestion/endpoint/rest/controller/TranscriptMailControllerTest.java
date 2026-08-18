package hei.school.gestion.endpoint.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hei.school.gestion.service.TranscriptMailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class TranscriptMailControllerTest {

  private TranscriptMailService transcriptMailService;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    transcriptMailService = mock(TranscriptMailService.class);
    mockMvc =
        MockMvcBuilders.standaloneSetup(new TranscriptMailController(transcriptMailService))
            .build();
  }

  @Test
  void delegatesToServiceAndReturnsAccepted() throws Exception {
    mockMvc
        .perform(
            post("/transcript/s1/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"destinataire@hei.school\"}"))
        .andExpect(status().isAccepted());

    ArgumentCaptor<String> studentIdCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
    verify(transcriptMailService).sendTranscript(studentIdCaptor.capture(), emailCaptor.capture());
    assertEquals("s1", studentIdCaptor.getValue());
    assertEquals("destinataire@hei.school", emailCaptor.getValue());
  }
}
