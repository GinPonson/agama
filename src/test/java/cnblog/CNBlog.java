package cnblog;

import com.github.gin.agama.annotation.Xpath;
import com.github.gin.agama.site.bean.XpathEntity;

import java.util.List;

/**
 * Created by FSTMP on 2016/10/20.
 */
public class CNBlog extends XpathEntity {

    @Xpath("//div[@id='post_list']/div[@class='post_item']")
    private List<CNBlogItem> items;

    public List<CNBlogItem> getItems() {
        return items;
    }

    public void setItems(List<CNBlogItem> items) {
        this.items = items;
    }
}
