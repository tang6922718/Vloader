package dao;

import test.DbUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/23-15:12
 * Modified By:
 */
public class ZCYDao {
    public static void  insertXieLou(Map<String,String> map){
        DbUtil.MysqlUpdate("insert into zcy values (?,?,?,?,?,?,?,?,?,?) "
                , map.get("url"),map.get("name"),map.get("pri"),map.get("guige"),map.get("time")
                ,map.get("shichang"),map.get("chandi"),map.get("zoushi"),map.get("yueduibi"),map.get("nianduibi"));
    }

}
