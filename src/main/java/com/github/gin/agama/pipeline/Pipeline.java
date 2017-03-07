package com.github.gin.agama.pipeline;

import com.github.gin.agama.entity.AgamaEntity;

import java.util.Collection;

public interface Pipeline<T extends AgamaEntity> {

	void process(T entity);

}
