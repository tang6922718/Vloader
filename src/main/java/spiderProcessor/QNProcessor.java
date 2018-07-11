package spiderProcessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.PhantomJSDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import util.FileOperate;

import java.io.File;
import java.util.List;

public class QNProcessor implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setSleepTime(1000)
            .addCookie("Cookie","QN99=8470; QN300=auto_4e0d874a; QunarGlobal=10.86.213.148_-2f156d46_164176f4ce3_6299|1529402442970; QN601=d4ff354a4461f9e82d2301ccfc29687f; _i=ueHd8Zdz0aRuMDJy4yUFGYq-_kjX; QN269=A22BC3F073A711E893C7FA163E9DCB6D; QN48=tc_213b84548037d59e_164177d4e2c_6a89; QN1=ezu0rVso1F1By1ZrCqWKAg==; QN205=auto_4e0d874a; QN277=auto_4e0d874a; QN73=3186-3187; __utmz=183398822.1529477304.4.3.utmcsr=hotel.qunar.com|utmccn=(referral)|utmcmd=referral|utmcct=/city/yueyang/; csrfToken=bjiY2UqssDBrDzNNECAJpuzqoYSm4weC; _vi=WQIgVcBuCR_Tfg1cbxJWyq02-7yeiOt8gSBrrBMVAe_SKp_BxKTx3Ka34tYcU9WT9FB3NDCmVbDahD_lRGpqTADEdjKOYhLxYabTD8Yc9-McpzLn8ehaLzk1CJUbCxDdPxHZMIQkNNge8H8nnUWmKwtgaDN4MEeFYkE_jfllo0ko; QN163=0; QN6=auto_4e0d874a; Hm_lvt_75154a8409c0f82ecd97d538ff0ab3f3=1529402414,1529460297,1529543780; Hm_lpvt_75154a8409c0f82ecd97d538ff0ab3f3=1529543780; QN70=1ca74fdf61641ff4ef66; Qs_lvt_55613=1529461385%2C1529544471; Qs_pv_55613=739697898940801400%2C103566753973411120%2C1628636509664696000%2C4278698140426438000%2C4339322752684999000; Hm_lvt_8fa710fe238aadb83847578e333d4309=1529461387,1529544472; Hm_lpvt_8fa710fe238aadb83847578e333d4309=1529544472; __utma=183398822.1566349577.1529402430.1529477304.1529544472.5; __utmc=183398822; __utmt=1; __utmb=183398822.1.10.1529544472; _jzqa=1.63462026566767420.1529402456.1529477304.1529544473.4; _jzqc=1; _jzqx=1.1529402456.1529544473.4.jzqsr=hotel%2Equnar%2Ecom|jzqct=/city/yueyanglou/.jzqsr=hotel%2Equnar%2Ecom|jzqct=/city/yueyang/dt-2589/; _jzqckmp=1; _jzqb=1.2.10.1529544473.1; QN268=1529544503120_c28bec4238c0460d|1529544507197_969cf7a86f8a042f; QN267=1529544503120_c28bec4238c0460d")
            .setCharset("utf8");
    String helperUrl = "http://xiazai.zol.com.cn/pupils_soft_index/pupils_page_2.html";
    String targeUrl = "";

    public void process(Page page) {
        //编写抓取逻辑
        //收集详情页url
        String html = page.getHtml().toString();
        System.out.println(html);
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        List strings = FileOperate.readStringFromFile("Text/", "去哪儿岳阳酒店url");
        Spider.create(new QNProcessor())
                .startUrls(strings)
                .setDownloader(new PhantomJSDownloader("C:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe"))
                .addPipeline(new ConsolePipeline())
                .thread(20)
                .start();
    }
}
