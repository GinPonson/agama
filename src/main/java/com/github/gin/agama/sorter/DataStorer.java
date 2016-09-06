package com.github.gin.agama.sorter;

import java.util.Collection;

public interface DataStorer<HtmlEntity> {

	void store(Collection<HtmlEntity> records);

}
