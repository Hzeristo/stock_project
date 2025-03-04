package com.haydenshui.stock.lib.entity.account;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "corporate_securities_account")
public class CorporateSecuritiesAccount extends SecuritiesAccount {

    @Column(name = "corporation_name", nullable = false)
    private String corporationName;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @Column(name = "business_license_number", nullable = false, unique = true)
    private String businessLicenseNumber;

    @Column(name = "authorized_trader_name", nullable = false)
    private String authorizedTraderName;

    @Column(name = "authorized_trader_id_card_no", nullable = false, unique = true)
    private String authorizedTraderIdCardNo;

    @Column(name = "authorized_trader_phone", nullable = false)
    private String authorizedTraderPhone;

    @Column(name = "authorized_trader_address", nullable = false)
    private String authorizedTraderAddress;
}
