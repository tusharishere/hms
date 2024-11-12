package com.hms.service;

import com.hms.entity.City;
import com.hms.exception.ResourceNotFoundException;
import com.hms.payload.CityDto;
import com.hms.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CityService {

    private CityRepository cityRepository;
    private ModelMapper modelMapper;

    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }


    public CityDto createCity(CityDto dto) {
            City city = mapToEntity(dto);
            City save = cityRepository.save(city);
            CityDto cityDto = mapToDto(save);
            return cityDto;
    }


    public List<CityDto> findAllCities() {
        List<City> cities = cityRepository.findAll();
        List<CityDto> cityDtos = cities.stream().map(r->mapToDto(r)).collect(Collectors.toList());
        return cityDtos;
    }


    public CityDto updateCity(Long id, CityDto cityDto) {
        City city = cityRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Record not found"));
        city.setCityName(cityDto.getCityName());
        City updatedCity = cityRepository.save(city);
        mapToDto(updatedCity);
        return mapToDto(updatedCity);
    }


    public CityDto getCityById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Record not found"));
        CityDto cityDto = mapToDto(city);
        return cityDto;
    }

    @Transactional
    public void delete(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Record not found"));
        cityRepository.deleteById(id);
    }


    private CityDto mapToDto(City city) {
        CityDto cityDto = modelMapper.map(city,CityDto.class);
        return cityDto;
    }

    private City mapToEntity(CityDto dto) {
        City city = modelMapper.map(dto, City.class);
        return city;
    }

}
