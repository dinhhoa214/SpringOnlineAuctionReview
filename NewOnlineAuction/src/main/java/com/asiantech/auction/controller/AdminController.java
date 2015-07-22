package com.asiantech.auction.controller;
  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;  

@Controller
@RequestMapping("/admin")
public class AdminController {
	//default method is get
	//you can implement pageable of spring
    //you can use defaultvalue for page param 
	@RequestMapping("/adminStPanels")
    public String getAdminStpanelsPage(Model model){  
		model.addAttribute("pageview", "admin/stpanels");
        return "admin/admin-views";
    }
	
	@RequestMapping("/adminStIcons")
    public String getAdminSticonsPage(Model model){  
        model.addAttribute("pageview", "admin/sticons");
        return "admin/admin-views";
    }
	
	@RequestMapping("/adminStTypography")
    public String getAdminSttypoPage(Model model){  
        model.addAttribute("pageview", "admin/sttypography");
        return "admin/admin-views";
    }

	@RequestMapping("/adminTablePage")
    public String getAdminTablePage(Model model){  
        model.addAttribute("pageview", "admin/tablepage");
        return "admin/admin-views";
    }
	@RequestMapping("/adminBlankPage")
    public String getAdminBlankPage(Model model){  
		model.addAttribute("pageview", "admin/blankpage");
        return "admin/admin-views";
    }
}
