package net.osc.gin.agama.serekuta;

import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.TagNode;

import net.osc.gin.agama.util.UrlUtils;

public class XpathSerekuta implements Serekuta{
	
	private TagNode[] tagNodes;
	
	private String domain;
	
	public XpathSerekuta(TagNode[] tagNodes){
		this.tagNodes = tagNodes;
	}
	
	public XpathSerekuta(TagNode[] tagNodes, String baseUri) {
		this.tagNodes = tagNodes;
		domain = baseUri;
	}

	@Override
	public List<String> list() {
		List<String> list = new ArrayList<>();
		for(TagNode tagNode : tagNodes){
			list.add(UrlUtils.toAsbLink(domain,tagNode.getText().toString()));
		}
		return list;
	}
	
	/*@Override
	public List<String> list(String regex) {
		List<String> list = new ArrayList<>();
		for(TagNode tagNode : tagNodes){
			if(tagNode.getText().toString().matches(regex)){
				list.add(UrlUtils.toAsbLink(domain,tagNode.getText().toString()));
			}
		}
		return list;
	}*/
	
	@Override
	public String attr(String attr) {
		return tagNodes[0].getAttributeByName(attr);
	}

	@Override
	public String text() {
		return tagNodes[0].getText().toString();
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
