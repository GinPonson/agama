package cnblog;

import com.github.gin.agama.annotation.Xpath;
import com.github.gin.agama.site.entity.XpathEntity;

/**
 * Created by GinPonson on 2017/3/12.
 */
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
