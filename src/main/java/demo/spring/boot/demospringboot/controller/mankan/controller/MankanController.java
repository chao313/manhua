package demo.spring.boot.demospringboot.controller.mankan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.spring.boot.demospringboot.controller.mankan.service.MankanUtil;


@Controller
@RequestMapping(value = "/mankan")
public class MankanController {

    @Autowired
    private MankanUtil mankanUtil;

    @GetMapping(value = "/download_show")
    public String freeMarker(Map<String, Object> map,
                             @RequestParam(name = "url") String url) {
        //获取漫画的id
        String id = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        String newUrl = url.substring(0, url.lastIndexOf("/"));
        Integer total =
                mankanUtil.getTotalPages(url);
        for (int i = 2; i < total; i++) {
            newUrl = newUrl + id + "_" + 2 + ".html";
            try {
                mankanUtil.getImgSrc(newUrl);
            } catch (Exception e) {
                continue;
            }
        }

        return "mankan";
    }

    @GetMapping(value = "/show")
    public String show(Map<String, Object> map,
                       @RequestParam(name = "url") String url,
                       @RequestParam(name = "page") Integer page,
                       @RequestParam(name = "size") Integer size) {
        //获取漫画的id
        String id = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        String newUrl = url.substring(0, url.lastIndexOf("/") + 1);
        Integer total =
                mankanUtil.getTotalPages(url);
        List<String> list = new ArrayList<>();
        for (int i = (page - 1) * size; i < page * size && i < total; i++) {
            try {
                String result = newUrl + id + "_" + i + ".html";
                result = mankanUtil.getImgSrc(result);
                list.add(result);
            } catch (Exception e) {
                continue;
            }
        }
        map.put("pre", "/chao/mankan/show?url=" + url + "&page=" + (page - 1) + "&size=" + size);
        map.put("next", "/chao/mankan/show?url=" + url + "&page=" + (page + 1) + "&size=" + size);
        map.put("list", list);
        return "mankan";
    }

    @GetMapping(value = "/index")
    public String index(Map<String, Object> map,
                        @RequestParam(name = "url", required = false, defaultValue = "") String url,
                        @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                        @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

        map.put("now", "/chao/mankan/show?url=" + url + "&page=" + page + "&size=" + size);
        return "index";
    }

    @GetMapping("/imageUrl")
    public void imageUrl(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        String picPath = request.getQueryString();
        picPath = picPath.substring(4, picPath.length());
        URLConnection u = new URL(picPath).openConnection();
        InputStream in = u.getInputStream();
        if (null != in) {
            int len;
            byte[] b = new byte[1024];
            while ((len = in.read(b)) != -1) { // 循环读取
                os.write(b, 0, len); // 写入到输出流
            }
            os.flush();
            in.close();
        }
        os.close();
    }


}
