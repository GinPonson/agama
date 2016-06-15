package net.osc.gin.agama.serekuta;

import java.util.List;

public interface Serekuta {

	public List<String> list();
	
	public List<String> list(String regex);
	
	public String text();
	
	public String attr(String attr);
	
	public Serekuta first();
	
	public Serekuta last();
	
}
