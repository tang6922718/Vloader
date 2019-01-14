package dao;

import test.DbUtil;

import java.util.Map;

/**
 * @Author:Tang
 * @Description:插入航班信息
 * @Date:Created in 2018/5/23-15:12
 * Modified By:
 */
public class PlaneDao {
    public static void  insertXieLou(Map<String,String> map){
        DbUtil.MysqlUpdate( "insert into airplane (airplane_number,airplane_company,plan_fly_time,plan_fall_time,start_airport "+
                ",end_airport,flight_status,total_distance,plane_type,plane_age,start_weather,end_weather) values (?,?,?,?,?,?,?,?,?,?,?,?)"
                , map.get("airplaneNumber"),map.get("airplane_company"),map.get("plan_fly_time"),map.get("plan_fall_time"),map.get("start_airport")
                ,map.get("end_airport"),map.get("flight_status"),map.get("total_distance"),map.get("plane_type"),map.get("plane_age")
                ,map.get( "start_weather" ),map.get( "end_weather" )  );
    }

}
