import processor.TravelProcessor;
import processor.news.News3Processor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import util.RedisOperate;

import java.util.ArrayList;
import java.util.List;


public class Main2 {


    public static void main(String[] args) {

       List list = new ArrayList();
        for (int i=1;i<=100;i++){
            list.add("http://search.cctv.com/search.php?qtext=%E6%9E%97%E9%83%91%E6%9C%88%E5%A8%A5&sort=relevance&type=web&vtime=&datepid=1&channel=&page="+i);
        }
        Spider spider = Spider.create(new News3Processor()).addPipeline(new ConsolePipeline());
        spider.startUrls(list);
        spider.thread(50).start();
    }


}
