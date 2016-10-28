package cloud;

import com.github.gin.agama.core.CrawlConfiger;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.proxy.ProxyPool;
import com.github.gin.agama.site.AgamaJson;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.Proxy;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FSTMP on 2016/10/27.
 */
public class BaiduYunProcess implements PageProcess {
    @Override
    public void process(Page page) {
        //System.out.println(page.getRender().renderToHtml().toString());
        Pattern pattern = Pattern.compile("window.yunData = (.*})");
        Matcher matcher = pattern.matcher(page.getRender().renderToHtml().toString());
        String json = null;
        while (matcher.find()) {
            json = matcher.group(1);
            AgamaJson agamaJson = new AgamaJson(json);
            List<YunData> datas = agamaJson.toEntityList(YunData.class);
            System.out.println(datas.size());
        }
    }

    public static void main(String[] args) {
        HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
        //ProxyPool.addProxy(proxy);

        Request request = new Request();
        request.getHeaders().put("X-Requested-With","XMLHttpRequest");
        request.getHeaders().put("Accept", "application/json, text/javascript, */*; q=0.01");
        request.getHeaders().put("Referer", "https://yun.baidu.com/share/home?uk=325913312#category/type=0");
        request.getHeaders().put("Accept-Language", "zh-CN");
        request.setUrl("http://pan.baidu.com/wap/share/home?uk=2889076181&start=0&adapt=pc&fr=ftw");

        CrawlConfiger config = new CrawlConfiger(request);
        config.setDepth(1);
        config.setThreadNum(2);
        JCrawler.create(new BaiduYunProcess()).setConfig(config).run();
    }
}
