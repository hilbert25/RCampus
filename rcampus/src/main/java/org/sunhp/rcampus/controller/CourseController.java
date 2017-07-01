package org.sunhp.rcampus.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sunhp.rcampus.bean.Course;
import org.sunhp.rcampus.bean.HttpResult;
import org.sunhp.rcampus.bean.Judge;
import org.sunhp.rcampus.components.Constants;
import org.sunhp.rcampus.service.ApiService;
import org.sunhp.rcampus.service.CourseService;
import org.sunhp.rcampus.service.JudgeService;
import org.sunhp.rcampus.util.FileUtils;
import org.sunhp.rcampus.vo.ExamResult;
import org.sunhp.rcampus.vo.OcpuResult;
import org.sunhp.rcampus.vo.SimpleCourse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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

	/**
	 * 获得某章节的课程列表
	 * @param request
	 * @param response
	 * @param chapterId
     * @return
     */
	@ResponseBody
	@RequestMapping("list.do")
	public String getCourseList(HttpServletRequest request, HttpServletResponse response, Long chapterId) {
		Course course = new Course();
		course.setChapter(chapterId);
		List<Course>  list  = courseService.getAll(course);
		List<SimpleCourse> sList = new ArrayList<>();
		for(Course co:list){
			SimpleCourse sc = new SimpleCourse(co,0.0);
			sList.add(sc);
		}
		return JSON.toJSONString(sList);
	}

	/**
	 * 获得某一课程内容
	 * @param request
	 * @param response
	 * @param courseId
     * @return
     */
	@ResponseBody
	@RequestMapping("detail.do")
	public String getCourseDetail(HttpServletRequest request, HttpServletResponse response, Long courseId) {
		Course course = courseService.get(courseId);
		if(course!=null){
			return JSON.toJSONString(course);
		}
		return null;
	}
	/**
	 * 提交课程作业
	 * @param request
	 * @param response
	 * @param courseId
	 * @param content
	 * @return
     * @throws IOException
     */
	@ResponseBody
	@RequestMapping("submit.do")
	public String getCourseSubmit(HttpServletRequest request, HttpServletResponse response, Long courseId,String content)throws IOException {
		ExamResult examResult;
		OcpuResult ocpuResult;
		//判断用户输入是否符合要求
		Course course = courseService.get(courseId);
		Judge judge = new Judge();
		judge.setExamId(courseId);
		List<Judge> judgeList = judgeService.getAll(judge);
		examResult = judgeService.judegeInput(judgeList,content);

		//将用户输入存入temp.R文件中
		String excuteFilePath = FileUtils.saveStrToFile(request,"temp.R",content);
		//将temp.R上传到OpenCPU
		String url = Constants.OPENCPU_HOST+"/"+Constants.OPENCPU_NAME+"/library/utils/R/file.edit";
		HttpResult result = apiService.doPostFile(url,excuteFilePath);
		if(result.getCode()!=201) {
			ocpuResult = new OcpuResult(content,"R文件上传出错");
			examResult.setStatus(false);
			examResult.setOcpuResult(ocpuResult);
			return JSON.toJSONString(examResult);
		}
		ocpuResult = new OcpuResult(result.getData());
		if(ocpuResult.getSessionID()==null){
			ocpuResult = new OcpuResult(content,"R文件上传后openCPU结果解析出错");
			examResult.setStatus(false);
			examResult.setOcpuResult(ocpuResult);
			return JSON.toJSONString(examResult);
		}
		//OpenCPU上执行emp.R
		String url2 = Constants.OPENCPU_HOST+"/ocpu/tmp/"+ocpuResult.getSessionID()+"/files/temp.R";
		result = apiService.doPost(url2);
		//编译出错
		if(result.getCode()==400){
			ocpuResult = new OcpuResult(content,result.getData());
			examResult.setStatus(false);
			examResult.setOcpuResult(ocpuResult);
			return JSON.toJSONString(examResult);
		}
		if(result.getCode()!=201&&result.getCode()!=200) {
			ocpuResult = new OcpuResult(content,"系统内部错误:"+result.getCode());
			examResult.setStatus(false);
			examResult.setOcpuResult(ocpuResult);
			return JSON.toJSONString(examResult);
		}
		//正常情况
		ocpuResult = new OcpuResult(result.getData());
		if(ocpuResult.getSessionID()==null){
			ocpuResult = new OcpuResult(content,"R文件执行后openCPU结果解析出错结果解析出错");
			examResult.setStatus(false);
			examResult.setOcpuResult(ocpuResult);
			return JSON.toJSONString(examResult);
		}
		//获取执行结果数据
		String source = apiService.doGet( Constants.OPENCPU_HOST+"/ocpu/tmp/"+ocpuResult.getSessionID()+"/source");
		String console = apiService.doGet( Constants.OPENCPU_HOST+"/ocpu/tmp/"+ocpuResult.getSessionID()+"/console");
		String info = apiService.doGet( Constants.OPENCPU_HOST+"/ocpu/tmp/"+ocpuResult.getSessionID()+"/info");
		ocpuResult.setSource(source);
		ocpuResult.setInfo(info);
		ocpuResult.setConsole(console);
		examResult.setOcpuResult(ocpuResult);
		examResult.setStatus(true);

		return JSON.toJSONString(examResult);
	}
	@ResponseBody
	@RequestMapping("add.do")
	public String addCourse(HttpServletRequest request, HttpServletResponse response,Course course) {
		courseService.save(course);
		return JSON.toJSONString(course);
	}
	@ResponseBody
	@RequestMapping("delete.do")
	public String addCourse(HttpServletRequest request, HttpServletResponse response,Long courseId) {
		courseService.delete(courseId);
		return JSON.toJSONString("deleted");
	}

}
