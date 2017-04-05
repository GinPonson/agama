package com.github.gin.agama.pipeline;

import com.alibaba.fastjson.JSON;
import com.github.gin.agama.site.entity.AgamaEntity;

import java.util.List;

/**
 * @author  GinPonson
 */
public class ConsolePipeline implements Pipeline<AgamaEntity> {

    @Override
    public void process(List<AgamaEntity> entityList) {
        for(AgamaEntity entity : entityList) {
            System.out.println(JSON.toJSONString(entity));
        }
    }
}
