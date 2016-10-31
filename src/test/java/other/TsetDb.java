package other;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GinPonson on 10/27/2016.
 */
public class TsetDb {

    public static void main(String[] args){
        Pattern p = Pattern.compile("query_uk=(\\d*)&");

        Matcher m = p.matcher("http://yun.baidu.com/pcloud/friend/getfollowlist?query_uk=2889076181&limit=20&start=0&bdstoken=e6f1efec456b92778e70c55ba5d81c3d&channel=chunlei&clienttype=0&web=1&logid=MTQ3NDA3NDg5NzU4NDAuMzQxNDQyMDY2MjA5NDA4NjU=");

        if(m.find())
            System.out.println(m.group(1));
    }
}
