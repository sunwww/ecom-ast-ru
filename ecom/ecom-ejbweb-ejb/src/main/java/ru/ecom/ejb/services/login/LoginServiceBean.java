package ru.ecom.ejb.services.login;

import java.io.FileInputStream;
import java.util.HashSet;
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

import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;
import org.jboss.mx.util.MBeanServerLocator;

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

    @Resource SessionContext theContext ;
}

