package org.example.restaurant.api;

import org.example.restaurant.dto.SimpleResponse;
import org.example.restaurant.dto.StopListRequest;
import org.example.restaurant.dto.StopListResponse;
import org.springframework.web.bind.annotation.*;
import org.example.restaurant.service.StopListService;

@RestController
@RequestMapping("/api/stoplists")
public class StopListApi {
private final StopListService stopListService;

    public StopListApi(StopListService stopListService) {
        this.stopListService = stopListService;
    }

    @PostMapping("/{foodId}")
    public SimpleResponse addStopList(@RequestBody StopListRequest stopListRequest,@PathVariable Long foodId) {
        return stopListService.saveStopList(stopListRequest, foodId);
    }

    @GetMapping("/{id}")
    public StopListResponse getStopList(@PathVariable Long id) {
        return stopListService.getStopList(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateStopList(@RequestBody StopListRequest stopListRequest,@PathVariable Long id) {
       return stopListService.updateStopList(stopListRequest, id);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteStopList(@PathVariable Long id) {
        return stopListService.deleteStopList(id);
    }
}
