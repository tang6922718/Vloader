package bean;

/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/17-9:20
 * Modified By:
 */
public class NewsBean {

    String pageUrl;
    String content;


    public NewsBean() {
    }

    public NewsBean(String pageUrl, String content, String title, String publishDate) {
        this.pageUrl = pageUrl;
        this.content = content;
        this.title = title;
        this.publishDate = publishDate;
    }

    String title;
    String publishDate;

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}
