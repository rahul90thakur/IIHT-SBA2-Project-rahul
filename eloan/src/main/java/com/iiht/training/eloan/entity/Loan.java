package com.iiht.training.eloan.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="loan")
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="loan_id")
	private Long id;
	
	@Column(name="cust_id")
	private Long customerId;
	
	@Column(name="loan_name")
	private String loanName;
	
	@Column(name="loan_amt")
	private Double loanAmount;
	
	@Column(name="loan_appl_date")
	private String loanApplicationDate;
	
	@Column(name="buss_strc")
	private String businessStructure;
	
	@Column(name="billing_ind")
	private String billingIndicator;
	
	@Column(name="tax_ind")
	private String taxIndicator;
	
	@Column(name="status")
	private Integer status;
	
	@Column(name="remark")
	private String remark;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users userObj;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public Users getUserObj() {
		return userObj;
	}
	public void setUserObj(Users userObj) {
		this.userObj = userObj;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
