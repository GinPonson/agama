package com.github.gin.agama.pipeline;

import com.github.gin.agama.site.entity.AgamaEntity;

import java.util.List;

/**
 * @author  GinPonson
 */
public interface Pipeline<T extends AgamaEntity> {

	void process(List<T> entityList);

}
