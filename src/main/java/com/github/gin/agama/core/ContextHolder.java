package com.github.gin.agama.core;

/**
 * @author  GinPonson
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
