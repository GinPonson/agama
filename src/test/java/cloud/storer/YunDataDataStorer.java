package cloud.storer;

import cloud.Singleton;
import cloud.entity.YunData;
import cloud.entity.YunUser;
import com.github.gin.agama.sorter.DataStorer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import other.DbUtils;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public class YunDataDataStorer implements DataStorer<YunData>{

    @Override
    public void store(Collection<YunData> items) {
        for(YunData item : items){
            Singleton.getYunDataService().save(item);
        }
    }
}
