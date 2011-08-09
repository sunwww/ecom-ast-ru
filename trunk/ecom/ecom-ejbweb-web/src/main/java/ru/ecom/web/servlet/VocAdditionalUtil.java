package ru.ecom.web.servlet;

import javax.servlet.http.HttpServletRequest;

import ru.nuzmsh.util.voc.VocAdditional;

/**
 * @author esinev 18.08.2006 1:57:33
 */
public class VocAdditionalUtil {

    public static VocAdditional create(HttpServletRequest aRequest) {
        return new VocAdditional(aRequest.getParameter("parent"));
    }

}
