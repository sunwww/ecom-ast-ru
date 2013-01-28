package ru.ecom.ejb.services.login;

import java.io.FileInputStream;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;
import org.jboss.mx.util.MBeanServerLocator;

import ru.ecom.ejb.services.live.domain.CustomMessage;
import ru.ecom.ejb.services.util.JBossConfigUtil;

/**
 */
@Stateless
@SecurityDomain("other")
@Remote(ILoginService.class)
public class LoginServiceBean implements ILoginService {

    private final static Logger LOG = Logger.getLogger(LoginServiceBean.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    @PermitAll
    public Set getUserRoles() {
        String user = theContext.getCallerPrincipal().getName();
        if (CAN_DEBUG) LOG.debug("user = " + user);
        // todo
        Properties prop = new Properties();
        HashSet<String> ret = new HashSet<String>();
        try {
            try {
                MBeanServer SERVER = MBeanServerLocator.locateJBoss();
                String[] signature = {"java.lang.String"};
                Object retVal  = SERVER.invoke(new ObjectName("jboss.security:service=JaasSecurityManager")
                , "flushAuthenticationCache", new String[]{"other"}, signature) ;
            } catch (Throwable e) {
                LOG.warn(e.getMessage()) ;
            }

            prop.load(new FileInputStream(JBossConfigUtil.getConfigDirname()+"/roles.properties")) ;
            StringTokenizer st = new StringTokenizer(prop.getProperty(user), ", ");
            while(st.hasMoreTokens()) {
                ret.add(st.nextToken()) ;
            }
        } catch (Exception e) {
            throw new IllegalStateException("Ошибка загрузки ролей пользователя "+user,e) ;
        }
        if (CAN_DEBUG) LOG.debug("roles = " + ret);
        return ret ;
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
    	theManager.persist(mes) ;
    	return mes.getId() ;
    }
    public void dispatchMessage(Long aIdMessage) {
    	CustomMessage mes = theManager.find(CustomMessage.class, aIdMessage) ;
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

