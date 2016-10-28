package cloud;

import com.github.gin.agama.sorter.DataStorer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import other.DbUtils;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public class FollowDataStorer implements DataStorer<Follow>{

    @Override
    public void store(Follow item) {
        try {
            QueryRunner qr = new QueryRunner(DbUtils.getDataSource());
            DbUtils.startTransaction();
            Follow follow = (Follow) qr.query("select * from yun_user where uk=?", new BeanHandler(Follow.class), item.getFollowUk());
            if(follow == null){
                Object[] params = new Object[]{item.getFollowUname(),item.getFollowUk(),new Date(),item.getAvatarUrl(),1,0};
                qr.update("insert into yun_user(username,uk,update_time,picture,version,flag) values(?,?,?,?,?,?)", params);
            }
            DbUtils.commit();
        } catch (SQLException e) {
            DbUtils.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void store(Collection<Follow> items) {
        for(Follow htmlEntity : items){
            store(htmlEntity);
        }
    }
}
