package ru.ecom.mis.ejb.service.contract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.contract.ContractAccount;
import ru.ecom.mis.ejb.domain.contract.ContractAccountMedService;
import ru.ecom.mis.ejb.domain.contract.ContractGuarantee;
import ru.ecom.mis.ejb.domain.contract.JuridicalPerson;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.ecom.mis.ejb.domain.contract.PriceMedService;
import ru.nuzmsh.util.format.DateFormat;

@Stateless
@Remote(IContractService.class)
public class ContractServiceBean implements IContractService {
	private final static Logger log = Logger.getLogger(ContractServiceBean.class);


	public String makeKKMPaymentOrRefund(Long aAccountId,String aDiscont, Boolean isRefund,Boolean isTerminalPayment, String aKassir, EntityManager aManager) {
		try {
			String discontSql = "cams.cost";
			if (aDiscont!=null&&!aDiscont.equals("")) {
				//	log.info("=== Send KKM, discont = "+aDiscont);
				discontSql = "round(cams.cost*(100-"+aDiscont+")/100,2)";
			}
			StringBuilder sb = new StringBuilder();
			sb.append("select pp.id, pp.code as f2_code, pp.name as f3_name, cams.countmedservice as f4_count, cast("+discontSql+" as varchar) as f5_cost, cast("+discontSql+"*cams.countmedservice as varchar) as f6_sum")
					.append(",case when pp.isVat='1' then 'Ставка налога НДС '||coalesce(vv.taxrate,0) ||'%' end as f7_taxName")
					.append(",case when pp.isVat='1' then cast(round("+discontSql+"*cams.countmedservice*vv.taxrate/100,2) as varchar) end as f8_taxSum")
					.append(" from contractaccount  ca")
					.append(" left join contractaccountmedservice cams on cams.account_id=ca.id")
					.append(" left join pricemedservice pms on pms.id=cams.medservice_id")
					.append(" left join priceposition pp on pp.id=pms.priceposition_id")
					.append(" left join vocvat vv on vv.id=pp.tax_id")
					.append(" where ca.id=").append(aAccountId);
			List<Object[]> l = aManager.createNativeQuery(sb.toString()).getResultList();
			//Collection<WebQueryResult> l = service.executeNativeSql(sb.toString());
			if (!l.isEmpty()) {
				//	System.out.println("not empty");
				Double totalSum = 0.00;
				Double taxSum = 0.00;
				JSONObject root = new JSONObject();
				root.put("function", isRefund?"makeRefund":"makePayment");
				JSONArray arr = new JSONArray();
				for (Object[] r: l) {

					Double sum = Double.valueOf(r[5].toString());
					Double tax = Double.valueOf(r[7]!=null?r[7].toString():"0.00");
					totalSum+=sum;
					taxSum+=tax;
					JSONObject record = new JSONObject();
					record.put("code", r[1]);
					record.put("name", r[2]);
					record.put("count", r[3]);
					record.put("price", r[4].toString());
					record.put("sum", r[5].toString());
					//	record.put("price", 0);
					//	record.put("sum", 0);
					if (r[6]!=null&&!(""+r[6]).equals("")) {
						record.put("taxName", "");
						record.put("taxSum", r[7]!=null?r[7].toString():"");
					}
					arr.put(record);
				}
				root.put("isTerminalPayment", isTerminalPayment);
				if (isRefund) {
					root.put("totalRefundSum", totalSum) ;
				} else {
					root.put("pos", arr) ;
					root.put("totalPaymentSum", ""+totalSum+"") ;
					if (taxSum>0) {
						root.put("totalTaxSum", ""+ new BigDecimal(taxSum).setScale(2, RoundingMode.HALF_UP).toString()+"") ;
					}
				}
				//root.put("isTerminalPayment", isTerminalPayment);
				root.put("FIO", aKassir);
			//	log.warn("isTermPayment = "+isTerminalPayment);
				makeHttpPostRequest(root.toString(), aManager);
				return "Чек отправлен на печать";
			} else {
				return "Произошла ошибка, обратитесь к программистам";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	private void makeHttpPostRequest(String data, EntityManager aManager) throws IOException, NamingException {
		if (aManager==null) {aManager=theManager;}
		log.debug("===Send to KKM_BEAN. Data = "+data);
		List<Object> list = aManager.createNativeQuery("select keyvalue from  softconfig where key='KKM_WEB_SERVER'").getResultList();
		if (!list.isEmpty()) {
			String address = list.iterator().next().toString();
			URL url = new URL(address);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			writer.write(data);
			writer.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			//   StringBuffer answerString = new StringBuffer();
			//   String line;
			//   while ((line = br.readLine()) != null) {
			//   	answerString.append(line);
			//   }
			br.close();
			connection.disconnect();
		} else {
			log.error("Нет настройки 'KKM_WEB_SERVER', работа с ККМ невозможна");
		}

	}


	//Печать K, Z отчета
	public String printKKMReport(String aType, EntityManager aManager) {
		if (aType!=null&&(aType.equals("Z")||aType.equals("X"))) {
			try {
				JSONObject root = new JSONObject();
				if (root!=null) {
					root.put("function", "print"+aType+"Report");
					makeHttpPostRequest(root.toString(),aManager);
					return  aType+" отчет успешно отправлен на печать";
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}

		}
		return "Неизвестный тип отчета";
	}
	public String sendKKMRequest(String aFunction, Long aAccountId, String aDiscont, Boolean isTerminalPayment, String aKassir,EntityManager aManager)  {
			if (aFunction!=null &&aFunction.equals("makePayment")) {
				return makeKKMPaymentOrRefund(aAccountId, aDiscont, false, isTerminalPayment,aKassir,aManager);
			} else if (aFunction!=null&&aFunction.equals("makeRefund")) {
				return makeKKMPaymentOrRefund(aAccountId, aDiscont, true, isTerminalPayment,aKassir, aManager);
			} else if (aFunction!=null&&aFunction.equals("printZReport")){
				return printKKMReport("Z", aManager);
			} else if (aFunction!=null&&aFunction.equals("printXReport")){
				return printKKMReport("X",aManager);
			} else if (aFunction!=null&&aFunction.equals("printLastOrder")) {
				//return printLastOrder(aRequest);
			}
			return "Неизвестная функция";

	}
	
	private Long getMedService(Long aDepartment, Long aBedType, Long aBedSubType, Long aRoomType) {
		return getMedService(aDepartment, aBedType, aBedSubType, aRoomType, null);
	}
	private Long getMedService(Long aDepartment, Long aBedType, Long aBedSubType, Long aRoomType, String aCompanyType) {
		/**
		 *  Для силовиков по умолчанию ищем общие палаты
		 */
		
		if (aRoomType==null) aRoomType=Long.valueOf(3);
		if (aRoomType.equals(Long.valueOf(1))) aRoomType=Long.valueOf(3);
		if (aCompanyType!=null&&aCompanyType.equals("SILOVIK")) {aRoomType=Long.valueOf(1);}
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
	}
	public Long getPriceMedService(Long aPriceList,Long aMedService) {
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
	private boolean checkNoDoubleCAMS(Long aIdSmo, String aDtype) {
		List<Object> l = theManager.createNativeQuery("select id from ContractAccountMedService where Smo="+aIdSmo+" and TypeService='"+aDtype+"'").getResultList() ;
		if (l.size()>0) return false ; 
		return true ;
	}
	public Long setSmoByAccount(Long aAccount,String aDtypeSmo, Long aIdSmo, boolean aDeleteServiceWithOtherAccount, boolean aPeresech) throws ParseException {
		Long contract = null ;
		Long diag = null ;
		Long guarLetter = null ;
		Long priceList = null ;
		ContractGuarantee sp = null ;
		Long patient = null ;
		Long servicestream = null ;
		String polNumber = null ;
		//String date = null ;
		if (aDeleteServiceWithOtherAccount) {
			theManager.createNativeQuery("delete from ContractAccountMedService cams where cams.mainparent="+aIdSmo+" and (select count(ca.id) from ContractAccount ca where cams.account_id=ca.id and ca.isfinished='1')=0").executeUpdate() ;
			aPeresech=false ;
		}
		
		ContractAccount account = theManager.find(ContractAccount.class, aAccount) ;
		if (aDtypeSmo.equals("HOSPITALMEDCASE")) {
			System.out.println("=== ContractSBean ,account="+account.getContract().getId() +", smo="+aIdSmo);
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
				+" left join contractperson cpp on cpp.patient_id=sls.patient_id " 
				+" left join medcontract mc on mc.servicestream_id=sls.serviceStream_id and mc.id="+account.getContract().getId() 
				+" left join vochospitalizationresult vhr on vhr.id=sls.result_id"
				+" left join Patient pat on pat.id=sls.patient_id"
				+" left join medcase_medpolicy mpmc on mpmc.medcase_id=sls.id"
				+" left join medpolicy mp on mp.id=mpmc.policies_id "
				+" where sls.id="+aIdSmo+" and sls.deniedHospitalizating_id is null"
				//+" and mc.id is not null"
				).getResultList();
			String lastname=null; 
			String firstname=null; 
			String middlename=null;
			Boolean isDeath=null;
			
			Date birthday=null; 
			String datestart=null; 
			String datefinish=null;
			String customerType = "";
			
			if (l.size()>0) {
				try {
					JuridicalPerson jp =  (JuridicalPerson) account.getContract().getCustomer();
					if (jp!=null&& jp.getJuridicalPersonType()!=null) {
						customerType = jp.getJuridicalPersonType().getCode();
					}
				} catch (Exception e) {}
				contract = ConvertSql.parseLong(l.get(0)[1]) ;
				if (contract==null) {
					contract = account.getContract().getId() ;
					priceList=account.getContract().getPriceList().getId() ;
				} else {
					priceList = ConvertSql.parseLong(l.get(0)[2]) ;
				}
				patient = ConvertSql.parseLong(l.get(0)[0]) ;
				servicestream = ConvertSql.parseLong(l.get(0)[13]) ;
				
				guarLetter = ConvertSql.parseLong(l.get(0)[3]) ;
				if (guarLetter!=null) {
					sp = theManager.find(ContractGuarantee.class, guarLetter);
				}
				
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
			if (contract==null) {
				contract = account.getContract().getId() ;
				priceList=account.getContract().getPriceList().getId() ;
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
				String dtype="" ;
				Long idsmo;
				for (Object[] obj:listSlo) {
					dtype="MEDCASE" ;
					idsmo=ConvertSql.parseLong(obj[5]) ;
					boolean save=true ;
					if (!aPeresech) {
						if (!checkNoDoubleCAMS(aIdSmo, dtype)) save=false;
					}
					if (save) {
						ContractAccountMedService cams = new ContractAccountMedService() ;
						cams.setDiagnosis(diag) ;
						cams.setIsDeath(isDeath) ;
						cams.setMainParent(aIdSmo) ;
						cams.setLastname(lastname) ;
						cams.setFirstname(firstname) ;
						cams.setMiddlename(middlename) ;
						cams.setBirthday(birthday) ;
						cams.setAccount(account) ;
						cams.setTypeService(dtype) ;
						Long service = getMedService(ConvertSql.parseLong(obj[6]), ConvertSql.parseLong(obj[7]), ConvertSql.parseLong(obj[8]), ConvertSql.parseLong(obj[9]), customerType)  ;
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
						cams.setSmo(idsmo) ;
						cams.setPolNumber(polNumber) ;
						theManager.persist(cams) ;
					}

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
					dtype="SURGICALOPERATION" ;
					idsmo=ConvertSql.parseLong(obj[0]) ;
					boolean save=true ;
					if (!aPeresech) {
						if (!checkNoDoubleCAMS(aIdSmo, dtype)) save=false;
					}
					if (save) {
						ContractAccountMedService cams = new ContractAccountMedService() ;
						cams.setDiagnosis(diag) ;
						cams.setIsDeath(isDeath) ;
						cams.setMainParent(aIdSmo) ;
						cams.setLastname(lastname) ;
						cams.setFirstname(firstname) ;
						cams.setMiddlename(middlename) ;
						cams.setBirthday(birthday) ;
						cams.setAccount(account) ;
						cams.setTypeService(dtype) ;
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
						cams.setSmo(idsmo) ;
						cams.setPolNumber(polNumber) ;
						cams.setDoctor(ConvertSql.parseLong(obj[3])) ;
						theManager.persist(cams) ;
					}
				}
				
				sql = new StringBuilder() ;
				sql.append("select so.id as soid,to_char(so.operationdate,'dd.mm.yyyy') as operdate,ms.id as msid,wf.id as wfid") ;
				sql.append("      from Anesthesia anes ") ;
				sql.append("      left join SurgicalOperation so on anes.surgicalOperation_id=so.id") ;
				sql.append("      left join workfunction wf on wf.id=anes.anesthesist_id") ;
				sql.append("      left join vocworkfunction vwf on vwf.id=wf.workfunction_id");
				sql.append("      left join worker w on w.id=wf.worker_id");
				sql.append("      left join patient wp on wp.id=w.person_id");
				sql.append("      left join medcase slo on slo.id=so.medcase_id");
				sql.append("      left join vocservicestream vss on vss.id=so.servicestream_id");
				sql.append("      left join medservice ms on ms.id=anes.medservice_id");
				sql.append("      where  (slo.parent_id='").append(aIdSmo).append("' or slo.id='").append(aIdSmo).append("') and ms.id is not null");
				List<Object[]> listAn=theManager.createNativeQuery(sql.toString()).getResultList() ;
				for (Object[] obj:listAn) {
					dtype="ANESTHESIA" ;
					idsmo=ConvertSql.parseLong(obj[0]) ;
					boolean save=true ;
					if (!aPeresech) {
						if (!checkNoDoubleCAMS(aIdSmo, dtype)) save=false;
					}
					if (save) {
						ContractAccountMedService cams = new ContractAccountMedService() ;
						cams.setDiagnosis(diag) ;
						cams.setIsDeath(isDeath) ;
						cams.setMainParent(aIdSmo) ;
						cams.setLastname(lastname) ;
						cams.setFirstname(firstname) ;
						cams.setMiddlename(middlename) ;
						cams.setBirthday(birthday) ;
						cams.setAccount(account) ;
						cams.setTypeService(dtype) ;
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
						cams.setSmo(idsmo) ;
						cams.setPolNumber(polNumber) ;
						cams.setDoctor(ConvertSql.parseLong(obj[3])) ;
						theManager.persist(cams) ;
					}
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
					dtype="MEDCASE" ;
					idsmo=ConvertSql.parseLong(obj[0]) ;
					boolean save=true ;
					if (!aPeresech) {
						if (!checkNoDoubleCAMS(aIdSmo, dtype)) save=false;
					}
					if (save) {
						ContractAccountMedService cams = new ContractAccountMedService() ;
						cams.setDiagnosis(diag) ;
						cams.setIsDeath(isDeath) ;
						cams.setMainParent(aIdSmo) ;
						cams.setLastname(lastname) ;
						cams.setFirstname(firstname) ;
						cams.setMiddlename(middlename) ;
						cams.setBirthday(birthday) ;
						cams.setAccount(account) ;
						cams.setTypeService(dtype) ;
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
						cams.setSmo(idsmo) ;
						cams.setPolNumber(polNumber) ;
						cams.setDoctor(ConvertSql.parseLong(obj[3])) ;
						theManager.persist(cams) ;
					}
				}
				if (patient!=null && datestart!=null && servicestream!=null) {
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
					dtype="MEDCASE" ;
					idsmo=ConvertSql.parseLong(obj[0]) ;
					boolean save=true ;
					if (!aPeresech) {
						if (!checkNoDoubleCAMS(aIdSmo, dtype)) save=false;
					}
					if (save) {
						ContractAccountMedService cams = new ContractAccountMedService() ;
						cams.setDiagnosis(diag) ;
						cams.setIsDeath(isDeath) ;
						cams.setMainParent(aIdSmo) ;
						cams.setLastname(lastname) ;
						cams.setFirstname(firstname) ;
						cams.setMiddlename(middlename) ;
						cams.setBirthday(birthday) ;
						cams.setAccount(account) ;
						cams.setTypeService("MEDCASE") ;
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
					}}
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
					cams.setTypeService("MEDCASE") ;
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
		} else if (aDtypeSmo.equals("POLYCLINICMEDCASE")||aDtypeSmo.equals("VISIT")
				||aDtypeSmo.equals("SERVICEMEDCASE")) {
			StringBuilder sql = new StringBuilder() ;
			Long policy =Long.valueOf("0") ;
			String datestart=null ;
			String datefinish=null ;
			String lastname=null ;
			String firstname=null ;
			String middlename=null ;
			Date birthday=null ;
			List<Object[]> l = theManager.createNativeQuery("select sls.patient_id as cpid,mc.id as mcid,mc.priceList_id as pricelist"
					+" , (select max(cg.id) from ContractGuarantee cg where cg.contract_id=mc.id and cg.contractPerson_id=cpp.id and sls.datestart between cg.actionDate and cg.actionDateTo) as cgid"
					+", to_char(sls.datestart,'dd.mm.yyyy') as datestart"
					+", to_char(coalesce(sls.datefinish,current_date),'dd.mm.yyyy') as datefinish"
					+", max(case when mp.dtype like 'MedPolicyD%' and mp.dtype like 'MedPolicyDmc%' and sls.datestart between mp.actualDateFrom and coalesce(mp.actualDateTo,current_date) then mp.polNumber else '' end) as polnumber"
					+",pat.lastname, pat.firstname,pat.middlename,to_char(pat.birthday,'dd.mm.yyyy') as birthday"
					+",sls.serviceStream_id"
					+" from medcase sls" 
					+" left join Patient pat on pat.id=sls.patient_id"
					+" left join medpolicy mp on mp.patient_id=sls.patient_id"
					+" left join contractperson cp on cp.regcompany_id=mp.company_id"
					+" left join contractperson cpp on cpp.patient_id=sls.patient_id"
					+" left join medcontract mc on mc.servicestream_id=sls.serviceStream_id and cp.id=mc.customer_id "
					+" where sls.id="+aIdSmo //+ " and mc.id="+account.getContract().getId()
					+" group by sls.id,sls.patient_id,mc.id,mc.pricelist_id,sls.datestart,sls.datefinish" 
					+" ,pat.lastname,pat.firstname,pat.middlename,pat.birthday,sls.serviceStream_id,cpp.id"
					+" order by cgid, polnumber desc"
					).getResultList();
			if (l.size()>0) {
				
				contract = ConvertSql.parseLong(l.get(0)[1]) ;
				priceList = ConvertSql.parseLong(l.get(0)[2]) ;
				if (contract==null) {
					contract = account.getContract().getId() ;
					priceList = account.getContract().getPriceList().getId() ;
				}
				
				patient = ConvertSql.parseLong(l.get(0)[0]) ;
				servicestream = ConvertSql.parseLong(l.get(0)[11]) ;
				
				guarLetter = ConvertSql.parseLong(l.get(0)[3]) ;
				if (guarLetter!=null) {
					sp = theManager.find(ContractGuarantee.class, guarLetter);
				}
				datestart= ""+l.get(0)[4] ;
				datefinish = ""+l.get(0)[5] ;
				polNumber = ""+l.get(0)[6] ;
				lastname = ""+l.get(0)[7] ;
				firstname = ""+l.get(0)[8] ;
				middlename = ""+l.get(0)[9] ;
				birthday = DateFormat.parseSqlDate(""+l.get(0)[10]) ;
				
			} 
			sql = new StringBuilder() ;
			sql.append("      select so.id as smoid,to_char(coalesce(so.dateStart,slo.datestart),'dd.mm.yyyy') as dt");
			sql.append("	  ,ms.id as msid,coalesce(wf.id,slo.workFunctionExecute_id) as wfid ");
			sql.append("	  ,coalesce(so.idc10_id,max(case when vpd.code='1' then d1.idc10_id else null end) )    as mkb");
			sql.append("      from MedCase so") ;
			sql.append("      left join VocIdc10 mkb on mkb.id=so.idc10_id");
			sql.append("      left join workfunction wf on wf.id=so.workFunctionExecute_id");
			sql.append("      left join vocworkfunction vwf on vwf.id=wf.workfunction_id");
			sql.append("      left join worker w on w.id=wf.worker_id");
			sql.append("      left join patient wp on wp.id=w.person_id");
			sql.append("      left join medcase slo on slo.id=so.parent_id");
			sql.append("      left join vocservicestream vss on vss.id=so.servicestream_id");
			sql.append("      left join medservice ms on ms.id=so.medservice_id");
			sql.append("      left join  Diagnosis d1 on d1.medcase_id=slo.id");
			sql.append("      left join VocPriorityDiagnosis vpd on vpd.id=d1.priority_id ");
			sql.append("      where");
			sql.append("      (slo.parent_id='").append(aIdSmo).append("' or slo.id='").append(aIdSmo).append("' or so.id='").append(aIdSmo).append("')");
			sql.append("      and upper(so.dtype)='SERVICEMEDCASE'") ;
			sql.append("      and (slo.noActuality='0' or slo.noActuality is null) and slo.dateStart is not null");
			sql.append("      group by so.id,so.datestart,so.datefinish,slo.datestart,ms.id,wf.id,slo.workfunctionexecute_id,so.idc10_id") ;
			
			List<Object[]> listMs=theManager.createNativeQuery(sql.toString()).getResultList() ;
			
			for (Object[] obj:listMs) {
				diag = ConvertSql.parseLong(obj[4]) ;
				ContractAccountMedService cams = new ContractAccountMedService() ;
				cams.setDiagnosis(diag) ;
				cams.setIsDeath(null) ;
				cams.setMainParent(aIdSmo) ;
				cams.setLastname(lastname) ;
				cams.setFirstname(firstname) ;
				cams.setMiddlename(middlename) ;
				cams.setBirthday(birthday) ;
				cams.setAccount(account) ;
				cams.setTypeService("MEDCASE") ;
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

		}
		return contract ;
	}
	public void unionAccountsWithContract(Long aAccount1, Long aAccount2) {
		theManager.createNativeQuery("update ContractAccountMedService set account_id="+aAccount1+" where account_id="+aAccount2).executeUpdate() ;
		theManager.createNativeQuery("delete ContractAccount where id="+aAccount2).executeUpdate() ;
	}
	public void accountCreation(long aMonitorId, String aDate, String aDateFrom,String aDateTo, Long aContract, String aAccountNumber
			,boolean aIsHosp,int aIsPolyc, boolean aIsEmpty, boolean aDeleteServiceWithOtherAccount, boolean aIsPeresech) {
		IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Подготовка к импорту") ;
		try {
			MedContract contract = theManager.find(MedContract.class, aContract) ;
			ContractAccount ac = new ContractAccount() ;
			ac.setContract(contract) ;
			ac.setDateFrom(DateFormat.parseSqlDate(aDate)) ;
			ac.setPeriodFrom(DateFormat.parseSqlDate(aDateFrom)) ;
			ac.setPeriodTo(DateFormat.parseSqlDate(aDateTo)) ;
			ac.setAccountNumber(aAccountNumber) ;
			theManager.persist(ac) ;
			System.out.println("isEmpty="+aIsEmpty) ;
	        System.out.println("isHospit="+aIsHosp) ;
	        System.out.println("isPolyc="+aIsPolyc) ;
			if (aIsEmpty) {
				monitor=theMonitorService.startMonitor(aMonitorId, "Создание пустого счета",1) ;
				monitor.finish("Создан счет") ;
				return ;
			}
			boolean isStart=false ;
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
				if (aIsHosp) {
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
					if (par[3]!=null) sql.append(" and mp.company_id=").append(par[3]).append(" and mp.dtype like 'MedPolicyD%'") ;
					if (par[4]!=null) sql.append(" and mc.orderLpu_id=").append(par[3]) ;
					sql.append(" group by mc.id,mp.id,mc.patient_id") ;
					System.out.println("Формирование счета по стациционару. SQL = "+sql);
					List<Object[]> listHosp = theManager.createNativeQuery(sql.toString()).getResultList() ;
					if (!isStart) {
						monitor = theMonitorService.startMonitor(aMonitorId, "Формирование счета по стационару. Найдено госпитализаций: "+listHosp.size(),listHosp.size()) ;
						isStart=true ;
					} else {
						monitor.setText("Формирование счета по стационару. Найдено госпитализаций: "+listHosp.size()) ;
					}
					for (Object[] hosp:listHosp) {
						Long hospId=ConvertSql.parseLong(hosp[0]) ;
						setSmoByAccount(ac.getId(), "HOSPITALMEDCASE", hospId, aDeleteServiceWithOtherAccount,aIsPeresech) ;
					}
				} 
				//обращение
				if (aIsPolyc==1) {
					Object[] par=list.get(0) ;
					sql = new StringBuilder() ;
					sql.append("select mc.id as mcid")
						.append(", max(mp.id) as mpid")
						.append(",mc.patient_id from medcase mc")
						.append(" left join medpolicy mp on mp.patient_id=mc.patient_id")
						
					.append(" where mc.dtype='PolyclinicMedCase' and mc.dateFinish between to_date('")
					.append(aDateFrom).append("','dd.mm.yyyy')")
					.append(" and to_date('")
					.append(aDateTo).append("','dd.mm.yyyy')") ;
					if (par[2]!=null) sql.append(" and mc.serviceStream_id=").append(par[2]) ;
					if (par[3]!=null) sql.append(" and mp.company_id=").append(par[3]+" and mp.dtype like 'MedPolicyDmc%' and mc.datestart between mp.actualDateFrom and coalesce(mp.actualDateTo,current_date)") ;
					if (par[4]!=null) sql.append(" and mc.orderLpu_id=").append(par[4]) ;
					sql.append(" group by mc.id,mc.patient_id") ;
					System.out.println("Формирование счета по поликлиническим обращениям. SQL = "+sql);
					List<Object[]> listHosp = theManager.createNativeQuery(sql.toString()).getResultList() ;
					if(!isStart){
						monitor = theMonitorService.startMonitor(aMonitorId, "Формирование счета по поликлиническим обращениям. Найдено обращений: "+listHosp.size(),listHosp.size()) ;
						isStart=true ;
					} else {
						monitor.setText("Формирование счета по поликлиническим обращениям. Найдено обращений: "+listHosp.size()) ;
					}
					
					for (Object[] hosp:listHosp) {
						Long hospId=ConvertSql.parseLong(hosp[0]) ;
						setSmoByAccount(ac.getId(), "POLYCLINICMEDCASE", hospId, aDeleteServiceWithOtherAccount,aIsPeresech) ;
					}
				} else if (aIsPolyc==2) {
					Object[] par=list.get(0) ;
					sql = new StringBuilder() ;
					sql.append("select mc.id as mcid")
					.append(", max(mp.id) as mpid")
					.append(",mc.patient_id from medcase mc")
					.append(" left join medpolicy mp on mp.patient_id=mc.patient_id")
					.append(" where mc.dtype='Visit' and mc.dateStart between to_date('")
					.append(aDateFrom).append("','dd.mm.yyyy')")
					.append(" and to_date('")
					.append(aDateTo).append("','dd.mm.yyyy')") ;
					if (par[2]!=null) sql.append(" and mc.serviceStream_id=").append(par[2]) ;
					if (par[3]!=null) sql.append(" and mp.company_id=").append(par[3]+" and mp.dtype like 'MedPolicyDmc%' and mc.datestart between mp.actualDateFrom and coalesce(mp.actualDateTo,current_date)") ;
					if (par[4]!=null) sql.append(" and mc.orderLpu_id=").append(par[4]) ;
					sql.append(" group by mc.id,mc.patient_id,mc.datestart") ;
					System.out.println("Формирование счета по поликлинике визит. SQL = "+sql);
					List<Object[]> listHosp = theManager.createNativeQuery(sql.toString()).getResultList() ;
					if (!isStart) {
						monitor = theMonitorService.startMonitor(aMonitorId, "Формирование счета по поликлинике визит. Найдено визитов: "+listHosp.size(),listHosp.size()) ;
						isStart=true ;
					} else {
						monitor.setText("Формирование счета по поликлинике визит. Найдено визитов: "+listHosp.size()) ;
					}
					for (Object[] hosp:listHosp) {
						Long hospId=ConvertSql.parseLong(hosp[0]) ;
						setSmoByAccount(ac.getId(), "VISIT", hospId, aDeleteServiceWithOtherAccount,aIsPeresech) ;
					}
					
					
					sql = new StringBuilder() ;
					sql.append("select mc.id as mcid")
						.append(", max(mp.id) as mpid")
						.append(",mc.patient_id from medcase mc left join medcase mcp on mcp.id=mc.parent_id")
						.append(" left join medpolicy mp on mp.patient_id=mc.patient_id")
						.append(" where mc.dtype='ServiceMedCase' and mc.dateStart between to_date('")
						.append(aDateFrom).append("','dd.mm.yyyy')")
						.append(" and to_date('")
						.append(aDateTo).append("','dd.mm.yyyy') and mcp.dtype='PolyclinicMedCase'") ;
					if (par[2]!=null) sql.append(" and mc.serviceStream_id=").append(par[2]) ;
					if (par[3]!=null) sql.append(" and mp.company_id=").append(par[3]+" and mp.dtype like 'MedPolicyDmc%' and mc.datestart between mp.actualDateFrom and coalesce(mp.actualDateTo,current_date)") ;
					if (par[4]!=null) sql.append(" and mc.orderLpu_id=").append(par[4]) ;
					sql.append(" group by mc.id,mc.patient_id,mc.datestart") ;
					List<Object[]> listHosp1 = theManager.createNativeQuery(sql.toString()).getResultList() ;
					if (!isStart) {
						monitor = theMonitorService.startMonitor(aMonitorId, "Формирование счета по поликлинике визит. Найдено услуг: "+listHosp.size(),listHosp.size()) ;
						isStart=true ;
					} else {
						monitor.setText("Формирование счета по поликлинике визит. Найдено услуг: "+listHosp1.size()) ;
					}
					for (Object[] hosp:listHosp1) {
						Long hospId=ConvertSql.parseLong(hosp[0]) ;
						setSmoByAccount(ac.getId(), "SERVICEMEDCASE", hospId, aDeleteServiceWithOtherAccount,aIsPeresech) ;
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