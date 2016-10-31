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
System.out.println(page.getUrl());
        Matcher m = Constant.USER_PATTERN.matcher(page.getUrl());
        if(m.find()){
            String uk = m.group(1);
            Singleton.getYunUserService().updateFansCrawled(Long.parseLong(uk));
        }

        page.setRawText(page.getRawText().replace("fans_list", "list").replace("fans_uname", "uname").replace("fans_uk", "uk"));

        List<YunUser> userList = page.getRender().renderToJson().toEntityList(YunUser.class);

        for(YunUser user : userList){
            for(int i =0 ; i < user.getFollowCount();i = i+ Constant.LIMIT){
                Request request = RequestUtil.createRequest();
                request.setUrl(String.format(Constant.FANS_URL, user.getUk(), Constant.LIMIT, i));
                page.getRequests().add(request);
            }
        }
System.out.println(page.getRender().renderToJson().toString());
        page.getResultItems().add(userList);
    }

}
