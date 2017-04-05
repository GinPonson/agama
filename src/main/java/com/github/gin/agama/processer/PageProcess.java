package com.github.gin.agama.processer;

import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.entity.AgamaEntity;

import java.util.List;

/**
 * @author GinPonson
 */
public interface PageProcess<T extends AgamaEntity> {

    void process(Page page, List<T> entityList);

}
