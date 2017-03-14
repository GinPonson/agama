package com.github.gin.agama.pipeline;

import com.github.gin.agama.site.bean.AgamaEntity;

public interface Pipeline<T extends AgamaEntity> {

	void process(T entity);

}
