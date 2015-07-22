package com.asiantech.auction.controller;
 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.asiantech.auction.entity.Account;
import com.asiantech.auction.entity.Bid;
import com.asiantech.auction.entity.Category;
import com.asiantech.auction.entity.Item;
import com.asiantech.auction.entity.Rating;
import com.asiantech.auction.service.AccountService;
import com.asiantech.auction.service.BidService;
import com.asiantech.auction.service.CategoryService;
import com.asiantech.auction.service.ItemService;
import com.asiantech.auction.service.RatingService;
@Controller
@RequestMapping("/item")
public class UserItemController {
	
	@Autowired 
	ItemService itemSv; 
	
	@Autowired 
	CategoryService categorySv;

	@Autowired 
	BidService bidSv;
	
	@Autowired 
	AccountService accountSv;
	
	@Autowired
	RatingService ratingSv;
	
	@ModelAttribute("categories")
	public List<Category> seeAllCategories() {
	    return categorySv.getAll();
	}
	
	@RequestMapping("/{id}")
    public String getItemPage(@PathVariable("id") Integer itemId, Bid bid, Model model){
		Item item = itemSv.getById(itemId); 
		model.addAttribute(item);
		model.addAttribute(bid);
		model.addAttribute("bidtop", bidSv.getByItemOrderAmountTop(item));
		model.addAttribute("bids", bidSv.getTop10ByItemId(item));
		model.addAttribute("pageview", "item");
		return "PAGES-VIEWS";
    }
	
	@RequestMapping(value = "bidItem{id}", method = RequestMethod.POST)
	public String insertBid(@PathVariable("id") Integer itemId,
			@ModelAttribute("bid") Bid bid, Model model) {  
		Item item = itemSv.getById(itemId);
		bid.setItemId(item);
		bid.setBidderId(getAccountLogin());
		
		
		if(!bidSv.insertBid(bid))
			return "redirect:/item/"+itemId;
		return "redirect:/item/"+itemId;
	}

	private Account getAccountLogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Account account = accountSv.getUserByEmail(email);
		return account;
	} 
	
	@RequestMapping(value = "{id}/endAuction", method = RequestMethod.POST)
	public String setItemEnded(@PathVariable("id") Integer itemId) { 
		itemSv.setEndedItem(itemId);
		return "redirect:/item/"+itemId;
	}
	
	@RequestMapping("/{id}/ratingUser/{score}")
	public String ratingUser(@PathVariable("id") Integer itemId,
			@RequestParam("emailRater") String emailRater,
			@PathVariable("score") Integer score) { 
		Account accountRater = getAccountLogin();
		Account accountRated = accountSv.getUserByEmail(emailRater);
		Rating rate = new Rating(accountRater, accountRated, score);
		ratingSv.insertRating(rate);
		return "redirect:/item/"+itemId;
	}
}
