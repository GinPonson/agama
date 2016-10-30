package cloud;

import cloud.entity.YunUser;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import other.DbUtils;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by GinPonson on 10/30/2016.
 */
public class YunUserDao {

    public void insert(YunUser user) throws SQLException {
        QueryRunner qr = new QueryRunner(DbUtils.getDataSource());
        Object[] params = new Object[]{user.getUsername(),user.getUk(),new Date(),user.getAvatarUrl(),1,0};
        qr.update("insert into yun_user(username,uk,update_time,picture,version,flag) values(?,?,?,?,?,?)", params);
    }

    public YunUser selectByUk(long uk) throws SQLException {
        QueryRunner qr = new QueryRunner(DbUtils.getDataSource());
        YunUser user = (YunUser) qr.query("select * from yun_user where uk=?", new BeanHandler(YunUser.class), uk);
        return user;
    }
}
