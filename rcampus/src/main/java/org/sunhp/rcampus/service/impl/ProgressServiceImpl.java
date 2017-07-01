package org.sunhp.rcampus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunhp.rcampus.bean.Progress;
import org.sunhp.rcampus.dao.ProgressDao;
import org.sunhp.rcampus.service.ProgressService;

import java.util.List;

@Service("progressService")
public class ProgressServiceImpl extends BaseServiceImpl<Progress>implements ProgressService {
	@Autowired
	public ProgressServiceImpl(ProgressDao progressDao) {
		super.baseDao = progressDao;
	}

	@Autowired
	ProgressDao progressDao;
	public String test() {
		List<Progress> list = progressDao.find(new Progress());
		return list.size()+"";
	}

}
