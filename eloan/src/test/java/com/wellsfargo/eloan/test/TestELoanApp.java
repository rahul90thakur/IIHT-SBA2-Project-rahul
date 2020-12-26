package com.wellsfargo.eloan.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.iiht.training.eloan.dto.UserDto;

public class TestELoanApp {
	
	public static void main(String[] args) {

		UserDto u = new UserDto(1L,"abc","xyz","abc@xyz.com","9876543219","customer");

		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();		
		em.persist(u);
		txn.commit();
		em.close();
	}

}
