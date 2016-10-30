package cloud.process;

import cloud.entity.YunUser;
import com.github.gin.agama.core.CrawlConfiger;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;

import java.net.Proxy;
import java.util.List;

/**
 * Created by FSTMP on 2016/10/27.
 */
public class FansProcess implements PageProcess {
    @Override
    public void process(Page page) {
        page.setRawText(page.getRawText().replace("fans_list", "list").replace("fans_uname", "uname").replace("fans_uk", "uk"));

        List<YunUser> fansList = page.getRender().renderToJson().toEntityList(YunUser.class);

        page.getResultItems().add(fansList);
    }

    public static void main(String[] args) {
        HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
        //ProxyPool.addProxy(proxy);

        Request request = new Request();
        request.getHeaders().put("X-Requested-With","XMLHttpRequest");
        request.getHeaders().put("Accept", "application/json, text/javascript, */*; q=0.01");
        request.getHeaders().put("Referer", "https://yun.baidu.com/share/home?uk=325913312#category/type=0");
        request.getHeaders().put("Accept-Language", "zh-CN");
        request.setUrl("http://pan.baidu.com/pcloud/friend/getfanslist?query_uk=2889076181&limit=24&start=0&bdstoken=null&channel=chunlei&clienttype=0&web=1&logid=MTQ3NDAzNjQwNzg3OTAuNzM1MzMxMDUyMDczMjYxNA==");

        CrawlConfiger config = new CrawlConfiger(request);
        config.setDepth(1);
        config.setThreadNum(2);
        JCrawler.create(new FansProcess()).setConfig(config).run();
    }
}
