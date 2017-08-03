package org.sunhp.rcampus.controller;

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

	/**
	 * 增加一条judge记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add")
	public String addJudge(HttpServletRequest request,
			HttpServletResponse response) {
		String judgeAnswer = request.getParameter("judgeAnswer");
		String judgeProblem = request.getParameter("judgeProblem");
		String judgeTips = request.getParameter("judgeTips");
		long courseId = Long.valueOf(request.getParameter("courseId"));
		if (judgeProblem.length() < 1 || judgeProblem.charAt(0) != '#')
			judgeProblem = "#" + judgeProblem;
		Judge judge = new Judge();
		judge.setCourseId(courseId);
		judge.setJudgeAnswer(judgeAnswer);
		judge.setJudgeProblem(judgeProblem);
		judge.setJudgeTips(judgeTips);
		judgeService.save(judge);
		return JSON.toJSONString("add");
	}

	/**
	 * 删除一条记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public String deleteJudge(HttpServletRequest request,
			HttpServletResponse response) {
		judgeService.delete(Long.valueOf(request.getParameter("judgeId")));
		return JSON.toJSONString("deleted");
	}

	/**
	 * 查找一条judge记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getJudgeById")
	public String getJudge(HttpServletRequest request,
			HttpServletResponse response) {
		return JSON.toJSONString(judgeService.get(Long.valueOf(request
				.getParameter("judgeId"))));
	}

	/**
	 * 获取一节课的全部judge
	 * 用在courseDeatil 页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getJudgeList")
	public String getJudgeList(HttpServletRequest request,
			HttpServletResponse response) {
		String courseId = request.getParameter("courseId");
		Pageable<Judge> judgePageable = new Pageable<Judge>();
		judgePageable.setSearchProperty("course_id");
		judgePageable.setSearchValue(courseId);
		judgePageable.setPageNumber(Integer.MAX_VALUE);
		List<Judge> judgeList = judgeService.findByPager(judgePageable)
				.getRows();
		Course course = courseService.get(Long.valueOf(courseId));
		request.setAttribute("judgeList", judgeList);
		request.setAttribute("course", course);
		request.setAttribute("chapter",
				chapterService.get(Long.valueOf(course.getChapter())));
		return "course_detail_manage";
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
	@ResponseBody
	@RequestMapping("modifyJudge")
	public String modifyJudge(HttpServletRequest request,
			HttpServletResponse response) {
		String judgeAnswer = request.getParameter("judgeAnswer");
		String judgeProblem = request.getParameter("judgeProblem");
		String judgeTips = request.getParameter("judgeTips");
		long judgeId = Long.valueOf(request.getParameter("judgeId"));
		Judge judge = judgeService.get(judgeId);
		judge.setJudgeAnswer(judgeAnswer);
		judge.setJudgeProblem(judgeProblem);
		judge.setJudgeTips(judgeTips);
		judgeService.update(judge);
		return JSON.toJSONString(null);
	}

}
