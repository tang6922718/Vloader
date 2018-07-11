package test;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/4/16-11:40
 * Modified By:
 */
public class ReMatcher {

    public static String  reOne(String str, String regEx) {

        String result = "";
        if (str == null || str.equals("")) {
           return "无信息";
        }
        Matcher matcher = Pattern.compile(regEx).matcher(str);
        while (matcher.find()) {
            char first_str = matcher.group().charAt(0);
            if (first_str >= 'A' && first_str <= 'Z') {
                result = matcher.group().toString();
                break;
            }
        }
        return result;
    }



    @Test
   public void test(){
       System.out.println( reOne("  <h1>广西国力招标有限公司广西旅游在湖南、四川、重庆三省宣传项目（GXZC2018-G3-14858-GXGL）中标结果公告</h1> ","[A-Z0-9-\\[\\]]{4,}"));
   }
}
