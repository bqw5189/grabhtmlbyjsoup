package com.grab.html;

import com.grab.html.jsoup.CssGrab;
import com.grab.html.jsoup.HtmlGrab;
import com.grab.html.jsoup.PageGrab;

/**
 * Created by baiqunwei on 15/3/8.
 */
public class GrabFactory {

    public static IGrab build(String url){

        if (!(url.endsWith(".css") || url.endsWith(Main.PAGE_EXTRA)
                || url.endsWith(".js")
                || url.endsWith(".woff")
                || url.endsWith(".ttf")
                || url.endsWith(".svg")
                || url.endsWith(".png")
                || url.endsWith(".jpg")
                || url.endsWith(".ico")
                || url.endsWith(".gif")
                || url.endsWith(".eot"))) {
            System.out.println("error extent name!!!!!!!===>" + url);
            return null;
        }

        if(url.endsWith(".html")){
            return new HtmlGrab(url);
        }else if (url.endsWith(".css")){
            return new CssGrab(url);
        }else{
            return new PageGrab(url);
        }

//        return null;
    }
}
