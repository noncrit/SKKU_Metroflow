package com.metroflow.model.dao;

import com.metroflow.repository.FavoriteListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class favoriteDAO {

    private final FavoriteListRepository favoriteListRepository;

    @Autowired
    public favoriteDAO(FavoriteListRepository favoriteListRepository) {
        this.favoriteListRepository = favoriteListRepository;
    }

}
