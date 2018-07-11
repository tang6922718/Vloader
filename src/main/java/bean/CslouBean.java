package bean;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/6/6-17:48
 * Modified By:
 */
public class CslouBean {
    String url;
    String name;
    String type;
    String area;
    String phone;
    String floor_num;
    String opening_time;
    String decorate_state;
    String developers;
    String addr;
    String detail_introduce;

    public CslouBean() {
    }

    public CslouBean(String url, String name, String type, String addr, String area, String phone, String floor_num, String opening_time, String decorate_state, String developers, String detail_introduce) {
        this.url = url;
        this.name = name;
        this.type = type;
        this.addr = addr;
        this.area = area;
        this.phone = phone;
        this.floor_num = floor_num;
        this.opening_time = opening_time;
        this.decorate_state = decorate_state;
        this.developers = developers;
        this.detail_introduce = detail_introduce;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFloor_num() {
        return floor_num;
    }

    public void setFloor_num(String floor_num) {
        this.floor_num = floor_num;
    }

    public String getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(String opening_time) {
        this.opening_time = opening_time;
    }

    public String getDecorate_state() {
        return decorate_state;
    }

    public void setDecorate_state(String decorate_state) {
        this.decorate_state = decorate_state;
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDetail_introduce() {
        return detail_introduce;
    }

    public void setDetail_introduce(String detail_introduce) {
        this.detail_introduce = detail_introduce;
    }
}
