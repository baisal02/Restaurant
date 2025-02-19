package org.example.restaurant.service;

import org.example.restaurant.dto.SimpleResponse;
import org.example.restaurant.dto.StopListRequest;
import org.example.restaurant.dto.StopListResponse;

public interface StopListService {
    StopListResponse getStopList(Long stopListId);
    SimpleResponse updateStopList(StopListRequest stopList,Long stopListId);
    SimpleResponse deleteStopList(Long stopListId);

    SimpleResponse saveStopList(StopListRequest stopListRequest, Long finishedFoodId);

}
