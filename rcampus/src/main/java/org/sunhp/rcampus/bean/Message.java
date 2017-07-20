/**
 * 
 */
package org.sunhp.rcampus.bean;

import java.util.Date;

/** 
 * @author 作者 : hht
 * @version 创建时间：2017年7月13日 下午12:40:09 
 * 类说明 
 */
/**
 * @author hht
 * 
 */
public class Message {
	private Long messageId;
	private Long userId;
	private String messageContent;
	private Long isDeal;
	private Long adminId;
	private Date createTime;
	private Date dealTime;
	private User user;
	private String createDiff;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Long getIsDeal() {
		return isDeal;
	}

	public void setIsDeal(Long isDeal) {
		this.isDeal = isDeal;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCreateDiff() {
		return createDiff;
	}

	public void setCreateDiff(String diff) {
		this.createDiff = diff;
	}

}
