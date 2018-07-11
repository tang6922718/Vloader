package util;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/4/8-10:51
 * Modified By:
 */
public class DownloadImage {

    public static void download(String urlString, String filename,String savePath) throws Exception {
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        con.setConnectTimeout(5*1000);
        InputStream is = con.getInputStream();
        byte[] bs = new byte[1024];
        int len;
        File sf=new File(savePath);
        if(!sf.exists()){
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename+".jpg");
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

    public static boolean createFile(String path,String fileName,String filecontent){
        Boolean bool = false;
        String filenameTemp = path+fileName+".txt";//文件路径+名称+文件类型
        File dirFile = new File(path);
        File file = new File(filenameTemp);
        //如果路径文件夹不存在，则创建新的文件
        if (!dirFile.exists()){
            dirFile.mkdirs();
        }
        try {
            //如果文件不存在，则创建新的文件
            if(!file.exists()){
                file.createNewFile();
                bool = true;
                System.out.println("success create file,the file is "+filenameTemp);
                //创建文件成功后，写入内容到文件里
                writeFileContent(filenameTemp, filecontent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bool;
    }

    public static boolean writeFileContent(String filepath,String content) throws IOException {
        Boolean bool = false;
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(content);
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }


    @Test
    public  void  start(){
            this.createFile("G:\\log\\","22333","吃饭了吗？");

    }
}