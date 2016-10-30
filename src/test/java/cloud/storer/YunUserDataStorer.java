package cloud.storer;

import cloud.Singleton;
import cloud.entity.YunUser;
import com.github.gin.agama.sorter.DataStorer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import other.DbUtils;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public class YunUserDataStorer implements DataStorer<YunUser>{

    @Override
    public void store(Collection<YunUser> items) {
        for(YunUser item : items){
            Singleton.getYunUserService().save(item);
        }
    }
}
