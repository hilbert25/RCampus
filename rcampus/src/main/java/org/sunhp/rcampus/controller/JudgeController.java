package org.sunhp.rcampus.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sunhp.rcampus.bean.Course;
import org.sunhp.rcampus.bean.Judge;
import org.sunhp.rcampus.components.Pageable;
import org.sunhp.rcampus.service.ChapterService;
import org.sunhp.rcampus.service.CourseService;
import org.sunhp.rcampus.service.JudgeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/judge")
public class JudgeController {
	@Autowired
	JudgeService judgeService;
	@Autowired
	CourseService courseService;
	@Autowired
	ChapterService chapterService;

	@ResponseBody
	@RequestMapping("add.do")
	public String addJudge(HttpServletRequest request,
			HttpServletResponse response) {
		String examPage = request.getParameter("examPage");
		String judgeItem = request.getParameter("judgeItem");
		String judgeTips = request.getParameter("judgeTips");
		if (examPage.length() == 0 || judgeItem.length() == 0
				|| judgeTips.length() == 0) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", "不能有空缺值");
			return JSON.toJSONString(jsonObject);
		}
		Long courseId = Long.valueOf(request.getParameter("courseId"));
		Course course = courseService.get(courseId);

		if (examPage.length() < 1 || examPage.charAt(0) != '#')
			examPage = "#" + examPage;
		String oldExamPage = course.getExamPage();
		examPage = oldExamPage + "\n" + examPage;
		course.setExamPage(examPage);
		courseService.update(course);
		Judge judge = new Judge();
		judge.setExamId(courseId);
		judge.setJudgeItem(request.getParameter("judgeItem"));
		judge.setJudgeTips(request.getParameter("judgeTips"));
		judgeService.save(judge);
		return JSON.toJSONString(null);
	}

	@ResponseBody
	@RequestMapping("delete.do")
	public String addCourse(HttpServletRequest request,
			HttpServletResponse response, Long judgeId) {
		judgeService.delete(judgeId);
		return JSON.toJSONString("deleted");
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getJudgeCount")
	public String getJudgeCount(HttpServletRequest request,
			HttpServletResponse response) {
		Pageable<Judge> judgePageable = new Pageable<Judge>();
		judgePageable.setPageNumber(Integer.MAX_VALUE);
		JSONObject jsonObject = new JSONObject();
		System.out.println(judgeService.count(judgePageable));
		jsonObject.put("judgeCount", judgeService.count(judgePageable));
		return JSON.toJSONString(jsonObject);
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getJudgeList")
	public String getJudgeList(HttpServletRequest request,
			HttpServletResponse response) {
		String courseId = request.getParameter("courseId");
		Pageable<Judge> judgePageable = new Pageable<Judge>();
		judgePageable.setSearchProperty("exam_id");
		judgePageable.setSearchValue(courseId);
		judgePageable.setPageNumber(Integer.MAX_VALUE);
		List<Judge> judgeList = judgeService.findByPager(judgePageable)
				.getRows();
		String[] examPageArr = courseService.get(Long.valueOf(courseId))
				.getExamPage().split("\n");
		List<String> examPageList = new ArrayList<String>();
		for (String s : examPageArr) {
			if (!s.equals(""))
				examPageList.add(s);
		}
		Course course = courseService.get(Long.valueOf(courseId));
		request.setAttribute("judgeList", judgeList);
		request.setAttribute("examPageList", examPageList);
		request.setAttribute("course", course);
		request.setAttribute("chapter",
				chapterService.get(Long.valueOf(course.getChapter())));
		return "course_detail_manage";
	}

}
