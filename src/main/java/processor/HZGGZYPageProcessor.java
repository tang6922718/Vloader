package processor;

import bean.WinBidBean;
import dao.ZBDao;
import test.ReMatcher;
import test.SearchProjectNum;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HZGGZYPageProcessor implements PageProcessor{
    private Site site =Site.me()
            .setRetryTimes(3)
            .setDomain("http://www.hzzfcg.com")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setTimeOut( 10000 )
            .setCharset("utf-8");
    String helperUrl = "http://www.hzzfcg.com/page__gp_portal/list_article.aspx\\?id=083422fb-432a-426d-bd0d-58625a9ce035";
    String targetUrl = "/page__gp_portal/display_article.aspx.*?=cbHandle_Main_unitList_sinker_Refresh";
    DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH表示24小时制；
    String nowDate = dFormat.format(new Date());
    @Override
    public void process( Page page) {
        //编写抓取逻辑
        //收集详情页url
        if ( page.getUrl().regex( helperUrl ).match() ) {
            Request request = new Request( "http://www.hzzfcg.com/page__gp_portal/list_article.aspx?id=083422fb-432a-426d-bd0d-58625a9ce035");
            request.setMethod( HttpConstant.Method.POST );
//            request.addHeader( "Cookie","yunsuo_session_verify=48ee689246db300cf792af92a0f57b3a; ASP.NET_SessionId=sa5csboocxzuxz4hff4yi1yr");
            try {
               String currPage= page.getHtml().xpath("//input[@class=\"pager-content-selected\"]/@value").all().get(0);
                System.out.println(currPage);
                String code =page.getHtml().regex( "ChallengeScript.*?\\d+" ).regex("\\d+").toString();
                int newCode=0-Integer.parseInt( code )-1;
                Map map = new HashMap();
                map.put( "__EVENTTARGET","" );
                map.put( "__EVENTARGUMENT","" );
                map.put( "__LASTFOCUS","" );
                map.put( "__VIEWSTATE", page.getHtml().xpath( "//input[@id='__VIEWSTATE']/@value" ).toString());
                map.put( "ctl00$Main$noB$noB_NoBotExtender_ClientState",String.valueOf(newCode));
                map.put( "__VIEWSTATEENCRYPTED", "" );
                String name= "//input[@id='Main_unitList_unitPager_lstMain_btnPage_"+(Integer.parseInt(currPage))+"']/@name";
                String value= "//input[@id='Main_unitList_unitPager_lstMain_btnPage_"+(Integer.parseInt(currPage))+"']/@value";
                System.out.println("下一页请求name："+page.getHtml().xpath(name));
                System.out.println("下一页请求页码："+page.getHtml().xpath(value));
                map.put( page.getHtml().xpath(name).toString(),page.getHtml().xpath(value).toString());
                map.put( "__VIEWSTATEGENERATOR", "8CB3B3E0" );
                map.put( "ctl00$Main$unitList$ctl00$ddl_fField","fEntityName" );
                map.put( "ctl00$Main$unitList$ctl00$ddl_fType","_3_Equal" );
                map.put( "ctl00$Main$unitList$ctl00$tb_fLower","" );
//                request.addHeader("Cookie","yunsuo_session_verify=0b4d59ceb05a231bd95d4520a9b6d707; ASP.NET_SessionId=jv2c43zthbcr0ucwpyi1j0op");
                request.setRequestBody( HttpRequestBody.form( map, "utf-8" ) );
                page.addTargetRequest( request );


            } catch ( UnsupportedEncodingException e ) {
                e.printStackTrace();
            }
//            List< String > listUrl = page.getHtml().xpath( "//*[@id=\"Main_unitList_lstMain\"]/tbody" ).regex( targetUrl ).all();
//            page.addTargetRequests( listUrl );
        }
//         抽取数据
        if ( page.getUrl().regex( targetUrl ).match() ) {
            String title = page.getHtml().xpath( "//*[@id=\"Main_unitDisplay_metaField_lab_fCaption\"]/text()" ).toString();
            String content = page.getHtml().$( "#Main_unitDisplay_trContent > td", "allText" ).toString();
            String num = ReMatcher.reOne( content, "[A-Z0-9-\\[\\]]{4,}" ).toString();
            String url = page.getUrl().toString();
            Map map = null;
            if(num.length()>4){
                map= SearchProjectNum.search( num );
            }


            WinBidBean winBidBean = new WinBidBean();
            winBidBean.setArea( "" );
            winBidBean.setAddTime( nowDate );
            winBidBean.setUrl( url );
            winBidBean.setContent( content );
            winBidBean.setProjectCode( num );
            winBidBean.setPublishDate( page.getHtml().xpath( "//*[@id=\"Main_unitDisplay_docInfo_lab_fRemarkTime\"]/text()" ).regex( "\\d+-\\d+月\\d+日" ).toString() );
            winBidBean.setTitle( title );
            winBidBean.setUrlList( "" );
            winBidBean.setWebsiteCity( "贺州市" );
            winBidBean.setWebsiteSource( "11" );
            winBidBean.setValue2( "" );

            if ( map != null ) {
                winBidBean.setCcgpHtml( map.get( "ccgpHtml" ).toString() );
                winBidBean.setCcgpUrl( map.get( "ccgpUrl" ).toString() );
            } else {
                winBidBean.setCcgpHtml( "" );
                winBidBean.setCcgpUrl( "" );
            }
//            ZBDao.insertWinBidInfo( winBidBean );
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}
