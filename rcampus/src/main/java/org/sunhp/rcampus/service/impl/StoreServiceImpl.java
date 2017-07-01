package org.sunhp.rcampus.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.sunhp.rcampus.bean.Store;
import org.sunhp.rcampus.components.Constants;
import org.sunhp.rcampus.dao.StoreDao;
import org.sunhp.rcampus.service.StoreService;

@Service("storeService")
public class StoreServiceImpl extends BaseServiceImpl<Store>implements StoreService {
	@Autowired
	public StoreServiceImpl(StoreDao storeDao) {
		super.baseDao = storeDao;
	}

	@Autowired
	StoreDao storeDao;
	public String test() {
		List<Store> list = storeDao.find(new Store());
		return list.size()+"";
	}

}
