package com.grab.html.jsoup;

import com.grab.html.Main;
import com.grab.html.SuperGrab;
import com.grab.html.schedule.GrabTask;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.util.URLUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by baiqunwei on 15/3/8.
 */
public class PageGrab extends SuperGrab {
    private static Logger logger = LoggerFactory.getLogger(PageGrab.class);

    public PageGrab(String url) {
        super(url);
    }

    @Override
    public void grab() {
        logger.debug("Page grab:{}", this.getUrl());
        pageToFile(this.getUrl(), this.getPageEntity().getFilePath(), this.getPageEntity().getFileName());

    }

    /**
     * 将页面结果存储到文件
     *
     * @param pageUrl
     * @param filePath
     * @param fileName
     * @return
     * @throws java.io.IOException
     */
    private void pageToFile(String pageUrl, String filePath, String fileName) {
        if (fileName.endsWith(Main.PAGE_EXTRA)) {
            fileName = fileName.replaceAll(Main.PAGE_EXTRA, Main.SAVE_PAGE_EXTRA);
        }

        File pageFile = new File(filePath + File.separator + fileName);
        try {
            FileUtils.writeByteArrayToFile(pageFile, IOUtils.toByteArray(new URL(pageUrl)));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        FileOutputStream fout = null;
//        HttpEntity entity = null;
//
//        try {
//            fout = new FileOutputStream(pageFile);
//
//            if (!pageFile.exists()) {
//                entity = getPageEntity(pageUrl);
//                entity.writeTo(new FileOutputStream(pageFile));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            IOUtils.closeQuietly(fout);
//            if (null != entity) {
//                try {
//                    IOUtils.closeQuietly(entity.getContent());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

    }

}
