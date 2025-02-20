package org.example.restaurant.api;

import org.example.restaurant.dto.ChequeREESPONSE;
import org.example.restaurant.dto.ChequeRequest;
import org.example.restaurant.dto.SimpleResponse;
import org.example.restaurant.dto.TotalAmountOneDay;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.example.restaurant.service.ChequeService;

import java.time.LocalDate;

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


    @GetMapping("/total-amount-of-a-day/{date}")
    public TotalAmountOneDay getTotalAmountOfADay(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return chequeService.getTotalAmountOneDay(date);
    }


}
