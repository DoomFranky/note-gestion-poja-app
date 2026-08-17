package hei.school.gestion.service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfStream;
import com.lowagie.text.pdf.PdfWriter;
import hei.school.gestion.entity.model.Bulletin;
import hei.school.gestion.entity.model.CourseResult;
import hei.school.gestion.entity.model.User;
import hei.school.gestion.entity.model.YearResult;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
public class TranscriptPdfGenerator {

  private static final Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD);
  private static final Font SECTION_FONT =
      FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
  private static final Font BODY_FONT = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);

  public byte[] generate(Bulletin bulletin) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Document document = new Document(PageSize.A4);
    try {
      PdfWriter writer = PdfWriter.getInstance(document, outputStream);
      writer.setCompressionLevel(PdfStream.NO_COMPRESSION);
      document.open();
      addTitle(document);
      addStudentInfo(document, bulletin.getStudent());
      addYears(document, bulletin.getYears());
      addOverall(document, bulletin);
    } catch (DocumentException e) {
      throw new RuntimeException("Erreur lors de la génération du relevé de notes", e);
    } finally {
      document.close();
    }
    return outputStream.toByteArray();
  }

  private void addTitle(Document document) throws DocumentException {
    Paragraph title = new Paragraph("RELEVÉ DE NOTES", TITLE_FONT);
    title.setAlignment(Element.ALIGN_CENTER);
    document.add(title);
    document.add(new Paragraph(" "));
  }

  private void addStudentInfo(Document document, User student) throws DocumentException {
    document.add(new Paragraph("Référence : " + nullSafe(student.getRef()), BODY_FONT));
    document.add(
        new Paragraph(
            "Nom : "
                + nullSafe(student.getLastName())
                + "  Prénom : "
                + nullSafe(student.getFirstName()),
            BODY_FONT));
    document.add(new Paragraph("Promotion : " + promotionLabel(student), BODY_FONT));
    document.add(new Paragraph("Parcours : " + trackLabel(student), BODY_FONT));
    document.add(new Paragraph(" "));
  }

  private void addYears(Document document, List<YearResult> years) throws DocumentException {
    if (years == null) return;
    for (YearResult year : years) {
      if (year == null) continue;
      Paragraph header =
          new Paragraph(
              "Année "
                  + year.getYear()
                  + (year.isProvisional() ? " — Relevé provisoire" : " — Relevé complet"),
              SECTION_FONT);
      document.add(header);
      document.add(createCourseTable(year.getCourses()));
      Paragraph summary =
          new Paragraph(
              "Moyenne de l'année : "
                  + formatAverage(year.getAverage())
                  + "    Crédits validés : "
                  + (year.getCredits() == null ? "-" : year.getCredits()),
              BODY_FONT);
      document.add(summary);
      document.add(new Paragraph(" "));
    }
  }

  private PdfPTable createCourseTable(List<CourseResult> courses) {
    PdfPTable table = new PdfPTable(5);
    table.setWidthPercentage(100);
    table.addCell(cell("Code", SECTION_FONT));
    table.addCell(cell("Intitulé", SECTION_FONT));
    table.addCell(cell("Crédits", SECTION_FONT));
    table.addCell(cell("Moyenne", SECTION_FONT));
    table.addCell(cell("État", SECTION_FONT));
    if (courses != null) {
      for (CourseResult course : courses) {
        if (course == null) continue;
        table.addCell(cell(nullSafe(course.getCourse().getCode()), BODY_FONT));
        table.addCell(cell(nullSafe(course.getCourse().getName()), BODY_FONT));
        table.addCell(cell(integerValue(course.getCourse().getCredits()), BODY_FONT));
        table.addCell(cell(formatAverage(course.getAverage()), BODY_FONT));
        table.addCell(cell(course.isComplete() ? "Complet" : "Incomplet", BODY_FONT));
      }
    }
    return table;
  }

  private void addOverall(Document document, Bulletin bulletin) throws DocumentException {
    document.add(new Paragraph(" "));
    document.add(
        new Paragraph(
            "Moyenne générale sur 3 ans : "
                + formatAverage(bulletin.getOverallAverage())
                + "    Total crédits : "
                + (bulletin.getTotalCredits() == null ? "-" : bulletin.getTotalCredits()),
            SECTION_FONT));
  }

  private PdfPCell cell(String content, Font font) {
    PdfPCell cell = new PdfPCell(new Phrase(content == null ? "" : content, font));
    cell.setPadding(4);
    return cell;
  }

  private String promotionLabel(User student) {
    return student.getPromotion() != null ? nullSafe(student.getPromotion().getLabel()) : "-";
  }

  private String trackLabel(User student) {
    return student.getTrack() != null ? student.getTrack().name() : "-";
  }

  private String formatAverage(Double average) {
    return average == null ? "-" : String.format(Locale.FRENCH, "%.2f", average);
  }

  private String integerValue(Integer value) {
    return value == null ? "-" : String.valueOf(value);
  }

  private String nullSafe(String value) {
    return value == null ? "" : value;
  }
}
