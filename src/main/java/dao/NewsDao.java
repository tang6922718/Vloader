package dao;

import bean.NewsBean;
import bean.ZBean;
import test.DbUtil;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/17-9:27
 * Modified By:
 */
public class NewsDao {
    public static void  insertNews(NewsBean newsBean){
        DbUtil.MysqlUpdate("insert into news_copy values (?,?,?,?) ",
              newsBean.getPageUrl(),newsBean.getContent(),newsBean.getTitle(),newsBean.getPublishDate());
    }


}
