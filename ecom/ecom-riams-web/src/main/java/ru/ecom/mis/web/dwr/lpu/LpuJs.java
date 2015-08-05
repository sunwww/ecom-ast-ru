package ru.ecom.mis.web.dwr.lpu;

import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.lpu.ILpuService;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.web.util.Injection;

public class LpuJs {
	public String getOtherEquipmentByLpu (String aLpu, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select e.id, e.name from equipment_mislpu el" +
				" left join equipment e on e.id=el.equipment_id" +
				" where el.otherLpu_id="+aLpu;
		Collection<WebQueryResult> res = service.executeNativeSql(sql);
		StringBuilder ret = new StringBuilder();
		if (!res.isEmpty()) {
			for (WebQueryResult r: res) {
				ret.append(r.get1().toString()+":"+r.get2().toString()+"@");
				}
		}
		
		return ret.length()>0?ret.substring(0,ret.length()-1):"";
	}
	public void createOtherEquipment(Long aLpuId, Long aEquipmentId, HttpServletRequest aRequest) throws NamingException{
		ILpuService service = Injection.find(aRequest).getService(ILpuService.class) ;
		service.createOtherEquipment(aLpuId, aEquipmentId);
	}

	public void removeOtherEquipment(Long aLpuId, Long aEquipmentId, HttpServletRequest aRequest) throws NamingException{
		ILpuService service = Injection.find(aRequest).getService(ILpuService.class) ;
		service.removeOtherEquipment(aLpuId, aEquipmentId);
	}
}