package com.iiht.training.eloan.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iiht.training.eloan.dto.LoanDto;
import com.iiht.training.eloan.dto.LoanOutputDto;
import com.iiht.training.eloan.dto.ProcessingDto;
import com.iiht.training.eloan.dto.RejectDto;
import com.iiht.training.eloan.dto.SanctionDto;
import com.iiht.training.eloan.dto.SanctionOutputDto;
import com.iiht.training.eloan.entity.Loan;
import com.iiht.training.eloan.entity.ProcessingInfo;
import com.iiht.training.eloan.entity.SanctionInfo;
import com.iiht.training.eloan.entity.Users;
import com.iiht.training.eloan.repository.LoanRepository;
import com.iiht.training.eloan.repository.ProcessingInfoRepository;
import com.iiht.training.eloan.repository.SanctionInfoRepository;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.ManagerService;

@Service
public abstract class ManagerServiceImpl<SanctionOutputDto, SanctionInfo> implements ManagerService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private ProcessingInfoRepository processingInfoRepository;
	
	@Autowired
	private SanctionInfoRepository sanctionInfoRepository;
	
	@Override
	public List<LoanDto> allProcessedLoans() {
		List<LoanDto> u1 = null;
		u1 = loanRepository.findAll().stream().map(e -> loanParse(e)).collect(Collectors.toList());
		 for(int i =0;i<u1.size();i++) {
			 //System.out.println("i is - "+i);
			 	//System.out.println("LoanId:"+u1.get(i).getLoanAppId()+" remark is "+u1.get(i).getRemark());
				if(((u1.get(i)).getRemark().equalsIgnoreCase("Processed"))) {
					System.out.println("** "+u1.get(i).getLoanAmount()+" is processed and status is "+u1.get(i).getStatus()+"-- not Removed *****");
				} else {
					System.out.println("** "+u1.get(i).getLoanAmount()+" is not processed and status is "+u1.get(i).getStatus()+"-- Removed");
					u1.remove(i);
					
				} 
				}
		 System.out.println("Current existing data -- Manager");
		 for(int i =0;i<u1.size();i++) {
			 System.out.println(u1.get(i).getLoanAmount()+" - status is "+u1.get(i).getRemark());
		 }
		 
		 return u1;
	}
	
	@Modifying
	@Override
	public RejectDto rejectLoan(Long managerId, Long loanAppId, RejectDto rejectDto) {
		return rejectParse(loanRepository.save(rejectParse(managerId,loanAppId,rejectDto)));
	}
	

	private RejectDto rejectParse(Loan loanObj) {
		RejectDto rejectDto = new RejectDto();
		rejectDto.setRemark("Rejected");
		
		return rejectDto;
	}

	private Loan rejectParse(Long managerId, Long loanAppId, RejectDto source) {
		Loan loanInf = getLoanInfo(loanAppId);
		System.out.println("Reject -- Earlier:: Loan ID:"+ loanInf.getId()+ " status:"+loanInf.getStatus()+" Remark: "+loanInf.getRemark());
		loanInf.setRemark("Rejected");
		loanInf.setStatus(-1);
		
		System.out.println("Reject -- Later:: Loan ID:"+ loanInf.getId()+ " status:"+loanInf.getStatus()+" Remark: "+loanInf.getRemark());
		return loanInf;
	}
	
	private Loan getLoanInfo(Long loanAppId) {
		return loanRepository.findById(loanAppId).get();
	}
	
	@Transactional
	@Override
	public SanctionOutputDto sanctionLoan(Long managerId, Long loanAppId, SanctionDto sanctionDto) {
		return sanctionParse(sanctionInfoRepository.save(sanctionParse(managerId,loanAppId,sanctionDto)));
	}
	
private SanctionInfo sanctionParse(Long managerId, Long loanAppId, SanctionDto source) {
	SanctionInfo target = new SanctionInfo();
	
	target.setManagerId(managerId);
	target.setLoanAppId(loanAppId);
	target.setLoanAmountSanctioned(source.getLoanAmountSanctioned());
	target.setTermOfLoan(source.getTermOfLoan());
	target.setPaymentStartDate(source.getPaymentStartDate());
	
	int interestRate = 10;
	int totalPaymentAmt = (int) ((source.getLoanAmountSanctioned()) * Math.pow((1+interestRate/100),source.getTermOfLoan()/12));
	double loanEmiAmt = totalPaymentAmt / (source.getTermOfLoan()/12);
	LocalDate loanClosureDt = LocalDate.now().plusMonths((long) (source.getTermOfLoan()+1));
	
	target.setLoanClosureDate(loanClosureDt.toString());
	target.setMonthlyPayment(loanEmiAmt);
	
	Loan loanInf = getLoanInfo(loanAppId);
	System.out.println("Sanction -- Earlier:: Loan ID:"+ loanInf.getId()+ " status:"+loanInf.getStatus()+" Remark: "+loanInf.getRemark());
	loanInf.setRemark("Sanctioned");
	loanInf.setStatus(2);
	
	System.out.println("Sanction -- Later:: Loan ID:"+ loanInf.getId()+ " status:"+loanInf.getStatus()+" Remark: "+loanInf.getRemark());
	
	return target;
	}

private SanctionOutputDto sanctionParse(SanctionInfo source) {
	SanctionOutputDto target = new SanctionOutputDto();
	
	target.setLoanAmountSanctioned(source.getLoanAmountSanctioned());
	target.setTermOfLoan(source.getTermOfLoan());
	target.setPaymentStartDate(source.getPaymentStartDate());
	target.setLoanClosureDate(source.getLoanClosureDate());
	target.setMonthlyPayment(source.getMonthlyPayment());
	
	return target;
}
	
public static LoanOutputDto loanParse(Loan loanInf) {
		
		LoanDto loanDto = new LoanDto();
		loanDto.setLoanAmount(loanInf.getLoanAmount());
		loanDto.setLoanName(loanInf.getLoanName());
		loanDto.setBillingIndicator(loanInf.getBillingIndicator());
		loanDto.setBusinessStructure(loanInf.getBusinessStructure());
		loanDto.setTaxIndicator(loanInf.getTaxIndicator());
		
		
		LoanOutputDto loanOutputDto = new LoanOutputDto();
		loanOutputDto.setCustomerId(loanInf.getCustomerId());
		loanOutputDto.setLoanAppId(loanInf.getId());
		loanOutputDto.setLoanDto(loanDto);
		loanOutputDto.setStatus(loanInf.getStatus());
		loanOutputDto.setRemark(loanInf.getRemark());
		//System.out.println("loanInf.getUserObj().getId())"+loanInf.getUserObj());
		//loanOutputDto.getUserDto().setId(loanInf.getUserObj().getId());
		
		
		return loanOutputDto;
	}
	
	public static Loan loanParse(LoanDto loanDto) {
		Loan loanInf = new Loan();
		loanInf.setCustomerId(loanDto.getUserDto().getId());
		loanInf.setLoanName(loanDto.getLoanName());
		loanInf.setLoanAmount(loanDto.getLoanAmount());
		loanInf.setLoanApplicationDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
		loanInf.setBusinessStructure(loanDto.getBusinessStructure());
		loanInf.setBillingIndicator(loanDto.getBillingIndicator());
		loanInf.setTaxIndicator(loanDto.getTaxIndicator());
		loanInf.setStatus(0);
		loanInf.setRemark("Applied");
		
		
		Users userObj = new Users();
		userObj.setId(loanDto.getUserDto().getId());
		
		loanInf.setUserObj(userObj);
		
		return loanInf;
		
	}


}
