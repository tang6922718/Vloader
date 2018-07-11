package processor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import util.CMP;
import util.FileOperate;
import util.RedisOperate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static kafka.SaveToKafka.sendMsgToKafka;
import static us.codecraft.webmagic.selector.Selectors.$;
import static us.codecraft.webmagic.selector.Selectors.xpath;

public class Travel1Processor implements PageProcessor {

    private static Producer<String, String> producer;
    private  static String topic ="";
    static {
        Properties props = new Properties();
        Properties props1 = new Properties();
        try {
            props.load( Files.newInputStream( Paths.get( "./kafka.properties" )));
            props1.put("bootstrap.servers", props.getProperty("bootstrap.servers"));//服务器ip:端口号，集群用逗号分隔
            props1.put("acks", "all");
            props1.put("retries", 0);
            props1.put("batch.size", 16384);
            props1.put("linger.ms", 200);
            props1.put("buffer.memory", 33554432);
            props1.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props1.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            topic=props.getProperty("topic");
            producer = new KafkaProducer<>(props1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    CMP cmp;

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(5000)
            .setCharset("utf8");
    String helperUrl = "http://vacations.ctrip.com/\\B";
    String helperUrl1 = "http://vacations.ctrip.com/tours/[\\w\\-]+";
    String targeUrl = "http://\\w+.ctrip.com.*.html";
    //通过构造方法传参，公用一个爬虫逻辑
   static  long  num=0;
    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url

        if (page.getUrl().regex(targeUrl).match()) {
            String pageUrl = page.getUrl().toString();
            String title = page.getHtml().xpath("/html/head/title/text()").toString();
//            String price = page.getHtml().$("strong.total_price","allText").toString();
//            String crawlerDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();
            List<String>  comment = page.getHtml().$("ul.detail_comment_list > li").all();
            for (int i = 0; i < comment.size() ; i++) {
                String comment1 = $("li","allText").select(comment.get(i)).toString();
               /* String commentStar =xpath("//strong/text()").select(comment.get(i)).toString();
                String user =xpath("//p[@class='user_id']/text()").select(comment.get(i)).toString();*/
              /*  String time =xpath("//p[@class='detail_comment_tips']/a/text()").select(comment.get(i)).toString();*/
               String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                /*System.out.println(title + "\r\n" + pageUrl + "\r\n" +comment1 + "\r\n"+commentStar
                        + "\r\n" +user + "\r\n" +time);*/
                String temp = "4|15|24|25|26|27|29|";
                String data = temp + pageUrl + "|" + "" + "|" + comment1.replaceAll("|","") + "|" + title + "|" + "" + "|" + time+
                        "|" + "" + "|" + "" + "|"
                        + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + ""+"\r\n";
                sendMsgToKafka(topic, data);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前爬取页面进度：-》》》》》》"+(num++));
                /*FileOperate.writeStringToFile(data,"F:\\Vloader\\Text\\","travel");*/
                System.out.println("正在kafka发送数据:" + data);
            }

            //ul[@class='detail_comment_list']ravelBean travelBean = new TravelBean(pageUrl,title,price,comment,crawlerDate);
//            TravelDao.insertNews(travelBean);

        } else if (page.getUrl().regex(helperUrl1).match()) {
            List list1 = page.getHtml().xpath("//*[@id=\"_prd\"]").links().regex("//\\w+.ctrip.com.*.html").all();
            page.addTargetRequests(list1);
            String url1 = page.getHtml().xpath("//a[contains(@class,'down')]/@href").toString();
            page.addTargetRequest(url1);

        } else if (page.getUrl().regex(helperUrl).match()) {
            List list = page.getHtml().$(" ul.destination_list").links().regex("//vacations.ctrip.com/tours/[\\w\\-]+").all();
            page.addTargetRequests(list);
        }
    }

    public Site getSite() {
        return site;
    }
    public CMP getCmp() {
        return cmp;
    }

    public void setCmp(CMP cmp) {
        this.cmp = cmp;
    }

    public static void main(String[] args) {
/*      驴妈妈网页评论
        String[] area = {"成都", "上海", "上海", "苏州", "杭州", "北京"
                , "太原", "广州", "重庆", "三亚", "常州", "湖州", "青岛", "天津", "深圳"
                , "昆明", "海口", "南京", "嘉兴", "南通", "无锡", "西安"};

        List<String> list = new ArrayList<>();
        for (int i = 0; i < area.length; i++) {
            list.add("http://s.lvmama.com/route/H258K510100?keyword=" + area[i] + "&k=0#list");
        }*/
        //携程爬取
        String[] area = {"成都", "上海", "上海", "苏州", "杭州", "北京"
                , "太原", "广州", "重庆", "三亚", "常州", "湖州", "青岛", "天津", "深圳"
                , "昆明", "海口", "南京", "嘉兴", "南通", "无锡", "西安"};

        List<String> list = new ArrayList<>();
        for (int i = 0; i < area.length; i++) {
            list.add(area[i]);
        }
        Travel1Processor travel1Processor = new Travel1Processor();
        Spider spider = Spider.create(travel1Processor).addPipeline(new ConsolePipeline());
        RedisOperate redisOperate = new RedisOperate(args,spider);
        travel1Processor.setCmp(redisOperate);
        spider.addUrl("http://vacations.ctrip.com/");
        spider.thread(20).start();
    }

}
