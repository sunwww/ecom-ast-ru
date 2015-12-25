package ru.ecom.mis.web.dwr.contract;

import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

public class ContractServiceJs {
	
	public String saveWorkFunctionService(
 			String aMedServiciesId, String aVWF,String aWF,String aLpu,String aBedType,String aBedSubType
      		 ,String aRoomType,String aPrescriptType,String aNoActiveByPrescript,HttpServletRequest aRequest) throws NamingException {
		StringBuilder sql = new StringBuilder() ;
		String[][] fld = new String[9][2] ;
		fld[0][1] = "medService_id" ;
		fld[1][0]= aVWF; fld[1][1] = "vocWorkFunction_id" ;
		fld[2][0]= aWF; fld[2][1] = "workFunction_id" ;
		fld[3][0]= aLpu; fld[3][1] = "lpu_id" ;
		fld[4][0]= aBedType; fld[4][1] = "bedType_id" ;
		fld[5][0]= aBedSubType; fld[5][1] = "bedSubType_id" ;
		fld[6][0]= aRoomType; fld[6][1] = "roomType_id" ;
		fld[7][0]= aPrescriptType; fld[7][1] = "prescriptType_id" ;
		fld[8][0]= aNoActiveByPrescript; fld[8][1] = "noActiveByPrescript" ;
		String[] ids = aMedServiciesId.split(",") ;
		for (int i=0;i<fld.length-1;i++) {
			if (fld[i][0]==null||fld[i][0].equals("0")||fld[i][0].equals("")) {fld[i][0]=null;}
		}
		for (String medService:ids) {
			fld[0][0]= medService;
			sql.append("select id from workfunctionservice where");
			for (int i=0;i<fld.length-1;i++) {
				if (i>0) sql.append(" and ") ;
				sql.append(" ").append(fld[i][1]).append(" ") ;if (fld[i][0]!=null) {sql.append(" = ").append(fld[i][0]);} else {sql.append(" is null") ;}
			}
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
			Collection<WebQueryResult> col = service.executeNativeSql(sql.toString()) ;
			if (col.isEmpty()) {
				sql = new StringBuilder() ;
				sql.append("insert into workfunctionservice (");
				for (int i=0;i<fld.length;i++) {
					if (i>0) sql.append(",") ;
					sql.append(fld[i][1]).append(" ") ;
				}
				sql.append(") values (") ;
				for (int i=0;i<fld.length;i++) {
					if (i>0) sql.append(",") ;
					if (fld[i][0]!=null) {
						sql.append("'").append(fld[i][0]).append("' ") ;
					} else {
						sql.append("null") ;
					}
				}
				sql.append(")") ;
				service.executeUpdateNativeSql(sql.toString()) ;
			} else {
				sql = new StringBuilder() ;
				sql.append("update workfunctionservice set noActiveByPrescript='").append(aNoActiveByPrescript).append("' where id=").append(col.iterator().next().get1());
				service.executeUpdateNativeSql(sql.toString()) ;
			}
		}
		return "" ;
	}
	public String addContractPerson(
   		 Long aIdPat,String aField,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		
		StringBuilder res = new StringBuilder() ;
		res.append(aIdPat) ;
		res.append("#") ;
		service.executeUpdateNativeSql("insert into ContractPerson (patient_id,dtype) values ('"
				+aIdPat+"','NaturalPerson')") ;
		Collection<WebQueryResult> col = service.executeNativeSql("select id from ContractPerson where patient_id="+aIdPat) ;
		if (col.isEmpty()) {
			throw new IllegalArgumentException("ОШИБКА ПРИ ОБРАБОТКА ДАННЫХ!!!") ;
		} else {
			res.append(col.iterator().next().get1()) ;
		}
		res.append("#") ;
		res.append(aField) ;
		return res.toString();
		
	}
	public String updateService(String aTable, String aId, String aTable1, String aId1
			, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		if (aTable1.toUpperCase().equals("MEDSERVICE")) {
			if (aTable.toUpperCase().equals("VOCMEDSERVICE")) {
				sql.append("update MedService set isNoOmc=null,vocMedService_id='").append(aId).append("' where id=").append(aId1) ;
				service.executeUpdateNativeSql(sql.toString()) ;
			} else if (aTable.toUpperCase().equals("PRICEPOSITION")) {
				aTable1="PRICEPOSITION" ;aTable="MEDSERVICE" ;
				String id = aId1 ;
				aId1=aId;aId=id;
			}	
		} 
		if (aTable1.toUpperCase().equals("PRICEPOSITION")) {
			if (aTable.toUpperCase().equals("MEDSERVICE")) {
				sql.append("select id from pricemedservice where medService_id='").append(aId).append("' and pricePosition_id='").append(aId1).append("'") ;
				Collection<WebQueryResult> col = service.executeNativeSql(sql.toString()) ;
				if (col.isEmpty()) {
					sql = new StringBuilder() ;
					sql.append("select id from pricemedservice where medService_id is null and pricePosition_id='").append(aId1).append("'") ;
					col.clear() ;
					col = service.executeNativeSql(sql.toString()) ;
					if (col.isEmpty()) {
						sql = new StringBuilder() ;
						sql.append("insert into PriceMedService (medService_id,pricePosition_id) values ('").append(aId).append("','").append(aId1).append("') ") ;
						service.executeUpdateNativeSql(sql.toString()) ;
					} else {
						sql = new StringBuilder() ;
						sql.append("update PriceMedService set medService_id='").append(aId).append("' where id=").append(col.iterator().next().get1()) ;
						service.executeUpdateNativeSql(sql.toString()) ;
						
					}
				}
			}
		}
		return "" ;
	}
	public String findService(String aTable, String aServiceId, String aTable1, Long aPriceList, String aCode, String aName
			, String aJavascript, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select p.id as pid,p.code as pcode,p.name as pname from ").append(aTable).append(" p where ") ;
		if (aTable.toUpperCase().trim().equals("PRICEPOSITION")) {
			sql.append(" p.priceList_id='").append(aPriceList).append("' and ") ;
		}
		boolean isNext = false;
		if (aCode!=null && !aCode.equals("")) {
			sql.append(" p.code like '%").append(aCode.toUpperCase()).append("%'") ;
			isNext=true ;
		}
		if (aName!=null && !aName.equals("")) {
			if (isNext) sql.append(" and ") ;
			sql.append(" upper(p.name) like '%").append(aName.toUpperCase()).append("%'") ;
		}
		
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString()) ;
		StringBuilder res = new StringBuilder() ;
		if (list.isEmpty()) return "НЕТ ДАННЫХ" ;
		res.append("<ll>" ) ;
		for (WebQueryResult wqr:list) {
			res.append("<li><a href=\"").append(aJavascript).append("('");
			res.append(aTable) ;
			res.append("','");
			res.append(wqr.get1()!=null?wqr.get1():"") ;
			res.append("','");
			res.append(aTable1) ;
			res.append("','");
			res.append(aServiceId) ;
			res.append("','");
			res.append(wqr.get2()).append(" ").append(wqr.get3()) ;
			res.append("')\">").append(wqr.get2()).append(" ").append(wqr.get3()).append("</a></li>");
		}
		res.append("</ll>" ) ;
		return res.toString() ;
	}
	public String findPerson(String aLastname, String aFirstname, String aMiddlename
			, String aBirthday, String aJavascript, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select p.id as pid,cp.id as cpid,p.lastname||' '||p.firstname||' '||p.middlename||' г.р. '||to_char(p.birthday,'dd.mm.yyyy') from patient p left join ContractPerson cp on cp.patient_id=p.id where ") ;
		if (aLastname!=null && !aLastname.equals("")) {
			sql.append(" p.lastname like '").append(aLastname.toUpperCase()).append("%'") ;
		}
		if (aFirstname!=null && !aFirstname.equals("")) {
			sql.append(" and p.firstname like '").append(aFirstname.toUpperCase()).append("%'") ;
		}
		if (aMiddlename!=null && !aMiddlename.equals("")) {
			sql.append(" and p.middlename like '").append(aMiddlename.toUpperCase()).append("%'") ;
		}
		if (aBirthday!=null && !aBirthday.equals("")) {
			sql.append(" and p.birthday = to_date('").append(aBirthday).append("','dd.mm.yyyy')") ;
		}
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString()) ;
		StringBuilder res = new StringBuilder() ;
		if (list.isEmpty()) return "НЕТ ДАННЫХ" ;
		res.append("<ll>" ) ;
		for (WebQueryResult wqr:list) {
			res.append("<li><a href=\"").append(aJavascript).append("('");
			res.append(wqr.get1()!=null?wqr.get1():"") ;
			res.append("','");
			res.append(wqr.get2()!=null?wqr.get2():"") ;
			res.append("','");
			res.append(wqr.get3()!=null?wqr.get3():"") ;
			res.append("')\">").append(wqr.get3()).append("</a></li>");
		}
		res.append("</ll>" ) ;
		return res.toString() ;
	}
	
	public String updateExtDispPlanService(Long aPlan, String aAction, Long aServiceId, Long aAgeGroup, Long aSex, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String actionNext="" ;
		Long sexOther = aSex!=null && aSex.equals(Long.valueOf(1))?Long.valueOf("2"):Long.valueOf("1") ;
		System.out.println("action="+aAction+"=") ;
		if (aAction!=null && (aAction.equals("Д") || aAction.equals("-"))) {
			Collection<WebQueryResult> list = service.executeNativeSql("select edps.id,edps.sex_id "
				+"from ExtDispPlanService edps left join vocsex vs on vs.id=edps.sex_id where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId
				+"' and edps.ageGroup_id='"+aAgeGroup+"' and (vs.omcCode='"+sexOther+"' or vs.id is null)") ;
			if (!list.isEmpty()) {
				list = service.executeNativeSql("select edps.id,edps.sex_id "
						+"from ExtDispPlanService edps left join vocsex vs on vs.id=edps.sex_id where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId
						+"' and edps.ageGroup_id='"+aAgeGroup+"' and (vs.omcCode='"+sexOther+"')") ;
				if (!list.isEmpty()) {
					service.executeUpdateNativeSql("update "
							+" ExtDispPlanService edps "
							+" set sex_id=null"
							+" where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId
							+"' and edps.ageGroup_id='"+aAgeGroup+"'") ;
				}
			} else {
				try {
					Collection<WebQueryResult> wqrSex = service.executeNativeSql("select vs.id,vs.name from vocsex vs where vs.omcCode='"+aSex+"'",1) ;
					if (wqrSex.isEmpty()) {
						service.executeUpdateNativeSql("insert into "
								+" ExtDispPlanService "
								+" (sex_id,plan_id,serviceType_id,ageGroup_id) values ((select min(vs.id) from vocsex vs where vs.omcCode='"+aSex+"'),'"+aPlan+"','"+aServiceId+"','"+aAgeGroup+"')"
								) ;
					} else {
						service.executeUpdateNativeSql("insert into "
								+" ExtDispPlanService "
								+" (sex_id,plan_id,serviceType_id,ageGroup_id) values ('"+wqrSex.iterator().next().get1()+"','"+aPlan+"','"+aServiceId+"','"+aAgeGroup+"')"
								) ;
					}
				} catch (Exception e) {
					service.executeUpdateNativeSql("alter table extdispplanservice alter column id set default nextval('extdispplanservice_sequence')") ;
					service.executeUpdateNativeSql("insert into "
							+" ExtDispPlanService "
							+" (sex_id,plan_id,serviceType_id,ageGroup_id) values ((select min(vs.id) from vocsex vs where vs.omcCode='"+aSex+"'),'"+aPlan+"','"+aServiceId+"','"+aAgeGroup+"')"
							) ;
				}
			}
			
			actionNext=aAction.equals("Д")?"У":"+" ;
		} else if (aAction!=null && (aAction.equals("У")||aAction.equals("+"))) {
			service.executeUpdateNativeSql("delete "
					+" from ExtDispPlanService edps where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId
					+"' and edps.ageGroup_id='"+aAgeGroup+"' and (select vs.omcCode from vocsex vs where vs.id=edps.sex_id)='"+aSex+"'") ;
			service.executeUpdateNativeSql("update "
					+" ExtDispPlanService edps "
					+" set sex_id=(select min(vs.id) from vocsex vs where vs.omcCode='"+sexOther+"')"
					+" where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId
					+"' and edps.ageGroup_id='"+aAgeGroup+"' and edps.sex_id is null") ;
			actionNext=aAction.equals("У")?"Д":"-" ;
		} 
		return actionNext ;
	}

	public String updateExtDispPlanServiceAllAge(Long aPlan, String aAction, Long aServiceId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String actionNext="" ;
		System.out.println("action="+aAction+"=") ;
		Collection<WebQueryResult> listAge = service.executeNativeSql("select veda.id,veda.name from VocExtDispAgeGroup veda left join ExtDispPlan edp on edp.dispType_id=veda.dispType_id where edp.id="+aPlan) ;
		if (aAction!=null && (aAction.equals("+"))) {
			for (WebQueryResult wqrAge:listAge) {
			String aAgeGroup=""+wqrAge.get1() ;
			
				Collection<WebQueryResult> list = service.executeNativeSql("select edps.id,edps.sex_id "
					+"from ExtDispPlanService edps left join vocsex vs on vs.id=edps.sex_id where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId
					+"' and edps.ageGroup_id='"+aAgeGroup+"'") ;
				if (!list.isEmpty()) {
					service.executeUpdateNativeSql("update "
								+" ExtDispPlanService edps "
								+" set sex_id=null"
								+" where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId
								+"' and edps.ageGroup_id='"+aAgeGroup+"'") ;
				} else {
					try {
						service.executeUpdateNativeSql("insert into "
								+" ExtDispPlanService "
								+" (plan_id,serviceType_id,ageGroup_id) values ('"+aPlan+"','"+aServiceId+"','"+aAgeGroup+"')"
								) ;
					} catch (Exception e) {
						service.executeUpdateNativeSql("alter table extdispplanservice alter column id set default nextval('extdispplanservice_sequence')") ;
						service.executeUpdateNativeSql("insert into "
								+" ExtDispPlanService "
								+" (plan_id,serviceType_id,ageGroup_id) values ('"+aPlan+"','"+aServiceId+"','"+aAgeGroup+"')"
								) ;
					}
				}
			} 
			actionNext="-" ;
		} else if (aAction!=null && (aAction.equals("-"))) {
			service.executeUpdateNativeSql("delete "
					+" from ExtDispPlanService edps where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId+"'") ;
			actionNext="+" ;
		}
		return aAction ;
	}

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
