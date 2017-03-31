package com.github.gin.agama.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * URL的各种工具
 * @author  GinPonson
 */
public class UrlUtils {
    //正则：charset开头，任意空格，=，任意空格，任意'或""，组合(非空格 or 非; or 非' or 非"的任意个字符)
    private static final Pattern PATTERN_FOR_CHARSET = Pattern.compile("charset\\s*=\\s*['\"]*([^\\s;'\"]*)");

    /**
     * 获取charset
     *
     * @param contentType
     * @return
     */
    public static String matchCharset(String contentType) {
        Matcher matcher = PATTERN_FOR_CHARSET.matcher(contentType);
        if (matcher.find()) {
            String charset = matcher.group(1);
            if (Charset.isSupported(charset)) {
                return charset;
            }
        }
        return null;
    }

    private static final Pattern PATTERN_FOR_DOMAIN = Pattern.compile("(http[s]?://[\\w\\.]*)([/\\w\\-\\.]*)\\??([=\\w&]*)");

    /**
     * 根据地址获取默认的域
     *
     * @param url
     * @return
     */
    public static String getDomain(String url) {
        Matcher matcher = PATTERN_FOR_DOMAIN.matcher(url);
        if (matcher.find()) {
            String domain = matcher.group(1);
            return domain;
        }
        return null;
    }

    public static String toReLink(String url){
        Matcher matcher = PATTERN_FOR_DOMAIN.matcher(url);
        if (matcher.find()) {
            String domain = matcher.group(2);
            return domain;
        }
        return null;
    }

    /**
     * 将相对地址转成绝对地址
     *
     * @param baseUri
     * @param relUrl
     * @return
     */
    public static String toAsbLink(String baseUri, String relUrl) {
        URL base;
        try {
            try {
                base = new URL(baseUri);
            } catch (MalformedURLException e) {
                // the base is unsuitable, but the attribute may be abs on its own, so try that
                URL abs = new URL(relUrl);
                return abs.toExternalForm();
            }
            // workaround: java resolves '//path/file + ?foo' to '//path/?foo', not '//path/file?foo' as desired
            if (relUrl.startsWith("?"))
                relUrl = base.getPath() + relUrl;
            URL abs = new URL(base, relUrl);
            return abs.toExternalForm();
        } catch (MalformedURLException e) {
            return "";
        }
    }

    public static boolean isHttps(String url) {
        return url.startsWith("https");
    }

    private static final Pattern PATTERN_FOR_PARAM = Pattern.compile("\\$\\{([\\w]*)}");

    /**
     * 获取需要填充的参数
     * @param url
     * @return
     */
    public static List<String> getParam(String url) {
        List<String> list = new ArrayList<>();
        Matcher matcher = PATTERN_FOR_PARAM.matcher(url);
        if (matcher.find()) {
            list.add(matcher.group(1));

            String patternStr = matcher.group(0);
            int lastPos = url.indexOf(patternStr) + patternStr.length();
            String subStr = url.substring(lastPos, url.length());
            list.addAll(getParam(subStr));
        }
        return list;
    }

    public static boolean matchUrl(String matchUrl, String url) {
        return url.contains(matchUrl);
    }

    public static void main(String[] args) {
        System.out.println(toAsbLink("http://bilibili.com", "/video/bangumi-two-2.html"));
        System.out.println(getParam("D:\\aa\\${bbb}\\cc\\${dd}\\${gg}"));
        System.out.println("D:\\aa\\${bbb}\\${dd}");
        System.out.println(getDomain("http://pic.cnblogs.com/face/u325852.jpg?id=18155157"));

    }
}
