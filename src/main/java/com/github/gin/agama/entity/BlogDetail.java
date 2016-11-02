package com.github.gin.agama.entity;

import com.github.gin.agama.annotation.Xpath;

import java.util.Date;

/**
 * Created by FSTMP on 2016/10/21.
 */
public class BlogDetail extends AgamaEntity {

    @Xpath("//a/[@id='cb_post_title_url']")
    private String title;

    @Xpath(value = "//div/[@id='cnblogs_post_body']",content = "html")
    private String content;

    @Xpath("//div/[@class='postDesc']/a[1]")
    private String poster;

    @Xpath("//span/[@id='post-date']")
    private Date postDate;

    @Xpath("//span/[@id='post_view_count']")
    private String read;

    @Xpath("//span/[@id='post_comment_count']")
    private String comment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "标题：" + title + ", 发表人：" + poster + ", 发表时间：" + postDate +
                ", 阅读数：" + read + ", 评论数：" + comment +
                ", 文章：" + content ;
    }
}
