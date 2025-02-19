package org.example.restaurant.api;

import org.example.restaurant.dto.RestaurantInfoResponse;
import org.example.restaurant.dto.RestaurantRequest;
import org.example.restaurant.dto.SimpleResponse;
import org.springframework.web.bind.annotation.*;
import org.example.restaurant.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantApi {
    private final RestaurantService restaurantService;

    public RestaurantApi(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public SimpleResponse createRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        return restaurantService.saveRestaurant(restaurantRequest);
    }

    @GetMapping("/{id}")
    public RestaurantInfoResponse getRestaurant(@PathVariable Long id) {
       return restaurantService.getRestaurantInfoById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateRestaurant(@RequestBody RestaurantRequest restaurantRequest, @PathVariable Long id) {
        return restaurantService.updateRestaurant(restaurantRequest, id);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteRestaurant(@PathVariable Long id) {
        return restaurantService.deleteRestaurant(id);
    }
}
