package net.osc.gin.agama.serekuta;

import java.util.ArrayList;
import java.util.List;

import net.osc.gin.agama.site.TagNodes;
import net.osc.gin.agama.util.XpathUtils;
import org.htmlcleaner.TagNode;

import net.osc.gin.agama.util.UrlUtils;

public class XpathSerekuta implements Serekuta{
	
	//private TagNode[] tagNodes;

    private TagNodes tagNodes;
	
	private String domain;
	
	public XpathSerekuta(TagNode[] tagNodes){
		tagNodes = new TagNodes(tagNodes);
	}
	
	public XpathSerekuta(TagNode[] tagNodes, String baseUri) {
		this.tagNodes = tagNodes;
		domain = baseUri;
	}

    @Override
    public XpathSerekuta find(String nodeExp) {
        TagNode[] tns =
        for(TagNode node : tagNodes){
            TagNode[] nodes = XpathUtils.evaluate(node,nodeExp);
        }
        return null;
    }

    @Override
    public String text() {
        return tagNodes[0].getText().toString();
    }

    @Override
    public List<String> texts() {
        List<String> list = new ArrayList<>();
        for(TagNode tagNode : tagNodes){
            list.add(UrlUtils.toAsbLink(domain,tagNode.getText().toString()));
        }
        return list;
    }

    @Override
    public String attr(String attr) {
        return tagNodes[0].getAttributeByName(attr);
    }

    @Override
    public List<String> attrs(String attr) {
        return tagNodes[0].getAttributeByName(attr);
    }

    @Override
    public Serekuta first() {
        return new XpathSerekuta(new TagNode[]{tagNodes[0]});
    }

    @Override
    public Serekuta last() {
        return new XpathSerekuta(new TagNode[]{tagNodes[tagNodes.length-1]});
    }
}
