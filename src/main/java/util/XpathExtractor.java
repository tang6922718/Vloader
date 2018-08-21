package util;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import us.codecraft.webmagic.selector.Selectable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;

/**
 * created by 李啸峰 on 2018/4/12.
 * 2018 @ market_crawler
 */
public class XpathExtractor {
    private static XPath xPath;
    private static DomSerializer domSerializer;
    private static HtmlCleaner hc;
    private static DocumentBuilderFactory domFactory;

    static {
        xPath = XPathFactory.newInstance().newXPath();
        domFactory = DocumentBuilderFactory.newInstance( );
        domSerializer = new DomSerializer(new CleanerProperties());
        hc = new HtmlCleaner();
    }

    public static Document getDocument(String html) throws ParserConfigurationException {
        return domSerializer.createDOM(hc.clean(html));
    }

    public static String extract(Selectable document, String xpath) throws XPathExpressionException {
        String result = (String) xPath.evaluate(xpath, document, XPathConstants.STRING);
        return result.trim();
    }

    public static String extract(String document, String xpath) throws ParserConfigurationException, XPathExpressionException, IOException, SAXException {
        Document doc= string2Doc(document);
        XPathExpression expr= xPath.compile(xpath);
        String result =expr.evaluate(doc);
//        String result = (String) xPath.evaluate(xpath,getDocument(document), XPathConstants.STRING);
        return result.trim();
    }

    public static Document string2Doc(String xml) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        InputSource source;
        StringReader reader = null;
        try {
            builder = factory.newDocumentBuilder();
            reader = new StringReader(xml);
            source = new InputSource(reader);//使用字符流创建新的输入源
            doc = builder.parse(source);
            return doc;
        } catch (Exception e) {
            return null;
        } finally {
            if(reader != null){
                reader.close();
            }
        }
    }

}
