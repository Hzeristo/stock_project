package com.haydenshui.stock.lib.entity.account;

import jakarta.persistence.*;
import lombok.*;


/**
 * Entity class representing an individual securities account.
 * <p>
 * This class extends {@link SecuritiesAccount} to inherit common fields for a securities account,
 * and adds additional fields specific to individual account holders, such as gender, occupation, 
 * education, and employer.
 * </p>
 * <p>
 * The {@link IndividualSecuritiesAccount} class is mapped to the database table
 * "individual_securities_account".
 * </p>
 *
 * @see SecuritiesAccount
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "individual_securities_account")
public class IndividualSecuritiesAccount extends SecuritiesAccount {

    /**
     * Gender of the individual account holder.
     * This field is marked as non-nullable in the database.
     */
    @Column(name = "individual_gender", nullable = false)
    private String individualGender;

    /**
     * Occupation of the individual account holder.
     */
    @Column(name = "individual_occupation")
    private String individualOccupation;

    /**
     * Education level of the individual account holder.
     */
    @Column(name = "individual_education")
    private String individualEducation;

    /**
     * Employer of the individual account holder.
     */
    @Column(name = "individual_employer")
    private String individualEmployer;
}
