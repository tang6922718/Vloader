package requestGenerator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import test.FantomJs;
import test.WebDriver;

import java.util.List;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/31-15:37
 * Modified By:
 */
public class BaiDuChromeDriver {
    static int i = 1;

    public static void main(String[] args) throws InterruptedException {
        crawlerData();
    }


    public static void crawlerData1() throws InterruptedException {
        WebDriver driver1 = new WebDriver();
        org.openqa.selenium.chrome.ChromeDriver driver = driver1.driver;
        driver.get("http://api.map.baidu.com/lbsapi/getpoint/index.html");
        while (true) {
            List<WebElement> list = driver.findElements(By.xpath("//a[@class='e_title js_list_name']"));
            //处理单条详情
            for (WebElement webElement : list) {
                webElement.click();
                Thread.sleep(2000);
                String price = driver.findElement(By.xpath("//div[@class='pr']/b/text()")).toString();
                System.out.println(price);
                driver.navigate().back();

            }
            //翻页
//            WebDriverWait wait4 = new WebDriverWait(driver, 100);
//            wait4.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='next']"))).click();

        }
    }

    //百度拾取坐标系统爬取
    public static void crawlerData() throws InterruptedException {
        FantomJs fantomJs = new FantomJs();
        org.openqa.selenium.WebDriver driver = fantomJs.driver;
        driver.get("http://api.map.baidu.com/lbsapi/getpoint/index.html");

        driver.findElement(By.xpath("//*[@id='localvalue']")).sendKeys("常德写字楼");
        driver.findElement(By.xpath("//*[@id='localsearch']")).click();

        while (true) {
            List<WebElement> list = driver.findElements(By.xpath("//ul[@class='local_s']/li"));
            for (WebElement webElement : list) {
              String [] strs = webElement.getText().split("\n");
              //存库操作
            }
            try {
                driver.findElement(By.xpath("//a[contains(text(),'下一页')]")).click();
            } catch (Exception e) {
                System.out.println("列表页爬取完成！");
                break;
            }
            System.out.println("**********************************************当前爬取page:" + (i++));
            Thread.sleep(2000);
        }
    }
}
