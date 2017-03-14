package cnblog;

import com.github.gin.agama.core.ContextHolder;
import com.github.gin.agama.pipeline.Pipeline;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.site.bean.AgamaEntity;

/**
 * Created by GinPonson on 3/14/2017.
 */
public class AttackPiline implements Pipeline {
    @Override
    public void process(AgamaEntity entity) {
        if(ContextHolder.getContext().getScheduler().left() < 100){
            for (int i = 0; i < 10; i++) {
                ContextHolder.getContext().getScheduler().push(new Request("http://www.yibaogao.com/"));
            }
        }
    }
}
