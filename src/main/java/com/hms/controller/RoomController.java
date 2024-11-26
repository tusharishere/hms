package com.hms.controller;


import com.hms.entity.Property;
import com.hms.payload.RoomDto;
import com.hms.repository.PropertyRepository;
import com.hms.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    private RoomService roomService;
    private PropertyRepository propertyRepository;

    public RoomController(RoomService roomService, PropertyRepository propertyRepository) {
        this.roomService = roomService;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addRoom")
    public ResponseEntity<RoomDto> addRooms(
            @RequestParam long propertyId,
            @RequestBody RoomDto dto
    ){
        Property property = propertyRepository.findById(propertyId).get();
        dto.setProperty(property);
        RoomDto roomsDto = roomService.addRooms(dto);
        return new ResponseEntity<>(roomsDto, HttpStatus.CREATED);
    }

}
