package com.grab.html.jsoup;

import com.grab.html.SuperGrab;
import com.grab.html.schedule.GrabTask;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bqw on 14-3-25.
 */
public class CssGrab extends SuperGrab {
    private static Logger logger = LoggerFactory.getLogger(CssGrab.class);

    public CssGrab(String url) {
        super(url);
    }

    public void grab() {

        logger.debug("CSS grab:{}", this.getUrl());

        Document doc = request();

        if (null == doc) return;

        String pageResource = doc.body().text();
//        logger.debug("css :{}", pageResource);

        String cssSource = pageResource.replaceAll(";", ";\n").replaceAll("}", "}\n");

        Pattern pat = Pattern.compile("url\\([\\s\\S]*?(\\))");
        Matcher mat = pat.matcher(cssSource);

        while (mat.find()) {
            String url = mat.group();
            System.out.println("css url:" + url);

            String indexChart = "'";

            if (url.indexOf("\"") > -1) {
                indexChart = "\"";
            }

            int start = url.indexOf(indexChart) != -1 ? url.indexOf(indexChart) + 1 : 4;
            int end = url.lastIndexOf(indexChart) != -1 ? url.lastIndexOf(indexChart) : url.length() - 1;

            url = url.substring(start, end);
            if (url.indexOf("?") > -1) {
                url = url.substring(0, url.indexOf("?"));
            }
            if (url.indexOf("#") > -1) {
                url = url.substring(0, url.indexOf("#"));
            }

            logger.debug("css urls is : {}", url);

            GrabTask.put(this.getPageEntity(), url);
        }
//        com.grab.netty.Grab.NOT_FINSH_GRAB_PAGE.remove(pageFileEntity.getPageUrl());

    }


}
