package service;

import dto.RestaurantInfoResponse;
import dto.RestaurantRequest;
import dto.SimpleResponse;
import entities.Restaurant;

public interface RestaurantService {
    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);
    RestaurantInfoResponse getRestaurantInfoById(Long restaurantId);
    SimpleResponse updateRestaurant(RestaurantRequest restaurantRequest,Long restaurantId);
    SimpleResponse deleteRestaurant(Long restaurantId);

}
