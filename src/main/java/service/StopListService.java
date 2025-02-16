package service;

import dto.SimpleResponse;
import dto.StopListRequest;
import dto.StopListResponse;
import entities.StopList;

public interface StopListService {
    StopListResponse getStopList(Long stopListId);
    SimpleResponse updateStopList(StopListRequest stopList,Long stopListId);
    SimpleResponse deleteStopList(Long stopListId);

    SimpleResponse saveStopList(StopListRequest stopListRequest, String finishedFood);

}
