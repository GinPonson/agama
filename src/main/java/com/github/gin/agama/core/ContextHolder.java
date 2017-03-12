package com.github.gin.agama.core;

/**
 * Created by GinPonson on 2017/3/12.
 */
public class ContextHolder {

    private static ThreadLocal<CrawlerContext> threadLocal = new ThreadLocal<>();

    public static void setContext(CrawlerContext context) {
        threadLocal.set(context);
    }


    public static CrawlerContext getContext() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
