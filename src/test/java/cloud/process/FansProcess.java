package cloud.process;

import cloud.Constant;
import cloud.RequestUtil;
import cloud.Singleton;
import cloud.entity.YunUser;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by FSTMP on 2016/10/27.
 */
public class FansProcess implements PageProcess {
    Logger logger = LoggerFactory.getLogger(FollowProcess.class);

    @Override
    public void process(Page page) {
        Matcher m = Constant.USER_PATTERN.matcher(page.getUrl());
        if(m.find()){
            long uk = Long.parseLong(m.group(1));
            int start = Integer.parseInt(m.group(3));

            //设置用户的粉丝是否已经被爬取,从其他人列表中取出的fans_count不一定对
            int fanCount = page.getRender().renderToJson().getJson().getInteger("total_count");
            if((start + Constant.LIMIT) >= fanCount){
                Singleton.getYunUserService().updateFansCrawled(uk);

                List<YunUser> yunUserList = Singleton.getYunUserService().findFansUnCrawled();
                for(YunUser yunUser : yunUserList){
                    Request request = RequestUtil.createRequest();
                    request.setUrl(String.format(Constant.FANS_URL, yunUser.getUk(), Constant.LIMIT, 0));
                    page.getRequests().add(request);
                }
            } else {
                Request request = RequestUtil.createRequest();
                request.setUrl(String.format(Constant.FANS_URL, uk, Constant.LIMIT, start+Constant.LIMIT));
                page.getRequests().add(request);
            }
        }

        //为了fans和YunUser可以重用
        page.setRawText(page.getRawText().replace("fans_list", "list").replace("fans_uname", "uname").replace("fans_uk", "uk"));
        List<YunUser> userList = page.getRender().renderToJson().toEntityList(YunUser.class);
        if(logger.isDebugEnabled())
            logger.debug("fans:"+page.getRawText());

        //保存粉丝用户
        page.getResultItems().add(userList);

    }

}
