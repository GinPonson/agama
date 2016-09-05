package com.github.gin.agama.sorter;

import java.util.List;
import java.util.Map;

public interface DataStorer {

	void store(List<Map<String, String>> records);

   // void store(List<Object> records);
}
