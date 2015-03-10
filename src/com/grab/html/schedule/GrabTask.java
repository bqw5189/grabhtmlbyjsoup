package com.grab.html.schedule;


import com.grab.html.GrabFactory;
import com.grab.html.IGrab;
import com.grab.html.Main;
import com.grab.html.PageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 任务扫描 根据 任务类型 调用相应处理脚本
 * 逆变器数据采集
 * Created by baiqw on 14-11-12.
 */
@Component
// 默认将类中的所有public函数纳入事务管理.
//@Transactional(readOnly = true)
public class GrabTask {
    public static final Set<String> GRAB_PAGE = new TreeSet<String>();

    private static Logger logger = LoggerFactory.getLogger(GrabTask.class);

    private static final Queue<String> QUEUE = new LinkedBlockingQueue<String>();

    public static void put(String url) {
        put(null, url);
    }

    public static void put(PageEntity pageEntity, String url) {
        if (url.length() < 1) return;

        if (null == pageEntity || url.startsWith("/")) {
            url = Main.HTTP_HOST + url;
        } else {
            if (!url.startsWith("http://")) {
                url = pageEntity.getCurrentUrl() + "/" + url;
            }
        }

        //页面已经存在
        if (GRAB_PAGE.contains(url) || QUEUE.contains(url)) {
//            logger.debug("contains:{}", url);
            return;
        }

        logger.debug("put url:{}", url);

        QUEUE.add(url);
    }

    public void execute() throws Exception {
        String url = QUEUE.poll();

        if (null == url) return;

        logger.info("grab page count : {} , queue count:{}, url:{}", GRAB_PAGE.size(), QUEUE.size(), url);

        IGrab grab = GrabFactory.build(url);

        if (null != grab) {
            grab.grab();
        }

        GRAB_PAGE.add(url);
    }
}
