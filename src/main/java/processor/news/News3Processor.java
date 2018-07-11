package processor.news;

import bean.NewsBean;
import dao.NewsDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

public class News3Processor implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(5000)
            .setCharset("utf-8");
    String helperUrl = "http://search.cctv.com/search.php.*?";
    String targeUrl = "http://.*?html";
   Boolean flag=true;
    //通过构造方法传参，公用一个爬虫逻辑
    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url

        if (page.getUrl().regex(helperUrl).match()) {
            List<String> urls = page.getHtml().xpath("//h3[@class='tit']/a/@href").regex("http://news.*?shtml").all();
            page.addTargetRequests(urls);
        } else{
            String pageUrl = page.getUrl().toString();
            String pageContent =page .getHtml().$("body", "allText").toString();
            String title = page.getHtml().xpath("//div[@class='cnt_bd']/h1/text()").toString();
            String publishDate = page.getHtml().$("div.function > span.info","allText").toString();
            NewsBean newsBean = new NewsBean(pageUrl, pageContent, title, publishDate);
            NewsDao.insertNews(newsBean);
        }
    }


    public Site getSite() {
        return site;
    }

}
