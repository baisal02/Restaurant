package org.example.restaurant.service;

import org.example.restaurant.dto.ChequeREESPONSE;
import org.example.restaurant.dto.ChequeRequest;
import org.example.restaurant.dto.SimpleResponse;
import org.example.restaurant.dto.TotalAmountOneDay;

import java.time.LocalDate;

public interface ChequeService {

    SimpleResponse createCheque(Long waiterId, ChequeRequest chequeRequest);
    ChequeREESPONSE getChequeInfo(Long chequeId);
    SimpleResponse updateCheque(ChequeRequest chequeRequest,Long chequeId);
    SimpleResponse deleteCheque(Long chequeId);

    TotalAmountOneDay getTotalAmountOneDay(LocalDate date);

}
