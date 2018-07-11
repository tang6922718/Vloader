package test;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/3/28-15:46
 * Modified By:
 */
public class TestUtil {


    //合并表操作
    @Test
    public void test() throws UnknownHostException {
//        String selectSql = "SELECT  gxzfcg_content  FROM admin_gx_win_bid_info LIMIT  2000";
//        String insertSql="insert into sub_num values (?,?)  ";
//        DbUtil dbUtil = new DbUtil();
//        //第一层循环查询数据，第二层循环插入数据
//        dbUtil.MysqlHB(selectSql,insertSql);

//        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        InetAddress addr = InetAddress.getLocalHost();
        String ip=addr.getHostAddress().toString(); //获取本机ip
        System.out.println(ip);
    }

    public void cmd(String cmd){
        String s;
        try {
            Process ps= Runtime.getRuntime().exec(cmd);
         /*   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ps.getInputStream(),"gbk"));
            while((s=bufferedReader.readLine()) != null)
                  System.out.println(s);*/
         Thread.sleep(2000);
            ps.waitFor();
        } catch (Exception e) {
        }
    }
}
