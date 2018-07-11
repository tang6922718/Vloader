package bean;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/6/6-17:48
 * Modified By:
 */
public class Cd58Bean {
    String url;
    String name;
    String addr;
    String area;
    String type;
    String phone;
    String price;
    String houses;

    public Cd58Bean(String url, String name, String addr, String area, String type, String phone, String price, String houses) {
        this.url = url;
        this.name = name;
        this.addr = addr;
        this.area = area;
        this.type = type;
        this.phone = phone;
        this.price = price;
        this.houses = houses;
    }

    public Cd58Bean() {
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPrice() {
        return price;
    }

    public String getArea() {
        return area;
    }

    public String getType() {
        return type;
    }

    public String getHouses() {
        return houses;
    }

    public String getAddr() {
        return addr;
    }
}
