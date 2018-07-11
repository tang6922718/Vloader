package proxy;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/6/28-9:57
 * Modified By:
 */
public class XiaoDaiLiProxy implements ProxyProvider {


    List<Proxy> list = new ArrayList<>();
    String  proxyUrl = "http://api.xdaili.cn/xdaili-api//privateProxy/applyStaticProxy?spiderId=d48156ea10064885ab1d237453c86f47&returnType=1&count=1";

    public  void addProxy() throws IOException {
        URL url = new URL(proxyUrl);
        URLConnection con = url.openConnection();
        con.setConnectTimeout(5 * 1000);
        InputStream is = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String str;
        while ((str = br.readLine()) != null) {
            String[] strings = str.split(":");
            this.list.add(new Proxy(strings[0], Integer.parseInt(strings[1])));
        }
    }

    @Override
    public void returnProxy(Proxy proxy, Page page, Task task) {
        list.add(0,proxy);
    }

    @Override
    public Proxy getProxy(Task task) {
        if (list.isEmpty()) {
            return list.get(0);
        } else {
            try {
                this.addProxy();
                return    getProxy(task);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
       return null;
    }
}
