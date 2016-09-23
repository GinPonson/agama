

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class WeatherProcess1 {
	
	public static void CaptureScreenshot(String fileName, WebDriver driver) {
		String dirName = "d://dn//screenshot";
System.out.println(fileName);		
		if (!(new File(dirName).isDirectory())) {
			new File(dirName).mkdir();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
	    String time = sdf.format(new Date());
	    TakesScreenshot tsDriver = (TakesScreenshot) driver;
	    File image = new File(dirName + File.separator + time + "_" + (fileName==null?"":fileName + ".png"));
	    System.out.println(image.getName());
	    File file = tsDriver.getScreenshotAs(OutputType.FILE);
System.out.println(file);
	    try {
			FileUtils.copyFile(file, image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) throws IOException {
        //如果火狐浏览器没有默认安装在C盘，需要制定其路径
        System.setProperty("phantomjs.binary.path", "D:/download/phantomjs-2.1.1-windows/bin/phantomjs.exe");
        // System.setProperty("webdriver.firefox.bin", "D:/Program Files/Mozilla firefox/firefox.exe");

		/*FirefoxProfile firefoxProfile = new ProfilesIni().getProfile("default");
		File pluginAutoAuth = new File("src/test/resources/autoauth-2.1-fx+fn.xpi");
		firefoxProfile.addExtension(pluginAutoAuth);*/
        ArrayList<String> cliArgsCap = new ArrayList<String>();
        cliArgsCap.add("--proxy=http://10.228.110.21:80");
        cliArgsCap.add("--proxy-auth=panyongjian:pan240409F");
        cliArgsCap.add("--proxy-type=http");
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
                cliArgsCap);

		WebDriver driver = new PhantomJSDriver(capabilities);
         driver.get("http://www.weather.com.cn/alarm/newalarmlist.shtml");
         //driver.manage().window().maximize();
         
        Select selectShen = new Select(driver.findElement(By.id("pro")));
        selectShen.selectByValue("10128");
System.out.println(driver.getPageSource());
        WebElement btn = driver.findElement(By.id("Submit"));
        btn.click();
       // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement wn = driver.findElement(By.className("dDisasterAlarm"));
        StringBuilder sb = new StringBuilder(wn.getText().replace("\n", "\r\n"));

System.out.println("~~~~~~~~");
        List<WebElement> btn2 = driver.findElements(By.className("dClick"));
        for(int i = 1 ;i< btn2.size();i++){
            WebElement element = btn2.get(i);
            element.click();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebElement wn2 = driver.findElement(By.className("dDisasterAlarm"));
            sb.append(wn2.getText().replace("\n", "\r\n"));
        }
System.out.println("~~~~~~~~");
        CaptureScreenshot("1",driver);
        
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String path = "D://dn//"+time;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        StringReader sr = new StringReader(sb.toString());
        //DataInputStream di = new DataInputStream(new ReaderInputStream(sr));
        FileWriter fos = new FileWriter(path+File.separator+"weather"+UUID.randomUUID().toString()+".txt");
        char[] bytes = new char[100];
        int len = 0;
        while((len = sr.read(bytes))!=-1){
            fos.write(bytes, 0, len);
        }
        fos.close();
        sr.close();

        System.out.println(wn.getText());
        driver.quit();
	}
	
	
}
