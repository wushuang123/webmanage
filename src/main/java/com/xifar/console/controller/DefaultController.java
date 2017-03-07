package com.xifar.console.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller  
public class DefaultController {  
    @RequestMapping("/")
    @ResponseBody
    public String index(Model model,HttpServletRequest request,String action,String msg){  
        HttpSession session=request.getSession();  
        if ("set".equals(action)){  
            session.setAttribute("msg", msg);
            return "OK";  
        }else if ("get".equals(action)){  
            String message=(String)session.getAttribute("msg");  
            model.addAttribute("msgFromRedis",message);
            System.out.println(message);
            return message;  
        }else{
        	return "error";
        }
        
    }  
}  
