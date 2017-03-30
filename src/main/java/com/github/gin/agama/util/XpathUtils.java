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


/**
 * @author  GinPonson
 */
public class XpathUtils {

    public static Document toW3cDom(TagNode tagNode) {
        Document document = null;
        try {
            document = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static TagNodes evaluate(TagNode tagNode, String xpath) {
        TagNodes tagNodes = null;
        try {
            Object[] res = tagNode.evaluateXPath(xpath);
            tagNodes = new TagNodes(toTagNdes(res));
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return tagNodes;
    }

    private static TagNode[] toTagNdes(Object[] args) {
        TagNode[] tagNodes = new TagNode[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof TagNode) {
                tagNodes[i] = (TagNode) arg;
            } else {
                TagNode node = new TextNode(arg);
                tagNodes[i] = node;
            }
        }
        return tagNodes;
    }

    /**
     * 获取TagNode补全html标签
     *
     * @param tagNode
     * @return
     */
    public static CharSequence getHtmlText(TagNode tagNode) {
        StringBuilder text = new StringBuilder();
        for (Object item : tagNode.getAllChildren()) {
            if (item instanceof ContentNode) {
                text.append(((ContentNode) item).getContent());
            } else if (item instanceof TagNode) {
                String tag = ((TagNode) item).getName();
                StringBuilder perfixTag = new StringBuilder(tag);
                for (Map.Entry<String, String> entry : ((TagNode) item).getAttributes().entrySet()) {
                    perfixTag.append(" ").append(entry.getKey()).append("='").append(entry.getValue()).append("'");
                }
                CharSequence subtext = getHtmlText(((TagNode) item));
                text.append("<").append(perfixTag).append(">")
                        .append(subtext)
                        .append("</").append(tag).append(">");
            }
        }

        return text;
    }
}
