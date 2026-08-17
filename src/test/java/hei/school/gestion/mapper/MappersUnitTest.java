package hei.school.gestion.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import hei.school.gestion.entity.domain.JCourse;
import hei.school.gestion.entity.domain.JExam;
import hei.school.gestion.entity.domain.JExamGrade;
import hei.school.gestion.entity.domain.JGrade;
import hei.school.gestion.entity.domain.JGradeHistory;
import hei.school.gestion.entity.domain.JGroup;
import hei.school.gestion.entity.domain.JPromotion;
import hei.school.gestion.entity.domain.JStudentGroup;
import hei.school.gestion.entity.domain.JTrack;
import hei.school.gestion.entity.domain.JUser;
import hei.school.gestion.entity.domain.JUserRole;
import hei.school.gestion.entity.model.Course;
import hei.school.gestion.entity.model.Exam;
import hei.school.gestion.entity.model.ExamGrade;
import hei.school.gestion.entity.model.Grade;
import hei.school.gestion.entity.model.GradeHistory;
import hei.school.gestion.entity.model.Group;
import hei.school.gestion.entity.model.Promotion;
import hei.school.gestion.entity.model.StudentGroup;
import hei.school.gestion.entity.model.Track;
import hei.school.gestion.entity.model.User;
import hei.school.gestion.entity.model.UserRole;
import java.time.Instant;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MappersUnitTest {

  private final PromotionMapper promotionMapper = new PromotionMapper();
  private final UserMapper userMapper = new UserMapper(promotionMapper);
  private final GroupMapper groupMapper = new GroupMapper();
  private final CourseMapper courseMapper = new CourseMapper(userMapper, groupMapper);
  private final ExamMapper examMapper = new ExamMapper(courseMapper);
  private final GradeMapper gradeMapper = new GradeMapper(userMapper, courseMapper);
  private final ExamGradeMapper examGradeMapper = new ExamGradeMapper(examMapper, userMapper);
  private final GradeHistoryMapper gradeHistoryMapper =
      new GradeHistoryMapper(gradeMapper, userMapper);
  private final StudentGroupMapper studentGroupMapper =
      new StudentGroupMapper(userMapper, groupMapper);

  private JPromotion jPromotion() {
    return JPromotion.builder()
        .id("p1")
        .label("2024")
        .createdAt(Instant.parse("2024-09-01T08:00:00Z"))
        .build();
  }

  private JUser jTeacher() {
    return JUser.builder()
        .id("u1")
        .ref("TCH1")
        .firstName("Rina")
        .lastName("Andriamahery")
        .email("rina.andriamahery@hei.school")
        .role(JUserRole.TEACHER)
        .track(null)
        .promotion(null)
        .build();
  }

  private JUser jStudent() {
    return JUser.builder()
        .id("s1")
        .ref("STD24001")
        .firstName("Jean")
        .lastName("Rakoto")
        .email("jean.rakoto@hei.school")
        .role(JUserRole.STUDENT)
        .track(JTrack.EL)
        .promotion(jPromotion())
        .build();
  }

  private JGroup jGroup() {
    return JGroup.builder()
        .id("g1")
        .name("K1")
        .academicYear("2024-2025")
        .createdAt(Instant.parse("2024-09-01T08:00:00Z"))
        .build();
  }

  private JCourse jCourse() {
    return JCourse.builder()
        .id("c1")
        .code("PROG1")
        .name("Programmation 1")
        .credits(6)
        .track(JTrack.EL)
        .teachers(Set.of(jTeacher()))
        .groups(Set.of(jGroup()))
        .build();
  }

  private JExam jExam() {
    return JExam.builder()
        .id("e1")
        .course(jCourse())
        .academicYear(1)
        .label("Examen final")
        .examDatetime(Instant.parse("2024-12-10T09:00:00Z"))
        .coefficientNum(1)
        .coefficientDen(2)
        .build();
  }

  private JGrade jGrade() {
    return JGrade.builder()
        .id("gr1")
        .student(jStudent())
        .course(jCourse())
        .score(13.5)
        .academicYear(1)
        .updatedAt(Instant.parse("2024-12-20T09:00:00Z"))
        .updatedBy(jTeacher())
        .build();
  }

  private JExamGrade jExamGrade() {
    return JExamGrade.builder()
        .id("eg1")
        .exam(jExam())
        .student(jStudent())
        .score(14.0)
        .updatedAt(Instant.parse("2024-12-20T09:00:00Z"))
        .updatedBy(jTeacher())
        .build();
  }

  private JGradeHistory jGradeHistory() {
    return JGradeHistory.builder()
        .id("gh1")
        .grade(jGrade())
        .previousScore(10.0)
        .newScore(13.5)
        .reason("Réclamation")
        .modifiedBy(jTeacher())
        .modifiedAt(Instant.parse("2024-12-21T09:00:00Z"))
        .build();
  }

  private JStudentGroup jStudentGroup() {
    return JStudentGroup.builder()
        .id("sg1")
        .student(jStudent())
        .group(jGroup())
        .joinedAt(Instant.parse("2024-09-01T08:00:00Z"))
        .leftAt(Instant.parse("2025-01-15T08:00:00Z"))
        .build();
  }

  @Test
  void mapsPromotionBothWays() {
    Promotion domain = promotionMapper.toDomain(jPromotion());
    assertEquals("p1", domain.getId());
    assertEquals("2024", domain.getLabel());
    assertEquals(jPromotion().getCreatedAt(), domain.getCreatedAt());

    JPromotion entity = promotionMapper.toEntity(domain);
    assertEquals("p1", entity.getId());
    assertEquals("2024", entity.getLabel());
    assertNull(promotionMapper.toDomain(null));
    assertNull(promotionMapper.toEntity(null));
  }

  @Test
  void mapsUserBothWays() {
    User domain = userMapper.toDomain(jStudent());
    assertEquals("s1", domain.getId());
    assertEquals("STD24001", domain.getRef());
    assertEquals(UserRole.STUDENT, domain.getRole());
    assertEquals(Track.EL, domain.getTrack());
    assertEquals("2024", domain.getPromotion().getLabel());

    JUser entity = userMapper.toEntity(domain);
    assertEquals("s1", entity.getId());
    assertEquals(JUserRole.STUDENT, entity.getRole());
    assertEquals(JTrack.EL, entity.getTrack());
    assertEquals("2024", entity.getPromotion().getLabel());
    assertNull(userMapper.toDomain(null));
    assertNull(userMapper.toEntity(null));
  }

  @Test
  void mapsUserWithNullTrackAndPromotion() {
    User domain = userMapper.toDomain(jTeacher());
    assertNull(domain.getTrack());
    assertNull(domain.getPromotion());

    JUser entity = userMapper.toEntity(domain);
    assertNull(entity.getTrack());
    assertNull(entity.getPromotion());
  }

  @Test
  void mapsGroupBothWays() {
    Group domain = groupMapper.toDomain(jGroup());
    assertEquals("g1", domain.getId());
    assertEquals("K1", domain.getName());
    assertEquals("2024-2025", domain.getAcademicYear());

    JGroup entity = groupMapper.toEntity(domain);
    assertEquals("g1", entity.getId());
    assertEquals("K1", entity.getName());
    assertNull(groupMapper.toDomain(null));
    assertNull(groupMapper.toEntity(null));
  }

  @Test
  void mapsCourseBothWays() {
    Course domain = courseMapper.toDomain(jCourse());
    assertEquals("c1", domain.getId());
    assertEquals("PROG1", domain.getCode());
    assertEquals(Track.EL, domain.getTrack());
    assertEquals(1, domain.getTeachers().size());
    assertEquals(1, domain.getGroups().size());
    assertEquals("Rina", domain.getTeachers().iterator().next().getFirstName());
    assertEquals("K1", domain.getGroups().iterator().next().getName());

    JCourse entity = courseMapper.toEntity(domain);
    assertEquals("c1", entity.getId());
    assertEquals(JTrack.EL, entity.getTrack());
    assertEquals(1, entity.getTeachers().size());
    assertEquals(1, entity.getGroups().size());
  }

  @Test
  void mapsCourseWithNullTrackAndEmptyCollections() {
    JCourse jCourse = JCourse.builder().id("c2").code("C2").name("Cours 2").credits(2).build();
    Course domain = courseMapper.toDomain(jCourse);
    assertNull(domain.getTrack());
    assertEquals(0, domain.getTeachers().size());
    assertEquals(0, domain.getGroups().size());

    JCourse entity = courseMapper.toEntity(domain);
    assertNull(entity.getTrack());
    assertEquals(0, entity.getTeachers().size());
    assertNull(courseMapper.toDomain(null));
    assertNull(courseMapper.toEntity(null));
  }

  @Test
  void mapsExamBothWays() {
    Exam domain = examMapper.toDomain(jExam());
    assertEquals("e1", domain.getId());
    assertEquals("c1", domain.getCourse().getId());
    assertEquals(1, domain.getCoefficientNum());
    assertEquals(2, domain.getCoefficientDen());

    JExam entity = examMapper.toEntity(domain);
    assertEquals("e1", entity.getId());
    assertEquals("c1", entity.getCourse().getId());
    assertNull(examMapper.toDomain(null));
    assertNull(examMapper.toEntity(null));
  }

  @Test
  void mapsGradeBothWays() {
    Grade domain = gradeMapper.toDomain(jGrade());
    assertEquals("gr1", domain.getId());
    assertEquals("s1", domain.getStudent().getId());
    assertEquals("c1", domain.getCourse().getId());
    assertEquals(13.5, domain.getScore());

    JGrade entity = gradeMapper.toEntity(domain);
    assertEquals("gr1", entity.getId());
    assertEquals("s1", entity.getStudent().getId());
    assertEquals("c1", entity.getCourse().getId());
    assertEquals(13.5, entity.getScore());
    assertNull(gradeMapper.toDomain(null));
    assertNull(gradeMapper.toEntity(null));
  }

  @Test
  void mapsExamGradeBothWays() {
    ExamGrade domain = examGradeMapper.toDomain(jExamGrade());
    assertEquals("eg1", domain.getId());
    assertEquals("e1", domain.getExam().getId());
    assertEquals("s1", domain.getStudent().getId());
    assertEquals(14.0, domain.getScore());

    JExamGrade entity = examGradeMapper.toEntity(domain);
    assertEquals("eg1", entity.getId());
    assertEquals("e1", entity.getExam().getId());
    assertEquals("s1", entity.getStudent().getId());
    assertNull(examGradeMapper.toDomain(null));
    assertNull(examGradeMapper.toEntity(null));
  }

  @Test
  void mapsGradeHistoryBothWays() {
    GradeHistory domain = gradeHistoryMapper.toDomain(jGradeHistory());
    assertEquals("gh1", domain.getId());
    assertEquals("gr1", domain.getGrade().getId());
    assertEquals(10.0, domain.getPreviousScore());
    assertEquals("Réclamation", domain.getReason());
    assertEquals("u1", domain.getModifiedBy().getId());

    JGradeHistory entity = gradeHistoryMapper.toEntity(domain);
    assertEquals("gh1", entity.getId());
    assertEquals("gr1", entity.getGrade().getId());
    assertEquals(10.0, entity.getPreviousScore());
    assertEquals("u1", entity.getModifiedBy().getId());
    assertNull(gradeHistoryMapper.toDomain(null));
    assertNull(gradeHistoryMapper.toEntity(null));
  }

  @Test
  void mapsStudentGroupBothWays() {
    StudentGroup domain = studentGroupMapper.toDomain(jStudentGroup());
    assertEquals("sg1", domain.getId());
    assertEquals("s1", domain.getStudent().getId());
    assertEquals("g1", domain.getGroup().getId());

    JStudentGroup entity = studentGroupMapper.toEntity(domain);
    assertEquals("sg1", entity.getId());
    assertEquals("s1", entity.getStudent().getId());
    assertEquals("g1", entity.getGroup().getId());
    assertNull(studentGroupMapper.toDomain(null));
    assertNull(studentGroupMapper.toEntity(null));
  }
}
