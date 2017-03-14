package com.github.gin.agama.site;

import com.github.gin.agama.site.bean.AgamaEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FSTMP on 2016/10/28.
 */
public class ResultItems {

    private List<AgamaEntity> items = new ArrayList<>();

    public void add(AgamaEntity htmlEntity){
        items.add(htmlEntity);
    }

    public void add(List<? extends AgamaEntity> htmlEntity){
        items.addAll(htmlEntity);
    }

    public List<AgamaEntity> getItems(){
        return items;
    }
}
