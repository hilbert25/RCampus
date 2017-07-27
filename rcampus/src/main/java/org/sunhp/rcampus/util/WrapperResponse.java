package org.sunhp.rcampus.util;

import java.io.ByteArrayOutputStream;   
import java.io.IOException;   
import java.io.PrintWriter;   
import java.io.UnsupportedEncodingException;   
import javax.servlet.http.HttpServletResponse;   
import javax.servlet.http.HttpServletResponseWrapper;   


public class WrapperResponse extends HttpServletResponseWrapper {  
	
	
   private MyPrintWriter tmpWriter;   
   private ByteArrayOutputStream output;   
   
   public WrapperResponse(HttpServletResponse httpServletResponse) {   
      super(httpServletResponse);   
      output = new ByteArrayOutputStream();   
      tmpWriter = new MyPrintWriter(output);   
   }   
   
   public void finalize() throws Throwable {   
      super.finalize();   
      output.close();   
      tmpWriter.close();   
   }   
   public String getContent() {   
      try {   
         tmpWriter.flush();   //ˢ�¸����Ļ��壬�꿴java.io.Writer.flush()   
         String s = tmpWriter.getByteArrayOutputStream().toString("utf-8");   
         //�˴��ɸ�����Ҫ���ж�������Լ�Writer�����ò���   
       //����tmpWriter.getByteArrayOutputStream().reset()   
         return s;   
      } catch (UnsupportedEncodingException e) {   
         return "UnsupportedEncoding";   
      }   
   }   
  
   //����getWriter()������ʹ�������Լ������Writer   
   public PrintWriter getWriter() throws IOException {   
      return tmpWriter;   
   }   
   public void close() throws IOException {   
      tmpWriter.close();   
   }   
  
   //�Զ���PrintWriter��Ϊ���ǰ�response��д���Լ�ָ��������������   
   //����Ĭ�ϵ�ServletOutputStream   
   private static class MyPrintWriter extends PrintWriter {   
      ByteArrayOutputStream myOutput;   //�˼�Ϊ���response�������Ķ���   
  
      public MyPrintWriter(ByteArrayOutputStream output) {   
         super(output);   
         myOutput = output;   
      }   
      public ByteArrayOutputStream getByteArrayOutputStream() {   
         return myOutput;   
      }   
   }   
}   