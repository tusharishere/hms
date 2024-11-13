package com.hms.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

@Service
public class PdfGeneratorService {

    public void generateBookingPdf(String filePath) throws IOException, DocumentException, URISyntaxException {
        // Check if the file exists
        File file = new File(filePath);
        if (file.exists()) {
            // Generate a new file name with a timestamp
            String newFilePath = generateNewFilePath(filePath);
            file = new File(newFilePath);
        }

        // Generate the PDF
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();

        // Add header
        addHeader(document);

        // Add table
        PdfPTable table = new PdfPTable(3); // 3 columns
        addTableHeader(table);
        addRows(table);
        addCustomRows(table);
        document.add(table);

        // Add footer
        addFooter(writer);

        document.close();
    }

    // Header method
    private void addHeader(Document document) throws DocumentException {
        Paragraph header = new Paragraph("Hotel Booking Invoice", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(new Chunk("\n"));  // Add space after the header
    }

    // Footer method (using PdfWriter for adding footer)
    private void addFooter(PdfWriter writer) {
        writer.setPageEvent(new PdfPageEventHelper() {
            @Override
            public void onEndPage(PdfWriter writer, Document document) {
                // Footer content
                Phrase footer = new Phrase("Page " + writer.getPageNumber(), FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY));
                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, footer, 290, 30, 0);
            }
        });
    }

    // Table header method
    private void addTableHeader(PdfPTable table) {
        Stream.of("Column Header 1", "Column Header 2", "Column Header 3")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    // Table rows method
    private void addRows(PdfPTable table) {
        for (int i = 0; i < 5; i++) { // 5 rows
            Stream.of("Row " + (i + 1), "Value " + (i + 1), "Value " + (i + 1))
                    .forEach(value -> {
                        PdfPCell cell = new PdfPCell();
                        cell.setPhrase(new Phrase(value));
                        table.addCell(cell);
                    });
        }
    }

    // Custom rows method
    private void addCustomRows(PdfPTable table) {
        Stream.of("Custom 1", "Custom 2", "Custom 3", "Custom 4", "Custom 5")
                .forEach(value -> {
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(value));
                    table.addCell(cell);
                });
    }

    // Method to generate a new file path with a timestamp to avoid overwriting
    private String generateNewFilePath(String originalFilePath) {
        String fileExtension = originalFilePath.substring(originalFilePath.lastIndexOf("."));
        String fileName = originalFilePath.substring(0, originalFilePath.lastIndexOf("."));

        // Create a new file name with the current timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return fileName + "_" + timestamp + fileExtension;
    }
}
