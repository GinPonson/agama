package cloud.process;

import cloud.Constant;
import cloud.RequestUtil;
import cloud.Singleton;
import cloud.entity.YunData;
import cloud.entity.YunUser;
import cloud.storer.YunDataDataStorer;
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
    @Override
    public void process(Page page) {
        Logger logger = LoggerFactory.getLogger(FollowProcess.class);

        //解析页面
        Pattern pattern = Pattern.compile("window.yunData = (.*})");
        Matcher matcher = pattern.matcher(page.getRender().renderToHtml().toString());
        while (matcher.find()) {
            String json = matcher.group(1);
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

            YunUser user = Singleton.getYunUserService().get(uk);
            //设置该用户的资源是否已获取完毕
            if((start + Constant.LIMIT) >= user.getPubshareCount()){
                Singleton.getYunUserService().updateYunCrawled(uk);

                List<YunUser> yunUserList = Singleton.getYunUserService().findUnfinish();
                for(YunUser yunUser : yunUserList){
                    Request request = RequestUtil.createRequest();
                    request.setUrl(String.format(Constant.YUN_URL, yunUser.getUk(), 0));
                    page.getRequests().add(request);
                }

            } else {
                Request request = RequestUtil.createRequest();
                request.setUrl(String.format(Constant.YUN_URL, user.getUk(), start + Constant.LIMIT));
                page.getRequests().add(request);
            }

            //循环获取所有的页面
            /*for(int i =0 ; i < user.getPubshareCount();i = i+ Constant.LIMIT){
                Request request = RequestUtil.createRequest();
                request.setUrl(String.format(Constant.YUN_URL, user.getUk(), i));
                page.getRequests().add(request);
            }*/
        }

    }

    public static void main(String[] args) {
        HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
        ProxyPool.addProxy(proxy);

        CrawlConfiger config = new CrawlConfiger();
        config.setThreadNum(2);
        //config.setSleepTime(8000);

        List<YunUser> yunUserList = Singleton.getYunUserService().findUnfinish();
        if(yunUserList.isEmpty()){
            //当缺少没有爬取资源完毕的用户数据时，使用默认uk获取
            Request request = RequestUtil.createRequest();
            request.setUrl(String.format(Constant.YUN_URL,Constant.DEFAULT_UK,0));
            config.getStartRequests().add(request);
        } else {
            //获取每个用户的所有资源链接
            for(YunUser user : yunUserList){
                Request request = RequestUtil.createRequest();
                request.setUrl(String.format(Constant.YUN_URL, user.getUk(), 0));
                config.getStartRequests().add(request);
            }
        }

        JCrawler.create(new BaiduYunProcess()).persistBy(new YunDataDataStorer()).setConfig(config).run();
    }
}
