package ru.ecom.jaas.ejb.service;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.entityform.PersistList;
import ru.ecom.ejb.services.live.domain.journal.ChangeJournal;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.domain.SecRole;
import ru.ecom.jaas.ejb.form.SecUserForm;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Роли
 */
@Stateless
@Remote(ISecRoleService.class)
public class SecRoleServiceBean implements ISecRoleService {

    private static final Logger LOG = Logger.getLogger(SecRoleServiceBean.class);

    public String getRoleInfo(long aRoleId) {
    	SecRole role = theManager.find(SecRole.class, aRoleId) ;
    	return role!=null? role.getName() : "" ;
    }

	public List<SecUserForm>listUsersByRole(long aRoleId, boolean aIsSystemView) {
	//	SecRole role = theManager.find(SecRole.class, aRoleId) ;
		String add="" ;
		if (!aIsSystemView) add=" and (su.isSystems is null or su.isSystems='0') " ;
        List<Object[]> roles = theManager.createNativeQuery("select su.id,su.fullname,su.login,su.comment from SecUser_SecRole as susr"
        	+" inner join SecUser as su on su.id=secuser_id"
        	+" where susr.roles_id=:idRole and (su.disabled is null or su.disabled='0')"+add+" order by su.login").setParameter("idRole", aRoleId).getResultList() ;
		return convert(roles) ;
	}
	
	public List<SecUserForm> listUsersToAdd(long aRoleId, boolean aIsSystemView) {
		String add="" ;
		if (!aIsSystemView) add=" and (su.isSystems is null or su.isSystems='0') " ;
		List<Object[]> roles = theManager.createNativeQuery("select su.id,su.fullname,su.login,su.comment from SecUser as su where (su.disabled is null or su.disabled='0')"+add+" and (select count(*) from SecUser_SecRole as susr where susr.roles_id=:idRole and susr.secUser_id=su.id)=0  order by su.login")
			.setParameter("idRole", aRoleId).getResultList() ;
		return convert(roles) ;		
	}
	
	public void removeUsersFromRole(long aRoleId, long[] aUsersId) {
		String ids =convertArrayToString(aUsersId);
		java.util.Date date = new java.util.Date() ;
		String username = theContext.getCallerPrincipal().getName() ;
		theManager.createNativeQuery("delete from SecUser_SecRole where roles_id=:idRole and secuser_id in ("+ids+")")
			.setParameter("idRole", aRoleId)
			//.setParameter("idUsers", ids)
			.executeUpdate() ;
		for (long user:aUsersId) {
	        ChangeJournal jour = new ChangeJournal() ;
			jour.setClassName("SecUser_SecRole") ;
			
			jour.setChangeDate(new java.sql.Date(date.getTime())) ;
			jour.setChangeTime(new java.sql.Time(date.getTime())) ;
			jour.setLoginName(username) ;
			jour.setComment(" remove role userid="+user+" roleid="+aRoleId) ;
			jour.setSerializationAfter("user:"+user) ;
			jour.setSerializationBefore("role:"+aRoleId) ;
			theManager.persist(jour) ;
		}
	}
	public void addUsersToRole(long aRoleId, long[] aUsersId) {
		//LOG.info("ids="+convertArrayToString(aUsersId)) ;
		java.util.Date date = new java.util.Date() ;
		String username = theContext.getCallerPrincipal().getName() ;
		for (long user:aUsersId) {
			Object check = theManager.createNativeQuery("select count(*) from SecUser_SecRole where roles_id=:idRole and secuser_id=:idUser")
			.setParameter("idRole", aRoleId)
			.setParameter("idUser", user)
			.getSingleResult() ;
			Long ch = PersistList.parseLong(check) ;
			
			if (ch.intValue()==0) {
				int result = theManager.createNativeQuery("insert into SecUser_SecRole (roles_id,secuser_id) values (:idRole,:idUser)")
					.setParameter("idRole", aRoleId)
					.setParameter("idUser", user)
					.executeUpdate() ;
				ChangeJournal jour = new ChangeJournal() ;
				jour.setClassName("SecUser_SecRole") ;
				
				jour.setChangeDate(new java.sql.Date(date.getTime())) ;
				jour.setChangeTime(new java.sql.Time(date.getTime())) ;
				jour.setLoginName(username) ;
				jour.setComment(" add role userid="+user+" roleid="+aRoleId) ;
				jour.setSerializationAfter("user:"+user) ;
				jour.setSerializationBefore("role:"+aRoleId) ;
				theManager.persist(jour) ;
			}
		}
		
	}
	private String convertArrayToString(long[] aArray) {
		StringBuilder ret = new StringBuilder() ;
		for (long id:aArray) {
			ret.append(",").append(id) ;
		}
		return ret.substring(1) ;
	}
	
