package processor;

import bean.WinBidBean;
import bean.ZBean;
import dao.ZBDao2;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import test.FantomJs;
import test.ReMatcher;
import test.SearchProjectNum;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.xml.bind.Element;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ZBProcessor1 implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(1000)
            .setCharset("utf-8");
    String helperUrl = "http://www.bhsggzy.cn/gxbhzbw/showinfo/zbgsmore.aspx";
    String targetUrl = "/gx.*&CategoryNum=\\d+";
    DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH表示24小时制；
    String nowDate = dFormat.format(new Date());
    List<String> list = new ArrayList<>();

    public static int pageNum = 2;
    public Boolean flag = true;//收集post请求只进入一次

    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url
        if (page.getUrl().regex(helperUrl).match()) {
            System.out.println("当前URL请求："+page.getRequest().toString());
            System.out.println("当前访问页数："+page.getHtml().xpath("//font[@color=\"red\"]/b/text()").toString());
            System.out.println("当前请求验证值-----》："+page.getHtml().xpath("//input[@id=\"__VIEWSTATE\"]/@value").toString());
            Request request = new Request("http://www.bhsggzy.cn/gxbhzbw/showinfo/zbgsmore.aspx");
            request.setMethod(HttpConstant.Method.POST);
            try {
                Map map = new HashMap();
                map.put("__VIEWSTATE",page.getHtml().xpath("//input[@id=\"__VIEWSTATE\"]/@value").toString());
                map.put("__EVENTTARGET","MoreInfoListZbgs1$Pager");
                map.put("__VIEWSTATEGENERATOR","BEB32BDF");
                map.put("__EVENTARGUMENT",pageNum);
                map.put("__VIEWSTATEENCRYPTED","");
                request.setRequestBody(HttpRequestBody.form(map, "utf-8"));
//                request.addHeader("Cookie", "ASP.NET_SessionId=he2oobifir41f3r0aexjsrnh");
//                request.addHeader("Content-Type", "application/x-www-form-urlencoded");
//                request.addHeader("Host", "www.hcggzy.cn");
//                request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
//                request.addHeader("Upgrade-Insecure-Requests", "1");
//                request.addHeader("Referer", "http://www.hcggzy.cn/gxhczbw/showinfo/zbgsmore.aspx");
//                request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                page.addTargetRequest(request);
                pageNum++;

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            List<String> listUrl = page.getHtml().xpath("//*[@id=\"MoreInfoListZbgs1_moreinfo\"]").links().regex(targetUrl).all();
            page.addTargetRequests(listUrl);
        }
       // 抽取数据
        if (page.getUrl().regex(targetUrl).match()) {
            String title = page.getHtml().xpath("//*[@id='lblTitle']//text()").toString();
            String content = page.getHtml().$("#tblInfo","allText").toString();
            String num = ReMatcher.reOne(content, "[A-Z0-9-\\[\\]]{4,}").toString();
            String url = page.getUrl().toString();
            Map map = SearchProjectNum.search(num);

                WinBidBean winBidBean = new WinBidBean();
                winBidBean.setArea("");
                winBidBean.setAddTime(nowDate);
                winBidBean.setUrl(url);
                winBidBean.setContent(content);
                winBidBean.setProjectCode(num);

                winBidBean.setTitle(title);
                winBidBean.setUrlList("");
                winBidBean.setPublishDate("");
                winBidBean.setWebsiteCity("北海市");
                winBidBean.setWebsiteSource("17");
                winBidBean.setRead_label("0");
                winBidBean.setValue2("");

            if (map.get("ccgpHtml")!= null) {
                winBidBean.setCcgpHtml(map.get("ccgpHtml").toString());
                winBidBean.setCcgpUrl(map.get("ccgpUrl").toString());
            } else {
                winBidBean.setCcgpHtml("");
                winBidBean.setCcgpUrl("");
            }
//            ZBDao2.insertWinBidInfo(winBidBean);
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
