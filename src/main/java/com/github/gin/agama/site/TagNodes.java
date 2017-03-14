package com.github.gin.agama.site;

import com.github.gin.agama.site.serekuta.XpathSerekuta;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagNodes extends ArrayList<TagNode> {

    public TagNodes() {
    }

    public TagNodes(TagNode... tagNodes) {
        super(Arrays.asList(tagNodes));
    }

    public String text() {
        StringBuilder sb = new StringBuilder();
        for (TagNode tagNode : this) {
            sb.append(tagNode.getText() + " ");
        }
        return sb.toString().trim();
    }

    public String attr(String attr) {
        StringBuilder sb = new StringBuilder();
        for (TagNode tagNode : this) {
            if (tagNode instanceof TextNode) {
                sb.append(tagNode.getText() + " ");
            } else {
                sb.append(tagNode.getAttributeByName(attr) + " ");
            }
        }
        return sb.toString().trim();
    }

    public String getFirstNodeText() {
        return this.isEmpty() ? "" : this.get(0).getText().toString();
    }

    @Override
    public String toString() {
        List<String> list = XpathSerekuta.htmls(this);
        return Arrays.toString(XpathSerekuta.htmls(this).toArray());
    }

}
