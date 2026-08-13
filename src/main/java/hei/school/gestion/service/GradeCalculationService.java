package hei.school.gestion.service;

import hei.school.gestion.entity.domain.JCourse;
import hei.school.gestion.entity.domain.JExam;
import hei.school.gestion.entity.domain.JExamGrade;
import hei.school.gestion.entity.domain.JGrade;
import hei.school.gestion.entity.domain.JGradeHistory;
import hei.school.gestion.entity.domain.JTrack;
import hei.school.gestion.entity.domain.JUser;
import hei.school.gestion.entity.model.Bulletin;
import hei.school.gestion.entity.model.CourseResult;
import hei.school.gestion.entity.model.YearResult;
import hei.school.gestion.mapper.CourseMapper;
import hei.school.gestion.mapper.UserMapper;
import hei.school.gestion.repository.CourseRepository;
import hei.school.gestion.repository.ExamGradeRepository;
import hei.school.gestion.repository.ExamRepository;
import hei.school.gestion.repository.GradeHistoryRepository;
import hei.school.gestion.repository.GradeRepository;
import hei.school.gestion.repository.StudentGroupRepository;
import hei.school.gestion.repository.UserRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class GradeCalculationService {

  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final ExamRepository examRepository;
  private final ExamGradeRepository examGradeRepository;
  private final GradeRepository gradeRepository;
  private final GradeHistoryRepository gradeHistoryRepository;
  private final StudentGroupRepository studentGroupRepository;
  private final UserMapper userMapper;
  private final CourseMapper courseMapper;

  public Double courseAverage(String studentId, String courseId, Integer year) {
    List<JExam> exams = examRepository.findByCourseIdAndAcademicYear(courseId, year);
    if (exams.isEmpty()) return null;

    Map<String, Double> scoreByExamId =
        examGradeRepository.findByStudentIdAndExamCourseId(studentId, courseId).stream()
            .filter(grade -> year.equals(grade.getExam().getAcademicYear()))
            .collect(Collectors.toMap(grade -> grade.getExam().getId(), JExamGrade::getScore));
    return weightedAverage(exams, scoreByExamId);
  }

  public Bulletin computeBulletin(String studentId) {
    JUser student =
        userRepository
            .findById(studentId)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Étudiant introuvable"));

    List<JCourse> courses = programCourses(student);
    Map<String, List<JExam>> examsByCourseId =
        examRepository.findAll().stream()
            .collect(Collectors.groupingBy(exam -> exam.getCourse().getId()));
    Map<String, Double> scoreByExamId =
        examGradeRepository.findByStudentId(studentId).stream()
            .collect(Collectors.toMap(grade -> grade.getExam().getId(), JExamGrade::getScore));

    List<YearResult> years = new ArrayList<>();
    for (int year = 1; year <= 3; year++) {
      final int currentYear = year;
      List<CourseResult> courseResults = new ArrayList<>();
      for (JCourse course : courses) {
        List<JExam> courseExams = examsByCourseId.getOrDefault(course.getId(), List.of());
        if (courseExams.isEmpty()) continue;
        Integer courseYear = courseExams.get(0).getAcademicYear();
        if (courseYear == null || courseYear != currentYear) continue;

        Double average = weightedAverage(courseExams, scoreByExamId);
        courseResults.add(
            CourseResult.builder()
                .course(courseMapper.toDomain(course))
                .academicYear(currentYear)
                .average(average)
                .complete(average != null)
                .build());
      }
      courseResults.sort(Comparator.comparing(c -> c.getCourse().getCode()));

      boolean provisional = courseResults.stream().anyMatch(c -> !c.isComplete());
      int credits = courseResults.stream().mapToInt(c -> c.getCourse().getCredits()).sum();
      years.add(
          YearResult.builder()
              .year(currentYear)
              .provisional(provisional)
              .average(weightedAverageOf(courseResults))
              .credits(credits)
              .courses(courseResults)
              .build());
    }

    List<CourseResult> allCourseResults =
        years.stream().flatMap(y -> y.getCourses().stream()).toList();
    return Bulletin.builder()
        .student(userMapper.toDomain(student))
        .years(years)
        .overallAverage(weightedAverageOf(allCourseResults))
        .totalCredits(allCourseResults.stream().mapToInt(c -> c.getCourse().getCredits()).sum())
        .build();
  }

  @Transactional
  public void refreshStudentGrades(String studentId, String actorId) {
    Bulletin bulletin = computeBulletin(studentId);
    if (bulletin.getYears() == null) return;

    JUser actor = userRepository.findById(actorId).orElse(null);
    for (YearResult year : bulletin.getYears()) {
      for (CourseResult course : year.getCourses()) {
        if (course.getAverage() == null) continue;
        upsertGrade(studentId, course, actor);
      }
    }
  }

  private void upsertGrade(String studentId, CourseResult course, JUser actor) {
    Optional<JGrade> existing =
        gradeRepository.findByStudentIdAndCourseIdAndAcademicYear(
            studentId, course.getCourse().getId(), course.getAcademicYear());

    if (existing.isPresent()) {
      JGrade grade = existing.get();
      if (!grade.getScore().equals(course.getAverage())) {
        gradeHistoryRepository.save(
            JGradeHistory.builder()
                .id(UUID.randomUUID().toString())
                .grade(grade)
                .previousScore(grade.getScore())
                .newScore(course.getAverage())
                .reason("Mise à jour automatique de la moyenne")
                .modifiedBy(actor)
                .build());
        grade.setScore(course.getAverage());
        grade.setUpdatedBy(actor);
        gradeRepository.save(grade);
      }
      return;
    }

    gradeRepository.save(
        JGrade.builder()
            .id(UUID.randomUUID().toString())
            .student(userRepository.getReferenceById(studentId))
            .course(courseRepository.getReferenceById(course.getCourse().getId()))
            .academicYear(course.getAcademicYear())
            .score(course.getAverage())
            .updatedBy(actor)
            .build());
  }

  private List<JCourse> programCourses(JUser student) {
    List<String> groupIds =
        studentGroupRepository.findByStudentId(student.getId()).stream()
            .map(sg -> sg.getGroup().getId())
            .distinct()
            .toList();
    if (groupIds.isEmpty()) return List.of();

    return courseRepository.findByGroupsIdIn(groupIds).stream()
        .filter(course -> isForTrack(course.getTrack(), student.getTrack()))
        .collect(
            Collectors.collectingAndThen(
                Collectors.toMap(
                    JCourse::getId, course -> course, (first, second) -> first, LinkedHashMap::new),
                map -> new ArrayList<>(map.values())));
  }

  private boolean isForTrack(JTrack courseTrack, JTrack studentTrack) {
    if (courseTrack == null || studentTrack == null) return true;
    return courseTrack == JTrack.COMMON || courseTrack == studentTrack;
  }

  private Double weightedAverage(List<JExam> exams, Map<String, Double> scoreByExamId) {
    double weighted = 0;
    double totalCoefficient = 0;
    for (JExam exam : exams) {
      Double score = scoreByExamId.get(exam.getId());
      if (score == null) return null;
      double coefficient = (double) exam.getCoefficientNum() / exam.getCoefficientDen();
      weighted += score * coefficient;
      totalCoefficient += coefficient;
    }
    if (totalCoefficient == 0) return null;
    return round(weighted / totalCoefficient);
  }

  private Double weightedAverageOf(List<CourseResult> courseResults) {
    double weighted = 0;
    int totalCredits = 0;
    for (CourseResult course : courseResults) {
      if (course.getAverage() == null) continue;
      weighted += course.getAverage() * course.getCourse().getCredits();
      totalCredits += course.getCourse().getCredits();
    }
    if (totalCredits == 0) return null;
    return round(weighted / totalCredits);
  }

  private Double round(Double value) {
    if (value == null) return null;
    return Math.round(value * 100.0) / 100.0;
  }
}
