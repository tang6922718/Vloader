package tlz.core;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class Spider_One {


    String url = "http://www.zyccst.com/";
    Scanner scanner = new Scanner(System.in);

    @Test
    public void test() throws IOException {
       /* System.out.println("请输入搜索关键词：");
        String search=scanner.nextLine();*/
        System.setProperty("webdriver.chrome.driver", "C:/dev/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement keyWord = driver.findElement(By.id("txtSearchKeyWords"));
        keyWord.sendKeys("黄精");
        WebElement button = driver.findElement(By.id("searchByTypeAndKeyword"));
        button.click();
        /* Document doc = Jsoup.connect(url).get();
        String keyWord = scanner.next();
        Element  element = doc.getElementById("txtSearchKeyWords");*/
        //搜索跳转，进行爬去输出
        System.out.println(driver.getCurrentUrl());

/*/html/body/div[6]/div[1]/div[2]/div[2]/div[2]/div[1]*/ /* /html/body/div[6]/div[1]/div[2]/div[2]*/
//        /html/body/div[6]/div[1]/div[2]/div[2]/div[2]/div[1]/span/a
//        /html/body/div[6]/div[1]/div[2]/div[3]/div[2]/div[1]/span/a
        List<WebElement>  goods = driver.findElements(By.xpath("/html/body/div[6]/div[1]/div[2]/div/div[2]/div[1]"));
        for (WebElement good:goods){
            WebElement  info1= good.findElement(By.cssSelector("a"));
          /*  WebElement   addr= good.findElement(By.xpath("//div[2]/div[2]/div[2]/p[3]/span[2]"));*/
            System.out.println("标题："+info1.getAttribute("title"));
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    public static void  crawl_start(){




    }

}
