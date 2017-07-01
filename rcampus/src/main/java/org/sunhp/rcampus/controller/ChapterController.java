package org.sunhp.rcampus.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sunhp.rcampus.bean.Chapter;
import org.sunhp.rcampus.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("chapter")
public class ChapterController {
	@Autowired
	ChapterService chapterService;

	/**
	 * 获得章节列表
	 * @param request
	 * @param response
     * @return
     */
	@ResponseBody
	@RequestMapping("list.do")
	public String getChapterList(HttpServletRequest request, HttpServletResponse response) {
		List<Chapter>list  = chapterService.getAll( new Chapter());
		return JSON.toJSONString(list);
	}
	@ResponseBody
	@RequestMapping("add.do")
	public String addChapter(HttpServletRequest request, HttpServletResponse response,Chapter chapter) {
		chapterService.save(chapter);
		return JSON.toJSONString(chapter);
	}
	@ResponseBody
	@RequestMapping("delete.do")
	public String addChapter(HttpServletRequest request, HttpServletResponse response,Long chapterId) {
		chapterService.delete(chapterId);
		return JSON.toJSONString("deleted");
	}
}
