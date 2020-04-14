package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;
import org.mozilla.javascript.*;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.script.ScriptServiceContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.form.medcase.DiagnosisForm;
import ru.ecom.mis.ejb.form.medcase.hospital.DepartmentMedCaseForm;
import ru.nuzmsh.forms.response.FormMessage;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

public class DepartmentViewInterceptor  implements IFormInterceptor{

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		DepartmentMedCaseForm form = (DepartmentMedCaseForm)aForm ;
		DepartmentMedCase dep = (DepartmentMedCase)aEntity ;
		DiagnosisForm frm ;
		frm = DischargeMedCaseViewInterceptor.getDiagnosis(aContext.getEntityManager(), dep.getId(), "4", "1", false) ;
		if (frm!=null) {
			form.setClinicalDiagnos(frm.getName());
			if (frm.getIdc10()!=null) form.setClinicalMkb(frm.getIdc10()) ;
			if (frm.getIllnesPrimary()!=null) form.setClinicalActuity(frm.getIllnesPrimary()) ;
			if (frm.getMkbAdc()!=null) form.setMkbAdc(frm.getMkbAdc()) ;
		}
		form.setComplicationDiags(getDiagnosis(aContext.getEntityManager(), dep.getId(), "4","4")) ;
		form.setConcomitantDiags(getDiagnosis(aContext.getEntityManager(), dep.getId(), "4", "3")) ;
		
		if (!form.isTypeCreate() && form.getSaveType()==form.TYPE_SAVE) {
		
				if (dep.getParent() instanceof HospitalMedCase
						&& ((HospitalMedCase)dep.getParent()).getDischargeTime()!=null
						&&aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Discharge/OnlyCurrentDay")
						) {
					boolean isOpen;
					try {
						isOpen = checkPermission("DischargeMedCase", "editAfterDischarge", dep.getParent().getId(),aContext);
						if (!isOpen) {
							form.getPage();
							form.setTypeViewOnly() ;
						}
						//throw new IllegalArgumentException("is Open = "+isOpen) ;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						form.addMessage(new FormMessage(e.getMessage()));
					}
					
			} 
		}
			
		
	}
	
	  private boolean checkPermission(String aObject, String aPermission,  Long aIdTicket, InterceptorContext aContext) {
	    	HashMap<String, Comparable> param = new HashMap<>() ;
	    	long res1 ;
	    	Object res ;
	    	
	    	param.put("obj",aObject) ;
			param.put("permission" ,aPermission) ;
			param.put("id", aIdTicket) ;
			res = invoke(aContext.getEntityManager(), aContext.getSessionContext(), "WorkerService", "checkPermission", new Object[]{param});
			res1 = ConvertSql.parseLong(res);
		  	return res1>0 ;
	    }
	  
	public static String getDiagnosis(EntityManager aManager, Long aMedCase, String aRegistrationType, String aPriority) {
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select coalesce(d.idc10_id,'0'),coalesce(mkb.code||' '||mkb.name,'НЕОБХОДИМО УКАЗАТЬ КОД МКБ!!!') as mkbname,d.name from Diagnosis as d left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id");
		sql.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=d.registrationType_id left join VocIdc10 mkb on mkb.id=d.idc10_id where d.medCase_id=").append(aMedCase)
    		.append(" and vdrt.code='").append(aRegistrationType)
    		.append("' and vpd.code='").append(aPriority).append("' order by d.id") ;
    	StringBuilder res = new StringBuilder() ;
    	List<Object[]> list = aManager.createNativeQuery(sql.toString()).getResultList() ;
    	for (Object[] child : list) {
			res.append(child[0]).append("@#@").append(child[1]).append("@#@") ;
			res.append(child[2]).append("#@#") ;
		}
			
			
		return res.length()>3?res.substring(0,res.length()-3):"" ;
    }
	public Object invoke(EntityManager aManager,SessionContext aContext ,String aServiceName, String aMethodName, Object[] aArgs) {
		
		

		String jsResourceName = "/META-INF/scriptService/"+aServiceName+".js" ;
		try {
			InputStream inputStream = theEcomConfig.getInputStream(jsResourceName, EjbEcomConfig.SCRIPT_SERVICE_PREFIX, true) ;
			if(inputStream!=null) {
				Context jsContext = Context.enter();
				try (Reader in = new InputStreamReader(inputStream, "utf-8")){
					Scriptable scope = jsContext.initStandardObjects();

						Script script = jsContext.compileReader(in, jsResourceName, 1,null);
						script.exec(jsContext, scope);
						
						Object o = scope.get(aMethodName, scope);
						if(o instanceof Function) {
							Function f = (Function) o ;
							ScriptServiceContext ctx = new ScriptServiceContext(aManager, aContext, theEjbInjection) ;
							Object[] args = new Object[aArgs.length+1] ;
							args[0] = ctx ;
							System.arraycopy(aArgs, 0, args, 1, aArgs.length);
							Object result = f.call(jsContext, scope, scope, args);
							
							Object ret ;
							if(result instanceof NativeJavaObject) {
								ret = ((NativeJavaObject) result).unwrap() ;
							} else {
								ret = result ;
							}
							return ret ;
						} else {
							throw new IllegalArgumentException("Нет функции "+aMethodName+" у сервиса "+aServiceName+" : "+o) ; 
						}
				} finally {
					Context.exit();
				}
			}
		} catch (IOException e) {
			throw new IllegalStateException("Ошибка открытия ресурса "+jsResourceName) ;
		}
		
		return "Hello" ;
	}
	private final EjbInjection theEjbInjection = EjbInjection.getInstance();
	EjbEcomConfig theEcomConfig = EjbEcomConfig.getInstance();
}
