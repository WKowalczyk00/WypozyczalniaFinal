package Wypozyczalnia;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
//trzeba dodac paczke ctrl alt shift s
//https://github.com/itext/itext-java/releases?expanded=true&page=4&q=7.0.4
//dodałem caly folder
public class PdfGenerator {

    public static void generateCarListPdf(String pdfPath, Car car, String customerName, String rentalPeriod, double totalCost, String driversLicenceNumber) {
        try {
            // Tworzenie PDF
            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Faktura wynajmu samochodu").setBold().setFontSize(16).setMarginBottom(20));

            // Dodanie szczegółów klienta
            document.add(new Paragraph("Dane klienta:").setBold());
            document.add(new Paragraph("Imię i nazwisko: " + customerName));
            document.add(new Paragraph("Identyfikujący się prawem jazdy o numerze: " + driversLicenceNumber));

            // Dodanie szczegółów wynajmu
            document.add(new Paragraph("\nSzczegóły wynajmu:").setBold());
            document.add(new Paragraph("Okres wynajmu: " + rentalPeriod));
            document.add(new Paragraph("Data wystawienia faktury: " + new Date().toString()));

            // Szczegóły samochodu
            document.add(new Paragraph("\nSzczegóły samochodu:").setBold());
            document.add(new Paragraph(car.toString()));

            // Koszt całkowity
            document.add(new Paragraph("\nKoszt całkowity: " + String.format("%.2f PLN", totalCost)).setBold());



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
