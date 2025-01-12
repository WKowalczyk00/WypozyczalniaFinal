package Wypozyczalnia;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileOutputStream;
import java.util.List;
//trzeba dodac paczke ctrl alt shift s
//https://github.com/itext/itext-java/releases?expanded=true&page=4&q=7.0.4
//dodałem caly folder
public class PdfGenerator {

    public static void generateCarListPdf(String pdfPath, Car car) {
        try {
            // Tworzenie PDF
            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Dodanie tytułu
            document.add(new Paragraph("Lista samochodów").setBold().setFontSize(16));

            document.add(new Paragraph(car.toString()));


            // Zamknięcie dokumentu
            document.close();
            System.out.println("PDF wygenerowany pomyślnie: " + pdfPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
