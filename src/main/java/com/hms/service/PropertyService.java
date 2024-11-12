package com.hms.service;

import com.hms.entity.Property;
import com.hms.exception.ResourceNotFoundException;
import com.hms.payload.PropertyDto;
import com.hms.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {
    private PropertyRepository propertyRepository;
    private ModelMapper modelMapper;

    public PropertyService(PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }

    public PropertyDto createProperty(PropertyDto dto) {
        Property property = mapToEntity(dto);
        Property save = propertyRepository.save(property);
        PropertyDto propertyDto = mapToDto(save);
        return propertyDto;
    }

    public List<PropertyDto> findAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        List<PropertyDto> propertyDtos = properties.stream().map(r->mapToDto(r)).collect(Collectors.toList());
        return propertyDtos;
    }

    @Transactional
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    public Property updateProperty(Long id, Property property) {
        Property prop = propertyRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Record not found")
        );
        prop.setPropertyName(property.getPropertyName());
        Property save = propertyRepository.save(prop);
        return save;
    }

    public PropertyDto getPropertyById(Long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                ()->new ResourceNotFoundException("Record not found")
        );
        PropertyDto propertyDto = mapToDto(property);
        return propertyDto;
    }

    public List<Property> searchAllHotels(String name) {
        List<Property> properties = propertyRepository.searchHotels(name);
        return properties;
    }

    public PropertyDto mapToDto(Property save) {
        PropertyDto propertyDto = modelMapper.map(save, PropertyDto.class);
        return propertyDto;
    }

    public Property mapToEntity(PropertyDto dto) {
        Property property = modelMapper.map(dto, Property.class);
        return property;
    }
}
