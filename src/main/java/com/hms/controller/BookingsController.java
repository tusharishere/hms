package com.hms.controller;

import com.hms.entity.Bookings;
import com.hms.entity.Property;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.BookingsRepository;
import com.hms.repository.PropertyRepository;
import com.hms.service.BookingService;
import com.hms.utils.PdfGeneratorService;
import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("api/v1/generatepdf")
public class BookingsController {

    private PdfGeneratorService pdfGeneratorService;
    private PropertyRepository propertyRepository;
    private BookingService bookingService;
    private BookingsRepository bookingsRepository;

    public BookingsController(PdfGeneratorService pdfGeneratorService, PropertyRepository propertyRepository, BookingService bookingService, BookingsRepository bookingsRepository) {
        this.pdfGeneratorService = pdfGeneratorService;
        this.propertyRepository = propertyRepository;
        this.bookingService = bookingService;
        this.bookingsRepository = bookingsRepository;
    }
    @PostMapping("/create-booking")
    public ResponseEntity<?> createBooking(
            @RequestParam long propertyId,
            @RequestBody Bookings bookings) throws DocumentException, IOException, URISyntaxException {
        {
            // Check room availability based on booking dates
            boolean isRoomAvailable = bookingService.checkRoomAvailability(
                                                                            propertyId,
                                                                            bookings.getRoomType() ,
                                                                            bookings.getCheckInDate(),
                                                                            bookings.getCheckOutDate());
            if (!isRoomAvailable) {
                return  new ResponseEntity<>("Room not available for the given dates", HttpStatus.NOT_FOUND);
            }
            Property property = propertyRepository.findById(propertyId).orElseThrow(()->new ResourceNotFoundException("Record not found"));
            Bookings savedBooking = bookingsRepository.save(bookings);
            pdfGeneratorService.generateBookingPdf("D:\\hms_Bookings\\Confirmation-Order "+savedBooking.getId()+".pdf", property);
            return new ResponseEntity<>("Booking PDF generated successfully",HttpStatus.OK);
        }
    }
}
