package ru.ecom.web.servlet;

import ru.nuzmsh.util.voc.VocAdditional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author esinev 18.08.2006 1:57:33
 */
public class VocAdditionalUtil {

    public static VocAdditional create(HttpServletRequest aRequest) {
        return new VocAdditional(aRequest.getParameter("parent"));
    }

}
