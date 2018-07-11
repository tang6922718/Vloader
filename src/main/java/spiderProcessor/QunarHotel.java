package spiderProcessor;

import bean.HotelBean;
import dao.HotelDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.PhantomJSDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import util.FileOperate;
import util.StringClean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QunarHotel implements PageProcessor {
    private Site site = Site.me().setDomain("hotel.qunar.com")
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.170 Safari/537.36")
            .setTimeOut(5000)
            .setRetryTimes(3)
            .setCharset("UTF-8")
            .setSleepTime(500);
    @Override
    public void process(Page page) {
        HotelBean bean = new HotelBean();
        Date date = new Date();
        String sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        StringClean sc = new StringClean();
        String name=page.getHtml().xpath("//*[@id=\"detail_pageHeader\"]/h2/span/text()").toString();
        String addr =page.getHtml().xpath("//*[@id=\"detail_pageHeader\"]/p/span[1]/text()").toString();
        if (name==null){
             name=page.getHtml().xpath("//span[@class=\"title\"]/text()").toString();
             addr =page.getHtml().xpath("//p[@class=\"adress\"]/span[1]/text()").toString();
        }


        bean.setName(name);
        bean.setGrade(page.getHtml().xpath("//*[@id=\"detail_pageHeader\"]/h2/em/text()").toString());
        bean.setUrl(page.getUrl().toString());
        bean.setId(sc.QunarIdClean(page.getUrl().toString()));
        bean.setCity("长沙");
        bean.setAddress(addr);
        bean.setTel(sc.TelClean(page.getHtml().xpath("//*[@id=\"descContent\"]/div/dl[1]/dd/cite[1]/text()").toString()));
        bean.setRate(page.getHtml().xpath("//*[@id=\"lastInfo\"]/div[1]/div/span[1]/allText()").toString());
        bean.setOpentime(sc.QunarDetailClean((page.getHtml().xpath("//*[@id=\"descContent\"]/div/dl[2]/allText()").toString()),"开业"));
        bean.setTotalroom(sc.QunarDetailClean((page.getHtml().xpath("//*[@id=\"descContent\"]/div/dl[2]/allText()").toString()),"客房"));
        bean.setDecorate(sc.QunarDetailClean((page.getHtml().xpath("//*[@id=\"descContent\"]/div/dl[2]/allText()").toString()),"装修"));
        bean.setHotel_content(page.getHtml().xpath("//*[@id=\"js_hotelInfo_descAll\"]/p/text()").toString());
        bean.setC_date(sdf);
        bean.setC_from("去哪儿");
        HotelDao.insertData(bean);
//        System.out.println(page.getHtml().xpath("//*[@id=\"descContent\"]/div/dl[2]/allText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        List strings = FileOperate.readStringFromFile("Text/", "长沙酒店url");
        Spider.create(new QunarHotel())
                .startUrls(strings)
                .addPipeline(new ConsolePipeline())
                .thread(20)
                .start();
    }


}
