package com.github.gin.agama.sorter;

import com.github.gin.agama.entity.HtmlEntity;

import java.util.Collection;

public interface DataStorer {

	void store(Collection<HtmlEntity> records);

}
