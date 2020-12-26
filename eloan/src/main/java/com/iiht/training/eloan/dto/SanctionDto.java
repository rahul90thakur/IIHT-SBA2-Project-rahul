package com.iiht.training.eloan.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SanctionDto {
	
	@NotNull(message="Loan Amount Sanctioned is mandatory")
	@DecimalMin(value="0.1", message="Loan Amount Sanctioned must be greater than 0")
	private Double loanAmountSanctioned;
	
	@NotNull(message="Loan Term is mandatory")
	@DecimalMin(value="0.1", message="Loan Term must be greater than 0")
	private Double termOfLoan;
	
	private String paymentStartDate;
	
	public Double getLoanAmountSanctioned() {
		return loanAmountSanctioned;
	}
	public void setLoanAmountSanctioned(Double loanAmountSanctioned) {
		this.loanAmountSanctioned = loanAmountSanctioned;
	}
	public Double getTermOfLoan() {
		return termOfLoan;
	}
	public void setTermOfLoan(Double termOfLoan) {
		this.termOfLoan = termOfLoan;
	}
	public String getPaymentStartDate() {
		return paymentStartDate;
	}
	public void setPaymentStartDate(String paymentStartDate) {
		this.paymentStartDate = paymentStartDate;
	}
	
	
}
