package com.iiht.training.eloan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.training.eloan.dto.LoanDto;
import com.iiht.training.eloan.dto.LoanOutputDto;
import com.iiht.training.eloan.dto.ProcessingDto;
import com.iiht.training.eloan.dto.SanctionOutputDto;
import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.entity.Loan;
import com.iiht.training.eloan.entity.Users;
import com.iiht.training.eloan.exception.InvalidDataException;
import com.iiht.training.eloan.exception.LoanNotFoundException;
import com.iiht.training.eloan.repository.LoanRepository;
import com.iiht.training.eloan.repository.ProcessingInfoRepository;
import com.iiht.training.eloan.repository.SanctionInfoRepository;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private ProcessingInfoRepository pProcessingInfoRepository;
	
	@Autowired
	private SanctionInfoRepository sanctionInfoRepository;
	
	@Transactional
	@Override
	public UserDto register(UserDto userDto) throws InvalidDataException {
		if(userDto!=null) {
			if(usersRepository.existsById(userDto.getId())) {
				throw new InvalidDataException("UserId #"+userDto.getId()+" already exists");
			}
			userDto = userParse(usersRepository.save(userParse(userDto)));

		}
		return userDto;
	}
	
	@Transactional
	@Override
	public LoanOutputDto applyLoan(Long customerId, LoanDto loanDto) {
		LoanOutputDto loanOutput = null;
		loanOutput = loanParse(loanRepository.save(loanParse(loanDto)));
		
		return loanOutput;
	}

	

	@Override
	public LoanOutputDto getStatus(Long loanAppId) {
		if(!loanRepository.existsById(loanAppId)) {
			throw new LoanNotFoundException("Loan Id #"+loanAppId+" does not exist");
		}
		return loanParse(loanRepository.findById(loanAppId).get());
	}

	@Override
	public List<LoanOutputDto> getStatusAll(Long customerId) {
		return loanRepository.findAllByCustomerId((customerId)).stream().map(e -> loanParse(e)).collect(Collectors.toList());

	}

	public static UserDto userParse(Users source) {
		UserDto target = new UserDto();
		target.setId(source.getId());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setEmail(source.getEmail());
		target.setMobile(source.getMobile());
		target.setRole(source.getRole());
		
		return target;
	}
	
	public static Users userParse(UserDto source) {
		Users target = new Users();
		target.setId(source.getId());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setEmail(source.getEmail());
		target.setMobile(source.getMobile());
		target.setRole("customer");
		
		return target;
	}
	
public static LoanDto loanParse(Loan loanInf) {
		
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
