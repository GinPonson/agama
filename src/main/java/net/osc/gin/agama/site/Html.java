package net.osc.gin.agama.site;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import net.osc.gin.agama.annotation.Xpath;
import net.osc.gin.agama.serekuta.JsoupSerekuta;
import net.osc.gin.agama.serekuta.Serekuta;
import net.osc.gin.agama.serekuta.XpathSerekuta;
import net.osc.gin.agama.site.converter.TypeConverter;
import net.osc.gin.agama.util.XpathUtils;
import net.osc.gin.agama.util.ReflectUtils;

public class Html {
	
    private static final HtmlCleaner HTML_CLEANER = new HtmlCleaner();
    
    private Document document;
    
    private TagNode pageTagNode;
    
    public Html(Document doc) {
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
    
    /**
     * 转换相应对象
     * @param target
     * @return List<T>
     */
    public <T>List<T> toEntityList(Class<T> target){
    	String ancestor = "";
    	if(target.isAnnotationPresent(Xpath.class)){
    		ancestor = target.getAnnotation(Xpath.class).value();
    	}

    	List<T> resList = new ArrayList<>();
    	
    	TagNodes tagNodes = XpathUtils.evaluate(pageTagNode,"".equals(ancestor)?"//html":ancestor);
    	for(TagNode tagNode : tagNodes){
    		T instance = ReflectUtils.newInstance(target);
    		
    		for(Field field : target.getDeclaredFields()){
    			
    			if(field.isAnnotationPresent(Xpath.class)){
    				String xpath = field.getAnnotation(Xpath.class).value();
    				TagNodes nodes = XpathUtils.evaluate(tagNode,xpath);
    				
    				if(nodes.size() > 0){
    					String dataText = nodes.get(0).getText().toString().trim();
    					Object data = TypeConverter.convert(dataText, field.getType());
    					ReflectUtils.setValue(field.getName(), instance, data);
    				}
    				
    			}
    		}
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
