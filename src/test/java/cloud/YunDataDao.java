package cloud;

import cloud.entity.YunData;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import other.DbUtils;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by GinPonson on 10/30/2016.
 */
public class YunDataDao {

    public void insert(YunData data) throws SQLException {
        QueryRunner qr = new QueryRunner(DbUtils.getDataSource());
        Object[] params = new Object[]{data.getShareId(),data.getDataId(),data.getShareName(),data.getUk(),data.getDescription(),new Date(),data.getShareTime(),data.getPicture(),1,0};
        qr.update("insert into yun_data(share_id,data_id,share_name,uk,description,update_time,share_time,picture,version,flag) values(?,?,?,?,?,?,?,?,?,?)", params);
    }

    public YunData selectByShareid(long shareid) throws SQLException {
        QueryRunner qr = new QueryRunner(DbUtils.getDataSource());
        YunData data = (YunData) qr.query("select * from yun_data where share_id=?", new BeanHandler(YunData.class), shareid);
        return data;
    }

}
