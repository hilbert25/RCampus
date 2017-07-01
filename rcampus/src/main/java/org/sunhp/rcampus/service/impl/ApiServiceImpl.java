package org.sunhp.rcampus.service.impl;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunhp.rcampus.bean.HttpResult;
import org.sunhp.rcampus.service.ApiService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Created by yufeng.fyf on 17/4/28.
 */
@Service("apiService")
public class ApiServiceImpl implements ApiService {
    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig requestConfig;

    /**
     * 执行get请求,200返回响应内容，其他状态码返回null
     *
     * @param url
     * @return
     * @throws IOException
     */
    public String doGet(String url) throws IOException {
        //创建httpClient对象
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(url);
        //设置请求参数
        httpGet.setConfig(requestConfig);
        try {
            //执行请求
            response = httpClient.execute(httpGet);
            //判断返回状态码是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * 执行带有参数的get请求
     *
     * @param url
     * @param paramMap
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String doGet(String url, Map<String, String> paramMap) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(url);
        for (String s : paramMap.keySet()) {
            builder.addParameter(s, paramMap.get(s));
        }
        return doGet(builder.build().toString());
    }

    /**
     * 执行post请求
     *
     * @param url
     * @param paramMap
     * @return
     * @throws IOException
     */
    public HttpResult doPost(String url, Map<String, String> paramMap) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        //设置请求参数
        httpPost.setConfig(requestConfig);
        if (paramMap != null) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            for (String s : paramMap.keySet()) {
                parameters.add(new BasicNameValuePair(s, paramMap.get(s)));
            }
            //构建一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, Charset.forName("UTF-8"));
            //将请求实体放入到httpPost中
            httpPost.setEntity(formEntity);
        }
        //创建httpClient对象
        CloseableHttpResponse response = null;
        try {
            //执行请求
            response = httpClient.execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     *
     * @param url
     * @param filePath
     * @return
     * @throws IOException
     */
    public HttpResult doPostFile(String url, String filePath) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        //设置请求参数
        httpPost.setConfig(requestConfig);
        if (filePath != null) {
            FileBody fileBody = new FileBody(new File(filePath));
            HttpEntity entity = MultipartEntityBuilder.create().addPart("file", fileBody).build();
            httpPost.setEntity(entity);
        }
        //创建httpClient对象
        CloseableHttpResponse response = null;
        try {
            //执行请求
            response = httpClient.execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
    /**
     * 执行post请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public HttpResult doPost(String url) throws IOException {
        return doPost(url, null);
    }


    /**
     * 提交json数据
     *
     * @param url
     * @param json
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResult doPostJson(String url, String json) throws ClientProtocolException, IOException {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);

        if (json != null) {
            // 构造一个请求实体
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = this.httpClient.execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity(), "UTF-8"));
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}