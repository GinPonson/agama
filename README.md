# agama
agamga是一个轻量高效的Java爬虫框架。

# 特点
- [x] 使用流式Api，方便易用
- [x] 使用自定义的xpath注解+POJO方式爬取
- [x] 支持多线程
- [x] 支持Redis实现分布式抓取
- [x] 支持抓取Ajax加载的页面
- [x] 支持解析Json形式的Api数据
- [x] 支持抓取js变量
- [X] 支持代理池(静态)

# 使用
```java
@Xpath("//div[@id='post_list']/div[@class='post_item']")
public class CNBlogItem extends XpathEntity {

    @Xpath("//a[@class='titlelnk']")
    private String title;

    @Xpath("//p[@class='post_item_summary']")
    private String summary;

    @Xpath("//a[@class='lightblue']")
    private String poster;

    @Xpath("//a[@class='titlelnk']/@href")
    private String href;

    @Download(dist = "D:\\test\\${poster}.jpg")
    @Url(src = "//img/@src")
    private String photo;

    @Url(src ="//a[@class='titlelnk']/@href",click = true)
    private CNBlogDetail cnBlogDetail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getHref() {
        return href;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public CNBlogDetail getCnBlogDetail() {
        return cnBlogDetail;
    }

    public void setCnBlogDetail(CNBlogDetail cnBlogDetail) {
        this.cnBlogDetail = cnBlogDetail;
    }

    public static void main(String[] args) {
        JCrawler.create()
                .crawl("http://www.cnblogs.com/")
                .prey(CNBlogItem.class)
                .run();
    }
}
```

```java
public class CNBlogDetail extends XpathEntity {

    @Xpath("//div[@id='post_detail']")
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
```

# TODO
- [ ] 支持Jquery注解
- [ ] 支持自动登录
- [ ] 支持定时器
- [ ] 支持与Spring结合开发

# 项目依赖
jsoup、htmlcleaner、selenium、redisson、fastjson
