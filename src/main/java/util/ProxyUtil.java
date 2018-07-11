package util;

import org.junit.jupiter.api.Test;
import sun.net.www.content.text.PlainTextInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/6/27-16:34
 * Modified By:
 */
public class ProxyUtil {




    public  static List  getProxyIp(String proxyUrl) throws IOException {

        List<String> list = new ArrayList<>();
        URL url = new URL(proxyUrl);
        URLConnection con = url.openConnection();
        con.setConnectTimeout(5*1000);
        InputStream is = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String str =null;
        while ((str=br.readLine())!=null){
           list.add(str);
        }

        return list;

    }
/*

    public static void main(String[] args) throws IOException {
        getProxyIp("http://api.xdaili.cn/xdaili-api//privateProxy/applyStaticProxy?spiderId=2070402026a84a5fae0a81fb5cb4e580&returnType=1&count=1");
    }
*/

}
