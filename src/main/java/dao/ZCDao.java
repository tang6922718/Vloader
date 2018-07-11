package dao;

import test.DbUtil;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/23-15:12
 * Modified By:
 */
public class ZCDao {
    public static void  insertXieLou(String url,String type,String name,String star,String addr,String phone, String appraise,String comment){
        DbUtil.MysqlUpdate("insert into zhucheng_entertainment values (?,?,?,?,?,?,?,?) "
                ,url,type,name,star,addr,phone,appraise,comment);
    }

}
