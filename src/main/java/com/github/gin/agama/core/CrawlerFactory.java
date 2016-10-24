package com.github.gin.agama.core;

import com.github.gin.agama.annotation.Agama;
import com.github.gin.agama.annotation.Config;
import com.github.gin.agama.downloader.Downloader;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.sorter.DataStorer;
import com.github.gin.agama.util.ReflectUtils;

/**
 * Created by FSTMP on 2016/10/24.
 */
public class CrawlerFactory {

    public static JCrawler create(Agama agama){
        Class processClass = agama.processer();
        PageProcess process = (PageProcess) ReflectUtils.newInstance(processClass);

        Class datastoreClass = agama.datastore();
        DataStorer dataStorer = (DataStorer) ReflectUtils.newInstance(datastoreClass);

        Class downloaderClass = agama.downloader();
        Downloader downloader = (Downloader) ReflectUtils.newInstance(downloaderClass);


        CrawlConfiger crawlConfiger = new CrawlConfiger();
        Config[] configs = agama.configs();
        for(Config config :configs){
            String key = config.key();
            String val = config.val();
            ReflectUtils.setValue(key,crawlConfiger,val);
        }

        return JCrawler.create(process).setDownloader(downloader).persistBy(dataStorer).setConfig(crawlConfiger);
    }
}
