package ru.ecom.websocket;


import ru.ecom.web.util.Injection;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
@WebListener
public class ServletRequestListenerImpl implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ((HttpServletRequest) sre.getServletRequest()).getSession().setAttribute(Injection.class.getName(), Injection.find(sre.getServletContext(),"riams"));
    }

}