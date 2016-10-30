package cloud;

/**
 * Created by GinPonson on 10/30/2016.
 */
public class Singleton {

    private static YunDataDao yunDataDao = new YunDataDao();

    private static YunUserDao yunUserDao = new YunUserDao();

    private static YunDataService yunDataService = new YunDataService();

    private static YunUserService yunUserService = new YunUserService();

    public static YunDataDao getYunDataDao(){
        return yunDataDao;
    }

    public static YunUserDao getYunUserDao(){
        return yunUserDao;
    }

    public static YunDataService getYunDataService(){
        return yunDataService;
    }

    public static YunUserService getYunUserService(){
        return yunUserService;
    }
}
