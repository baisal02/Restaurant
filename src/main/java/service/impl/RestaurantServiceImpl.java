package service.impl;

import dto.*;
import entities.MenuItem;
import entities.Restaurant;
import entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import repositories.RestaurantRepo;
import repositories.UserRepo;
import service.RestaurantService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepo restaurantRepo;
    private final UserRepo userRepo;

    public RestaurantServiceImpl(RestaurantRepo restaurantRepo, UserRepo userRepo) {
        this.restaurantRepo = restaurantRepo;
        this.userRepo = userRepo;
    }

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        if(restaurantRepo.existsByName(restaurantRequest.getName())) {
            return new SimpleResponse("such restaurant already exists", HttpStatus.CONFLICT);
        }
             if(restaurantRequest.getName()!=null &&
                restaurantRequest.getLocation()!=null &&
                restaurantRequest.getRestaurantType()!=null){

        Restaurant restaurantToBeSaved = new Restaurant();
        restaurantToBeSaved.setName(restaurantRequest.getName());
        restaurantToBeSaved.setLocation(restaurantRequest.getLocation());
        restaurantToBeSaved.setRestType(restaurantRequest.getRestaurantType());
        restaurantRepo.save(restaurantToBeSaved);
        return new SimpleResponse("Restaurant has been saved", HttpStatus.CREATED);
        }
        return new SimpleResponse("such restaurant can not be created", HttpStatus.CONFLICT);
    }


    @Override
    public RestaurantInfoResponse getRestaurantInfoById(Long restaurantId) {
        //to avoid nullpointer
        Restaurant restaurant = restaurantRepo.findById(restaurantId).
                orElseThrow(()->new RuntimeException("restaurant not found"));
        List<User> users = restaurant.getUserList() != null ? restaurant.getUserList() : new ArrayList<>();
        List<MenuItem> menuItems = restaurant.getMenuItemList() != null ? restaurant.getMenuItemList() : new ArrayList<>();
        //-----------------------------------------------------------------------------------

        //number of employees,users and menuitems (from database) to be setted into our response
        int numberofEmps = restaurant.getUserList().size();
        List<MenuItemResponse>menuItemResponses = new ArrayList<>();
        List<UserResponse>userResponses = new ArrayList<>();

        //filling the lists------------
        for(User user: users) {
            userResponses.add(new UserResponse(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getRole()
                    ));
        }

        for(MenuItem menuItem:menuItems) {
            menuItemResponses.add(new MenuItemResponse(
                    menuItem.getId(),
                    menuItem.getName(),
                    menuItem.getImage(),
                    menuItem.getPrice(),
                    menuItem.getDescription(),
                    menuItem.isVegetarian()
            ));
        }
        //-------------
        //preparing response
        RestaurantInfoResponse restaurantInfoResponse = new RestaurantInfoResponse();
        restaurantInfoResponse.setId(restaurant.getId());
        restaurantInfoResponse.setNumberOfEmployees(numberofEmps);
        restaurantInfoResponse.setLocation(restaurant.getLocation());
        restaurantInfoResponse.setUserResponses(userResponses);
        restaurantInfoResponse.setMenuItemResponses(menuItemResponses);

        return restaurantInfoResponse;
    }


    @Override
    public SimpleResponse updateRestaurant(RestaurantRequest restaurantRequest,Long restaurantId) {
        if(restaurantRequest==null) {
            return new SimpleResponse("Cannot update", HttpStatus.CONFLICT);
        }
        Restaurant restaurant = restaurantRepo.findById(restaurantId).
                orElseThrow(()->new RuntimeException("restaurant not found"));
        restaurant.setName(restaurantRequest.getName());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setRestType(restaurantRequest.getRestaurantType());
        restaurantRepo.save(restaurant);
        return new SimpleResponse("Restaurant has been updated", HttpStatus.OK);
    }

    @Override
    public SimpleResponse deleteRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepo.
                findById(restaurantId).orElseThrow(()->new RuntimeException("not found"));

        restaurantRepo.delete(restaurant);
        return new SimpleResponse("Restaurant has been deleted", HttpStatus.OK);
    }
}
