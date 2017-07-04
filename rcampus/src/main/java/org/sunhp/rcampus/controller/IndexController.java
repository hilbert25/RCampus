package org.sunhp.rcampus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.sunhp.rcampus.service.*;

import java.io.IOException;

@Controller
public class IndexController {
	@Autowired
	ApiService apiService;
	@Autowired
	MailUtil mailUtil;
	
	@RequestMapping("/index.do")
	public ModelAndView index() {
		return new ModelAndView("index");
	}
	@ResponseBody
	@RequestMapping("/test.do")
	public String test() throws IOException {
		return apiService.doGet("http://www.baidu.com");
//		List<Store> list = new ArrayList<Store>();
//		list = storeService.getAll(new Store());
//		return "总共有：" + list.size();
	}
   @RequestMapping("/home")
   public String home(){
	   return "home";
   }
   @ResponseBody
	@RequestMapping("signin")
	public String signin(HttpServletRequest request, HttpServletResponse response, Long courseId) {
		String email=request.getParameter("email");
		String link="www.baidu.com";
		mailUtil.sendMail(email,link);
		Result result=new Result();
		result.setStatus("success");
		result.setFlag(1);
		return JSON.toJSONString(result);
	}

}
