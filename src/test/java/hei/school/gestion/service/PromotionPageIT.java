package hei.school.gestion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hei.school.gestion.conf.FacadeIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class PromotionPageIT extends FacadeIT {

  @Autowired TestRestTemplate restTemplate;

  @Test
  void listsPromotionsWithGraduateDownloadLinks() {
    ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().contains("2024"));
    assertTrue(response.getBody().contains("2026"));
    assertTrue(response.getBody().contains("/download/graduates/p2024?track=EL"));
    assertTrue(response.getBody().contains("/download/graduates/p2024?track=TN"));
    assertTrue(response.getBody().contains("/download/graduates/p2026?track=EL"));
    assertTrue(response.getBody().contains("/download/graduates/p2026?track=TN"));
  }
}
