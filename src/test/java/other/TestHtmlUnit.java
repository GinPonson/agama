package other;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author GinPonson
 */
public class TestHtmlUnit {
    static final String HOST = "127.0.0.1";
    static final String PORT = "80";
    static final String USER = "gin";
    static final String PWD = "12345";

    public static void main(String[] args) throws Exception{
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //设置代理
        //ProxyConfig proxyConfig = webClient.getOptions().getProxyConfig();
        //proxyConfig.setProxyHost(HOST);
        //proxyConfig.setProxyPort(Integer.valueOf(PORT));
        //DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
        //credentialsProvider.addCredentials(USER, PWD);

        //设置参数
        //webClient.getOptions().setCssEnabled(false);
        //webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage page = webClient.getPage("http://www.bilibili.com/video/bangumi-two-1.html");

        System.out.println(page.asXml());
        webClient.close();
    }
}
