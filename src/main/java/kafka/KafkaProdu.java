package kafka;




import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;

import java.util.Properties;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/28-11:01
 * Modified By:
 */
public class KafkaProdu {


  public void send(String topic, String key, String data) throws IOException {
      Properties props = new Properties();
      props.put("bootstrap.servers", "192.168.1.21:9092");
      props.put("acks", "all");
      props.put("retries", 0);
      props.put("batch.size", 16384);
      props.put("key.serializer", StringSerializer.class.getName());
      props.put("value.serializer", StringSerializer.class.getName());
      KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
      producer.send(new ProducerRecord<String, String>(topic,key,data));
  }
    public static void main(String[] args) {
        KafkaProdu s = new KafkaProdu();
        try {
            s.send("test","test1","rose");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
