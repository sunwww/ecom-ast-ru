package ru.ecom.jaas.ejb.service;

public interface ISoftConfigService {
	public void addConfigDefaults()  ;
	public String getCspBaseUrl()  ;
	public String getCspBaseUrl(String aTomcatUrl) ;
	public void saveContextHelp(String aUrl,String aContext) ;
	public String getContextHelp(String aUrl) ;
}
