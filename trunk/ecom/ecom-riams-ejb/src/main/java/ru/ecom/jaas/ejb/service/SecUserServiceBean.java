package ru.ecom.jaas.ejb.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

import ru.ecom.ejb.services.entityform.PersistList;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.domain.SecRole;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.ecom.jaas.ejb.form.SecRoleForm;

/**
 *
 */
@Stateless
@Remote(ISecUserService.class )
public class SecUserServiceBean implements ISecUserService {

    public void fhushJboss() throws ReflectionException, InstanceNotFoundException, MBeanException, MalformedObjectNameException {
        MBeanServer SERVER = MBeanServerLocator.locateJBoss();
        String[] signature = {"java.lang.String"};
        Object retVal  = SERVER.invoke(new ObjectName("jboss.security:service=JaasSecurityManager")
        , "flushAuthenticationCache", new String[]{"other"}, signature) ;

    }

    public void exportUsersProperties() throws IOException {
        exportUsersProperties(Config.getConfigDir()+"/users.properties");
    }
    private void log(Object obj) {
    	System.out.println(obj) ;
    }

    public static String getHashPassword(String aUsername, String aPassword) {
    	String hash = String.valueOf(aPassword.hashCode() + aUsername.hashCode()) ;
    	System.out.println(hash) ;
    	return new StringBuilder().append("F").append(hash).toString();
    }
    
    public void exportUsersProperties(String aFilename) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(aFilename));
        try {
            List<SecUser> users = theManager.createQuery("from SecUser where disable is null or cast(disable as integer)=0").getResultList();
            for (SecUser user : users) {
            	if (user.getIsHash()==null || user.getIsHash()==false) {
            		user.setPassword(getHashPassword(user.getLogin(), user.getPassword())) ;
            		user.setIsHash(true) ;
            	}
            	log(user.getFullname()) ;
                out.print("#") ;
                out.println(user.getFullname()) ;
                out.print(user.getLogin()) ;
                out.print("=") ;
                out.println(user.getPassword().substring(1)) ;
            }
        } finally {
        	if(out!=null) out.close() ;
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
            	//out.print("#   ") ;
                //out.println(role.getName()) ;
                out.print(user.getLogin()) ;
                out.print("=") ;

                //out.println("#  РОЛИ:") ;
                 //String rolesIds = getRolesByUser(user.getId()) ;
                 //out.println("#  ids="+rolesIds) ;
                List<Long> listRole = new ArrayList<Long>() ;
                for (SecRole role : user.getRoles()) {
                	Long idP = role.getId() ;
                	log(idP) ;
                	if (listRole.contains(idP)) {
                		
                	} else {
                		log("Добавление..") ;
                		listRole.add(idP) ;
                		out.print(createPoliciesString(user,role,hash)) ;
                		
                        for (SecRole childRole: role.getChildren()) {
                        	Long idC = childRole.getId() ;
                        	log("...child="+idC) ;
                        	if (listRole.contains(idC)) {
                        		
                        	} else {
                        		log("....Добавление..") ;
                        		listRole.add(idC) ;
                        		out.print(createPoliciesString(user,childRole,hash)) ;
                        	}
                        }
                        
                	}
                	
                } 
                out.println("PolicyAllData,PolicyServiceAll") ;
                listRole.clear() ;
            }
        }catch(Exception e) {
        	
        } finally {
        	if(out!=null) out.close() ;
        }
        
    	//return aRoles ;
    }

    private String createPoliciesString(SecUser aUser, SecRole aRole, Map<SecPolicy,String> aPoliciesHash) {
        TreeSet<String> policies = new TreeSet<String>();
       // List <SecRole> roles = theManager.createQuery("from SecRole where id in ("+aRolesId+")").getResultList() ;
        //List<Object> listPolicies = theManager.createNativeQuery("select sp.id from SecPolicy as sp where sp.id in (select rp.secPolicies_id from SecRole_SecPolicy rp where rp.SecRole_id in ("+aRolesId+")) group by sp.id").getResultList() ;
        //for (SecRole role: roles) {
        	for(SecPolicy policy:aRole.getSecPolicies()) {
        		policies.add(getPolicyFullKey(policy,aPoliciesHash)) ;
        	}
        //}
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
        /*SecUser user = theManager.find(SecUser.class, aUserId) ;
        List<SecRole> roles = user.getRoles();
        for (long roleId : aRoles) {
            roles.add(theManager.find(SecRole.class, roleId)) ;
        }
        theManager.persist(user);
        */
    	for (long role:aRoles) {
			Object check = theManager.createNativeQuery("select count(*) from SecUser_SecRole where roles_id=:idRole and secuser_id=:idUser")
			.setParameter("idRole", role)
			.setParameter("idUser", aUserId)
			.getSingleResult() ;
			Long ch = PersistList.parseLong(check) ;
			if (ch.intValue()==0) {
				theManager.createNativeQuery("insert into SecUser_SecRole (roles_id,secuser_id) values (:idRole,:idUser)")
					.setParameter("idRole", role)
					.setParameter("idUser", aUserId)
					.executeUpdate() ;
				//LOG.info("result for insert:"+result) ;
			}
		}
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
