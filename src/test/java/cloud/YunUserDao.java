package cloud;

import cloud.entity.YunUser;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import other.DbUtils;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by GinPonson on 10/30/2016.
 */
public class YunUserDao {

    private static final String COLUMNS = "username,uk,update_time AS updateTime,picture AS avatarUrl,follow_count AS followCount,fans_count AS fansCount,pubshare_count AS pubshareCount,version,flag,fans_crawled AS fansCrawled,follow_crawled AS followCrawled";

    public void insert(YunUser user) throws SQLException {
        QueryRunner qr = new QueryRunner(DbUtils.getDataSource());
        Object[] params = new Object[]{user.getUsername(),user.getUk(),new Date(),user.getAvatarUrl(),user.getFollowCount(),user.getFansCount(),user.getPubshareCount(),user.getVersion(),user.isFlag(),user.isFansCrawled(),user.isFollowCrawled()};
        qr.update("insert into yun_user(username,uk,update_time,picture,follow_count,fans_count,pubshare_count,version,flag,fans_crawled,follow_crawled) values(?,?,?,?,?,?,?,?,?,?,?)", params);
    }

    public void update(YunUser user) throws SQLException {
        QueryRunner qr = new QueryRunner(DbUtils.getDataSource());
        Object[] params = new Object[]{user.getUsername(),user.getUk(),new Date(),user.getAvatarUrl(),user.getFollowCount(),user.getFansCount(),user.getPubshareCount(),user.getVersion(),user.isFlag(),user.isFansCrawled(),user.isFollowCrawled()};
        qr.update("update yun_user set username=?,uk=?,update_time=?,picture=?,follow_count=?,fans_count=?,pubshare_count=?,version=?,flag=?,fans_crawled=?,follow_crawled=?", params);
    }

    public YunUser selectByUk(long uk) throws SQLException {
        QueryRunner qr = new QueryRunner(DbUtils.getDataSource());
        YunUser user = (YunUser) qr.query("select "+ COLUMNS +" from yun_user where uk=?", new BeanHandler(YunUser.class), uk);
        return user;
    }

    public List<YunUser> fetchByFlag(boolean flag) throws SQLException {
        QueryRunner qr = new QueryRunner(DbUtils.getDataSource());
        List<YunUser> yunUsers = (List<YunUser>) qr.query("select "+ COLUMNS +" from yun_user where flag=? limit 0,1", new BeanListHandler(YunUser.class),flag);
        return yunUsers;
    }

    public List<YunUser> fetchByFollowCrawled(boolean followCrawled) throws SQLException {
        QueryRunner qr = new QueryRunner(DbUtils.getDataSource());
        List<YunUser> yunUsers = (List<YunUser>) qr.query("select "+ COLUMNS +" from yun_user where follow_crawled=? limit 0,1", new BeanListHandler(YunUser.class),followCrawled);
        return yunUsers;
    }

    public List<YunUser> fetchByFansCrawled(boolean fansCrawled) throws SQLException {
        QueryRunner qr = new QueryRunner(DbUtils.getDataSource());
        List<YunUser> yunUsers = (List<YunUser>) qr.query("select "+ COLUMNS +" from yun_user where fans_crawled=? limit 0,1", new BeanListHandler(YunUser.class),fansCrawled);
        return yunUsers;
    }

    public void updateFollowCrawled(long uk,boolean followCrawled) throws SQLException {
        QueryRunner qr = new QueryRunner(DbUtils.getDataSource());
        qr.update("update yun_user set follow_crawled=? where uk=?", followCrawled,uk);
    }

    public void updateFansCrawled(long uk,boolean fansCrawled) throws SQLException {
        QueryRunner qr = new QueryRunner(DbUtils.getDataSource());
        qr.update("update yun_user set fans_crawled=? where uk=?", fansCrawled,uk);
    }

    public void updateYunCrawled(long uk,boolean yunCrawled) throws SQLException {
        QueryRunner qr = new QueryRunner(DbUtils.getDataSource());
        qr.update("update yun_user set flag=? where uk=?", yunCrawled,uk);
    }
}
