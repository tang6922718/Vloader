package util;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.w3c.dom.Document;
import us.codecraft.webmagic.selector.Selectable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * created by 李啸峰 on 2018/4/12.
 * 2018 @ market_crawler
 */
public class XpathExtractor {
    private static XPath xPath;
    private static DomSerializer domSerializer;
    private static HtmlCleaner hc;

    static {
        xPath = XPathFactory.newInstance().newXPath();
        domSerializer = new DomSerializer(new CleanerProperties());
        hc = new HtmlCleaner();
    }
    public static Document getDocument( String html) throws ParserConfigurationException {
        return domSerializer.createDOM( hc.clean(html) );
    }
    public static String extract(Selectable document, String xpath) throws XPathExpressionException {
            String result;
            result =  ( String ) xPath.evaluate( xpath,document.toString(), XPathConstants.STRING);
        return result.trim();
    }
    public static String extract( String document, String xpath) throws ParserConfigurationException, XPathExpressionException {
        String result;
        result =  ( String ) xPath.evaluate( xpath,getDocument( document ), XPathConstants.STRING);
        return result.trim();
    }

}
