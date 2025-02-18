package api;

import dto.ChequeREESPONSE;
import dto.ChequeRequest;
import dto.SimpleResponse;
import dto.TotalAmountOneDay;
import org.springframework.web.bind.annotation.*;
import service.ChequeService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/cheques")
public class ChequApi {
    private final ChequeService chequeService;

    public ChequApi(ChequeService chequeService) {
        this.chequeService = chequeService;
    }

    @PostMapping("/{waiterId}")
    public SimpleResponse addCheque(@RequestBody ChequeRequest chequeRequest,@PathVariable Long waiterId) {
        return chequeService.createCheque(waiterId,chequeRequest);
    }

    @GetMapping("/{id}")
    public ChequeREESPONSE getCheque(@PathVariable Long id) {
       return chequeService.getChequeInfo(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateCheque(@PathVariable Long id,@RequestBody ChequeRequest chequeRequest) {
        return chequeService.updateCheque(chequeRequest,id);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteCheque(@PathVariable Long id) {
        return chequeService.deleteCheque(id);
    }

    @GetMapping("/total-amount-of-a-day")
    public TotalAmountOneDay getTotalAmountOfADay(@RequestParam LocalDate date) {
        return chequeService.getTotalAmountOneDay(date);
    }

}
