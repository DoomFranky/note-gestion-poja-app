package hei.school.gestion.service;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import hei.school.gestion.conf.FacadeIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PromotionPageIT extends FacadeIT {

  @Autowired private MockMvc mockMvc;

  @Test
  @WithMockUser(roles = "ADMIN", username = "admin@test.com")
  void listsPromotionsWithGraduateDownloadLinks() throws Exception {
    mockMvc
        .perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("2024")))
        .andExpect(content().string(containsString("2026")))
        .andExpect(content().string(containsString("/download/graduates/p2024?track=EL")))
        .andExpect(content().string(containsString("/download/graduates/p2024?track=TN")))
        .andExpect(content().string(containsString("/download/graduates/p2026?track=EL")))
        .andExpect(content().string(containsString("/download/graduates/p2026?track=TN")));
  }
  ;
}
