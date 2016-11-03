package cloud.process;

import cloud.Constant;
import cloud.RequestUtil;
import cloud.Singleton;
import cloud.entity.YunData;
import cloud.entity.YunUser;
import cloud.storer.YunDataDataStorer;
import com.alibaba.fastjson.JSON;
import com.github.gin.agama.core.CrawlConfiger;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.proxy.ProxyPool;
import com.github.gin.agama.site.AgamaJson;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Proxy;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FSTMP on 2016/10/27.
 */
public class BaiduYunProcess implements PageProcess {
    Logger logger = LoggerFactory.getLogger(FollowProcess.class);
    @Override
    public void process(Page page) {
        String json = null;
        //解析页面
        Pattern pattern = Pattern.compile("window.yunData = (.*})");
        Matcher matcher = pattern.matcher(page.getRender().renderToHtml().toString());
        while (matcher.find()) {
            json = matcher.group(1);
            AgamaJson agamaJson = new AgamaJson(json);
            List<YunData> datas = agamaJson.toEntityList(YunData.class);

            //保存资源
            page.getResultItems().add(datas);

            if(logger.isDebugEnabled())
                logger.debug("Yun Data:"+json);
        }

        Matcher m = Constant.YUN_PATTERN.matcher(page.getUrl());
        if(m.find()){
            long uk = Long.parseLong(m.group(1));
            int start = Integer.parseInt(m.group(2));

            //设置该用户的资源是否已获取完毕
            int dataCount = JSON.parseObject(json).getJSONObject("feedata").getInteger("total_count");
            if((start + Constant.LIMIT) >= dataCount){
                Singleton.getYunUserService().updateYunCrawled(uk);

                List<YunUser> yunUserList = Singleton.getYunUserService().findUnfinish();
                for(YunUser yunUser : yunUserList){
                    Request request = RequestUtil.createRequest();
                    request.setUrl(String.format(Constant.YUN_URL, yunUser.getUk(), 0));
                    page.getRequests().add(request);
                }

            } else {
                Request request = RequestUtil.createRequest();
                request.setUrl(String.format(Constant.YUN_URL, uk, start + Constant.LIMIT));
                page.getRequests().add(request);
            }
        }

    }

}
