package requestGenerator;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/31-11:06
 * Modified By:
 */
public class JsoupUtil {

    /**
     * 发送get请求获取返回body
     * @param url  请求地址
     * @param cookie  请求cookie
     * @return
     * @throws IOException
     */
    public static String httpGet(String url,String cookie) throws IOException{
        //获取请求连接
        Connection con = Jsoup.connect(url);
        //请求头设置，特别是cookie设置
        con.header("Accept", "text/html, application/xhtml+xml, */*");
        con.header("Content-Type", "application/x-www-form-urlencoded");
        con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))");
        con.header("Cookie", cookie);
        //解析请求结果
        Document doc=con.get();
        //获取标题
        //System.out.println(doc.title());
        return doc.toString();

    }

    /**
     * 发送get请求获取返回headers具体值
     * @param url  请求地址
     * @param cookie  请求cookie
     * @return
     * @throws IOException
     */
    public static String httpGetHeader(String url,String cookie,String header) throws IOException{
        //获取请求连接
        Connection con = Jsoup.connect(url);
        //请求头设置，特别是cookie设置
        con.header("Accept", "text/html, applica1tion/xhtml+xml, */*");
        con.header("Content-Type", "application/x-www-form-urlencoded");
        con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))");
        con.header("Cookie", cookie);
        //发送请求
        Connection.Response resp=con.method(Connection.Method.GET).execute();
        //获取cookie名称为__bsi的值
        String cookieValue = resp.cookie(header);
//		System.out.println("cookie  __bsi值：  "+cookieValue);
//		//获取返回cookie所值
//		Map<String,String> cookies = resp.cookies();
//		System.out.println("所有cookie值：  "+cookies);
//		//获取返回头文件值
//		String headerValue = resp.header(header);
//		System.out.println("头文件"+header+"的值："+headerValue);
//		//获取所有头文件值
//		Map<String,String> headersOne =resp.headers();
//		System.out.println("所有头文件值："+headersOne);
        return cookieValue;

    }

    /**
     * 发送post请求获取返回body
     * @param url  请求地址
     * @param map  请求参数
     * @param cookie  请求cookie
     * @return
     * @throws IOException
     */
    public static Document httpPost(String url,Map<String,String> map,String cookie){
        //获取请求连接
        Connection con = Jsoup.connect(url);
        //遍历生成参数
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                //添加参数
                con.data(entry.getKey(), entry.getValue());
            }
        }
        //插入cookie（头文件形式）
        con.header("Accept", "text/html, application/xhtml+xml, */*");
        con.header("Content-Type", "application/x-www-form-urlencoded");
        con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))");
        con.header("Cookie", cookie);
        Document doc = null;
        try {
            doc = con.post();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }



    /**
     * 发送post请求获取返回头文件获这cookie
     * @param url  请求地址
     * @param map  请求参数
     * @param cookie  请求cookie
     * @param header  返回获取cookie或者头文件
     * @return
     * @throws IOException
     */
    public static String httpPost(String url,Map<String,String> map,String cookie,String header) throws IOException{
        //获取请求连接
        Connection con = Jsoup.connect(url);
        //遍历生成参数
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                //添加参数
                con.data(entry.getKey(), entry.getValue());
            }
        }
        con.header("Accept", "text/html, application/xhtml+xml, */*");
        con.header("Content-Type", "application/x-www-form-urlencoded");
        con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))");
        //插入cookie（头文件形式）
        con.header("Cookie", cookie);
        //发送请求
        Connection.Response resp=con.method(Connection.Method.POST).execute();
        //获取cookie名称为__bsi的值
        String cookieValue = resp.cookie(header);
        System.out.println(cookieValue);
        return cookieValue;
    }

    /**
     * @param url  请求地址
     * @param params  请求参数
     * @param cookie  请求cookie
     * @return DocumentString
     */
    public static String getDocument(String url, Map params, String cookie) {
        Document document = JsoupUtil.httpPost(url, params, cookie);
        return document.toString();
    }

}
