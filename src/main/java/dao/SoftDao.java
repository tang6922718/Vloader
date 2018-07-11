package dao;

import bean.SoftBean;
import bean.WinBidBean;
import bean.ZBean;
import test.DbUtil;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/4/16-15:18
 * Modified By:
 */
public class SoftDao {
    public static void  insert(SoftBean softBean){
        DbUtil.MysqlUpdate("insert into software(name,iconUrl,detail,downloadUrl,type) values (?,?,?,?,?) ",softBean.getName(),softBean.getIcon()
                ,softBean.getDetail(),softBean.getDownloadUrl(),"app");
    }



}
