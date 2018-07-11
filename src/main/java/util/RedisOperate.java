package util;

import us.codecraft.webmagic.Spider;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/23-17:25
 * Modified By:
 */
public class RedisOperate extends CMP {

    Spider spider;

    public RedisOperate(String[] params, Spider spider) {
        super(params);
        this.spider = spider;
    }

    @Override
    public void onUrlReceive(String url) {
        spider.addUrl(url);
    }

    @Override
    public void afterStop() {
        spider.stop();
    }
}
