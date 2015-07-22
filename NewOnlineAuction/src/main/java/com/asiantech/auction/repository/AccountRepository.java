package com.asiantech.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;  
import com.asiantech.auction.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{
	//you can : findByUserNameContain(string username)
	/*@Query("select u from User u where u.userName  = 1?") */
	Account findByUserName(String username);
	
	Account findByEmail(String email);
	
	Account findByEmailAndBlock(String email, boolean blockFalse);
}
