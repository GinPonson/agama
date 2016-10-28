package com.github.gin.agama.sorter;

import com.github.gin.agama.entity.AgamaEntity;

import java.util.Collection;

public class ConsoleDataStorer implements DataStorer<AgamaEntity>{

    @Override
    public void store(AgamaEntity item) {
        System.out.println(item.toString());
    }

    @Override
    public void store(Collection<AgamaEntity> items) {
        for(AgamaEntity htmlEntity : items){
            store(htmlEntity);
        }
    }
}
