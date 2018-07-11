package processor.news;

import bean.NewsBean;
import dao.NewsDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class News2Processor implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(1000)
            .setCharset("utf-8");
    String helperUrl = "http://search.sina.com.cn/.*?";
    String targeUrl = "http://www.guancha.cn/local/.*?";

    //通过构造方法传参，公用一个爬虫逻辑
    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url
        if (page.getUrl().regex(helperUrl).match()){
            List<String> urls =  page.getHtml().xpath("//div[@id=\"result\"]").links().all();
           page.addTargetRequests(urls);
        }else {
            String pageUrl =  page.getUrl().toString();
            String pageContent = page.getHtml().$("#article","allText").toString();
            String title=page.getHtml().xpath("//h1[@class=\"main-title\"]/text()").toString();
            String publishDate=page.getHtml().xpath("//span[@class=\"date\"]/text()").toString();
            NewsBean newsBean = new NewsBean(pageUrl,pageContent,title,publishDate);
            NewsDao.insertNews(newsBean);
        }
    }



    public Site getSite() {
        return site;
    }

}
