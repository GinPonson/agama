package com.github.gin.agama.pipeline;

import com.github.gin.agama.entity.AgamaEntity;

import java.util.Collection;

public class ConsolePipeline implements Pipeline<AgamaEntity> {

    @Override
    public void process(Collection<AgamaEntity> items) {
        for(AgamaEntity item : items){
            System.out.println(item.toString());
        }
    }
}
