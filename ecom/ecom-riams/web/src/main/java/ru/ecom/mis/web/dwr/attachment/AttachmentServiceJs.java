package ru.ecom.mis.web.dwr.attachment;

import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.expomc.ejb.services.sync.ISyncService;
import ru.ecom.mis.ejb.service.addresspoint.IAddressPointService;
import ru.ecom.mis.ejb.service.sync.lpuattachment.ISyncAttachmentDefectService;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 
 * @author VTsybulin 28.01.2015
 * 
 *
 */
public class AttachmentServiceJs {

	//Импорт населения с фонда
	public String syncUpload(String aEntity, HttpServletRequest aRequest) throws NamingException {
		 IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
		 IWebQueryService wqs = Injection.find(aRequest).getService(IWebQueryService.class) ;
		long monitorId = monitorService.createMonitor(); 
		ISyncService service = Injection.find(aRequest).getService(ISyncService.class);
		service.syncByEntity(monitorId, aEntity);
		try{
			String s = wqs.executeNativeSql("select max(id) from fondimport").iterator().next().get1().toString(); 
			aRequest.setAttribute("impResult", "");
			return "Синхронизация успешно завершена. <a href='js-exp_importtime-checkByTime.do?id="+s+"' target='_blank'>Просмотреть результаты</a>";
		} catch (Exception e) { 
			return ""+e.toString();
		}
		
	} 
	
	public String getAreaByPatient (Long aPatientId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		try {
			String sql = "select la.id as laId,la.number ||' '||vat.name as laName, lpu.id as lpuId,lpu.name as lpuName, mp.company_id, ri.name as skName " +
					" ,(select id||':'||id||' '||name from vocattachedtype vott where vott.code=case when la.id is not null then '1' else '2' end) as att_info" +					
					" from patient p " +
					" left join lpuareaaddresspoint laap on laap.address_addressid=p.address_addressid" +
					" 	and ((laap.housenumber is null or laap.housenumber='') or laap.housenumber=p.housenumber)" +
					"   and (((laap.housebuilding is null or laap.housebuilding='')and (p.housebuilding is null or p.housebuilding='')) or laap.housebuilding=p.housebuilding)" +
					" left join lpuareaaddresstext lat on lat.id=laap.lpuareaaddresstext_id" +
					" left join lpuarea la on la.id=lat.area_id " +
					" left join vocareatype vat on vat.id=la.type_id" +
					" and vat.code= case when cast(to_char(current_date,'yyyy') as int) " +
					" -cast(to_char(p.birthday,'yyyy') as int) +(case when (cast(to_char(current_date, 'mm') as int) " +
					" -cast(to_char(p.birthday, 'mm') as int) +(case when (cast(to_char(current_date,'dd') as int) " +
					" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) <18 then '2' else '1' end" +
					" left join mislpu lpu on lpu.id=la.lpu_id " +
					" left join medpolicy mp on mp.patient_id = p.id and mp.dtype='MedPolicyOmc' and mp.id=(select max(id) from medpolicy where patient_id=p.id and dtype='MedPolicyOmc')" +
					" left join reg_ic ri on ri.id=mp.company_id" +
					" where p.id="+aPatientId
					;
			Collection<WebQueryResult> res = service.executeNativeSql(sql);
			if (!res.isEmpty()) {
				WebQueryResult r = res.iterator().next();
				String lpuId = r.get3()!=null?r.get3().toString():""
					,lpuName = r.get4()!=null?r.get4().toString():""
					,areaId = r.get1()!=null?r.get1().toString():""
					,areaName = r.get2()!=null?r.get2().toString():""
					,companyId = r.get5()!=null?r.get5().toString():""
					,companyName = r.get6()!=null?r.get6().toString():""
					,attachedType =r.get7()!=null?r.get7().toString():"";
				return lpuId+":"+lpuName+":"+areaId+":"+areaName+":"+companyId+":"+companyName+":"+attachedType;
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		return "";
		
	}
	
	public String changeArea (long aOldAreaId, long aNewLpuId, long aNewAreaId, HttpServletRequest aRequest) throws NamingException {
		ISyncAttachmentDefectService service = Injection.find(aRequest).getService(ISyncAttachmentDefectService.class) ;
		return service.changeAttachmentArea(aOldAreaId, aNewLpuId, aNewAreaId); 
		
	}
	public String cleanDefect(long aAttachmentId, HttpServletRequest aRequest) throws NamingException {
		ISyncAttachmentDefectService service = Injection.find(aRequest).getService(ISyncAttachmentDefectService.class) ;
		try {
			return service.cleanDefect(aAttachmentId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Ошибка: "+e.toString();
		}
	}
	public String importDefectsFromXML(String aFileText, HttpServletRequest aRequest) throws NamingException {
		try {
			return Injection.find(aRequest).getService(ISyncAttachmentDefectService.class).importDefectFromXML(aFileText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public String createAttachmentFromPatient (String needUpdateIns, HttpServletRequest aRequest)throws NamingException {
		IAddressPointService service = Injection.find(aRequest).getService(IAddressPointService.class) ;
		try {
			return service.createAttachmentFromPatient(needUpdateIns);
		} catch (Exception e) {
			return "Ошибка:"+e.toString();
		}
	}
	
	public String setInsuranceCompany (String needUpdateAll, HttpServletRequest aRequest)throws NamingException {
		IAddressPointService service = Injection.find(aRequest).getService(IAddressPointService.class) ;
		try {
			return service.setInsuranceCompany(needUpdateAll);
		} catch (Exception e) {
			return "Ошибка:"+e.toString();
		}
	}	
}
