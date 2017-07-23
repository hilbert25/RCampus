package org.sunhp.rcampus.controller;

import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sunhp.rcampus.bean.Chapter;
import org.sunhp.rcampus.bean.Course;
import org.sunhp.rcampus.bean.User;
import org.sunhp.rcampus.components.Page;
import org.sunhp.rcampus.components.Pageable;
import org.sunhp.rcampus.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("chapter")
public class ChapterController {
	@Autowired
	ChapterService chapterService;
	@Autowired
	UserService userService;
	@Autowired
	CourseService courseService;

	/**
	 * 获得章节列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("list.do")
	public String getChapterList(HttpServletRequest request,
			HttpServletResponse response) {
		List<Chapter> list = chapterService.getAll(new Chapter());
		return JSON.toJSONString(list);
	}

	@ResponseBody
	@RequestMapping("add.do")
	public String addChapter(HttpServletRequest request,
			HttpServletResponse response, Chapter chapter) {
		chapterService.save(chapter);
		return JSON.toJSONString(chapter);
	}

	@ResponseBody
	@RequestMapping("delete.do")
	public String addChapter(HttpServletRequest request,
			HttpServletResponse response, Long chapterId) {
		chapterService.delete(chapterId);
		return JSON.toJSONString("deleted");
	}

	@RequestMapping("add")
	public String addChapter(HttpServletRequest request,
			HttpServletResponse response) {
		Chapter chapter = new Chapter();
		chapter.setChapterDescrbe(request.getParameter("chapterDescribe"));
		chapter.setChapterName(request.getParameter("chapterName"));
		chapter.setChapterOrder(Integer.valueOf(request
				.getParameter("chapterOrder")));
		chapterService.save(chapter);
		return "course_manage";
	}

	@RequestMapping("chapter_manage")
	public String chapterManage(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		User user = userService.get(userId);
		request.setAttribute("user", user);
		return "chapter_manage";
	}

	@RequestMapping("chapter_detail")
	public String getChapterDetail(HttpServletRequest request,
			HttpServletResponse response, Long chapterId) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		User user = userService.get(userId);
		Chapter chapter = chapterService.get(chapterId);
		Pageable<Course> coursePageable = new Pageable<Course>();
		coursePageable.setSearchProperty("chapter");
		coursePageable.setSearchValue(String.valueOf(chapter.getChapterId()));
		Page<Course> pagePage = courseService.findByPager(coursePageable);
		chapter.setCourseList(pagePage.getRows());
		request.setAttribute("user", user);
		request.setAttribute("chapter", chapter);
		request.setAttribute("courseList", pagePage.getRows());
		return "chapter_detail_manage";
	}
}
