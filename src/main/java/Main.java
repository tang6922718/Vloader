
import processor.Travel1Processor;
import processor.TravelProcessor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import util.RedisOperate;


import java.util.ArrayList;
import java.util.List;


public class Main {

/*

    public static void main(String[] args) {
*/
/*      驴妈妈网页评论
        String[] area = {"成都", "上海", "上海", "苏州", "杭州", "北京"
                , "太原", "广州", "重庆", "三亚", "常州", "湖州", "青岛", "天津", "深圳"
                , "昆明", "海口", "南京", "嘉兴", "南通", "无锡", "西安"};

        List<String> list = new ArrayList<>();
        for (int i = 0; i < area.length; i++) {
            list.add("http://s.lvmama.com/route/H258K510100?keyword=" + area[i] + "&k=0#list");
        }*//*

       //携程爬取
        String[] area = {"成都", "上海", "上海", "苏州", "杭州", "北京"
                , "太原", "广州", "重庆", "三亚", "常州", "湖州", "青岛", "天津", "深圳"
                , "昆明", "海口", "南京", "嘉兴", "南通", "无锡", "西安"};

        List<String> list = new ArrayList<>();
        for (int i = 0; i < area.length; i++) {
            list.add(area[i]);
        }
        Travel1Processor travel1Processor = new Travel1Processor();
        Spider spider = Spider.create(travel1Processor).addPipeline(new ConsolePipeline());
        RedisOperate redisOperate = new RedisOperate(args,spider);
        travel1Processor.setCmp(redisOperate);
        spider.addUrl("http://vacations.ctrip.com/");
        spider.thread(20).start();
    }
*/


}
