package org.pyj.vertical.JCrawler.util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;


public class HttpRequest {
    
    //private static final Log log=LogFactory.getLog(HttpRequest.class);
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @throws UnsupportedEncodingException 
     */
    public static String sendGet(String url,String enc) throws UnsupportedEncodingException {
        String result = "";
        BufferedReader in = null;
        //System.setProperty("http.proxyHost", ConfigUtil.get("http.proxyHost"));
        //System.setProperty("http.proxyPort", ConfigUtil.get("http.proxyPort"));
        
        /*String username = ConfigUtil.get("http.userName");
        String password = ConfigUtil.get("http.password");
        Authenticator.setDefault(new MyAuthenticator(username, password));
        */
        try {
            //String urlNameString = url + "?" + param;
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),enc));
            /*String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }*/
            System.out.println(connection.getContentType());
            System.out.println(connection.getContentEncoding());
            System.out.println(connection.getContent());
            //InflaterInputStream
            byte[] bytes = IOUtils.toByteArray( new GZIPInputStream (connection.getInputStream()));
            result = new String(bytes,"UTF-8");
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            //log.error("发送GET请求出现异常！", e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(sendGet("http://www.bilibili.com/video/bangumi-two-1.html", "utf-8"));
	}

}
