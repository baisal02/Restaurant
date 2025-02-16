package service.impl;

import dto.*;
import entities.MenuItem;
import entities.StopList;
import jakarta.persistence.Entity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import repositories.MenuItemRepo;
import repositories.StopListRepo;
import service.StopListService;

import java.util.Optional;

@Service
public class StopListServiceImpl implements StopListService {
    private final StopListRepo stopListRepo;
    private final MenuItemRepo menuItemRepo;

    public StopListServiceImpl(StopListRepo stopListRepo, MenuItemRepo menuItemRepo) {
        this.stopListRepo = stopListRepo;
        this.menuItemRepo = menuItemRepo;
    }

    @Override
    public StopListResponse getStopList(Long stopListId) {
        StopList stopList = stopListRepo.findById(stopListId).
                orElseThrow(()->new RuntimeException("doesn't exist"));


        MenuItemResponse menuItemResponse = new MenuItemResponse();
        MenuItem menuItem = stopList.getMenuItem();
        menuItemResponse.setId(menuItem.getId());
        menuItemResponse.setName(menuItem.getName());
        menuItemResponse.setPrice(menuItem.getPrice());
        menuItemResponse.setDescription(menuItem.getDescription());
        menuItemResponse.setImage(menuItem.getImage());
        menuItemResponse.setVegetarian(menuItem.isVegetarian());


        StopListResponse stopListResponse = new StopListResponse();
        stopListResponse.setId(stopList.getId());
        stopListResponse.setReason(stopList.getReason());
        stopListResponse.setDate(stopList.getDate());
        stopListResponse.setMenuItemResponse(menuItemResponse);

        return stopListResponse;
    }

    @Override
    public SimpleResponse updateStopList(StopListRequest stopListRequest, Long stopListId) {
       StopList stopList = stopListRepo.findById(stopListId).
                orElseThrow(()->new RuntimeException("doesn't exist"));

       MenuItemRequest menuItemRequest = stopListRequest.getMenuItem();
       MenuItem menuItem = new MenuItem();

       menuItem.setDescription(menuItemRequest.getDescription());
       menuItem.setName(menuItemRequest.getName());
       menuItem.setPrice(menuItemRequest.getPrice());
       menuItem.setVegetarian(menuItemRequest.isVegetarian());
       menuItem.setImage(menuItemRequest.getImage());
       menuItem.setDescription(menuItemRequest.getDescription());

       stopList.setReason(stopListRequest.getReason());
       stopList.setDate(stopListRequest.getDate());
       //creating relation
       stopList.setMenuItem(menuItem);
       menuItem.setStoplist(stopList);

       stopListRepo.save(stopList);
       menuItemRepo.save(menuItem);
        return null;
    }

    @Override
    public SimpleResponse deleteStopList(Long stopListId) {
       StopList stopList = stopListRepo.findById(stopListId).
               orElseThrow(()->new RuntimeException("doesn't exist"));

       stopList.getMenuItem().setStoplist(null);
       stopList.setMenuItem(null);

        stopListRepo.deleteById(stopListId);
        return new SimpleResponse("deleted", HttpStatus.NO_CONTENT);
    }


    @Override
    public SimpleResponse saveStopList(StopListRequest stopListRequest,String finishedFood) {
        MenuItem menuItem = menuItemRepo.findMenuItemByName(finishedFood);

        StopList stopList = new StopList(stopListRequest.getReason(),stopListRequest.getDate());

        if(menuItem!=null&&menuItem.getStoplist()==null) {
            menuItem.setStoplist(stopList);
            stopList.setMenuItem(menuItem);
            stopListRepo.save(stopList);
            return new SimpleResponse("saved,the food is not for service now", HttpStatus.CREATED);
        }
        return new SimpleResponse("failed", HttpStatus.CONFLICT);

    }

}
