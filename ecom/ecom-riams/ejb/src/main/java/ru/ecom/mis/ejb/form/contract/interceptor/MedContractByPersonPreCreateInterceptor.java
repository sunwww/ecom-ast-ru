package ru.ecom.mis.ejb.form.contract.interceptor;

import ru.ecom.ejb.sequence.service.ISequenceService;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.contract.NaturalPerson;
import ru.ecom.mis.ejb.form.contract.MedContractByPersonForm;
import ru.nuzmsh.util.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MedContractByPersonPreCreateInterceptor  implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	
    	MedContractByPersonForm form = (MedContractByPersonForm)aForm ;
    	if (aParentId!=null) {
    		NaturalPerson parent = aContext.getEntityManager().find(NaturalPerson.class, aParentId) ;
    		String next = EjbInjection.getInstance().getLocalService(ISequenceService.class).startUseNextValue("MedContract","contractNumber");
			form.setContractNumber(next);
    		if (parent!=null) {
        		/*if (parent.getPriceList()!=null) {
        			form.setPriceList(parent.getPriceList().getId()) ;
        		}
        		if (parent.getRulesProcessing()!=null) {
        			form.setRulesProcessing(parent.getRulesProcessing().getId()) ;
        		}*/
    			form.setServedPerson(parent.getId()) ;
		/*		String sql = "select mc.contractNumber,mc.id from MedContract mc where mc.parent_id='" +
						aParentId + "' order by mc.id desc";
				List<Object[]> list = aContext.getEntityManager().createNativeQuery(sql).setMaxResults(1)
        				.getResultList() ;
        		String addDog;
        		if (!list.isEmpty()) {
        			Object[] objs = list.get(0) ;
        			addDog = ""+objs[0] ;
        			//addDog = addDog.replaceFirst(parent.getContractNumber()+".", "") ;
        			addDog = "."+((Integer.valueOf(addDog)+1)) ;
        		}
        		*/
        		/*form.setContractNumber(
        			new StringBuilder().append(parent.getContractNumber())
        			//.append(".")
        			.append(addDog)
        			.toString()
        			) ;*/
    		}
    		
    	}
    	
    	form.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString());
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        form.setCreateDate(DateFormat.formatToDate(date));
        form.setCreateTime(timeFormat.format(date));
        form.setDateFrom(DateFormat.formatToDate(date));
    }

}