package api;

import dto.SimpleResponse;
import dto.UserLIstForm;
import dto.UserRequest;
import dto.UserResponse;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserApi {
    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public SimpleResponse addUser(@RequestBody UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateUser(@RequestBody UserRequest userRequest, @PathVariable Long id) {
        return userService.updateUser(userRequest, id);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    //to see all users with no restaurant (to choose)
    @GetMapping("/all-users-form")
    public UserLIstForm getAllUsers() {
        return userService.getAllUsersToSelect();
    }

    @PostMapping("/select-user/{userId}/restaurant/{restaurantId}")
    public SimpleResponse selectUserToRestaurant(@PathVariable Long restaurantId, @PathVariable Long userId) {
        return userService.chooseUserToRestaurant(restaurantId, userId);
    }

    @PostMapping("/{restaurandId}")
    public SimpleResponse addUserToRestaurant(@PathVariable Long restaurantId,@RequestBody UserRequest userRequest) {
        return userService.addUserToRestaurant(userRequest, restaurantId);
    }

}
