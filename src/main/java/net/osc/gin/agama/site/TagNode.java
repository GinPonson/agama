package net.osc.gin.agama.site;

public class TagNode extends org.htmlcleaner.TagNode{
	private Object text;
	
	public TagNode(String name) {
		super(name);
	}

	public TagNode(Object text){
		super(text.toString());
		this.text = text;
	}
	
	@Override
	public CharSequence getText() {
        return text.toString();
    }
}
