package other;

import cnblog.CNBlog;
import cnblog.CNBlogItem;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONPath;
import com.github.gin.agama.annotation.Xpath;
import com.github.gin.agama.entity.AgamaEntity;
import com.github.gin.agama.entity.JsonEntity;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import net.sf.cglib.beans.BeanMap;
import org.junit.Test;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by FSTMP on 2017/2/28.
 */
public class MyTest extends JsonEntity {

    public static final String FOLLOW_URL = "http://yun.baidu.com/pcloud/friend/getfollowlist?query_uk=%d&limit=%d&start=%d&bdstoken=e6f1efec456b92778e70c55ba5d81c3d&channel=chunlei&clienttype=0&web=1&logid=MTQ3NDA3NDg5NzU4NDAuMzQxNDQyMDY2MjA5NDA4NjU=";

    public static final String FANS_URL = "http://pan.baidu.com/pcloud/friend/getfanslist?query_uk=%d&limit=%d&start=%d&bdstoken=null&channel=chunlei&clienttype=0&web=1&logid=MTQ3NDAzNjQwNzg3OTAuNzM1MzMxMDUyMDczMjYxNA==";

    public static final String YUN_URL = "http://pan.baidu.com/wap/share/home?uk=%d&start=%d&adapt=pc&fr=ftw";

    public static final long DEFAULT_UK = 2889076181L;


    @Test
    public void test() throws IOException {
        CNBlog blog = new CNBlog();
        CNBlogItem cnBlog = new CNBlogItem();
        cnBlog.setHref("aa");
        BeanMap beanMap = BeanMap.create(cnBlog);
        //System.out.println(beanMap);
        beanMap.put("title","bbb");
        //System.out.println(cnBlog.getTitle());

        List<CNBlogItem> list = new ArrayList<>();
        list.add(cnBlog);
        blog.setItems(list);

        String json = JSON.toJSONString(cnBlog);
        //System.out.println(JSONPath.eval(JSON.parseObject(json),"$.href"));

        String blogJson = JSON.toJSONString(blog);
        Object object = JSONPath.eval(JSON.parseObject(blogJson),"$.items");
        JSONArray array = (JSONArray) object;
        System.out.println(JSONPath.eval(array.get(0),"$.title"));

        List<CNBlogItem> list2 = JSONArray.parseArray(object.toString(),CNBlogItem.class);
        //System.out.println(object);
        //System.out.println(list2);

/*        new ConfigurationBuilder().forPackages("com.github.gin.agama");
        Reflections reflections = new Reflections();
        Set<Class<? extends AgamaEntity>> set =  reflections.getSubTypesOf(AgamaEntity.class);
        System.out.println(set);

        Set<Class<?>> set1 = reflections.getTypesAnnotatedWith(Xpath.class);
        System.out.println(set1);*/

        System.out.println(MyTest.class.getSuperclass());
    }


}
