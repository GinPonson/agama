package cloud;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.gin.agama.entity.AgamaEntity;

import java.util.Date;

/**
 * Created by FSTMP on 2017/3/8.
 */
public class YunUser extends AgamaEntity {
    public long id;

    @JSONField(name = "avatar_url")
    private String avatarUrl;

    @JSONField(name = "follow_time")
    private Date followTime;

    @JSONField(name = "fans_uname")
    private String username;

    @JSONField(name = "uk")
    private long uk;

    @JSONField(name = "follow_count")
    private int followCount;

    @JSONField(name = "fans_count")
    private int fansCount;

    @JSONField(name = "pubshare_count")
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
}
