package hei.school.gestion.mapper;

import hei.school.gestion.endpoint.rest.dto.GraduatesResponse;
import hei.school.gestion.entity.model.GraduateResult;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GraduateDtoMapper {

  public GraduatesResponse toResponse(
      String promotionId, String promotionLabel, String track, List<GraduateResult> graduates) {
    if (graduates == null) return null;

    return new GraduatesResponse(
        promotionId,
        promotionLabel,
        track,
        graduates.stream()
            .map(
                graduate ->
                    new GraduatesResponse.GraduateResponse(
                        graduate.getRank(),
                        graduate.getStudent().getRef(),
                        graduate.getStudent().getFirstName(),
                        graduate.getStudent().getLastName(),
                        graduate.getOverallAverage()))
            .toList());
  }
}
