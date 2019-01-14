package spiderProcessor.hunan;

import dao.ZCYDao;
import org.xml.sax.SAXException;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import util.XpathExtractor;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static us.codecraft.webmagic.selector.Selectors.$;
import static us.codecraft.webmagic.selector.Selectors.xpath;

public class ZyczycProcessor implements PageProcessor {


    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(5000)
            .setCharset("utf8");
    String helperUrl = "http://www.zyczyc.com";
    String targeUrl = "http://changde.58.com/zhaozu/\\w+.shtml";
    boolean flag = true;

    //通过构造方法传参，公用一个爬虫逻辑
    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url
        //获取总页数，循环爬去后续
     /*   if (flag) {
            int pageNum = Integer.parseInt(page.getHtml().xpath("//*[@id='ContentPlaceHolder1_lbPgCount']//text()").toString());
            for (int i = 1; i <= pageNum; i++) {

                page.addTargetRequest("http://www.zyczyc.com/info/JiaGe.aspx?p=" + i);
            }
            flag = false;
        } else {

*/
        try {
            String time = page.getHtml().xpath("//span[@id='ContentPlaceHolder1_LabelDtm']/text()").toString();
            List<String> lists = page.getHtml().xpath("//table[@id='ContentPlaceHolder1_GridView1']//tr[@align='center']").all();
            for (String list : lists) {
                String name = XpathExtractor.extract(list, "/tr/td/a/text()");
                String guige = XpathExtractor.extract(list, "/tr/td[2]");
                String chandi = XpathExtractor.extract(list, "/tr/td[3]");
                String pri = XpathExtractor.extract(list, "/tr/td[4]");
                String zoushi = XpathExtractor.extract(list, "/tr/td[5]");
                String ydb = XpathExtractor.extract(list, "/tr/td[8]");
                String ndb = XpathExtractor.extract(list, "/tr/td[6]");
                Map<String, String> map = new HashMap<>();
                map.put("url", helperUrl);
                map.put("name", name);
                map.put("pri", pri);
                map.put("time", time);
                map.put("shichang", "-");

                map.put("chandi", chandi);
                map.put("guige", guige);
                map.put("zoushi", zoushi);
                map.put("yueduibi", ydb);
                map.put("nianduibi", ndb);
                ZCYDao.insertXieLou(map);

            }
            String next_url = XpathExtractor.extract(page.getHtml().xpath("//table[@id='ContentPlaceHolder1_tblFenye']//tr").toString()
                    , "//a[contains(text(),'下一页')]/@href");
            System.out.println("next-url->>>>>>>>>>>>>"+helperUrl+next_url);
            page.addTargetRequest(helperUrl+next_url);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Spider spider = Spider.create(new ZyczycProcessor()).addPipeline(new ConsolePipeline());
        spider.addUrl("http://www.zyczyc.com/info/JiaGe.aspx?p=1");
        spider.thread(20).start();
    }

}
