package dao;

import test.DbUtil;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/4/3-15:56
 * Modified By:
 */
public class SjDao {
     public static void  insertID(String id){
            DbUtil.MysqlUpdate("insert into sjjy_id values (?) ",id);
     }
}
