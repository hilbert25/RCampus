package org.sunhp.rcampus.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.sunhp.rcampus.bean.Course;
import org.sunhp.rcampus.bean.Progress;
import org.sunhp.rcampus.bean.User;
import org.sunhp.rcampus.components.Page;
import org.sunhp.rcampus.components.Pageable;
import org.sunhp.rcampus.service.CourseService;
import org.sunhp.rcampus.service.ProgressService;
import org.sunhp.rcampus.service.UserService;
import org.sunhp.rcampus.vo.FileUpload;
import java.io.IOException;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private ProgressService progressService;
	@Autowired
	private CourseService courseService;

	/**
	 * 获得user
	 * 
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/getUserById")
	public String getUserById(HttpServletRequest request,
			HttpServletResponse response, Long userId, Integer currentPage) {
		int countForEachPage = 5;// 每页5条
		// 获取user信息
		userId = userId == null ? 0 : userId;
		currentPage = currentPage == null ? 1 : currentPage;
		User user = userService.get(userId);
		// 获取该user的progress
		Pageable<Progress> progressPageable = new Pageable<Progress>();
		progressPageable.setSearchProperty("user_id");
		progressPageable.setSearchValue(String.valueOf(userId));
		progressPageable.setPageSize(Integer.MAX_VALUE);
		long progressCount = progressService.count(progressPageable);// progress总数
		int beginPage = 10 * ((currentPage - 1) / 10) + 1;// 底部显示的起始页码
		int endPage = (int) (beginPage + 10 > Math.ceil((double) progressCount
				/ countForEachPage) ? Math.ceil((double) progressCount
				/ countForEachPage) : beginPage + 9);// 底部显示的终止页码
		progressPageable.setPageSize(countForEachPage);
		progressPageable.setPageNumber(currentPage);
		Page<Progress> progressPage = progressService
				.findByPager(progressPageable);
		List<Progress> progressList = progressPage.getRows();
		List<Course> courseList = new ArrayList<Course>();
		// progress对应的course信息
		for (int i = 0; i < progressList.size(); i++) {
			courseList
					.add(courseService.get(progressList.get(i).getCourseId()));
		}
		request.setAttribute("progressList", progressList);
		request.setAttribute("courseList", courseList);
		request.setAttribute("user", user);
		request.setAttribute("progressListSize", progressCount);
		request.setAttribute("courseList", courseList);
		request.setAttribute("progressList", progressList);
		request.setAttribute("pageCount", Math.ceil(progressCount / 5));
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		return "myprofile";
	}

	/**
	 * 设置用户信息
	 * 
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/setUserInfo")
	public String setUserInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			Long userId,
			@RequestParam(value = "user_head", required = false) MultipartFile file)
			throws IOException {
		User user = userService.get(userId);
		String filePath = file.getOriginalFilename().equals("") ? user
				.getPhoto() : FileUpload.uploadFile(file, userId, request);
		user.setUserName(request.getParameter("user_name"));
		user.setEmail(request.getParameter("user_email"));
		user.setPhoto(filePath);
		userService.update(user);
		request.setAttribute("user", user);
		request.setAttribute("status", true);
		return "set_userinfo";
	}

	/**
	 * jump
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/toSetUserInfo")
	public String toSetUserPhoto(HttpServletRequest request,
			HttpServletResponse response, Long userId) {
		User user = userService.get(userId);
		request.setAttribute("user", user);
		request.setAttribute("status", false);
		return "set_userinfo";
	}
}
