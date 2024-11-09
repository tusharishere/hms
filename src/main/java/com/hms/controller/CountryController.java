package com.hms.controller;

import com.hms.entity.Country;
import com.hms.payload.CountryDto;
import com.hms.repository.CountryRepository;
import com.hms.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    private CountryService countryService;
    private CountryRepository countryRepository;


    public CountryController(CountryService countryService, CountryRepository countryRepository) {
        this.countryService = countryService;
        this.countryRepository = countryRepository;
    }

    @PostMapping("/addCountry")
    public ResponseEntity<?> createCountry(
            @RequestBody CountryDto dto
    ){
        Optional<Country> opCountryName = countryRepository.findByCountryName(dto.getCountryName());
        if(opCountryName.isPresent()){
            return new ResponseEntity<>("Country already present", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CountryDto countryDto = countryService.createCountry(dto);
        return new ResponseEntity<>(countryDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries(){
        List<CountryDto> countryDtos = countryService.findAllCountries();
        return new ResponseEntity<>(countryDtos,HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<String> deleteCountry(
            @RequestParam Long id
    ){
        countryService.delete(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryDto> updateCountry(
            @PathVariable Long id,
            @RequestBody CountryDto countryDto
    ){
        CountryDto updatedCoun = countryService.update(id, countryDto);
        return new ResponseEntity<>(updatedCoun,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCOuntryById(
            @PathVariable Long id
    ){
        CountryDto countryDto = countryService.getCountryById(id);
        return new ResponseEntity<>(countryDto, HttpStatus.OK);
    }

}
