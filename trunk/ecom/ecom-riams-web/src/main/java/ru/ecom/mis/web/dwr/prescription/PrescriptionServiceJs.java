package ru.ecom.mis.web.dwr.prescription;

import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.prescription.IPrescriptionService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;

/**
 * Сервис назначений
 * @author STkacheva
 */
public class PrescriptionServiceJs {
	
	/*
	 * Возвращаем тип исследование, его код и имя, код и имя кабинета
	 * в формате: medserviceID:msName:msType:date:cabinetName# 
	 * На входе берем список исследований в формате ID:date:cabinet#
	 */
	public String getPresLabTypes(String aPresIDs, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		aPresIDs = aPresIDs.substring(0,aPresIDs.length()-1); // Обрезаем # 
		StringBuilder sqlMS = new StringBuilder() ;
		StringBuilder sqlCab = new StringBuilder();
		StringBuilder res = new StringBuilder() ;
		String[] labListArr = aPresIDs.split("#");
		if (labListArr.length>0) {
			
			for (int i=0; i<labListArr.length;i++) {
				String[] param = labListArr[i].split(":");
			//	System.out.println("For="+i+" data ID= "+param[0]);
			//	System.out.println("For="+i+" RES "+res.toString());
				String msID = param.length>0&&param[0]!=null? param[0] : null;
				String date = param.length>1&&param[1]!=null ? param[1]: "";
				String cabID = param.length>2&&param[2]!=null? param[2] : null;
				if (msID!=null && msID!=""){
					sqlMS.setLength(0);
					sqlMS.append("select ms.id, ms.code ||' ' ||ms.name, vst.code from medservice ms ")
					.append("left join vocservicetype vst on vst.id = ms.servicetype_id ")
					.append("where ms.id='").append(msID).append("'");
					
					Collection<WebQueryResult> listMS = service.executeNativeSql(sqlMS.toString()) ;
					for (WebQueryResult wqr :listMS) {
						res.append(wqr.get1()).append(":")
						.append(wqr.get2()).append(":")
						.append(wqr.get3()).append(":")
						.append(date).append(":");
					}					
					
					if (cabID!=null && cabID !=""){
						sqlCab.setLength(0);
						sqlCab.append("Select wf.id || ' ' || wf.groupname,wf.id from workfunction wf where wf.id='")
							.append(cabID).append("' ");
						
						Collection<WebQueryResult> listCab = service.executeNativeSql(sqlCab.toString()) ;
						for (WebQueryResult wqr :listCab) {
							res.append(wqr.get1()).append("#");
						}
					} else res.append("#");
				}
			}
		}
		return res.length()>0?res.substring(0,res.length()-1):"";
	}
	public String getDescription(Long aIdTemplateList, HttpServletRequest aRequest) throws NamingException {
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		System.out.println("Получить описание шаблона: "+aIdTemplateList);
		return service.getDescription(aIdTemplateList) ;
	}
	public String intakeService(String aListPrescript,String aMaterialId,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		java.util.Date date = new java.util.Date() ;
		SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy") ;
		SimpleDateFormat formatT = new SimpleDateFormat("hh:mm") ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ; 
		sql.append("update Prescription set materialId='").append(aMaterialId).append("',intakeDate='").append(formatD.format(date)).append("',intakeTime='").append(formatT.format(date)).append("',intakeUsername='").append(username).append("' where id in (").append(aListPrescript).append(")");
		service.executeUpdateNativeSql(sql.toString()) ;
		return "1" ;
	}
	public String intakeServiceRemove(String aListPrescript,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("update Prescription set intakeDate=null,intakeTime=null,intakeUsername=null where id in (").append(aListPrescript).append(")");
		service.executeUpdateNativeSql(sql.toString()) ;
		return "1" ;
	}
	public boolean checkMedCaseEmergency(Long aIdTemplateList, String idType, HttpServletRequest aRequest) throws NamingException {
		
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		return service.checkMedCaseEmergency(aIdTemplateList, idType) ;
	}
	
	public String savePrescription(Long aIdParent,Long aIdTemplateList, Long aFlag, HttpServletRequest aRequest) throws NamingException {
//		Long id = Long.valueOf(aIdParent);
//		Long flag = Long.valueOf(aFlag);
		System.out.println("Родитель: "+aIdParent);
		System.out.println("Шаблон: "+aIdTemplateList);
		System.out.println("Флаг: "+aFlag);
		
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		if (aFlag==1) return savePrescriptExists(aIdTemplateList,aIdParent, service) ;
		if (aFlag==2) return savePrescriptNew(aIdTemplateList,aIdParent, service) ;
		return "" ;
		
	}
	private String savePrescriptExists(Long aIdTemplateList, Long aIdPrescript, IPrescriptionService service) {
		String ret ="";
		try {
			if (service.savePrescriptExists(aIdTemplateList, aIdPrescript)) ret ="Сохранено в текущий лист назначений" ;
			else ret = "Ошибка при сохранении  в текущий лист назначений" ;
		} catch (Exception e) {
			ret = "Ошибка при сохранении  в текущий лист назначений"+e.getMessage() ;
		}
		
		return ret ;
	}
	
	private String savePrescriptNew(Long aTemplateList, Long aMedCase, IPrescriptionService service) {
		String ret ="";
		try {
			if (service.savePrescriptNew(aTemplateList, aMedCase)) ret ="Сохранено в новый лист назначений" ;
			else ret = "Ошибка при сохранении  в новый лист назначений" ;
		} catch (Exception e) {
			ret = "Ошибка при сохранении  в новый лист назначений:"+e.getMessage() ;
		}
		return ret;	}
	
}
