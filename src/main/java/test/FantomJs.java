package test;

import com.sun.jna.platform.win32.GL;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/4/4-15:48
 * Modified By:
 */
public class FantomJs {


    public WebDriver driver;

    public FantomJs() {
        System.setProperty("phantomjs.binary.path", "C:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        desiredCapabilities.setCapability("phantomjs.page.settings.loadImages", false);
        desiredCapabilities.setCapability("acceptSslCerts", true);
        //截屏支持
        desiredCapabilities.setCapability("loadImage", false);
        //css搜索支持
        desiredCapabilities.setCapability("platform","win7");
        desiredCapabilities.setCapability("cssSelectorsEnabled", true);
        //js支持
        desiredCapabilities.setJavascriptEnabled(true);
        WebDriver driver = new PhantomJSDriver(desiredCapabilities);
        this.driver = driver;
    }

    public static void main(String[] args) throws InterruptedException {
        FantomJs fantomJs = new FantomJs();
        WebDriver driver = fantomJs.driver;
        driver.get("http://www.cebpubservice.com/ctpsp_iiss/searchbusinesstypebeforedooraction/getSearch.do");
       driver.findElement(By.xpath("//li[@id='tenderBulletinTableId']")).click();
        while (true) {
            Thread.sleep(1000);
            List<WebElement> list = driver.findElements(By.xpath("//tbody[@id='tenderProjectTab']/tr"));
            //收集列表
            Thread.sleep(1000);
            for (WebElement webElement : list) {
                System.out.println(webElement.findElement(By.xpath("//text()")).toString());
            }
            //翻页
            WebDriverWait wait4 = new WebDriverWait(driver,100);
            wait4.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='next']"))).click();


        }

    }

}
