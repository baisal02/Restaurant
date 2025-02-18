package api;

import dto.SimpleResponse;
import dto.StopListRequest;
import dto.StopListResponse;
import dto.SubCategoryRequest;
import org.springframework.web.bind.annotation.*;
import service.StopListService;

@RestController
@RequestMapping("/api/stoplists")
public class StopListApi {
private final StopListService stopListService;

    public StopListApi(StopListService stopListService) {
        this.stopListService = stopListService;
    }

    @PostMapping("/{foodName}")
    public SimpleResponse addStopList(@RequestBody StopListRequest stopListRequest,@PathVariable String foodName) {
        return stopListService.saveStopList(stopListRequest, foodName);
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
