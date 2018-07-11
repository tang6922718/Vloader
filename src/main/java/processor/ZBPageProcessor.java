package processor;

import bean.ZBean;
import dao.ZBDao;
import test.ReMatcher;
import test.SearchProjectNum;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static us.codecraft.webmagic.selector.Selectors.xpath;

public class ZBPageProcessor implements PageProcessor {

    String urlRe = "http://www.gxzfcg.gov.cn/view/staticpags/sxjcg_zbgg/.*[a-z_0-9/].html";
    String helpUrl = "http://www.gxzfcg.gov.cn/CmsNewsController/getCmsNewsList/channelCode-sxjcg_zbgg/param_bulletin/20/page_\\d+.html";

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setDomain("http://www.gxzfcg.gov.cn")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(1000)
            .setCharset("utf-8");

    public void process(Page page) {
        //编写抓取逻辑
        DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH表示24小时制；
        String nowDate = dFormat.format(new Date());
        List<String> list = new ArrayList<String>();
        for (int i = 3001; i <=4000; i++) {
            page.addTargetRequest("http://www.gxzfcg.gov.cn/CmsNewsController/getCmsNewsList/channelCode-sxjcg_zbgg/param_bulletin/20/page_" + i + ".html");
        }
        if (page.getUrl().regex(helpUrl).match()) {
            list = page.getHtml().xpath("//*[@id=\"channelBody\"]/div/ul/li").all();
            for (String list1 : list) {
                String url = xpath("li/a/@href").select(list1).toString();
                String area = xpath("li/a/span/text()").select(list1).toString();
                String time = xpath("//span[@class=\"date\"]/text()").select(list1).toString();
                String listUrl = page.getUrl().toString();
                Request request = new Request(getSite().getDomain() + url).setPriority(1).putExtra("area", area).putExtra("time", time).putExtra("listUrl", listUrl);
                page.addTargetRequest(request);
            }
        }else  if (page.getUrl().regex(urlRe).match()){
            String content = page.getHtml().xpath("//*[@id=\"bodyMain\"]/div/div/div[2]/div[2]").toString();
            String content1 = page.getHtml().$("#bodyMain","allText").all().toString();
            ZBean zBean = new ZBean();
            Map map = SearchProjectNum.search( ReMatcher.reOne(content, "[A-Z0-9-\\[\\]]{4,}").toString());
            if (map.size()!=0) {
                zBean.setArea(page.getRequest().getExtra("area").toString());
                zBean.setUrl(page.getUrl().toString());
                zBean.setUrlList(page.getRequest().getExtra("listUrl").toString());
                zBean.setTitle(page.getHtml().xpath("//div[@class=\"reportTitle\"]/h1/text()").toString());
                zBean.setContent(content1);
                zBean.setPublishDate(page.getRequest().getExtra("time").toString());
                zBean.setProjectCode(ReMatcher.reOne(content, "[A-Z0-9-\\[\\]]{4,}").toString());
                zBean.setCcgpUrl(map.get("ccgpUrl").toString());
                zBean.setCcgpHtml(map.get("ccgpHtml").toString());
                zBean.setAddTime(nowDate);
            }else {
                zBean.setArea(page.getRequest().getExtra("area").toString());
                zBean.setUrl(page.getUrl().toString());
                zBean.setUrlList(page.getRequest().getExtra("listUrl").toString());
                zBean.setTitle(page.getHtml().xpath("//div[@class=\"reportTitle\"]/h1/text()").toString());
                zBean.setContent(content1);
                zBean.setPublishDate(page.getRequest().getExtra("time").toString());
                zBean.setProjectCode(ReMatcher.reOne(content, "[A-Z0-9-\\[\\]]{4,}").toString());
                zBean.setCcgpUrl("");
                zBean.setCcgpHtml("");
                zBean.setAddTime(nowDate);
            }

            ZBDao.insertID(zBean);
//            System.out.println(map.get("ccgpTitle"));
        } else {
            System.out.println("这是一个详情页");
        }


//        page.putField("name",page.getHtml().xpath("//div[@class='reportTitle']/h1").toString());
//        page.putField("detail",page.getHtml().xpath("//*[@id='bodyMain']/div/div/div[2]/div[2]/text()").all());
    }

    public Site getSite() {
        return site;
    }

    private List<String> saveNewsListData(String pageUrl, String pindaoId) {
        return null;
    }


}
