package dao;

import bean.Cd58Bean;
import test.DbUtil;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/23-15:12
 * Modified By:
 */
public class DZDPDao {
    public static void  insertXieLou(String url,String area,String type,String name,String star,String appraise,String addr,String phone){
        DbUtil.MysqlUpdate("insert into dzdp_entertainment values (?,?,?,?,?,?,?,?) "
                ,url,area,type,name,star,appraise,addr,phone);
    }

}
