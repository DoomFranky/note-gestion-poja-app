package hei.school.gestion.endpoint.rest.dto;

import java.util.List;

public record TranscriptResponse(
    String studentId,
    String ref,
    String firstName,
    String lastName,
    String promotion,
    String track,
    List<YearTranscript> years,
    Double overallAverage,
    Integer totalCredits) {

  public record YearTranscript(
      Integer year,
      Boolean provisional,
      Double average,
      Integer credits,
      List<CourseTranscript> courses) {

    public record CourseTranscript(
        String courseId,
        String code,
        String name,
        Integer credits,
        Double average,
        Boolean complete) {}
  }
}
