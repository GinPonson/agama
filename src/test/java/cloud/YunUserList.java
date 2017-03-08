package cloud;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.gin.agama.annotation.Json;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.entity.AgamaEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by GinPonson on 10/30/2016.
 */
public class YunUserList extends AgamaEntity{

    @Json("$.fans_list")
    List<YunUser> yunUsers;

    @Json("$.total_count")
    int totalCount;

    public List<YunUser> getYunUsers() {
        return yunUsers;
    }

    public void setYunUsers(List<YunUser> yunUsers) {
        this.yunUsers = yunUsers;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public static void main (String[] args){
        JCrawler.create()
                .crawl("http://pan.baidu.com/pcloud/friend/getfanslist?query_uk=2889076181&limit=20&start=0&bdstoken=null&channel=chunlei&clienttype=0&web=1&logid=MTQ3NDAzNjQwNzg3OTAuNzM1MzMxMDUyMDczMjYxNA==")
                .prey(YunUserList.class)
                .run();
    }
}
