package service;

import dto.*;
import entities.User;

public interface UserService {
    SimpleResponse saveUser(UserRequest userRequest);
    UserResponse getUserById(Long userId);
    SimpleResponse updateUser(UserRequest userRequest,Long userId);
    SimpleResponse deleteUser(Long userId);

    UserLIstForm getAllUsersToSelect();
    SimpleResponse chooseUserToRestaurant(Long restaurantId, Long userId);
    SimpleResponse addUserToRestaurant(UserRequest userRequest, Long restaurantId);

}
