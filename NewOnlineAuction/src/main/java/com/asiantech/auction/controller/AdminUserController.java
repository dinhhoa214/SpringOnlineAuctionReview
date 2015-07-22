package com.asiantech.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.service.AccountService;

@Controller
@RequestMapping("/admin")
public class AdminUserController {
	
	@Autowired 
	AccountService userSv;
	
	@RequestMapping("/adminUsers")
    public String getAdminUsersPage(
    		@RequestParam(required = false, defaultValue = "0") Integer page, 
			@RequestParam(required = false, defaultValue = "5") Integer maxRows,
			@RequestParam(required = false, defaultValue = "userId") String sort,
			Account user,Model model  ){    
		Order order = new Order(Direction.DESC,sort); 
		Page<Account> usersPage = userSv.getAllUserAndPagination(new PageRequest(
				page, maxRows, new Sort(order)));  
		//model = getPageModel(model, pageDefault, sizePageDefault, usersPage); 
		model.addAttribute("users", usersPage);  
		model.addAttribute(user);
		model.addAttribute("pageview", "admin/admin-user");
        return "admin/admin-views";
    }   
	@RequestMapping("/editUser")
	public String editUser(@RequestParam int id,RedirectAttributes redirectAttrs ) {  
		
		redirectAttrs.addFlashAttribute(userSv.getById(id));  
		return "redirect:adminUsers";
	} 
	/*@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String postDeleteUser(@RequestParam int id) { 
		userSv.deleteById(id); 
		return "";
	}*/
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") Account user) { 
		userSv.updateUser(user); 
		return "redirect:adminUsers";
	}
}
