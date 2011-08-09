package ru.ecom.web.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Настройка приложения
 */
public class ConfigServlet extends HttpServlet {

    public void init(ServletConfig aServletConfig) throws ServletException {
        throw new IllegalStateException("УСТАРЕЛ") ;
//        String ejbAppName = aServletConfig.getInitParameter("ejb-app-name") ;
//        String providerUrl = aServletConfig.getInitParameter("java.naming.provider.url") ;
//        if(StringUtil.isNullOrEmpty(ejbAppName)) throw new ServletException("Нет параметра в инициализации сервлета ejb-app-name") ;
//        if(StringUtil.isNullOrEmpty(providerUrl)) throw new ServletException("Нет параметра в инициализации сервлета java.naming.provider.url") ;
//        try {
//            Injection.config(ejbAppName,providerUrl);
//        } catch (NamingException e) {
//            throw new ServletException("Ошибка инициализации приложения",e);
//        }

    }
}
