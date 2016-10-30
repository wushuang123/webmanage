package com.xifar.console.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = { "/home" })
public class HomeController {

	@RequestMapping(value = { "/index" })
	public ModelAndView gotoHome() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/index");
		return mv;
	}
	
	@RequestMapping(value = { "/demo" })
	public ModelAndView demo() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/demo");
		return mv;
	}
}