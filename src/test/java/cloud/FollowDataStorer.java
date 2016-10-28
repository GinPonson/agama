package cloud;

import com.github.gin.agama.sorter.DataStorer;
import org.apache.commons.dbutils.QueryRunner;

import java.util.Collection;

public class FollowDataStorer implements DataStorer<Follow>{

    @Override
    public void store(Follow item) {
        QueryRunner qr = new QueryRunner();
        System.out.println(item.getAvatarUrl());
    }

    @Override
    public void store(Collection<Follow> items) {
        for(Follow htmlEntity : items){
            store(htmlEntity);
        }
    }
}
