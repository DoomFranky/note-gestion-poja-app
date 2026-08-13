package hei.school.gestion.service;

import hei.school.gestion.endpoint.rest.dto.GraduatesResponse;
import hei.school.gestion.entity.domain.JPromotion;
import hei.school.gestion.entity.domain.JUser;
import hei.school.gestion.entity.domain.JUserRole;
import hei.school.gestion.entity.model.Bulletin;
import hei.school.gestion.entity.model.GraduateResult;
import hei.school.gestion.entity.model.YearResult;
import hei.school.gestion.mapper.GraduateDtoMapper;
import hei.school.gestion.mapper.UserMapper;
import hei.school.gestion.repository.PromotionRepository;
import hei.school.gestion.repository.UserRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class GraduateService {

  private final UserRepository userRepository;
  private final PromotionRepository promotionRepository;
  private final GradeCalculationService gradeCalculationService;
  private final UserMapper userMapper;
  private final GraduateDtoMapper graduateDtoMapper;

  public GraduatesResponse getGraduates(String promotionId, String track) {
    JPromotion promotion =
        promotionRepository
            .findById(promotionId)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion introuvable"));

    List<GraduateResult> graduates = new ArrayList<>();
    for (JUser student : studentsOf(promotionId, track)) {
      Bulletin bulletin = gradeCalculationService.computeBulletin(student.getId());
      if (isGraduate(bulletin)) {
        graduates.add(
            GraduateResult.builder()
                .student(userMapper.toDomain(student))
                .overallAverage(bulletin.getOverallAverage())
                .build());
      }
    }

    graduates.sort(
        Comparator.comparing(GraduateResult::getOverallAverage)
            .reversed()
            .thenComparing(g -> g.getStudent().getLastName())
            .thenComparing(g -> g.getStudent().getFirstName()));
    for (int i = 0; i < graduates.size(); i++) {
      graduates.get(i).setRank(i + 1);
    }

    return graduateDtoMapper.toResponse(promotionId, promotion.getLabel(), track, graduates);
  }

  private List<JUser> studentsOf(String promotionId, String track) {
    return userRepository.findByPromotionId(promotionId).stream()
        .filter(user -> user.getRole() == JUserRole.STUDENT)
        .filter(
            user ->
                !StringUtils.hasText(track)
                    || (user.getTrack() != null && user.getTrack().name().equalsIgnoreCase(track)))
        .toList();
  }

  private boolean isGraduate(Bulletin bulletin) {
    if (bulletin.getYears() == null || bulletin.getYears().size() < 3) return false;
    for (YearResult year : bulletin.getYears()) {
      if (year.getCourses() == null || year.getCourses().isEmpty()) return false;
      for (var course : year.getCourses()) {
        if (course.getAverage() == null || course.getAverage() < 10) return false;
      }
    }
    return true;
  }
}
