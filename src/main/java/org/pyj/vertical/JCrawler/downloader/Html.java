package org.pyj.vertical.JCrawler.downloader;


import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Html {

    private Document document;
    public Html(Document doc) {
        this.document = doc;
    }

    public List<String> links(){
        List<String> links = new ArrayList<>();
        Elements elements = document.select("a");
        for(Element element : elements){
            links.add(element.attr("href"));
        }
        return  links;
    }
    
    public List<String> absLinks(){
    	List<String> absLinks = new ArrayList<>();
    	Elements elements = document.select("a");
        for(Element element : elements){
        	absLinks.add(element.absUrl("href"));
        }
        return absLinks;
    }
    
    public String toString(){
    	return document.html();
    }
}
