package ru.ecom.jaas.ejb.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.jaas.ejb.domain.SoftConfig;

@Stateless
@Remote(ISoftConfigService.class )
public class SoftConfigServiceBean implements ISoftConfigService {

	public void addConfigDefaults() {
		Map<String,String> defaultConfig = create() ;
		//findOrCreateSoftConfig("csp_base_url", defaultConfig) ;
		findOrCreateSoftConfig("data_base_namespace", defaultConfig) ;
	}

	public String getCspBaseUrl() {
		Map<String,String> defaultConfig = create() ;
		SoftConfig configUrl = findOrCreateSoftConfig("csp_base_url", defaultConfig) ;
		int len = configUrl.getKeyValue().length()-1 ;
		System.out.print("len="+len) ;
		System.out.print("end="+configUrl.getKeyValue().substring(len)) ;
		if (len>0&&configUrl.getKeyValue().substring(len).equals("\\") || configUrl.getKeyValue().substring(len).equals("/")) {
			configUrl.setKeyValue(configUrl.getKeyValue().substring(0, len)) ;
			theManager.persist(configUrl) ;
		}
		
		SoftConfig configNspace = findOrCreateSoftConfig("data_base_namespace",defaultConfig) ;
		return new StringBuilder().append(configUrl.getKeyValue()).append("/csp/").append(configNspace.getKeyValue()).toString();
	}

	public String getCspBaseUrl(String aTomcatUrl) {
		Map<String,String> defaultConfig = create() ;
		System.out.println("tomcaturl="+aTomcatUrl) ;
		SoftConfig configUrl = findOrCreateSoftConfig(aTomcatUrl, defaultConfig) ;
		System.out.println("configurl="+configUrl) ;
		if (configUrl.getKeyValue()!=null && !configUrl.getKeyValue().equals("")) {
		int len = configUrl.getKeyValue().length()-1 ;
		System.out.print("len="+len) ;
		System.out.print("end="+configUrl.getKeyValue().substring(len)) ;
		if (len>0&&configUrl.getKeyValue().substring(len).equals("\\") || configUrl.getKeyValue().substring(len).equals("/")) {
			configUrl.setKeyValue(configUrl.getKeyValue().substring(0, len)) ;
			theManager.persist(configUrl) ;
		}
		
		SoftConfig configNspace = findOrCreateSoftConfig("data_base_namespace",defaultConfig) ;
		return new StringBuilder().append(configUrl.getKeyValue()).append("/csp/").append(configNspace.getKeyValue()).toString();
		} else {
			throw new IllegalStateException(new StringBuilder().append("Настройте приложение ключ(\"").append(aTomcatUrl).append("\")").toString())   ;
		}
	}

	private SoftConfig findOrCreateSoftConfig(String aKey, Map<String,String> aDefault) {
		SoftConfig config = null ;
		if (aKey!=null && !aKey.equals("")) {
			List<SoftConfig> list = theManager.createQuery("from SoftConfig where key=:key")
				.setParameter("key", aKey)
				.getResultList();
			if (list.size()==0) {
				config = new SoftConfig() ;
				config.setKey(aKey) ;
				String value=aDefault.get(new StringBuilder().append(aKey).append(":value").toString()) ;
				String description=aDefault.get(new StringBuilder().append(aKey).append(":description").toString()) ;
				if (value==null) value="" ;
				config.setKeyValue(value) ;
				config.setDescription("auto " +description) ;
				theManager.persist(config) ;
			} else {
				config= list.get(0) ;
			}
			
		} 
		return config ;
	}
	
	private Map<String, String> create() {
		Map<String, String> map = new TreeMap<String, String>();
        System.out.println("creating theMap = " + map);
        map.put("data_base_namespace:value","riams") ;
        map.put("data_base_namespace:description","Основной namespace, в котором настроено приложение") ;
        return map ;
    }
    @PersistenceContext private EntityManager theManager ;
    @Resource SessionContext theContext ;
}
