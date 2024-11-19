package com.hms.controller;


import com.hms.entity.AppUser;
import com.hms.entity.Bookings;
import com.hms.entity.Property;
import com.hms.payload.BookingDto;
import com.hms.repository.BookingsRepository;
import org.springframework.core.io.ByteArrayResource;
import com.hms.repository.PropertyRepository;
import com.hms.service.BucketService;
import com.hms.utils.PdfGeneratorService;
import com.hms.utils.SmsService;
import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.io.InputStream;

@RestController
@RequestMapping("api/v1/generatepdf")
public class BookingsController {

    private PdfGeneratorService pdfGeneratorService;
    private PropertyRepository propertyRepository;
    private BookingsRepository bookingsRepository;
    private SmsService smsService;
    private BucketService bucketService;

    public BookingsController(PdfGeneratorService pdfGeneratorService, PropertyRepository propertyRepository, BookingsRepository bookingsRepository, SmsService smsService, BucketService bucketService) {
        this.pdfGeneratorService = pdfGeneratorService;
        this.propertyRepository = propertyRepository;
        this.bookingsRepository = bookingsRepository;
        this.smsService = smsService;
        this.bucketService = bucketService;
    }
    @PostMapping("/create-booking/{propertyId}")
    public ResponseEntity<?> createBooking(
            @PathVariable long propertyId,
            @AuthenticationPrincipal AppUser user,
            @RequestBody Bookings bookings) throws DocumentException, IOException{
        {
            bookings.setAppUser(user);
            Optional<Property> byId = propertyRepository.findById(propertyId);
            if (byId.isEmpty()) {
                return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
            }
            Property property = byId.get();
            int propertyPrice = property.getNightlyPrice();
            Double totalNights = bookings.getTotalNights();

            // Calculate the total price for the booking
            Double totalPrice = propertyPrice * totalNights;
            bookings.setProperty(property);
            bookings.setTotalPrice( totalPrice);

            if (property.getAvailableRooms() <= 0) {
                return new ResponseEntity<>("No rooms available for the selected dates", HttpStatus.BAD_REQUEST);
            }
            property.setAvailableRooms(property.getAvailableRooms() - 1);
            Bookings savedBooking = bookingsRepository.save(bookings);
            propertyRepository.save(property);

            // Create BookingDto to pass data to PDF generator

            BookingDto bookingDto = new BookingDto();
            bookingDto.setId(savedBooking.getId());
            bookingDto.setEmail(savedBooking.getEmail());
            bookingDto.setGuestName(savedBooking.getGuestName());
            bookingDto.setMobile(savedBooking.getMobile());
            bookingDto.setRoomType(savedBooking.getRoomType());
            bookingDto.setTotalNights(savedBooking.getTotalNights());
            bookingDto.setTotalPrice(savedBooking.getTotalPrice());
            bookingDto.setPropertyName(property.getPropertyName());
            boolean pdfGenerated = pdfGeneratorService.generateBookingPdf("D:\\hms_Bookings\\Confirmation-Order "+savedBooking.getId()+".pdf",bookingDto);
            if (pdfGenerated) {
                MultipartFile file = convert("D://hms_Bookings//Confirmation-Order-" + savedBooking.getId() + ".pdf");
                String uploadedFileUrl = bucketService.uploadFile(file, "hotelmgmthms");
                smsService.sendSms(bookings.getMobile(), "Your booking is confirmed. Click for more information: " + uploadedFileUrl);
            } else {
                return new ResponseEntity<>("Error generating PDF", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(savedBooking,HttpStatus.OK);
        }
    }
    // Utility method to convert file to MultipartFile
    public static MultipartFile convert(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        ByteArrayResource resource = new ByteArrayResource(fileContent);

        return new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return fileContent.length == 0;
            }

            @Override
            public long getSize() {
                return fileContent.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return fileContent;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return resource.getInputStream();
            }

            @Override
            public void transferTo(File dest) throws IOException {
                Files.write(dest.toPath(), fileContent);
            }
        };
    }
}

