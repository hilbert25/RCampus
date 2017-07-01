package org.sunhp.rcampus.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.sunhp.rcampus.components.Constants;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUtils {
	public static String saveFileAndReturnPath(HttpServletRequest request, String fileName) {
		String savePath = "";
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		multipartResolver.setDefaultEncoding("utf-8");
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			if (null != multiRequest) {
				//项目标志
				MultipartFile multipartFile = multiRequest.getFile(fileName);
				if (null != multipartFile) {
					// 获得新的文件名称
					String projectLogoName = UUID.randomUUID() + "_"
							+ multipartFile.getOriginalFilename();

					if (null != projectLogoName && !"".equals(projectLogoName.trim())) {
						//文件路径处理
						SimpleDateFormat df = new SimpleDateFormat("yyyyMM");// 设置时间格式，年月。
						String dateMK = df.format(new Date());
						String path = request.getSession().getServletContext()
								.getRealPath("/") + Constants.UPLOAD_PATH
								+ dateMK + "/";
						File dirFile = new File(path);
						if (!dirFile.exists()) {
							dirFile.mkdirs();
						}
						// 获得新的文件名称
						String newFileName = UUID.randomUUID() + "_"
								+ multipartFile.getOriginalFilename();

						File localFile = new File(path, newFileName);
						try {
							multipartFile.transferTo(localFile);
						} catch (IllegalStateException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						// 获得文件大小
						try {
							InputStream imageFile = new FileInputStream(
									localFile);
							try {
								System.out.println(imageFile.available());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								try {
									imageFile.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// 图片保存路径
						savePath = path + newFileName;
					}
				}
			}
		}
		return savePath;
	}

	public static String saveStrToFile(HttpServletRequest request, String fileName, String content) throws IOException {
		String path = request.getSession().getServletContext()
				.getRealPath("/") + Constants.UPLOAD_PATH + "/";
		File dirFile = new File(path);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		// 获得新的文件名称
		File localFile = new File(path, fileName);
		if (!localFile.exists()) {
			localFile.createNewFile();
		}
		BufferedWriter output = new BufferedWriter(new FileWriter(localFile));
		output.write(content);
		output.close();
		return localFile.getAbsolutePath();
	}
}
