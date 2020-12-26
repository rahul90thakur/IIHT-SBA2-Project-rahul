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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.dto.exception.ExceptionResponse;
import com.iiht.training.eloan.exception.CustomerNotFoundException;
import com.iiht.training.eloan.exception.InvalidDataException;
import com.iiht.training.eloan.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/register-clerk")
	public ResponseEntity<UserDto> registerClerk(@RequestBody @Valid UserDto userDto,BindingResult result) {
		if(result.hasErrors()) {
			//throw new InvalidDataException(from(result));
		}
		return new ResponseEntity<UserDto>(adminService.registerClerk(userDto),HttpStatus.OK);
	}
	
	@PostMapping("/register-manager")
	public ResponseEntity<UserDto> registerManager(@RequestBody @Valid UserDto userDto,BindingResult result){
		if(result.hasErrors()) {
			//throw new InvalidDataException(from(result));
		}
		return new ResponseEntity<UserDto>(adminService.registerManager(userDto),HttpStatus.OK);
	}
	
	@GetMapping("/all-clerks")
	public ResponseEntity<List<UserDto>> getAllClerks(){
		return new ResponseEntity<>(adminService.getAllClerks(),HttpStatus.OK);
	}
	
	@GetMapping("/all-managers")
	public ResponseEntity<List<UserDto>> getAllManagers(){
		return new ResponseEntity<>(adminService.getAllManagers(),HttpStatus.OK);
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
