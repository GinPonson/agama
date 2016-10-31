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
        //解析页面
        Pattern pattern = Pattern.compile("window.yunData = (.*})");
        Matcher matcher = pattern.matcher(page.getRender().renderToHtml().toString());
        while (matcher.find()) {
            String json = matcher.group(1);
            AgamaJson agamaJson = new AgamaJson(json);
            List<YunData> datas = agamaJson.toEntityList(YunData.class);

            //保存资源
            page.getResultItems().add(datas);
        }

        Matcher m = Constant.YUN_PATTERN.matcher(page.getUrl());
        if(m.find()){
            long uk = Long.parseLong(m.group(1));
            int start = Integer.parseInt(m.group(2));

            YunUser user = Singleton.getYunUserService().get(uk);
            //设置该用户的资源是否已获取完毕
            if((start + Constant.LIMIT) >= user.getPubshareCount()){
                Singleton.getYunUserService().updateYunCrawled(uk);
            }

            //循环获取所有的页面
            for(int i =0 ; i < user.getPubshareCount();i = i+ Constant.LIMIT){
                Request request = RequestUtil.createRequest();
                request.setUrl(String.format(Constant.YUN_URL, user.getUk(), i));
                page.getRequests().add(request);
            }
        }

    }

    public static void main(String[] args) {
        HttpProxy proxy = new HttpProxy(Proxy.Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
        //ProxyPool.addProxy(proxy);

        CrawlConfiger config = new CrawlConfiger();
        config.setThreadNum(1);

        List<YunUser> yunUserList = Singleton.getYunUserService().findUnfinish();
        if(yunUserList.isEmpty()){
            Request request = RequestUtil.createRequest();
            request.setUrl(String.format(Constant.YUN_URL,Constant.DEFAULT_UK,0));
            config.getStartRequests().add(request);
        } else {
            for(YunUser user : yunUserList){
                for(int i =0 ; i < user.getFollowCount();i = i+ Constant.LIMIT){
                    Request request = RequestUtil.createRequest();
                    request.setUrl(String.format(Constant.YUN_URL, user.getUk(), i));
                    config.getStartRequests().add(request);
                }
            }
        }

        config.setThreadNum(2);
        JCrawler.create(new BaiduYunProcess()).persistBy(new YunDataDataStorer()).setConfig(config).run();
    }
}
