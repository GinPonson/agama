package cloud.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.gin.agama.annotation.Ason;
import com.github.gin.agama.entity.AgamaEntity;

import java.util.Date;

/**
 * Created by GinPonson on 10/30/2016.
 */
@Ason("list")
public class YunUser extends AgamaEntity{

    public long id;

    @JSONField(name = "avatar_url")
    private String avatarUrl;

    @JSONField(name = "follow_time")
    private Date followTime;

    @JSONField(name = "uname")
    private String username;

    @JSONField(name = "uk")
    private long uk;

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
}
