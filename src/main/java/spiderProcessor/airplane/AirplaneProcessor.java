package spiderProcessor.airplane;

import dao.PlaneDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 爬取飞常准航班信息
 *
 * @author 管理员
 */
public class AirplaneProcessor implements PageProcessor {

    private Site site = Site.me()
            .setRetryTimes( 3 )
            .setUserAgent( "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36" )
            .setSleepTime( 5000 )
            .addHeader( "Cookie", "PHPSESSID=44sqjh8905miva7bkf0mc68t82; Hm_lvt_d1f759cd744b691c20c25f874cadc061=1546845202,1546845421; orderRole=1; _ga=GA1.2.948178438.1546846386; _gid=GA1.2.2037778601.1546846386; fnumHistory=%5B%7B%22fnum%22%3A%223U5020%22%7D%2C%7B%22fnum%22%3A%223U5110%22%7D%2C%7B%22fnum%22%3A%223U5084%22%7D%2C%7B%22fnum%22%3A%223U5015%22%7D%2C%7B%22fnum%22%3A%223U5046%22%7D%2C%7B%22fnum%22%3A%22CA111%22%7D%5D; midsalt=5c3316737877f; citiesHistory=%5B%7B%22depCode%22%3A%22PEK%22%2C%22arrCode%22%3A%22CGQ%22%2C%22depCity%22%3A%22%5Cu5317%5Cu4eac%5Cu9996%5Cu90fd%22%2C%22arrCity%22%3A%22%5Cu957f%5Cu6625%5Cu9f99%5Cu5609%22%7D%2C%7B%22depCode%22%3A%22PEK%22%2C%22arrCode%22%3A%22WUH%22%2C%22depCity%22%3A%22%5Cu5317%5Cu4eac%5Cu9996%5Cu90fd%22%2C%22arrCity%22%3A%22%5Cu6b66%5Cu6c49%5Cu5929%5Cu6cb3%22%7D%2C%7B%22depCode%22%3A%22PEK%22%2C%22arrCode%22%3A%22SHA%22%2C%22depCity%22%3A%22%5Cu5317%5Cu4eac%5Cu9996%5Cu90fd%22%2C%22arrCity%22%3A%22%5Cu4e0a%5Cu6d77%5Cu8679%5Cu6865%22%7D%2C%7B%22depCode%22%3A%22CSX%22%2C%22arrCode%22%3A%22TSN%22%2C%22depCity%22%3A%22%5Cu957f%5Cu6c99%5Cu9ec4%5Cu82b1%22%2C%22arrCity%22%3A%22%5Cu5929%5Cu6d25%5Cu6ee8%5Cu6d77%22%7D%2C%7B%22depCode%22%3A%22CGQ%22%2C%22arrCode%22%3A%22XMN%22%2C%22depCity%22%3A%22%5Cu957f%5Cu6625%5Cu9f99%5Cu5609%22%2C%22arrCity%22%3A%22%5Cu53a6%5Cu95e8%5Cu9ad8%5Cu5d0e%22%7D%2C%7B%22depCode%22%3A%22PEK%22%2C%22arrCode%22%3A%22NGB%22%2C%22depCity%22%3A%22%5Cu5317%5Cu4eac%5Cu9996%5Cu90fd%22%2C%22arrCity%22%3A%22%5Cu5b81%5Cu6ce2%5Cu680e%5Cu793e%22%7D%5D; salt=5c331e4a87537; Hm_lpvt_d1f759cd744b691c20c25f874cadc061=1546853999" )
            .setCharset( "utf8" );
    String helperUrl = "http://www.variflight.com/sitemap.*?";
    String helperUrl1 = "http://www.variflight.com/flight/.*?";

    String targeUrl = "http://www.variflight.com/schedule/.*?";


    private final ReentrantLock lock = new ReentrantLock( true );

