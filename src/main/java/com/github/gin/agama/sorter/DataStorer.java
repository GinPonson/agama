package com.github.gin.agama.sorter;

import com.github.gin.agama.entity.AgamaEntity;

import java.util.Collection;

public interface DataStorer<T extends AgamaEntity> {

	void store(Collection<T> items);

}
