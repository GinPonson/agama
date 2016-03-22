package org.pyj.vertical.JCrawler.site;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pyj.vertical.JCrawler.annotation.AncestorElement;
import org.pyj.vertical.JCrawler.util.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Html {
	
	private static Logger log = LoggerFactory.getLogger(Html.class);

    private Document document;
    
    private static final HtmlCleaner HTML_CLEANER = new HtmlCleaner(); 
    
    private TagNode pageTagNode;
    
    public Html(Document doc) {
        this.document = doc;
    	pageTagNode = HTML_CLEANER.clean(this.toString());
    }
    
    /**
     * 获取整个htlm的<a>标签的链接
     * @return List<String>
     */
    public List<String> absLinks(){
    	List<String> absLinks = new ArrayList<>();
    	Elements elements = document.select("a");
        for(Element element : elements){
        	absLinks.add(element.absUrl("href"));
        }
        return absLinks;
    }
    
    public List<String> xpath(String xpath){
    	List<String> res = new ArrayList<>();
    	try {
			Object[] elements = pageTagNode.evaluateXPath(xpath);
			for(Object element : elements){
				String text = "";
				if(element instanceof TagNode){
					StringBuffer sb = new StringBuffer(((TagNode)element).getText());
					text = sb.toString();
				} else {
					text = element.toString();
				}
 				
				res.add(text);
			}
		} catch (XPatherException e) {
			log.error("xpath parse error!!");
			e.printStackTrace();
		}
    	return res;
    }
    
    public List<String> select(String selector){
    	List<String> res = new ArrayList<>();
    	Elements elements = document.select(selector);
    	for(Element element :elements){
    		res.add(element.text());
    	}
    	return res;
    }
    
    /**
     * 获取指定selector的html代码
     * @param jquerySelector
     * @return String
     */
    public String targetHtml(String selector){
    	Elements elements = document.select(selector);
    	return elements.outerHtml();
    }
    
    /**
     * 转换相应对象
     * @param target
     * @return List<T>
     */
    public <T>List<T> toEntityList(Class<T> target){
    	String ancestorXpath = null;
    	if(target.isAnnotationPresent(AncestorElement.class)){
    		ancestorXpath = target.getAnnotation(AncestorElement.class).xpath();
    	}

    	List<T> resList = new ArrayList<>();
    	
    	try{
	    	HtmlCleaner cleaner = new HtmlCleaner();
	    	TagNode tagNode = cleaner.clean(this.toString());
	    	Object[] elements = tagNode.evaluateXPath(ancestorXpath==null?"//html":ancestorXpath);
	    	
	    	for(Object element : elements){
	    		Map<String,Object> fieldValue = new HashMap<>();
	    		for(Map.Entry<String, String> eleAnno : ReflectUtils.elementAnno(target).entrySet()){
	    			String fieldName = eleAnno.getKey();
	    			String xpath = eleAnno.getValue();
	    			if(element instanceof TagNode){
	    				Object[] obj = ((TagNode)element).evaluateXPath(xpath);	
	    				//当取属性的value时，Object不是TagNode，而是String
	    				String text ="";
	    				if(obj.length >0){
	    					if(obj[0] instanceof TagNode){
	    						text = new StringBuffer(((TagNode)obj[0]).getText()).toString();
	    					} else {
	    						text = obj[0].toString();
	    					}
	    				}
	    				
		    			Object value = ReflectUtils.convert(target,fieldName,text.trim());
		    			
		    			fieldValue.put(fieldName, value);
	    			}
	    			
	    		}
	    		T instance = ReflectUtils.createInstance(target,fieldValue);
	    		resList.add(instance);
	    	}
    	}catch(XPatherException e){
    		log.error("xpath error!");
    		e.printStackTrace();
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
