package com.btreynor.foodappbeta.service;

import com.btreynor.foodappbeta.model.Comment;
import com.btreynor.foodappbeta.model.Dish;
import com.btreynor.foodappbeta.model.Restaurant;
import com.btreynor.foodappbeta.model.RestaurantInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {

    int addDish(String id, Dish dish);

    int removeDish(String id, Dish dish);

    List<Dish> getAllDishes(String id);

    RestaurantInfo getInformation(String id);

    int updateInfo(String id, RestaurantInfo info);
}
