package com.hms.controller;


import com.hms.entity.AppUser;
import com.hms.entity.Bookings;
import com.hms.entity.Property;
import com.hms.entity.Room;
import com.hms.exception.ResourceNotFoundException;
import com.hms.payload.BookingDto;
import com.hms.repository.BookingsRepository;
import com.hms.repository.RoomRepository;
import com.hms.utils.WhatsAppService;
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
import java.util.List;
import java.io.InputStream;

@RestController
@RequestMapping("api/v1/generatepdf")
public class BookingsController {

    private PdfGeneratorService pdfGeneratorService;
    private PropertyRepository propertyRepository;
    private BookingsRepository bookingsRepository;
    private RoomRepository roomRepository;
    private SmsService smsService;
    private WhatsAppService whatsAppService;
    private BucketService bucketService;

    public BookingsController(PdfGeneratorService pdfGeneratorService, PropertyRepository propertyRepository, BookingsRepository bookingsRepository, RoomRepository roomRepository, SmsService smsService, WhatsAppService whatsAppService, BucketService bucketService) {
        this.pdfGeneratorService = pdfGeneratorService;
        this.propertyRepository = propertyRepository;
        this.bookingsRepository = bookingsRepository;
        this.roomRepository = roomRepository;
        this.smsService = smsService;
        this.whatsAppService = whatsAppService;
        this.bucketService = bucketService;
    }
    @PostMapping("/create-booking/{propertyId}")
    public ResponseEntity<?> createBooking(
            @PathVariable long propertyId,
            @RequestParam String roomType,
            @AuthenticationPrincipal AppUser user,
            @RequestBody Bookings bookings) throws DocumentException, IOException{
        {
            Property property = propertyRepository.findById(propertyId).orElseThrow(
                ()-> new ResourceNotFoundException("Property not found")
            );
            List<Room> rooms = roomRepository.findRoomsByTypeAndProperty(bookings.getCheckInDate(), bookings.getCheckOutDate(), roomType, property);
            for (Room room : rooms) {
                if (room.getAvailableCount() == 0) {
                    return new ResponseEntity<>("No Rooms available for " + room.getDate(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

//            for(Room room : rooms) {
//                double totalPrice =  room.getNightlyPrice()*(double)(rooms.size()-1)*1.18;
//            }

            bookings.setProperty(property);
            bookings.setAppUser(user);
            Bookings savedBooking = bookingsRepository.save(bookings);
            if (savedBooking != null) {
                for (Room rm : rooms) {
                    rm.setAvailableCount(rm.getAvailableCount()-1);
                    roomRepository.save(rm);
                }
            }


            // Create BookingDto to pass data to PDF generator

            BookingDto bookingDto = new BookingDto();
            bookingDto.setEmail(savedBooking.getEmail());
            bookingDto.setGuestName(savedBooking.getGuestName());

            boolean pdfGenerated = pdfGeneratorService.generateBookingPdf("D:\\hms_Bookings\\Confirmation-Order "+savedBooking.getId()+".pdf",bookingDto);
            if (pdfGenerated) {
                MultipartFile file = convert("D://hms_Bookings//Confirmation-Order-" + savedBooking.getId() + ".pdf");
                String uploadedFileUrl = bucketService.uploadFile(file, "hotelmgmthms");
                smsService.sendSms(bookings.getMobile(), "Your booking is confirmed. Click for more information: " + uploadedFileUrl);
                whatsAppService.sendWhatsAppMessage(bookings.getMobile(), "Your booking is confirmed. Your booking id is: " + bookings.getId());
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
            public byte[] getBytes()  {
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

