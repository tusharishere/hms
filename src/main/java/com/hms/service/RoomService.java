package com.hms.service;


import com.hms.entity.Room;
import com.hms.payload.RoomDto;
import com.hms.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private ModelMapper modelMapper;
    private RoomRepository roomsRepository;

    public RoomService(ModelMapper modelMapper, RoomRepository roomsRepository) {
        this.modelMapper = modelMapper;
        this.roomsRepository = roomsRepository;
    }

    public RoomDto addRooms(RoomDto dto) {
        Room rooms = mapToEntity(dto);
        Room save = roomsRepository.save(rooms);
        return mapToDto(save);
    }

    private RoomDto mapToDto(Room save) {
        RoomDto roomsDto = modelMapper.map(save, RoomDto.class);
        return roomsDto;
    }

    private Room mapToEntity(RoomDto dto) {
        Room rooms = modelMapper.map(dto, Room.class);
        return rooms;
    }
}

