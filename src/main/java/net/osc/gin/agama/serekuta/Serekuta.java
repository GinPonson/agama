package net.osc.gin.agama.serekuta;

import java.util.List;

public interface Serekuta {

	List<String> list();
	
	List<String> list(String regex);
	
	String text();
	
	String attr(String attr);
	
	Serekuta first();
	
	Serekuta last();
	
}
