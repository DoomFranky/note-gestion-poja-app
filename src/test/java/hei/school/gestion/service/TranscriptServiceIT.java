package hei.school.gestion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hei.school.gestion.conf.FacadeIT;
import hei.school.gestion.endpoint.rest.dto.TranscriptResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class TranscriptServiceIT extends FacadeIT {

  @Autowired TranscriptService transcriptService;

  @Test
  void returnsCompleteTranscriptForGraduateStudent() {
    TranscriptResponse transcript = transcriptService.getTranscript("s1");

    assertEquals("STD24001", transcript.ref());
    assertEquals("EL", transcript.track());
    assertEquals("2024", transcript.promotion());
    assertEquals(3, transcript.years().size());
    assertFalse(transcript.years().get(0).provisional());
    assertFalse(transcript.years().get(1).provisional());
    assertFalse(transcript.years().get(2).provisional());
    assertNotNull(transcript.overallAverage());
    assertEquals(145, transcript.totalCredits());
    assertEquals(27, transcript.years().stream().mapToInt(y -> y.courses().size()).sum());
  }

  @Test
  void returnsProvisionalTranscriptWhenGradesAreMissing() {
    TranscriptResponse transcript = transcriptService.getTranscript("s10");

    assertTrue(transcript.years().get(0).provisional());
    assertTrue(transcript.years().get(0).courses().stream().anyMatch(c -> !c.complete()));
    assertTrue(transcript.years().get(1).courses().isEmpty());
    assertTrue(transcript.years().get(2).courses().isEmpty());
  }
}
