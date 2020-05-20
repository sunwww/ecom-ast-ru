package ru.ecom.jaas.ejb.service;

import org.apache.log4j.Logger;
import org.jboss.mx.util.MBeanServerLocator;
import ru.ecom.ejb.services.entityform.PersistList;
import ru.ecom.ejb.services.live.domain.journal.ChangeJournal;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.domain.SecRole;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.ecom.jaas.ejb.form.SecRoleForm;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.management.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
@Stateless
@Remote(ISecUserService.class )
public class SecUserServiceBean implements ISecUserService {
	private static final Logger LOG = Logger.getLogger(SecUserServiceBean.class);

	private void exportPropertiesOtherServer(String aFilename) {
		try {
		String sb = "/home/scripts/upd_props.sh "+aFilename;
		LOG.info("==== exportPropertiesOtherServer sb="+sb);
		Runtime.getRuntime().exec(sb);
		} catch (Exception e) {
			LOG.error(e);
		}
	}
	
	public void setDefaultPassword(String aNewPassword, String aUsername, String aUsernameChange) throws IOException {
		String hashPassword = getHashPassword(aUsername, aNewPassword);

		theManager.createNativeQuery("update secuser set password =:password, editDate=current_date,editTime=current_time,editUsername=:editUsername, changePasswordAtLogin='1' where login = :login")
				.setParameter("editUsername",aUsernameChange).setParameter("password",hashPassword).setParameter("login",aUsername)
				.executeUpdate();
		exportUsersProperties();
	}
	public String changePassword(String aNewPassword, String aOldPassword, String aUsername) throws IOException {
		if (aOldPassword.equals(aNewPassword)){
			return "0Новый пароль ничем не отличается от старого";
		}
		String oldHash = getHashPassword(aUsername, aOldPassword);
		List<Object> lo  = theManager.createNativeQuery("select id from secuser where login =:username and password = :password")
				.setParameter("username",aUsername).setParameter("password",oldHash)
				.getResultList();
		if (lo.isEmpty()) {
			return "0Старый пароль указан неверно";
		}
		String isGood = ""+lo.get(0).toString();
		if (isGood.equals("")) {
			return "0Старый пароль указан неверно";			
		}
		List<Object[]> l = theManager.createNativeQuery("select sc.KeyValue, sc.description from SoftConfig sc where sc.key='PASSWORDREGEXP'").getResultList() ;
		if (!l.isEmpty()) {
			String regexp = l.get(0)[0].toString();
			Pattern p = Pattern.compile(regexp);
			Matcher m = p.matcher(aNewPassword);
			if (!m.matches()) {
				return "0Пароль не удоволетворяет требованиям безопасности!\n"+l.get(0)[1].toString();
			}
		}
		String hashPassword  = getHashPassword(aUsername, aNewPassword);
		theManager.createNativeQuery("update secuser set password =:hash, passwordChangedDate=current_date, changePasswordAtLogin='0' where login =:username")
				.setParameter("username",aUsername).setParameter("hash",hashPassword)
				.executeUpdate();
		exportUsersProperties();
		
		return "1Пароль успешно обновлен";
	}
    public void fhushJboss() throws ReflectionException, InstanceNotFoundException, MBeanException, MalformedObjectNameException {
        MBeanServer SERVER = MBeanServerLocator.locateJBoss();
        String[] signature = {"java.lang.String"};
     	SERVER.invoke(new ObjectName("jboss.security:service=JaasSecurityManager"), "flushAuthenticationCache", new String[]{"other"}, signature) ;

    }

    public void exportUsersProperties() throws IOException {
        exportUsersProperties(Config.getConfigDir()+"/users.properties");
    }

    public static String getHashPassword(String aUsername, String aPassword) { //именно паблик!
    	String hash = String.valueOf(aPassword.hashCode() + aUsername.hashCode()) ;
    	return "F" + hash;
    }
    
