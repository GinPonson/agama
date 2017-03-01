package other;

import cnblog.CNBlog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.JSONReaderScanner;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.common.reflect.Reflection;
import net.sf.cglib.beans.BeanMap;
import org.junit.Test;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * Created by FSTMP on 2017/2/28.
 */
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
        URL url = Resources.getResource("header.json");
        String json1  = Files.toString(new File(url.getPath()), Charset.defaultCharset());
        JSONArray array = JSON.parseArray(json1);
        System.out.println(array);

        System.out.println(new Random(10).nextInt());
    }
}
