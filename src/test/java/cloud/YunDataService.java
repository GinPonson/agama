package cloud;

import cloud.entity.YunData;
import cloud.entity.YunUser;
import other.DbUtils;

import java.sql.SQLException;

/**
 * Created by GinPonson on 10/30/2016.
 */
public class YunDataService {

    public void save(YunData data){
        try {
            DbUtils.startTransaction();
            if(Singleton.getYunDataDao().selectByUk(data.getUk()) == null){
                Singleton.getYunDataDao().insert(data);
            }
            DbUtils.commit();
        } catch (SQLException e) {
            DbUtils.rollback();
            e.printStackTrace();
        }
    }
}
