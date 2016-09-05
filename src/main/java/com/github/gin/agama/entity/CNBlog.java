package com.github.gin.agama.entity;

import com.github.gin.agama.annotation.Xpath;
import com.github.gin.qcsv.annotation.CSV;


@Xpath("//div[@class='post_item']")
public class CNBlog {

    private int id;

    @CSV(name = "标题")
    @Xpath("//a[@class='titlelnk']")
    private String title;

    @CSV(name = "概要")
    @Xpath("//p[@class='post_item_summary']")
    private String summary;

    @CSV(name = "发布人")
    @Xpath("//a[@class='lightblue']")
    private String poster;

    @CSV(name = "文章链接")
    @Xpath("//a[@class='titlelnk']/@href")
    private String href;

    @CSV(name = "发布时间")
    @Xpath("//div[@class='post_item_foot']")
    private String postTime;

    @CSV(name = "阅读数")
    @Xpath("//span[@class='article_view']")
    private String read;

    @CSV(name = "推荐数")
    @Xpath("//span[@class='diggnum']")
    private String diggnum;

    @CSV(name = "评论数")
    @Xpath("//span[@class='article_comment']")
    private String comment;


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

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
}
