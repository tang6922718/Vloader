package dao;

import bean.WinBidBean;
import bean.ZBean;
import test.DbUtil;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/4/16-15:18
 * Modified By:
 */
public class ZBDao2 {
    public static void  insertID(ZBean zBean){
        DbUtil.MysqlUpdate("insert into admin_gx_zhongbiao_0416 values (?,?,?,?,?,?,?,?,?,?,?,?) ",zBean.getArea(),
                zBean.getUrl(),zBean.getUrlList(),zBean.getTitle(),zBean.getContent(),zBean.getPublishDate(),zBean.getProjectCode()
                ,zBean.getCcgpUrl(),zBean.getCcgpHtml(),zBean.getAddTime());
    }

    public static void insertWinBidInfo( WinBidBean winBidBean){
        DbUtil.MysqlUpdate( "insert into admin_gx_win_bid_info values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",winBidBean.getArea(),
                winBidBean.getUrl(),winBidBean.getUrlList(),winBidBean.getTitle(),winBidBean.getContent(),winBidBean.getPublishDate(),
                winBidBean.getProjectCode(),winBidBean.getCcgpUrl(),winBidBean.getCcgpHtml(),winBidBean.getWebsiteSource(),winBidBean.getWebsiteCity(),
                winBidBean.getAddTime(),winBidBean.getRead_label(),winBidBean.getValue2());
    }

}
