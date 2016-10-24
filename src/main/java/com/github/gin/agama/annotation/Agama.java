package com.github.gin.agama.annotation;

import com.github.gin.agama.downloader.Downloader;
import com.github.gin.agama.downloader.HttpDownloader;
import com.github.gin.agama.entity.HtmlEntity;
import com.github.gin.agama.processer.PageProcess;
import com.github.gin.agama.sorter.ConsoleDataStorer;
import com.github.gin.agama.sorter.DataStorer;

import java.lang.annotation.*;

/**
 * Created by FSTMP on 2016/10/20.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface Agama {

    Class<? extends HtmlEntity> entity();

    Class<? extends PageProcess> processer();

    Class<? extends DataStorer> datastore() default ConsoleDataStorer.class;

    Class<? extends Downloader> downloader() default HttpDownloader.class;

    Config[] configs() default {};

}