package org.sunhp.rcampus.controller;

import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sunhp.rcampus.bean.Chapter;
import org.sunhp.rcampus.bean.Course;
import org.sunhp.rcampus.bean.HttpResult;
import org.sunhp.rcampus.bean.Judge;
import org.sunhp.rcampus.bean.User;
import org.sunhp.rcampus.components.Constants;
import org.sunhp.rcampus.components.Page;
import org.sunhp.rcampus.components.Pageable;
import org.sunhp.rcampus.service.ApiService;
import org.sunhp.rcampus.service.ChapterService;
import org.sunhp.rcampus.service.CourseService;
import org.sunhp.rcampus.service.JudgeService;
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
	 * 获得某一课程内容
	 * 
	 * @param request
	 * @param response
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value = "/getCourseDetail")
	public void getCourseDetail(HttpServletRequest request,
			HttpServletResponse response, Long courseId) {
		// List<Chapter> chapterList = new ArrayList<Chapter>();// 获取这门课的全部章节
		// for (int i = 0; i < chapterList.size(); i++) {
		//
		// }
		String page=courseId+".html";
		try {
			response.sendRedirect(page);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		ExamResult examResult;
		OcpuResult ocpuResult;
		// 判断用户输入是否符合要求
		// Course course = courseService.get(courseId);
		Judge judge = new Judge();
		judge.setExamId(courseId);
		List<Judge> judgeList = judgeService.getAll(judge);
		for (Judge j : judgeList)
			System.out.println(j.getJudgeItem());
		examResult = judgeService.judegeInput(judgeList, code);

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
			examResult.setOcpuResult(ocpuResult);
			return JSON.toJSONString(examResult);
		}
		ocpuResult = new OcpuResult(result.getData());
		if (ocpuResult.getSessionID() == null) {
			ocpuResult = new OcpuResult(code, "R文件上传后openCPU结果解析出错");
			examResult.setStatus(false);
			examResult.setOcpuResult(ocpuResult);
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
			examResult.setOcpuResult(ocpuResult);
			return JSON.toJSONString(examResult);
		}
		if (result.getCode() != 201 && result.getCode() != 200) {
			ocpuResult = new OcpuResult(code, "系统内部错误:" + result.getCode());
			examResult.setStatus(false);
			examResult.setOcpuResult(ocpuResult);
			return JSON.toJSONString(examResult);
		}
		// 正常情况
		ocpuResult = new OcpuResult(result.getData());
		if (ocpuResult.getSessionID() == null) {
			ocpuResult = new OcpuResult(code, "R文件执行后openCPU结果解析出错结果解析出错");
			examResult.setStatus(false);
			examResult.setOcpuResult(ocpuResult);
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
	public String generateCourseContent(HttpServletRequest request,HttpServletResponse response){
		Pageable<Course> pageable=new Pageable<Course>();
		pageable.setOrderProperty("course_order");
		Page<Course> page=courseService.findByPager(pageable);
		List<Course> courseList=page.getRows();
		Pageable<Chapter> chapterPg=new Pageable<Chapter>();
		chapterPg.setOrderProperty("chapter_order");
		Page<Chapter> chapterPage=chapterService.findByPager(chapterPg);
		List<Chapter> chapterList=chapterPage.getRows();
		Map<Chapter,List<Course>> ccMap=new HashMap<Chapter,List<Course>>();
		int loop1=0;
		int loop2=0;
		while(loop1<chapterList.size()){
			loop1++;
			List<Course> cList=new ArrayList<Course>();
			while(loop2<courseList.size()){
				if(loop2<(courseList.size()-1)&&courseList.get(loop2).getChapter()!=courseList.get(loop2+1).getChapter()){
					cList.add(courseList.get(loop2));
					loop2++;
					break;
				}
			    cList.add(courseList.get(loop2));
				loop2++;
			}
			ccMap.put(chapterList.get(loop1-1),cList);
		}
		request.setAttribute("chapterList",chapterList);
		request.setAttribute("ccMap",ccMap);
		String jspPath="../WEB-INF/jsp/courseContent.jsp";
		String target=request.getServletContext().getRealPath("\\")+"page\\courseContent.html";
		String result=JspToHtml.jsp2Html(jspPath, request, response,target);
		return JSON.toJSONString(result);
	}
	@RequestMapping("add")
	public void addCourse(HttpServletRequest request,
			HttpServletResponse response, Course course) {
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
		String jspPath="..\\WEB-INF\\jsp\\course_detail.jsp";
		String target=request.getServletContext().getRealPath("\\")+"page\\courses\\"+course.getCourseId()+".html";
		JspToHtml.jsp2Html(jspPath, request, response, target);
		try {
			response.sendRedirect("../chapter/chapter_manage");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping("delete.do")
	public String addCourse(HttpServletRequest request,
			HttpServletResponse response, Long courseId) {
		courseService.delete(courseId);
		return JSON.toJSONString("deleted");
	}
}