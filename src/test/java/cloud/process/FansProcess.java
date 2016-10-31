package cloud.process;

import cloud.Constant;
import cloud.RequestUtil;
import cloud.Singleton;
import cloud.entity.YunUser;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;

import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by FSTMP on 2016/10/27.
 */
public class FansProcess implements PageProcess {
    @Override
    public void process(Page page) {
        //设置用户的粉丝已经被爬取
        Matcher m = Constant.USER_PATTERN.matcher(page.getUrl());
        if(m.find()){
            String uk = m.group(1);
            Singleton.getYunUserService().updateFansCrawled(Long.parseLong(uk));
        }

        //为了fans和YunUser可以重用
        page.setRawText(page.getRawText().replace("fans_list", "list").replace("fans_uname", "uname").replace("fans_uk", "uk"));
        List<YunUser> userList = page.getRender().renderToJson().toEntityList(YunUser.class);

        //循环获取页面
        for(YunUser user : userList){
            for(int i =0 ; i < user.getFollowCount();i = i+ Constant.LIMIT){
                Request request = RequestUtil.createRequest();
                request.setUrl(String.format(Constant.FANS_URL, user.getUk(), Constant.LIMIT, i));
                page.getRequests().add(request);
            }
        }
        //保存粉丝用户
        page.getResultItems().add(userList);
    }

}
