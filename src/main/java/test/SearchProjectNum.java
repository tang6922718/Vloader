package test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SearchProjectNum {

    public static void main(String[] args) {

    }

    public static Map<String, String> search(String projectNum) {
        Map<String, String> map = new HashMap<>(4);
        String searchUrl = null;

        searchUrl = "http://search.ccgp.gov.cn/bxsearch?searchtype=2&page_index=1&bidSort=0&buyerName=&projectId=" + projectNum + "&pinMu=0&bidType=0&dbselect=bidx&kw=&start_time=2013%3A01%3A01&end_time=2018%3A01%3A01&timeType=6&displayZone=&zoneId=&pppStatus=&agentName=";

        try {
            Document doc = Jsoup.connect(searchUrl).get();
            Element detaulUrlEle = doc.select("div.vT-srch-result-list>ul>li>a").first();
            if (detaulUrlEle == null) {
                doc = Jsoup.connect(searchUrl).get();
                detaulUrlEle = doc.select("div.vT-srch-result-list>ul>li>a").first();
            }
            String detailUrl = detaulUrlEle.attr("href");
            Document detailDocument = Jsoup.connect(detailUrl).get();
//            String title =detailDocument.select("head>title").text();
            String html = detailDocument.html().replaceAll( "          |         |\n|\t","" );
            map.put("ccgpUrl", detailUrl);
            map.put("ccgpHtml", html);
//            map.put( "ccgpTitle",title );
        } catch (Exception e) {
            return map;
        }

        return map;
    }
}
