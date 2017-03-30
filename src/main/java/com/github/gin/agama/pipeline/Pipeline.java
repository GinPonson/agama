package com.github.gin.agama.pipeline;

import com.github.gin.agama.site.entity.AgamaEntity;

/**
 * @author  GinPonson
 */
public interface Pipeline<T extends AgamaEntity> {

	void process(T entity);

}
