package com.github.gin.agama.site.serekuta;

import java.util.List;

public interface Serekuta {

    Serekuta find(String nodeExp);

	String text();

    List<String> texts();
	
	String attr(String attr);

    List<String> attrs(String attr);
	
	Serekuta first();
	
	Serekuta last();

    Serekuta parent();
	
}
