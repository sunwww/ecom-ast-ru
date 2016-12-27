package ru.ecom.mis.web.dwr.lpu;

import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

public class EquipmentServiceJs {
	public String getEquipmentType(Long aEquipmentId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l = service.executeNativeSql("select vte.id, vte.name from vocTypeEquip vte " +
				" left join equipment e on vte.id=e.typeequip_id where e.id="+aEquipmentId) ;
		if (l.size()>0) {
			WebQueryResult r = l.iterator().next();
			return r.get1().toString()+"@"+r.get2().toString();
		} else {
			return "";
		}
		
	}
	public String setEquipmentType(Long aId, Long aTypeId, HttpServletRequest aRequest) throws NamingException{
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	service.executeUpdateNativeSql("update equipment set typeequip_id="+aTypeId +" where id="+aId) ;
		return "";
	}
}