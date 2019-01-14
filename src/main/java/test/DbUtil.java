package test;

import bean.ZBean;
import com.alibaba.druid.pool.DruidPooledConnection;
import db.ServerDBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/3/14-10:27
 * Modified By:
 */
public class DbUtil {


    public static int MysqlUpdate(String sql, String... param) {
        try {
            try (DruidPooledConnection connection = ServerDBConnection.INSTANCE.getConnection();
                 PreparedStatement pst = connection.prepareStatement(sql)) {

                for (int i = 1; i <= param.length; i++) {
                    pst.setString(i, param[i - 1]);
                }
                return pst.executeUpdate();

            }
        } catch (SQLException e) {
            System.out.println("数据操作异常--》" + e.getMessage());
        }
        return 0;
    }

    public static boolean MysqlExceute(String sql, String... param) {
        try {
            try (DruidPooledConnection connection = ServerDBConnection.INSTANCE.getConnection();
                 PreparedStatement pst = connection.prepareStatement(sql)) {
                for (int i = 1; i <= param.length; i++) {
                    pst.setString(i, param[i - 1]);
                }
                return pst.execute();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean MysqlExceute1(String sql) {
        try {
            try (DruidPooledConnection connection = ServerDBConnection.INSTANCE.getConnection();
                 PreparedStatement pst = connection.prepareStatement(sql)) {
                return pst.execute();

            }
        } catch (SQLException e) {
            System.out.println("数据操作异常--》" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    //合并表
    public int MysqlHB(String selectSql, String insertSql) {
        try {
            try (DruidPooledConnection connection = ServerDBConnection.INSTANCE.getConnection();
                 PreparedStatement pst = connection.prepareStatement(selectSql);
                 PreparedStatement pst1 = connection.prepareStatement(insertSql)
            ) {
                ResultSet resultSet = pst.executeQuery();
                while (resultSet.next()) {
                    String content = resultSet.getString("gxzfcg_content");
                    String num = ReMatcher.reOne(content, "[^\\u4e00-\\u9fa5\\,\\：]+(?![^\\)])");
                    pst1.setString(1, content);
                    pst1.setString(2, num);
                    pst1.execute();
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}


