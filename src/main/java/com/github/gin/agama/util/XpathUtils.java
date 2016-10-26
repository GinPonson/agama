package com.github.gin.agama.util;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import com.github.gin.agama.site.TagNodes;
import com.github.gin.agama.site.TextNode;
import org.htmlcleaner.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Map;

public class XpathUtils {

    public static Document toW3cDom(TagNode tagNode){
        Document document = null;
        try {
            document = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }

	public static TagNodes evaluate(TagNode tagNode,String xpath){
		TagNodes tagNodes = null;
		try {
			Object[] res = tagNode.evaluateXPath(xpath);
			tagNodes = new TagNodes(toTagNdes(res));
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
				TagNode node = new TextNode(arg);
				tagNodes[i] = node;
			}
		}
		return tagNodes;
	}

    public static CharSequence getHtmlText(TagNode tagNode) {
        StringBuilder text = new StringBuilder();
        for (Object item :tagNode.getAllChildren()) {
            if (item instanceof ContentNode) {
                text.append(((ContentNode) item).getContent());
            } else if (item instanceof TagNode) {
                String tag = ((TagNode) item).getName();
                String perfixTag = tag;
                for(Map.Entry<String,String> entry : ((TagNode) item).getAttributes().entrySet() ){
                    perfixTag += " "+entry.getKey()+"='"+entry.getValue()+"'";
                }
                CharSequence subtext = getHtmlText(((TagNode) item));
                text.append("<"+perfixTag+">"+subtext+"</"+tag+">");
            }
        }

        return text;
    }
}
