package com.asiantech.auction.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asiantech.auction.entity.Category;
import com.asiantech.auction.service.CategoryService;

@Controller
@RequestMapping("/admin")
public class AdminCategoriController {

	@Autowired 
	CategoryService categorySv;
	
	@ModelAttribute("categories")
	public List<Category> seeAllCategories() {
	    return categorySv.getAll();
	}
	
	@RequestMapping("/adminCategories")
    public String getAdminCategoriesPage(Category category, Model model){    
		model.addAttribute(category);
		model.addAttribute("pageview", "admin/admin-categories");
        return "admin/admin-views"; 
    }
	
    @RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
	public String saveCategory(@Valid Category category,
			BindingResult bindingResult, Model model)
			throws Exception {
    	if (bindingResult.hasErrors()) {  
	        return getAdminCategoriesPage(category, model);
		}
    	categorySv.saveOrUpdate(category);
    	return "redirect:adminCategories";
	}
    
    @RequestMapping("/editCategory")
	public String editCategory(@RequestParam int id,
			RedirectAttributes redirectAttrs) { 
    	redirectAttrs.addFlashAttribute(categorySv.getById(id));
		return "redirect:adminCategories";
	}
    
    @RequestMapping("/deleteCategory")
	public String deleteCategory(@RequestParam int id){ 
    	categorySv.deleteById(id);
		return "redirect:adminCategories";
	}
}
