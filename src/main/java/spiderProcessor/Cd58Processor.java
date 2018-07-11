package spiderProcessor;

import bean.Cd58Bean;
import bean.CslouBean;
import dao.Cd58Dao;
import dao.CslouDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

import static us.codecraft.webmagic.selector.Selectors.$;
import static us.codecraft.webmagic.selector.Selectors.xpath;

public class Cd58Processor implements PageProcessor {


    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(5000)
            .setCharset("utf8");
    String helperUrl = "http://changde.58.com/zhaozu/pn\\w+";

    String targeUrl = "http://changde.58.com/zhaozu/\\w+.shtml";
    //通过构造方法传参，公用一个爬虫逻辑

    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url
        if (page.getUrl().regex(helperUrl).match()) {
            List<String> urls = page.getHtml().xpath("//ul[@class='house-list-wrap']//h2/a/@href").all();
            page.addTargetRequests(urls);
        } else {
            List<String> li = page.getHtml().xpath("//ul[@class='house_basic_title_content xzl-house_basic_title_content']/li").all();
            String url = page.getUrl().toString();
            String name = page.getHtml().xpath("//div[@class='house-title']/h1//text()").toString();
            String price = page.getHtml().xpath("//div[@class='house-basic-right fr']/p[@class='house_basic_title_money']/span//text()").toString();
            String area = xpath("//span[@class='house_basic_title_content_item2']//text()").select(li.get(0)).toString().trim();
            String type = xpath("//a[@class='house_basic_title_content_item3 blue-link']//text()").select(li.get(1)).toString().trim();
            String houses = xpath("//span[@class='house_basic_title_content_item2']//text()").select(li.get(3)).toString().trim();
            String addr = $("li","allText").select(li.get(4)).toString().trim();
            String phone = page.getHtml().xpath("//p[@class='phone-num']//text()").toString();
            System.out.println(url + "\r\n" + name + "\r\n" + price + "\r\n" + area + "\r\n" + type
                    + "\r\n" + houses + "\r\n" + addr + "\r\n" + phone);
            Cd58Bean cd58Bean = new Cd58Bean(url,name,addr,area,type,phone,price,houses);

            Cd58Dao.insertXieLou(cd58Bean);

        }

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        List<String> list = new ArrayList<String>();

        for (int i = 34; i >=1 ; i--) {
            list.add("http://changde.58.com/zhaozu/pn" + i);
        }
        Spider spider = Spider.create(new Cd58Processor()).addPipeline(new ConsolePipeline());
        spider.startUrls(list);
        spider.thread(20).start();
    }

}
