package weather;

import com.github.gin.agama.downloader.PhantomDownloader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by FSTMP on 2016/9/9.
 */
public class WeatherDownloader extends PhantomDownloader {
    @Override
    public void opration(WebDriver webDriver) {
        Select selectSheng = new Select(webDriver.findElement(By.id("pro")));
        selectSheng.selectByValue("10128");

        WebElement submitBtn = webDriver.findElement(By.id("Submit"));
        submitBtn.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement disasterAlarm = webDriver.findElement(By.className("dDisasterAlarm"));
        StringBuilder sb = new StringBuilder(disasterAlarm.getText().replace("\n", "\r\n"));

        List<WebElement> pages = webDriver.findElements(By.className("dClick"));
        for(int i = 1 ;i< pages.size();i++){
            WebElement element = pages.get(i);
            element.click();
            webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            WebElement disasterAlarm1 = webDriver.findElement(By.className("dDisasterAlarm"));
            sb.append(disasterAlarm1.getText().replace("\n", "\r\n"));
        }

        captureScreenshot(webDriver);

        writeToFile(sb);
    }

    public void captureScreenshot(WebDriver driver) {
        String dirName = "d://dn//screenshot";
        if (!(new File(dirName).isDirectory())) {
            new File(dirName).mkdir();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String time = sdf.format(new Date());
        TakesScreenshot tsDriver = (TakesScreenshot) driver;
        File image = new File(dirName + File.separator + time + ".png");
        File file = tsDriver.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeToFile(StringBuilder sb){
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File file = new File("D://dn//"+time);
        if(!file.exists()){
            file.mkdirs();
        }
        StringReader sr = new StringReader(sb.toString());

        String fileName = file.getPath()+File.separator+"weather"+ UUID.randomUUID().toString()+".txt";

        FileWriter fos = null;
        try {
            fos = new FileWriter(fileName);
            char[] bytes = new char[100];
            int len = 0;
            while((len = sr.read(bytes))!=-1){
                fos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sr.close();
        }


    }
}
