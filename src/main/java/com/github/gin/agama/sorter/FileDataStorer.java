package com.github.gin.agama.sorter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileDataStorer implements DataStorer{

    private File file ;

    public FileDataStorer(String fileName){
        this(new File(fileName));
    }

    public FileDataStorer(File file){
        this.file = file;
    }

	@Override
	public void store(List<Map<String, String>> records) {
        OutputStreamWriter writer = null;
        CSVPrinter csvPrinter = null;
        boolean isInitedHead = false;
        try {
            if(file.exists()){
               isInitedHead = true;
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            writer = new OutputStreamWriter(fileOutputStream, Charset.forName("GBK"));
            csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

            if(!isInitedHead && !records.isEmpty()){
                csvPrinter.printRecord(records.get(0).keySet());
            }

            for(Map<String,String> rawRecord : records){
                List<String> record = new ArrayList<>();
                for(String title : rawRecord.keySet()){
                    record.add(rawRecord.get(title));
                }
                csvPrinter.printRecord(record);
            }

            csvPrinter.flush();
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                csvPrinter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
