package com.haydenshui.stock.lib.entity.account;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED) 
@Table(name = "securities_account")
public abstract class SecuritiesAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "id_card_no", nullable = false, unique = true)
    private String idCardNo;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "status", nullable = false)
    private AccountStatus status;

    @OneToMany(mappedBy = "securitiesAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CapitalAccount> capitalAccounts = new ArrayList<>();
}
