package ru.ecom.ejb.services.login;

import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;
import org.jboss.mx.util.MBeanServerLocator;
import ru.ecom.ejb.services.live.domain.CustomMessage;
import ru.ecom.ejb.services.live.domain.journal.AuthenticationJournal;
import ru.ecom.ejb.services.util.JBossConfigUtil;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileInputStream;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 */
@Stateless
@SecurityDomain("other")
@Remote(ILoginService.class)
public class LoginServiceBean implements ILoginService {

    private static final Logger LOG = Logger.getLogger(LoginServiceBean.class) ;

    public String[] getConfigUrl() {
    	String ret = null ;
    	String ret1 = null ;
    	List<Object> l = theManager.createNativeQuery("select sf.KeyValue from SoftConfig sf where sf.key='config_url_main'").getResultList() ;
    	if (!l.isEmpty()) {ret = ""+l.get(0);}
    	List<Object> l1 = theManager.createNativeQuery("select sf.KeyValue from SoftConfig sf where sf.key='config_url_report'").getResultList() ;
    	if (!l1.isEmpty()) {ret1 = ""+l1.get(0);}
    	String[] rets = new String[2] ;
    	rets[0]=ret ;rets[1]=ret1 ;
    	return rets ;

    }

    @PermitAll
    public Set getUserRoles() {
        String user = theContext.getCallerPrincipal().getName();
        Properties prop = new Properties();
        HashSet<String> ret = new HashSet<>();
        try (FileInputStream inputStream = new FileInputStream(JBossConfigUtil.getConfigDirname()+"/roles.properties")) {
            try {
                MBeanServer SERVER = MBeanServerLocator.locateJBoss();
                String[] signature = {"java.lang.String"};
                Object retVal  = SERVER.invoke(new ObjectName("jboss.security:service=JaasSecurityManager")
                , "flushAuthenticationCache", new String[]{"other"}, signature) ;
            } catch (Exception e) {
                LOG.warn(e.getMessage()) ;
            }

            prop.load(inputStream) ;
            StringTokenizer st = new StringTokenizer(prop.getProperty(user), ", ");
            while(st.hasMoreTokens()) {
                ret.add(st.nextToken()) ;
            }
        } catch (Exception e) {
            throw new IllegalStateException("Ошибка загрузки ролей пользователя "+user,e) ;
        }
        return ret ;
    }
    
    @PermitAll
    public void createRecordInAuthJournal(String aUsername, String aRemoteAdd, String aLocalAdd
    		,String aServerName,boolean aIsChecked,String aError,String aErrorPassword) {
    	AuthenticationJournal authJour = new AuthenticationJournal() ;
    	long dat = new java.util.Date().getTime() ;
    	authJour.setAuthDate(new java.sql.Date(dat)) ;
    	authJour.setAuthTime(new java.sql.Time(dat)) ;
    	authJour.setIsChecked(aIsChecked) ;
    	authJour.setUsername(aUsername) ;
    	authJour.setRemoteAdd(aRemoteAdd) ;
    	authJour.setLocalAdd(aLocalAdd) ;
    	authJour.setServerName(aServerName) ;
    	authJour.setErrorMessage(aError) ;
    	authJour.setErrorPassword(aErrorPassword);
    	theManager.persist(authJour) ;
    }

    public Long createSystemMessage(String aTitle, String aText, String aRecipient) {
    	CustomMessage mes = new CustomMessage() ;
    	mes.setMessageTitle(aTitle) ;
    	mes.setMessageText(aText) ;
    	mes.setUsername("system_message") ;
    	long date = new java.util.Date().getTime() ;
    	mes.setDateReceipt(new Date(date)) ;
    	mes.setTimeReceipt(new Time(date)) ;
    	mes.setDispatchDate(new Date(date)) ;
    	mes.setDispatchTime(new Time(date)) ;
    	mes.setRecipient(aRecipient) ;
    	mes.setIsSystem(true) ;
    	mes.setIsEmergency(false) ;
    	theManager.persist(mes) ;
    	return mes.getId() ;
    }
    public void dispatchMessage(Long aIdMessage) {
    	CustomMessage mes = theManager.find(CustomMessage.class, aIdMessage) ;
    	if (mes==null) return;
    	long date = new java.util.Date().getTime() ;
    	mes.setDateReceipt(new Date(date)) ;
    	mes.setTimeReceipt(new Time(date)) ;
    	theManager.persist(mes) ;
    }
    public void checkMessage(Long aIdMessage) {
    	java.util.Date date = new java.util.Date() ;
    	SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy") ;
    	SimpleDateFormat formatT = new SimpleDateFormat("HH:mm") ;
    	theManager.createNativeQuery("update CustomMessage set readDate=to_date('"+formatD.format(date)+"','dd.mm.yyyy'),readTime=cast('"+formatT.format(date)+"' as time) where id="+aIdMessage).executeUpdate() ;
    }
	public void hideMessage(Long aIdMessage) {
    	theManager.createNativeQuery("update CustomMessage set isHidden='1' where id="+aIdMessage).executeUpdate() ;
	}
    @Resource SessionContext theContext ;
    private @PersistenceContext EntityManager theManager;

}

