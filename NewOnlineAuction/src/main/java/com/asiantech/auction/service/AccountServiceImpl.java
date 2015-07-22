package com.asiantech.auction.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.asiantech.auction.entity.Account;
import com.asiantech.auction.entity.Account.Role;
import com.asiantech.auction.entity.Rating;
import com.asiantech.auction.repository.AccountRepository;

@Service(AccountService.NAME)
public class AccountServiceImpl implements AccountService,UserDetailsService {
	@Resource
	AccountRepository userRepositoty;
	 
	@Autowired
	RatingService ratingSv;

	@Autowired
	PasswordEncoder passwordEncoder ;
	
	@Autowired
	MailService mailSv;

	public Collection<? extends GrantedAuthority> getAuthorities(Role role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}
	
	public List<String> getRoles(Role role){
		List<String> roles = new ArrayList<>();
		roles.add(role.name());
		return roles;
	}

	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	 

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException { 
		System.out.println("Email login: "+email);
		Account acLogin = getUserByEmail(email); 
		User user = new User(acLogin.getEmail(), acLogin.getPassword(), 
				true, true, true,true, getAuthorities(acLogin.getRole()));
		return user;
	}

	@Override
	public Account getUserByEmail(String email) {   
		return userRepositoty.findByEmailAndBlock(email, false);
	}

	@Override
	public Account getById(int id) {
		return userRepositoty.findOne(id);
	}
	
	@Override
	public boolean checkByUserName(Account user) {
		Account check = userRepositoty.findByUserName(user.getUserName());  
		if(check.getPassword().equalsIgnoreCase(user.getPassword())) 
			return true;
		return false;
	}
	
	@Override
	public Account getByUserName(String userName) { 
		return userRepositoty.findByUserName(userName);
	}

	@Override
	public boolean insertUser(Account user) {
		// kiem tra userName co trung ko? 
		if(getUserByEmail(user.getEmail())!=null) 
			return false;
		// Gui email dang ky thanh cong
		mailSv.newEmailForRegister(user.getEmail(), user.getPassword());
		Thread t = new Thread(mailSv);
		t.start();
		String pwd = passwordEncoder.encode(user.getPassword());
		user.setPassword(pwd);
		user.setRole(Role.ROLE_USER); 
		if(user.getImage()==null)
			user.setImage("user/default.jpg");
		userRepositoty.save(user);
		
		return true;
	}

	@Override
	public Account updateUser(Account user) {
		return userRepositoty.save(user);
	}

	@Override
	public boolean changePass(Account user, String oldPass, String newPass) { 
		if(!passwordEncoder.matches(oldPass, user.getPassword()))
			return false;
		user.setPassword(passwordEncoder.encode(newPass));
		userRepositoty.save(user);
		return true;
	}

	@Override
	public void deleteById(int id) {
		userRepositoty.delete(id);
	}

	@Override
	public List<Account> getAll() {
		return userRepositoty.findAll();
	}

	@Override
	public Page<Account> getAllUserAndPagination(Pageable pageable) {
		return userRepositoty.findAll(pageable);
	}

	@Override
	public void updateRating(Account user) {
		List<Rating> ratingOfUser = ratingSv.getAllByUser(user);
		float sumRate = 0;
		for(Rating i:ratingOfUser){
			sumRate += i.getRate();
		}
		user.setTotalRating(sumRate/ratingOfUser.size());
		userRepositoty.save(user);
	}
	
	
	
}
