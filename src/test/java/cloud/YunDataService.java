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
            if(get(data.getShareId()) == null){
                Singleton.getYunDataDao().insert(data);
            }
            DbUtils.commit();
        } catch (SQLException e) {
            DbUtils.rollback();
            e.printStackTrace();
        }
    }

    public YunData get(long shareid){
        YunData yunData = null;
        try {
            yunData = Singleton.getYunDataDao().selectByShareid(shareid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return yunData;
    }
}
