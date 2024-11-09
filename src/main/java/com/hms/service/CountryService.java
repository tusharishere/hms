package com.hms.service;

import com.hms.entity.Country;
import com.hms.exception.ResourceNotFoundException;
import com.hms.payload.CountryDto;
import com.hms.repository.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private CountryRepository countryRepository;
    private ModelMapper modelMapper;

    public CountryService(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }


    public CountryDto createCountry(CountryDto dto) {
        Country country = mapToEntity(dto);
        Country save = countryRepository.save(country);
        CountryDto countryDto = mapToDto(save);
        return countryDto;
    }

    public List<CountryDto> findAllCountries() {
        List<Country> countries = countryRepository.findAll();
        List<CountryDto> countryDtos = countries.stream().map(r->mapToDto(r)).collect(Collectors.toList());
        return countryDtos;
    }

    @Transactional
    public void delete(Long id) {
        countryRepository.deleteById(id);
    }

    public CountryDto update(Long id, CountryDto countryDto) {
        Country coun = countryRepository.findById(id)
                .orElseThrow(() ->new EntityNotFoundException("Country not found with id"));
        coun.setCountryName(countryDto.getCountryName());
        Country updatedCountry = countryRepository.save(coun);
        return mapToDto(updatedCountry);
    }

    public CountryDto getCountryById(Long id) {
        Country country = countryRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Record not found")
        );
        CountryDto countryDto = mapToDto(country);
        return countryDto;
    }


    private CountryDto mapToDto(Country save) {
        CountryDto countryDto = modelMapper.map(save, CountryDto.class);
        return countryDto;
    }

    private Country mapToEntity(CountryDto dto) {
        Country country = modelMapper.map(dto, Country.class);
        return country;
    }

}
