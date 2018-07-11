package requestGenerator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import test.WebDriver;

import java.io.*;
import java.util.List;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/31-15:37
 * Modified By:
 */
public class ChromeDriver {
    public static void main(String[] args) throws InterruptedException {
        crawlerData();
    }


    public static void crawlerBDBKData() throws IOException {

        String s;
        WebDriver driver1 = new WebDriver();
        File file = new File("./personList.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        org.openqa.selenium.chrome.ChromeDriver driver = driver1.driver;
        driver.get("https://baike.baidu.com/");
        while ((s = bufferedReader.readLine()) != null) {
            driver.findElement(By.xpath("//input[@id='query']")).sendKeys(s);
            driver.findElement(By.xpath("//button[@id='search']")).click();
            driver.findElement(By.xpath("//input[@id='query']")).clear();
        }


    }


    public static void crawlerData() {
        WebDriver driver1 = new WebDriver();
        org.openqa.selenium.chrome.ChromeDriver driver = driver1.driver;
        driver.get("http://map.baidu.com/?newmap=1&ie=utf-8&s=s%26wd%3D%E5%B8%B8%E5%BE%B7%E5%8C%BB%E9%99%A2");
        /*
        Actions action = new Actions(driver);
        action.sendKeys(Keys.CONTROL + "n").perform();
       if (driver.getWindowHandles().size() >5){
           driver.close();
       }*/
        List<WebElement> list = driver.findElements(By.xpath("//ul[@class='poilist']/li"));
        for (int i=0;i<list.size();i++) {
            String name = list.get(0).findElement(By.xpath("//a[@data-stat-code='poisearch.all.title']/text()")).toString();



//            String phone = webElement.findElement(By.xpath("//div[@class='row tel']/text()")).toString();


            System.out.println(name + "\r\n"  + "\r\n" );
        }
    }
}
