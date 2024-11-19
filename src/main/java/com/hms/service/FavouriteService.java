package com.hms.service;

import com.hms.entity.AppUser;
import com.hms.entity.Favourite;
import com.hms.entity.Property;
import com.hms.repository.FavouriteRepository;
import com.hms.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final PropertyRepository propertyRepository;

    public FavouriteService(FavouriteRepository favouriteRepository, PropertyRepository propertyRepository) {
        this.favouriteRepository = favouriteRepository;
        this.propertyRepository = propertyRepository;
    }

    public Favourite addFavourite(AppUser user, long propertyId) {
        Optional<Property> propertyContainer = propertyRepository.findById(propertyId);
        if (propertyContainer.isEmpty()) {
            throw new RuntimeException("Property not found with id: " + propertyId);
        }

        Favourite favourite = new Favourite();
        favourite.setUser(user);
        favourite.setProperty(propertyContainer.get());
        favourite.setIsFavourite(true);
        return favouriteRepository.save(favourite);

    }

    public List<Favourite> getAllFavourites(AppUser user) {
        return favouriteRepository.fetchByUsername(user);
    }
}
