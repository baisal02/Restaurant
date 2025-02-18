package api;

import dto.*;
import org.springframework.web.bind.annotation.*;
import service.RestaurantService;

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
