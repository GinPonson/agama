package org.pyj.vertical.JCrawler.util;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {
	//正则：charset开头，任意空格，=，任意空格，任意'或""，组合(非空格 or 非; or 非' or 非"的任意个字符)
	private static final Pattern patternForCharset = Pattern.compile("charset\\s*=\\s*['\"]*([^\\s;'\"]*)");
	public static String matchCharset(String contentType) {
        Matcher matcher = patternForCharset.matcher(contentType);
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


}
