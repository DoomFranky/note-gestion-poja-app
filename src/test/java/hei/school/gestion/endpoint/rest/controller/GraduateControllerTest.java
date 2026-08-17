package hei.school.gestion.endpoint.rest.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hei.school.gestion.endpoint.rest.dto.GraduatesResponse;
import hei.school.gestion.service.GraduateService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class GraduateControllerTest {

  private GraduateService graduateService;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    graduateService = mock(GraduateService.class);
    mockMvc = MockMvcBuilders.standaloneSetup(new GraduateController(graduateService)).build();
  }

  private GraduatesResponse sampleResponse(String track) {
    return new GraduatesResponse(
        "p2024",
        "2024",
        track,
        List.of(
            new GraduatesResponse.GraduateResponse(1, "STD24001", "Jean", "Rakoto", 13.5),
            new GraduatesResponse.GraduateResponse(2, "STD24004", "Sahondra", "Ravelo", 12.0)));
  }

  @Test
  void downloadsGraduatesXlsxWithTrack() throws Exception {
    when(graduateService.getGraduates("p2024", "EL")).thenReturn(sampleResponse("EL"));

    byte[] body =
        mockMvc
            .perform(get("/download/graduates/p2024").param("track", "EL"))
            .andExpect(status().isOk())
            .andExpect(
                content()
                    .contentType(
                        MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")))
            .andExpect(
                header()
                    .string("Content-Disposition", "attachment; filename=\"graduates-EL.xlsx\""))
            .andReturn()
            .getResponse()
            .getContentAsByteArray();

    assertTrue(body.length > 100);
    assertArrayEquals(
        new byte[] {(byte) 0x50, (byte) 0x4B, (byte) 0x03, (byte) 0x04},
        java.util.Arrays.copyOf(body, 4));
  }

  @Test
  void downloadsGraduatesXlsxWithoutTrack() throws Exception {
    when(graduateService.getGraduates("p2024", null)).thenReturn(sampleResponse(null));

    mockMvc
        .perform(get("/download/graduates/p2024"))
        .andExpect(status().isOk())
        .andExpect(
            header().string("Content-Disposition", "attachment; filename=\"graduates-all.xlsx\""));
  }
}
