package net.osc.gin.agama.sorter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileDataStorer implements DataStorer{

    private File file ;

    public FileDataStorer(File file){
        this.file = file;
    }

    public FileDataStorer(String fileName){
        this.file = new File(fileName);
    }

	@Override
	public void store(Map<String, String> fields) {
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

            if(!isInitedHead){
                csvPrinter.printRecord(fields.keySet());
            }

            List<String> record = new ArrayList<>();
            for(String id : fields.keySet()){
                record.add(fields.get(id));
            }
            csvPrinter.printRecord(record);

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
