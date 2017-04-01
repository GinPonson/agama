package other;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by FSTMP on 2017/4/1.
 */
public class TestHtmlUnit {

    @Test
    public void testHtmlUnit() throws IOException {
        final WebClient webClient=new WebClient();
        //System.setProperty("http.proxyHost", "10.228.110.21");
        //System.setProperty("http.proxyPort", "80");
        Authenticator.setDefault(new MyAuthenticator("panyongjian","pan240409F"));
        ProxyConfig proxyConfig = webClient.getOptions().getProxyConfig();
        proxyConfig.setProxyHost("10.228.110.21");
        proxyConfig.setProxyPort(80);
        final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
        credentialsProvider.addCredentials("panyongjian","pan240409F");
        //webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);//setJavaScriptEnabled(false);
        final HtmlPage page=webClient.getPage("http://www.weather.com.cn/alarm/warninglist.shtml");

        HtmlForm form = page.getFormByName("warnning_find");
        final HtmlButtonInput button = form.getInputByName("Submit");
        final HtmlSelect textField = form.getSelectByName("area");

        textField.setSelectedAttribute("10129",true);

        final HtmlPage page1 = button.click();
        System.out.println(page1.asXml());
        webClient.close();
    }

    class MyAuthenticator extends Authenticator {
        private String user;
        private String password;
        public MyAuthenticator(String username, String pwd) {
            user = username;
            password = pwd;
        }
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, password.toCharArray());
        }
    }

    /*Select selectSheng = new Select(webDriver.findElement(By.id("pro")));
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
    }*/
}
