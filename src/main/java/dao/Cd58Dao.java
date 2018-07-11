package dao;

import bean.Cd58Bean;
import bean.CslouBean;
import test.DbUtil;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/23-15:12
 * Modified By:
 */
public class Cd58Dao {
    public static void  insertXieLou(Cd58Bean cd58Bean){
        DbUtil.MysqlUpdate("insert into cd58 values (?,?,?,?,?,?,?,?) ",cd58Bean.getUrl(),cd58Bean.getName(),
                cd58Bean.getAddr(),cd58Bean.getArea(),cd58Bean.getType(),cd58Bean.getPhone(),
                cd58Bean.getPrice(),cd58Bean.getHouses()
               );
    }

}
