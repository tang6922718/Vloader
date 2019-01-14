package spiderProcessor.hunan;

import cn.hutool.core.util.ReUtil;
import dao.ZCYDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZCYProcessor implements PageProcessor {


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
        String url ="http://www.yt1998.com";
        List<String> lists = new JsonPathSelector("$.data").selectList(page.getRawText());
        for (String list : lists) {
            String name = new JsonPathSelector("$.ycnam").select(list);
            String pri = new JsonPathSelector("$.pri").select(list);
            String time = new JsonPathSelector("$.dtm").select(list);
            String shichang = new JsonPathSelector("$.shichang").select(list);
            String chandi = new JsonPathSelector("$.chandi").select(list);

            String guige = new JsonPathSelector("$.guige").select(list);
            String zoushi = new JsonPathSelector("$.zoushi").select(list);
            String yueduibi = new JsonPathSelector("$.yueduibi").select(list);
            yueduibi= ReUtil.get("(?<=>)[^<>]+(?=<)", yueduibi, 0);
            String nianduibi = new JsonPathSelector("$.nianduibi").select(list);
            nianduibi= ReUtil.get("(?<=>)[^<>]+(?=<)", nianduibi, 0);

            Map<String,String> map = new HashMap<>();
            map.put("url",url);
            map.put("name",name);
            map.put("pri",pri);
            map.put("time",time);
            map.put("shichang",shichang);

            map.put("chandi",chandi);
            map.put("guige",guige);
            map.put("zoushi",zoushi);
            map.put("yueduibi",yueduibi);
            map.put("nianduibi",nianduibi);

            ZCYDao.insertXieLou(map);
        }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Spider spider = Spider.create(new ZCYProcessor()).addPipeline(new ConsolePipeline());
        spider.addUrl("http://www.yt1998.com/price/nowDayPriceQ!getPriceList.do?random=0.09412667671086394&ycnam=&market=&leibie=&istoday=&spices=&paramName=&paramValue=&pageIndex=0&pageSize=4915");
        spider.thread(20).start();
    }

}