    public void exportUsersProperties(String aFilename) throws IOException {

        try (PrintWriter out = new PrintWriter(new FileWriter(aFilename))){
            List<SecUser> users = theManager.createQuery("from SecUser where disable is null or cast(disable as integer)=0").getResultList();
            for (SecUser user : users) {
            	if (user.getIsHash()==null || !user.getIsHash()) {
            		user.setPassword(getHashPassword(user.getLogin(), user.getPassword())) ;
            		user.setIsHash(true) ;
            	}
                out.print("#") ;
                out.println(user.getFullname()) ;
                out.print(user.getLogin()) ;
                out.print("=") ;
                out.println(user.getPassword().substring(1)) ;
            }
        }
        exportPropertiesOtherServer(aFilename);
        try {
			fhushJboss();
		} catch (Exception e) {
			LOG.error("=== Jboss Flush Exception:"+e.getMessage(),e);
			e.printStackTrace();
		} 
    }

    public void exportRolesProperties() throws IOException {
        exportRolesProperties(Config.getConfigDir()+"/roles.properties");
    }

    public void exportRolesProperties(String aFilename) {

        LOG.info("---Begin ExportRoles");
        Map<SecPolicy, String> hash = new HashMap<>() ;
        try ( PrintWriter out = new PrintWriter(new FileWriter(aFilename))) {
            List<SecUser> users = theManager.createQuery("from SecUser where disable is null or cast(disable as integer)=0").getResultList();
            for (SecUser user : users) {
                out.print("# ") ;
                out.println(user.getFullname()) ;
                out.print(user.getLogin()) ;
                out.print("=") ;
                 //String rolesIds = getRolesByUser(user.getId()) ;
                List<Long> listRole = new ArrayList<>() ;
                for (SecRole role : user.getRoles()) {
                	Long idP = role.getId() ;
                	if (!listRole.contains(idP)) {
                	//	log("Добавление..") ;
                		listRole.add(idP) ;
                		out.print(createPoliciesString(user,role,hash)) ;
                        for (SecRole childRole: role.getChildren()) {
                        	Long idC = childRole.getId() ;
                        	if (!listRole.contains(idC)) {
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
        	LOG.error(e);
        }
    	//return aRoles ;
        exportPropertiesOtherServer(aFilename);
    }

    private String createPoliciesString(SecUser aUser, SecRole aRole, Map<SecPolicy,String> aPoliciesHash) {
        TreeSet<String> policies = new TreeSet<>();
        	for(SecPolicy policy:aRole.getSecPolicies()) {
        		policies.add(getPolicyFullKey(policy,aPoliciesHash)) ;
        	}
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

    public Collection<SecRoleForm> listRolesToAdd(long aUserId, boolean aIsSystemView) {
        SecUser user = theManager.find(SecUser.class, aUserId) ;
        List<SecRole> userRoles = user.getRoles();
        String add="" ;
        if (!aIsSystemView) {
        	add="where (isSystems='0' or isSystems is null)" ;
        }
        List<SecRole> allRoles = theManager.createQuery("from SecRole "+add).getResultList();
        for (SecRole role : userRoles) {
        	allRoles.remove(role) ;
        }
        return convert(allRoles, aIsSystemView) ;
    }

    private static Collection<SecRoleForm> convert(Collection<SecRole> aForm, boolean aIsSystemView) {
        LinkedList<SecRoleForm> ret = new LinkedList<>();
        for (SecRole role : aForm) {
        	if (aIsSystemView || (role.getIsSystems()==null || !role.getIsSystems())) {
	            SecRoleForm form = new SecRoleForm();
	            form.setId(role.getId());
	            form.setName(role.getName());
	            form.setComment(role.getComment());
	            ret.add(form) ;
        	}
        }
        return ret ;
    }

    public Collection<SecRoleForm> listUserRoles(long aUserId, boolean aIsSystemView) {
        SecUser user = theManager.find(SecUser.class, aUserId) ;
        List<SecRole> roles = user.getRoles();
        return convert(roles,  aIsSystemView) ;
    }

    public void addRoles(long aUserId, long[] aRoles) {
    	for (long role:aRoles) {
			Object check = theManager.createNativeQuery("select count(*) from SecUser_SecRole where roles_id=:idRole and secuser_id=:idUser")
			.setParameter("idRole", role)
			.setParameter("idUser", aUserId)
			.getSingleResult() ;
			Long ch = PersistList.parseLong(check) ;
			java.util.Date date = new java.util.Date() ;
			String username = theContext.getCallerPrincipal().getName() ;
			if (ch.intValue()==0) {
				theManager.createNativeQuery("insert into SecUser_SecRole (roles_id,secuser_id) values (:idRole,:idUser)")
					.setParameter("idRole", role)
					.setParameter("idUser", aUserId)
					.executeUpdate() ;
				ChangeJournal jour = new ChangeJournal() ;
				jour.setClassName("SecUser_SecRole") ;
				
				jour.setChangeDate(new java.sql.Date(date.getTime())) ;
				jour.setChangeTime(new java.sql.Time(date.getTime())) ;
				jour.setLoginName(username) ;
				jour.setComment(" add user userid="+aUserId+" roleid="+role) ;
				jour.setSerializationAfter("user:"+aUserId) ;
				jour.setSerializationBefore("role:"+role) ;
				theManager.persist(jour) ;
			}
		}
    }

    public void removeRoles(long aUserId, long[] aRoles) {
        SecUser user = theManager.find(SecUser.class, aUserId) ;
        List<SecRole> roles = user.getRoles();
        java.util.Date date = new java.util.Date() ;
		String username = theContext.getCallerPrincipal().getName() ;
        for (long roleId : aRoles) {
            roles.remove(theManager.find(SecRole.class, roleId)) ;
            ChangeJournal jour = new ChangeJournal() ;
			jour.setClassName("SecUser_SecRole") ;
			
			jour.setChangeDate(new java.sql.Date(date.getTime())) ;
			jour.setChangeTime(new java.sql.Time(date.getTime())) ;
			jour.setLoginName(username) ;
			jour.setComment(" remove user userid="+aUserId+" roleid="+roleId) ;
			jour.setSerializationAfter("user:"+aUserId) ;
			jour.setSerializationBefore("role:"+roleId) ;
			theManager.persist(jour) ;
        }
        theManager.persist(user);
    }


	/**
	 * Получить персону по работнику
	 * @param aUserId Long Пользователь
	 * @return Patient.id
	 */
	private Long getPersonByWorker(Long aUserId) {
		try {
			Object obPersonId = theManager.createNativeQuery("select w.person_id" +
					" from SecUser su" +
					" left join WorkFunction wf on su.id=wf.secUSer_id" +
					" left join Worker w on wf.worker_id=w.id" +
					" where su.id=:userId")
					.setParameter("userId", aUserId)
					.getSingleResult();

			Long personId = PersistList.parseLong(obPersonId);
			return personId;
		}
		catch(javax.persistence.NoResultException e) {
			throw new IllegalArgumentException("Не найдена персона у пользователя!");
		}
	}

	/**
	 * Проверить, существует ли работник с такими параметрами
	 * @param aPatId Long Персона
	 * @param aLpuId Long Госпиталь
	 * @return  Worker.id or OL (если не существует)
	 */
	private Worker getIfWorkerExists(Long aPatId, Long aLpuId) {
		List<Object> list = theManager.createQuery("from Worker where" +
				" person_id=:person" +
				" and lpu_id = :lpu")
				.setParameter("person", aPatId)
				.setParameter("lpu", aLpuId)
				.getResultList();
		return !list.isEmpty()?
				(Worker)list.get(0)
				: null;
	}

	/**
	 * Проверить, существует ли раб. функция с такими параметрами
	 * @param aWorkerId Long Работник
	 * @param avWfId Long Должность
	 * @return  WorkFunction.id or OL (если не существует)
	 */
	private Long getIfWorkFunctionExists(Long aWorkerId, Long avWfId) {
		List<Object> list = theManager.createNativeQuery("select wf.id" +
				" from SecUser su" +
				" left join WorkFunction wf on su.id=wf.secUser_id" +
				" left join Worker w on wf.worker_id=w.id" +
				" left join VocWorkFunction vwf on vwf.id=wf.workfunction_id" +
				" where w.id=:wId and vwf.id=:vwfId")
				.setParameter("wId", aWorkerId)
				.setParameter("vwfId", avWfId)
				.getResultList();
		return !list.isEmpty() && !list.get(0).equals(0)?
				Long.valueOf(list.get(0).toString())
				: 0L;
	}


	/**
	 * Добавить пользователя в отделение с должностью
	 * @param aUserId Long Пользователь
	 * @param aLpuId Long Госпиталь
	 * @param avWfId Long VocWorkFunction
     * @param newPsw String Пароль
	 * @return Сообщение
	 */
	public String addUserToCovidHosp(Long aUserId, Long aLpuId, Long avWfId, String newPsw) throws IOException {
        SecUser secUser = theManager.find(SecUser.class, aUserId);
        java.util.Date date = new java.util.Date();
        java.sql.Date dd = new java.sql.Date(date.getTime());
        java.sql.Time tt = new java.sql.Time(date.getTime());
        String username = theContext.getCallerPrincipal().getName();
		String msgResult = "";
		if (aLpuId!=0L && avWfId!=0L) {
            MisLpu misLpu = theManager.find(MisLpu.class, aLpuId);
            if (secUser != null && misLpu != null) {
                Long patId = getPersonByWorker(aUserId);
                Worker w = getIfWorkerExists(patId, aLpuId);
                Long wId = 0L;
                if (w == null) {
                    w = new Worker();
                    w.setLpu(misLpu);
                    w.setPerson(theManager.find(Patient.class, patId));
                    w.setCreateDate(dd);
                    w.setCreateTime(tt);
                    w.setCreateUsername(username);
                    theManager.persist(w);
                    wId = w.getId();
                    msgResult += "Создан новый сотрудник<br>";
                } else {
                    wId = w.getId();
                    w = theManager.find(Worker.class, wId);
                    msgResult += "Сотрудник с заданными параметрами (персона, ЛПУ) уже существует<br>";
                }
                Long wfId = getIfWorkFunctionExists(wId, avWfId);
                if (wfId == 0L) {
                    PersonalWorkFunction pwf = new PersonalWorkFunction();
                    pwf.setWorkFunction(theManager.find(VocWorkFunction.class, avWfId));
                    pwf.setWorker(w);
                    pwf.setRegistrationInterval(0);
                    pwf.setCreateDate(dd);
                    pwf.setCreateTime(tt);
                    pwf.setCreateUsername(username);
                    theManager.persist(pwf);
                    msgResult += "Создана новая рабочая функция<br>";
                } else
                    msgResult += "Рабочая функция с заданными параметрами (работник, должность) уже существует<br>";
            } else
                msgResult += "Неверные параметры!";
        }
		if (!newPsw.equals("")) {
            //установка пароля
            setDefaultPassword("1", secUser.getLogin(), username);
            String res = changePassword(newPsw, "1", secUser.getLogin());
            if (res.startsWith("1")) {
				secUser.setEditDate(dd);
				secUser.setPasswordChangedDate(dd);
				secUser.setEditUsername(username);
				secUser.setEditTime(tt);
				msgResult += "Пароль успешно установлен.";
			}
            else
            	msgResult += res.substring(1);
        }
		return msgResult;
	}

    @PersistenceContext private EntityManager theManager ;
    @Resource SessionContext theContext ;
}