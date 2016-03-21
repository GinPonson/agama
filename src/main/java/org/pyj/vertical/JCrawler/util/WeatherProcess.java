package org.pyj.vertical.JCrawler.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WeatherProcess {

	
	public static void main(String[] args) {
        //如果火狐浏览器没有默认安装在C盘，需要制定其路径
         //System.setProperty("webdriver.firefox.bin", "D:/Program Files/Mozilla firefox/firefox.exe");
		Proxy proxy = new Proxy();
        //设置代理服务器地址
        //proxy.setHttpProxy("10.228.110.21:80");
        DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
        capabilities.setCapability(CapabilityType.PROXY, proxy);
        WebDriver driver = new FirefoxDriver();
         driver.get("http://www.weather.com.cn/alarm/newalarmlist.shtml");
         //driver.manage().window().maximize();

         //WebElement txtbox = driver.findElement(By.name("wd"));
        // txtbox.sendKeys("Glen");

         //WebElement btn = driver.findElement(By.id("su"));
         //btn.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement wn = driver.findElement(By.className("dDisasterAlarm"));
        System.out.println(wn.getText());
         driver.close();
	}
}
