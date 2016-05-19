package ru.ecom.mis.ejb.service.contract;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.contract.ContractAccount;
import ru.ecom.mis.ejb.domain.contract.ContractAccountMedService;
import ru.ecom.mis.ejb.domain.contract.ContractGuarantee;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.ecom.mis.ejb.domain.contract.PriceMedService;
import ru.nuzmsh.util.format.DateFormat;

@Stateless
@Remote(IContractService.class)
public class ContractServiceBean implements IContractService {
	public static void unionServiceWithContract(String aDateFrom, String aDateTo, EntityManager aManager) {
		
	}
	private Long getMedService(Long aDepartment, Long aBedType, Long aBedSubType, Long aRoomType) {

		if (aRoomType==null) aRoomType=Long.valueOf(3);
		if (aRoomType.equals(Long.valueOf(1))) aRoomType=Long.valueOf(3);
		String idsertypebed = "11" ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select wfs.medservice_id from workfunctionservice wfs left join medservice ms on ms.id=wfs.medservice_id" );
		sql.append(" where wfs.lpu_id=").append(aDepartment) ;
		sql.append(" and wfs.bedtype_id=").append(aBedType);
		sql.append(" and wfs.bedsubtype_id=").append(aBedSubType);
		sql.append(" and wfs.roomtype_id=").append(aRoomType);
		sql.append(" and ms.servicetype_id='").append(idsertypebed).append("'"); 
		List<Object> list=theManager.createNativeQuery(sql.toString()).getResultList() ;
		if (list.isEmpty()) {
			sql = new StringBuilder() ;
			aRoomType=Long.valueOf(2);
			sql.append("select wfs.medservice_id from workfunctionservice wfs left join medservice ms on ms.id=wfs.medservice_id " );
			sql.append(" where wfs.lpu_id=").append(aDepartment) ;
			sql.append(" and wfs.bedtype_id=").append(aBedType);
			sql.append(" and wfs.bedsubtype_id=").append(aBedSubType);
			sql.append(" and wfs.roomtype_id=").append(aRoomType);
			sql.append(" and ms.servicetype_id='").append(idsertypebed).append("'"); 
			list=theManager.createNativeQuery(sql.toString()).getResultList() ;
			if (list.isEmpty()) {
				if (list.isEmpty()) {
					sql = new StringBuilder() ;
					aRoomType=Long.valueOf(1);
					sql.append("select wfs.medservice_id from workfunctionservice wfs left join medservice ms on ms.id=wfs.medservice_id " );
					sql.append(" where wfs.lpu_id=").append(aDepartment) ;
					sql.append(" and wfs.bedtype_id=").append(aBedType);
					sql.append(" and wfs.bedsubtype_id=").append(aBedSubType);
					sql.append(" and wfs.roomtype_id=").append(aRoomType);
					sql.append(" and ms.servicetype_id='").append(idsertypebed).append("'"); 
					list=theManager.createNativeQuery(sql.toString()).getResultList() ;
					if (list.isEmpty()) {
						return null ;
					} else {
						return ConvertSql.parseLong(list.get(0)) ;
					}
				} else {
					return ConvertSql.parseLong(list.get(0)) ;
				}
			} else {
				return ConvertSql.parseLong(list.get(0)) ;
			}
		} else {
			return ConvertSql.parseLong(list.get(0)) ;
		}
	}
	private Long getPriceMedService(Long aPriceList,Long aMedService) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select pms.id from priceposition pp " );
		sql.append(" left join pricemedservice pms on pms.priceposition_id=pp.id " );
		sql.append(" where pp.pricelist_id=").append(aPriceList).append(" and pms.medservice_id=").append(aMedService) ;
		List<Object> list=theManager.createNativeQuery(sql.toString()).getResultList() ;
		if (list.isEmpty()) {
			return null ;
		} else {
			return ConvertSql.parseLong(list.get(0)) ;
		}
		/*
		 left join workfunctionservice wfs on wfs.lpu_id=slo.department_id
    and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id
    and wfs.roomType_id=wp.roomType_id
left join medservice ms on ms.id=wfs.medservice_id
    left join pricemedservice pms on pms.medservice_id=wfs.medservice_id
        left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='${priceList}' 
and (pp.isvat is null or pp.isvat='0')

		 */
		//return null ;
	}
	@SuppressWarnings("unchecked")
	public Long getMedContractBySmo(String aDtypeSmo, Long aIdSmo, boolean aIsRecalc) {
		return null ;
	}
	public Long setSmoByAccount(Long aAccount,String aDtypeSmo, Long aIdSmo, boolean aIsRecalc) throws ParseException {
		Long contract = null ;
		Long diag = null ;
		Long guarLetter = null ;
		Long priceList = null ;
		ContractGuarantee sp = null ;
		Long patient = null ;
		Long servicestream = null ;
		String polNumber = null ;
		//String date = null ;
		if (aIsRecalc) {
			theManager.createNativeQuery("delete from ContractAccountMedService cams where mainparent="+aIdSmo).executeUpdate() ;
		}
		ContractAccount account = theManager.find(ContractAccount.class, aAccount) ;
		if (aDtypeSmo.equals("HOSPITALMEDCASE")) {
			List<Object[]> l = theManager.createNativeQuery("select sls.patient_id as cpid,mc.id as mcid,mc.priceList_id as pricelist"
				+" , (select max(cg.id) from ContractGuarantee cg where cg.contract_id=mc.id and cg.contractPerson_id=cpp.id and sls.datestart between cg.actionDate and cg.actionDateTo) as cgid"
				+", to_char(sls.datestart,'dd.mm.yyyy') as datestart"
				+", to_char(coalesce(sls.datefinish,current_date),'dd.mm.yyyy') as datefinish"
				+", mp.polNumber as polnumber"
				+",pat.lastname, pat.firstname,pat.middlename,to_char(pat.birthday,'dd.mm.yyyy') as birthday"
				+",(select max(diag.idc10_id) from diagnosis diag"
				+"    	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
				+"    	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id"
				+"    	where diag.medCase_id=sls.id"  
				+" and vpd.code='1' and vdrt.code='4') as diag, case when vhr.code='11' then vhr.code else null end as result"
				+",sls.serviceStream_id"
				+" from medcase sls" 
				+" left join vochospitalizationresult vhr on vhr.id=sls.result_id"
				+" left join Patient pat on pat.id=sls.patient_id"
				+" left join medcase_medpolicy mpmc on mpmc.medcase_id=sls.id"
				+" left join medpolicy mp on mp.id=mpmc.policies_id"
				+" left join contractperson cp on cp.regcompany_id=mp.company_id"
				+" left join contractperson cpp on cpp.patient_id=sls.patient_id"
				+" left join medcontract mc on mc.servicestream_id=sls.serviceStream_id and cp.id=mc.customer_id"
				+" where sls.id="+aIdSmo
				//+" and mc.id is not null"
				).getResultList();
			String lastname=null; 
			String firstname=null; 
			String middlename=null;
			Boolean isDeath=null;
			
			Date birthday=null; 
			String datestart=null; 
			String datefinish=null; 
			if (l.size()==1) {
				
				contract = ConvertSql.parseLong(l.get(0)[1]) ;
				if (contract==null) {
					contract = account.getContract().getId() ;
				}
				patient = ConvertSql.parseLong(l.get(0)[0]) ;
				servicestream = ConvertSql.parseLong(l.get(0)[13]) ;
				
				guarLetter = ConvertSql.parseLong(l.get(0)[3]) ;
				if (guarLetter!=null) {
					sp = theManager.find(ContractGuarantee.class, guarLetter);
				}
				priceList = ConvertSql.parseLong(l.get(0)[2]) ;
				datestart= ""+l.get(0)[4] ;
				datefinish = ""+l.get(0)[5] ;
				polNumber = ""+l.get(0)[6] ;
				lastname = ""+l.get(0)[7] ;
				firstname = ""+l.get(0)[8] ;
				middlename = ""+l.get(0)[9] ;
				birthday = DateFormat.parseSqlDate(""+l.get(0)[10]) ;
				diag = ConvertSql.parseLong(l.get(0)[11]) ;
				if (ConvertSql.parseLong(l.get(0)[12])!=null) isDeath=true;
			} 
				StringBuilder sql = new StringBuilder() ;
				sql.append("select pat.id as patid,pat.lastname, pat.firstname,pat.middlename,pat.birthday");
				sql.append(",slo.id as sloid,ml.id as mlid,vbt.id as vbtid,vbst.id as vbstid,vrt.id as vrtid") ;
				sql.append(",to_char(slo.datestart,'dd.mm.yyyy') as slodatestart, to_char(coalesce(slo.datefinish,slo.transferdate,current_date),'dd.mm.yyyy') as slodatefinish") ;
				sql.append(",case when coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart=0 then '1' else coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart+case when vht.code='ALLTIMEHOSP' then 0 else 1 end end as cntDays");
				sql.append(" from medcase slo ");
				sql.append("       left join medcase sls on sls.id=slo.parent_id") ;
				sql.append(" left join Vochosptype vht on vht.id=sls.hosptype_id");
				sql.append(" left join bedfund bf on bf.id=slo.bedfund_id") ;
				sql.append(" left join vocbedtype vbt on vbt.id=bf.bedtype_id") ;
				sql.append(" left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id");
				sql.append(" left join workPlace wp on wp.id=slo.roomNumber_id");
				sql.append(" left join Patient pat on pat.id=slo.patient_id");
				sql.append(" left join VocRoomType vrt on vrt.id=wp.roomType_id");
				sql.append(" left join mislpu ml on ml.id=slo.department_id");
				sql.append(" where slo.parent_id='").append(aIdSmo).append("'  and slo.dtype='DepartmentMedCase'");
				sql.append("  group by pat.id,pat.lastname, pat.firstname,pat.middlename,pat.birthday,slo.id,ml.id,vbt.id,vbst.id,vrt.id,slo.datefinish,slo.transferdate,slo.datestart,vht.code") ;
				List<Object[]> listSlo = theManager.createNativeQuery(sql.toString()).getResultList();
				
				for (Object[] obj:listSlo) {
					ContractAccountMedService cams = new ContractAccountMedService() ;
					cams.setDiagnosis(diag) ;
					cams.setIsDeath(isDeath) ;
					cams.setMainParent(aIdSmo) ;
					cams.setLastname(lastname) ;
					cams.setFirstname(firstname) ;
					cams.setMiddlename(middlename) ;
					cams.setBirthday(birthday) ;
					cams.setAccount(account) ;
					cams.setTypeService("BED") ;
					Long service = getMedService(ConvertSql.parseLong(obj[6]), ConvertSql.parseLong(obj[7]), ConvertSql.parseLong(obj[8]), ConvertSql.parseLong(obj[9]))  ;
					if (service!=null) {
						Long prm = getPriceMedService(priceList, service) ;
						cams.setServiceIn(service) ;
						PriceMedService pms = null ;
						if (prm!=null) {
							pms = theManager.find(PriceMedService.class, prm) ;
							cams.setMedService(pms) ;
							cams.setCost(pms.getPricePosition().getCost()) ;
						}
						
					}
					cams.setCountMedService(ConvertSql.parseLong(obj[12]).intValue()) ;
					cams.setDateFrom(DateFormat.parseSqlDate(""+obj[10])) ;
					cams.setDateTo(DateFormat.parseSqlDate(""+obj[11])) ;
					//if (guarLetter!=null) {
						
						cams.setGuarantee(sp) ;
					//}
					cams.setSmo(ConvertSql.parseLong(obj[5])) ;
					cams.setPolNumber(polNumber) ;
					theManager.persist(cams) ;

				}
				sql = new StringBuilder() ;
				sql.append("select so.id as soid,to_char(so.operationdate,'dd.mm.yyyy') as operdate,ms.id as msid,wf.id as wfid") ;
				sql.append("      from SurgicalOperation so") ;
				sql.append("      left join workfunction wf on wf.id=so.surgeon_id") ;
				sql.append("      left join vocworkfunction vwf on vwf.id=wf.workfunction_id");
				sql.append("      left join worker w on w.id=wf.worker_id");
				sql.append("      left join patient wp on wp.id=w.person_id");
				sql.append("      left join medcase slo on slo.id=so.medcase_id");
				sql.append("      left join vocservicestream vss on vss.id=so.servicestream_id");
				sql.append("      left join medservice ms on ms.id=so.medservice_id");
				sql.append("      where  (slo.parent_id='").append(aIdSmo).append("' or slo.id='").append(aIdSmo).append("')");
				List<Object[]> listSo=theManager.createNativeQuery(sql.toString()).getResultList() ;
				for (Object[] obj:listSo) {
					ContractAccountMedService cams = new ContractAccountMedService() ;
					cams.setDiagnosis(diag) ;
					cams.setIsDeath(isDeath) ;
					cams.setMainParent(aIdSmo) ;
					cams.setLastname(lastname) ;
					cams.setFirstname(firstname) ;
					cams.setMiddlename(middlename) ;
					cams.setBirthday(birthday) ;
					cams.setAccount(account) ;
					cams.setTypeService("SURGICALOPERATION") ;
					Long service = ConvertSql.parseLong(obj[2]) ;
					if (service!=null) {
						Long prm = getPriceMedService(priceList, service) ;
						cams.setServiceIn(service) ;
						if (prm!=null) {
							PriceMedService pms = theManager.find(PriceMedService.class, prm) ;
							cams.setMedService(pms) ;
							cams.setCost(pms.getPricePosition().getCost()) ;
						}
					}
					cams.setCountMedService(1) ;
					cams.setDateFrom(DateFormat.parseSqlDate(""+obj[1])) ;
					cams.setGuarantee(sp) ;
					cams.setSmo(ConvertSql.parseLong(obj[0])) ;
					cams.setPolNumber(polNumber) ;
					cams.setDoctor(ConvertSql.parseLong(obj[3])) ;
					theManager.persist(cams) ;
				}
				sql = new StringBuilder() ;
				sql.append("      select so.id as smoid,to_char(so.dateStart,'dd.mm.yyyy') as dt,ms.id as msid,wf.id as wfid from MedCase so") ;
				sql.append("      left join VocIdc10 mkb on mkb.id=so.idc10_id");
				sql.append("      left join workfunction wf on wf.id=so.workFunctionExecute_id");
				sql.append("      left join vocworkfunction vwf on vwf.id=wf.workfunction_id");
				sql.append("      left join worker w on w.id=wf.worker_id");
				sql.append("      left join patient wp on wp.id=w.person_id");
				sql.append("      left join medcase slo on slo.id=so.parent_id");
				sql.append("      left join vocservicestream vss on vss.id=so.servicestream_id");
				sql.append("      left join medservice ms on ms.id=so.medservice_id");
				sql.append("      where");
				sql.append("      (slo.parent_id='").append(aIdSmo).append("' or slo.id='").append(aIdSmo).append("')");
				sql.append("      and upper(so.dtype)='SERVICEMEDCASE' and upper(slo.dtype)!='VISIT'") ;
				List<Object[]> listMs=theManager.createNativeQuery(sql.toString()).getResultList() ;
				for (Object[] obj:listMs) {
					ContractAccountMedService cams = new ContractAccountMedService() ;
					cams.setDiagnosis(diag) ;
					cams.setIsDeath(isDeath) ;
					cams.setMainParent(aIdSmo) ;
					cams.setLastname(lastname) ;
					cams.setFirstname(firstname) ;
					cams.setMiddlename(middlename) ;
					cams.setBirthday(birthday) ;
					cams.setAccount(account) ;
					cams.setTypeService("ADDSERVICE") ;
					Long service = ConvertSql.parseLong(obj[2]) ;
					if (service!=null) {
						Long prm = getPriceMedService(priceList, service) ;
						cams.setServiceIn(service) ;
						if (prm!=null) {
							PriceMedService pms = theManager.find(PriceMedService.class, prm) ;
							cams.setMedService(pms) ;
							cams.setCost(pms.getPricePosition().getCost()) ;
						}
					}
					cams.setCountMedService(1) ;
					cams.setDateFrom(DateFormat.parseSqlDate(""+obj[1])) ;
					cams.setGuarantee(sp) ;
					cams.setSmo(ConvertSql.parseLong(obj[0])) ;
					cams.setPolNumber(polNumber) ;
					cams.setDoctor(ConvertSql.parseLong(obj[3])) ;
					theManager.persist(cams) ;
				}
				sql = new StringBuilder() ;
				sql.append("select vis.id as visid,to_char(vis.datestart,'dd.mm.yyyy'),ms.id as msid,wf.id as wfid") ;
				sql.append("      from medcase vis") ;
				sql.append("      left join workfunction wf on wf.id=vis.workfunctionexecute_id");
				sql.append("      left join vocworkfunction vwf on vwf.id=wf.workfunction_id");
				sql.append("      left join worker w on w.id=wf.worker_id");
				sql.append("      left join patient wp on wp.id=w.person_id");
				sql.append("      left join vocservicestream vss on vss.id=vis.servicestream_id");
				sql.append("      left join medcase smc on smc.parent_id=vis.id");
				sql.append("      left join medservice ms on ms.id=smc.medservice_id");
				sql.append("      where vis.patient_id='").append(patient).append("' and (vis.datestart between to_date('").append(datestart).append("','dd.mm.yyyy') and to_date('").append(datefinish).append("','dd.mm.yyyy')");
				sql.append("       and upper(vis.dtype)='VISIT' and (vss.code='HOSPITAL' or vss.id='").append(servicestream).append("' or vss.code='OTHER')");
				sql.append("       or ");
				sql.append("       vis.datestart-to_date('").append(datestart).append("','dd.mm.yyyy') = -1");
				sql.append("       and upper(vis.dtype)='VISIT' and ( vss.id='").append(servicestream).append("' )");
				sql.append("       )");
				sql.append("        and (vis.noActuality='0' or vis.noActuality is null) ");
				List<Object[]> listVis = theManager.createNativeQuery(sql.toString()).getResultList();
				for (Object[] obj:listVis) {
					ContractAccountMedService cams = new ContractAccountMedService() ;
					cams.setDiagnosis(diag) ;
					cams.setIsDeath(isDeath) ;
					cams.setMainParent(aIdSmo) ;
					cams.setLastname(lastname) ;
					cams.setFirstname(firstname) ;
					cams.setMiddlename(middlename) ;
					cams.setBirthday(birthday) ;
					cams.setAccount(account) ;
					cams.setTypeService("VISSERVICE") ;
					Long service = ConvertSql.parseLong(obj[2]) ;
					if (service!=null) {
						Long prm = getPriceMedService(priceList, service) ;
						cams.setServiceIn(service) ;
						if (prm!=null) {
							PriceMedService pms = theManager.find(PriceMedService.class, prm) ;
							cams.setMedService(pms) ;
							cams.setCost(pms.getPricePosition().getCost()) ;
						}
					}
					cams.setCountMedService(1) ;
					cams.setDateFrom(DateFormat.parseSqlDate(""+obj[1])) ;
					cams.setGuarantee(sp) ;
					cams.setSmo(ConvertSql.parseLong(obj[0])) ;
					cams.setPolNumber(polNumber) ;
					cams.setDoctor(ConvertSql.parseLong(obj[3])) ;
					theManager.persist(cams) ;
				}

				
				
				sql = new StringBuilder() ;
				sql.append("select vis.id as visid,to_char(vis.datestart,'dd.mm.yyyy'),ms.id as msid,wf.id as wfid") ;
				sql.append("      from medcase vis");
				sql.append("      left join workfunction wf on wf.id=vis.workfunctionexecute_id");
				sql.append("      left join vocworkfunction vwf on vwf.id=wf.workfunction_id");
				sql.append("      left join worker w on w.id=wf.worker_id");
				sql.append("      left join patient wp on wp.id=w.person_id");
				sql.append("      left join vocservicestream vss on vss.id=vis.servicestream_id");
				sql.append("      left join medcase smc on smc.parent_id=vis.id");
				sql.append("      left join medservice ms on ms.id=smc.medservice_id");
				sql.append("      where vis.parent_id='").append(aIdSmo).append("'");
				sql.append("      and vis.datestart between to_date('").append(datestart).append("','dd.mm.yyyy') and to_date('").append(datefinish).append("','dd.mm.yyyy')");
				sql.append("       and upper(vis.dtype)='VISIT' and upper(smc.dtype)='SERVICEMEDCASE'");
				sql.append("        and (vss.code='HOSPITAL' or vss.id is null)");
				sql.append("        and (vis.noActuality='0' or vis.noActuality is null) ");
				List<Object[]> listLab = theManager.createNativeQuery(sql.toString()).getResultList();
				for (Object[] obj:listLab) {
					ContractAccountMedService cams = new ContractAccountMedService() ;
					cams.setDiagnosis(diag) ;
					cams.setMainParent(aIdSmo) ;
					cams.setIsDeath(isDeath) ;
					cams.setLastname(lastname) ;
					cams.setFirstname(firstname) ;
					cams.setMiddlename(middlename) ;
					cams.setBirthday(birthday) ;
					cams.setAccount(account) ;
					cams.setTypeService("LABSERVICE") ;
					Long service = ConvertSql.parseLong(obj[2]) ;
					if (service!=null) {
						Long prm = getPriceMedService(priceList, service) ;
						cams.setServiceIn(service) ;
						if (prm!=null) {
							PriceMedService pms = theManager.find(PriceMedService.class, prm) ;
							cams.setMedService(pms) ;
							cams.setCost(pms.getPricePosition().getCost()) ;
						}
					}
					cams.setCountMedService(1) ;
					cams.setDateFrom(DateFormat.parseSqlDate(""+obj[1])) ;
					cams.setGuarantee(sp) ;
					cams.setSmo(ConvertSql.parseLong(obj[0])) ;
					cams.setPolNumber(polNumber) ;
					cams.setDoctor(ConvertSql.parseLong(obj[3])) ;
					theManager.persist(cams) ;
				}

				//theManager.createNativeQuery("delete from ContractMedCase where mainMedCase_id="+aIdSmo) ;
				//theManager.createNativeQuery("insert into MainMedCase (mainmedcase,Contract,Guarantee) values ("+aIdSmo+","+contract+","+guarLetter+")") ;
		}
	
			
		/*} else if (aDtypeSmo.equals("POLYCLINICMEDCASE")) {
			
			List<Object[]> l = theManager.createNativeQuery("select sls.patient_id as cpid,mc.id as mcid from medcase slo" 
					+" left join medcase sls on sls.id=slo.parent_id"
					+" left join medcase_medpolicy mpmc on mpmc.medcase_id=sls.id"
					+" left join medpolicy mp on mp.id=mpmc.policies_id"
					+" left join contractperson cp on cp.regcompany_id=mp.company_id"
					+" left join medcontract mc on mc.servicestream_id=sls.serviceStream_id and cp.id=mc.customer_id"
					+" where slo.id="+aIdSmo+" and mc.id is not null").getResultList();
			if (l.size()==1) {
				contract = ConvertSql.parseLong(l.get(0)[1]) ;
				patient = ConvertSql.parseLong(l.get(0)[0]) ;
			}
		}
		/*} else if (aDtypeSmo.equals("DEPARTMENTMEDCASE")) {
			List<Object[]> l = theManager.createNativeQuery("select sls.patient_id as cpid,mc.id as mcid from medcase slo" 
					+" left join medcase sls on sls.id=slo.parent_id"
					+" left join medcase_medpolicy mpmc on mpmc.medcase_id=sls.id"
					+" left join medpolicy mp on mp.id=mpmc.policies_id"
					+" left join contractperson cp on cp.regcompany_id=mp.company_id"
					+" left join medcontract mc on mc.servicestream_id=sls.serviceStream_id and cp.id=mc.customer_id"
					+" where slo.id="+aIdSmo+" and mc.id is not null").getResultList();
			if (l.size()==1) {
				contract = ConvertSql.parseLong(l.get(0)[1]) ;
				patient = ConvertSql.parseLong(l.get(0)[0]) ;
			}
		} else if (aDtypeSmo.equals("SURGICALOPERATION")) {
			List<Object[]> l = theManager.createNativeQuery("select sls.patient_id as cpid,mc.id as mcid from surgicaloperation so" 
					+" left join medcase slo on slo.id=so.medcase_id"
					+" left join medcase sls on sls.id=slo.parent_id"
					+" left join medcase_medpolicy mpmc on mpmc.medcase_id=sls.id"
					+" left join medpolicy mp on mp.id=mpmc.policies_id"
					+" left join contractperson cp on cp.regcompany_id=mp.company_id"
					+" left join medcontract mc on mc.servicestream_id=sls.serviceStream_id and cp.id=mc.customer_id"
					+" where slo.id="+aIdSmo+" and mc.id is not null").getResultList();
				if (l.size()==1) {
					contract = ConvertSql.parseLong(l.get(0)[1]) ;
				} else {
					l = theManager.createNativeQuery("select sls.patient_id as cpid,mc.id as mcid from surgicaloperation so" 
							+" left join medcase sls on sls.id=so.medcase_id"
							+" left join medcase_medpolicy mpmc on mpmc.medcase_id=sls.id"
							+" left join medpolicy mp on mp.id=mpmc.policies_id"
							+" left join contractperson cp on cp.regcompany_id=mp.company_id"
							+" left join medcontract mc on mc.servicestream_id=sls.serviceStream_id and cp.id=mc.customer_id"
							+" where slo.id="+aIdSmo+" and mc.id is not null").getResultList();
						if (l.size()==1) contract = ConvertSql.parseLong(l.get(0)[1]) ;
				}
			
		} else if (aDtypeSmo.equals("SERVICEMEDCASE")) {
			List<Object[]> l = theManager.createNativeQuery("select cp.id as cpid,mc.id as mcid from medcase smc" 
					+" left join medcase slo on slo.id=smc.parent_id"
					+" left join medcase sls on sls.id=slo.parent_id"
					+" left join medcase_medpolicy mpmc on mpmc.medcase_id=sls.id"
					+" left join medpolicy mp on mp.id=mpmc.policies_id"
					+" left join contractperson cp on cp.regcompany_id=mp.company_id"
					+" left join medcontract mc on mc.servicestream_id=sls.serviceStream_id and cp.id=mc.customer_id"
					+" where slo.id="+aIdSmo+" and mc.id is not null").getResultList();
				if (l.size()==1) {
					contract = ConvertSql.parseLong(l.get(0)[1]) ;
				} else {
					l = theManager.createNativeQuery("select cp.id as cpid,mc.id as mcid from medcase smc" 
							+" left join medcase sls on sls.id=smc.medcase_id"
							+" left join medcase_medpolicy mpmc on mpmc.medcase_id=sls.id"
							+" left join medpolicy mp on mp.id=mpmc.policies_id"
							+" left join contractperson cp on cp.regcompany_id=mp.company_id"
							+" left join medcontract mc on mc.servicestream_id=sls.serviceStream_id and cp.id=mc.customer_id"
							+" where slo.id="+aIdSmo+" and mc.id is not null").getResultList();
					if (l.size()==1) contract = ConvertSql.parseLong(l.get(0)[1]) ;
				}
		}*/
		return contract ;
	}
	public void unionServiceWithContract(String aDateFrom, String aDateTo) {
		unionServiceWithContract(aDateFrom, aDateTo, theManager) ;
	}
	public void accountCreation(long aMonitorId, String aDateFrom,String aDateTo, Long aContract, String aAccountNumber) {
		IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Подготовка к импорту") ;
		try {
			MedContract contract = theManager.find(MedContract.class, aContract) ;
			ContractAccount ac = new ContractAccount() ;
			ac.setContract(contract) ;
			ac.setDateFrom(DateFormat.parseSqlDate(aDateFrom)) ;
			ac.setDateTo(DateFormat.parseSqlDate(aDateTo)) ;
			ac.setAccountNumber(aAccountNumber) ;
			theManager.persist(ac) ;
			StringBuilder sql = new StringBuilder() ;
			sql.append("select mc.id as mcid,cp.id as cpid ,vss.id as vssid, rc.id as rcid,lpu.id as lpuid,mc.priceList_id as pricelist")
				.append(" from medcontract mc")
				.append(" left join contractperson cp on cp.id=mc.customer_id")
				.append(" left join VocServiceStream vss on vss.id=mc.serviceStream_id")
				.append(" left join Reg_ic rc on rc.id=cp.regcompany_id")
				.append(" left join MisLpu lpu on lpu.id=cp.lpu_id") 
				.append(" where mc.id=").append(aContract);
			List<Object[]> list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			if (!list.isEmpty()) {
				Object[] par=list.get(0) ;
				sql = new StringBuilder() ;
				sql.append("select mc.id as mcid,mp.id as mpid,mc.patient_id from medcase mc")
					.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=mc.id")
					.append(" left join medpolicy mp on mp.id=mcmp.policies_id")
					.append(" where mc.dtype='HospitalMedCase' and mc.dateFinish between to_date('")
					.append(aDateFrom).append("','dd.mm.yyyy')")
					.append(" and to_date('")
					.append(aDateTo).append("','dd.mm.yyyy')") ;
				if (par[2]!=null) sql.append(" and mc.serviceStream_id=").append(par[2]) ;
				if (par[3]!=null) sql.append(" and mp.company_id=").append(par[3]) ;
				if (par[4]!=null) sql.append(" and mc.orderLpu_id=").append(par[3]) ;
				sql.append(" group by mc.id,mp.id,mc.patient_id") ;
				List<Object[]> listHosp = theManager.createNativeQuery(sql.toString()).getResultList() ;
				monitor = theMonitorService.startMonitor(aMonitorId, "Формирование счета по стационару. Найдено госпитализаций: "+listHosp.size(),listHosp.size()) ;
				
				for (Object[] hosp:listHosp) {
					// Койки
					sql = new StringBuilder() ;
					sql.append("select slo.id,max(pms.id) as ppcost")
						.append(" ,coalesce(slo.datefinish,slo.transferdate),slo.datestart")
						.append(" from medcase slo")
						.append(" left join medcase sls on sls.id=slo.parent_id")
						.append(" left join Vochosptype vht on vht.id=sls.hosptype_id")
						.append(" left join statisticstub ss on ss.id=sls.statisticStub_id")
						.append(" left join bedfund bf on bf.id=slo.bedfund_id")
						.append(" left join vocbedtype vbt on vbt.id=bf.bedtype_id")
						.append(" left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id")
						.append(" left join workPlace wp on wp.id=slo.roomNumber_id")
						.append(" left join Patient pat on pat.id=slo.patient_id")
						.append(" left join VocRoomType vrt on vrt.id=wp.roomType_id")
						.append(" left join mislpu ml on ml.id=slo.department_id")
						.append(" left join workfunctionservice wfs on wfs.lpu_id=slo.department_id")
						.append("     and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id")
						.append("     and wfs.roomType_id=wp.roomType_id")
						.append(" left join medservice ms on ms.id=wfs.medservice_id")
						.append(" left join pricemedservice pms on pms.medservice_id=wfs.medservice_id")
						.append(" left join priceposition pp on pp.id=pms.priceposition_id")
						.append(" and (pp.isvat is null or pp.isvat='0')")
						.append(" where slo.parent_id='").append(hosp[0]).append("'")
						.append("  and ms.servicetype_id='").append(11).append("' and pp.priceList_id='").append(par[5]).append("'")
						.append("  group by slo.id,ml.name,vbt.name,vbst.name,vrt.name,slo.datefinish,slo.transferdate,slo.datestart,vht.code") ;
					List<Object[]> bedL= theManager.createNativeQuery(sql.toString()).getResultList() ; 
					for (Object[] bed:bedL) {
						//System.out.println("bed="+bed[0]+" pp="+bed[1]) ;
						ContractAccountMedService cams = new ContractAccountMedService() ;
						cams.setTypeService("BED") ;
						cams.setAccount(ac) ;
						PriceMedService pms = theManager.find(PriceMedService.class, ConvertSql.parseLong(bed[1])) ;
						cams.setMedService(pms) ;
						cams.setCost(pms.getPricePosition().getCost()) ;
						cams.setCountMedService(1) ;
						//cams.setDateFrom() ;
						//cams.setDateTo() ;
						ContractGuarantee sp = theManager.find(ContractGuarantee.class, hosp[3]);
						cams.setGuarantee(sp) ;
						//cams.setLastname() ;
						//cams.setFirstname() ;
						//cams.setMiddlename() ;
						//cams.setBirthday() ;
						cams.setSmo(ConvertSql.parseLong(bed[0])) ;
						theManager.persist(cams) ;
					}
				}
			}
			monitor.finish("Обработка завершена") ;
		} catch (Exception e) {
			monitor.error(e.getMessage(), e) ;
		}
	}
	public void addMedServiceByAccount(Long aAccount,Long aPriceMedService, Integer aCount, BigDecimal aPrice, Long oldid) {
		StringBuilder sql = new StringBuilder() ;
		ContractAccount account = theManager.find(ContractAccount.class, aAccount) ;
		PriceMedService service = theManager.find(PriceMedService.class, aPriceMedService);
		sql.append("from ContractAccountMedService where id=:CAMS");
		
		List<ContractAccountMedService> list = theManager.createQuery(sql.toString())
				.setParameter("CAMS", oldid)
				.getResultList() ;
		if (list.size()>0) {
			
			ContractAccountMedService ms = list.get(0) ;
			ms.setCountMedService(aCount) ;
			ms.setCost(aPrice) ;
			theManager.persist(ms) ;
		} else {
			ContractAccountMedService ms = new ContractAccountMedService() ;
			ms.setCountMedService(aCount) ;
			ms.setCost(aPrice) ;
			ms.setAccount(account) ;
			ms.setMedService(service) ;
			theManager.persist(ms) ;
			
		}
		
	}
	
	@PersistenceContext EntityManager theManager ;
	 private @EJB ILocalMonitorService theMonitorService ;
}