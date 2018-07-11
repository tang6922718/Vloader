package requestGenerator;

import cn.wanghaomiao.xpath.exception.NoSuchAxisException;
import cn.wanghaomiao.xpath.exception.NoSuchFunctionException;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/31-11:36
 * Modified By:
 */
public class XpathExample {

        public XpathExample() {
        }

        public static void main(String[] args) throws IOException, NoSuchFunctionException, XpathSyntaxErrorException, NoSuchAxisException {
            String x1 = "//a/@href";
            String x2 = "//div[@id='paging_block']/div/a[text()*='Next']/@href";
            String x3 = "//h1/text()";
            String x4 = "//h1/allText()";
            String x5 = "//h1//text()";
            String x6 = "//div/a";
            String x7 = "//div[@id='post_list']/div[position()<3]/div/h3/allText()";
            String x8 = "//div[@id='post_list']/div[first()]/div/h3/allText()";
            String x9 = "//div[@id='post_list']/div[1]/div/h3/allText()";
            String x10 = "//div[@id='post_list']/div[./div/div/span[@class='article_view']/a/num()>1000]/div/h3/allText()";
            String x11 = "//div[@id='post_list']/div[self::div/div/div/span[@class='article_view']/a/num()>1000]/div/h3/allText()";
            String x12 = "//div[@id='post_list']/div[2]/div/p/preceding-sibling::h3/allText()";
            String x13 = "//div[@id='post_list']/div[2]/div/p/preceding-sibling::h3/allText()|//div[@id='post_list']/div[1]/div/h3/allText()";
            String x14 = "//html/body/div/div[4]/div[6]/div[19]/div[2]/h3/a";
            String x15 = "//html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/ul[1]/li/div[2]/h2/a";
            String x16 = "//html/body/div[3]/div/div/div/ul/li/div[2]/h2/a";
            Document doc = Jsoup.connect("http://book.douban.com/search/java").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
            JXDocument jxDocument = new JXDocument(doc);
            List<Object> rs = jxDocument.sel(x16);
            Object o;
            for(Iterator i$ = rs.iterator(); i$.hasNext(); System.out.println(o.toString())) {
                o = i$.next();
                if (o instanceof Element) {
                    int index = ((Element)o).siblingIndex();
                    System.out.println(index);
                }
            }

        }

}
