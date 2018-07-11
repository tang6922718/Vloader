package util;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import test.FantomJs;

import javax.xml.bind.Element;
import java.util.List;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/3/30-15:23
 * Modified By:
 */
public class Fatomjs {

    @Test
    public void image() throws InterruptedException {
        FantomJs fantomJs = new FantomJs();
        WebDriver webDriver = fantomJs.driver;
        int i = 1;
        webDriver.get("http://www.hcggzy.cn/gxhczbw/showinfo/zbgsmore.aspx");
        while (i <= 57) {

            System.out.println(webDriver.findElement(By.xpath("//*[@id=\"MoreInfoListZbgs1_Pager\"]/table/tbody/tr/td[1]")).getText());

            webDriver.findElement(By.xpath("//*[@id=\"MoreInfoListZbgs1_Pager\"]//a[last()-1]")).click();
            i++;
        }
    }
}
