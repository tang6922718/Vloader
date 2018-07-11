package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * 重写文件输出
 */
public class CustomPipeline  extends FilePersistentBase implements Pipeline{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public CustomPipeline() {
            this.setPath("D:\\magic");
        }

    public CustomPipeline(String path) {
            this.setPath(path);
        }

    public void process(ResultItems resultItems, Task task) {

        StringBuffer temp = new StringBuffer();
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter(this.getFile(path + "333") + ".txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }

        temp.append("name:" + ((List) resultItems.get("name")) + "\t");
        temp.append("detail:" + ((List) resultItems.get("detail")) + "\t");


        printWriter.write(temp.toString());
    }







       /* String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;



        StringBuffer  temp= new StringBuffer();

        try {
            //path + DigestUtils.md5Hex(resultItems.getRequest().getUrl())
            PrintWriter printWriter = new PrintWriter(new FileWriter(this.getFile(path+"222") + ".txt",true));
            for (int i = 0; i <resultItems.getAll().size() ; i++) {

                temp.append("title:"+((List)resultItems.get("title")).get(i)+"\t");
                temp.append("guige:"+((List)resultItems.get("guige")).get(i)+"\t");
                temp.append("c_addr:"+((List)resultItems.get("c_addr")).get(i)+"\t");
                temp.append("store:"+((List)resultItems.get("store")).get(i)+"\t");
                temp.append(((List)resultItems.get("sale_num")).get(i)+"\t");
                temp.append("price:"+((List)resultItems.get("price")).get(i)+"\r\n");

            }
            printWriter.write(temp.toString());
           *//* printWriter.write(JSON.toJSONString(resultItems.getAll()));*//*
            printWriter.close();
        } catch (IOException var5) {
            this.logger.warn("write file error", var5);
        }*/




}
