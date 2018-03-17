package demo.spring.boot.demospringboot.controller.mankan.util;


import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpHost;
import org.apache.http.conn.params.ConnRoutePNames;



public class IpadGet {
    private HttpClient httpClient = null;
    private GetMethod getMethod = null;
    private String refererValue = null;

    //代理
    private HttpHost proxyHost = null;

    protected void init(String url, String refererValue) {
        this.refererValue = refererValue;
        // 打开浏览器
        this.httpClient = new HttpClient();
        // 输入网址
        this.getMethod = new GetMethod(url);
        this.getMethod.addRequestHeader("Referer", refererValue);
        this.getMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 4 Build/LMY48T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.89 Mobile Safari/537.36");

        this.proxyHost = new HttpHost("211.142.236.137", 8080);//代理
        this.httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxyHost);//设置代理
        this.httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");//设置编码格式
        this.getMethod.addRequestHeader("x-forwarded-for", "211.142.236.137");
    }

    protected void execute() {

        // 回车
        try {
            httpClient.executeMethod(getMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        // 释放连接
        if (null != getMethod) {
            this.getMethod.releaseConnection();
            this.getMethod = null;
        }
        httpClient = null;

    }

    //获得GetMethod
    public GetMethod getGetMethod(String url, String refererValue) {
        this.init(url, refererValue);
        this.execute();
        return getMethod;

    }

    public String getGetMethodString(String url, String refererValue) {
        this.init(url, refererValue);
        this.execute();
        try {
            return getMethod.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}

