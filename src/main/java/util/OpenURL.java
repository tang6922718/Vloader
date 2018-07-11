package util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URLConnection;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/24-11:29
 * Modified By:
 */
public class OpenURL {

    public static String getOfUrl(String url) {

        Document doc=null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc.body().html();
    }
    public static String postOfUrl(String url,String params) {

        Document doc=null;
        try {
            doc = Jsoup.connect(url).data("data", params)
                    .userAgent("Mozilla")
                    .timeout(3000)
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc.body().html();
    }

    @Test
    public  void test(){
        System.out.println(getOfUrl("http://dujia.lvmama.com/vst_front/comment/newPaginationOfComments?"+"type:'ref',currentPage:3,totalCount:'40',placeId:'',productId:'',placeIdType:'',isPicture:'',isBest:'',isPOI:'N',isELong:'N',productRef:'264568',vstType:'null',isRef:'2'".replaceAll(":","=").replaceAll(",","&").replaceAll("\'"," ")));
    }

}
