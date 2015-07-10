package ru.ecom.mis.web.dwr.attachment;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.jdom.JDOMException;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.addresspoint.IAddressPointService;
import ru.ecom.mis.ejb.service.extdisp.IExtDispService;
import ru.ecom.mis.ejb.service.sync.lpuattachment.ISyncAttachmentDefectService;
import ru.ecom.web.util.Injection;

/**
 * 
 * @author VTsybulin 28.01.2015
 * 
 *
 */
public class AttachmentServiceJs {
	
	public String getAreaByPatient (Long aPatientId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select la.id,la.number ||' '||vat.name, lpu.id,lpu.name, mp.company_id, ri.name from patient p " +
				" left join lpuareaaddresstext lat on lat.address_addressid=p.address_addressid" +
				" left join lpuarea la on la.id=lat.area_id " +
				" left join vocareatype vat on vat.id=la.type_id" +
				" left join mislpu lpu on lpu.id=la.lpu_id " +
				" left join medpolicy mp on mp.patient_id = p.id" +
				" left join reg_ic ri on ri.id=mp.company_id" +
				
				" where p.id="+aPatientId +
				" and mp.dtype='MedPolicyOmc' and mp.id=(select max(id) from medpolicy where patient_id=p.id and dtype='MedPolicyOmc')";
		Collection<WebQueryResult> res = service.executeNativeSql(sql);
		if (!res.isEmpty()) {
			WebQueryResult r = res.iterator().next();
			String lpuId = r.get3()!=null?r.get3().toString():""
				,lpuName = r.get4()!=null?r.get4().toString():""
				,areaId = r.get1()!=null?r.get1().toString():""
				,areaName = r.get2()!=null?r.get2().toString():""
				,companyId = r.get5()!=null?r.get5().toString():""
				,companyName = r.get6()!=null?r.get6().toString():"";
			return lpuId+":"+lpuName+":"+areaId+":"+areaName+":"+companyId+":"+companyName;
		}
		return ":::::";
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
	public String importDefectsFromXML(String aFileName, HttpServletRequest aRequest) throws NamingException {
		ISyncAttachmentDefectService service = Injection.find(aRequest).getService(ISyncAttachmentDefectService.class) ;
		try {
			return service.importDefectFromXML(aFileName);
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
