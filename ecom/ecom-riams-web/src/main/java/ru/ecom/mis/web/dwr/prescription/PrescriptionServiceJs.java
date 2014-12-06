package ru.ecom.mis.web.dwr.prescription;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.prescription.IPrescriptionService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;

/**
 * Сервис назначений
 * @author STkacheva
 */
public class PrescriptionServiceJs {
	public String cancelService(String aPrescript,String aReason,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		java.util.Date date = new java.util.Date() ;
		SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy") ;
		SimpleDateFormat formatT = new SimpleDateFormat("hh:mm") ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ; 
		sql.append("update Prescription set cancelReasonText='").append(aReason).append("', cancelDate=to_date('").append(formatD.format(date)).append("','dd.mm.yyyy'),cancelTime=cast('").append(formatT.format(date)).append("' as time),cancelUsername='").append(username).append("' where id='").append(aPrescript).append("'");
		service.executeUpdateNativeSql(sql.toString()) ;
		return "1" ;
	}
	/**
	 * Поиск СЛО по ИД листа назначения
	 * @param aPrescList - ИД листа назначения
	 * @param aRequest
	 * @return ИД MedCase
	 * @throws NamingException
	 */
	public Long getMedcaseByPrescriptionList(String aPrescList, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String req = "Select pl.medcase_id from prescriptionList pl where pl.id='"+aPrescList+"' ";
		Collection<WebQueryResult> list = service.executeNativeSql(req,1) ;
		if (!list.isEmpty()) {
			WebQueryResult obj = list.iterator().next() ; 
		//	System.out.println("res.get1 ================================ "+obj.get1());
				return ConvertSql.parseLong(obj.get1());
			
		} 
		return Long.valueOf(0);
	}
	/**
	 * Проверка назначений на наличие дублей (имеются назначения на такие же 
	 * исследования в том же СЛО, в котором создается назначение)
	 */
	
	public String getDuplicatePrescriptions(String aMedCase, String aData, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder req = new StringBuilder();
		req.append("select distinct p.medservice_id ");
		req.append(",ms.code ||' ' || ms.name as labName ");
		//req.append(",count(p.id) as cnt1 ");
		req.append("from medcase mc ");
		req.append("left join prescriptionList pl on pl.medcase_id = mc.id ");
		req.append("left join prescription p on p.prescriptionList_id = pl.id ");
		req.append("left join patient pat on pat.id = mc.patient_id ");
		req.append("left join medservice ms on ms.id = p.medservice_id ");
		req.append("where mc.id ='").append(aMedCase).append("' ");
		req.append("and p.dtype='ServicePrescription' ");
		req.append("and p.medservice_id is not null ");
		req.append("and p.fulfilmentstate_id is null ");
		req.append("and p.canceldate is null ");
		req.append("group by p.medservice_id, labName ");
		//req.append("having count(p.id)>1 ");
	//	System.out.println("--------------------getDuplicatePrescriptions Request is = "+req.toString());
		Collection<WebQueryResult> list = service.executeNativeSql(req.toString().toString()) ;
	//	System.out.println("-------------in PS - start working!!!");
		StringBuilder res = new StringBuilder();
		if (list.size()>0) {
	//		System.out.println("-------------list_size>0"+list.size());
			String[] aDataArr  = aData.split(":");
			for (WebQueryResult obj: list) {
				for (int i=0; i<aDataArr.length;i++) {
					if (obj.get1().toString().equals(aDataArr[i])) {
						res.append(obj.get1().toString()).append(":").append(obj.get2().toString()).append("#");
					}
				}
			}
		}
	//	System.out.println("in getDuplicatePrescriptions Result is = "+res.toString());
		return res.length()>0?res.substring(0,res.length()-1):"";
	}
	
	/*
	 * Возвращаем тип исследование, его код и имя, код и имя кабинета
	 * в формате: medserviceID:msName:msType:date:cabinetName# 
	 * На входе берем список исследований в формате ID:date:cabinet#
	 */
	public String getPresLabTypes(String aPresIDs, HttpServletRequest aRequest) throws NamingException {
		System.out.println(aPresIDs);
		return getPresLabTypes(aPresIDs, 0,aRequest);
	}
	
	
	/*
	 * Проверяем полученные исследования на соответствие типу листа назначения 
	 * и возвращаем те, которые соответствуют типу ЛН.
	 */
	public String getPresLabTypes(String aPresIDs, int aPrescriptListType, HttpServletRequest aRequest) throws NamingException {
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
					sqlMS.append("select vst.code, ms.id, ms.code ||' ' ||ms.name from medservice ms ")
					.append("left join vocservicetype vst on vst.id = ms.servicetype_id ")
					.append("where ms.id='").append(msID).append("' ");
					if (aPrescriptListType>0) {
					sqlMS.append("and ms.servicesubtype_id='").append(aPrescriptListType).append("' ");
					}
					
					Collection<WebQueryResult> listMS = service.executeNativeSql(sqlMS.toString()) ;
					for (WebQueryResult wqr :listMS) {
						res.append(wqr.get1()).append(":")
						.append(wqr.get2()).append(":")
						.append(wqr.get3()).append(":")
						.append(date).append(":");
						
						if (cabID!=null && cabID !=""){
							sqlCab.setLength(0);
							sqlCab.append("Select wf.id,wf.groupname from workfunction wf where wf.id='")
								.append(cabID).append("' ");
							
							Collection<WebQueryResult> listCab = service.executeNativeSql(sqlCab.toString()) ;
							for (WebQueryResult wqr2 :listCab) {
								res.append(wqr2.get1()).append(":");
								res.append(wqr2.get2()).append("#");
							}
						} else res.append(":#");
					}					
					
					
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
		sql.append("update Prescription set intakeDate=to_date('").append(formatD.format(date)).append("','dd.mm.yyyy'),intakeTime=cast('").append(formatT.format(date)).append("' as time),intakeUsername='").append(username).append("' where id in (").append(aListPrescript).append(")");
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
	
	public String getPrescriptionTypes(boolean isEmergency, HttpServletRequest aRequest) throws NamingException {
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		return service.getPrescriptionTypes(isEmergency) ;
	}
	
	public String getLabListFromTemplate(Long aIdTemplateList, HttpServletRequest aRequest) throws NamingException {
		
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		return service.getLabListFromTemplate(aIdTemplateList) ;
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
