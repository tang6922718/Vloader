package dao;

import bean.CslouBean;
import bean.TravelBean;
import test.DbUtil;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/23-15:12
 * Modified By:
 */
public class CslouDao {
    public static void  insertXieLou(CslouBean cslouBean){
        DbUtil.MysqlUpdate("insert into lou values (?,?,?,?,?,?,?,?,?,?,?) ",
                cslouBean.getUrl(),cslouBean.getName(),cslouBean.getType(),cslouBean.getAddr(),cslouBean.getArea(),
                cslouBean.getPhone(), cslouBean.getFloor_num(),cslouBean.getOpening_time(), cslouBean.getDecorate_state(),cslouBean.getDevelopers(),
                cslouBean.getDetail_introduce());
    }

}
