package com.grab.html;

import com.grab.html.schedule.GrabTask;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.URL;

/**
 * Created by baiqunwei on 15/3/8.
 */
public class Main {
    private static final boolean ON_PROXY = false;
    private static final String PROXY_HOST = "";
    private static final String PROXY_HOST_PORT = "";
    private static final String PROXY_USER = "";
    private static final String PROXY_PASSWORD = "";

    public static final String ROOT_DIR = "/Volumes/MACINTOSH-WORK/work/code/utils/grabhtml/clip-two";
    public static final String HTTP_HOST = "http://www.cliptheme.com/preview/admin/clip-one";
    public static final String INDEX_PAGE = "/index.html";
    public static final String PAGE_EXTRA = ".html";
    public static final String SAVE_PAGE_EXTRA = ".html";



    public static void main(String[] args) {
//        proxyConfig();

        GrabTask.put(INDEX_PAGE);
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Quartz.xml");

//        try {
//            System.out.println(IOUtils.toString(new URL("http://www.cliptheme.com/preview/admin/clip-one/assets/plugins/jQuery-lib/2.0.3/jquery.min.js")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 代理设置
     */
    public static void proxyConfig() {
        if (!ON_PROXY) return;

        if (StringUtils.isEmpty(PROXY_HOST)) {
            //启动系统代理
            System.setProperty("java.net.useSystemProxies", "true");
        } else {
            System.setProperty("http.proxyHost", PROXY_HOST);
            System.setProperty("http.proxyPort", PROXY_HOST_PORT);
//            String encoded = new String(Base64.encode(new String(PROXY_USER + ":" + PROXY_PASSWORD).getBytes()));
//            uc.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
        }


    }
}
