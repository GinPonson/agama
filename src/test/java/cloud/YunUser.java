package cloud;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.gin.agama.annotation.Json;
import com.github.gin.agama.core.CrawlerContext;
import com.github.gin.agama.core.JCrawler;
import com.github.gin.agama.entity.AgamaEntity;
import com.github.gin.agama.entity.JsonEntity;

import java.util.Date;

import static jodd.Jodd.JSON;

/**
 * Created by FSTMP on 2017/3/8.
 */
@Json("$.fans_list")
public class YunUser extends JsonEntity {
    public long id;

    @Json("$.avatar_url")
    private String avatarUrl;

    @Json("$.follow_time")
    private Date followTime;

    @Json("$.fans_uname")
    private String username;

    @Json("$.fans_uk")
    private long uk;

    @Json("$.follow_count")
    private int followCount;

    @Json("$.fans_count")
    private int fansCount;

    @Json("$.pubshare_count")
    private int pubshareCount;

    private Date updateTime;

    private boolean flag;

    private Integer version = 1;

    private boolean followCrawled;

    private boolean fansCrawled;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Date followTime) {
        this.followTime = followTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getUk() {
        return uk;
    }

    public void setUk(long uk) {
        this.uk = uk;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public boolean isFollowCrawled() {
        return followCrawled;
    }

    public void setFollowCrawled(boolean followCrawled) {
        this.followCrawled = followCrawled;
    }

    public boolean isFansCrawled() {
        return fansCrawled;
    }

    public void setFansCrawled(boolean fansCrawled) {
        this.fansCrawled = fansCrawled;
    }

    public int getPubshareCount() {
        return pubshareCount;
    }

    public void setPubshareCount(int pubshareCount) {
        this.pubshareCount = pubshareCount;
    }


    public static void main (String[] args){
        JCrawler.create()
                .crawl("http://pan.baidu.com/pcloud/friend/getfanslist?query_uk=2889076181&limit=20&start=0&bdstoken=null&channel=chunlei&clienttype=0&web=1&logid=MTQ3NDAzNjQwNzg3OTAuNzM1MzMxMDUyMDczMjYxNA==")
                .prey(YunUser.class)
                .context(CrawlerContext.create().build())
                .run();
    }
}
