package demo.spring.boot.demospringboot.controller.mankan.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import demo.spring.boot.demospringboot.controller.mankan.util.Http;
import demo.spring.boot.demospringboot.controller.mankan.util.IpadGet;
import io.swagger.models.auth.In;


@Service
public class MankanUtil {

    private static Logger LOGGER
            = LoggerFactory.getLogger(MankanUtil.class);

    @Autowired
    private Http http;
    private Document document = null;
    private Elements elements = null;

    public Integer getTotalPages(String url) {

        IpadGet ipadGet = new IpadGet();
        String page = ipadGet.getGetMethodString(url, url);
        LOGGER.info("page1:{}", page);
        document = Jsoup.parse(page);
        Element main = document.body().getElementById("main");
        LOGGER.info("main:{}", main);
        main.getElementsByTag("img").stream().forEach(img -> {
            LOGGER.info("img:{}", img);
            img.attr("src");
        });
        Integer total;
        String result =
                main.getElementsByClass("showpage")
                        .get(0)
                        .getElementsByTag("a")
                        .get(0)
                        .html();
        result = result.substring(1, result.length() - 2);
        total = Integer.valueOf(result);
        return total;
    }

    public String getImgSrc(String url) {

        IpadGet ipadGet = new IpadGet();
        String page = ipadGet.getGetMethodString(url, url);
        LOGGER.info("page1:{}", page);
        document = Jsoup.parse(page);
        Element main = document.body().getElementById("main");
        LOGGER.info("main:{}", main);
        return main.getElementsByTag("img").get(0).attr("src");

    }
}
