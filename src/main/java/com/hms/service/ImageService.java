package com.hms.service;

import com.hms.entity.Images;
import com.hms.entity.Property;
import com.hms.repository.ImagesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private ImagesRepository imagesRepository;

    public ImageService(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    public Images addImages(Images image) {
        Images savedImages = imagesRepository.save(image);
        return savedImages;
    }

    public List<Images> ImagesByProperty(Property property){
        List<Images> images = imagesRepository.findByProperty(property);
        return images;
    }
}
