# agama
agamga是一个轻量易用的Java爬虫框架。

# 特点
- [x] 使用流式Api，方便易用
- [x] 使用自定义的xpath注解+POJO方式爬取
- [x] 支持多线程
- [x] 支持Redis实现分布式抓取
- [x] 支持抓取Ajax加载的页面
- [x] 支持解析Json形式的Api数据

# 使用
```java
@Xpath("//div[@class='post_item']")
public class CNBlog extends AgamaEntity {

    @Xpath("//a[@class='titlelnk']")
    private String title;

    @Xpath("//p[@class='post_item_summary']")
    private String summary;

    @Xpath("//a[@class='lightblue']")
    private String poster;

    @Xpath("//a[@class='titlelnk']/@href")
    private String href;

    @Xpath("//span[@class='article_view']")
    private String read;

    @Xpath("//span[@class='diggnum']")
    private String diggnum;

    @Xpath("//span[@class='article_comment']")
    private String comment;

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

    public void setHref(String href) {
        this.href = href;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getDiggnum() {
        return diggnum;
    }

    public void setDiggnum(String diggnum) {
        this.diggnum = diggnum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

```

```java
public class CNBlogProcess implements PageProcess {

    @Override
    public void process(Page page) {
        page.addRequests(
                page.getRender()
                        .renderToHtml()
                        .xpath("//div[@class='pager']/a")
                        .attrs("href")
        );

        page.getResultItems().add(
                page.getRender()
                        .renderToHtml()
                        .toEntityList(CNBlog.class)
        );
    }

    public static void main(String[] args) {
        JCrawler.create()
                .crawl("http://www.cnblogs.com/")
                .processBy(new CNBlogProcess())
                .run();
    }
}
```

# TODO
- [ ] 支持智能过滤html标签
- [ ] 支持Jquery注解
- [ ] 支持url的正则抓取
- [ ] 支持代理池
- [ ] 支持自动登录
- [ ] 支持定时器
- [ ] 支持与Spring结合开发


# 项目依赖
jsoup、htmlcleaner、selenium、redisson、fastjson
