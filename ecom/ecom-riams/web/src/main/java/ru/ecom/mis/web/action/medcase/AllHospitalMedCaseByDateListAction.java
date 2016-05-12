package ru.ecom.mis.web.action.medcase;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.web.actions.parententity.ListAction;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.decorator.ITableDecorator;

public class AllHospitalMedCaseByDateListAction extends ListAction {
	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, 
				HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
	        
	        //aRequest.setAttribute("list", service.listAll(form.getClass(), id));

	        String ssl_id = aRequest.getParameter("sslid") ;
	        String date = aRequest.getParameter("id") ;
	        String dateSearch = aRequest.getParameter("dateSearch") ;
	        Long id ;
	        long sslId = -1 ;
	        System.out.println("sslid="+ssl_id) ;
	        if (!StringUtil.isNullOrEmpty(ssl_id)){
	        	sslId=Long.valueOf(ssl_id);
	        	IHospitalMedCaseService service = (IHospitalMedCaseService)Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
	        	id = service.getPatient(sslId) ;
	        	System.out.println("date = "+date) ;
	        } else {
	            if(StringUtil.isNullOrEmpty(date)) {
	            	throw new IllegalArgumentException("Нет параметра date") ;
	            }
	            id = Long.parseLong(date);
	        }
	        aRequest.setAttribute("id", id);
	        IParentEntityFormService service = EntityInjection.find(aRequest).getParentEntityFormService();
	        IEntityForm form = castEntityForm(aForm, aMapping) ;

	        //Long id = Long.parseLong(idString) ;
	        

			boolean isMap = isMap(form);
			Collection list = isMap 
					? service.listAll(getFormClassname(aMapping), id) 
					: service.listAll(form.getClass(), id);

			aRequest.setAttribute("list"
					, isMap ? ru.ecom.web.actions.entity.ListAction.transormCollection(list, form.getClass()) : list);
	        aRequest.setAttribute("decorator", getDecorator(sslId));
	        return aMapping.findForward("success") ;
	    }
		
    private ITableDecorator getDecorator(final long aSlsId) {
        return new ITableDecorator() {

        	public String getId(Object aRow) {
        		HospitalMedCaseForm form = (HospitalMedCaseForm) aRow ;
        		return String.valueOf(form.getId());
        	}

        	public String getRowCssClass(Object aRow) {
        		StringBuffer style = new StringBuffer();
        		HospitalMedCaseForm form = (HospitalMedCaseForm) aRow ;
        		if(form.getIsDeniedHospitalizating()) style.append("deniedHospitalizating") ;
        		if (form.getId()==aSlsId) style.append( " current") ; 
        		return style.toString() ;
        	}
        };
    }

}
