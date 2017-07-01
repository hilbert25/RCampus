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


}
