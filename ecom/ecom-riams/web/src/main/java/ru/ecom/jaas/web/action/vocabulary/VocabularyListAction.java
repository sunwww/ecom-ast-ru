package ru.ecom.jaas.web.action.vocabulary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.ecom.mis.ejb.service.vocabulary.IVocabularyService;
import ru.ecom.mis.ejb.service.vocabulary.VocEntityInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.web.tags.decorator.ITableDecorator;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class VocabularyListAction extends BaseAction{

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		IVocabularyService service = Injection.find(aRequest).getService(IVocabularyService.class) ;
		 final String sort = (aRequest.getParameter("sort") ==null || aRequest.getParameter("sort").equals(""))  ? "comment" : aRequest.getParameter("sort") ;

		Collection<VocEntityInfo> list = service.listVocEntities() ;
		List<VocabularyForm> listForm = new ArrayList<VocabularyForm>() ;
		for(VocEntityInfo voc : list) {
			
			if (RolesHelper.checkRoles(new StringBuilder().append("/Policy/Voc/").append(voc.getSimpleName()).toString(),aRequest)) {
				VocabularyForm vocForm = new VocabularyForm(voc.getClassname(),voc.getSimpleName(),voc.getComment(),service.getCount(voc.getSimpleName()),voc.getIsSystem()) ;
				System.out.println(new StringBuilder().append("/Policy/Voc/").append(voc.getSimpleName()+"---"+voc.getIsSystem()).toString()) ;
				listForm.add(vocForm) ;
			}
		}
		
		String vocid = aRequest.getParameter("id") ;
		if (vocid==null || vocid.equals("")) vocid="null" ;

		Collections.sort(listForm, new  Comparator<VocabularyForm>() {
			public int compare(VocabularyForm o1, VocabularyForm o2) {
				if (sort.equals("count")) {
					return o1.getCount()-o2.getCount() ;
				} else {
					if (sort.equals("classname")) {
						return o1.getClassname().compareTo(o2.getClassname()) ; 
					} {
						return o1.getComment().compareTo(o2.getComment()) ;
					}
				}
			}
		}) ;
		aRequest.setAttribute("list", listForm);
		
		aRequest.setAttribute("decorator", getDecorator(vocid));
		return aMapping.findForward("success");
	}
	
	private ITableDecorator getDecorator(final String aVocId) {
        return new ITableDecorator() {

        	public String getId(Object aRow) {
        		VocabularyForm form = (VocabularyForm) aRow ;
        		return String.valueOf(form.getClassname());
        	}

        	public String getRowCssClass(Object aRow) {
        		StringBuffer style = new StringBuffer();
        		VocabularyForm form = (VocabularyForm) aRow ;
        		if (form.getIsSystem()) style.append("systemVoc") ;
        		if (form.getClassname().equals(aVocId)) style.append( " current") ; 
        		return style.toString() ;
        	}
        };
    }

}
