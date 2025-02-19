package org.example.restaurant.service;

import org.example.restaurant.dto.SimpleResponse;
import org.example.restaurant.dto.UserLIstForm;
import org.example.restaurant.dto.UserRequest;
import org.example.restaurant.dto.UserResponse;

public interface UserService {
    SimpleResponse saveUser(UserRequest userRequest);
    UserResponse getUserById(Long userId);
    SimpleResponse updateUser(UserRequest userRequest,Long userId);
    SimpleResponse deleteUser(Long userId);

    UserLIstForm getAllUsersToSelect();


    SimpleResponse chooseUserToRestaurant(Long restaurantId, Long userId);
    SimpleResponse addUserToRestaurant(UserRequest userRequest, Long restaurantId);

}
