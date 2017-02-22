package weather;

import com.github.gin.agama.annotation.Xpath;
import com.github.gin.agama.entity.AgamaEntity;

import java.util.Date;

/**
 * Created by FSTMP on 2017/2/22.
 */
@Xpath("//li[@itemprop='owns']")
public class GitHub extends AgamaEntity {

    @Xpath("//a[@itemprop='name codeRepository']")
    private String project;

    @Xpath("//p[@itemprop='description']")
    private String description;

    @Xpath("//span[@itemprop='programmingLanguage']")
    private String language;

    @Xpath("//a[@aria-label='Stargazers']")
    private Integer star;

    @Xpath("//a[@aria-label='Forks']")
    private Integer fork;

    @Xpath("//relative-time")
    private Date relativeTime;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getFork() {
        return fork;
    }

    public void setFork(Integer fork) {
        this.fork = fork;
    }

    public Date getRelativeTime() {
        return relativeTime;
    }

    public void setRelativeTime(Date relativeTime) {
        this.relativeTime = relativeTime;
    }
}
