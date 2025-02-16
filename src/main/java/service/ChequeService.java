package service;

import dto.ChequeREESPONSE;
import dto.ChequeRequest;
import dto.SimpleResponse;
import dto.TotalAmountOneDay;
import entities.Cheque;

import java.time.LocalDate;

public interface ChequeService {

    SimpleResponse createCheque(Long waiterId, ChequeRequest chequeRequest);
    ChequeREESPONSE getChequeInfo(Long chequeId);
    SimpleResponse updateCheque(ChequeRequest chequeRequest,Long chequeId);
    SimpleResponse deleteCheque(Long chequeId);

    TotalAmountOneDay getTotalAmountOneDay(LocalDate date);

}
