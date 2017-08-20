package org.sunhp.rcampus.util;



import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtil {
 private JavaMailSenderImpl mailSender;
 @Autowired
 public void setMailSender(JavaMailSenderImpl mailSender){
	 this.mailSender=mailSender;
 }
 public void sendMessage(String to,String content,int type){
    try {
    	MimeMessage message=mailSender.createMimeMessage();
    	MimeMessageHelper helper=new MimeMessageHelper(message,"utf-8");	
    	String text=null;
    	helper.setFrom("ayahui3@126.com");
		helper.setTo(to);
		if(type==0){
		helper.setSubject("邮箱验证的信息");
		text="<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'>"
				+ "</head><body>感谢注册 rcampus，学数据处理，就来rcampus!<br>"
				+ "<a href="+content+">"+"点击链接完成验证"+"</a><br>"+"您的Email:"+to+"<br>您的Email将会作为账户名来登录rcampus"
				+ "</body></html>";
		}
		else if(type==1){
			helper.setSubject("重置密码");
			text="请使用新密码:"+content+"重新登录";
		}
		helper.setText(text,true);
		mailSender.send(message);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
 }
}
