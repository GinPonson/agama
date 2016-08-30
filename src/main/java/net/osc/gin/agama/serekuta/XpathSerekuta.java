package net.osc.gin.agama.serekuta;

import java.util.ArrayList;
import java.util.List;

import net.osc.gin.agama.site.TagNodes;
import net.osc.gin.agama.site.TextNode;
import net.osc.gin.agama.util.XpathUtils;
import org.htmlcleaner.TagNode;

import net.osc.gin.agama.util.UrlUtils;

public class XpathSerekuta implements Serekuta{
	
    private TagNodes tagNodes;
	
	private String domain;
	
	public XpathSerekuta(TagNodes tagNodes, String baseUri) {
		this.tagNodes = tagNodes;
		this.domain = baseUri;
	}

    @Override
    public XpathSerekuta find(String nodeExp) {
        TagNodes nodes = new TagNodes();
        for(TagNode tagNode : tagNodes){
            if(!(tagNode instanceof TextNode)){
                nodes.addAll(XpathUtils.evaluate(tagNode, nodeExp));
            }
        }
        return new XpathSerekuta(nodes,domain);
    }

    @Override
    public String text() {
        return tagNodes.text();
    }

    @Override
    public List<String> texts() {
        List<String> list = new ArrayList<>();
        for(TagNode tagNode : tagNodes){
            list.add(tagNode.getText().toString());
        }
        return list;
    }

    @Override
    public String attr(String attr) {
        if("href".equals(attr) || "src".equals(attr))
            return UrlUtils.toAsbLink(domain,tagNodes.attr(attr));
        else
            return tagNodes.attr(attr);
    }

    @Override
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

    @Override
    public XpathSerekuta first() {
        TagNodes nodes = new TagNodes();
        nodes.add(tagNodes.get(0));
        return new XpathSerekuta(nodes,domain);
    }

    @Override
    public XpathSerekuta last() {
        TagNodes nodes = new TagNodes();
        nodes.add(tagNodes.get(tagNodes.size()-1));
        return new XpathSerekuta(nodes,domain);
    }

    @Override
    public XpathSerekuta parent(){
        TagNodes nodes = new TagNodes();
        for(TagNode tagNode : tagNodes){
            nodes.add(tagNode.getParent());
        }
        return new XpathSerekuta(nodes,domain);
    }
}
