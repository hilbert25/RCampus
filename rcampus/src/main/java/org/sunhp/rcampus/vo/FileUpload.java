/**
 * 
 */
package org.sunhp.rcampus.vo;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 作者 : hht
 * @version 创建时间：2017年7月3日 下午4:54:21 上传文件
 */
public class FileUpload {
	public static final String FILE_PATH = "/home/hht/code/web/RCampus/rcampus/src/main/webapp/page/assets/img/user_head";

	public static String uploadFile(MultipartFile file, Long userId,
			HttpServletRequest request) throws IOException {
		String fileName = String.valueOf(userId) + "_head"
				+ getFileType(file.getOriginalFilename());
		File tempFile = new File(FILE_PATH, fileName);
		if (!tempFile.getParentFile().exists()) {
			tempFile.getParentFile().mkdir();
		}
		if (!tempFile.exists()) {
			tempFile.createNewFile();
		}
		file.transferTo(tempFile);
		return "../../rcampus/page/assets/img/user_head/" + fileName;
	}

	public static File getFile(String fileName) {
		return new File(FILE_PATH, fileName);
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName) {
		int i = fileName.length() - 1;
		while (i >= 0 && fileName.charAt(i) != '.') {
			i--;
		}
		return fileName.substring(i, fileName.length());
	}
}
