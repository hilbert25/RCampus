/**
 * 
 */
package org.sunhp.rcampus.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sunhp.rcampus.bean.Message;
import org.sunhp.rcampus.bean.User;
import org.sunhp.rcampus.components.Page;
import org.sunhp.rcampus.components.Pageable;
import org.sunhp.rcampus.service.MessageService;
import org.sunhp.rcampus.service.UserService;
import org.sunhp.rcampus.util.DateUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/** 
 * @author 作者 : hht
 * @version 创建时间：2017年7月13日 下午4:17:50 
 * 类说明 
 */
/**
 * @author hht
 * 
 */
@Controller
@RequestMapping("message")
public class MessageController {
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(value = "/getUnDealCount")
	public String getUnDealMessageCount() {
		Pageable<Message> pageable = new Pageable<Message>();
		pageable.setPageSize(Integer.MAX_VALUE);
		return JSON.toJSONString(messageService.count(pageable));
	}

	@ResponseBody
	@RequestMapping(value = "/addMessage")
	public String addMessage(HttpServletRequest request,
			HttpServletResponse response, String content) {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		Message message = new Message();
		message.setCreateTime(new Date());
		message.setMessageContent(content);
		message.setUserId(userId);
		messageService.save(message);
		return JSON.toJSONString("");
	}

	@ResponseBody
	@RequestMapping(value = "/getShortMessageList")
	public String getShortMessageList() {
		Pageable<Message> pageable = new Pageable<Message>();
		pageable.setPageSize(5);
		Page<Message> page = messageService.findByPager(pageable);
		List<Message> list = page.getRows();
		for (int i = 0; i < list.size(); i++) {
			User user = userService.get(list.get(i).getUserId());
			list.get(i).setUser(user);
			list.get(i).setCreateDiff(
					DateUtil.howLongFromNow(list.get(i).getCreateTime()));
		}
		return JSON.toJSONString(page.getRows());
	}
}
