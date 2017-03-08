package com.github.gin.agama.site;


import com.github.gin.agama.annotation.Text;
import com.github.gin.agama.annotation.Xpath;
import com.github.gin.agama.entity.AgamaEntity;
import com.github.gin.agama.serekuta.XpathSerekuta;
import com.github.gin.agama.site.converter.TypeConverter;
import com.github.gin.agama.util.ReflectUtils;
import com.github.gin.agama.util.UrlUtils;
import com.github.gin.agama.util.XpathUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

public class XpathRender implements Render {

    private static final Logger LOGGER = LoggerFactory.getLogger(XpathRender.class);
	
    private static final HtmlCleaner HTML_CLEANER = new HtmlCleaner();

    @Override
    public AgamaEntity inject(Page page, Class<? extends AgamaEntity> clazz) {
        //格式化html以便xpath操作,因为有些标签是不对称的
        TagNode pageTagNode = HTML_CLEANER.clean(page.getRawText());

        AgamaEntity entity = ReflectUtils.newInstance(clazz);
        Set<Field> fieldSet = getAllFields(clazz,withAnnotation(Xpath.class));

        for(Field field : fieldSet){
            //1、根据xpath解析html
            String xpath = field.getAnnotation(Xpath.class).value();
            TagNodes nodes = XpathUtils.evaluate(pageTagNode,xpath);

            Class<?> fieldClas = field.getType();
            //boolean isArray = fieldClas.isArray();
            boolean isList = ReflectUtils.haveSuperType(fieldClas,List.class);

            if(isList){
                Type type = field.getGenericType();
                Class genericClass = ReflectUtils.getGenericClass(type,0);

                boolean isAgamaEntity = ReflectUtils.haveSuperType(genericClass,AgamaEntity.class);
                if(isAgamaEntity) {
                    List<AgamaEntity> subEntityList = new ArrayList<>();

                    XpathSerekuta serekuta = new XpathSerekuta(nodes, UrlUtils.getDomain(page.getUrl()));
                    List<String> itemHtml = serekuta.list();
                    for(String html : itemHtml){
                        AgamaEntity subEntity = inject(new Page(html),genericClass);
                        subEntityList.add(subEntity);
                    }
                    ReflectUtils.setValue(field.getName(), entity, subEntityList);
                }
            } else {
                //不需要解析集合的情况
                String dataText = "";
                if(field.isAnnotationPresent(Text.class)){
                    if(field.getAnnotation(Text.class).value()){
                        dataText = XpathUtils.getHtmlText(nodes.get(0)).toString().trim();
                    }
                } else {
                    dataText = nodes.get(0).getText().toString().trim();
                }

                Object data = TypeConverter.convert(dataText, field.getType());
                ReflectUtils.setValue(field.getName(), entity, data);
            }
        }

        return entity;
    }
}
