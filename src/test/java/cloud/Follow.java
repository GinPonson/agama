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

    public int id;

    @JSONField(name = "avatar_url")
    public String avatarUrl;

    @JSONField(name = "follow_time")
    private Date followTime;

    @JSONField(name = "follow_uname")
    private String followUname;

    @JSONField(name = "follow_uk")
    private String followUk;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getFollowUk() {
        return followUk;
    }

    public void setFollowUk(String followUk) {
        this.followUk = followUk;
    }
}
