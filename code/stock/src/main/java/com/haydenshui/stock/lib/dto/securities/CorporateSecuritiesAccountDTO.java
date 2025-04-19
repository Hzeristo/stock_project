package com.haydenshui.stock.lib.dto.securities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class CorporateSecuritiesAccountDTO extends SecuritiesAccountDTO {
    
    
    /**
     * Name of the corporation that owns this securities account.
     */
    private String corporationName;

    /**
     * Unique registration number of the corporation.
     */
    private String registrationNumber;

    /**
     * Unique business license number of the corporation.
     */
    private String businessLicenseNumber;

    /**
     * Name of the authorized trader who manages the securities account on behalf of the corporation.
     */
    private String authorizedTraderName;

    /**
     * National identification card number of the authorized trader.
     */
    private String authorizedTraderIdCardNo;

    /**h
     * Contact phone number of the authorized trader.
     */
    private String authorizedTraderPhone;

    /**
     * Residential or business address of the authorized trader.
     */
    private String authorizedTraderAddress;

    public CorporateSecuritiesAccountDTO() {
        super();
    }
}
