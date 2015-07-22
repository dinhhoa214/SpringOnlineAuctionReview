package com.asiantech.auction.controller;  

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile; 

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.entity.Category;
import com.asiantech.auction.entity.Item;
import com.asiantech.auction.service.AccountService;
import com.asiantech.auction.service.CategoryService;
import com.asiantech.auction.service.ImageService;
import com.asiantech.auction.service.ItemService;

@Controller
public class HomeController {

	@Autowired
	CategoryService categorySv;

	@Autowired
	AccountService accountSv;
	
	@Autowired
	ItemService itemSv;

	@Autowired
	ImageService imageSv;
	
	@ModelAttribute("categories")
	public List<Category> seeAllCategories() {
	    return categorySv.getAll();
	}
	
	@ModelAttribute("newAuction")
	public List<Item> seeAllNewAuction() {
	    return itemSv.getNewTop6Item();
	}
	
	@ModelAttribute("hotAuction")
	public List<Item> seeAllHotAuction() {
	    return itemSv.getHotTop6Item();
	}
	
	@ModelAttribute("endAuction")
	public List<Item> seeAllEndAuction() {
	    return itemSv.getEndTop6Item();
	}

	@RequestMapping({ "/", "/home", "/index" })
	public String getHomePage(Account account, Model model){
		model.addAttribute("pageview", "homepage");
		return "PAGES-VIEWS";
	}

	@RequestMapping("/register")
	public String getRegisterPage(Account account){
		return "register";
	}

	// extract to method with block if - else
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String saveAccount(@ModelAttribute("account") Account account, 
			BindingResult binding,@RequestParam(required = false) MultipartFile image) {
		if (!image.isEmpty()) {
			account.setImage(imageSv.updateImage(image, "user")); 
		}
		if (accountSv.insertUser(account) != true)
			return "register-error"; 
		return "register-success";
	}
	
	@RequestMapping("/searchItem")
	public String searchItemGet(Model model) { 
		model.addAttribute("itemSearch", itemSv.getAllByStatusActive());
		model.addAttribute("pageview", "search-result");
		return "PAGES-VIEWS";
		
	}
	
	@RequestMapping(value = "searchItem", method = RequestMethod.POST)
	public String searchItem(@RequestParam("textSearch") String textSearch,
			@RequestParam(value = "categories", defaultValue = "All") List<String> categories,
			Model model) {
		model.addAttribute("itemSearch", itemSv.searchItemByTitleAndCategories(textSearch, categories));
		model.addAttribute("pageview", "search-result");
		return "PAGES-VIEWS";
		
	}
	

}
