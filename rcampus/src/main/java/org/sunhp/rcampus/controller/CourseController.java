package org.sunhp.rcampus.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sunhp.rcampus.bean.Chapter;
import org.sunhp.rcampus.bean.Course;
import org.sunhp.rcampus.bean.HttpResult;
import org.sunhp.rcampus.bean.Judge;
import org.sunhp.rcampus.bean.Progress;
import org.sunhp.rcampus.bean.User;
import org.sunhp.rcampus.components.Constants;
import org.sunhp.rcampus.components.Page;
import org.sunhp.rcampus.components.Pageable;
import org.sunhp.rcampus.service.ApiService;
import org.sunhp.rcampus.service.ChapterService;
import org.sunhp.rcampus.service.CourseService;
import org.sunhp.rcampus.service.JudgeService;
import org.sunhp.rcampus.service.ProgressService;
import org.sunhp.rcampus.service.UserService;
import org.sunhp.rcampus.util.FileUtils;
import org.sunhp.rcampus.vo.ExamResult;
import org.sunhp.rcampus.vo.OcpuResult;
import org.sunhp.rcampus.vo.SimpleCourse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("course")
public class CourseController {

	@Autowired
	CourseService courseService;

	@Autowired
	ApiService apiService;

	@Autowired
	JudgeService judgeService;
	@Autowired
	ChapterService chapterService;
	@Autowired
	UserService userService;
	@Autowired
	ProgressService progressService;

	/**
	 * 获得某章节的课程列表
	 * 
	 * @param request
	 * @param response
	 * @param chapterId
	 * @return
	 */
	// @ResponseBody
	@RequestMapping("/")
	public String getCourseList(HttpServletRequest request,
			HttpServletResponse response, Long chapterId) {
		Course course = new Course();
		course.setChapter(chapterId);
		List<Course> list = courseService.getAll(course);
		List<SimpleCourse> sList = new ArrayList<>();
		for (Course co : list) {
			SimpleCourse sc = new SimpleCourse(co, 0.0);
			sList.add(sc);
		}
		// return JSON.toJSONString(sList);
		return "courses";
	}

	@RequestMapping("/test")
	public String test(HttpServletRequest request) {
		if (request.getParameterMap() == null) {
			request.setAttribute("chapterName", "chapterOne");
			request.setAttribute("courseName", "courseOne");
		}
		String chapterName = request.getParameter("chapterName");
		String courseName = request.getParameter("courseName");
		request.setAttribute("chapterName", chapterName);
		request.setAttribute("courseName", courseName);
		return "test";
	}

	@RequestMapping("/courseIntro")
	public String courseIntro() {
		return "courseIntro";
	}

