package com.iiht.training.eloan.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoanDto {

	@NotNull(message="Loan Name is mandatory")
	@NotBlank(message="Loan Name cannot be null")
	@Size(min=3,max=100, message="Loan Name must be 3 to 100 chars in length")
	private String loanName;
	
	@NotNull(message="Loan Amount is mandatory")
	@DecimalMin(value="0.1")
	private Long loanAmount;
	
	private String loanApplicationDate;
	private String businessStructure;
	private String billingIndicator;
	private String taxIndicator;
	private UserDto userDto;
	
	public LoanDto() {
		
	}
	
	public LoanDto(String loanName, Double loanAmount, String loanApplicationDate, String businessStructure,
			String billingIndicator, String taxIndicator, UserDto userDto) {
		super();
		this.loanName = loanName;
		this.loanAmount = loanAmount;
		this.loanApplicationDate = loanApplicationDate;
		this.businessStructure = businessStructure;
		this.billingIndicator = billingIndicator;
		this.taxIndicator = taxIndicator;
		this.userDto = userDto;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Long long1) {
		this.loanAmount = long1;
	}
	public String getLoanApplicationDate() {
		return loanApplicationDate;
	}
	public void setLoanApplicationDate(String loanApplicationDate) {
		this.loanApplicationDate = loanApplicationDate;
	}
	public String getBusinessStructure() {
		return businessStructure;
	}
	public void setBusinessStructure(String businessStructure) {
		this.businessStructure = businessStructure;
	}
	public String getBillingIndicator() {
		return billingIndicator;
	}
	public void setBillingIndicator(String billingIndicator) {
		this.billingIndicator = billingIndicator;
	}
	public String getTaxIndicator() {
		return taxIndicator;
	}
	public void setTaxIndicator(String taxIndicator) {
		this.taxIndicator = taxIndicator;
	}
	public UserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public void setCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		
	}

	public Double getRemark() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
