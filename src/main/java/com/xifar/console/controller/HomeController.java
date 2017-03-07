package com.xifar.console.controller;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Stack;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = { "/home" })
public class HomeController {

	@RequestMapping(value = { "/index" })
	public void gotoHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher sss = request.getRequestDispatcher("/WEB-INF/views/html/index.html");
		sss.forward(request, response);
	}
	
}