package processor;

import bean.SoftBean;
import dao.SoftDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import util.DownloadImage;

import java.util.List;
import java.util.UUID;

import static us.codecraft.webmagic.selector.Selectors.regex;
import static us.codecraft.webmagic.selector.Selectors.xpath;

public class ZOLAPPProcessor implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(1000)
            .setCharset("utf-8");
    String helperUrl = "http://apps.fjedu.cn/app/list.*?";
    String targeUrl = "http://apps.fjedu.cn/app/app.*?";

    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url
        if (page.getUrl().regex(helperUrl).match()) {
            List<String> list = page.getHtml().xpath("//ul[@class=\"app-list clearfix\"]/li/a/@onclick").all();
            for (String list1 : list) {
                String url = "http://apps.fjedu.cn/app/" + regex("app.*?\\d+").select(list1).toString();
                page.addTargetRequest(url);
            }
        }

        if (page.getUrl().regex(targeUrl).match()) {
            String appUrl = page.getUrl().toString().trim();
            String name = page.getHtml().xpath("//h3[@class=\"title-2\"]/text()").toString();
            String appIntroduce = page.getHtml().$(".app-introduce","allText").toString();
            DownloadImage.createFile("G:\\SoftStore\\" + name.trim()+"\\", name.trim() + "detai", appUrl+"  ----- "+appIntroduce);
            //下载图片
            String iconUrl = page.getHtml().xpath("//img[@class=\"fl\"]/@src").toString();
            try {
                DownloadImage.download(iconUrl,name.trim(), "G:\\SoftStore\\"+ name.trim());

                List<String> appImg = page.getHtml().xpath("//ul[@class=\"clearfix\"]/li/a/img/@src").all();
                for (String list1:appImg) {
                    DownloadImage.download(list1,UUID.randomUUID().toString().replaceAll("-",""), "G:\\SoftStore\\" + name.trim());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public Site getSite() {
        return site;
    }

    private List<String> saveNewsListData(String pageUrl, String pindaoId) {
        return null;
    }

//    oprationDriver
}
