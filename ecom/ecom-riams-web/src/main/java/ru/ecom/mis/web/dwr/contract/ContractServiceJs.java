package ru.ecom.mis.web.dwr.contract;

import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

public class ContractServiceJs {
	public String settingAppropriateService(String aAppropriateService, HttpServletRequest aRequest) throws NamingException {
		
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String[] appService = aAppropriateService.split(",") ;
		for (int i=0;i<appService.length;i++) {
			String[] vr = appService[i].split(":") ;
			String aMedService = vr[1] ;
			String aPricePosition = vr[0] ;
			StringBuilder sql = new StringBuilder() ;
			sql.append("select min(case when pms.medService_id='").append(aMedService)
				.append("' then pms.id else null end) as pmsser")
				.append(", min(case when pms.medService_id is null then pms.id else null end) as pmsmin")
				.append(", min(case when pms.medService_id is not null and pms.medService_id='").append(aMedService)
				.append("' then pms.id else null end) as pmsother")
				.append(" from PriceMedService pms where pricePosition_id='").append(aPricePosition).append("'");
			Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1) ;
			if (list.isEmpty()) {
				
			} else {
				WebQueryResult wqr = list.iterator().next() ;
				if (wqr.get1()!=null) {
					
				} else if (wqr.get2()!=null) {
					service.executeUpdateNativeSql("update PriceMedService set medService_id='"+aMedService
							+"' where id='"+wqr.get2()+"'") ;
				} else if (wqr.get3()!=null) {
					service.executeUpdateNativeSql("update PriceMedService set medService_id='"+aMedService
							+"' where id='"+wqr.get3()+"'") ;
				} else {
					service.executeUpdateNativeSql(
							"insert into PriceMedService (medService_id,pricePosition_id) values ('"
							+aMedService
							+"','"+aPricePosition+"')") ;
				}
			}
		}
		return "Соответствие установлено" ;
	}
	public String getCostByPriceMedService(Long aPriceMedService, HttpServletRequest aRequest) throws Exception {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		
		//IContractService service = Injection.find(aRequest).getService(IContractService.class) ;
		
		StringBuilder sql = new StringBuilder() ;
		sql.append("") ;
		sql.append("select pp.cost,pp.id ") ;
		sql.append(" from PriceMedService pms left join PricePosition pp on pp.id=pms.pricePosition_id");
		sql.append(" where pms.id='")
		.append(aPriceMedService).append("' and pp.cost>0") ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1) ;
		
		return list.isEmpty()?"":""+list.iterator().next().get1() ;
	}

}
