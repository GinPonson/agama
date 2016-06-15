package net.osc.gin.agama.sorter;

import java.util.Map;

public class ConsoleDataStorer implements DataStorer{

	@Override
	public void process(Map<String, String> fields) {
		for(Map.Entry<String, String> field: fields.entrySet()){
			System.out.println(field.getKey() + ":" + field.getValue());
		}
	}

}
