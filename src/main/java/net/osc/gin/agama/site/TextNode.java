package net.osc.gin.agama.site;

import org.htmlcleaner.TagNode;

public class TextNode extends TagNode{

    private Object text;

    public TextNode(String name) {
        super(name);
    }

    public TextNode(Object text){
        super(text.toString());
        this.text = text;
    }

    @Override
    public CharSequence getText() {
        return text.toString();
    }
}
