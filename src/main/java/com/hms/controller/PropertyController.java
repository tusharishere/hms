package com.hms.controller;

import com.hms.entity.City;
import com.hms.entity.Country;
import com.hms.entity.Property;
import com.hms.exception.ResourceNotFoundException;
import com.hms.payload.PropertyDto;
import com.hms.repository.CityRepository;
import com.hms.repository.CountryRepository;
import com.hms.repository.PropertyRepository;
import com.hms.service.PropertyService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/property")
public class PropertyController {
    private PropertyService propertyService;
    private PropertyRepository propertyRepository;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;

    public PropertyController(PropertyService propertyService, PropertyRepository propertyRepository, CityRepository cityRepository, CountryRepository countryRepository) {
        this.propertyService = propertyService;
        this.propertyRepository = propertyRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @PostMapping("/addProperty")
    public ResponseEntity<?> createProperty(
            @RequestBody PropertyDto dto,
            @RequestParam Long cityId,
            @RequestParam Long countryId
    ){
        City c = cityRepository.findById(cityId).orElseThrow(() -> new ResourceNotFoundException("City not found with this id"));
        Country coun = countryRepository.findById(countryId).orElseThrow(() -> new ResourceNotFoundException("Country not found with this id "));
        dto.setCity(c);
        dto.setCountry(coun);
        Optional<Property> opPropertyName = propertyRepository.findByPropertyName(dto.getPropertyName());
        if(opPropertyName.isPresent()){
            return new ResponseEntity<>("Property already present", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PropertyDto propertyDto = propertyService.createProperty(dto);
        return new ResponseEntity<>(propertyDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PropertyDto>> getAllProperties(){
        List<PropertyDto> propertyDtos = propertyService.findAllProperties();
        return new ResponseEntity<>(propertyDtos,HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<String> deleteProperty(
            @RequestParam Long id
    ){
        propertyService.deleteProperty(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(
            @PathVariable Long id,
            @RequestBody Property property
    ){
        Property prop = propertyService.updateProperty(id,property);
        return new ResponseEntity<>(prop,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDto> getPropertyById(
            @PathVariable Long id
    ){
        PropertyDto propertyDto = propertyService.getPropertyById(id);
        return new ResponseEntity<>(propertyDto, HttpStatus.OK);
    }

    @GetMapping("/search-hotels")
    public List<Property> searchHotels(
            @RequestParam String name
    ){
        List<Property> properties = propertyService.searchAllHotels(name);
        return new ResponseEntity<>(properties,HttpStatus.OK).getBody();
    }
}
