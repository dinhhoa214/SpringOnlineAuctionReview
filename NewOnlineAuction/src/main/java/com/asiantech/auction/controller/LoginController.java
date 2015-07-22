package com.asiantech.auction.controller;
 
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam;  
 

@Controller
@RequestMapping("/account/")
public class LoginController { 
	
	@RequestMapping(value="login")
	public String getLoginPage(@RequestParam(required= false) boolean error, Model model){
		model.addAttribute("errorLogin", error);
		return "login";
	} 
}
