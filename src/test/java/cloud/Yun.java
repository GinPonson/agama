package cloud;

import com.github.gin.agama.annotation.*;
import com.github.gin.agama.core.CrawlerContext;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.downloader.DefaultHtmlUnitDownloader;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.site.entity.XpathEntity;

import java.util.List;

/**
 * Created by GinPonson on 3/15/2017.
 */
@Prey(matchUrl = "http://pan.baidu.com/wap/share/home")
public class Yun extends XpathEntity{

    @JS(var = "window",jsonpath = "$.yunData.feedata.records")
    private List<YunData> yunDatas ;

    @JS(var = "window",jsonpath = "$.yunData.feedata.total_count")
    private Integer totalCount;

    public List<YunData> getYunDatas() {
        return yunDatas;
    }

    public void setYunDatas(List<YunData> yunDatas) {
        this.yunDatas = yunDatas;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
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
