package org.sunhp.rcampus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.sunhp.rcampus.bean.User;
import org.sunhp.rcampus.components.Page;
import org.sunhp.rcampus.components.Pageable;
import org.sunhp.rcampus.dao.impl.UserDaoImpl;
import org.sunhp.rcampus.service.*;
import org.sunhp.rcampus.util.MailUtil;
import org.sunhp.rcampus.util.Result;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes("user")
public class IndexController {
	@Autowired
	ApiService apiService;
	@Autowired
	MailUtil mailUtil;
<<<<<<< HEAD
	@Autowired
	UserDaoImpl userDao;
	@RequestMapping("/")
=======

	@RequestMapping("/index.do")
>>>>>>> e81a3cb0e23468e3f0abc89f01249800c87a6ddb
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	@ResponseBody
	@RequestMapping("/test.do")
	public String test() throws IOException {
		return apiService.doGet("http://www.baidu.com");
		// List<Store> list = new ArrayList<Store>();
		// list = storeService.getAll(new Store());
		// return "总共有：" + list.size();
	}
<<<<<<< HEAD
   @RequestMapping("/home")
   public String home(){
	   return "home";
   }
    @ResponseBody
	@RequestMapping("signin")
	public String signin(HttpServletRequest request, HttpServletResponse response,Model model) {
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		Pageable<User> pgl=new Pageable<User>();
		pgl.setSearchProperty("email");
		pgl.setSearchValue(email);
		Page<User> page=userDao.findPage(pgl);
		List<User> users=page.getRows();
		Result result=new Result();
		if(users.size()>0){
		User user=users.get(0);
		if(user.getPasswd().equals(password)){
			model.addAttribute("user",user);
		    result.setStatus("success");
		    result.setFlag(1);
		    return JSON.toJSONString(result);
    }
}
		result.setFlag(0);
		result.setStatus("failure");
		return JSON.toJSONString(result);
	}
    @ResponseBody
	@RequestMapping("signup")
	public String signup(HttpServletRequest request, HttpServletResponse response) {
		String email=request.getParameter("email");
		System.out.println(email);
		String password=request.getParameter("password");
		Pageable<User> pgl=new Pageable<User>();
		pgl.setSearchProperty("email");
		pgl.setSearchValue(email);
		long count=userDao.count(pgl);
		Result result=new Result();
		if(count==0){
		String link="http://127.0.0.1:8080/rcampus/verify?email="+email+"&password="+password;
		mailUtil.sendMail(email,link);

	@ResponseBody
	@RequestMapping("signin")
	public String signin(HttpServletRequest request,
			HttpServletResponse response, Long courseId) {
		String email = request.getParameter("email");
		String link = "www.baidu.com";
		mailUtil.sendMail(email, link);
		Result result = new Result();
		result.setStatus("success");
		result.setFlag(1);
		return JSON.toJSONString(result);
		}
		result.setStatus("failure");
		result.setFlag(0);
		return JSON.toJSONString(result);
	}
    @RequestMapping("verify")
    public String verify(HttpServletRequest request,Model model){
    	String email=request.getParameter("email");
    	String pwd=request.getParameter("password");
    	User user=new User();
    	user.setEmail(email);
    	user.setPasswd(pwd);
    	userDao.add(user);
    	model.addAttribute("user",user);
    	return "home";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session){
    	session.removeAttribute("user");
    	return "index";
    }
}
