package service.impl;

import dto.*;
import entities.Cheque;
import entities.Restaurant;
import entities.User;
import entities.enums.Role;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import repositories.ChequeRepo;
import repositories.RestaurantRepo;
import repositories.UserRepo;
import service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ChequeRepo chequeRepo;
    private final RestaurantRepo restaurantRepo;


    public UserServiceImpl(UserRepo userRepo, ChequeRepo chequeRepo, RestaurantRepo restaurantRepo) {
        this.userRepo = userRepo;
        this.chequeRepo = chequeRepo;
        this.restaurantRepo = restaurantRepo;
    }

    @Override
    public SimpleResponse saveUser(UserRequest userRequest) {
        User user = new User();
        if (userRepo.existsByEmail(userRequest.getEmail())) {
            return new SimpleResponse("Email already exists", HttpStatus.BAD_REQUEST);
        }

        if(userRequest.getPassword().length() < 4) {
            return new SimpleResponse("Password must be at least more 4 characters long", HttpStatus.BAD_REQUEST);
        }

        if (Stream.of(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getBirthDate(),
                        userRequest.getEmail(), userRequest.getPassword(), userRequest.getPhoneNumber())
                .anyMatch(field -> field == null || (field instanceof String && ((String) field).isEmpty()))) {
            return new SimpleResponse("All fields are required and must be non-empty", HttpStatus.BAD_REQUEST);
        }

        int ageOfUserRequest = Period.between(userRequest.getBirthDate(), LocalDate.now()).getYears();

        if(userRequest.getRole() == Role.CHIEF&&ageOfUserRequest>=25&&ageOfUserRequest<=45&&userRequest.getExperience()>=2
          ||userRequest.getRole()==Role.WAITER&&ageOfUserRequest>=18&&ageOfUserRequest<=30&&userRequest.getExperience()>=1)
        {
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setBirthDate(userRequest.getBirthDate());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setRole(userRequest.getRole());
            user.setExperience(userRequest.getExperience());
            userRepo.save(user);
            return new SimpleResponse("The Request to join the restaurant has been sent and is being reviewed", HttpStatus.OK);
        }
        return new SimpleResponse("Something went wrong", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepo.findById(userId).
                orElseThrow(() -> new RuntimeException("User not found"));

        List<Cheque> cheques = user.getCheques();

        //preparing the result
        //------------------------------------------------------------------
        List<ChequeResponse> chequeResponses = new ArrayList<>();
        UserResponse userResponse = new UserResponse();
        if (cheques != null) {
            for (Cheque cheque : cheques) {
                chequeResponses.add(new ChequeResponse(
                        cheque.getId(),
                        cheque.getPriceAverage(),
                        cheque.getDate()
                ));
            }
        }

        userResponse.setChequeResponses(chequeResponses);

        userResponse.setId(userId);
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setBirthday(user.getBirthDate());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setRole(user.getRole());
        userResponse.setExperience(user.getExperience());
        return userResponse;

    }

    @Override
    public SimpleResponse updateUser(UserRequest userRequest, Long userId) {
        User user = userRepo.findById(userId).
                orElseThrow(()->new RuntimeException("User not found"));

        if(userRepo.existsByEmail(userRequest.getEmail())&& !user.getEmail().equals(userRequest.getEmail())) {
            return new SimpleResponse("Email already exists", HttpStatus.BAD_REQUEST);
        }
        if(userRequest.getPassword().length() < 4) {
            return new SimpleResponse("Password must be at least more 4 characters long", HttpStatus.BAD_REQUEST);
        }

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setBirthDate(userRequest.getBirthDate());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setExperience(userRequest.getExperience());

        userRepo.save(user);
        return new SimpleResponse("User has been successfully updated", HttpStatus.OK);
    }

    @Override
    public SimpleResponse deleteUser(Long userId) {
        User user = userRepo.findById(userId).
                orElseThrow(()->new RuntimeException("User not found"));

        user.getRestaurant().getUserList().remove(user);
        user.setRestaurant(null);

        //cheques are being deleted // no need in breaking relation between cheques and menuitems
        //because they have "ManyToMany" relation , that way hibernate will automatically break relation
       /*
       List<Cheque>cheques = user.getCheques();
        for (Cheque cheque : cheques) {
            cheque.getMenuItems().clear();
            chequeRepo.save(cheque);
        }
        */

        userRepo.save(user);
        return new SimpleResponse("User has been deleted", HttpStatus.OK);
    }

    @Override
    public UserLIstForm getAllUsersToSelect() {
        UserLIstForm userListForm = new UserLIstForm();
        List<User> users = userRepo.findUsersWithNoRestaurant();//all users with no restaurant from database
        List<UserResponse> userResponses = users.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getBirthDate(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getRole(),
                        user.getExperience()))
                .collect(Collectors.toList());
            userListForm.setUserResponses(userResponses);
        return userListForm;
    }

    @Transactional
    @Override
    public SimpleResponse chooseUserToRestaurant(Long restaurantId, Long userId) {
        User user = userRepo.findById(userId).
                orElseThrow(()->new RuntimeException("User not found"));

        Restaurant restaurant = restaurantRepo.findById(restaurantId).
                orElseThrow(()->new RuntimeException("Restaurant not found"));

        restaurant.getUserList().add(user);
        user.setRestaurant(restaurant);

        return new SimpleResponse("User has been added to the restaurant:   "+ restaurant.getName(), HttpStatus.OK);
    }

    @Override
    public SimpleResponse addUserToRestaurant(UserRequest userRequest, Long restaurantId) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId).
                orElseThrow(()->new RuntimeException("Restaurant not found"));

        User user = new User();
        if (userRepo.existsByEmail(userRequest.getEmail())) {
            return new SimpleResponse("Email already exists", HttpStatus.BAD_REQUEST);
        }

        if(userRequest.getPassword().length() < 4) {
            return new SimpleResponse("Password must be at least more 4 characters long", HttpStatus.BAD_REQUEST);
        }

        if (Stream.of(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getBirthDate(),
                        userRequest.getEmail(), userRequest.getPassword(), userRequest.getPhoneNumber())
                .anyMatch(field -> field == null || (field instanceof String && ((String) field).isEmpty()))) {
            return new SimpleResponse("All fields are required and must be non-empty", HttpStatus.BAD_REQUEST);
        }

        int ageOfUserRequest = Period.between(userRequest.getBirthDate(), LocalDate.now()).getYears();
        if(userRequest.getRole() == Role.CHIEF&&ageOfUserRequest>=25&&ageOfUserRequest<=45&&userRequest.getExperience()>=2
                ||userRequest.getRole()==Role.WAITER&&ageOfUserRequest>=18&&ageOfUserRequest<=30&&userRequest.getExperience()>=1)
        {
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setBirthDate(userRequest.getBirthDate());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setRole(userRequest.getRole());
            user.setExperience(userRequest.getExperience());
            user.setRestaurant(restaurant);
            userRepo.save(user);
            return new SimpleResponse("The User has been added to the Restaurant", HttpStatus.OK);
        }
        return new SimpleResponse("Something went wrong", HttpStatus.BAD_REQUEST);
    }
}
