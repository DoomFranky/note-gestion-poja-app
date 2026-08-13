package hei.school.gestion.endpoint.rest.controller;

import hei.school.gestion.endpoint.rest.dto.GraduatesResponse;
import hei.school.gestion.service.GraduateService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class GraduateController {

  private final GraduateService graduateService;

  @GetMapping("/download/graduates/{promotionId}")
  public ResponseEntity<byte[]> downloadGraduates(
      @PathVariable String promotionId, @RequestParam(required = false) String track) {
    GraduatesResponse graduates = graduateService.getGraduates(promotionId, track);
    String filename = "graduates-" + (track == null ? "all" : track) + ".xlsx";
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
        .contentType(
            MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        .body(buildXlsx(graduates));
  }

  private byte[] buildXlsx(GraduatesResponse graduates) {
    try (Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      Sheet sheet = workbook.createSheet("Diplômés");
      String[] columns = {"Rang", "Réf", "Prénom", "Nom", "Moyenne générale"};
      Row header = sheet.createRow(0);
      for (int i = 0; i < columns.length; i++) {
        header.createCell(i).setCellValue(columns[i]);
      }
      int rowIndex = 1;
      for (GraduatesResponse.GraduateResponse graduate : graduates.graduates()) {
        Row row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue(graduate.rank());
        row.createCell(1).setCellValue(graduate.ref());
        row.createCell(2).setCellValue(graduate.firstName());
        row.createCell(3).setCellValue(graduate.lastName());
        row.createCell(4).setCellValue(graduate.overallAverage());
      }
      workbook.write(outputStream);
      return outputStream.toByteArray();
    } catch (IOException e) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la génération du fichier");
    }
  }
}
