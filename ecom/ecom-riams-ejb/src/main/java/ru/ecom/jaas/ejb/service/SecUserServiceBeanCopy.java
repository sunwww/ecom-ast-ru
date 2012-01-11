package ru.ecom.jaas.ejb.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.mx.util.MBeanServerLocator;

import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.domain.SecRole;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.ecom.jaas.ejb.form.SecRoleForm;

//@Stateless
//@Remote(ISecUserService.class )
public class SecUserServiceBeanCopy  implements ISecUserService {

	    public void fhushJboss() throws ReflectionException, InstanceNotFoundException, MBeanException, MalformedObjectNameException {
	        MBeanServer SERVER = MBeanServerLocator.locateJBoss();
	        String[] signature = {"java.lang.String"};
	        Object retVal  = SERVER.invoke(new ObjectName("jboss.security:service=JaasSecurityManager")
	        , "flushAuthenticationCache", new String[]{"other"}, signature) ;

	    }

	    public void exportUsersProperties() throws IOException {
	        exportUsersProperties(Config.getConfigDir()+"/users.properties");
	    }

	    public void exportUsersProperties(String aFilename) throws IOException {
	        PrintWriter out = new PrintWriter(new FileWriter(aFilename));
	        try {
	            List<SecUser> users = theManager.createQuery("from SecUser where disable is null or cast(disable as integer)=0").getResultList();
	            for (SecUser user : users) {
	                out.print("#") ;
	                out.println(user.getFullname()) ;
	                out.print(user.getLogin()) ;
	                out.print("=") ;
	                out.println(user.getPassword()) ;
	            }
	        } finally {
	            out.close() ;
	        }
	    }

	    public void exportRolesProperties() throws IOException {
	        exportRolesProperties(Config.getConfigDir()+"/roles.properties");
	    }

	    public void exportRolesProperties(String aFilename) throws IOException {
	        PrintWriter out = new PrintWriter(new FileWriter(aFilename));
	        System.out.println("---Begin") ;
	        Map<SecPolicy, String> hash = new HashMap<SecPolicy,String>() ;
	        try {
	            List<SecUser> users = theManager.createQuery("from SecUser where disable is null or cast(disable as integer)=0").getResultList();
	            for (SecUser user : users) {
	                out.print("# ") ;
	                out.println(user.getFullname()) ;

	                out.println("#  РОЛИ:") ;
	                 String rolesIds = getRolesByUser(user.getId()) ;
	                 out.println("#  ids="+rolesIds) ;
	                 if (rolesIds.length()>0) {
	                	 
	                     List <SecRole> roles = theManager.createQuery("from SecRole where id in ("+rolesIds+")").getResultList() ;
	                     for (SecRole role : roles) {
	                         out.print("#   ") ;
	                         out.println(role.getName()) ;
	                     }
	                     out.print(user.getLogin()) ;
	                     out.print("=") ;
	                     out.println(createPoliciesString(user,rolesIds,hash)) ;
	                 }
	            }
	        } finally {
	            out.close() ;
	        }
	        System.out.println("---End") ;
	    }
	    private String getRolesByUser(Long aUserId) {
	    	StringBuilder sql = new StringBuilder() ;
	    	sql.append("select list(roles_id),count(*) from SecUser_SecRole where secUser_id=:user group by secUser_id") ;
	    	List<Object[]> list = theManager.createNativeQuery(sql.toString()).setParameter("user", aUserId).getResultList() ;
	    	//return convertToString(getAllRole(list)) ;
	    	Object[] objs = list.size()>0?list.get(0):null ;
	    	return objs!=null?getAllRole(String.valueOf(objs[0])):"" ;
	    }
	    private String getAllRole(String aRoles) {
	    	int size_old = aRoles.length() ;
	    	if (size_old!=0) {
	        	//String ids = convertToString(aRoles) ;
	    		//String ids = aRoles ;
	        	StringBuilder sql = new StringBuilder() ;
	        	//boolean children = false ;
	        	//sql.append("select sr.id from SecRole sr where sr.id in (").append(ids).append(")") 
	        	//	.append("or sr.id in (select rr.children_id from SecRole_SecRole rr where rr.SecRole_id in (")
	        	//	.append(ids).append("))");
	        	sql.append("select list(rr.children_id),count(*) from SecRole_SecRole rr where rr.SecRole_id in (")
	    			.append(aRoles).append(") and rr.children_id not in (")
	    	    			.append(aRoles).append(")");
	        	List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
	        	Object[] obj = list.size()>0?list.get(0):null ;
	        	if (obj[0]!=null) {
	        		StringBuilder ids = new StringBuilder() ;
	        		aRoles = ids.append(aRoles).append(",").append(obj[0]).toString() ;
	        		return getAllRole(aRoles) ;
	        	}
	        	/*
	        	for (Object obj:list) {
	        		System.out.println("children="+obj) ;
	        		if (obj!=null && !aRoles.contains(obj)) {
	        			System.out.print(" adding") ;
	        			aRoles.add(obj) ;
	        			children=true ;
	        		}
	        	}*/
	        	
	    		//if (children) 
	    	}
	    	return aRoles ;
	    }
	    private String convertToString1(List<Object> aList) {
	    	StringBuilder ret = new StringBuilder() ;
	    	for (Object id:aList) {
	    		ret.append(",").append(id) ;
	    	}
	    	return ret.length()>1?ret.substring(1):"" ;
	    }

