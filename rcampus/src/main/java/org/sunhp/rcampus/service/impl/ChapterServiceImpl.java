package org.sunhp.rcampus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunhp.rcampus.bean.Chapter;
import org.sunhp.rcampus.dao.ChapterDao;
import org.sunhp.rcampus.service.ChapterService;

import java.util.List;

@Service("chapterService")
public class ChapterServiceImpl extends BaseServiceImpl<Chapter>implements ChapterService {
	@Autowired
	public ChapterServiceImpl(ChapterDao chapterDao) {
		super.baseDao = chapterDao;
	}

	@Autowired
	ChapterDao chapterDao;
	public String test() {
		List<Chapter> list = chapterDao.find(new Chapter());
		return list.size()+"";
	}

}
