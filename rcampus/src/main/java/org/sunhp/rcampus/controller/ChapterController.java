package org.sunhp.rcampus.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sunhp.rcampus.bean.Chapter;
import org.sunhp.rcampus.bean.Course;
import org.sunhp.rcampus.bean.Progress;
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
	@Autowired
	ProgressService progressService;

	@ResponseBody
	@RequestMapping("add.do")
	public String addChapter(HttpServletRequest request,
			HttpServletResponse response, String chapterOrder,
			String chapterName, String chapterDescribe) {
		System.out.println(chapterOrder + " " + chapterDescribe + " "
				+ chapterName);
		Chapter chapter = new Chapter();
		chapter.setChapterDescrbe(chapterDescribe);
		chapter.setChapterName(chapterName);
		chapter.setChapterOrder(Integer.valueOf(chapterOrder));
		Pageable<Chapter> chapterPageable = new Pageable<Chapter>();
		chapterPageable.setSearchProperty("chapter_order");
		chapterPageable.setSearchValue(chapterOrder);
		Page<Chapter> chapterPage = chapterService.findByPager(chapterPageable);
		if (chapterPage.getRows().size() != 0) {// 章节号重复
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", "已经存在第" + chapterOrder + "章,请勿重复添加");
			return JSON.toJSONString(jsonObject);
		} else {
			chapterService.save(chapter);
			return JSON.toJSONString(chapter);
		}

	}

	@ResponseBody
	@RequestMapping("delete")
	public String deleteChapter(HttpServletRequest request,
			HttpServletResponse response, Long chapterId) {
		chapterService.delete(chapterId);
		return JSON.toJSONString("deleted");
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

	/**
	 * 获取当前用户最新chapter
	 * 
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCompleteChapter")
	public String getUserLatestChapter(HttpServletRequest request,
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
		Long maxId = 0L;
		int index = 0;// 记录下progress方便查看
		for (int i = 0; i < progressList.size(); i++) {
			if (progressList.get(i).getCourseId() > maxId) {
				maxId = progressList.get(i).getCourseId();
				index = i;
			}
		}
		Course course = courseService.get(maxId);// progress表的最后一个课程

		Pageable<Course> coursePageable = new Pageable<Course>();
		coursePageable.setSearchProperty("chapter");
		coursePageable.setSearchValue(String.valueOf(course.getChapter()));
		coursePageable.setPageSize(Integer.MAX_VALUE);
		Page<Course> coursePage = courseService.findByPager(coursePageable);
		List<Course> courseList = coursePage.getRows();
		JSONObject jsonObject = new JSONObject();
		if (courseList.get(courseList.size() - 1).getCourseId() == course
				.getChapter() && progressList.get(index).getPoint() == 100) {// 最后一条记录是这一chapter的最后一节而且是100分
			jsonObject.put("latestChapter", course.getChapter());
		} else {// 否则就是前一章
			jsonObject.put("latestChapter", course.getChapter() - 1);
		}
		return JSON.toJSONString(jsonObject);
	}

	/**
	 * 获取chapter by id
	 * 
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getChapterbyId")
	public String getChapterbyId(HttpServletRequest request,
			HttpServletResponse response, String chapterId) {
		Chapter chapter = chapterService.get(Long.valueOf(chapterId));
		return JSON.toJSONString(chapter);
	}

	@ResponseBody
	@RequestMapping("modifyChapter")
	public String modifyChapter(HttpServletRequest request,
			HttpServletResponse response, String chapterId,
			String chapterOrder, String chapterName, String chapterDescribe) {
		System.out.println(chapterDescribe);
		Chapter chapter = chapterService.get(Long.valueOf(chapterId));
		chapter.setChapterDescrbe(chapterDescribe);
		chapter.setChapterName(chapterName);
		chapter.setChapterOrder(Integer.valueOf(chapterOrder));
		Pageable<Chapter> chapterPageable = new Pageable<Chapter>();
		chapterPageable.setSearchProperty("chapter_order");
		chapterPageable.setSearchValue(chapterOrder);
		Page<Chapter> chapterPage = chapterService.findByPager(chapterPageable);
		List<Chapter> chapterList = chapterPage.getRows();
		if (chapterList.size() != 0
				&& chapterList.get(0).getChapterId() != Long.valueOf(chapterId)) {// 章节号重复并且重复章节不是待修改的
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", "已经存在第" + chapterOrder + "章,请勿重复添加");
			return JSON.toJSONString(jsonObject);
		} else {
			chapterService.update(chapter);
			return JSON.toJSONString(chapter);
		}

	}

	/**
	 * 管理员获取chapter列表
	 * 
	 * @param request
	 * @param response
	 * @param chapterId
	 * @param chapterOrder
	 * @param chapterName
	 * @param chapterDescribe
	 * @return
	 */
	@RequestMapping("getChapterList")
	public String getChapterList(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("chapterList",
				chapterService.getAll(new Chapter()));
		return "chapter_manage";
	}
}
