package org.sunhp.rcampus.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.sunhp.rcampus.bean.Chapter;
import org.sunhp.rcampus.bean.Course;
import org.sunhp.rcampus.bean.Progress;
import org.sunhp.rcampus.bean.User;
import org.sunhp.rcampus.components.Page;
import org.sunhp.rcampus.components.Pageable;
import org.sunhp.rcampus.service.ChapterService;
import org.sunhp.rcampus.service.CourseService;
import org.sunhp.rcampus.service.ProgressService;
import org.sunhp.rcampus.service.UserService;
import org.sunhp.rcampus.vo.FileUpload;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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
	@Autowired
	private ChapterService chapterService;

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
		System.out.println(userId);
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
		String filePath=null;
		if(file!=null){
			String path=request.getSession().getServletContext().getRealPath("/")+"page/assets/img/icons/";
		    filePath = file.getOriginalFilename().equals("") ? user
				.getPhoto() : FileUpload.uploadIcon(file, userId,path);
		}
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

	@ResponseBody
	@RequestMapping("countUser")
	public String countUser(HttpServletRequest request,
			HttpServletResponse responses) {
		long ordinaryUser = 0, vip = 0;
		Pageable<User> pageable = new Pageable<User>();
		pageable.setSearchProperty("user_type");
		pageable.setPageSize(Integer.MAX_VALUE);
		pageable.setSearchValue("2");
		ordinaryUser = userService.count(pageable);
		pageable.setSearchValue("3");
		vip = userService.count(pageable);
		return JSON.toJSONString(ordinaryUser + vip);
	}

	@ResponseBody
	@RequestMapping("countAdmin")
	public String countAdmin(HttpServletRequest request,
			HttpServletResponse responses) {
		long admin = 0, superadmin = 0;
		Pageable<User> pageable = new Pageable<User>();
		pageable.setSearchProperty("user_type");
		pageable.setPageSize(Integer.MAX_VALUE);
		pageable.setSearchValue("1");
		admin = userService.count(pageable);
		pageable.setSearchValue("0");
		superadmin = userService.count(pageable);
		return JSON.toJSONString(admin + superadmin);
	}

	@ResponseBody
	@RequestMapping("userlist")
	public String getUserList(HttpServletRequest request,
			HttpServletResponse responses, int page, String type) {
		Pageable<User> pageable = new Pageable<User>();
		pageable.setSearchProperty("user_type");
		pageable.setPageSize(5);
		if (type.equals("admin")) {
			pageable.setSearchValue("1");
			Page<User> adminPage = userService.findByPager(pageable);
			pageable.setSearchValue("0");
			Page<User> superPage = userService.findByPager(pageable);
			List<User> allAdmin = adminPage.getRows();
			allAdmin.addAll(superPage.getRows());
			return JSON.toJSONString(allAdmin);
		} else {
			pageable.setSearchValue("2");
			Page<User> userPage = userService.findByPager(pageable);
			pageable.setSearchValue("3");
			Page<User> superPage = userService.findByPager(pageable);
			List<User> allUser = userPage.getRows();
			allUser.addAll(superPage.getRows());
			return JSON.toJSONString(allUser);
		}
	}

	/**
	 * 计算已经完成了百分之多少，可以优化
	 * 
	 * @param userId
	 * @return
	 */
	public int getFinishRate(Long userId) {
		Progress progress = new Progress();
		progress.setUserId(userId);
		List<Progress> progressList = progressService.find(progress);
		if (progressList.size() == 0)
			return 0;
		else {
			Course course = new Course();
			course.setCourseId(progressList.get(0).getCourseId());
			course = courseService.find(course).get(0);
			Chapter chapter = new Chapter();
			chapter.setChapterId(course.getChapter());
			chapter = chapterService.find(chapter).get(0);
			int total = 0;// 记录通过的总数
			List<Chapter> chapterList = chapterService.getAll(new Chapter());
			for (int i = 0; i < chapterList.size()
					&& chapter.getChapterId() < chapterList.get(i)
							.getChapterId(); i++) {// 遍历已完成的chapter
				Course tempCourse = new Course();
				tempCourse.setChapter(chapterList.get(i).getChapterId());
				total += courseService.find(tempCourse).size();
			}
			total += course.getCourseOrder();
			return 100*total / courseService.getAll(new Course()).size();
		}
	}
	@RequestMapping("/addAdmin")
	@ResponseBody
	public String addAdmin(User user){
		Pageable<User> pageable = new Pageable<User>();
		pageable.setSearchProperty("user_type");
		pageable.setPageSize(Integer.MAX_VALUE);
		pageable.setSearchValue(String.valueOf(user.getUserType()));
		List<User> admins=userService.findByPager(pageable).getRows();
		if(admins.size()>0){
			for(User admin:admins){
			if(admin.getEmail().equals(user.getEmail())){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", "已经存在,请勿重复添加");
			return JSON.toJSONString(jsonObject);
			}
		}
	  }
        userService.save(user);
	    return JSON.toJSONString("添加成功:"+user);
	}
}
