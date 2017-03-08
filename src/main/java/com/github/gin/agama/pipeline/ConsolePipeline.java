package com.github.gin.agama.pipeline;

import com.alibaba.fastjson.JSON;
import com.github.gin.agama.entity.AgamaEntity;

import java.util.Collection;

public class ConsolePipeline implements Pipeline<AgamaEntity> {

    @Override
    public void process(AgamaEntity entity) {
        System.out.println(JSON.toJSONString(entity));
    }
}
