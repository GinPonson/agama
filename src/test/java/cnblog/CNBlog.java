package cnblog;

import com.github.gin.agama.annotation.ChildItem;
import com.github.gin.agama.annotation.Xpath;
import com.github.gin.agama.entity.AgamaEntity;

import java.util.ArrayList;
import java.util.List;

public class CNBlog extends AgamaEntity {

    private int id;

    @ChildItem(BlogItem.class)
    @Xpath("//div[@class='post_item']")
    private List<BlogItem> blogItemses = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<BlogItem> getBlogItemses() {
        return blogItemses;
    }

    public void setBlogItemses(List<BlogItem> blogItemses) {
        this.blogItemses = blogItemses;
    }
}
