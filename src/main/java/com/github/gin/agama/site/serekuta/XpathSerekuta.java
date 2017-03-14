package com.github.gin.agama.site.serekuta;

import com.github.gin.agama.site.TagNodes;
import com.github.gin.agama.site.TextNode;
import com.github.gin.agama.util.XpathUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.List;

public class XpathSerekuta implements Serekuta {

    private TagNodes tagNodes;

    public XpathSerekuta(String html) {
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        this.tagNodes = new TagNodes(htmlCleaner.clean(html));
    }

    public XpathSerekuta(TagNodes tagNodes) {
        this.tagNodes = tagNodes;
    }


    @Override
    public Serekuta select(String css) {
        TagNodes nodes = new TagNodes();
        for(TagNode tagNode : tagNodes){
            if(!(tagNode instanceof TextNode)){
                nodes.addAll(XpathUtils.evaluate(tagNode, css));
            }
        }
        return new XpathSerekuta(nodes);
    }

    @Override
    public Serekuta find(String nodeExp) {
        return select(nodeExp);
    }

    @Override
    public String text() {
        return tagNodes.getFirstNodeText();
    }

    @Override
    public String attr(String attr) {
        return tagNode instanceof TextNode ?
                tagNode.getText().toString() :
                tagNode.getAttributeByName(attr);
    }

    @Override
    public Serekuta first() {
        Serekuta serekuta = null;
        List<TagNode> childTagList = tagNode.getChildTagList();
        if(!childTagList.isEmpty()){
            serekuta = new XpathSerekuta(html(childTagList.get(0)));
        }

        return serekuta;
    }

    @Override
    public Serekuta last() {
        Serekuta serekuta = null;
        List<TagNode> childTagList = tagNode.getChildTagList();
        if(!childTagList.isEmpty()){
            serekuta = new XpathSerekuta(html(childTagList.get(childTagList.size()-1)));
        }
        return serekuta;
    }

    @Override
    public Serekuta parent() {
        Serekuta serekuta = null;
        TagNode parentTagNode = tagNode.getParent();
        if(parentTagNode != null){
            serekuta = new XpathSerekuta(html(tagNode.getParent()));
        }
        return serekuta;
    }

    public static String html(TagNode tagNode) {
        return XpathUtils.getHtmlText(tagNode).toString();
    }

    public static List<String> htmls(TagNodes tagNodes) {
        List<String> list = new ArrayList<>();
        for (TagNode tagNode : tagNodes) {
            list.add(html(tagNode));
        }
        return list;
    }
}
