package org.sunhp.rcampus.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sunhp.rcampus.bean.Judge;
import org.sunhp.rcampus.service.JudgeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/judge")
public class JudgeController {
	@Autowired
	JudgeService judgeService;
	@ResponseBody
	@RequestMapping("add.do")
	public String addJudge(HttpServletRequest request, HttpServletResponse response, Judge judge) {
		judgeService.save(judge);
		return JSON.toJSONString(judge);
	}
	@ResponseBody
	@RequestMapping("delete.do")
	public String addCourse(HttpServletRequest request, HttpServletResponse response,Long judgeId) {
		judgeService.delete(judgeId);
		return JSON.toJSONString("deleted");
	}
}
