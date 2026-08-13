package hei.school.gestion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hei.school.gestion.conf.FacadeIT;
import hei.school.gestion.entity.domain.JExam;
import hei.school.gestion.entity.domain.JExamGrade;
import hei.school.gestion.entity.domain.JGrade;
import hei.school.gestion.entity.model.Bulletin;
import hei.school.gestion.entity.model.YearResult;
import hei.school.gestion.repository.ExamGradeRepository;
import hei.school.gestion.repository.ExamRepository;
import hei.school.gestion.repository.GradeHistoryRepository;
import hei.school.gestion.repository.GradeRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class GradeCalculationServiceIT extends FacadeIT {

  @Autowired GradeCalculationService gradeCalculationService;
  @Autowired ExamRepository examRepository;
  @Autowired ExamGradeRepository examGradeRepository;
  @Autowired GradeRepository gradeRepository;
  @Autowired GradeHistoryRepository gradeHistoryRepository;

  @Test
  void computesCourseAverageFromWeightedExams() {
    Double average = gradeCalculationService.courseAverage("s1", "c1", 1);
    assertNotNull(average);

    double weighted = 0;
    double totalCoefficient = 0;
    List<JExam> exams = examRepository.findByCourseIdAndAcademicYear("c1", 1);
    for (JExam exam : exams) {
      JExamGrade grade =
          examGradeRepository.findByStudentIdAndExamCourseId("s1", "c1").stream()
              .filter(candidate -> candidate.getExam().getId().equals(exam.getId()))
              .findFirst()
              .orElseThrow();
      double coefficient = (double) exam.getCoefficientNum() / exam.getCoefficientDen();
      weighted += grade.getScore() * coefficient;
      totalCoefficient += coefficient;
    }
    assertEquals(weighted / totalCoefficient, average, 0.001);
  }

  @Test
  void bulletinIsCompleteForStudentWithAllGrades() {
    Bulletin bulletin = gradeCalculationService.computeBulletin("s1");
    assertTrue(bulletin.getYears().stream().noneMatch(YearResult::isProvisional));
    assertNotNull(bulletin.getOverallAverage());
    assertEquals(12, bulletin.getYears().get(0).getCourses().size());
    assertEquals(27, bulletin.getYears().stream().mapToInt(y -> y.getCourses().size()).sum());
  }

  @Test
  void bulletinIsProvisionalWhenGradesAreMissing() {
    Bulletin bulletin = gradeCalculationService.computeBulletin("s10");
    assertTrue(bulletin.getYears().get(0).isProvisional());
    assertTrue(bulletin.getYears().get(0).getCourses().stream().anyMatch(c -> !c.isComplete()));
  }

  @Test
  void refreshStudentGradesPersistsGrades() {
    gradeCalculationService.refreshStudentGrades("s1", "u1");
    List<JGrade> grades = gradeRepository.findAll();
    assertTrue(grades.stream().anyMatch(g -> g.getStudent().getId().equals("s1")));
  }

  @Test
  void refreshStudentGradesRecordsHistoryOnScoreChange() {
    gradeCalculationService.refreshStudentGrades("s1", "u1");
    JGrade grade =
        gradeRepository.findByStudentIdAndCourseIdAndAcademicYear("s1", "c1", 1).orElseThrow();
    double recomputedScore = grade.getScore();
    assertFalse(grade.getScore().equals(5.0));

    grade.setScore(5.0);
    gradeRepository.save(grade);
    gradeCalculationService.refreshStudentGrades("s1", "u1");

    JGrade updated =
        gradeRepository.findByStudentIdAndCourseIdAndAcademicYear("s1", "c1", 1).orElseThrow();
    assertEquals(recomputedScore, updated.getScore(), 0.001);
    assertTrue(
        gradeHistoryRepository.findByGradeId(updated.getId()).stream()
            .anyMatch(history -> history.getPreviousScore().equals(5.0)));
  }
}
