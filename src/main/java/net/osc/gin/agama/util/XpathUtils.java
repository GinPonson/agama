package net.osc.gin.agama.util;

import javax.xml.parsers.ParserConfigurationException;

import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.w3c.dom.Document;

public class XpathUtils {

	public static Document toW3cDom(HtmlCleaner cleaner,String html){
		Document document = null;
		TagNode tagNode = cleaner.clean(html);
		try {
			document = new DomSerializer(cleaner.getProperties()).createDOM(tagNode);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return document;
	}
	
	public static TagNode[] evaluate(TagNode tagNode,String xpath){
		TagNode[] tagNodes = null;
		try {
			Object[] res = tagNode.evaluateXPath(xpath);
			tagNodes = toTagNdes(res);
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return tagNodes;
	}
	
	public static TagNode[] toTagNdes(Object[] args){
		TagNode[] tagNodes = new TagNode[args.length];
		for(int i=0; i<args.length; i++){
			Object arg = args[i];
			if(arg instanceof TagNode){
				tagNodes[i] = (TagNode) arg;
			} else {
				TagNode node = new net.osc.gin.agama.site.TagNode(arg);
				tagNodes[i] = node;
			}
		}
		return tagNodes;
	}
}
