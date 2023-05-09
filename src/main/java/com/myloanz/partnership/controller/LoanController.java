package com.myloanz.partnership.controller;

import com.myloanz.partnership.api.request.SubmitLoanRequest;
import com.myloanz.partnership.api.response.SubmitLoanResponse;
import com.myloanz.partnership.entity.Loan;
import com.myloanz.partnership.exception.LoanBusinessException;
import com.myloanz.partnership.exception.LoanOwnerException;
import com.myloanz.partnership.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoanController {

    private static final String HTTP_HEADER_PARTNER_SECRET = "partner-secret";

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping(value = "/api/loan", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SubmitLoanResponse> submitLoan(
            @RequestBody @Valid SubmitLoanRequest loanRequest,
            @RequestHeader(name = HTTP_HEADER_PARTNER_SECRET) String partnerSecret
    ) {
        var savedLoan = loanService.saveLoanToDatabase(loanRequest, partnerSecret);
        var submitLoanResponse = new SubmitLoanResponse();

        submitLoanResponse.setLoanId(savedLoan.getLoanId());
        submitLoanResponse.setCustomerName(savedLoan.getCustomerName());
        submitLoanResponse.setStatus(savedLoan.getStatus());

        return ResponseEntity.status(HttpStatus.CREATED).body(submitLoanResponse);
    }

    @GetMapping(value = "/api/loan", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Loan> findLoan(@RequestParam(name = "loan_id") String loanId,
                                  @RequestHeader(name = HTTP_HEADER_PARTNER_SECRET) String partnerSecret) {
        var existingLoan = loanService.findLoan(loanId, partnerSecret);

        if (existingLoan == null) {
            if (loanService.isLoanIdExists(loanId)) {
                throw new LoanOwnerException("You cannot access " + loanId);
            } else {
                throw new LoanBusinessException("Loan " + loanId + " does not exist");
            }
        }

        return ResponseEntity.ok().body(existingLoan);
    }

}