	private static List<SecUserForm> convert(List<Object[]> aFrom) {
        LinkedList<SecUserForm> ret = new LinkedList<>();
        for (Object[] user : aFrom) {
            SecUserForm form = new SecUserForm();
            form.setId(ConvertSql.parseLong(user[0]));
            form.setFullname(String.valueOf(user[1]));
            form.setLogin(String.valueOf(user[2]));
            form.setComment(String.valueOf(user[3]));
            ret.add(form) ;
        }
        return ret ;
    }

    public void saveRolePolicies(long aRoleId, long[] aAdded, long[] aRemoved) {
        SecRole role = theManager.find(SecRole.class, aRoleId) ;
        Collection<SecPolicy> policies = role.getSecPolicies() ;
        for (long idPolicy : aAdded) {
            SecPolicy policy = theManager.find(SecPolicy.class, idPolicy) ;
            if(policy!=null && !policies.contains(policy)) {
               LOG.info("idPolicy = " + idPolicy);
                policies.add(policy) ;
            }
        }
        for (long idPolicy : aRemoved) {
            SecPolicy policy = theManager.find(SecPolicy.class, idPolicy) ;
            if(policy!=null) {
                policies.remove(policy) ;
            }
        }
        role.setSecPolicies(policies) ;
        theManager.persist(role);
    }

    public CheckNode loadPoliciesByRole(long aRoleId) {
    	TreeSet<Long> policiesSet  = new TreeSet<>();
    	SecRole role = theManager.find(SecRole.class, aRoleId) ;
    	for (SecPolicy policy : role.getSecPolicies()) {
    		policiesSet.add(policy.getId()) ;
    	}
    	
    	SecPolicy rootPolicy = findRootPolicy() ; //theManager.find(SecPolicy.class, SecPolicy.ROOT_POLICY_ID)  ;
    	CheckNode rootNode = new CheckNode(1, "/", false);
    	add(rootPolicy, rootNode, policiesSet);
    	return rootNode;
    }
    // Загрузка дерева политик
    public CheckNode loadPolicies() {
        TreeSet<Long> policiesSet  = new TreeSet<>();
        SecPolicy rootPolicy = findRootPolicy() ;
        CheckNode rootNode = new CheckNode(1, "/", false);
        add(rootPolicy, rootNode, policiesSet);
        return rootNode;
    }

    private SecPolicy findRootPolicy() {
    	try {
        	return  (SecPolicy) theManager.createQuery("from SecPolicy where parentSecPolicy is null").getSingleResult() ;
    	} catch(NonUniqueResultException | org.hibernate.NonUniqueResultException e) {
    		throw new IllegalStateException("Должна быть одна головная политика (SecPolicy, где parentSecPolicy is null)")   ;
    	}
    }
    private void add(SecPolicy aPolicy, CheckNode aNode, TreeSet<Long> aPoliciesSet) {
    	
        for (SecPolicy policy : aPolicy.getChildsSecPolicies()) {
            CheckNode node = new CheckNode(policy.getId()
                    , policy.getName() + " (" + policy.getKey() + ")"
                    , aPoliciesSet.contains(policy.getId()));
            aNode.getChilds().add(node) ;
            add(policy, node, aPoliciesSet);
        }
    }

    @PersistenceContext
    private EntityManager theManager ;
    @Resource SessionContext theContext ;
}
