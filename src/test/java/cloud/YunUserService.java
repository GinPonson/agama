package cloud;

import cloud.entity.YunUser;
import other.DbUtils;

import java.sql.SQLException;

/**
 * Created by GinPonson on 10/30/2016.
 */
public class YunUserService {

    public void save(YunUser user){
        try {
            DbUtils.startTransaction();
            if(Singleton.getYunUserDao().selectByUk(user.getUk()) == null){
                Singleton.getYunUserDao().insert(user);
            }
            DbUtils.commit();
        } catch (SQLException e) {
            DbUtils.rollback();
            e.printStackTrace();
        }
    }
}
