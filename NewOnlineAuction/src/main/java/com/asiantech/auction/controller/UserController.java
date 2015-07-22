package com.asiantech.auction.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.asiantech.auction.service.RatingService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired 
	AccountService accountSv; 
	
	@Autowired
	CategoryService categorySv;
	
	@Autowired
	ItemService itemSv;
	
	@Autowired
	RatingService ratingSv;
	
	@Autowired
	ImageService imageSv;
	
	@ModelAttribute("categories")
	public List<Category> seeAllCategories() {
	    return categorySv.getAll();
	}
	
	@ModelAttribute("items")
	public List<Item> seeAllItemsByUserLogin() {
	    return itemSv.getAllByUserId(getAccountLogin());
	}
	
	@ModelAttribute("itemsWin")
	public List<Item> seeAllItemsWinByUserLogin() {
	    return itemSv.getAllByUserIdWin(getAccountLogin());
	}
	
	@RequestMapping("/newitem")
    public String getUserNewItemPage(Item item,Model model)
	{     
		model.addAttribute(item);
		model.addAttribute("pageview", "user/user-newitem");
		return "PAGES-VIEWS";
    } 
	
	@RequestMapping(value = "newitem", method = RequestMethod.POST)
    public String SaveNewItemPage(@ModelAttribute("item")Item item,
    		BindingResult binding, Model model,
    		@RequestParam(required = false) MultipartFile itemImage){ 
		item.setCreaterId(getAccountLogin());
		if (!itemImage.isEmpty()) {
			item.setItemImage(imageSv.updateImage(itemImage, "image.item")); 
		}
		itemSv.insertItem(item);
		return "redirect:newitem";
    }
	
	@RequestMapping("/active")
    public String setActiveItem(@RequestParam("itemId") Integer itemId,
    		@RequestParam("codeActive") String codeActive){   
		if(itemSv.activeItem(itemId, codeActive)!=true)
			return "user/user-active-error";
		return "redirect:newitem";
    }
	
	@RequestMapping("/delete")
	public String deleteItem(@RequestParam int id){ 
    	itemSv.sentEmailDeleteById(id);
		return "redirect:newitem";
	}
	
	@RequestMapping("/profile/{email:.*}")
    public String getUserProfilePage(@PathVariable("email") String email,
    		Account account ,Model model){   
		account = accountSv.getUserByEmail(email);  
		model.addAttribute(account);
		model.addAttribute("pageview", "user/user-profile");
		return "PAGES-VIEWS";
    } 
	
	@RequestMapping("/setting")
    public String getUserSettingsPage(Model model){  
		model.addAttribute(getAccountLogin());
		model.addAttribute("pageview", "user/user-settings");
		return "PAGES-VIEWS";
    }
	
	@RequestMapping(value = "changeAvatar", method = RequestMethod.POST)
	public String changeAvatar(@RequestParam("image") MultipartFile image) {
		Account account = getAccountLogin();
		if (!image.isEmpty()) {
			account.setImage(imageSv.updateImage(image, "user")); 
		} 
		accountSv.updateUser(account);
		return "redirect:setting"; 
	}

	private Account getAccountLogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Account account = accountSv.getUserByEmail(email);
		return account;
	}
	
	@RequestMapping(value = "changePass", method = RequestMethod.POST)
	public String changePass(
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("confirmPassword") String confirmPassword) { 
		
		if(!accountSv.changePass(getAccountLogin(), oldPassword, confirmPassword))
			System.out.println(accountSv.changePass(getAccountLogin(), oldPassword, confirmPassword));
		return "redirect:setting"; 
	}
	
	
}
