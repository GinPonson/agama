package cloud;

import cloud.entity.YunUser;
import other.DbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GinPonson on 10/30/2016.
 */
public class YunUserService {

    public void updateFollowCrawled(long uk){
        try {
            Singleton.getYunUserDao().updateFollowCrawled(uk,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFansCrawled(long uk){
        try {
            Singleton.getYunUserDao().updateFansCrawled(uk, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateYunCrawled(long uk){
        try {
            Singleton.getYunUserDao().updateYunCrawled(uk,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(YunUser user){
        try {
            DbUtils.startTransaction();
            if(get(user.getUk()) == null){
                Singleton.getYunUserDao().insert(user);
            }
            DbUtils.commit();
        } catch (SQLException e) {
            DbUtils.rollback();
            e.printStackTrace();
        }
    }

    public void saveBatch(List<YunUser> yunUsers){
        for(YunUser yunUser : yunUsers){
            save(yunUser);
        }
    }

    public YunUser get(long uk){
        YunUser yunUser = null;
        try {
            yunUser =  Singleton.getYunUserDao().selectByUk(uk);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return yunUser;
    }

    public List<YunUser> findUnfinish(){
        List<YunUser> yunUserList = new ArrayList<>();
        try {
            yunUserList =  Singleton.getYunUserDao().fetchByFlag(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return yunUserList;
    }

    public List<YunUser> findFollowUnCrawled(){
        List<YunUser> yunUserList = new ArrayList<>();
        try {
            yunUserList =  Singleton.getYunUserDao().fetchByFollowCrawled(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return yunUserList;
    }

    public List<YunUser> findFansUnCrawled() {
        List<YunUser> yunUserList = new ArrayList<>();
        try {
            yunUserList =  Singleton.getYunUserDao().fetchByFansCrawled(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return yunUserList;
    }

}
