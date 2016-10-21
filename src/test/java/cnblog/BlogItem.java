package cnblog;

import com.github.gin.agama.annotation.Agama;
import com.github.gin.agama.annotation.Config;
import com.github.gin.agama.annotation.Proxy;
import com.github.gin.agama.annotation.Xpath;

/**
 * Created by FSTMP on 2016/10/20.
 */
public class BlogItem {

    private int id;

    @Xpath("//a[@class='titlelnk']")
    private String title;

    @Xpath("//p[@class='post_item_summary']")
    private String summary;

    @Xpath("//a[@class='lightblue']")
    private String poster;

    @Agama(processer = BlogDetailProcess.class,
            //configs = {@Config(key = "depth",val = "2"),@Config(key = "threadNum",val = "2")},
            proxy = {@Proxy(host = "10.228.110.21",port = 80,username = "panyongjian",password = "pan240409F")})
    @Xpath("//a[@class='titlelnk']/@href")
    private String href;

    @Xpath("//div[@class='post_item_foot']")
    private String postTime;

    @Xpath("//span[@class='article_view']")
    private String read;

    @Xpath("//span[@class='diggnum']")
    private String diggnum;

    @Xpath("//span[@class='article_comment']")
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
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

    @Override
    public String toString() {
        return "标题:"+ title +"，发布人:" + poster +"，链接:"+href;
    }
}
