package processor;

import bean.TravelBean;
import dao.TravelDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;
import util.CMP;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelProcessor implements PageProcessor {
    CMP cmp;
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(2000)
            .setCharset("utf8");
    String helperUrl = "http://s.lvmama.com/route.*?";
    String helperUrl1 = "http://dujia.lvmama.com/\\w+/\\d+";
    String targeUrl = "http://dujia.lvmama.com/\\w+/\\d+";
    //通过构造方法传参，公用一个爬虫逻辑

    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url
        if (page.getUrl().regex(helperUrl).match()) {
            List<String> urls = page.getHtml().xpath("//div[@class='product-list']").links().regex(helperUrl1).all();
            page.addTargetRequests(urls);
            //分析ajax的下一页请求
            Request request = new Request("http://"+page.getHtml().xpath("//a[@class='nextpage']/@onclick").regex("s.lvmama.com.*?list").toString().replaceAll("<",".").replaceAll(">","/"));
            request.setMethod(HttpConstant.Method.POST);
            Map map = new HashMap();
            map.put("ajaxKey","add");
            try {
                request.setRequestBody(HttpRequestBody.form(map,"utf8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            page.addTargetRequest(request);
        } else if (page.getUrl().regex(helperUrl1).match()) {
            String pageUrl = page.getUrl().toString();
            String title = page.getHtml().xpath("//h1[@class='detail_product_tit']//text()").toString();
            if (title == null || "".equals(title)) {
                title = page.getHtml().xpath("//h1[@class='product_top_tit']//text()").toString();
            }
            String currentPrice = page.getHtml().xpath("//span[@class='price_num']/dfn/text()").toString();
            if (currentPrice == null || "".equals(currentPrice)) {
                currentPrice = page.getHtml().xpath("//span[@class='product_price']//text()").toString();
            }
            String crawlerDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();
            String comment = page.getHtml().$(".comment-li", "allText").all().toString();
            Map map = new HashMap<String, Object>();
            map.put("pageUrl", pageUrl);
            map.put("title", title);
            map.put("currentPrice", currentPrice);
            map.put("comment", comment);
            System.out.println(pageUrl + "\r\n" + title + "\r\n" + currentPrice + "\r\n" + crawlerDate);
            //存库
            TravelBean travelBean = new TravelBean(pageUrl,title,currentPrice,comment,crawlerDate);
            TravelDao.insertNews(travelBean);
         /*   cmp.sendData(map);*/
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
}
