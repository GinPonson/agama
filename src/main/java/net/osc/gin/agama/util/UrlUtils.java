package net.osc.gin.agama.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {
	//正则：charset开头，任意空格，=，任意空格，任意'或""，组合(非空格 or 非; or 非' or 非"的任意个字符)
	private static final Pattern PATTERN_FOR_CHARSET = Pattern.compile("charset\\s*=\\s*['\"]*([^\\s;'\"]*)");
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
	public static String getDefaultDomain(String url) {
		Matcher matcher = PATTERN_FOR_DOMAIN.matcher(url);
		if(matcher.find()){
			String domain = matcher.group(1);
			return domain;
		}
		return null;
	}

	public static boolean isAbsLink(String absLink) {
		return absLink.startsWith("http://") || absLink.startsWith("https://");
	}
	
	public static String toAsbLink(String baseUri,String relUrl) {
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

	public static void main(String[] args) {
		System.out.println(toAsbLink("http://bilibili.com", "/video/bangumi-two-2.html"));
	}

}
