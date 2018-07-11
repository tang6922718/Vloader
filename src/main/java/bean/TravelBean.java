package bean;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/23-15:12
 * Modified By:
 */
public class TravelBean {
        String url;
        String title;
        String comment;
        String time;
        String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public TravelBean() {

    }

    public TravelBean(String url, String title,String price, String comment, String time) {
        this.url = url;
        this.title = title;
        this.comment = comment;
        this.time = time;
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
