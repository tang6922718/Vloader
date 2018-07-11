package spiderProcessor;

import dao.DZDPDao;
import dao.ZCDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class ZhuChengProcessor implements PageProcessor {


    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(2000)
            .setCharset("utf8");
    String helperUrl = "http://www.dianping.com/changde/ch30/\\w+";
    String helperUrl1 = "http://www.dianping.com/dazhu/\\w+";
    String targeUrl = "http://www.dianping.com/shop/\\w+";
    //通过构造方法传参，公用一个爬虫逻辑

    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url
        if (page.getUrl().regex(helperUrl1).match()) {
            List<String> urls = page.getHtml().xpath("//div[@id='shop-all-list']//div[@class='pic']/a/@href").regex(targeUrl).all();
            page.addTargetRequests(urls);

            // 添加下一页
            String nextPage = page.getHtml().xpath("//a[@class='next']/@href").toString();
            page.addTargetRequest(nextPage);


        } else if (page.getUrl().regex(targeUrl).match()) {
            String url = page.getUrl().toString();
            String name = page.getHtml().xpath("//h1[@class='shop-name']/text()").toString();
            String star = page.getHtml().xpath("//span[contains(@class, 'mid-rank-star')]/@title").toString();
            String appraise = page.getHtml().$("div.brief-info", "allText").toString();
            String addr = page.getHtml().xpath("//span[@itemprop='street-address']//text()").toString();
            String phone = page.getHtml().xpath("//span[@itemprop='tel']//text()").toString();
            String comment  = page.getHtml().$("div#comment","allText").toString();
            System.out.println(url + "\r\n" + name + "\r\n" + appraise + "\r\n" + addr + "\r\n" + phone);

            ZCDao.insertXieLou(url,"休闲娱乐",name,star,addr,phone,appraise,comment);

        }


    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        String[] urls = {
               "http://www.dianping.com/dazhu/ch30"
        };
        Spider spider = Spider.create(new ZhuChengProcessor()).addPipeline(new ConsolePipeline());
        spider.addUrl(urls);
        spider.thread(40).start();
    }

}
