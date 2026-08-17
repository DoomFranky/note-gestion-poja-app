package hei.school.gestion.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hei.school.gestion.entity.model.Bulletin;
import hei.school.gestion.entity.model.Course;
import hei.school.gestion.entity.model.CourseResult;
import hei.school.gestion.entity.model.Promotion;
import hei.school.gestion.entity.model.Track;
import hei.school.gestion.entity.model.User;
import hei.school.gestion.entity.model.UserRole;
import hei.school.gestion.entity.model.YearResult;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;

class TranscriptPdfGeneratorTest {

  private final TranscriptPdfGenerator generator = new TranscriptPdfGenerator();

  private Course course(String code, String name, int credits) {
    return Course.builder().id(code).code(code).name(name).credits(credits).build();
  }

  private User student() {
    return User.builder()
        .id("s1")
        .ref("STD24001")
        .firstName("Jean")
        .lastName("Rakoto")
        .email("jean.rakoto@hei.school")
        .role(UserRole.STUDENT)
        .track(Track.EL)
        .promotion(Promotion.builder().id("p1").label("2024").build())
        .build();
  }

  private Bulletin completeBulletin() {
    YearResult year1 =
        YearResult.builder()
            .year(1)
            .provisional(false)
            .average(12.5)
            .credits(60)
            .courses(
                List.of(
                    CourseResult.builder()
                        .course(course("PROG1", "Programmation 1", 6))
                        .average(13.0)
                        .complete(true)
                        .build(),
                    CourseResult.builder()
                        .course(course("WEB1", "Développement web 1", 5))
                        .average(11.5)
                        .complete(true)
                        .build()))
            .build();
    YearResult year2 =
        YearResult.builder()
            .year(2)
            .provisional(true)
            .average(10.25)
            .credits(45)
            .courses(
                List.of(
                    CourseResult.builder()
                        .course(course("PROG3", "Programmation avancée", 5))
                        .average(10.0)
                        .complete(false)
                        .build()))
            .build();
    return Bulletin.builder()
        .student(student())
        .years(List.of(year1, year2))
        .overallAverage(11.8)
        .totalCredits(105)
        .build();
  }

  @Test
  void generatesValidPdfWithStudentInfoAndCourses() {
    byte[] pdf = generator.generate(completeBulletin());
    assertNotNull(pdf);
    assertTrue(pdf.length > 1000);
    assertArrayEquals("%PDF".getBytes(StandardCharsets.US_ASCII), firstBytes(pdf, 4));
    String text = new String(pdf, StandardCharsets.ISO_8859_1);
    assertTrue(text.contains("STD24001"));
    assertTrue(text.contains("Jean"));
    assertTrue(text.contains("Rakoto"));
    assertTrue(text.contains("PROG1"));
    assertTrue(text.contains("Programmation 1"));
    assertTrue(text.contains("provisoire"));
  }

  @Test
  void generatesPdfForBulletinWithEmptyYears() {
    Bulletin bulletin =
        Bulletin.builder()
            .student(student())
            .years(List.of())
            .overallAverage(null)
            .totalCredits(0)
            .build();
    byte[] pdf = generator.generate(bulletin);
    assertTrue(pdf.length > 500);
    assertArrayEquals("%PDF".getBytes(StandardCharsets.US_ASCII), firstBytes(pdf, 4));
  }

  @Test
  void generatesPdfForBulletinWithNullYears() {
    Bulletin bulletin = Bulletin.builder().student(student()).years(null).build();
    byte[] pdf = generator.generate(bulletin);
    assertTrue(pdf.length > 500);
  }

  @Test
  void handlesStudentWithoutPromotionAndTrack() {
    User bareStudent =
        User.builder().id("s2").ref("STD26001").firstName("Lova").lastName("Andria").build();
    Bulletin bulletin = Bulletin.builder().student(bareStudent).years(List.of()).build();
    byte[] pdf = generator.generate(bulletin);
    assertTrue(pdf.length > 500);
  }

  private byte[] firstBytes(byte[] data, int length) {
    byte[] result = new byte[length];
    System.arraycopy(data, 0, result, 0, length);
    return result;
  }

  @Test
  void generatesCompleteMarkerWhenNotProvisional() {
    byte[] pdf = generator.generate(completeBulletin());
    String text = new String(pdf, StandardCharsets.ISO_8859_1);
    assertTrue(text.contains("complet"));
    assertEquals(2, countOccurrences(text, "Année"));
  }

  private int countOccurrences(String text, String needle) {
    int count = 0;
    int index = 0;
    while ((index = text.indexOf(needle, index)) != -1) {
      count++;
      index += needle.length();
    }
    return count;
  }
}
