package processor.news;

import bean.NewsBean;
import dao.NewsDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class NewsProcessor implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(1000)
            .setCharset("utf-8");
    String helperUrl = "http://www.guancha.cn/Search/.*?";
    String targeUrl = "http://www.guancha.cn/local/.*?";

    //通过构造方法传参，公用一个爬虫逻辑
    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url
        if (page.getUrl().regex(helperUrl).match()) {
            for (int i = 2; i <= 29; i++) {
                    page.addTargetRequest("http://www.guancha.cn/Search/?k=%E5%8D%A0%E4%B8%AD&y=1&ps=20&pi=" + i);
                //关键词林郑月娥
//                page.addTargetRequest("http://www.guancha.cn/Search/?k=%E6%9E%97%E9%83%91%E6%9C%88%E5%A8%A5&y=1&ps=20&pi=" + i);
            }
            List<String> url2 = page.getHtml().xpath("//dd/h4/a/@href").all();
            page.addTargetRequests(url2);
        } else {
            String pageContent = page.getHtml().$("body > div.content", "allText").toString();
            String pageUrl = page.getUrl().toString();
            String title=page.getHtml().xpath("//li[@class=\"left left-main\"]/h3/text()").toString();
            String publishDate=page.getHtml().xpath("//div[@class=\"time fix\"]/span/text()").toString();
            NewsBean  newsBean = new NewsBean(pageUrl,pageContent,title,publishDate);
            NewsDao.insertNews(newsBean);
        }
    }


    public Site getSite() {
        return site;
    }

}
