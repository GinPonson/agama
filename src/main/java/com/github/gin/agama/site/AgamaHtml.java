package com.github.gin.agama.site;


import com.github.gin.agama.annotation.ChildItem;
import com.github.gin.agama.annotation.Xpath;
import com.github.gin.agama.exception.AgamaException;
import com.github.gin.agama.serekuta.JsoupSerekuta;
import com.github.gin.agama.serekuta.Serekuta;
import com.github.gin.agama.serekuta.XpathSerekuta;
import com.github.gin.agama.site.converter.TypeConverter;
import com.github.gin.agama.util.ReflectUtils;
import com.github.gin.agama.util.XpathUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AgamaHtml {

    private Logger logger = LoggerFactory.getLogger(AgamaHtml.class);
	
    private static final HtmlCleaner HTML_CLEANER = new HtmlCleaner();
    
    private Document document;
    
    private TagNode pageTagNode;
    
    public AgamaHtml(Document doc) {
        this.document = doc;
        //格式化html以便xpath操作,因为有些标签是不对称的
    	pageTagNode = HTML_CLEANER.clean(this.toString());
    }
    
    public Serekuta links(){
        return xpath("//a/@href");
    }
    
    public Serekuta imgs(){
        return xpath("//img/@src");
    }
    
    public JsoupSerekuta select(String selector){
    	Elements elements = document.select(selector);
    	return new JsoupSerekuta(elements,document.baseUri());
    }
    
    public XpathSerekuta xpath(String xpath){
    	TagNodes tagNodes = XpathUtils.evaluate(pageTagNode, xpath);
    	return new XpathSerekuta(tagNodes,document.baseUri());
    }
    
    public <T>T toEntity(Class<T> target){
        TagNode tagNode = pageTagNode;
        if(target.isAnnotationPresent(Xpath.class)){
            String xpath = target.getAnnotation(Xpath.class).value();
            TagNodes tagNodes = XpathUtils.evaluate(pageTagNode, xpath);
            if(!tagNodes.isEmpty())
                tagNode = tagNodes.get(0);
        }

        return toEntity(target,tagNode);
    }

    private <T>T toEntity(Class<T> target,TagNode tagNode){
        T instance = ReflectUtils.newInstance(target);

        for(Field field : target.getDeclaredFields()){
            //字段有xpath注解时，需要解析html并保存到字段中
            if(field.isAnnotationPresent(Xpath.class)){
                //1、根据xpath解析html
                String xpath = field.getAnnotation(Xpath.class).value();
                TagNodes nodes = XpathUtils.evaluate(tagNode,xpath);

                //2、将解析的数据注入字段中
                if(!nodes.isEmpty()){
                    //有集合需要解析
                    if(ReflectUtils.getValue(field.getName(),instance) instanceof Collection){
                        if(!field.isAnnotationPresent(ChildItem.class))
                            throw new AgamaException("请在集合字段上添加ChildItem注解");

                        Class childitem = field.getAnnotation(ChildItem.class).value();
                        Collection childitems = toEntityList(childitem,nodes);

                        ReflectUtils.setValue(field.getName(), instance, childitems);
                    } else {
                        String dataText = "";
                        //不需要解析集合的情况
                        if(field.getAnnotation(Xpath.class).content().equals("html"))
                            dataText = XpathUtils.getHtmlText(nodes.get(0)).toString().trim();
                        else
                            dataText = nodes.get(0).getText().toString().trim();

                        Object data = TypeConverter.convert(dataText, field.getType());
                        ReflectUtils.setValue(field.getName(), instance, data);

                    }
                }
            }
        }

        return instance;
    }

    public <T>List<T> toEntityList(Class<T> target){
        if(!target.isAnnotationPresent(Xpath.class))
            throw new AgamaException("请给类添加注解");

        String xpath = target.getAnnotation(Xpath.class).value();
        TagNodes tagNodes = XpathUtils.evaluate(pageTagNode, xpath);

        return toEntityList(target, tagNodes);
    }

	private <T>List<T> toEntityList(Class<T> target,TagNodes tagNodes){
		List<T> resList = new ArrayList<>();

		for(TagNode tagNode : tagNodes){
			T instance = toEntity(target, tagNode);
			resList.add(instance);
		}

		return resList;
	}
    
    public Document getDocument(){
    	return document;
    }
    
    public String toString(){
    	return document.html();
    }
}
