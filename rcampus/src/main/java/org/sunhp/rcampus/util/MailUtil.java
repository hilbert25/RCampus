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
 public void sendMail(String to,String link){
	 MimeMessage message=mailSender.createMimeMessage();
    try {
		MimeMessageHelper  helper=new MimeMessageHelper(message,"utf-8");
		helper.setFrom("ayahui3@126.com");
		helper.setTo(to);
		helper.setSubject("邮箱验证的信息");
		String text="<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'>"
				+ "</head><body>感谢注册 rcampus，学数据处理，就来rcampus!<br>"
				+ "<a href="+link+">"+"link"+"</a><br>"+"您的Email:"+to+"<br>您的Email将会作为账户名来登录rcampus"
				+ "</body></html>";
		helper.setText(text,true);
		mailSender.send(message);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
 }
}
