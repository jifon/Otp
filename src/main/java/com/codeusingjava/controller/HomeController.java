package com.codeusingjava.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.codeusingjava.service.OTPService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
//@RequestMapping("/api/product")
public class HomeController {
//    @Value("${spring.application.name}")

    @Value("")
    String appName;
    
    @Autowired
    public OTPService otpService;

    @GetMapping("/")
    public String homePage(Model model) {
    String message = " Welcome to Home Page";
        model.addAttribute("appName", appName);
        model.addAttribute("message", message);
        return "signin";
    }

    @GetMapping("/dashboard")
    public String dashboard(){
     return "dashboard";
    }
    @GetMapping("/login")
    public String login() {
        return "signin";
    }
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
    @GetMapping("/user")
    public String user() {
        return "user";
    }
  
  
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public @ResponseBody String logout(HttpServletRequest request, HttpServletResponse response){
       Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
       if (auth != null){    
       String username = auth.getName();
       //Remove the recently used OTP from server. 
       otpService.clearOTP(username);
       new SecurityContextLogoutHandler().logout(request, response, auth);}
       return "redirect:/login?logout";
    }
}
