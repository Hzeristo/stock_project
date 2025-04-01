package com.haydenshui.stock.lib.entity.account;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a securities account.
 * <p>
 * This class serves as the base entity for all types of securities accounts, such as individual and corporate accounts.
 * It includes common fields like account number, password, registration date, personal information, and account status.
 * It also maintains a list of associated capital accounts.
 * </p>
 *
 * @see IndividualSecuritiesAccount
 * @see CorporateSecuritiesAccount
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "securities_account")
public class SecuritiesAccount {

    /**
     * The unique identifier for the securities account.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique account number for the securities account.
     * This field must be unique and is used for identifying the account.
     */
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    /**
     * The hashed password associated with the securities account.
     * The password is securely stored using a hash function.
     */
    @Column(name = "password", nullable = false)
    private String password;  

    /**
     * The date when the securities account was registered.
     * This field is used to track the registration date of the account.
     */
    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    /**
     * The name of the account holder or corporate entity associated with the account.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The unique identification card number of the account holder.
     * This could be a national ID card, or similar.
     */
    @Column(name = "id_card_no", nullable = false, unique = true)
    private String idCardNo;

    /**
     * The phone number associated with the account holder.
     * This field is used for contacting the account holder.
     */
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * The residential or business address associated with the account holder.
     * This is used for correspondence and account verification.
     */
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * The status of the securities account.
     * The account can be active, inactive, suspended, or any other status as per business requirements.
     */
    @Column(name = "status", nullable = false)
    private AccountStatus status;

    /**
     * A list of capital accounts id associated with this securities account.
     * This is a one-to-many relationship, and all changes to capital accounts are cascaded.
     */
    @ElementCollection
    @CollectionTable(
        name = "securities_account_capital_accounts", 
        joinColumns = @JoinColumn(name = "securities_account_id")
    )
    @Column(name = "capital_account_id")
    private List<Long> capitalAccountIds = new ArrayList<>();
    
}
