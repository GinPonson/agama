package com.github.gin.agama.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class ImgUtils {
	
	public static void saveAsImg(String dist,String url){
		saveAsImg(dist, null, url);
	}
	
	public static void saveAsImg(String dist,String filename,String url){
		BufferedInputStream bis = null;
		FileOutputStream fios = null;
		try {
			File file = new File(dist);
			if(!file.exists()){
				file.mkdirs();
			}
			bis = new BufferedInputStream(new URL(url).openStream());
			String filepath = null;
			if(filename != null && !"".equals(filename)){
				filepath = dist+File.separator + filename + ".jpg";
			} else {
				filepath = dist+File.separator+UUID.randomUUID().toString()+".jpg";
			}
			fios = new FileOutputStream(filepath);
			byte[] bytes = new byte[1024 * 2];
			int len = 0;
			while( (len = bis.read(bytes)) != -1){
				fios.write(bytes, 0, len);
			}
			fios.flush();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bis != null){
					bis.close();
				}
				if(fios != null){
					fios.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		//saveAsFile("D://test//1.jpg","http://img9.bcyimg.com/coser/51601/post/177ne/ac8bc950c11e11e5aeae0bac70cf153b.jpg");
	}
}