	    private String createPoliciesString(SecUser aUser, String aRolesId, Map<SecPolicy,String> aPoliciesHash) {
	        TreeSet<String> policies = new TreeSet<String>();
	        List <SecRole> roles = theManager.createQuery("from SecRole where id in ("+aRolesId+")").getResultList() ;
	        //List<Object> listPolicies = theManager.createNativeQuery("select sp.id from SecPolicy as sp where sp.id in (select rp.secPolicies_id from SecRole_SecPolicy rp where rp.SecRole_id in ("+aRolesId+")) group by sp.id").getResultList() ;
	        for (SecRole role: roles) {
	        	for(SecPolicy policy:role.getSecPolicies()) {
	        		policies.add(getPolicyFullKey(policy,aPoliciesHash)) ;
	        	}
	        }
	        /*for (Object pol_id : listPolicies) {
	            	SecPolicy policy = theManager.find(SecPolicy.class, ConvertSql.parseLong(pol_id)) ;
	                policies.add(getPolicyFullKey(policy)) ;
	            }
	        */
	        StringBuilder sb = new StringBuilder();
	        for (String key : policies) {
	            sb.append(key) ;
	            sb.append(",") ;
	        }
	        sb.append("PolicyAllData,PolicyServiceAll") ;
	        return sb.toString();
	    }

	    public String getPolicyFullKey(SecPolicy aPolicy,Map<SecPolicy,String> aPoliciesHash) {
	        StringBuilder sb = new StringBuilder();
	        if(aPoliciesHash.containsKey(aPolicy)) {
	    		return aPoliciesHash.get(aPolicy);
	    	}
	        while(aPolicy!=null) {
	            sb.insert(0, '/') ;
	            sb.insert(0, aPolicy.getKey()) ;
	            aPolicy = aPolicy.getParentSecPolicy();
	        }
	        String policy = sb.substring(1,sb.length()-1) ;
	        aPoliciesHash.put(aPolicy, policy);
	        return policy;
	    }

	    public Collection<SecRoleForm> listRolesToAdd(long aUserId) {
	        SecUser user = theManager.find(SecUser.class, aUserId) ;
	        List<SecRole> userRoles = user.getRoles();
	        List<SecRole> allRoles = theManager.createQuery("from SecRole").getResultList();
	        for (SecRole role : userRoles) {
	            allRoles.remove(role) ;
	        }
	        return convert(allRoles) ;
	    }

	    private static Collection<SecRoleForm> convert(Collection<SecRole> aFrom) {
	        LinkedList<SecRoleForm> ret = new LinkedList<SecRoleForm>();
	        for (SecRole role : aFrom) {
	            SecRoleForm form = new SecRoleForm();
	            form.setId(role.getId());
	            form.setName(role.getName());
	            form.setComment(role.getComment());
	            ret.add(form) ;
	        }
	        return ret ;
	    }

	    public Collection<SecRoleForm> listUserRoles(long aUserId) {
	        SecUser user = theManager.find(SecUser.class, aUserId) ;
	        List<SecRole> roles = user.getRoles();
	        return convert(roles) ;
	    }

	    public void addRoles(long aUserId, long[] aRoles) {
	        SecUser user = theManager.find(SecUser.class, aUserId) ;
	        List<SecRole> roles = user.getRoles();
	        for (long roleId : aRoles) {
	            roles.add(theManager.find(SecRole.class, roleId)) ;
	        }
	        theManager.persist(user);
	    }

	    public void removeRoles(long aUserId, long[] aRoles) {
	        SecUser user = theManager.find(SecUser.class, aUserId) ;
	        List<SecRole> roles = user.getRoles();
	        for (long roleId : aRoles) {
	            roles.remove(theManager.find(SecRole.class, roleId)) ;
	        }
	        theManager.persist(user);
	    }

	    @PersistenceContext private EntityManager theManager ;

	}


