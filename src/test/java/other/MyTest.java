package other;

import cnblog.CNBlog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONPath;
import com.github.gin.agama.annotation.Xpath;
import com.github.gin.agama.entity.AgamaEntity;
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
import java.util.HashMap;
import java.util.Set;

/**
 * Created by FSTMP on 2017/2/28.
 */
@Xpath("//div[@class='post_item']")
public class MyTest {


    @Test
    public void test() throws IOException {
        //Reflections reflections = new Reflections();
        CNBlog cnBlog = new CNBlog();
        cnBlog.setHref("aa");
        BeanMap beanMap = BeanMap.create(cnBlog);
        System.out.println(beanMap);
        String json = JSON.toJSONString(cnBlog);
        System.out.println(JSONPath.eval(JSON.parseObject(json),"$.href"));
        URL url = Resources.getResource("UserAgent.json");
        String json1  = Files.toString(new File(url.getPath()), Charset.defaultCharset());
        JSONArray array = JSON.parseArray(json1);
        System.out.println(array.getString(0));

        new ConfigurationBuilder().forPackages("com.github.gin.agama");
        Reflections reflections = new Reflections("cnblog");
        Set<Class<? extends AgamaEntity>> set =  reflections.getSubTypesOf(AgamaEntity.class);
        System.out.println(set);

        Set<Class<?>> set1 = reflections.getTypesAnnotatedWith(Xpath.class);
        System.out.println(set1);
    }


}
