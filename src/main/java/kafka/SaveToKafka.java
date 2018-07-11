package kafka;

import com.alibaba.druid.pool.DruidPooledConnection;
import db.ServerDBConnection;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.consumer.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/28-14:20
 * Modified By:
 */
public class SaveToKafka {

    private static Producer<String, String> producer;
    private  static String sql ="";
    private  static String topic ="";
    static {
        Properties props = new Properties();
        Properties props1 = new Properties();
        try {
            props.load( Files.newInputStream( Paths.get( "./kafka.properties" )));
            props1.put("bootstrap.servers", props.getProperty("bootstrap.servers"));//服务器ip:端口号，集群用逗号分隔
            props1.put("acks", "all");
            props1.put("retries", 0);
            props1.put("batch.size", 16384);
            props1.put("linger.ms", 200);
            props1.put("buffer.memory", 33554432);
            props1.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props1.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            sql=props.getProperty("sql");
            topic=props.getProperty("topic");
            producer = new KafkaProducer<>(props1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void mysqlToKafka() {
        try {
            try (DruidPooledConnection connection = ServerDBConnection.INSTANCE.getConnection();
                 PreparedStatement pst = connection.prepareStatement(sql)) {
                ResultSet resultSet = pst.executeQuery();
                /*resultSet.last();
                System.out.println(resultSet.getRow());*/
                while (resultSet.next()) {
                    String url = resultSet.getString("url");
                    String title = resultSet.getString("title");
                    String date = resultSet.getString("time").substring(0, 10);
                    String comment = resultSet.getString("comment");
                    String temp = "4|15|24|25|26|27|29|";
                    String data = temp + url + "|" + "" + "|" + comment + "|" + title + "|" + "" + "|" + date +
                            "|" + "" + "|" + "" + "|"
                            + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "" + "|" + "";
                    sendMsgToKafka(topic, data);
                    Thread.sleep(300);
                    System.out.println("正在kafka发送数据:" + data);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void sendMsgToKafka(String topic,String msg) {
        producer.send(new ProducerRecord<String, String>(topic, String.valueOf(new Date().getTime()),
                msg));
    }

    public static void closeKafkaProducer() {
        producer.close();
    }


    public static void main(String[] args) {
        SaveToKafka.mysqlToKafka();
        closeKafkaProducer();
    }
}
