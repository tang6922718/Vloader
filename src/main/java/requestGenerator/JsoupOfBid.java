package requestGenerator;

import cn.wanghaomiao.xpath.model.JXDocument;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/31-11:15
 * Modified By:
 */
public class JsoupOfBid {

    public static void main(String[] args) {

        String cookies = "Hm_lvt_ef2114bed21175425a21bb8a1e40ebdf=1527574020,1527735252; Hm_lpvt_ef2114bed21175425a21bb8a1e40ebdf=1527735406; JSESSIONID=73104A288F82534536F78ED264D92D70; BIGipServerpool_zz_jtym_172.28.22.56=68557996.35874.0000; cmsurl=/../../index.shtml; Hm_lvt_9540c575727577f3c6819ea101f3790d=1527573995,1527652112,1527734921,1527735229; Hm_lpvt_9540c575727577f3c6819ea101f3790d=1527735407";
        String url = "http://www.cebpubservice.com/ctpsp_iiss/searchbusinesstypebeforedooraction/getSearch.do";
        Map<String,String> map = new HashMap<String,String>();

        String doc = JsoupUtil.getDocument(url,map,cookies);
        JXDocument jxDocument = new JXDocument(doc);


    }


}
