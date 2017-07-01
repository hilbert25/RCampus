package org.sunhp.rcampus.service;

import org.apache.http.client.ClientProtocolException;
import org.sunhp.rcampus.bean.HttpResult;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface ApiService {
	public String doGet(String url) throws IOException;

	/**
	 * 执行带有参数的get请求
	 *
	 * @param url
	 * @param paramMap
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public String doGet(String url, Map<String, String> paramMap) throws IOException, URISyntaxException;

	/**
	 *
	 * @param url
	 * @param filePath
     * @return
     */
	public HttpResult doPostFile(String url, String filePath) throws IOException;
	/**
	 * 执行post请求
	 *
	 * @param url
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public HttpResult doPost(String url, Map<String, String> paramMap) throws IOException;

	/**
	 * 执行post请求
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 */


	public HttpResult doPost(String url) throws IOException;


	/**
	 * 提交json数据
	 *
	 * @param url
	 * @param json
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResult doPostJson(String url, String json) throws ClientProtocolException, IOException;
}
