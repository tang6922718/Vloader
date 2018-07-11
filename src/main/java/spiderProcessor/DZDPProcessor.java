package spiderProcessor;

import bean.Cd58Bean;
import dao.Cd58Dao;
import dao.DZDPDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

import static us.codecraft.webmagic.selector.Selectors.$;
import static us.codecraft.webmagic.selector.Selectors.xpath;

public class DZDPProcessor implements PageProcessor {


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
        if (page.getUrl().regex(helperUrl).match()) {
            List<String> urls = page.getHtml().xpath("//div[@id='shop-all-list']//div[@class='pic']/a/@href").regex(targeUrl).all();
            page.addTargetRequests(urls);

            // 添加下一页
            String nextPage = page.getHtml().xpath("//a[@class='next']/@href").toString();
            page.addTargetRequest(nextPage);


        } else if (page.getUrl().regex(targeUrl).match()) {
            String url = page.getUrl().toString();
            String area = page.getHtml().xpath("//div[@class='breadcrumb']/a[3]//text()").toString();
            String name = page.getHtml().xpath("//h1[@class='shop-name']/text()").toString();
            String star = page.getHtml().xpath("//span[contains(@class, 'mid-rank-star')]/@title").toString();
            String appraise = page.getHtml().$("div.brief-info", "allText").toString();
            String addr = page.getHtml().xpath("//span[@itemprop='street-address']//text()").toString();
            String phone = page.getHtml().xpath("//span[@itemprop='tel']//text()").toString();
            String type = page.getHtml().xpath("//div[@class='breadcrumb']/a[2]//text()").toString();
            System.out.println(url + "\r\n" + area + name + "\r\n" + appraise + "\r\n" + addr + "\r\n" + phone);

            DZDPDao.insertXieLou(url, area, type, name, star, appraise, addr, phone);
        }
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
        Spider spider = Spider.create(new DZDPProcessor()).addPipeline(new ConsolePipeline());
        spider.addUrl(urls);
        spider.thread(40).start();
    }

}
