package com.github.gin.agama.sorter;

import com.github.gin.agama.entity.HtmlEntity;
import com.github.gin.qcsv.util.CSVExportUtil;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collection;

public class FileDataStorer implements DataStorer<HtmlEntity>{

    private File file ;

    public FileDataStorer(String fileName){
        this(new File(fileName));
    }

    public FileDataStorer(File file){
        this.file = file;
    }

    @Override
    public void store(Collection<HtmlEntity> records) {
        if(records.iterator().hasNext())
            CSVExportUtil.exportCSV(file, records.iterator().next().getClass(), records, Charset.forName("GBK"));
    }
}
