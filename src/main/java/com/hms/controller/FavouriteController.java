package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.entity.Favourite;
import com.hms.service.FavouriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fav")
public class FavouriteController {

    private final FavouriteService favouriteService;


    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;

    }

    @PostMapping("/addFavourite")
    public ResponseEntity<Favourite> addFavourite(@AuthenticationPrincipal AppUser user,
                                                  @RequestParam long propertyId) {
        Favourite favourite = favouriteService.addFavourite(user, propertyId);
        return new ResponseEntity<>(favourite, HttpStatus.CREATED);

    }


    @GetMapping("/getFavourites")
    public ResponseEntity<List<Favourite>> getAllFavourites(@AuthenticationPrincipal AppUser user) {
        List<Favourite> favourites = favouriteService.getAllFavourites(user);
        return new ResponseEntity<>(favourites, HttpStatus.OK);
    }
}