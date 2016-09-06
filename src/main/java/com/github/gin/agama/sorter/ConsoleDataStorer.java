package com.github.gin.agama.sorter;

import com.github.gin.agama.entity.HtmlEntity;

import java.util.Collection;

public class ConsoleDataStorer implements DataStorer<HtmlEntity >{

    @Override
    public void store(Collection<HtmlEntity> records) {
        for(HtmlEntity t : records){
            System.out.println(t.toString());
        }
    }
}
