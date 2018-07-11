package dao;

import test.DbUtil;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/23-15:12
 * Modified By:
 */
public class QBRDao {
    public static void  insertQNR(String str1 ,String str2,String str3){
        DbUtil.MysqlUpdate("insert into qnr_comment values (?,?,?) ",str1,str2,str3
               );
    }

}
