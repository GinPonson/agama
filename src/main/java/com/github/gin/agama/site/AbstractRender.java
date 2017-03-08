package com.github.gin.agama.site;

import com.github.gin.agama.entity.AgamaEntity;

/**
 * Created by FSTMP on 2016/10/27.
 */
public abstract class AbstractRender {

    public abstract AgamaEntity inject(Page page,Class<? extends AgamaEntity> clazz);

}
