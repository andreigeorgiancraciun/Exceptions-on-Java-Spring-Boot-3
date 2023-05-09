package com.myloanz.partnership.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myloanz.partnership.exception.Age;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Getter @Setter
public class SubmitLoanRequest {
    @Range(min = 100, max = 99999)
    private int principalAmount;
    @Range(min = 6, max = 24)
    private int termMonths;
    @Valid
    private Collateral collateral;
    @Valid
    private Customer customer;

    @Getter @Setter
    public static class Collateral {
        @Pattern(regexp = "TOYOTA|HONDA|BMW|FORD", flags = Pattern.Flag.CASE_INSENSITIVE)
        private String brand;

        @Size(max = 50)
        @NotBlank
        private String model;

        @Min(2015)
        private int manufacturingYear;
    }

    @Getter @Setter
    public static class Customer {
        @Pattern(regexp = "^[A-Za-z ]{3,50}$")
        private String name;

        @JsonFormat(pattern = "yyyy-MM-dd")
        @Age(min = 20, max = 50)
        private LocalDate birthDate;

        @Min(500)
        private int monthlyIncome;

        @Size(max = 50)
        @NotBlank
        private String idNumber;
    }
}
