package hei.school.gestion.mapper;

import hei.school.gestion.endpoint.rest.dto.TranscriptResponse;
import hei.school.gestion.entity.model.Bulletin;
import hei.school.gestion.entity.model.CourseResult;
import hei.school.gestion.entity.model.YearResult;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TranscriptDtoMapper {

  public TranscriptResponse toResponse(Bulletin bulletin) {
    if (bulletin == null) return null;

    return new TranscriptResponse(
        bulletin.getStudent().getId(),
        bulletin.getStudent().getRef(),
        bulletin.getStudent().getFirstName(),
        bulletin.getStudent().getLastName(),
        bulletin.getStudent().getPromotion() != null
            ? bulletin.getStudent().getPromotion().getLabel()
            : null,
        bulletin.getStudent().getTrack() != null ? bulletin.getStudent().getTrack().name() : null,
        toYearTranscripts(bulletin.getYears()),
        bulletin.getOverallAverage(),
        bulletin.getTotalCredits());
  }

  private List<TranscriptResponse.YearTranscript> toYearTranscripts(List<YearResult> years) {
    if (years == null) return null;
    return years.stream()
        .map(
            year ->
                new TranscriptResponse.YearTranscript(
                    year.getYear(),
                    year.isProvisional(),
                    year.getAverage(),
                    year.getCredits(),
                    toCourseTranscripts(year.getCourses())))
        .toList();
  }

  private List<TranscriptResponse.YearTranscript.CourseTranscript> toCourseTranscripts(
      List<CourseResult> courses) {
    if (courses == null) return null;
    return courses.stream()
        .map(
            course ->
                new TranscriptResponse.YearTranscript.CourseTranscript(
                    course.getCourse().getId(),
                    course.getCourse().getCode(),
                    course.getCourse().getName(),
                    course.getCourse().getCredits(),
                    course.getAverage(),
                    course.isComplete()))
        .toList();
  }
}
