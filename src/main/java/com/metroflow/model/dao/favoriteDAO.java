package com.metroflow.model.dao;

import com.metroflow.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class favoriteDAO {

    private final FavoriteRepository favoriteRepository;

    @Autowired
    public favoriteDAO(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }
}
