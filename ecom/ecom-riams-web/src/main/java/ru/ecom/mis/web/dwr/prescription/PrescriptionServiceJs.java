package ru.ecom.mis.web.dwr.prescription;

import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.prescription.IPrescriptionService;
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
		StringBuilder sqlMS = new StringBuilder() ;
		StringBuilder sqlCab = new StringBuilder();
		StringBuilder res = new StringBuilder() ;
		String[] labListArr = aPresIDs.split("#");
		if (labListArr.length>0) {
			for (int i=0; i<labListArr.length;i++) {
				String[] param = labListArr[i].split(":");
				String msID = param[0]!=null? param[0] : null;
				String date = param[1]!=null ? param[1]: "";
				String cabID = param[2]!=null? param[2] : null;
				if (msID!=null){
					sqlMS.setLength(0);
					sqlMS.append("select ms.id, ms.code ||' ' ||ms.name, vst.code from medservice ms ")
					.append("left join vocservicetype vst on vst.id = ms.servicetype_id ")
					.append("where ms.id='").append(msID).append("'");
					
					Collection<WebQueryResult> listMS = service.executeNativeSql(sqlMS.toString()) ;
					for (WebQueryResult wqr :listMS) {
						res.append(wqr.get1()).append(":")
						.append(wqr.get2()).append(":")
						.append(wqr.get3()).append(":");
					}
					
					res.append(date).append(":");
					if (cabID!=null && cabID !=""){
						sqlCab.setLength(0);
						sqlCab.append("Select wf.id || ' ' || wf.groupname,wf.id from workfunction wf where wf.id='")
							.append(cabID).append("'");
						
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
