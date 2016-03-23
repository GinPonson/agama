package org.pyj.vertical.JCrawler.proxy;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;

public class HttpProxy extends Proxy{
	
	private String host;
	
	private int port;
	
	public HttpProxy(Type type, String host, int port) {
		super(type, new InetSocketAddress(host, port));
		this.host = host;
		this.port = port;
	}
	
	public HttpProxy(Type type, String host, int port,String username,String password) {
		super(type, new InetSocketAddress(host, port));
		Authenticator.setDefault(new MyAuthenticator(username, password));
		this.host = host;
		this.port = port;
	}
	
	public HttpProxy(Type type, SocketAddress sa) {
		super(type, sa);
	}
	
	public void setAuthenticator(String username,String password){
		Authenticator.setDefault(new MyAuthenticator(username, password));
	}
	
	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}




	static class MyAuthenticator extends Authenticator {        
		   private String user = "";        
		   private String password = "";        
		   public MyAuthenticator(String user, String password) {            
		      this.user = user;            
		      this.password = password;        
		   }        
		   protected PasswordAuthentication getPasswordAuthentication() {            
		      return new PasswordAuthentication(user, password.toCharArray());        
		   }    
	}

}
