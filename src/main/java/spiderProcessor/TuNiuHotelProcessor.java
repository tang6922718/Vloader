package spiderProcessor;

import dao.HotelDao;
import proxy.XiaoDaiLiProxy;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.downloader.PhantomJSDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.JsonPathSelector;
import util.FileOperate;
import util.JacksonMapper;

import javax.xml.bind.SchemaOutputResolver;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TuNiuHotelProcessor implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(4000)
            .addCookie("Cookie", "QN99=8470; QN300=auto_4e0d874a; QunarGlobal=10.86.213.148_-2f156d46_164176f4ce3_6299|1529402442970; QN601=d4ff354a4461f9e82d2301ccfc29687f; _i=ueHd8Zdz0aRuMDJy4yUFGYq-_kjX; QN269=A22BC3F073A711E893C7FA163E9DCB6D; QN48=tc_213b84548037d59e_164177d4e2c_6a89; QN1=ezu0rVso1F1By1ZrCqWKAg==; QN205=auto_4e0d874a; QN277=auto_4e0d874a; QN73=3186-3187; __utmz=183398822.1529477304.4.3.utmcsr=hotel.qunar.com|utmccn=(referral)|utmcmd=referral|utmcct=/city/yueyang/; csrfToken=bjiY2UqssDBrDzNNECAJpuzqoYSm4weC; _vi=WQIgVcBuCR_Tfg1cbxJWyq02-7yeiOt8gSBrrBMVAe_SKp_BxKTx3Ka34tYcU9WT9FB3NDCmVbDahD_lRGpqTADEdjKOYhLxYabTD8Yc9-McpzLn8ehaLzk1CJUbCxDdPxHZMIQkNNge8H8nnUWmKwtgaDN4MEeFYkE_jfllo0ko; QN163=0; QN6=auto_4e0d874a; Hm_lvt_75154a8409c0f82ecd97d538ff0ab3f3=1529402414,1529460297,1529543780; Hm_lpvt_75154a8409c0f82ecd97d538ff0ab3f3=1529543780; QN70=1ca74fdf61641ff4ef66; Qs_lvt_55613=1529461385%2C1529544471; Qs_pv_55613=739697898940801400%2C103566753973411120%2C1628636509664696000%2C4278698140426438000%2C4339322752684999000; Hm_lvt_8fa710fe238aadb83847578e333d4309=1529461387,1529544472; Hm_lpvt_8fa710fe238aadb83847578e333d4309=1529544472; __utma=183398822.1566349577.1529402430.1529477304.1529544472.5; __utmc=183398822; __utmt=1; __utmb=183398822.1.10.1529544472; _jzqa=1.63462026566767420.1529402456.1529477304.1529544473.4; _jzqc=1; _jzqx=1.1529402456.1529544473.4.jzqsr=hotel%2Equnar%2Ecom|jzqct=/city/yueyanglou/.jzqsr=hotel%2Equnar%2Ecom|jzqct=/city/yueyang/dt-2589/; _jzqckmp=1; _jzqb=1.2.10.1529544473.1; QN268=1529544503120_c28bec4238c0460d|1529544507197_969cf7a86f8a042f; QN267=1529544503120_c28bec4238c0460d")
            .setCharset("utf8");
    String helperUrl = "http://hotel.tuniu.com/list.*?";
    String helperUrl2 = "http://hotel.tuniu.com/ajax/list.*?";
    String targeUrl = "";

    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url
        //url,name,city,address,tel,opentime,decorate,totalroom,c_from
        if (page.getUrl().regex(helperUrl).match()) {
            String id = "";
            Matcher matcher = Pattern.compile("\\d+").matcher(page.getUrl().toString());
            if (matcher.find()) {
                id = matcher.group();
            }
            for (int i = 1; i <= 62; i++) {
                page.addTargetRequest("http://hotel.tuniu.com/ajax/list?search%5Bcity%5D=" + id + "&search%5BcheckInDate%5D=2018-07-03&search%5BcheckOutDate%5D=2018-07-04&search%5BcityCode%5D=1504&page=" + i);
            }
        } else if (page.getUrl().regex(helperUrl2).match()) {
            //接口合并请求
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            JsonPathSelector jsonPathSelector = new JsonPathSelector("$.data.list[*].url");
            List<String> list = jsonPathSelector.selectList(page.getRawText());
            for (String s : list) {
                page.addTargetRequest("http://hotel.tuniu.com/ajax/getHotelStaticInfo?id=" + s.replace("/detail/", ""));
            }
        } else {
            //数据异步请求，页面使用js变量赋值，故使用  接口数据  jsonPath解析
            String url = page.getUrl().toString();
            String name = new JsonPathSelector("$..hotelName").select(page.getRawText());
            String city = "郴州";
            String address = new JsonPathSelector("$..address").select(page.getRawText());
            String tel = new JsonPathSelector("$..tel").select(page.getRawText());
            String detail = new JsonPathSelector("$..detail").select(page.getRawText());
            //正则处理
            String hotel_service = new JsonPathSelector("$..texts").select(page.getRawText());
            String opentime = new JsonPathSelector("$..debutYear").select(page.getRawText());
            String decorate = new JsonPathSelector("$..decorateDate").select(page.getRawText());
            String totalroom = new JsonPathSelector("$..roomNumber").select(page.getRawText());
            String c_from = "途牛";
            System.out.println(detail + "\r\n" + hotel_service);
            if (name.equals("[]")) {
                System.out.println("-----------验证码出现，赶快消灭它！！！" + " http://hotel.tuniu.com/list?city=1512");
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                page.addTargetRequest(page.getUrl().toString());
            } else {
                HotelDao.insertData1(url, name, city, address, tel, detail, hotel_service, opentime, decorate, totalroom, c_from);
            }
        }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        String[] urls = {
                "http://hotel.tuniu.com/list?city=1512",                  /*  岳阳酒店*/
                "http://hotel.tuniu.com/list?city=1508",   /* 娄底酒店*/
                "http://hotel.tuniu.com/list?city=1503",  /*常德酒店*/
                "http://hotel.tuniu.com/list?city=1514", /* 张家界酒店*/

                "http://hotel.tuniu.com/list?city=1506",/* 怀化酒店*/
                "http://hotel.tuniu.com/list?city=1515", /* 株洲酒店*/
                "http://hotel.tuniu.com/list?city=1513", /*永州酒店*/
                "http://hotel.tuniu.com/list?city=1510", /* 湘潭酒店*/
                "http://hotel.tuniu.com/list?city=1511",  /* 益阳酒店*/
                "http://hotel.tuniu.com/list?city=1505", /* 衡阳酒店*/
                "http://hotel.tuniu.com/list?city=1509", /*邵阳酒店*/
                "http://hotel.tuniu.com/list?city=1504", /* 郴州酒店*/
                "http://hotel.tuniu.com/list?city=1502"  /*长沙酒店*/

        };


        /*HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(new XiaoDaiLiProxy());*/
        Spider.create(new TuNiuHotelProcessor())
                .addUrl("http://hotel.tuniu.com/list?city=1504")
                .addPipeline(new ConsolePipeline())
                .thread(20)
                .start();
    }
}
