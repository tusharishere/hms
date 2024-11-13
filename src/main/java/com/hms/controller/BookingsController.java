package com.hms.controller;

import com.hms.utils.PdfGeneratorService;
import com.itextpdf.text.DocumentException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("api/v1/generatepdf")
public class BookingsController {

    private PdfGeneratorService pdfGeneratorService;

    public BookingsController(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }
    @PostMapping("/create-booking")
    public String createBooking() throws DocumentException, IOException, URISyntaxException {
        pdfGeneratorService.generateBookingPdf("D:\\hms_Bookings\\Bookings.pdf");
        return "Booking PDF generated successfully";
    }
}
