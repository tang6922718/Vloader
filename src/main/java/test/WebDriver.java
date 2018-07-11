package test;



import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/4/18-15:58
 * Modified By:
 */
public class WebDriver {

    public ChromeDriver driver;
    public Boolean isProxy;
    public WebDriver() {
        System.setProperty("webdriver.chrome.driver", "C:/dev/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        this.driver = driver;
    }

    public static DesiredCapabilities  useProxy(String proxyIpAndPort){
        DesiredCapabilities cap = new DesiredCapabilities();
        Proxy proxy=new Proxy();
        proxy.setHttpProxy(proxyIpAndPort).setFtpProxy(proxyIpAndPort).setSslProxy(proxyIpAndPort);
        cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
        cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
        System.setProperty("http.nonProxyHosts", "localhost");
        cap.setCapability(CapabilityType.PROXY, proxy);
        return cap;
    }
}
