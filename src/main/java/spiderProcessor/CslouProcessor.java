package spiderProcessor;

import bean.CslouBean;
import dao.CslouDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.XpathSelector;
import util.XpathExtractor;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;

import static us.codecraft.webmagic.selector.Selectors.$;
import static us.codecraft.webmagic.selector.Selectors.xpath;

public class CslouProcessor implements PageProcessor {


    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(5000)
            .setCharset("utf8");
    String helperUrl = "http://loupan.cslou.com/search_\\d+.html";
    String helperUrl1 = "http://vacations.ctrip.com/tours/[\\w\\-]+";
    String targeUrl = "http://loupan.cslou.com/\\w+";
    String targeUrl1 = "http://loupan.cslou.com/\\w+\\-detail";
    //通过构造方法传参，公用一个爬虫逻辑

    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url
        if (page.getUrl().regex(helperUrl).match()) {

            List<String> urls = page.getHtml().xpath("//strong[@class='f14']//a[@class='fontblue']/@href").all();
            page.addTargetRequests(urls);

        } else if (page.getUrl().regex(targeUrl).match()) {
            String url = page.getUrl().toString();

            String name = page.getHtml().xpath("//h1[@class='lp-tit-title']//text()").toString();

            String phone = page.getHtml().xpath("//div[@class='a2']//text()").toString();

            //0物业类别 -  纯写字楼  1 物业区域 -  雨花区    2 总层数 or 开盘时间 -""  3开发商 or 装修情况- 长沙兴业成房地产开发有限公司  4 物业费 or 开发商 - 暂无
            List<String> allList = page.getHtml().xpath("//div[@class='ul_f']/ul/li").all();

            String type = "";
            String area = "";
            String floorNum = "";
            String developers = "";

            String opening_time = "";
            String decorateState= "";

            String addr = page.getHtml().xpath("//*[@id=\"louAddress\"]//text()").toString();
            String detail = page.getHtml().xpath("//div[@class='nhbox w958']/p//text()").toString();
            //处理两种不同的结构
            //层数 list为8
            if (allList.size() == 8) {
                //物业类型
                type = xpath("//span[@class='td_1']/text()").select(allList.get(0)).toString();
                //物业区域
                area = xpath("//span[@class='td_1']/text()").select(allList.get(1)).toString();
                //楼层
                floorNum = xpath("//span[@class='td_1']/text()").select(allList.get(3)).toString();
                //开发商
                developers = xpath("//span[@class='td_1']/text()").select(allList.get(4)).toString();

                System.out.println(url + "\r\n" + name + "\r\n" + type + "\r\n" + addr + "\r\n" + area + "\r\n"
                        + phone+"\r\n" + floorNum + "\r\n" + developers +"\r\n" +detail);
                //入库
                CslouBean cslouBean = new CslouBean(url,name,type,addr,area,phone,floorNum,"/","/",developers,detail);
                CslouDao.insertXieLou(cslouBean);
            } else {
                //物业类型
                type = xpath("//span[@class='td_1']/text()").select(allList.get(0)).toString();
                //物业区域
                area = xpath("//span[@class='td_1']/text()").select(allList.get(1)).toString();
                //开盘时间
                opening_time = xpath("//span[@class='td_1']/text()").select(allList.get(3)).toString();
                //装修情况
                decorateState = xpath("//span[@class='td_1']/text()").select(allList.get(4)).toString();
                //开发商
                developers = xpath("//span[@class='td_1']/text()").select(allList.get(5)).toString();
                System.out.println(url + "\r\n" + name + "\r\n" + type + "\r\n" + addr+"\r\n" + area + "\r\n"
                        + phone + "\r\n" + opening_time + "\r\n" + decorateState + "\r\n" + developers
                        + "\r\n" + detail);
                CslouBean cslouBean = new CslouBean(url,name,type,addr,area,phone,"/",opening_time,decorateState,developers,detail);
                CslouDao.insertXieLou(cslouBean);
            }
        }


    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        List<String> list = new ArrayList<String>();

        for (int i = 1; i <= 11; i++) {
            list.add("http://loupan.cslou.com/search_" + i + ".html");
        }
        Spider spider = Spider.create(new CslouProcessor()).addPipeline(new ConsolePipeline());
        spider.startUrls(list);
        spider.thread(20).start();
    }

}
