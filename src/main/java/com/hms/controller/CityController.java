package com.hms.controller;

import com.hms.entity.City;
import com.hms.payload.CityDto;
import com.hms.repository.CityRepository;
import com.hms.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/city")
public class CityController {

    private CityService cityService;
    private CityRepository cityRepository;

    public CityController(CityService cityService, CityRepository cityRepository) {
        this.cityService = cityService;
        this.cityRepository = cityRepository;
    }

    @PostMapping("/addCity")
    public ResponseEntity<?> createCity(
            @RequestBody CityDto dto
    ){
        Optional<City> opCityName = cityRepository.findByCityName(dto.getCityName());
        if(opCityName.isPresent()){
            return new ResponseEntity<>("City already present", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CityDto cityDto = cityService.createCity(dto);
        return new ResponseEntity<>(cityDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities(){
        List<CityDto> cityDtos = cityService.findAllCities();
        return new ResponseEntity<>(cityDtos,HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CityDto> updateCity(
            @PathVariable Long id,
            @RequestBody CityDto cityDto
    ){
        CityDto updatedCity = cityService.updateCity(id, cityDto);
        return new ResponseEntity<>(updatedCity,HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<String> deleteCity(
            @RequestParam Long id
    ){
        cityService.delete(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(
            @PathVariable Long id
    ){
        CityDto cityDto = cityService.getCityById(id);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

}
