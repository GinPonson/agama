package com.github.gin.agama.site.render;

import com.github.gin.agama.entity.AgamaEntity;
import com.github.gin.agama.site.Page;

/**
 * Created by FSTMP on 2016/10/27.
 */
public interface Render {

    AgamaEntity inject(Page page, Class<? extends AgamaEntity> clazz);

}
