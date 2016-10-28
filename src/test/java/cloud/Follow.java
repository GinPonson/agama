package cloud;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.gin.agama.annotation.Ason;
import com.github.gin.agama.entity.AgamaEntity;

import java.util.Date;

/**
 * Created by FSTMP on 2016/10/27.
 */
@Ason("follow_list")
public class Follow extends AgamaEntity{

    public long id;

    @JSONField(name = "avatar_url")
    public String avatarUrl;

    @JSONField(name = "follow_time")
    private Date followTime;

    @JSONField(name = "follow_uname")
    private String followUname;

    @JSONField(name = "follow_uk")
    private long followUk;

    private Date updateTime;

    private boolean flag;

    private Integer version;

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

    public String getFollowUname() {
        return followUname;
    }

    public void setFollowUname(String followUname) {
        this.followUname = followUname;
    }

    public long getFollowUk() {
        return followUk;
    }

    public void setFollowUk(long followUk) {
        this.followUk = followUk;
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
}
