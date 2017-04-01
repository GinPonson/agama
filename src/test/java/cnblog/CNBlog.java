package cnblog;

import com.github.gin.agama.annotation.*;
import com.github.gin.agama.core.CrawlerConfig;
import com.github.gin.agama.core.CrawlerContext;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.site.entity.XpathEntity;

/**
 * Created by FSTMP on 2016/10/20.
 */
@Prey(matchUrl = "http://www.cnblogs.com/")
@Xpath("//div[@id='post_list']/div[@class='post_item']")
public class CNBlog extends XpathEntity {

    @Xpath("//a[@class='titlelnk']")
    private String title;

    @Xpath("//p[@class='post_item_summary']")
    private String summary;

    @Xpath("//a[@class='lightblue']")
    private String poster;

    @Xpath("//a[@class='titlelnk']/@href")
    private String href;

    @Regex(regex = "\\(([\\d]*)\\)",group = 1)
    @Xpath("//span[@class='article_view']")
    private String read;

    @Xpath("//span[@class='diggnum']")
    private String diggnum;

    @Regex(regex = "\\(([\\d]*)\\)",group = 1)
    @Xpath("//span[@class='article_comment']")
    private String comment;

    //@Download(dist = "D:\\test\\${poster}")
    @Url(src = "//img/@src")
    private String photo;

   /* @Url(src ="//a[@class='titlelnk']/@href",click = true)
    private CNBlogDetail cnBlogDetail;*/

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /*public CNBlogDetail getCnBlogDetail() {
        return cnBlogDetail;
    }

    public void setCnBlogDetail(CNBlogDetail cnBlogDetail) {
        this.cnBlogDetail = cnBlogDetail;
    }*/

    public static void main(String[] args) {
        JCrawler.create()
                .crawl("http://www.cnblogs.com/")
                .prey(CNBlog.class)
                .context(
                        CrawlerContext.create()
                                .useConfig(new CrawlerConfig().setEnableProxy(true))
                                .build()
                )
                .run();
    }
}
