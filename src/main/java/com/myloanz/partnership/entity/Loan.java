package com.myloanz.partnership.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "loans")
@Getter @Setter
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID loanId;

    private int principalAmount;

    private int termMonths;

    @Column(length = 50)
    private String collateralBrand;

    @Column(length = 50)
    private String collateralModel;

    private int collateralManufacturingYear;

    @Column(length = 50)
    private String customerName;

    private LocalDate customerBirthDate;

    private int customerMonthlyIncome;

    @Column(length = 50)
    private String customerIdNumber;

    @Column(length = 50)
    private String createdBy;

    @Column(length = 50)
    private String status;

    private int loanInterest;

    private int monthlyInstallment;
}
