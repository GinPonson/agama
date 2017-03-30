package com.github.gin.agama.proxy;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;

/**
 * @author  GinPonson
 */
public class HttpProxy extends Proxy{
	
	private String host;
	private int port;
    private String user = "";
    private String password = "";

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

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    class MyAuthenticator extends Authenticator {
       public MyAuthenticator(String username, String pwd) {
          user = username;
          password = pwd;
       }
       protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(user, password.toCharArray());
       }
	}

}
