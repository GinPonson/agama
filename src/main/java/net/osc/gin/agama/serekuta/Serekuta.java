package net.osc.gin.agama.serekuta;

import java.util.List;

public interface Serekuta {

    //Serekuta find(String nodeExp);

	List<String> list();
	
	String text();
	
	String attr(String attr);
	
	Serekuta first();
	
	Serekuta last();
	
}
