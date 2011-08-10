package ru.nuzmsh.web.tags.helper;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import ru.nuzmsh.commons.auth.ILoginInfo;

/**
 * Проверка наличия прав у пользователя
 */
public class RolesHelper {


    public static boolean checkRoles(String aRoles, HttpServletRequest aRequest) throws JspException {
        boolean ret ;
        HttpSession session = aRequest.getSession() ;
        if(session!=null) {
            ILoginInfo loginInfo = (ILoginInfo) session.getAttribute(ILoginInfo.class.getName()) ;
            if(loginInfo!=null) {
                Collection col = getRolesAsCollection(aRoles) ;
                ret = true ;
                for (Iterator iterator = col.iterator(); iterator.hasNext();) {
                    String role = (String) iterator.next();
                    if(!loginInfo.isUserInRole(role)) {
                        ret = false ;
                        break ;
                    }
                }
            } else {
                throw new JspException("Нет ILoginInfo");
            }
        } else {
            throw new JspException("Нет сессии");
        }

        return ret ;
    }


    private static Collection getRolesAsCollection(String aRoles) {
        LinkedList list = new LinkedList();
        
        if(aRoles.indexOf(';')<0 && aRoles.indexOf(',')<0&& aRoles.indexOf(' ')<0) {
            list.add(aRoles.indexOf(' ')>=0 ? aRoles.trim() : aRoles) ;
        } else {
            StringTokenizer st = new StringTokenizer(aRoles, ";, ");
            while(st.hasMoreTokens()) {
                String role = st.nextToken() ;
                list.add(role) ;
            }
        }
        return list ;
    }

    public static boolean checkRoles(String aRoles, JspContext aContext) throws JspException {
        PageContext ctx = (PageContext) aContext ;
        return checkRoles(aRoles, (HttpServletRequest) ctx.getRequest()) ;
    }
}
