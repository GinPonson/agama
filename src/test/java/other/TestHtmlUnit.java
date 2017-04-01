package other;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

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
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        //webClient.addRequestHeader("Proxy-Authorization","Basic cGFueW9uZ2ppYW46cGFuMjQwNDA5Rg==");
        final HtmlPage page=webClient.getPage("https://www.v2ex.com/");
        System.out.println(page.asText());
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
}
