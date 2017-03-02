package com.github.gin.agama.client;

import com.github.gin.agama.UserAgent;
import com.github.gin.agama.proxy.Proxys;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.site.Response;
import com.github.gin.agama.util.AgamaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class HttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    private HttpURLConnection conn = null;

    public Response execute(Request req) throws IOException {
        LOGGER.info("[{}] downloading the page : {}",
                Thread.currentThread().getName(), req.getUrl());

        URL url = new URL(req.getUrl());
        conn = (HttpURLConnection) url.openConnection(Proxys.getProxy());

        conn.setRequestProperty("User-Agent", UserAgent.randomUserAgent());
        for (Entry<String, String> header : req.getHeaders().entrySet()) {
            conn.setRequestProperty(header.getKey(), header.getValue());
        }

        conn.setRequestProperty("Cookie", getCookies(req));
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
        for(Entry<String,String> cookie : request.getCookies().entrySet()) {
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
                LOGGER.error("close error ! error message : {}", e.getMessage());
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
            LOGGER.error("error message : {}", e.getMessage());
            e.printStackTrace();
        }
        return input;
    }

}
