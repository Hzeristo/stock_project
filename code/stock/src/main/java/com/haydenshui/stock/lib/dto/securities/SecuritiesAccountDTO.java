package com.haydenshui.stock.lib.dto.securities;

import lombok.*;
import java.time.LocalDate;
import java.util.List;



/**
 * Data Transfer Object (DTO) for the {@link SecuritiesAccount} entity.
 * <p>
 * This class serves as the Data Transfer Object (DTO) for transferring securities account data between layers,
 * particularly between the service and presentation layers. It encapsulates the fields of a {@link SecuritiesAccount} entity
 * and provides utility methods for converting between the entity and DTO forms.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecuritiesAccountDTO {

    /**
     * The unique identifier for the securities account.
     */
    private Long id;

    /**
     * The unique account number for the securities account.
     */
    private String accountNumber;

    /**
     * The registration date of the securities account.
     */
    private LocalDate registrationDate;

    /**
     * The name of the account holder or entity associated with the securities account.
     */
    private String name;

    /**
     * The identification card number of the account holder.
     */
    private String idCardNo;

    /**
     * The phone number associated with the account holder.
     */
    private String phone;

    /**
     * The address associated with the account holder.
     */
    private String address;

    /**
     * The status of the securities account.
     */
    private String status;

    /**
     * A list of capital account DTOs associated with the securities account.
     */
    private List<Long> capitalAccountIds;
    
}
