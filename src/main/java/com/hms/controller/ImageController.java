package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.entity.Images;
import com.hms.entity.Property;
import com.hms.payload.PropertyDto;
import com.hms.repository.ImagesRepository;
import com.hms.repository.PropertyRepository;
import com.hms.service.BucketService;
import com.hms.service.ImageService;
import com.hms.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private BucketService bucketService;
    private PropertyService propertyService;
    private ImageService imageService;

    public ImageController(BucketService bucketService, PropertyService propertyService, PropertyRepository propertyRepository,
                           ImagesRepository imagesRepository, ImageService imageService) {
        this.bucketService = bucketService;
        this.propertyService = propertyService;;
        this.imageService = imageService;
    }

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadPropertyPhotos(@RequestParam MultipartFile file,
                                        @PathVariable String bucketName,
                                        @PathVariable long propertyId,
                                        @AuthenticationPrincipal AppUser user
    ) {
        PropertyDto propertyDto = propertyService.getPropertyById(propertyId);
        Property property = propertyService.mapToEntity(propertyDto);
        String imageUrl = bucketService.uploadFile(file, bucketName);
        Images image = new Images();
        image.setUrl(imageUrl);
        image.setProperty(property);
        Images savedImg = imageService.addImages(image);
        return new ResponseEntity<>(savedImg, HttpStatus.OK);
    }
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Images>> getAllImages(
            @PathVariable long propertyId
    ){
        PropertyDto propertyDto = propertyService.getPropertyById(propertyId);
        Property property = propertyService.mapToEntity(propertyDto);
        List<Images> images = imageService.ImagesByProperty(property);
        return new ResponseEntity<>(images, HttpStatus.OK);

    }
}