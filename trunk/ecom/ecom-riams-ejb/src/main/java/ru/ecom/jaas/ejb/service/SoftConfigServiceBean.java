package ru.ecom.jaas.ejb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.diary.ejb.service.protocol.KdlDiaryServiceBean;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.jaas.ejb.domain.SoftConfig;

@Stateless
@Remote(ISoftConfigService.class )
public class SoftConfigServiceBean implements ISoftConfigService {
	public static  String getDefaultParameterByConfig(String aParameter, String aValueDefault, EntityManager aManager) {
		List<Object[]> l = aManager.createNativeQuery("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key='"+aParameter+"'").getResultList();
		if (l.isEmpty()) {
			return aValueDefault ;
		} else {
			return new StringBuilder().append(l.get(0)[1]).toString() ;
		}
	}
	public String getDir(String aKey, String aDefaultValue) {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		return config.get(aKey, aDefaultValue) ;
	}
	public void saveContextHelp(String aUrl,String aContext) {
		PrintWriter out = null ;
		try {
			File file = getFile(aUrl, true) ;
			out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf8"));
            out.println(aContext) ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if (out!=null) out.close() ;
			
		}

	}
	private File getFile(String aUrl,boolean aIsCreateDir) {
		aUrl=aUrl.replace("/WEB-INF/actions", "/riams") ;
		aUrl=aUrl.replace(".jsp", ".htm") ;
		String dirName = getDir("help_riams", "/opt/tomcat/webapps/help") ;
		dirName= dirName+aUrl ;
		String[] u = dirName.split("/") ;
		String f =u[u.length-1] ;
		dirName=dirName.replace("/"+f, "") ;
		System.out.println("load dirName="+dirName+" file="+f) ;
		if (aIsCreateDir) {
			File dir = new File(dirName) ;
			if (dir.exists()) {
				System.out.println("exists dir") ;
			} else {
				System.out.println("create dir="+dir.mkdirs()) ;
			}
		}
		File file = new File(dirName,f) ;
		return file ;
	}
	public String getContextHelp(String aUrl) {
		StringBuilder sb =new StringBuilder() ;
		LineNumberReader in = null ;
			try {
				File file = getFile(aUrl, false) ;
				if (file.exists()) {
					in = new LineNumberReader(new InputStreamReader(new FileInputStream(file), "utf8"));
					String line ;
					while ( (line=in.readLine())!=null) {
						sb.append(line) ;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (in!=null) in.close() ;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		return sb.toString() ;
		
	}
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
