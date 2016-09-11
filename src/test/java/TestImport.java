import com.github.gin.qcsv.exporter.CSVExporter;
import com.github.gin.qcsv.util.CSVImportUtil;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by FSTMP on 2016/9/2.
 */
public class TestImport {
    @Test
    public void imp(){
        CSVExporter<XianChang> csvExporter = new CSVExporter<>(XianChang.class);

        List<XianChang> xianChangs = CSVImportUtil.importCSV(new File("C:\\Users\\fstmp\\Desktop\\doc\\8.30\\245371_20160824,20160830 现场.csv"), XianChang.class, Charset.forName("GBK"));

        System.out.print(xianChangs.size());
    }
}
