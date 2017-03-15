package cloud;

import com.github.gin.agama.annotation.JS;
import com.github.gin.agama.annotation.Json;
import com.github.gin.agama.core.CrawlerContext;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.site.entity.XpathEntity;

import java.util.List;

/**
 * Created by GinPonson on 3/15/2017.
 */
public class Yun extends XpathEntity{

    @JS(var = "window",jsonpath = "$.yunData.feedata.records")
    private List<YunData> yunDatas ;

    public List<YunData> getYunDatas() {
        return yunDatas;
    }

    public void setYunDatas(List<YunData> yunDatas) {
        this.yunDatas = yunDatas;
    }

    public static void main (String[] args){

        Request request = new Request();
        request.getHeaders().put("X-Requested-With","XMLHttpRequest");
        request.getHeaders().put("Accept", "application/json, text/javascript, */*; q=0.01");
        request.getHeaders().put("Referer", "https://yun.baidu.com/share/home?uk=325913312#category/type=0");
        request.getHeaders().put("Accept-Language", "zh-CN");
        request.setUrl("http://pan.baidu.com/wap/share/home?uk=2889076181&start=0&adapt=pc&fr=ftw");

        JCrawler.create()
                .crawl(request)
                .prey(Yun.class)
                .context(CrawlerContext.create().build())
                .run();
    }
}
