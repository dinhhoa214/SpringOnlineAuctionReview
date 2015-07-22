package com.asiantech.auction.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 
import com.asiantech.auction.entity.Bid; 
import com.asiantech.auction.service.BidService;

@Controller
@RequestMapping("/admin")
public class AdminBidsController {

	@Autowired 
	BidService bidSv;
	
	@RequestMapping("/adminBids")
    public String getAdminBidsPage(
    		@RequestParam(required = false, defaultValue = "0") Integer page, 
			@RequestParam(required = false, defaultValue = "5") Integer maxRows,
			@RequestParam(required = false, defaultValue = "numberIndex") String sort,
			Bid bid,Model model  ){    
		Order order = new Order(Direction.DESC,sort); 
		Page<Bid> bidsPage = bidSv.getAllBidAndPagination(new PageRequest(
				page, maxRows, new Sort(order)));   
		//model = getPageModel(model, pageDefault, sizePageDefault, usersPage); 
		model.addAttribute("bids", bidsPage);  
		model.addAttribute(bid);
		model.addAttribute("pageview", "admin/admin-bids");
        return "admin/admin-views";
    }  
	@RequestMapping(value = "/saveBid", method = RequestMethod.POST)
	public String saveCategory(@Valid Bid bid, Model model)
			throws Exception { 
    	bidSv.updateBid(bid);
    	return "redirect:adminBids";
	}
    @RequestMapping("/editBid")
	public String editBid(@RequestParam int id,RedirectAttributes redirectAttrs) { 
    	redirectAttrs.addFlashAttribute(bidSv.getById(id));
		return "redirect:adminBids";
	}
    @RequestMapping("/deleteBid")
	public String deleteBid(@RequestParam int id){ 
    	bidSv.deleteById(id);
		return "redirect:adminBids";
	}
}
