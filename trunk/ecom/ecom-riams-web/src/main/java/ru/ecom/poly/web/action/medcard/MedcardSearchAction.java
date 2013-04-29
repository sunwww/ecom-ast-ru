package ru.ecom.poly.web.action.medcard;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.poly.ejb.services.IMedcardService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;


/**
 * Поиск мед. карт. 
 */
public class MedcardSearchAction extends BaseAction {

     public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception
     {
         MedcardSearchForm form = (MedcardSearchForm) aForm;
         String exactMatch = aRequest.getParameter("exactMatch") ;
         String exactMatchH = aRequest.getParameter("exactMatchH") ;
         if (exactMatch ==null && exactMatchH==null) exactMatchH = (String)aRequest.getSession(true).getAttribute("findSlsByStatCard.exactMatch") ;

         
         IMedcardService service = Injection.find(aRequest).getService(IMedcardService.class);
         boolean isExM = false ;
     	if ((exactMatch==null && exactMatchH==null) 
        		|| (exactMatchH!=null && exactMatchH.equals("1"))
        		||  ( exactMatch!=null &&exactMatchH==null &&( exactMatch.equals("true") 
        				||exactMatch.equals("1") 
        				|| exactMatch.equals("on"))
        )) {

        	aRequest.setAttribute("exactMatch",1) ;
        	isExM=false ;
        	aRequest.getSession(true).setAttribute("findMedcard.exactMatch", "1") ;
        } else {
        	aRequest.setAttribute("exactMatch",0) ;
        	isExM=true ;
        	aRequest.getSession(true).setAttribute("findMedcard.exactMatch", "0") ;
        }
         if (form.getNumber()!=null && !form.getNumber().equals("")) {
        	 aRequest.setAttribute("list", service.findMedCard(form.getNumber(),isExM));
         } else {
        	 aRequest.setAttribute("list", new ArrayList()) ;
         }
         
         return aMapping.findForward("success");
    }
}
