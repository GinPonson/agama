package cloud;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.gin.agama.annotation.Json;
import com.github.gin.agama.site.bean.AgamaEntity;

import java.util.Date;

/**
 * Created by GinPonson on 10/28/2016.
 */
@Json("feedata.records")
public class YunData extends AgamaEntity{

    private int id;

    @JSONField(name = "shareid")
    private long shareId;

    @JSONField(name = "data_id")
    private long dataId;

    @JSONField(name = "title")
    private String shareName;

    @JSONField(name = "uk")
    private long uk;

    @JSONField(name = "desc")
    private String description;

    private Date updateTime;

    @JSONField(name = "feed_time")
    private Date shareTime;

    @JSONField(name = "avatar_url")
    private String picture;

    private int version;

    private boolean flag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getShareId() {
        return shareId;
    }

    public void setShareId(long shareId) {
        this.shareId = shareId;
    }

    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public long getUk() {
        return uk;
    }

    public void setUk(long uk) {
        this.uk = uk;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
