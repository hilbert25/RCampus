package org.sunhp.rcampus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.sunhp.rcampus.bean.Course;
import org.sunhp.rcampus.bean.Progress;
import org.sunhp.rcampus.bean.User;
import org.sunhp.rcampus.components.Page;
import org.sunhp.rcampus.components.Pageable;
import org.sunhp.rcampus.dao.impl.UserDaoImpl;
import org.sunhp.rcampus.service.*;
import org.sunhp.rcampus.util.MailUtil;
import org.sunhp.rcampus.util.RandomStr;
import org.sunhp.rcampus.util.Result;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class IndexController {
	@Autowired
	ApiService apiService;
	@Autowired
	MailUtil mailUtil;
	@Autowired
	UserDaoImpl userDao;
	@Autowired
	UserService userService;
	@Autowired
	ProgressService progressService;
	@Autowired
	CourseService courseService;
	@Autowired
	UserController userController;
	@Autowired
	CourseController courseController;

	/*
	 * @RequestMapping("/") public ModelAndView index() { return new
	 * ModelAndView("index"); }
	 */
	@ResponseBody
	@RequestMapping("/test.do")
	public String test() throws IOException {
		return apiService.doGet("http://www.baidu.com");
		// List<Store> list = new ArrayList<Store>();
		// list = storeService.getAll(new Store());
		// return "总共有：" + list.size();
	}

	@RequestMapping("/home")
	public String home(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		Progress progress = new Progress();
		progress.setUserId(userId);
		List<Progress> progressList = progressService.find(progress);
		if (progressList.size() > 0)
			request.setAttribute("nextCourse", courseController
					.getNextCourse(progressList.get(0).getCourseId()));
		else {
			request.setAttribute("nextCourse",
					courseService.getAll(new Course()).get(0));// 没记录就定位到第一节课
		}
		request.setAttribute("finishrate",
				String.valueOf(userController.getFinishRate(userId)) + "%");
		User user=(User) request.getSession().getAttribute("user");
		String photo=user.getPhoto();
		String icon=null;
		if(photo!=null&&photo.length()>0){
            int pos=photo.lastIndexOf('.');
			if(pos!=-1&&pos<photo.length()-1)
				icon=String.valueOf(user.getUserId()) + "_head"+photo.substring(pos);
		}
		request.setAttribute("icon",icon);
		return "home";
	}

	@RequestMapping("/adminhome")
	public String adminhome(HttpServletRequest request,
			HttpServletResponse response, String email) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		User user = userService.get(userId);
		request.setAttribute("user", user);
		return "user_manage";
	}
	@ResponseBody
	@RequestMapping("signin")
	public String signin(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Pageable<User> pgl = new Pageable<User>();
		pgl.setSearchProperty("email");
		pgl.setSearchValue(email);
		Page<User> page = userDao.findPage(pgl);
		List<User> users = page.getRows();
		Result result = new Result();
		if (users.size() > 0) {
			User user = users.get(0);
			if (user.getPasswd().equals(password)) {
				model.addAttribute("user", user);
				result.setStatus("success");
				HttpSession session = request.getSession();
				session.setAttribute("userId", user.getUserId());
				if (user.getUserType() == 1 || user.getUserType() == 0) {// admin
																			// or
																			// superadmin
					result.setFlag(2);
				} else if(user.getUserType()==2){
					result.setFlag(1);
				}
				return JSON.toJSONString(result);
			}
		}
		result.setFlag(0);
		result.setStatus("failure");
		return JSON.toJSONString(result);
	}

	@ResponseBody
	@RequestMapping("signup")
	public String signup(HttpServletRequest request,
			HttpServletResponse response) {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Pageable<User> pgl = new Pageable<User>();
		pgl.setSearchProperty("email");
		pgl.setSearchValue(email);
		long count = userDao.count(pgl);
		System.out.println(RandomStr.getRandomString(6));
		Result result = new Result();
		if (count == 0) {
			User user=new User();
			user.setEmail(email);
			user.setPasswd(password);
			user.setUserType((long) 3);
			userService.save(user);
			//String link = "http://127.0.0.1:8080/rcampus/verify?email=" + email
			//		+ "&password=" + password;
			String link = "http://127.0.0.1:8080/rcampus/verify?email=" + email;
			mailUtil.sendMessage(email, link,0);
			result.setStatus("success");
			result.setFlag(1);
			return JSON.toJSONString(result);
		}
		result.setStatus("failure");
		result.setFlag(0);
		return JSON.toJSONString(result);
	}

	@RequestMapping("verify")
	public String verify(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		//String pwd = request.getParameter("password");
		Pageable<User> pgl = new Pageable<User>();
		pgl.setSearchProperty("email");
		pgl.setSearchValue(email);
		Page<User> page = userDao.findPage(pgl);
		List<User> users = page.getRows();
		if(users.size()>0){
		User user = users.get(0);
		//user.setPasswd(pwd);
		user.setUserType((long)2);
		userService.update(user);
		HttpSession session = request.getSession();
		session.setAttribute("userId", user.getUserId());
		model.addAttribute("user", user);
		}
		return "redirect:home";
	}
    @RequestMapping("/forgetPsw")
    @ResponseBody
    public String forgetPsw(HttpServletRequest request,HttpServletResponse response,
    		String email){
    	Pageable<User> pgl = new Pageable<User>();
    	pgl.setSearchProperty("email");
		pgl.setSearchValue(email);
		Page<User> page = userDao.findPage(pgl);
		List<User> users = page.getRows();
		if(users.size()>0){
			User user=users.get(0);
			String newPsw=RandomStr.getRandomString(6);
			user.setPasswd(newPsw);
			userService.update(user);
			mailUtil.sendMessage(email,newPsw,1);
			return JSON.toJSONString(user);
		}
    	return null;
    }
	@RequestMapping("/logout")
	public void logout(HttpSession session, HttpServletResponse response) {
		session.removeAttribute("user");
		try {
			response.sendRedirect("/rcampus/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}