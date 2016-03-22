

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.input.ReaderInputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.Select;

public class WeatherProcess {

	
	public static void main(String[] args) throws IOException {
        //如果火狐浏览器没有默认安装在C盘，需要制定其路径
         //System.setProperty("webdriver.firefox.bin", "D:/Program Files/Mozilla firefox/firefox.exe");

		FirefoxProfile firefoxProfile = new ProfilesIni().getProfile("default");
		File pluginAutoAuth = new File("src/test/resources/autoauth-2.1-fx+fn.xpi");
		firefoxProfile.addExtension(pluginAutoAuth);
		
        WebDriver driver = new FirefoxDriver(firefoxProfile);
         driver.get("http://www.weather.com.cn/alarm/newalarmlist.shtml");
         //driver.manage().window().maximize();
         
         Select selectShen = new Select(driver.findElement(By.id("pro")));
         selectShen.selectByValue("10128");

         //WebElement txtbox = driver.findElement(By.id("pro"));
         //txtbox.sendKeys("Glen");

         WebElement btn = driver.findElement(By.id("Submit"));
         btn.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement wn = driver.findElement(By.className("dDisasterAlarm"));
        
        //String test = wn.getText();
        StringReader sr = new StringReader(wn.getText().replace("\n", "\r\n"));
        //DataInputStream di = new DataInputStream(new ReaderInputStream(sr));
        FileWriter fos = new FileWriter("D://weather.txt");
        char[] bytes = new char[100];
        int len = 0;
        while((len = sr.read(bytes))!=-1){
        	fos.write(bytes, 0, len);
        }
        fos.close();
        sr.close();
        System.out.println(wn.getText());
         driver.close();
	}
	
	
}
