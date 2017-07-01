package org.sunhp.rcampus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunhp.rcampus.bean.User;
import org.sunhp.rcampus.dao.UserDao;
import org.sunhp.rcampus.service.UserService;

import java.util.List;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User>implements UserService {
	@Autowired
	public UserServiceImpl(UserDao userDao) {
		super.baseDao = userDao;
	}

	@Autowired
	UserDao userDao;
	public String test() {
		List<User> list = userDao.find(new User());
		return list.size()+"";
	}

}
