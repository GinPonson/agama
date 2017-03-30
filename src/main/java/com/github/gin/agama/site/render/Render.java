package com.github.gin.agama.site.render;

import com.github.gin.agama.site.entity.AgamaEntity;
import com.github.gin.agama.site.Page;

import java.util.List;

/**
 * @author  GinPonson
 */
public interface Render {

    List<AgamaEntity> renderToList(Page page, Class<? extends AgamaEntity> clazz) ;

    AgamaEntity renderToBean(Page page, Class<? extends AgamaEntity> clazz);

}
