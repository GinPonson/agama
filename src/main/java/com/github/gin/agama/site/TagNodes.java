package com.github.gin.agama.site;

import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.Arrays;

public class TagNodes extends ArrayList<TagNode>{

    public TagNodes(){}

    public TagNodes(TagNode... tagNodes) {
        super(Arrays.asList(tagNodes));
    }

    public String text(){
        String text = "";
        for(TagNode tagNode : this){
            text += tagNode.getText().toString();
        }
        return text;
    }

    public String attr(String attr) {
        String attrs = "";
        for(TagNode tagNode : this){
            if(tagNode instanceof TextNode){
                attrs += tagNode.getText().toString();
            } else {
                attrs += tagNode.getAttributeByName(attr);
            }
        }
        return attrs;
    }

    public String getFirstNodeText(){
        return this.isEmpty() ? "" : this.get(0).getText().toString();
    }

}
