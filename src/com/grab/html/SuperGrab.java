package com.grab.html;

import com.grab.html.schedule.GrabTask;
import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by baiqunwei on 15/3/8.
 */
public abstract class SuperGrab implements IGrab {
    public static int TIME_OUT = 5000;

    private String url;

    private PageEntity pageEntity;

    private Logger logger = LoggerFactory.getLogger(SuperGrab.class);

    public SuperGrab(String url) {
        this.url = url;

        pageEntity = mkdirs();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PageEntity getPageEntity() {
        return pageEntity;
    }

    public void setPageEntity(PageEntity pageEntity) {
        this.pageEntity = pageEntity;
    }

    public Document request() {
        try {

            Document doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/7.0; Touch; .NET4.0E; .NET4.0C; Tablet PC 2.0; InfoPath.3; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729)").timeout(TIME_OUT).get();

            if (this.getPageEntity().getFileName().endsWith(Main.PAGE_EXTRA)) {
                pageToFile(doc.html(), this.getPageEntity());
            }else{
                pageToFile(doc.body().text(), this.getPageEntity());
            }

            return doc;


        } catch (IOException e) {
            // e.printStackTrace();
            logger.warn("request time out!");
            GrabTask.put(this.getPageEntity(), this.getUrl());
//            request();
        }

        return null;
    }

    private void pageToFile(String html, PageEntity pageEntity) {
        pageToFile(html, pageEntity.getFilePath(), pageEntity.getFileName());
    }

    private PageEntity mkdirs() {
        PageEntity PageEntity = new PageEntity();
        PageEntity.setPageUrl(this.getUrl());

        //获取 uri index.html
        String uri = this.getUrl().replaceAll(Main.HTTP_HOST, "");
        String filePath = Main.ROOT_DIR;
        String fileName = uri;

        if (uri.indexOf("http://") > -1) {
            uri = uri.replaceFirst("http://", "");
        }
        if (uri.indexOf("https://") > -1) {
            uri = uri.replaceFirst("https://", "");
        }

        //包含目录
        if (uri.indexOf("/") > -1) {
            int fileNameIndex = uri.lastIndexOf("/");
            fileName = uri.substring(fileNameIndex);

            //创建目录
            filePath = Main.ROOT_DIR + File.separator + uri.substring(0, fileNameIndex);

            File pageFile = new File(filePath);
            if (!pageFile.exists()) {
                pageFile.mkdirs();
            }

            logger.debug("mkdir :{}", filePath);
        }

        PageEntity.setFileName(fileName);
        PageEntity.setFilePath(filePath);
        PageEntity.setUri(uri);

        return PageEntity;

    }

    /**
     * 将页面结果存储到文件
     *
     * @param pageHtml
     * @param filePath
     * @param fileName
     * @return
     * @throws java.io.IOException
     */
    private void pageToFile(String pageHtml, String filePath, String fileName) {
        if (fileName.endsWith(Main.PAGE_EXTRA)) {
            fileName = fileName.replaceAll(Main.PAGE_EXTRA, Main.SAVE_PAGE_EXTRA);
        }
        File pageFile = new File(filePath + File.separator + fileName);

        try {
            if (!pageFile.exists()) {
                FileUtils.writeStringToFile(pageFile, pageHtml);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
