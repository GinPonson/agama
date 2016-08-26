package net.osc.gin.agama.site;

import java.util.ArrayList;
import java.util.Arrays;

public class TagNodes extends ArrayList<org.htmlcleaner.TagNode>{

    public TagNodes(org.htmlcleaner.TagNode... tagNodes) {
        super(Arrays.asList(tagNodes));
    }



}
