package dao;

import bean.TravelBean;
import test.DbUtil;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/23-15:12
 * Modified By:
 */
public class TravelDao {
    public static void  insertNews(TravelBean travelBean){
        DbUtil.MysqlUpdate("insert into travel values (?,?,?,?,?) ",travelBean.getUrl(),
                travelBean.getTitle(),travelBean.getPrice(),travelBean.getComment(),travelBean.getTime()
            );
    }

}
