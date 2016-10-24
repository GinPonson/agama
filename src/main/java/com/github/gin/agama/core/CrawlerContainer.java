package com.github.gin.agama.core;

import com.github.gin.agama.entity.HtmlEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by FSTMP on 2016/10/24.
 */
public class CrawlerContainer {

    private static Map<Class<? extends HtmlEntity>,JCrawler> crawlerMap = new ConcurrentHashMap<>();

    public static JCrawler get(Class<? extends HtmlEntity> entity){
        return crawlerMap.get(entity);
    }

    public static void put(Class<? extends HtmlEntity> entity,JCrawler crawler){
        if(get(entity) == null)
            crawlerMap.put(entity,crawler);
    }
}
