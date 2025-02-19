package org.example.restaurant.service;

import org.example.restaurant.dto.RestaurantInfoResponse;
import org.example.restaurant.dto.RestaurantRequest;
import org.example.restaurant.dto.SimpleResponse;

public interface RestaurantService {
    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);
    RestaurantInfoResponse getRestaurantInfoById(Long restaurantId);
    SimpleResponse updateRestaurant(RestaurantRequest restaurantRequest,Long restaurantId);
    SimpleResponse deleteRestaurant(Long restaurantId);

}
