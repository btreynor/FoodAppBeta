package com.btreynor.foodappbeta.service;

import com.btreynor.foodappbeta.model.Dish;
import com.btreynor.foodappbeta.model.RestaurantInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchEngineService {
    void addRestaurant(String word, String restaurantId);
    List<String> searchRestaurant(String word);
    void removeRestaurant(String word, String restaurantId);
    void eraseInfo(RestaurantInfo info, String restaurantId);
    void eraseDishes(List<Dish> dishes, String restaurantId);
    void updateInfo(RestaurantInfo info, String restaurantId);
}
