package bean;

public class WinBidBean {
    String area;
    String url;
    String urlList;
    String title;
    String content;

    String publishDate;
    String projectCode;
    String ccgpUrl;
    String ccgpHtml;
    String websiteSource;

    String websiteCity;
    String addTime;
    String read_label;
    String value2;

    public String getRead_label() {
        return read_label;
    }

    public void setRead_label(String read_label) {
        this.read_label = read_label;
    }

    public String getArea() {
        return area;
    }

    public void setArea( String area ) {
        this.area = area;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    public String getUrlList() {
        return urlList;
    }

    public void setUrlList( String urlList ) {
        this.urlList = urlList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate( String publishDate ) {
        this.publishDate = publishDate;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode( String projectCode ) {
        this.projectCode = projectCode;
    }

    public String getCcgpUrl() {
        return ccgpUrl;
    }

    public void setCcgpUrl( String ccgpUrl ) {
        this.ccgpUrl = ccgpUrl;
    }

    public String getCcgpHtml() {
        return ccgpHtml;
    }

    public void setCcgpHtml( String ccgpHtml ) {
        this.ccgpHtml = ccgpHtml;
    }

    public String getWebsiteSource() {
        return websiteSource;
    }

    public void setWebsiteSource( String websiteSource ) {
        this.websiteSource = websiteSource;
    }

    public String getWebsiteCity() {
        return websiteCity;
    }

    public void setWebsiteCity( String websiteCity ) {
        this.websiteCity = websiteCity;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime( String addTime ) {
        this.addTime = addTime;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2( String value2 ) {
        this.value2 = value2;
    }

    @Override
    public String toString() {
        return "WinBidBean{" +
                "area='" + area + '\'' +
                ", url='" + url + '\'' +
                ", urlList='" + urlList + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", ccgpUrl='" + ccgpUrl + '\'' +
                ", ccgpHtml='" + ccgpHtml + '\'' +
                ", websiteSource='" + websiteSource + '\'' +
                ", websiteCity='" + websiteCity + '\'' +
                ", addTime='" + addTime + '\'' +
                ", read_label='" + read_label + '\'' +
                ", value2='" + value2 + '\'' +
                '}';
    }
}
