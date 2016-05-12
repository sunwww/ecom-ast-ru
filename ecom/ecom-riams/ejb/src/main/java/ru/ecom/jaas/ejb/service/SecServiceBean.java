package ru.ecom.jaas.ejb.service;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.QueryResultUtil;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.domain.SecRole;
import ru.ecom.jaas.ejb.form.SecRoleForm;
import ru.ecom.report.util.XmlDocument;
/**
 * Экспорт, импорт политик, ролей
 * @author stkacheva
 *
 */
@Stateless
@Remote(ISecService.class)
public class SecServiceBean implements ISecService {
    private final static Logger LOG = Logger.getLogger(SecServiceBean.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    
    public Long findRole(PolicyForm aRole) {
    	SecRole role;
		if (aRole.getKey()==null || aRole.getKey().equals("") || aRole.getName()==null 
					|| aRole.getName().equals("")) throw new IllegalStateException("Одно из полей должно быть заполнено или название, или ключ")   ;
		
		if (aRole.getName()!=null && !aRole.getName().equals("")){
			role = QueryResultUtil.getFirst(SecRole.class
    				, theManager.createQuery("from SecRole where name=:name")
    	    		  .setParameter("name", aRole.getName())) ;
		} else {
			role = QueryResultUtil.getFirst(SecRole.class
    				, theManager.createQuery("from SecRole where key=:key")
    	    		  .setParameter("key", aRole.getKey())) ;
		}
    	return role!=null?role.getId():null;
    }
    
    public List<SecRoleForm> listRoles() {
    	List<SecRoleForm> list = new LinkedList<SecRoleForm>();
    	List<SecRole> allRoles = theManager.createQuery("from SecRole").getResultList();
    	  System.out.println(allRoles) ;
    	 for (SecRole role : allRoles) {
    		 System.out.println(role) ;
             SecRoleForm form = new SecRoleForm();
             form.setId(role.getId());
             form.setKey(role.getKey());
             form.setName(role.getName());
             form.setComment(role.getComment());
             list.add(form) ;
         }
		return list;
	}

    public String exportRoles(long[] aRoles) throws ParserConfigurationException, TransformerException {
    	EjbEcomConfig config = EjbEcomConfig.getInstance() ;
    	Map<SecPolicy, String> hash = new HashMap<SecPolicy,String>() ;
    	String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
    	String filename = "export-role-"+System.currentTimeMillis()+".xml" ;
    	File outFile = new File(workDir+"/"+filename) ;
    	XmlDocument xmlDoc = new XmlDocument() ;
    	Element root = xmlDoc.newElement(xmlDoc.getDocument(), "roles", null) ;
    	
    	SecUserServiceBean userbean = new SecUserServiceBean() ;
    	for (Long idrole:aRoles) {
    		SecRole role = theManager.find(SecRole.class, idrole) ;
    		Collection<SecPolicy> listPolicies = role.getSecPolicies() ;
    		
    		Element roleEl = xmlDoc.newElement(root, "role", null);
    		xmlDoc.newAttribute(roleEl, "key", role.getKey());
    		xmlDoc.newAttribute(roleEl, "name", role.getName());
    		xmlDoc.newAttribute(roleEl, "comment", role.getComment());
    		
    		for (SecPolicy policy : listPolicies) {
        		String key = userbean.getPolicyFullKey(policy,hash) ;
        		String name= policy.getName() ;
        		String comment = policy.getComment() ;
        		Element pol = xmlDoc.newElement(roleEl, "policy", null);
        		xmlDoc.newAttribute(pol, "key", key);
        		xmlDoc.newAttribute(pol, "name", name);
        		xmlDoc.newAttribute(pol, "comment", comment);
    		}
    	}
    	xmlDoc.saveDocument(outFile) ;
    	return filename;
    }
    
	public String exportPolicy(long[] aPolicies) throws ParserConfigurationException, TransformerException {
        EjbEcomConfig config = EjbEcomConfig.getInstance() ;
        Map<SecPolicy, String> hash = new HashMap<SecPolicy,String>() ;
        String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
        workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
        String filename = "export-policy-"+System.currentTimeMillis()+".xml" ;
        File outFile = new File(workDir+"/"+filename) ;
        XmlDocument xmlDoc = new XmlDocument() ;
        Element root = xmlDoc.newElement(xmlDoc.getDocument(), "policies", null);
        
		SecUserServiceBean userbean = new SecUserServiceBean() ;
		for (Long idpol:aPolicies) {
			SecPolicy policy = theManager.find(SecPolicy.class, idpol) ;
			String key = userbean.getPolicyFullKey(policy,hash) ;
			String name= policy.getName() ;
			String comment = policy.getComment() ;
			Element pol = xmlDoc.newElement(root, "policy", null);
			xmlDoc.newAttribute(pol, "key", key);
			xmlDoc.newAttribute(pol, "name", name);
			xmlDoc.newAttribute(pol, "comment", comment);
		}
		xmlDoc.saveDocument(outFile) ;
		return filename;
	}
    
	/**
	 * С поддержкой кэша
	 * @param aPolicies массив политик
	 * @param aNames    перевод политик
	 */
	public void importPolicies(long aMonitorId, List<PolicyForm> aPolicies) {
		
		IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Подготовка к импорту") ;
		try {
			monitor = theMonitorService.startMonitor(aMonitorId, "Импорт политик безопасности", aPolicies.size()) ;
			Map<String, SecPolicy> hash = new HashMap<String, SecPolicy>() ;
			hash.put("/", findRootPolicy()) ;
			for(PolicyForm policy : aPolicies) {
				if(monitor.isCancelled()) throw new IllegalStateException("Прервано пользователем") ;
				monitor.advice(1) ;
				monitor.setText("Импортируется "+policy);
				if (CAN_DEBUG)
					LOG.debug("importPolicies: policy = " + policy); 
				importPolicy(policy, hash) ;
			}
			monitor.finish(hash.get("/").getId()+"") ;
		} catch (Exception e) {
			monitor.error(e.getMessage(), e) ;
		}
		
	}
	
	/**
	 * Импорт ролей с поддержкой кэша
	 * @param aListRoles - список ролей с их политиками
	 */
	public void importRoles(long aMonitorId, boolean aClearRole, List<ImportRoles> aListRoles) {
		IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Подготовка к импорту") ;
		try {
			double size = 0 ;
			for (ImportRoles role : aListRoles) {
				size=size+role.getPolicies().size() ;
			}
			monitor = theMonitorService.startMonitor(aMonitorId, "Импорт политик безопасности", size) ;
			Map<String, SecPolicy> hash = new HashMap<String, SecPolicy>() ;
			for (ImportRoles role : aListRoles) {
				importPoliciesByRole(monitor,hash, aClearRole,role.getRole() , role.getPolicies()) ;
			}
			monitor.finish(hash.get("/").getId()+"") ;
		} catch(Exception e) {
			monitor.error(e.getMessage(), e) ;
		}
		
	}
    /**
     * С поддержкой кэша
     * @param aPolicies массив политик
     * @param aNames    перевод политик
     */
    public void importPoliciesByRole(IMonitor aMonitor,Map<String, SecPolicy> aHash,boolean aClearRole, PolicyForm aRole, List<PolicyForm> aPolicies) {
		SecRole role= findOrCreateRole(aRole) ;
		Collection<SecPolicy> listPolicy ;
		if (aClearRole) {
			listPolicy = new LinkedList<SecPolicy>();
		} else {
			listPolicy = role.getSecPolicies() ;
		}
		
		aMonitor.setText("Импортируется РОЛЬ: "+role.getName());
		if (CAN_DEBUG)
			LOG.debug("importPolicies: role = " + role.getName()); 
    	
    	aHash.put("/", findRootPolicy()) ;
    	if(aMonitor.isCancelled()) throw new IllegalStateException("Прервано пользователем") ;
    	
    	for(PolicyForm policy : aPolicies) {
    		aMonitor.setText("Импортируется РОЛЬ: "+role.getName()+ " политика "+ policy.getKey());
    		if(aMonitor.isCancelled()) throw new IllegalStateException("Прервано пользователем") ;
    		SecPolicy policyByRole = importPolicy(policy, aHash) ;
    		if (!listPolicy.contains(policyByRole)) listPolicy.add(policyByRole) ;
    		aMonitor.advice(1) ;
    	}
    	role.setSecPolicies(listPolicy);
    	
    	theManager.persist(role) ;
    }

    private SecPolicy findRootPolicy() {
    	SecPolicy policy = QueryResultUtil.getFirst(SecPolicy.class
    			, theManager.createQuery("from SecPolicy where key=:key")
    				.setParameter("key", "/")) ; 
        if (policy == null) {
            policy = new SecPolicy();
            policy.setKey("/");
            policy.setName("ROOT");
            policy.setComment("Корневая политика безопасности");
            theManager.persist(policy);
        }
        return policy;
    }    

    private SecPolicy importPolicy(PolicyForm aPolicy, Map<String, SecPolicy> aHash) {
    	// уже импортировали
    	if(aHash.containsKey(aPolicy.getKey())) {
    		return aHash.get(aPolicy.getKey());
    	} else { // поиск от самой верхней
    		StringTokenizer st = new StringTokenizer(aPolicy.getKey(), " /\\\"'\t,.:|\n") ; 
    		StringBuilder sb = new StringBuilder("") ;
    		SecPolicy parentPolicy = aHash.get("/") ; //findOrCreatePolicy("/", aHash) ;
    		while(st.hasMoreTokens()) {
    			String key = st.nextToken() ;
    			if(sb.length()!=1) sb.append(key) ;
    			if (st.hasMoreTokens()) {
    				parentPolicy = findOrCreatePolicy(false,parentPolicy, key, aHash, sb.toString(), "", "") ;
    			} else {
    				parentPolicy =findOrCreatePolicy(true,parentPolicy, key, aHash, sb.toString(), aPolicy.getName(), aPolicy.getComment()) ;
    			}
    		}
    		return parentPolicy ; 
    	}
    }

    private SecPolicy findOrCreatePolicy(boolean aEditIs, SecPolicy aParentPolicy, String aKey, Map<String, SecPolicy> aHash, String aFullPath, String aName, String aComment) {
    	SecPolicy policy = aHash.get(aFullPath) ;
    	if(policy==null) {
    		policy = QueryResultUtil.getFirst(SecPolicy.class
    				, theManager.createQuery("from SecPolicy where key=:key and parentSecPolicy=:parent")
    		  .setParameter("key", aKey)
    		  .setParameter("parent", aParentPolicy));
    		if(policy==null) {
    			
    			policy = new SecPolicy() ;
    			policy.setKey(aKey) ;
    			// name
    			policy.setParentSecPolicy(aParentPolicy) ;
    			theManager.persist(policy) ;
    			aHash.put(aFullPath, policy) ;
    		}
    	}
		if (aEditIs) {
			if (aName!=null&&!aName.equals(""))policy.setName(aName);
			if (aComment!=null&&!aComment.equals(""))policy.setComment(aComment);
		}
    	return policy ;
	}

    /**
     * Поиск роли, если роль не найдена, то она создается
     * @param aRole - информация о роли
     */
    private SecRole findOrCreateRole(PolicyForm aRole) {
    	SecRole role;
		if (aRole.getKey()==null || aRole.getKey().equals("") || aRole.getName()==null 
					|| aRole.getName().equals("")) throw new IllegalStateException("Одно из полей должно быть заполнено или название, или ключ")   ;
		
		if (aRole.getName()!=null && !aRole.getName().equals("")){
			role = QueryResultUtil.getFirst(SecRole.class
    				, theManager.createQuery("from SecRole where name=:name")
    	    		  .setParameter("name", aRole.getName())) ;
		} else {
			role = QueryResultUtil.getFirst(SecRole.class
    				, theManager.createQuery("from SecRole where key=:key")
    	    		  .setParameter("key", aRole.getKey())) ;
		}
		if (role ==null) {
            role = new SecRole();
            role.setKey(aRole.getKey());
            role.setName(aRole.getName());
            role.setComment(aRole.getComment());
            role.setSecPolicies(new LinkedList<SecPolicy>()) ;
            theManager.persist(role);
		} 
    	return role;
    }
    
	@PersistenceContext private EntityManager theManager ;
    private @EJB ILocalMonitorService theMonitorService ;



}
