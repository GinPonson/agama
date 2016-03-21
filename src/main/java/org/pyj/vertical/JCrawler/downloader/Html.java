package org.pyj.vertical.JCrawler.downloader;


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
    
    public Html(Document doc) {
        this.document = doc;
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
    
    /**
     * 获取指定selector下面的<a>标签的链接
     * @param jquerySelector 
     * @return List<String>
     */
    public List<String> absLinks(String jquerySelector){
    	List<String> absLinks = new ArrayList<>();
    	Elements elements = document.select(jquerySelector).select("a");
        for(Element element : elements){
        	absLinks.add(element.absUrl("href"));
        }
        return absLinks;
    }
    
    /**
     * 获取指定selector的html代码
     * @param jquerySelector
     * @return String
     */
    public String targetHtml(String jquerySelector){
    	Elements elements = document.select(jquerySelector);
    	return elements.outerHtml();
    }
    
    //public List>String getImgUrl(String )
    
    /**
     * 将指定selector的Element属性转换相应对象
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
	    	TagNode tagNode = cleaner.clean(document.html());
	    	Object[] elements = tagNode.evaluateXPath(ancestorXpath==null?"//html":ancestorXpath);
	    	
	    	for(Object element : elements){
	    		Map<String,Object> fieldValue = new HashMap<>();
	    		for(Map.Entry<String, String> eleAnno : ReflectUtils.nodeAnnotations(target).entrySet()){
	    			String fieldName = eleAnno.getKey();
	    			String xpath = eleAnno.getValue();
	    			Object[] obj = ((TagNode)element).evaluateXPath(xpath);			
	    			String text = obj.length >0 ? new StringBuffer(((TagNode)obj[0]).getText()).toString() : "";
	    			
	    			Object value = ReflectUtils.convert(target,fieldName,text.trim());
	    			
	    			fieldValue.put(fieldName, value);
	    		}
	    		T instance = ReflectUtils.getInstance(target,fieldValue);
	    		resList.add(instance);
	    	}
    	}catch(XPatherException e){
    		log.error("xpath error!");
    		e.printStackTrace();
    	}
    	return resList;
    }
    
    /*private String getElementText(Node node,Element e){
    	String text = "";
		if(!isBlank(node.getId())){
			text = getTextById(node.getId(), e);
		} else if(!isBlank(node.getClazz())){
			text = getTextByClass(node.getClazz(), e);
		}
		
		return text;
    }
    
    private boolean isBlank(String seq){
    	return StringUtils.isBlank(seq);
    }
    
    public Element getSubElementById(String id,Element parentElement){
    	Element element = null;
    	if(id.startsWith("#")){
    		element = parentElement.select(id).first();
		} else {
			element = parentElement.getElementById(id);
		}
    	return element;
    }
    
    public String getTextById(String id, Element element) {
		return getSubElementById(id,element).text();
	}
    
    public Elements getSubElementByClass(String clazz,Element parentElement){
    	Elements elements = null;
    	if(clazz.startsWith(".")){
    		elements = parentElement.select(clazz);
		} else {
			elements = parentElement.getElementsByClass(clazz);
		}
    	return elements;
    }
    
    public String getTextByClass(String clazz, Element element) {
		return getSubElementByClass(clazz,element).text();
	}
*/
    
    public Document getDocument(){
    	return document;
    }
    
    public String toString(){
    	return document.html();
    }
}
