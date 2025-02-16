package dto;

import java.math.BigDecimal;

public class ChequeREESPONSE {
    private String waiterName;
    private ChequeResponse chequeResponse;
    private BigDecimal service;
    private Long GrandTotal;

    public Long getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(Long grandTotal) {
        GrandTotal = grandTotal;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public ChequeResponse getChequeResponse() {
        return chequeResponse;
    }

    public void setChequeResponse(ChequeResponse chequeResponse) {
        this.chequeResponse = chequeResponse;
    }

    public BigDecimal getService() {
        return service;
    }

    public void setService(BigDecimal service) {
        this.service = service;
    }
}
