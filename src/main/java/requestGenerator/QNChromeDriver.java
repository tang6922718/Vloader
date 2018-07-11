package requestGenerator;

import dao.QBRDao;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import test.FantomJs;
import test.WebDriver;
import util.FileOperate;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/31-15:37
 * Modified By:
 */
public class QNChromeDriver {

    static String domain = "http://hotel.qunar.com";

    //长沙、株洲、湘潭、衡阳、邵阳、岳阳、常德、张家界、益阳、郴州、永州、怀化、娄底
    public static void main(String[] args) throws InterruptedException {
       /* List<String> strings = FileOperate.readStringFromFile("Text/", "去哪儿岳阳酒店url");
        for (String string : strings) {
            crawlerData1(string);
        }*/
        Executor executor = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            if(i==0) {
                executor.execute(() -> {
                    try {
                        crawlerData("http://hotel.qunar.com/city/changsha/","长沙酒店url");
                    } catch (Exception e) {
                        System.out.println("线程执行异常！！");
                    }
                });
            }else if (i==1){
                executor.execute(() -> {
                    try {
                        crawlerData("http://hotel.qunar.com/city/loudi/","娄底酒店url");
                    } catch (Exception e) {
                        System.out.println("线程执行异常！！");
                    }
                });


            }
        }
//        crawlerData("http://hotel.qunar.com/city/changsha/", "长沙酒店url");
    }

    public static void crawlerData1(String url) throws InterruptedException {
        WebDriver webDriver = new WebDriver();
        org.openqa.selenium.WebDriver driver1 = webDriver.driver;
        driver1.get(url);
        Thread.sleep(2000);
        driver1.findElement(By.xpath("//input[@id='ugc_comment_filter_worse']")).click();
        //翻页循环爬取差评
        while (true) {
            //获取差评数据
            Thread.sleep(1000);
            try {
                String name = driver1.findElement(By.xpath("//div[@id='detail_pageHeader']/h2/span")).getText();
                List<WebElement> comment = driver1.findElements(By.xpath("//div[@class='js-feed-list']/div"));
                for (WebElement webElement : comment) {
                    String data = webElement.getText();
                    if (Pattern.matches(".*?(wifi|电视|信号|网络|网速).*", data)) {
                        //只保留含“wifi”“电视”“信号”“网络”“网速”的评论
                        System.out.println(data);
                        QBRDao.insertQNR(url, name, data);

                    }
                }
                driver1.findElement(By.xpath("//a[contains(@data-page,\"next\")]")).click();
            } catch (Exception e) {
                System.out.println("列表页爬取完成！");
                break;
            }

        }
        driver1.close();

    }


    //去哪儿爬取
    public static void crawlerData(String url1, String fileName) throws InterruptedException {
        WebDriver webDriver = new WebDriver();
        org.openqa.selenium.WebDriver driver = webDriver.driver;
        driver.get(url1);
        String url="";
        Thread.sleep(2000);
        while (true) {
            String setscroll = "document.documentElement.scrollTop=" + "3000";
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript(setscroll);
            Thread.sleep(4000);
            List<WebElement> list = driver.findElements(By.xpath("//div[@id='jxContentPanel']/div"));
            for (WebElement webElement : list) {
                String strs = webElement.getAttribute("data-link");
                url = domain + strs + "\n";
                //将详情页输出到文件，有webmagic做爬取
                FileOperate.writeStringToFile(url, "./Text/", fileName);
                //存库操作
            }
            try {
                driver.findElement(By.xpath("//a[contains(@class,\"num icon-tag\")]")).click();
            } catch (Exception e) {
                System.out.println("列表页爬取完成！");
                driver.close();
                break;
            }

        }
    }
}
