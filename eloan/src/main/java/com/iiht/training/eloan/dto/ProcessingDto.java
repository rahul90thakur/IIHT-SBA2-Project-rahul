package com.iiht.training.eloan.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProcessingDto {
	
	@NotNull(message="Acres of Land is mandatory")
	@DecimalMin(value="0.1", message="Acres of Land must be greater than 0")
	private Double acresOfLand;
	
	@NotNull(message="Land Value is mandatory")
	@DecimalMin(value="0.1", message="Land Value must be greater than 0")
	private Double landValue;
	
	private String appraisedBy;
	private String valuationDate;
	
	@NotNull(message="Property Address is mandatory")
	@NotBlank(message="Property Address cannot be null")
	@Size(min=3,max=150, message="Property Address must be 3 to 150 chars in length")
	private String addressOfProperty;
	
	@NotNull(message="Suggested Loan Amount is mandatory")
	@DecimalMin(value="0.1", message="Suggested Loan Amount must be greater than 0")
	private Double suggestedAmountOfLoan;
	
	public Double getAcresOfLand() {
		return acresOfLand;
	}
	public void setAcresOfLand(Double acresOfLand) {
		this.acresOfLand = acresOfLand;
	}
	public Double getLandValue() {
		return landValue;
	}
	public void setLandValue(Double landValue) {
		this.landValue = landValue;
	}
	public String getAppraisedBy() {
		return appraisedBy;
	}
	public void setAppraisedBy(String appraisedBy) {
		this.appraisedBy = appraisedBy;
	}
	public String getValuationDate() {
		return valuationDate;
	}
	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}
	public String getAddressOfProperty() {
		return addressOfProperty;
	}
	public void setAddressOfProperty(String addressOfProperty) {
		this.addressOfProperty = addressOfProperty;
	}
	public Double getSuggestedAmountOfLoan() {
		return suggestedAmountOfLoan;
	}
	public void setSuggestedAmountOfLoan(Double suggestedAmountOfLoan) {
		this.suggestedAmountOfLoan = suggestedAmountOfLoan;
	}
	
	
}
