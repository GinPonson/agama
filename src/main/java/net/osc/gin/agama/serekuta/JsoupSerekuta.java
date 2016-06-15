package net.osc.gin.agama.serekuta;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.osc.gin.agama.util.UrlUtils;

public class JsoupSerekuta  implements Serekuta{
	
	private Elements elements;
	
	private String domain;
	
	public JsoupSerekuta(Elements elements) {
		this.elements = elements;
	}

	public JsoupSerekuta(Elements elements, String baseUri) {
		this.elements = elements;
		domain = baseUri;
	}

	@Override
	public List<String> list() {
		List<String> list = new ArrayList<>();
		for(Element element : elements){
			list.add(UrlUtils.toAsbLink(domain,element.text()));
		}
		return list;
	}
	
	@Override
	public List<String> list(String regex) {
		List<String> list = new ArrayList<>();
		for(Element element : elements){
			if(element.text().matches(regex)){
				list.add(UrlUtils.toAsbLink(domain,element.text()));
			}
		}
		return list;
	}
	
	@Override
	public String text() {
		return elements.text();
	}
	
	@Override
	public String attr(String attr) {
		return elements.attr(attr);
	}

	@Override
	public Serekuta first() {
		Elements es = new Elements();
		es.add(elements.first());
		return new JsoupSerekuta(es);
	}

	@Override
	public Serekuta last() {
		Elements es = new Elements();
		es.add(elements.last());
		return new JsoupSerekuta(es);
	}


}
