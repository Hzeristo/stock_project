package com.haydenshui.stock.lib.entity.account;


import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class representing a corporate securities account.
 * <p>
 * This class extends {@link SecuritiesAccount} to include additional attributes specific
 * to corporate entities, such as company registration details and an authorized trader.
 * </p>
 *
 * <p>
 * A corporate securities account is used by businesses for stock trading, asset management,
 * and other financial activities in the securities market.
 * </p>
 *
 * @author YourName
 * @version 1.0
 * @since 2025-03-06
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "corporate_securities_account")
public class CorporateSecuritiesAccount extends SecuritiesAccount {

    /**
     * Name of the corporation that owns this securities account.
     */
    @Column(name = "corporation_name", nullable = false)
    private String corporationName;

    /**
     * Unique registration number of the corporation.
     */
    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    /**
     * Unique business license number of the corporation.
     */
    @Column(name = "business_license_number", nullable = false, unique = true)
    private String businessLicenseNumber;

    /**
     * Name of the authorized trader who manages the securities account on behalf of the corporation.
     */
    @Column(name = "authorized_trader_name", nullable = false)
    private String authorizedTraderName;

    /**
     * National identification card number of the authorized trader.
     */
    @Column(name = "authorized_trader_id_card_no", nullable = false, unique = true)
    private String authorizedTraderIdCardNo;

    /**
     * Contact phone number of the authorized trader.
     */
    @Column(name = "authorized_trader_phone", nullable = false)
    private String authorizedTraderPhone;

    /**
     * Residential or business address of the authorized trader.
     */
    @Column(name = "authorized_trader_address", nullable = false)
    private String authorizedTraderAddress;

}
