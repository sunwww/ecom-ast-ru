package ru.ecom.jaas.ejb.service;

public interface ISoftConfigService {
	public void addConfigDefaults()  ;
	public String getCspBaseUrl()  ;
	public String getCspBaseUrl(String aTomcatUrl) ;
	
}
