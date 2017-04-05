package com.github.gin.agama.core;

import com.github.gin.agama.Closeable;
import com.github.gin.agama.downloader.DefaultPhantomDownloader;
import com.github.gin.agama.downloader.Downloader;
import com.github.gin.agama.downloader.HttpDownloader;
import com.github.gin.agama.pipeline.ConsolePipeline;
import com.github.gin.agama.pipeline.Pipeline;
import com.github.gin.agama.processer.DefaultPageProcess;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.proxy.Proxys;
import com.github.gin.agama.scheduler.DuplicateUrlScheduler;
import com.github.gin.agama.scheduler.FIFOUrlScheduler;
import com.github.gin.agama.scheduler.Scheduler;
import com.github.gin.agama.site.entity.AgamaEntity;
import com.github.gin.agama.site.entity.JsonEntity;
import com.github.gin.agama.site.render.JsonRender;
import com.github.gin.agama.site.render.Render;
import com.github.gin.agama.site.render.RenderType;
import com.github.gin.agama.site.render.XpathRender;
import com.github.gin.agama.util.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GinPonson
 */
public class CrawlerContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerContext.class);

    private CrawlerConfig configure;

    private Downloader downloader;

    private PageProcess pageProcess;

    private Pipeline pipeline;

    private Scheduler scheduler;

    private Map<RenderType, Render> renderMap;

    private List<Closeable> closeableList;

    private CrawlerContext() {
        this.configure = new CrawlerConfig();
    }

    public static CrawlerContext create() {
        return new CrawlerContext();
    }

    public CrawlerContext persistBy(Pipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public CrawlerContext useConfig(CrawlerConfig config) {
        this.configure = config;
        return this;
    }

    public CrawlerContext downloadBy(Downloader downloader) {
        this.downloader = downloader;
        return this;
    }

    public CrawlerContext processBy(PageProcess pageProcess) {
        this.pageProcess = pageProcess;
        return this;
    }

    public CrawlerContext scheduleBy(Scheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    public CrawlerContext build() {
        if (downloader == null) {
            downloader = new HttpDownloader();
        }
        if (pageProcess == null) {
            pageProcess = new DefaultPageProcess();
        }
        if (pipeline == null) {
            pipeline = new ConsolePipeline();
        }
        if (scheduler == null) {
            scheduler = new DuplicateUrlScheduler(new FIFOUrlScheduler());
        }
        closeableList = new ArrayList<>();
        for (Field field : CrawlerContext.class.getDeclaredFields()) {
            if (ReflectUtils.haveSuperType(field.getClass(), Closeable.class)) {
                closeableList.add((Closeable) ReflectUtils.getValue(field.getName(), this));
            }
        }

        renderMap = new HashMap<>();
        renderMap.put(RenderType.Json, new JsonRender());
        renderMap.put(RenderType.Xpath, new XpathRender());

        Proxys.setEnable(configure.isEnableProxy());

        return this;
    }

    public CrawlerConfig getConfigure() {
        return configure;
    }

    public Downloader getDownloader() {
        return downloader;
    }

    public PageProcess getPageProcess() {
        return pageProcess;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public List<Closeable> getCloseableList() {
        return closeableList;
    }

    public Map<RenderType, Render> getRenderMap() {
        return renderMap;
    }

    public Render getRender(Class<? extends AgamaEntity> prey) {
        Render render = renderMap.get(RenderType.Xpath);
        if(ReflectUtils.haveSuperType(prey, JsonEntity.class)){
            render = renderMap.get(RenderType.Json);
        }
        return render;
    }
}
