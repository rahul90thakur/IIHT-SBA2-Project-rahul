package com.iiht.training.eloan.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.training.eloan.dto.LoanDto;
import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.dto.exception.ExceptionResponse;
import com.iiht.training.eloan.exception.CustomerNotFoundException;
import com.iiht.training.eloan.exception.InvalidDataException;
import com.iiht.training.eloan.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController<LoanOutputDto> {

	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@RequestBody @Valid UserDto userInfo,BindingResult result) {
		if(result.hasErrors()) {
			//throw new InvalidDataException(from(result));
		}
		
		return new ResponseEntity<UserDto>(customerService.register(userInfo),HttpStatus.OK);
	}

	@PostMapping("/apply-loan/{customerId}")
	public ResponseEntity<LoanOutputDto> applyLoan(@PathVariable Long customerId, @Valid @RequestBody LoanDto loanDto,BindingResult result){
		if(result.hasErrors()) {
			//throw new InvalidDataException(from(result));
		}
		return new ResponseEntity<LoanOutputDto>(HttpStatus.OK);
	}
	
	@GetMapping("/loan-status/{loanAppId}")
	public ResponseEntity<LoanOutputDto> getStatus(@PathVariable Long loanAppId){
		return new ResponseEntity<LoanOutputDto>(HttpStatus.OK);
	}
	
	@GetMapping("/loan-status-all/{customerId}")
	public ResponseEntity<List<LoanOutputDto>> getStatusAll(@PathVariable Long customerId) {
		return null;
		//return new ResponseEntity<>(customerService.getStatusAll(customerId),HttpStatus.OK);
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handler(CustomerNotFoundException ex){
		ExceptionResponse exception = new ExceptionResponse(ex.getMessage(),System.currentTimeMillis(),HttpStatus.NOT_FOUND.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception, HttpStatus.NOT_FOUND);
		return response;
	}
	
	
	public static String from(BindingResult result) {
		StringBuilder sb = new StringBuilder();
		
		for(ObjectError err : result.getAllErrors()) {
			sb.append(err.getDefaultMessage()+",");
		}
		
		return sb.toString();
	} 
}
