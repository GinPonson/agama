package com.github.gin.agama.site;

import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.Arrays;

public class TagNodes extends ArrayList<TagNode>{

    public TagNodes(){}

    public TagNodes(TagNode... tagNodes) {
        super(Arrays.asList(tagNodes));
    }

    public String getFirstNodeText(){
        return this.isEmpty() ? "" : this.get(0).getText().toString();
    }

}
