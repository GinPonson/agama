package com.github.gin.agama.downloader;

import com.github.gin.agama.UserAgent;
import com.github.gin.agama.proxy.Proxys;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.site.Response;
import com.github.gin.agama.util.AgamaUtils;
import com.github.gin.agama.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/**
 * @author GinPonson
 */
public class HttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    private HttpURLConnection conn = null;

    public Response execute(Request req) throws IOException {
        LOGGER.info(" Downloading the page : {}", req.getUrl());

        URL url = new URL(req.getUrl());
        if (UrlUtils.isHttps(req.getUrl())) {
            conn = (HttpsURLConnection) url.openConnection(Proxys.getProxy());
        } else {
            conn = (HttpURLConnection) url.openConnection(Proxys.getProxy());
        }

        conn.setRequestProperty("User-Agent", UserAgent.randomUserAgent());
        for (Entry<String, String> header : req.getHeaders().entrySet()) {
            conn.setRequestProperty(header.getKey(), header.getValue());
        }

        String cookies = getCookies(req);
        if (AgamaUtils.isNotBlank(cookies)) {
            conn.setRequestProperty("Cookie", getCookies(req));
        }
        conn.setRequestMethod(req.getMethod());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            LOGGER.debug(" {} {}", conn.getRequestMethod(), conn.getURL().toString());
            for (Entry<String, List<String>> entry : conn.getRequestProperties().entrySet()) {
                LOGGER.debug(" {}: {}", entry.getKey(), entry.getValue());
            }
            LOGGER.debug(" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        }

        conn.connect();

        Response response = new Response();
        response.setContentByte(getResponseByByte());
        response.setResponseCode(conn.getResponseCode());
        response.setResponseMsg(conn.getResponseMessage());
        response.setContentType(conn.getContentType());

        conn.disconnect();
        return response;
    }

    private String getCookies(Request request) {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> cookie : request.getCookies().entrySet()) {
            sb.append(cookie.getKey())
                    .append("=")
                    .append(cookie.getValue())
                    .append(";");
        }
        return sb.toString();
    }

    private byte[] getResponseByByte() {
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = null;
        byte[] bytes = null;
        try {
            bis = new BufferedInputStream(getInputStream());
            baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] bufs = new byte[1024 * 4];
            while ((len = bis.read(bufs)) != -1) {
                baos.write(bufs, 0, len);
            }
            bytes = baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null)
                    bis.close();
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    private InputStream getInputStream() {
        InputStream input = null;
        String contentEncoding = conn.getContentEncoding();
        try {
            if (AgamaUtils.isNotBlank(contentEncoding)) {
                if ("gzip".equalsIgnoreCase(contentEncoding)) {
                    input = new GZIPInputStream(conn.getInputStream());
                } else if ("deflate".equalsIgnoreCase(contentEncoding)) {
                    input = new InflaterInputStream(conn.getInputStream());
                } else {
                    input = conn.getInputStream();
                }
            } else {
                input = conn.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

}
