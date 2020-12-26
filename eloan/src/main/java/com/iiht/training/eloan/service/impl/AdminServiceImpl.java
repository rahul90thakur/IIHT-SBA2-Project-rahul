package com.iiht.training.eloan.service.impl;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.entity.Users;
import com.iiht.training.eloan.exception.InvalidDataException;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Transactional
	@Override
	public UserDto registerClerk(UserDto userDto)  {
			userDto = clerkParse(usersRepository.save(clerkParse(userDto)));
		return userDto;
	}

	@Override
	public UserDto registerManager(UserDto userDto){
			userDto = managerParse(usersRepository.save(managerParse(userDto)));
		return userDto;
}

	@Override
	public List<UserDto> getAllClerks() {
		return usersRepository.findAllByRole("clerk").stream().map(e -> clerkParse(e)).collect(Collectors.toList());
	}

	@Override
	public List<UserDto> getAllManagers() {
		return usersRepository.findAllByRole("manager").stream().map(e -> clerkParse(e)).collect(Collectors.toList());
	}

	public static UserDto clerkParse(Users source) {
		UserDto target = new UserDto();
		target.setId(source.getId());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setEmail(source.getEmail());
		target.setMobile(source.getMobile());
		target.setRole(source.getRole());
		return target;
	}
	
	public static Users clerkParse(UserDto source) {
		Users target = new Users();
		target.setId(source.getId());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setEmail(source.getEmail());
		target.setMobile(source.getMobile());
		target.setRole("clerk");
		
		return target;
	}
	
	public static UserDto managerParse(Users source) {
		UserDto target = new UserDto();
		target.setId(source.getId());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setEmail(source.getEmail());
		target.setMobile(source.getMobile());
		target.setRole(source.getRole());
		return target;
	}
	
	public static Users managerParse(UserDto source) {
		Users target = new Users();
		target.setId(source.getId());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setEmail(source.getEmail());
		target.setMobile(source.getMobile());
		target.setRole("manager");
		
		return target;
	}
	
}
