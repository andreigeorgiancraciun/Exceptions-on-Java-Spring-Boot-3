package com.myloanz.partnership.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SubmitLoanResponse {

    private String customerName;

    private UUID loanId;

    private String status;
}
