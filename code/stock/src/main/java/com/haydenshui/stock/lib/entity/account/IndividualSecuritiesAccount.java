package com.haydenshui.stock.lib.entity.account;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "individual_securities_account")
public class IndividualSecuritiesAccount extends SecuritiesAccount {

    @Column(name = "individual_gender", nullable = false)
    private String individualGender;

    @Column(name = "individual_occupation")
    private String individualOccupation;

    @Column(name = "individual_education")
    private String individualEducation;

    @Column(name = "individual_employer")
    private String individualEmployer;
}
