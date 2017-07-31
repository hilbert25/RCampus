package org.sunhp.rcampus.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import org.sunhp.rcampus.util.JspToHtml;
import org.sunhp.rcampus.vo.ExamResult;
import org.sunhp.rcampus.vo.OcpuResult;
import org.sunhp.rcampus.vo.SimpleCourse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	public String courseIntro(ModelMap map) {
		return "courseIntro";
	}

	/**
	 * 获得某一课程内容,这个是用户做题用的
	 * 
	 * @param request
	 * @param response
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value = "/getCourseById")
	public String getCourseDetail(HttpServletRequest request,
			HttpServletResponse response, Long courseId) {
		// 下边是用的静态页
		/*
		 * String page = courseId + ".html"; try { response.sendRedirect(page);
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
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
		Long finishCourse = 0L;
		Pageable<Progress> progressPageable = new Pageable<Progress>();
		progressPageable.setSearchProperty("user_id");
		progressPageable.setSearchValue(String.valueOf(userId));
		progressPageable.setPageNumber(Integer.MAX_VALUE);
		Page<Progress> progressPage = progressService
				.findByPager(progressPageable);
		List<Progress> progressList = progressPage.getRows();
		for (Progress progress : progressList) {
			if (progress.getPoint() == 100)
				finishCourse = progress.getCourseId();
		}
		Long maxId = 0L;
		int index = 0;
		for (int i = 0; i < progressList.size(); i++) {
			if (progressList.get(i).getCourseId() > maxId) {
				maxId = progressList.get(i).getCourseId();
				index = i;
			}
		}
		Course latestCourse = courseService.get(maxId);// progress表的最后一个课程
		System.out.println("ch" + latestCourse.getChapter() + " "
				+ latestCourse.getCourseId());
		Pageable<Course> coursePageable = new Pageable<Course>();
		coursePageable.setSearchProperty("chapter");
		coursePageable
				.setSearchValue(String.valueOf(latestCourse.getChapter()));
		coursePageable.setPageSize(Integer.MAX_VALUE);
		Page<Course> coursePage = courseService.findByPager(coursePageable);
		List<Course> courseList = coursePage.getRows();// 该chapter下全部课程
		if (courseList.get(courseList.size() - 1).getCourseId() == latestCourse
				.getCourseId() && progressList.get(index).getPoint() == 100) {// 最后一条记录是这一chapter的最后一节而且是100分
			request.setAttribute("finishChapter", latestCourse.getChapter());
		} else {// 否则就是前一章
			request.setAttribute("finishChapter", latestCourse.getChapter() - 1);
		}
		request.setAttribute("chapterList", chapterList);
		request.setAttribute("course", course);
		request.setAttribute("finishCourse", finishCourse);
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
		code = code.replace(" ", "");
		ExamResult examResult;
		OcpuResult ocpuResult;
		// 判断用户输入是否符合要求
		// Course course = courseService.get(courseId);
		// Judge judge = new Judge();
		// judge.setExamId(courseId);
		String preJudge = "";
		String[] codeArray = code.split("\n");
		Pageable<Judge> judgePageable = new Pageable<Judge>();
		judgePageable.setSearchProperty("exam_id");
		judgePageable.setSearchValue(String.valueOf(courseId));
		judgePageable.setPageSize(Integer.MAX_VALUE);
		List<Judge> judgeList = judgeService.findByPager(judgePageable)
				.getRows();// 获取当前course的judge
		int rightCount = 0;// 记录通过的test cast的数目
		for (int j = 0; j < judgeList.size(); j++) {
			boolean find = false;
			int i = 0;
			for (i = 0; i < codeArray.length; i++) {
				if (codeArray[i].equals(judgeList.get(j).getJudgeItem())) {
					rightCount++;
					find = true;
					break;
				}
			}
			if (!find) {// 没找到e
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

	@RequestMapping("generate")
	@ResponseBody
	public String generateCourseContent(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("generate");
		Pageable<Course> pageable = new Pageable<Course>();
		pageable.setOrderProperty("course_order");
		Page<Course> page = courseService.findByPager(pageable);
		List<Course> courseList = page.getRows();
		Pageable<Chapter> chapterPg = new Pageable<Chapter>();
		chapterPg.setOrderProperty("chapter_order");
		Page<Chapter> chapterPage = chapterService.findByPager(chapterPg);
		List<Chapter> chapterList = chapterPage.getRows();
		Map<Chapter, List<Course>> ccMap = new HashMap<Chapter, List<Course>>();
		int loop1 = 0;
		int loop2 = 0;
		while (loop1 < chapterList.size()) {
			loop1++;
			List<Course> cList = new ArrayList<Course>();
			while (loop2 < courseList.size()) {
				if (loop2 < (courseList.size() - 1)
						&& courseList.get(loop2).getChapter() != courseList
								.get(loop2 + 1).getChapter()) {
					cList.add(courseList.get(loop2));
					loop2++;
					break;
				}
				cList.add(courseList.get(loop2));
				loop2++;
			}
			ccMap.put(chapterList.get(loop1 - 1), cList);
		}
		request.setAttribute("chapterList", chapterList);
		request.setAttribute("ccMap", ccMap);
		String jspPath = "../WEB-INF/jsp/courseContent.jsp";
		String target = request.getServletContext().getRealPath("\\")
				+ "page\\courseContent.html";
		String result = JspToHtml.jsp2Html(jspPath, request, response, target);
		return JSON.toJSONString(result);
	}

	@RequestMapping("add")
	@ResponseBody
	public String addCourse(HttpServletRequest request,
			HttpServletResponse response, String courseName, String courseNote,
			String courseOrder, String belongChapter) {
		Course course = new Course();
		course.setChapter(Long.valueOf(belongChapter));
		course.setCourseName(courseName);
		course.setCourseNote(courseNote);
		course.setCourseOrder(Integer.valueOf(courseOrder));
		Pageable<Course> tempCoursePageable = new Pageable<Course>();
		tempCoursePageable.setSearchProperty("course_order");
		tempCoursePageable.setSearchValue(courseOrder);
		Page<Course> tempCoursePage = courseService
				.findByPager(tempCoursePageable);
		List<Course> courseList = tempCoursePage.getRows();
		if (courseList.size() > 0
				&& courseList.get(0).getChapter() == Long
						.valueOf(belongChapter)) {// 存在重复章节号
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", "已经存在第" + courseOrder + "节,请勿重复添加");
			return JSON.toJSONString(jsonObject);
		}
		courseService.save(course);
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
		String jspPath = "..\\WEB-INF\\jsp\\course_detail.jsp";
		String target = request.getServletContext().getRealPath("\\")
				+ "page\\courses\\" + course.getCourseId() + ".html";
		JspToHtml.jsp2Html(jspPath, request, response, target);
		// try {
		// response.sendRedirect("../chapter/chapter_manage");
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return JSON.toJSONString(course);
	}

	@ResponseBody
	@RequestMapping("delete")
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

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getNextCourse")
	public String getNextCourse(HttpServletRequest request,
			HttpServletResponse response, Long courseId) {
		List<Course> courseList = courseService.getAll(new Course());
		int next = 0;
		int i = 0;
		while (courseList.get(i).getCourseId() != courseId) {
			i++;
		}
		next = Math.min(i + 1, courseList.size() - 1);
		return JSON.toJSONString(courseService.get(courseList.get(next)
				.getCourseId()));
	}

	/**
	 * 这门课是否完成
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("isFinish")
	public String isFinish(HttpServletRequest request,
			HttpServletResponse response, Long courseId) {
		Pageable<Progress> progressAble = new Pageable<Progress>();
		progressAble.setSearchProperty("user_id");
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		progressAble.setSearchValue(String.valueOf(userId));
		Page<Progress> progressPage = progressService.findByPager(progressAble);
		List<Progress> progressList = progressPage.getRows();
		JSONObject jsonObject = new JSONObject();

		for (int i = 0; i < progressList.size(); i++) {
			if (progressList.get(i).getCourseId() == courseId) {
				if (progressList.get(i).getPoint() == 100) {
					jsonObject.put("result", true);
					return JSON.toJSONString(jsonObject);
				} else {
					jsonObject.put("result", false);
					return JSON.toJSONString(jsonObject);
				}
			}
		}
		jsonObject.put("result", false);
		return JSON.toJSONString(jsonObject);
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
		jsonObject.put("rate", (int) rate);
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
		return progressList.get(progressList.size() - 1).getCourseId();
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCourseCount")
	public String getCourseCount(HttpServletRequest request,
			HttpServletResponse response) {
		Pageable<Course> coursePageable = new Pageable<Course>();
		coursePageable.setPageNumber(Integer.MAX_VALUE);
		JSONObject jsonObject = new JSONObject();
		System.out.println(courseService.count(coursePageable));
		jsonObject.put("courseCount", courseService.count(coursePageable));
		return JSON.toJSONString(jsonObject);
	}

	@RequestMapping("getCourseList")
	public String getCourseList(HttpServletRequest request,
			HttpServletResponse response) {
		String chapterId = request.getParameter("chapterId");
		Pageable<Course> coursePageable = new Pageable<Course>();
		coursePageable.setSearchProperty("chapter");
		coursePageable.setSearchValue(chapterId);
		coursePageable.setPageNumber(Integer.MAX_VALUE);
		request.setAttribute("chapter",
				chapterService.get(Long.valueOf(chapterId)));
		request.setAttribute("courseList",
				courseService.findByPager(coursePageable).getRows());
		return "chapter_detail_manage";
	}

	/**
	 * 这个是后台管理员用的
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getCourseInfo")
	@ResponseBody
	public String getCourseInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Course course = courseService.get(Long.valueOf(request
				.getParameter("courseId")));
		return JSON.toJSONString(course);
	}

	/**
	 * 这个是后台管理员用的
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("modifyCourseInfo")
	@ResponseBody
	public String modifyCourseInfo(HttpServletRequest request,
			HttpServletResponse response) {
		int courseOrder = Integer.valueOf(request.getParameter("courseOrder"));
		long courseId = Long.valueOf(request.getParameter("courseId"));
		String courseName = request.getParameter("courseName");
		String courseNote = request.getParameter("courseNote");
		Pageable<Course> coursePageable = new Pageable<Course>();
		coursePageable.setSearchProperty("course_order");
		coursePageable.setSearchValue(String.valueOf(courseOrder));
		Course tempCourse = courseService.findByPager(coursePageable).getRows()
				.get(0);
		if (tempCourse.getCourseOrder() == courseOrder
				&& tempCourse.getCourseId() != courseId) {// 存在order相同
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", "已经存在第" + courseOrder + "节,请勿重复添加");
			return JSON.toJSONString(jsonObject);
		}
		System.out.println("courseName"+courseName);
		System.out.println("courseNote"+courseNote);
		Course course = courseService.get(courseId);
		course.setCourseName(courseName);
		course.setCourseNote(courseNote);
		course.setCourseOrder(courseOrder);
		courseService.update(course);
		return JSON.toJSONString(course);
	}
}