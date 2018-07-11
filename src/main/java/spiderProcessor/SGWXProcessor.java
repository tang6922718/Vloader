package spiderProcessor;

import dao.DZDPDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class SGWXProcessor implements PageProcessor {


    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(2000)
            .setCharset("utf8");
    String helperUrl = "http://www.dianping.com/changde/ch30/\\w+";

    String targeUrl = "http://www.dianping.com/shop/\\w+";
    //通过构造方法传参，公用一个爬虫逻辑

    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url


    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        String[] urls = {
                "http://www.dianping.com/changde/ch30/r4647",
                "http://www.dianping.com/changde/ch30/r4648",
                "http://www.dianping.com/changde/ch30/c1384",
                "http://www.dianping.com/changde/ch30/c1385",
                "http://www.dianping.com/changde/ch30/c1387",
                "http://www.dianping.com/changde/ch30/c1383",
                "http://www.dianping.com/changde/ch30/c1382",
                "http://www.dianping.com/changde/ch30/c1386",
                "http://www.dianping.com/changde/ch30/c1388"
        };
        Spider spider = Spider.create(new SGWXProcessor()).addPipeline(new ConsolePipeline());
        spider.addUrl(urls);
        spider.thread(40).start();
    }

}
