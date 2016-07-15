package org.bdi.dsw.domain;

/**
 * Êı¾İ¿âÁ¬½ÓĞÅÏ¢Àà
 * @author Ê¯ÏÄ¾¸,³Â¿­·á,ÁÎÃôÃô,Ñ¦Î¬´Ï,ÑîºçÓî
 * 
 */
public class DBInfo{
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String driver;
	public String url;
	public String username;
	public String password;
}
