package dao;

import bean.ZBean;
import test.DbUtil;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/4/16-15:18
 * Modified By:
 */
public class ZBDao {
    public static void  insertID(ZBean zBean){
        DbUtil.MysqlUpdate("insert into admin_gx_zhongbiao_0416 values (?,?,?,?,?,?,?,?,?,?) ",zBean.getArea(),
                zBean.getUrl(),zBean.getUrlList(),zBean.getTitle(),zBean.getContent(),zBean.getPublishDate(),zBean.getProjectCode()
                ,zBean.getCcgpUrl(),zBean.getCcgpHtml(),zBean.getAddTime());
    }
    public static void  insertZB(ZBean zBean){
        DbUtil.MysqlUpdate("insert into admin_gxbh_zhongbiao_0418 values (?,?,?,?,?,?,?) ",
                zBean.getUrl(),zBean.getTitle(),zBean.getContent(),zBean.getProjectCode(),zBean.getCcgpUrl(),zBean.getCcgpHtml(),zBean.getAddTime());
    }

}
