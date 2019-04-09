package com.jingtao.jtcommon.configurer;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HttpClientHelper {

    private Logger LOGGER = LoggerFactory.getLogger(HttpClientHelper.class);

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig requestConfig;

    /**编辑HttpGet请求
     * get:localhost:8091/add?id=1&name=tom
     * 1.使用map<k,v>集合信息,保存传递的参数
     * 2.指定uri
     * 3.设定字符集编码
     */
    public String doGet(String uri,Map<String,String> params,String charset){
        String result = null;
        //设定字符集
        if(StringUtils.isEmpty(charset)){
            charset = "UTF-8";
        }

        try {
            //判断是否还有参数
            if(params != null){
                //使用URIBuilder进行请求了路径的拼接
                URIBuilder builder = new URIBuilder(uri);
                for (Map.Entry<String,String> entry : params.entrySet()) {
                    builder.addParameter(entry.getKey(), entry.getValue());
                    // add?id=1&name=tom 生成新的请求路径
                    uri = builder.build().toString();
                }
            }

            //定义请求对象
            HttpGet httpGet = new HttpGet(uri);
            //定义请求的链接时长
            httpGet.setConfig(requestConfig);

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            //判断请求是否正确
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                result = EntityUtils.toString(httpResponse.getEntity(),charset);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String doGet(String uri,Map<String,String> params){

        return doGet(uri, params, null);
    }

    public String doGet(String uri){

        return doGet(uri, null);
    }


    /**
     * post请求难点
     *  参数如何封装.httpClient提供表单提交的对象.
     *  将数据封装到表单对象中即可.
     */
    public String doPost(String uri,Map<String,String> params,
                         String charset){
        //1.判断字符集是否为null
        if(StringUtils.isEmpty(charset)){
            charset = "UTF-8";
        }

        //2.创建请求对象
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(requestConfig);

        String result = null;

        try {
            //3.判断参数是否为null
            if(params !=null){

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                //遍历参数为list集合赋值
                for (Map.Entry<String,String> entry : params.entrySet()) {
                    BasicNameValuePair valuePair =
                            new BasicNameValuePair(entry.getKey(), entry.getValue());

                    parameters.add(valuePair);
                }
                UrlEncodedFormEntity formEntity =
                        new UrlEncodedFormEntity(parameters, charset);

                //将请求实体对象封装到post对象中
                httpPost.setEntity(formEntity);
            }

            CloseableHttpResponse httpResponse =
                    httpClient.execute(httpPost);

            if(httpResponse.getStatusLine().getStatusCode() == 200){

                result = EntityUtils.toString(httpResponse.getEntity(),charset);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public String doPost(String uri,Map<String,String> params){

        return doPost(uri, params,null);
    }

    public String doPost(String uri){

        return doPost(uri, null);
    }



}
