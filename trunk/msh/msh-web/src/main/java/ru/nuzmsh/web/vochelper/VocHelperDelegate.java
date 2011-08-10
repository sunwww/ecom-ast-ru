package ru.nuzmsh.web.vochelper;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * @author esinev
 * Date: 09.03.2006
 * Time: 10:10:56
 */
public class VocHelperDelegate {

     public static IVocHelper locateVocHelper() throws VocHelperLocateException {
         try {
             Context initCtx = new InitialContext();
             Context envCtx = (Context) initCtx.lookup("java:comp/env");
             IVocHelper bean =  (IVocHelper) envCtx.lookup("bean/IVocHelper");
             if(bean==null) {
                 throw new NullPointerException("Не найден 'bean/IVocHelper'") ;
             }
             return bean ;
         } catch (Exception e) {
             throw new VocHelperLocateException(e) ;
         }
     }
}
