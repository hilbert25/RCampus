package org.sunhp.rcampus.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JspToHtml {
	public static String jsp2Html(String jspPath,HttpServletRequest request,HttpServletResponse response,String target){
		response.setContentType("text/html;charset=utf-8");
		WrapperResponse wrapperResponse = new WrapperResponse(response);
		try {
			request.getRequestDispatcher(jspPath).include(request, wrapperResponse);
			String html=wrapperResponse.getContent();	
			//String rootPath = request.getServletContext().getRealPath("");
			//创建文件
			File file = new File(target);
			if(!file.exists())
			{
				file.createNewFile();
			}
			else
			{
				//先删除原文件
				file.delete();
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(target);  
			
			
			fos.write(html.getBytes());
			fos.flush();
			fos.close();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "生成成功";
	}
}
