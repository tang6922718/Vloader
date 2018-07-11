package test;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/3/28-17:28
 * Modified By:
 */
public class StringUtil {

    @Test
    public  void  runWith() {
        urlRange();
    }



    public  void  urlRange() {
        // 5932
        //http://www.bhsggzy.cn
        regeOperation2("/gx.*&CategoryNum=\\d+",new  StringBuffer("")
          );


    }

    public  String  regeOperation2(String rege,StringBuffer content) {
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile(rege);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()){
           sb.append("http://www.bhsggzy.cn"+matcher.group()+",");
        }
        return  sb.toString();
    }
    //招标网判断项目编号。大写字母打头，其余【字母数字-】
    public  void  regeOperation(String rege,String content) {

        Pattern pattern = Pattern.compile(rege);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()){
            char first_str = matcher.group().charAt(0);
            if (first_str>='A' && first_str<='Z'){
            System.out.println(matcher.group());
            break;
            }
        }
    }

}
