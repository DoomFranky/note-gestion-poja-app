package hei.school.gestion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hei.school.gestion.conf.FacadeIT;
import hei.school.gestion.entity.domain.JExam;
import hei.school.gestion.entity.domain.JExamGrade;
import hei.school.gestion.entity.domain.JExamGradeHistory;
import hei.school.gestion.entity.domain.JExamType;
import hei.school.gestion.entity.model.Bulletin;
import hei.school.gestion.repository.CourseRepository;
import hei.school.gestion.repository.ExamGradeHistoryRepository;
import hei.school.gestion.repository.ExamGradeRepository;
import hei.school.gestion.repository.ExamRepository;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class RattrapageScenarioIT extends FacadeIT {

  @Autowired GradeCalculationService gradeCalculationService;
  @Autowired GraduateService graduateService;
  @Autowired ExamRepository examRepository;
  @Autowired CourseRepository courseRepository;
  @Autowired ExamGradeRepository examGradeRepository;
  @Autowired ExamGradeHistoryRepository examGradeHistoryRepository;

  @Test
  void studentWhoFailsThenPassesRattrapageIsCappedAtTenAndGraduates() {
    assertTrue(isGraduate("STD24001"));

    forceFailureOnCourseOne();

    Double failedAverage = gradeCalculationService.courseAverage("s1", "c1", 1);
    assertTrue(failedAverage < 10);
    assertFalse(isGraduate("STD24001"));

    JExam rattrapage = insertRattrapage();
    gradeCalculationService.updateExamGrade(
        "s1", rattrapage.getId(), 14.0, "u1", "Note de rattrapage");

    Double cappedScore =
        examGradeRepository
            .findByExamIdAndStudentId(rattrapage.getId(), "s1")
            .orElseThrow()
            .getScore();
    assertEquals(10.0, cappedScore, 0.001);
    assertEquals(10.0, gradeCalculationService.courseAverage("s1", "c1", 1), 0.001);

    gradeCalculationService.updateExamGrade(
        "s1", rattrapage.getId(), 8.0, "u1", "Correction après réclamation");
    gradeCalculationService.updateExamGrade("s1", rattrapage.getId(), 10.0, "u1", "Note retenue");

    assertTrue(hasHistoryForRattrapage(rattrapage.getId()));

    Bulletin bulletin = gradeCalculationService.computeBulletin("s1");
    assertTrue(
        bulletin.getYears().stream()
            .flatMap(year -> year.getCourses().stream())
            .allMatch(course -> course.getAverage() == null || course.getAverage() >= 10));
    assertTrue(isGraduate("STD24001"));
    assertEquals(1, graduateService.getGraduates("p2024", "EL").graduates().get(0).rank());
  }

  private void forceFailureOnCourseOne() {
    for (String examId : List.of("ec1_1", "ec1_2", "ec1_3")) {
      gradeCalculationService.updateExamGrade(
          "s1", examId, 4.0, "u1", "Simulation d'un échec sur le cours");
    }
  }

  private JExam insertRattrapage() {
    JExam rattrapage =
        JExam.builder()
            .id("ec1_rattrapage")
            .course(courseRepository.findById("c1").orElseThrow())
            .academicYear(1)
            .label("Rattrapage")
            .type(JExamType.RATTRAPAGE)
            .examDatetime(Instant.parse("2025-04-10T09:00:00+03:00"))
            .coefficientNum(1)
            .coefficientDen(1)
            .build();
    return examRepository.save(rattrapage);
  }

  private boolean isGraduate(String ref) {
    return graduateService.getGraduates("p2024", "EL").graduates().stream()
        .anyMatch(graduate -> graduate.ref().equals(ref));
  }

  private boolean hasHistoryForRattrapage(String examId) {
    JExamGrade grade = examGradeRepository.findByExamIdAndStudentId(examId, "s1").orElseThrow();
    List<JExamGradeHistory> history = examGradeHistoryRepository.findByExamGradeId(grade.getId());
    return history.stream().anyMatch(entry -> entry.getPreviousScore().equals(10.0))
        && history.stream().anyMatch(entry -> entry.getPreviousScore().equals(8.0));
  }
}