	/**
	 * 获得某一课程内容
	 * 
	 * @param request
	 * @param response
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value = "/getCourseById")
	public String getCourseDetail(HttpServletRequest request,
			HttpServletResponse response, Long courseId) {
		Course course = courseService.get(courseId);
		Pageable<Chapter> chapterPageable = new Pageable<Chapter>();
		chapterPageable.setPageSize(Integer.MAX_VALUE);
		Page<Chapter> chapterPage = chapterService.findByPager(chapterPageable);
		List<Chapter> chapterList = chapterPage.getRows();
		for (Chapter chapter : chapterList) {
			Long chapterId = chapter.getChapterId();
			Pageable<Course> coursePageable = new Pageable<Course>();
			coursePageable.setPageSize(Integer.MAX_VALUE);
			coursePageable.setSearchProperty("chapter");
			coursePageable.setSearchValue(String.valueOf(chapterId));
			chapter.setCourseList(courseService.findByPager(coursePageable)
					.getRows());
		}
		request.setAttribute("chapterList", chapterList);
		request.setAttribute("course", course);
		return "course_detail";
	}

	/**
	 * 提交课程作业
	 * 
	 * @param request
	 * @param response
	 * @param courseId
	 * @param content
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("submit.do")
	public String getCourseSubmit(HttpServletRequest request,
			HttpServletResponse response, Long courseId, String code)
			throws IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		code = code.replaceAll("#[\\s\\S]*?\n", "");
		code.replaceAll(" ", "");
		System.out.println(code);
		ExamResult examResult;
		OcpuResult ocpuResult;
		// 判断用户输入是否符合要求
		// Course course = courseService.get(courseId);
		Judge judge = new Judge();
		judge.setExamId(courseId);
		String preJudge = "";
		String[] codeArray = code.split("\n");
		List<Judge> judgeList = judgeService.getAll(judge);
		int rightCount = 0;// 记录通过的test cast的数目
		for (int j = 0; j < judgeList.size(); j++) {
			int i = 0;
			for (i = 0; i < codeArray.length; i++) {
				if (codeArray[i].equals(judgeList.get(j).getJudgeItem())) {
					rightCount++;
					break;
				}
			}
			if (i == codeArray.length) {// 没有第j个judge
				preJudge += "you should input "
						+ judgeList.get(j).getJudgeItem() + " in the "
						+ String.valueOf(j + 1) + "th test case \n";
			}
		}

		examResult = judgeService.judegeInput(judgeList, code);
		Pageable<Progress> progressPageable = new Pageable<Progress>();
		progressPageable.setPageSize(Integer.MAX_VALUE);
		progressPageable.setSearchProperty("user_id");
		progressPageable.setSearchValue(String.valueOf(userId));
		Page<Progress> progressPage = progressService
				.findByPager(progressPageable);
		List<Progress> progressList = progressPage.getRows();
		int i = 0;
		int caseCount = judgeList.size();
		for (i = 0; i < progressList.size(); i++) {
			if (progressList.get(i).getCourseId() == courseId) {// 找到记录
				Progress progress = progressList.get(i);
				progress.setPoint(100 * (double) rightCount / caseCount);
				progress.setUpdateTime(new Date());
				progressService.update(progress);
				break;
			}
		}
		if (i == progressList.size()) {// 没找到记录
			Progress progress = new Progress();
			progress.setCourseId(courseId);
			progress.setPoint(100 * (double) rightCount / caseCount);
			progress.setUserId(userId);
			progress.setCreateTime(new Date());
			progress.setUpdateTime(new Date());
			progressService.save(progress);
		}
		if (!preJudge.equals("")) {
			ocpuResult = new OcpuResult(code, preJudge);
			examResult.setStatus(false);
			// examResult.setOcpuResult(ocpuResult);
			examResult.setOcpuJSON(JSON.toJSONString(ocpuResult));
			return JSON.toJSONString(examResult);
		}
		// 将用户输入存入temp.R文件中
		String excuteFilePath = FileUtils
				.saveStrToFile(request, "temp.R", code);
		// 将temp.R上传到OpenCPU
		String url = Constants.OPENCPU_HOST + "/" + Constants.OPENCPU_NAME
				+ "/library/utils/R/file.edit";
		HttpResult result = apiService.doPostFile(url, excuteFilePath);
		if (result.getCode() != 201) {
			ocpuResult = new OcpuResult(code, "R文件上传出错");
			examResult.setStatus(false);
			// examResult.setOcpuResult(ocpuResult);
			examResult.setOcpuJSON(JSON.toJSONString(ocpuResult));
			return JSON.toJSONString(examResult);
		}
		ocpuResult = new OcpuResult(result.getData());
		if (ocpuResult.getSessionID() == null) {
			ocpuResult = new OcpuResult(code, "R文件上传后openCPU结果解析出错");
			examResult.setStatus(false);
			// examResult.setOcpuResult(ocpuResult);
			examResult.setOcpuJSON(JSON.toJSONString(ocpuResult));
			return JSON.toJSONString(examResult);
		}
		// OpenCPU上执行emp.R
		String url2 = Constants.OPENCPU_HOST + "/ocpu/tmp/"
				+ ocpuResult.getSessionID() + "/files/temp.R";
		result = apiService.doPost(url2);
		// 编译出错
		if (result.getCode() == 400) {
			ocpuResult = new OcpuResult(code, result.getData());
			examResult.setStatus(false);
			// examResult.setOcpuResult(ocpuResult);
			examResult.setOcpuJSON(JSON.toJSONString(ocpuResult));
			return JSON.toJSONString(examResult);
		}
		if (result.getCode() != 201 && result.getCode() != 200) {
			ocpuResult = new OcpuResult(code, "系统内部错误:" + result.getCode());
			examResult.setStatus(false);
			// examResult.setOcpuResult(ocpuResult);
			examResult.setOcpuJSON(JSON.toJSONString(ocpuResult));
			return JSON.toJSONString(examResult);
		}
		// 正常情况
		ocpuResult = new OcpuResult(result.getData());
		if (ocpuResult.getSessionID() == null) {
			ocpuResult = new OcpuResult(code, "R文件执行后openCPU结果解析出错结果解析出错");
			examResult.setStatus(false);
			// examResult.setOcpuResult(ocpuResult);
			examResult.setOcpuJSON(JSON.toJSONString(ocpuResult));
			return JSON.toJSONString(examResult);
		}
		// 获取执行结果数据
		String source = apiService.doGet(Constants.OPENCPU_HOST + "/ocpu/tmp/"
				+ ocpuResult.getSessionID() + "/source");
		String console = apiService.doGet(Constants.OPENCPU_HOST + "/ocpu/tmp/"
				+ ocpuResult.getSessionID() + "/console");
		String info = apiService.doGet(Constants.OPENCPU_HOST + "/ocpu/tmp/"
				+ ocpuResult.getSessionID() + "/info");
		ocpuResult.setSource(source);
		ocpuResult.setInfo(info);
		ocpuResult.setConsole(console);
		examResult.setOcpuJSON(JSON.toJSONString(ocpuResult));
		// examResult.setOcpuResult(ocpuResult);
		examResult.setStatus(true);

		return JSON.toJSONString(examResult);
	}

	@RequestMapping("course_manage")
	public String addCourse(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		User user = userService.get(userId);
		request.setAttribute("user", user);
		request.setAttribute("course",
				courseService.get(request.getParameter("courseId")));
		return "course_manage";
	}

	@ResponseBody
	@RequestMapping("add.do")
	public String addCourse(HttpServletRequest request,
			HttpServletResponse response, Course course) {
		courseService.save(course);
		return JSON.toJSONString(course);
	}

	@ResponseBody
	@RequestMapping("delete.do")
	public String addCourse(HttpServletRequest request,
			HttpServletResponse response, Long courseId) {
		courseService.delete(courseId);
		return JSON.toJSONString("deleted");
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getContinue")
	public String getContinue(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		Long courseId = getUserLatestCourse(userId);
		Course course = courseService.get(courseId);
		return JSON.toJSONString(course);
	}

	@ResponseBody
	@RequestMapping("getCompleteRate")
	public String getCompleteRate(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		Pageable<Progress> progressPageable = new Pageable<Progress>();
		progressPageable.setSearchProperty("user_id");
		progressPageable.setSearchValue(String.valueOf(userId));
		progressPageable.setPageNumber(Integer.MAX_VALUE);
		Page<Progress> progressPage = progressService
				.findByPager(progressPageable);
		List<Progress> progressList = progressPage.getRows();
		int complete = 0;
		long total = 0;
		if (progressList.get(progressList.size() - 1).getPoint() == 100) {
			complete = progressList.size();
		} else {
			complete = Math.min(progressList.size(), 0);
		}
		Pageable<Course> coursePageable = new Pageable<Course>();
		coursePageable.setPageSize(Integer.MAX_VALUE);
		total = courseService.count(coursePageable);
		double rate = ((double) 100 * complete / total);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rate", (int)rate);
		return JSON.toJSONString(jsonObject);
	}

	/**
	 * 获取当前用户最新课程
	 * 
	 * @param userId
	 * @return
	 */
	public Long getUserLatestCourse(Long userId) {
		Pageable<Progress> progressPageable = new Pageable<Progress>();
		progressPageable.setSearchProperty("user_id");
		progressPageable.setSearchValue(String.valueOf(userId));
		progressPageable.setPageNumber(Integer.MAX_VALUE);
		Page<Progress> progressPage = progressService
				.findByPager(progressPageable);
		List<Progress> progressList = progressPage.getRows();
		Long maxId = 0L;
		for (int i = 0; i < progressList.size(); i++) {
			if (progressList.get(i).getCourseId() > maxId) {
				maxId = progressList.get(i).getCourseId();
			}
		}
		return maxId;
	}
}