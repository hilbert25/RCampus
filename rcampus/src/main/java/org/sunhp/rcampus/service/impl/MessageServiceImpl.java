/**
 * 
 */
package org.sunhp.rcampus.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunhp.rcampus.bean.Message;
import org.sunhp.rcampus.bean.User;
import org.sunhp.rcampus.components.Page;
import org.sunhp.rcampus.components.Pageable;
import org.sunhp.rcampus.dao.MessageDao;
import org.sunhp.rcampus.dao.UserDao;
import org.sunhp.rcampus.service.MessageService;
import org.sunhp.rcampus.service.UserService;

/** 
 * @author 作者 : hht
 * @version 创建时间：2017年7月13日 下午4:15:22 
 * 类说明 
 */
/**
 * @author hht
 * 
 */
@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl<Message> implements
		MessageService {
	@Autowired
	public MessageServiceImpl(MessageDao messageDao) {
		super.baseDao = messageDao;
	}

	@Autowired
	MessageDao messageDao;

	public String test() {
		List<Message> list = messageDao.find(new Message());
		return list.size() + "";
	}
}