    @Override
    public void process( Page page ) {
        try {
            lock.lock();
            //获取出发-到达地址
            if ( page.getUrl().regex( helperUrl ).match() ) {
                List< String > urls = page.getHtml().xpath( "//div[@id='wrap']/div/a/@href" ).all();
                for ( int i = 1; i < 10 ; i++ ) {
                    page.addTargetRequest( urls.get( i ));
                }


            } else if ( page.getUrl().regex( helperUrl1 ).match() ) {
                //获取所有航班
                if ( page.getHtml().regex( "验证码" ).match() ) {
                    System.out.println( "出现验证码了，赶快消灭它---》》" + page.getUrl() );
                    page.addTargetRequest( page.getUrl().toString() );
                    try {
                        TimeUnit.SECONDS.sleep( 10 );
                    } catch ( InterruptedException e ) {
                        e.printStackTrace();
                    }
                }
                List< String > urls = page.getHtml().xpath( "//ul[@id='list']/li/a/@href" ).all();
                page.addTargetRequests( urls );
            } else if ( page.getUrl().regex( targeUrl ).match() ) {
                //解析目标网页，详情信息。注意处理验证码
                //航班号
                String airplaneNumber = page.getHtml().xpath( "//div[@class=\"tit\"]/span/b/text()" ).toString();
                System.out.println( "航班号：" + airplaneNumber );
                //航空公司
                String airplane_company = page.getHtml().xpath( "//div[@class=\"tit\"]/span/b/text()" ).toString();
                System.out.println( "航空公司：" + airplane_company );

                //计划起飞时间
                String plan_fly_time = page.getHtml().xpath( "//span[@class=\"date\"]//text()" ).all().get( 0 );
                System.out.println( "计划起飞时间：" + plan_fly_time );


                //计划降落时间
                String plan_fall_time = page.getHtml().xpath( "//span[@class=\"date\"]//text()" ).all().get( 1 );
                System.out.println( "计划降落时间：" + plan_fall_time );
                //实际起飞时间

                // String real_fly_time =page.getHtml().xpath( "//div[@class=\"p_ti\"]//text()" ).all().toString();

                //实际降落时间

                //String real_fall_time = page.getHtml().xpath( "//div[@class=\"p_ti\"]//text()" ).all().toString();

                //起飞机场
                String start_airport = page.getHtml().xpath( "//div[@class=\"fly_mian\"]//h2/@title" ).all().get( 0 );
                System.out.println( "起飞机场：" + start_airport );
                //降落机场
                String end_airport = page.getHtml().xpath( "//div[@class=\"fly_mian\"]//h2/@title" ).all().get( 1 );
                System.out.println( "降落机场：" + end_airport );
                //航班状态
                String flight_status = page.getHtml().xpath( "//div[@class=\"state\"]/div/text()" ).all().toString();
                System.out.println( "航班状态：" + flight_status );

                //总里程时长
                String total_distance = page.getHtml().xpath( "//div[@class=\"p_ti\"]/span//text()" ).all().toString();
                System.out.println( "总里程时长：" + total_distance );
                //机型
                String plane_type = page.getHtml().xpath( "//li[@class=\"mileage\"]/span/text()" ).toString();
                System.out.println( "机型：" + plane_type );
                //机龄
                String plane_age = page.getHtml().xpath( "//li[@class=\"time\"]/span/text()" ).toString();
                System.out.println( "机龄：" + plane_age );
                //起飞地天气
                String start_weather = page.getHtml().xpath( "//ul[@class='f_common rand_ul_dep']/li[@class=\"weather\"]/p//text()" ).toString();
                System.out.println( "起飞地天气：" + start_weather );
                //到达地天气
                String end_weather = page.getHtml().xpath( "//ul[@class='f_common rand_ul_arr']/li[@class=\"weather\"]/p//text()" ).toString();
                System.out.println( "到达地天气：" + end_weather );

                Map map = new HashMap();
                map.put( "airplaneNumber",airplaneNumber );
                map.put( "airplane_company",airplane_company );
                map.put( "plan_fly_time",plan_fly_time );
                map.put( "plan_fall_time",plan_fall_time );
                map.put( "start_airport",start_airport );

                map.put( "end_airport",end_airport );
                map.put( "flight_status",flight_status );
                map.put( "total_distance",total_distance );
                map.put( "plane_type",plane_type );
                map.put( "plane_age",plane_age );

                map.put( "start_weather",start_weather );
                map.put( "end_weather",end_weather );
                PlaneDao.insertXieLou( map );


            }
        } finally {
            lock.unlock();
        }


    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main( String[] args ) {

        String urls = "http://www.variflight.com/sitemap/flight?AE71649A58c77=";
        Spider spider = Spider.create( new AirplaneProcessor() ).addPipeline( new ConsolePipeline() );
        spider.addUrl( urls );
        spider.thread( 10 ).start();
    }

}
