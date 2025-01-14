package Wypozyczalnia;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
//import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PdfGenerator {

    public static void generateCarListPdf(String pdfPath, Car car, String customerName, String rentalPeriod, double totalCost, String driversLicenceNumber) {
        try {
            // Tworzenie PDF
            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Nagłówek z nazwą firmy
            document.add(new Paragraph("Wypozyczalnia Samochodów Oltek")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Faktura wynajmu samochodu")
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));
//            document.add(new LineSeparator(new SolidBorder(1)).setMarginBottom(20));

            Date date = new Date();
            SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm dd MM yyyy");
            String formattedDate = newFormat.format(date);
            // Numer faktury i data
            document.add(new Paragraph("Numer faktury: " + "FV123456").setBold());
            document.add(new Paragraph("Data wystawienia: " + formattedDate));

            // Dane klienta
            document.add(new Paragraph("Dane klienta:").setBold());
            document.add(new Paragraph("Imię i nazwisko: " + customerName));
            document.add(new Paragraph("Prawo jazdy: " + driversLicenceNumber));
//            document.add(new LineSeparator(new SolidBorder(1)).setMarginBottom(10));

            // Tabela szczegółów wynajmu
            Table table = new Table(UnitValue.createPercentArray(new float[]{4, 6})).useAllAvailableWidth();
//            table.addHeaderCell(new Cell().add(new Paragraph("Szczegóły").setBold()).setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY));
//            table.addHeaderCell(new Cell().add(new Paragraph("Dane").setBold()).setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY));

            table.addCell("Okres wynajmu:");
            table.addCell(rentalPeriod);
            table.addCell("Samochod:");
            table.addCell(String.format("Model: %s, Klasa: %s, Skrzynia: %s, Rejestracja: %s, Liczba siedzen: %d",
                    car.getModel(),car.getCarClass(), car.getTransmission(), car.getRegistrationNumber(), car.getSeatCount()));
            table.addCell("Koszt calkowity:");
            table.addCell(String.format("%.2f PLN", totalCost));

            document.add(table.setMarginBottom(20));

            // Podsumowanie
            document.add(new Paragraph("Koszt calkowity do zaplaty: " + String.format("%.2f PLN", totalCost))
                    .setBold()
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(20));

            // Stopka
//            document.add(new LineSeparator(new SolidBorder(1)).setMarginTop(20));
            document.add(new Paragraph("Dziekujemy za skorzystanie z naszych uslug!")
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Wypozyczalnia Samochodow Oltek, ul. Sloneczko 123, 30-001 Krakow")
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Kontakt: +48 123 456 789 | email: kontakt@wypozyczalnia.pl")
                    .setTextAlignment(TextAlignment.CENTER));

            // Zamknięcie dokumentu
            document.close();
            System.out.println("PDF wygenerowany pomyslnie: " + pdfPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
