package org.sunhp.rcampus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunhp.rcampus.bean.Course;
import org.sunhp.rcampus.dao.CourseDao;
import org.sunhp.rcampus.service.CourseService;

import java.util.List;

@Service("courseService")
public class CourseServiceImpl extends BaseServiceImpl<Course>implements CourseService {
	@Autowired
	public CourseServiceImpl(CourseDao courseDao) {
		super.baseDao = courseDao;
	}

	@Autowired
	CourseDao courseDao;
	public String test() {
		List<Course> list = courseDao.find(new Course());
		return list.size()+"";
	}

}
