package net.osc.gin.agama.site;

import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.Arrays;

public class TagNodes extends ArrayList<TagNode>{

    public TagNodes(){}

    public TagNodes(TagNode... tagNodes) {
        super(Arrays.asList(tagNodes));
    }




}
