package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/6/20-11:32
 * Modified By:
 */
public class FileOperate {

    private static Logger logger = LoggerFactory.getLogger(FileOperate.class);
    /**
     * 把一个字符串写到指定文件中
     * @param str
     * @param path
     * @param fileName
     */
    public static void writeStringToFile(String str,String path,String fileName){
        try {
            File fileDir = new File(path);
            if(!fileDir.exists()){
                fileDir.mkdirs();
            }
            File file = new File(path+fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file,true);
            fw.write(str);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            logger.error("load in file error");
        }
    }

    public static List readStringFromFile(String path, String fileName){
        List<String> list =new ArrayList();
        File fileDir = new File(path);
        if(!fileDir.exists()){
            return null;
        }
        File file = new File(path+fileName);
        if(!file.exists()){
            return null;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str = "";
            while((str = br.readLine()) != null){
                list.add(str);
            }
        } catch (Exception e) {
            logger.error("read file error");
            return null;
        }
        return list;
    }
    /**
     * 把含html标签的富文本字符串转化成纯文本
     *
     * @param inputString
     *            待转换的字符串
     * @return
     */
    public static String htmlToTxt(String inputString) {

        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        textStr = textStr.replaceAll("&", "&");
        textStr = textStr.replaceAll("<", "<");
        textStr = textStr.replaceAll(">", ">");
        textStr = textStr.replaceAll("\"", "\"");

        return textStr;// 返回文本字符串
    }



}
