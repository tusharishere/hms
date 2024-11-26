package com.hms.utils;

import com.hms.payload.BookingDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class PdfGeneratorService {

    public boolean generateBookingPdf(String filePath, BookingDto bookingDto) throws IOException, DocumentException{
        File file = new File(filePath);

        // Generate the PDF
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();

        addHeader(document);

        PdfPTable table = new PdfPTable(2); // Set to 2 columns: one for header, one for value
        addRows(table, bookingDto);
        document.add(table);

        addFooter(writer);

        document.close();
        return file.exists();
    }

    private void addHeader(Document document) throws DocumentException {
        Paragraph header = new Paragraph("Hotel Booking Invoice", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(new Chunk("\n"));
    }

    private void addFooter(PdfWriter writer) {
        writer.setPageEvent(new PdfPageEventHelper() {
            @Override
            public void onEndPage(PdfWriter writer, Document document) {
                Phrase footer = new Phrase("Page " + writer.getPageNumber(), FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY));
                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, footer, 290, 30, 0);
            }
        });
    }

    private void addRows(PdfPTable table, BookingDto bookingDto) {
        // Create each row with a header (left column) and value (right column)
        addRow(table, "Guest Name", bookingDto.getGuestName());
        addRow(table, "Mobile", bookingDto.getMobile());
        addRow(table, "Email", bookingDto.getEmail());
    }

    private void addRow(PdfPTable table, String header, String value) {
        PdfPCell headerCell = new PdfPCell(new Phrase(header));
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.setBorderWidth(1);
        table.addCell(headerCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value));
        valueCell.setBorderWidth(1);
        table.addCell(valueCell);
    }
}
