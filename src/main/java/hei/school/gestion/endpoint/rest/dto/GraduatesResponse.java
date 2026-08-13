package hei.school.gestion.endpoint.rest.dto;

import java.util.List;

public record GraduatesResponse(
    String promotionId, String promotionLabel, String track, List<GraduateResponse> graduates) {

  public record GraduateResponse(
      Integer rank, String ref, String firstName, String lastName, Double overallAverage) {}
}
