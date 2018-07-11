package processor;

import bean.SoftBean;
import bean.WinBidBean;
import dao.SoftDao;
import downloader.SeleniumDownloader;
import test.ReMatcher;
import test.SearchProjectNum;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

import us.codecraft.webmagic.downloader.PhantomJSDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;


public class QNProcessor implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(1000)
            .setCharset("utf8");
    String helperUrl = "http://xiazai.zol.com.cn/pupils_soft_index/pupils_page_2.html";
    String targeUrl = "";

    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url


    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Spider.create(new QNProcessor())
                .addUrl("http://hotel.qunar.com/city/yueyang/")
                .addPipeline(new ConsolePipeline())
                .setDownloader(new PhantomJSDownloader("C:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe"))
                .thread(20)
                .start();
    }
}
