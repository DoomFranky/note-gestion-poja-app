package hei.school.gestion.endpoint.rest.controller;

import hei.school.gestion.entity.model.Promotion;
import hei.school.gestion.mapper.PromotionMapper;
import hei.school.gestion.repository.PromotionRepository;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PromotionViewController {

  private final PromotionRepository promotionRepository;
  private final PromotionMapper promotionMapper;

  @GetMapping("/")
  public String listPromotions(Model model) {
    List<Promotion> promotions =
        promotionRepository.findAll().stream()
            .map(promotionMapper::toDomain)
            .sorted(Comparator.comparing(Promotion::getLabel))
            .toList();
    model.addAttribute("promotions", promotions);
    return "promotions";
  }
}
