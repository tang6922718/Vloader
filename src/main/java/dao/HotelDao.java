package dao;

import bean.HotelBean;
import test.DbUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class HotelDao {

    public static void insertData( HotelBean bean){
        DbUtil.MysqlExceute("insert into   hotel_info  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",bean.getUrl(),bean.getName(),bean.getCity(),
                bean.getLocation(), bean.getAddress(),bean.getRoadcross(),bean.getTel(),bean.getOpentime(),bean.getDecorate(),bean.getTotalroom(),
                bean.getRate(),bean.getC_date(),bean.getGrade(), bean.getHotel_content(),bean.getGroup(),bean.getId(),bean.getC_from());
    }

    public static void insertData1( String str1 ,String str2,String str3 ,String str4 ,String str5 ,String str6 ,String str7 ,String str8 ,String str9,String str10 ,String str11){
        DbUtil.MysqlExceute("insert into   hotel_info_chenzhou  VALUES (?,?,?,?,?,?,?,?,?,?,?)",str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11);
    }

}
