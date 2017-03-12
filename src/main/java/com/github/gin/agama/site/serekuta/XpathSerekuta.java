package com.github.gin.agama.site.serekuta;

import java.util.ArrayList;
import java.util.List;

import com.github.gin.agama.site.TagNodes;
import com.github.gin.agama.util.UrlUtils;
import com.github.gin.agama.util.XpathUtils;
import com.github.gin.agama.site.TextNode;
import org.htmlcleaner.TagNode;

public class XpathSerekuta {
	
    private TagNodes tagNodes;
	
	private String domain;
	
	public XpathSerekuta(TagNodes tagNodes, String baseUri) {
		this.tagNodes = tagNodes;
		this.domain = baseUri;
	}

    public XpathSerekuta find(String nodeExp) {
        TagNodes nodes = new TagNodes();
        for(TagNode tagNode : tagNodes){
            if(!(tagNode instanceof TextNode)){
                nodes.addAll(XpathUtils.evaluate(tagNode, nodeExp));
            }
        }
        return new XpathSerekuta(nodes,domain);
    }

    public String text() {
        return tagNodes.text();
    }

    public List<String> texts() {
        List<String> list = new ArrayList<>();
        for(TagNode tagNode : tagNodes){
            list.add(tagNode.getText().toString());
        }
        return list;
    }

    public static String html(TagNode tagNode){
	    return XpathUtils.getHtmlText(tagNode).toString();
    }

    public static List<String> htmls(TagNodes tagNodes){
        List<String> list = new ArrayList<>();
        for(TagNode tagNode : tagNodes){
            list.add(XpathUtils.getHtmlText(tagNode).toString());
        }
        return list;
    }

    public String attr(String attr) {
        if("href".equals(attr) || "src".equals(attr))
            return UrlUtils.toAsbLink(domain, tagNodes.attr(attr));
        else
            return tagNodes.attr(attr);
    }

    public List<String> attrs(String attr) {
        List<String> attrs = new ArrayList<>();
        for(TagNode tagNode : tagNodes){
            String attribute = "";
            if(tagNode instanceof TextNode){
                attribute = tagNode.getText().toString() ;
            } else {
                attribute = tagNode.getAttributeByName(attr);
            }
            if("href".equals(attr) || "src".equals(attr))
                attrs.add(UrlUtils.toAsbLink(domain,attribute));
        }
        return attrs;
    }

    public XpathSerekuta first() {
        TagNodes nodes = new TagNodes();
        nodes.add(tagNodes.get(0));
        return new XpathSerekuta(nodes,domain);
    }

    public XpathSerekuta last() {
        TagNodes nodes = new TagNodes();
        nodes.add(tagNodes.get(tagNodes.size()-1));
        return new XpathSerekuta(nodes,domain);
    }

    public XpathSerekuta parent(){
        TagNodes nodes = new TagNodes();
        for(TagNode tagNode : tagNodes){
            nodes.add(tagNode.getParent());
        }
        return new XpathSerekuta(nodes,domain);
    }
}
