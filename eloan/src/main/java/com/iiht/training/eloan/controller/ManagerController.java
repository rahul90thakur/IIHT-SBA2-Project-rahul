package com.iiht.training.eloan.controller;

import java.util.List;

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

import com.iiht.training.eloan.dto.LoanOutputDto;
import com.iiht.training.eloan.dto.ProcessingDto;
import com.iiht.training.eloan.dto.RejectDto;
import com.iiht.training.eloan.dto.SanctionDto;
import com.iiht.training.eloan.dto.SanctionOutputDto;
import com.iiht.training.eloan.dto.exception.ExceptionResponse;
import com.iiht.training.eloan.exception.AlreadyFinalizedException;
import com.iiht.training.eloan.exception.AlreadyProcessedException;
import com.iiht.training.eloan.exception.InvalidDataException;
import com.iiht.training.eloan.exception.ManagerNotFoundException;
import com.iiht.training.eloan.service.ManagerService;

@RestController
@RequestMapping("/manager")
public class ManagerController<LoanOutputDto, AlreadyProcessedException, SanctionOutputDto> {

	@Autowired
	private ManagerService managerService;
	
	@GetMapping("/all-processed")
	public ResponseEntity<List<LoanOutputDto>> allProcessedLoans() {
		return new ResponseEntity<>(managerService.allProcessedLoans(),HttpStatus.OK);

	}
	
	@PostMapping("/reject-loan/{managerId}/{loanAppId}")
	public ResponseEntity<RejectDto> rejectLoan(@PathVariable Long managerId,
												@PathVariable Long loanAppId,
												@RequestBody RejectDto rejectDto, BindingResult result) {
		if(result.hasErrors()) {
			throw new InvalidDataException(from(result));
		}
		return new ResponseEntity<>(managerService.rejectLoan(managerId,loanAppId,rejectDto),HttpStatus.OK);
	}
	
	@PostMapping("/sanction-loan/{managerId}/{loanAppId}")
	public ResponseEntity<SanctionOutputDto> sanctionLoan(@PathVariable Long managerId,
												@PathVariable Long loanAppId,
												@RequestBody SanctionDto sanctionDto, BindingResult result) throws InvalidDataException{
		if(result.hasErrors()) {
			throw new InvalidDataException(from(result));
		}
		return (ResponseEntity<SanctionOutputDto>) new ResponseEntity<>(managerService.sanctionLoan(managerId,loanAppId,sanctionDto),HttpStatus.OK);
	}
	
	@ExceptionHandler(ManagerNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handler(ManagerNotFoundException ex){
		ExceptionResponse exception = 
				new ExceptionResponse(ex.getMessage(),
									  System.currentTimeMillis(),
									  HttpStatus.NOT_FOUND.value());
		ResponseEntity<ExceptionResponse> response =
				new ResponseEntity<ExceptionResponse>(exception, HttpStatus.NOT_FOUND);
		return response;
	}
	
	@ExceptionHandler(AlreadyFinalizedException.class)
	public ResponseEntity<ExceptionResponse> handler(AlreadyFinalizedException ex){
		ExceptionResponse exception = 
				new ExceptionResponse(ex.getMessage(),
									  System.currentTimeMillis(),
									  HttpStatus.BAD_REQUEST.value());
		ResponseEntity<ExceptionResponse> response =
				new ResponseEntity<ExceptionResponse>(exception, HttpStatus.BAD_REQUEST);
		return response;
	}
	
	@ExceptionHandler(AlreadyProcessedException.class)
	public ResponseEntity<ExceptionResponse> handler(AlreadyProcessedException ex){
		ExceptionResponse exception = 
				new ExceptionResponse(((ExceptionResponse) ex).getMessage(),
									  System.currentTimeMillis(),
									  HttpStatus.BAD_REQUEST.value());
		ResponseEntity<ExceptionResponse> response =
				new ResponseEntity<ExceptionResponse>(exception, HttpStatus.BAD_REQUEST);
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
