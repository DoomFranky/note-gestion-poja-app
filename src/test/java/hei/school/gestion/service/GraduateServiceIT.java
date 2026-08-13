package hei.school.gestion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hei.school.gestion.conf.FacadeIT;
import hei.school.gestion.endpoint.rest.dto.GraduatesResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class GraduateServiceIT extends FacadeIT {

  @Autowired GraduateService graduateService;

  @Test
  void returnsOnlyGraduatesForTrack() {
    GraduatesResponse el = graduateService.getGraduates("p2024", "EL");
    assertEquals(1, el.graduates().size());
    assertEquals("STD24001", el.graduates().get(0).ref());
    assertEquals(1, el.graduates().get(0).rank());

    GraduatesResponse tn = graduateService.getGraduates("p2024", "TN");
    assertEquals(2, tn.graduates().size());
    assertEquals("STD24004", tn.graduates().get(0).ref());
    assertEquals(1, tn.graduates().get(0).rank());
    assertEquals("STD24006", tn.graduates().get(1).ref());
    assertEquals(2, tn.graduates().get(1).rank());
  }

  @Test
  void returnsNoGraduatesForInProgressPromotion() {
    GraduatesResponse el = graduateService.getGraduates("p2026", "EL");
    assertTrue(el.graduates().isEmpty());
  }
}
