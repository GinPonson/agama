package com.github.gin.agama.site.render;


import com.github.gin.agama.annotation.Html;
import com.github.gin.agama.annotation.Url;
import com.github.gin.agama.annotation.Xpath;
import com.github.gin.agama.core.ContextHolder;
import com.github.gin.agama.downloader.Downloader;
import com.github.gin.agama.site.bean.AgamaEntity;
import com.github.gin.agama.site.bean.XpathEntity;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.site.TagNodes;
import com.github.gin.agama.site.converter.TypeConverter;
import com.github.gin.agama.site.serekuta.XpathSerekuta;
import com.github.gin.agama.util.AgamaUtils;
import com.github.gin.agama.util.ReflectUtils;
import com.github.gin.agama.util.UrlUtils;
import com.github.gin.agama.util.XpathUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.reflections.ReflectionUtils.getFields;
import static org.reflections.ReflectionUtils.withAnnotation;

public class XpathRender extends AbstractRender {

    private static final Logger LOGGER = LoggerFactory.getLogger(XpathRender.class);

    public static final HtmlCleaner HTML_CLEANER = new HtmlCleaner();

    @Override
    public List<AgamaEntity> renderToList(Page page, Class<? extends AgamaEntity> clazz) {
        List<AgamaEntity> result = new ArrayList<>();
        //格式化html以便xpath操作,因为有些标签是不对称的
        TagNode rootTagNode = HTML_CLEANER.clean(page.getRawText());

        //如果类带@Xpath注解，则只处理某段html
        if (clazz.isAnnotationPresent(Xpath.class)) {
            String xpath = clazz.getAnnotation(Xpath.class).value();
            TagNodes segmentNodes = XpathUtils.evaluate(rootTagNode, xpath);

            for (TagNode tagNode : segmentNodes) {
                String segmentHtml = XpathSerekuta.html(tagNode);
                page = new Page(page.getUrl(), segmentHtml);
                AgamaEntity agamaEntity = renderToBean(page, clazz);
                result.add(agamaEntity);
            }
        } else {
            result.add(renderToBean(page, clazz));
        }

        return result;
    }

    @Override
    public AgamaEntity renderToBean(Page page, Class<? extends AgamaEntity> clazz) {
        TagNode pageTagNode = HTML_CLEANER.clean(page.getRawText());

        AgamaEntity entity = ReflectUtils.newInstance(clazz);
        Set<Field> xpathFieldSet = getFields(clazz, withAnnotation(Xpath.class));
        for (Field field : xpathFieldSet) {
            //1、根据xpath解析html
            String xpath = field.getAnnotation(Xpath.class).value();
            TagNodes nodes = XpathUtils.evaluate(pageTagNode, xpath);

            boolean isList = ReflectUtils.haveSuperType(field.getType(), List.class);
            if (isList) {
                Type type = field.getGenericType();
                Class<?> genericClass = ReflectUtils.getGenericClass(type, 0);

                boolean isAgamaEntity = ReflectUtils.haveSuperType(genericClass, AgamaEntity.class);
                if (isAgamaEntity) {
                    Class<? extends AgamaEntity> $genericClass = (Class<? extends AgamaEntity>) genericClass;

                    List<AgamaEntity> subEntityList = new ArrayList<>();
                    List<String> segmentHtmls = XpathSerekuta.htmls(nodes);
                    for (String html : segmentHtmls) {
                        page = new Page(page.getUrl(), html);
                        AgamaEntity subEntity = renderToBean(page, $genericClass);
                        subEntityList.add(subEntity);
                    }
                    ReflectUtils.setValue(field.getName(), entity, subEntityList);
                }
            } else {
                String dataText = "";
                //是否带有@Html，@Html表示文本带html标签
                if (field.isAnnotationPresent(Html.class)) {
                    if (!nodes.isEmpty()) {
                        dataText = XpathUtils.getHtmlText(nodes.get(0)).toString().trim();
                    }
                } else {
                    dataText = nodes.getFirstNodeText().trim();
                }

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Render field name : {}, field type : {}, filed raw value : {}",
                            field.getName(), field.getType(), dataText);
                }

                Object data = TypeConverter.convert(dataText, field.getType());
                ReflectUtils.setValue(field.getName(), entity, data);
            }
        }
        renderUrl(page, entity);

        download(page, entity);

        return entity;
    }

    private void renderUrl(Page page, AgamaEntity entity) {
        TagNode pageTagNode = HTML_CLEANER.clean(page.getRawText());

        Set<Field> urlFieldSet = getFields(entity.getClass(), withAnnotation(Url.class));
        for (Field field : urlFieldSet) {
            String src = field.getAnnotation(Url.class).src();
            TagNodes urlNodes = XpathUtils.evaluate(pageTagNode, src);
            String nodeText = urlNodes.getFirstNodeText().trim();

            String domain = UrlUtils.getDomain(page.getUrl());
            String url = UrlUtils.toAsbLink(domain, nodeText);

            renderSubRequest(entity, field, url);
        }
    }

}
