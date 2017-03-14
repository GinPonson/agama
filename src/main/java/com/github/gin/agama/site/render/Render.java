package com.github.gin.agama.site.render;

import com.github.gin.agama.site.bean.AgamaEntity;
import com.github.gin.agama.site.Page;

import java.util.List;

/**
 * Created by FSTMP on 2016/10/27.
 */
public interface Render {

    List<AgamaEntity> renderToList(Page page, Class<? extends AgamaEntity> clazz) ;

    AgamaEntity renderToBean(Page page, Class<? extends AgamaEntity> clazz);

}
