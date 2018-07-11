package bean;

public class HotelBean {

    private String id;
    private String url;
    private String grade;
    private String name;
    private String group;
    private String city;
    private String location;
    private String address;
    private String roadcross;
    private String tel;
    private String opentime;
    private String decorate;
    private String totalroom;
    private String rate;
    private String hotel_content;
    private String c_date;
    private String price;
    private String c_from;

    public String getC_from() {
        return c_from;
    }

    public void setC_from(String c_from) {
        this.c_from = c_from;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "HotelBean{" +
                "grade='" + grade + '\'' +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                ", roadcross='" + roadcross + '\'' +
                ", tel='" + tel + '\'' +
                ", opentime='" + opentime + '\'' +
                ", decorate='" + decorate + '\'' +
                ", totalroom='" + totalroom + '\'' +
                ", rate='" + rate + '\'' +
                ", hotel_content='" + hotel_content + '\'' +
                ", c_date='" + c_date + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoadcross() {
        return roadcross;
    }

    public void setRoadcross(String roadcross) {
        this.roadcross = roadcross;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getDecorate() {
        return decorate;
    }

    public void setDecorate(String decorate) {
        this.decorate = decorate;
    }

    public String getTotalroom() {
        return totalroom;
    }

    public void setTotalroom(String totalroom) {
        this.totalroom = totalroom;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getHotel_content() {
        return hotel_content;
    }

    public void setHotel_content(String hotel_content) {
        this.hotel_content = hotel_content;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }
}
