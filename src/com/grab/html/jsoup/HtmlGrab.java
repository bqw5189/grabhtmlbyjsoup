package com.grab.html.jsoup;

import com.grab.html.Main;
import com.grab.html.SuperGrab;
import com.grab.html.schedule.GrabTask;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by baiqunwei on 15/3/8.
 */
public class HtmlGrab extends SuperGrab {
    private static Logger logger = LoggerFactory.getLogger(HtmlGrab.class);

    public HtmlGrab(String url) {
        super(url);
    }

    @Override
    public void grab() {
        logger.debug("HTML grab:{}", this.getUrl());

        Document doc = request();

        if (null == doc) return;

        //抓取 CSS
        Elements links = doc.select("link");

        for (Element link : links) {
            final String linkAttribute = link.attr("href");

            logger.debug("parse link href: {}" , linkAttribute);

            GrabTask.put(this.getPageEntity(), linkAttribute);
        }

        //抓取 JS
        Elements scripts = doc.select("script");

        for (Element script : scripts) {
            String scriptAttribute = script.attr("src");

            logger.debug("parse script src: {}" , scriptAttribute);

            GrabTask.put(this.getPageEntity(),scriptAttribute);
        }

        if (this.getUrl().endsWith(Main.INDEX_PAGE)) {
            //抓去 本站页面
            Elements aHrefs = doc.select("a");
            for (Element a : aHrefs) {
                String href = a.attr("href");

                if (null != href && href.endsWith(Main.PAGE_EXTRA)) {
                    GrabTask.put(this.getPageEntity(),a.attr("href"));
                }

                String dataTheme = a.attr("data-theme");
                if (null != dataTheme) {
                    GrabTask.put(this.getPageEntity(), a.attr("data-theme"));
                }
            }
//
            //抓取 图片
            Elements imgs = doc.select("img");

            for (Element img : imgs) {
                GrabTask.put(this.getPageEntity(),img.attr("src"));
            }

        }
    }
}
