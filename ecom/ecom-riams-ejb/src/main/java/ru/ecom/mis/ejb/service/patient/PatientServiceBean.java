package ru.ecom.mis.ejb.service.patient;

import java.lang.reflect.Method;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.alg.common.IsChild;
import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.jaas.ejb.domain.SoftConfig;
import ru.ecom.jaas.ejb.service.SoftConfigServiceBean;
import ru.ecom.mis.ejb.domain.contract.NaturalPerson;
import ru.ecom.mis.ejb.domain.licence.ExternalDocument;
import ru.ecom.mis.ejb.domain.licence.voc.VocExternalDocumentType;
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressPoint;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressText;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.LpuAttachedByDepartment;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.PatientFond;
import ru.ecom.mis.ejb.domain.patient.PatientFondCheckData;
import ru.ecom.mis.ejb.domain.patient.voc.VocAttachedType;
import ru.ecom.mis.ejb.domain.patient.voc.VocIdentityCard;
import ru.ecom.mis.ejb.domain.patient.voc.VocMedPolicyOmc;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.ecom.mis.ejb.domain.patient.voc.VocSocialStatus;
import ru.ecom.mis.ejb.form.lpu.interceptors.LpuAreaDynamicSecurity;
import ru.ecom.mis.ejb.form.patient.MedPolicyOmcForm;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.form.patient.VocOrgForm;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

/**
 * Сервис пациента
 */
@Stateless
@Remote(IPatientService.class)
@Local(IPatientService.class)
@SecurityDomain("other")
public class PatientServiceBean implements IPatientService {
	
	
	public boolean needUpdatePatient (Long aPatientId, Long aPatientFondId){
		boolean isDifference=false;
		PatientFond fond = theManager.find(PatientFond.class, aPatientFondId);
		Patient pat = theManager.find(Patient.class, aPatientId);
		if (needChangeValue(pat.getLastname(), fond.getLastname())					
				||needChangeValue(pat.getFirstname(), fond.getFirstname())
				||needChangeValue(pat.getMiddlename(), fond.getMiddlename())
				||needChangeValue(pat.getBirthday(), fond.getBirthday())
				||needChangeValue(pat.getSnils(), fond.getSnils())
				||needChangeValue(pat.getCommonNumber(), fond.getCommonNumber())
				||needChangeValue(pat.getPassportSeries(), fond.getDocumentSeries())
				||needChangeValue(pat.getPassportNumber(), fond.getDocumentNumber())) {
				isDifference = true;
			}
		String aAttachedType = fond.getAttachedType();
		String aAttachedDate = DateFormat.formatToDate(fond.getAttachedDate());
		String aLpuAttached = fond.getLpuAttached();
		String aPolicySeries = fond.getPolicySeries();
		String aPolicyNumber = fond.getPolicyNumber();
		String aCompanyCode = fond.getCompanyCode();
			
		//	try {
				if (aLpuAttached!=null&&aAttachedDate!=null&&aAttachedType!=null) {
					String aSql = "select " +
							" case when (select keyvalue from softconfig where key='DEFAULT_LPU_OMCCODE')!='"+aLpuAttached+"' then 1" +
							" else (select count(att.id) from lpuattachedbydepartment att where (att.patient_id="+aPatientId+
							" and att.attachedtype_id=(select id from vocattachedtype where code='"+aAttachedType+"')" +
							" and att.dateFrom=to_date('"+aAttachedDate+"','dd.MM.yyyy')and att.dateTo is null" +
							" and (select keyvalue from softconfig where key='DEFAULT_LPU_OMCCODE')='"+aLpuAttached+"')) end";
							//System.out.println("=========== aSQL = "+aSql);
					Object a = theManager.createNativeQuery(aSql).getSingleResult();
					if (a.toString().equals("0")) {isDifference = true;}
					
				}
				
				if (aPolicyNumber!=null&&aPolicySeries!=null&&aCompanyCode!=null) {
					Object mp = theManager.createNativeQuery("select count(mp.id) from medpolicy mp" +
							" where mp.patient_id="+aPatientId +
							" and mp.dtype='MedPolicyOmc'" +
							" and mp.series=:series and mp.polnumber=:number" +
							" and mp.company_id=(select id from reg_ic where omccode=:code)")
							.setParameter("series", aPolicySeries).setParameter("number", aPolicyNumber)
							.setParameter("code", aCompanyCode)
							.getSingleResult();
					if (mp.toString().equals("0")) {isDifference = true;}
				}
				
			/*} catch (Exception e) {
				System.out.println("---------Ошибка - patId:attType:attDate:attLpu:polSer:polNum:polSK = "+aPatientId+" : "+aAttachedType+":"+aAttachedDate+":"+
						aLpuAttached+":"+aPolicySeries+":"+aPolicyNumber+":"+aCompanyCode);
				e.printStackTrace();
			}*/
			return isDifference;
	}
	
	public boolean needChangeValue (Object aOldValue, Object aNewValue) {
		if (toStr(aOldValue)==null&&toStr(aNewValue)!=null) {return true;} //Старое значение пустое, новое - нет, обновляем.
		if (toStr(aNewValue)==null) {return false;} //На пустое значение не обновляем.
		if (toStr(aOldValue).equals(toStr(aNewValue))) {return false;} //Если значения равны - не обновляем
		return true;
		
	}
	public void insertPatientNotFound(Long aPatientId, Long aCheckTimeId) throws ParseException {
		StringBuilder str = new StringBuilder();
		Patient pat = theManager.find(Patient.class, aPatientId);
		str.append("insert into JournalPatientFondCheck (lastname, firstname, middlename, birthday, patient_id, checktime_id, isFound) values ")
		.append("('").append(pat.getLastname()).append("'")
		.append(",'").append(pat.getFirstname()).append("'")
		.append(",'").append(pat.getMiddlename()).append("'")
		.append(",to_date('").append(DateFormat.formatToLogic(pat.getBirthday())).append("','yyyyMMdd')")
		.append(",").append(aPatientId)
		.append(",").append(aCheckTimeId)
		.append(",'0'").append(")");
		try {
			theManager.createNativeQuery(str.toString()).executeUpdate();
		} catch (Exception e) {
			String sql = "ALTER TABLE journalpatientfondcheck ALTER COLUMN id SET DEFAULT NEXTVAL('journalpatientfondcheck_sequence');";
			theManager.createNativeQuery(sql).executeUpdate();
			theManager.createNativeQuery(str.toString()).executeUpdate();
		}
		
	}
	public PatientFondCheckData getNewPFCheckData(boolean aNeedUpdatePatient, boolean aNeedUpdateDocument, boolean aNeedUpdatePolicy, boolean aNeedUpdateAttachment) {
		PatientFondCheckData pfcd = new PatientFondCheckData ();
		pfcd.setStartDate(new java.sql.Date(new java.util.Date().getTime()));
		pfcd.setNeedUpdatePatient(aNeedUpdatePatient);
		pfcd.setNeedUpdateDocument(aNeedUpdateDocument);
		pfcd.setNeedUpdatePolicy(aNeedUpdatePolicy);
		pfcd.setNeedUpdateAttachment(aNeedUpdateAttachment);
		theManager.persist(pfcd);
		return pfcd;
	}
	public String getImageDir() {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String workDir =config.get("tomcat.image.dir", "/opt/tomcat/webapps/docmis/");
		return workDir ;
		
	}
	public float getImageCompress() {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String comp = config.get("tomcat.image.compress", "0.5") ;
		return Float.valueOf(comp) ;
	}
	public void insertExternalDocumentByObject(String aObject,Long aObjectId, Long aType,String aReferenceComp,String aReferenceTo, String aComment,String aUsername) {
		ExternalDocument doc = new ExternalDocument() ;
		doc.setReferenceTo(aReferenceTo) ;
		doc.setReferenceCompTo(aReferenceComp) ;
		java.util.Date date = new java.util.Date() ;
		doc.setCreateDate(new java.sql.Date(date.getTime())) ;
		doc.setCreateUsername(aUsername) ;
		doc.setCreateTime(new java.sql.Time(date.getTime()));
		if (aObject.equals("Patient")) {
			Patient pat = theManager.find(Patient.class, aObjectId) ;
			doc.setPatient(pat) ;
		} else if (aObject.equals("MedCase")) {
			MedCase mc = theManager.find(MedCase.class, aObjectId) ;
			doc.setMedCase(mc) ;
		} else {
			throw new IllegalStateException("Не определен object типа:"+aObject);
		}
		VocExternalDocumentType type = theManager.find(VocExternalDocumentType.class, aType) ;
		doc.setType(type) ;
		theManager.persist(doc) ;
	}
	public String getCodeByMedPolicyOmc(Long aType) {
		VocMedPolicyOmc type =null;
		if (aType!=null) {
			type = theManager.find(VocMedPolicyOmc.class, aType) ;
		}
		return type==null?"0":type.getCode() ;
	}
	
	public boolean isNaturalPerson(Long aPatient) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select count(*) from ContractPerson where dtype='NaturalPerson' and patient_id='")
			.append(aPatient).append("'") ;
		Object ret = theManager.createNativeQuery(sql.toString()).getSingleResult() ;
		Long cnt = ConvertSql.parseLong(ret) ;
		
		return cnt>Long.valueOf(0)?true:false ; 
		
		
	}
	public void createNaturalPerson(Long aPatient) {
		 Patient pat = theManager.find(Patient.class, aPatient) ;
		 NaturalPerson np = new NaturalPerson();
		 np.setPatient(pat) ;
		 theManager.persist(np);
		 //return np.getId();
	}
	
	private String getAddressByKladr(String aKladr,String aRegion,String aRayon, String aCity, String aStreet) {
		StringBuilder sql = new StringBuilder() ;
		aStreet=aStreet.toUpperCase().replaceAll(" ", "").replaceAll("-", "").replaceAll("№", "N") ;
		if (aCity!=null && aCity.contains("АСТРАХАН")) aCity="АСТРАХАНЬ" ;
		if (aCity.endsWith(" С")
				||aCity.endsWith(" П")
				||aCity.endsWith(" Г")
				||aCity.endsWith("Г.")
				||aCity.endsWith("П.")
				||aCity.endsWith("С.")
				) aCity = aCity.substring(0,aCity.length()-2) ;
		else if (aCity.endsWith("ГОР.")
				||aCity.endsWith(" ГОР")
				||aCity.endsWith(" ПОС")
				||aCity.endsWith("ПОС.")
				) aCity = aCity.substring(0,aCity.length()-4) ;
		else if (aCity.startsWith("С.")||aCity.startsWith("С ")) aCity = aCity.substring(2) ;
		else if (aCity.startsWith("Г.")||aCity.startsWith("Г ")) aCity = aCity.substring(2) ;
		else if (aCity.startsWith("П.")||aCity.startsWith("П ")) aCity = aCity.substring(2) ;
		else if (aCity.startsWith("ГОР.")) aCity = aCity.substring(4) ;
		else if (aCity.startsWith("ПОС.")) aCity = aCity.substring(4) ;
		else if (aCity.startsWith("ГОР ")) aCity = aCity.substring(4) ;
		else if (aCity.startsWith("ПОС ")) aCity = aCity.substring(4) ;
		
		aCity=aCity.trim().toUpperCase().replaceAll("-", "").replaceAll(" ", "").replaceAll("№", "N") ;
		
		sql.append("select addressid,kladr from Address2 where kladr='").append(aKladr).append("'" ) ;
		StringBuilder res = new StringBuilder() ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		if (list.size()>0) {
			res.append(list.get(0)[0]) ;
		} else {
			if (aStreet.startsWith("?")) return res.toString() ;
			if (aKladr!=null && !aKladr.equals("")) {
				String lastKl = aKladr.equals("")?aRegion:aKladr.substring(aKladr.length()-1, aKladr.length()) ;
				
				while (lastKl!=null&&!lastKl.equals("")&&lastKl.equals("0")) {
					aKladr = aKladr.substring(0,aKladr.length()-1) ;
					lastKl = aKladr.length()>1?aKladr.substring(aKladr.length()-1, aKladr.length()):null ;
				}
				sql = new StringBuilder() ;
				sql.append("select addressid,kladr from Address2 where kladr like '").append(aKladr).append("%' and UPPER(name)='").append(aCity).append("'" ) ;
				list.clear() ;
				list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
				if (list.size()>0) {
					aKladr = ""+list.get(0)[1] ;
					lastKl = aKladr.substring(aKladr.length()-1, aKladr.length()) ;
					while (lastKl!=null&&lastKl.equals("0")) {
						aKladr = aKladr.substring(0,aKladr.length()-1) ;
						lastKl = aKladr.length()>1?aKladr.substring(aKladr.length()-1, aKladr.length()):null ;
					}
					sql = new StringBuilder() ;
					sql.append("select addressid,kladr from Address2 where kladr like '").append(aKladr).append("%' and replace(replace(replace(UPPER(name),'-',''),' ',''),'№','N')='").append(aStreet).append("'" ) ;
					list.clear() ;
					list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
					if (list.size()>0) {
						//System.out.println("111") ;
						res.append(list.get(0)[0]) ;
					} else {
						//System.out.println("222") ;
						return getKladrByRayon(aRegion, aRayon, aCity, aStreet) ;
					}
				} else {
					return getKladrByRayon(aRegion, aRayon, aCity, aStreet) ;
				}
			} else {
				return getKladrByRayon(aRegion, aRayon, aCity, aStreet) ;
			}
		}
		return res.toString() ;
	}
	private String getKladrByRayon(String aRegion, String aRayon, String aCity, String aStreet) {
		StringBuilder sql = new StringBuilder() ;
		StringBuilder res = new StringBuilder() ;
		sql.append("select id, kladr from VocRayon where code='").append(aRayon).append("'" ) ;
		List<Object[]> list ;
		list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		String kladr=aRegion;
		if (list.size()>0) {
			kladr = ""+(list.get(0)[1]!=null?list.get(0)[1]:aRegion) ;
		}
		sql = new StringBuilder() ;
		sql.append("select addressid,kladr from Address2 where kladr like '").append(kladr).append("%' and domen<6 and replace(replace(replace(UPPER(name),'-',''),' ',''),'№','N')='").append(aCity).append("'" ) ;
		list.clear() ;
		list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		
		if (list.size()>0) {
			String kladrId=kladr = ""+list.get(0)[0] ; 
			kladr = ""+list.get(0)[1] ;
			String lastKl = kladr.substring(kladr.length()-1, kladr.length()) ;
			while (lastKl!=null&&lastKl.equals("0")) {
				kladr = kladr.substring(0,kladr.length()-1) ;
				lastKl = kladr.length()>1?kladr.substring(kladr.length()-1, kladr.length()):null ;
			}
			sql = new StringBuilder() ;
			sql.append("select addressid,kladr from Address2 where kladr like '").append(kladr).append("%' and replace(replace(replace(UPPER(name),'-',''),' ',''),'№','N')='").append(aStreet).append("'" ) ;
			list.clear() ;
			list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			if (list.size()>0) {
				res.append(list.get(0)[0]) ;
			} else {
				res.append(kladrId) ;
			}
		}
		return res.toString() ;
	}
	public String getInfoVocForFond(String aPassportType,String aAddress, String aPolicy) {
		StringBuilder sql = new StringBuilder() ;
		StringBuilder res = new StringBuilder() ;
		List<Object[]> list = null;
		if (aPassportType!=null && !aPassportType.trim().equals("")) {
			sql.append("select id,name from VocIdentityCard where omcCode='").append(aPassportType).append("'" ) ;
			list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		}
		if (list!=null && list.size()>0) {
			res.append(list.get(0)[0]).append("#").append(list.get(0)[1]).append("#") ;
		} else {
			res.append("##");
		}
		sql = new StringBuilder() ;
		if (aAddress!=null && !aAddress.trim().equals("")) {
			//res = new StringBuilder() ;
			String[] adr = aAddress.split("#") ;
			String kladr = adr[0] ;
			String rayon = adr[1].toUpperCase() ;
			String sity = adr[2].toUpperCase() ;
			String street = adr[3].toUpperCase() ;
			String region = adr[4].toUpperCase() ;
			//System.out.println("000") ;
			res.append(getAddressByKladr(kladr,region,rayon, sity, street)) ;
			//System.out.println("333"+rayon) ;
			res.append("#");
			sql = new StringBuilder() ;
			//res = new StringBuilder() ;
			
			sql.append("select id,code||' '||name from VocRayon where code='").append(rayon).append("' or upper(name) like '%").append(rayon).append("%'" ) ;
			//System.out.println(sql.toString()) ;
			if (list!=null) list.clear() ;
			list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			//System.out.println("444--") ;
			if (list.size()>0) {
				res.append(list.get(0)[0]).append("#").append(list.get(0)[1]).append("#") ;
			} else {
				res.append("##");
			} 
			
		} else {
			res.append("#") ;
			res.append("##") ;
		}
		//System.out.println("444") ;
		if (aPolicy!=null &&!aPolicy.equals("")) {
			String pol[] = aPolicy.split("#") ;
			sql = new StringBuilder() ;
			sql.append("select id,omcCode||' '||name from REG_IC where omcCode='").append(pol[0]).append("'" ) ;
			if (list!=null) list.clear() ;
			list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			if (list.size()>0) {
				res.append(list.get(0)[0]).append("#").append(list.get(0)[1]).append("#") ;
			} else {
				res.append("##");
			}
			String type=getTypePolicy(pol[1]) ;
			sql = new StringBuilder() ;
			sql.append("select id,id||' '||name from VocMedPolicyOmc where code='").append(type).append("'" ) ;
			if (list!=null) list.clear() ;
			list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			if (list.size()>0) {
				res.append(list.get(0)[0]).append("#").append(list.get(0)[1]).append("#") ;
			} else {
				res.append("##");
			}
		} else {
			res.append("##");
			res.append("##");
		}
		return res.toString() ;
	}
	public String toStr(Object o) {
		if (o==null||o.toString().equals("")) {
			return null;
			} else {
				return o.toString();
			}
	}
	public String prepSql(String aField, String aValue) {
		return prepSql (aField, aValue,"","");
	}
	public String prepSql(String aField, String aValue, String aPrefix, String aPostfix) {
		if (aValue==null) {aValue="";}
			return new String(aField+"="+aPrefix+"'"+aValue+"'"+aPostfix);
		
	}
	public boolean updateDataByFondAutomaticByFIO (String aLastName, String aFirstName, String aMiddleName, String aBirthday, Long aCheckTimeId,boolean needUpdatePatient, boolean needUpdateDocuments, boolean needUpdatePolicy, boolean needUpdateAttachment) {
		Object list = theManager.createNativeQuery("select max(pf.id) from patientfond pf where pf.lastname='"+aLastName+"' and " +
				"pf.firstname = '"+aFirstName+"' and pf.middlename = '"+aMiddleName+"' and pf.birthday=to_date('"+aBirthday +
				"','dd.MM.yyyy') and pf.checkTime_id='"+aCheckTimeId+"'").getSingleResult();
		if (list!=null) {			
			Long patF = Long.valueOf(list.toString());
			return updateDataByFondAutomatic(patF, aCheckTimeId, needUpdatePatient, needUpdateDocuments, needUpdatePolicy, needUpdateAttachment);
		}
		return false;
	}
	
	public boolean updateDataByFondAutomatic (Long aPatientFondId, Long aCheckId
			,boolean needUpdatePatient, boolean needUpdateDocuments, boolean needUpdatePolicy, boolean needUpdateAttachment) {
		try{
		if (needUpdateAttachment||needUpdateDocuments||needUpdatePatient||needUpdatePolicy) {
			StringBuilder str = new StringBuilder();
			String curDate = DateFormat.formatToDate(new java.util.Date()) ;
			str.append("update Patient p set ");
			int o=0;
			List<Object[]> listZ = theManager.createNativeQuery("select pf.id" +
					", pf.lastname, pf.firstname, pf.middlename, pf.commonnumber, pf.snils" +
					" ,pf.documentnumber,pf.documentseries, pf.documenttype, pf.documentdateissued, documentwhomissued" +
					" ,pf.lpuattached, pf.attachedtype, to_char(pf.attacheddate,'dd.MM.yyyy') as attDate " +
					" ,pf.policyseries, pf.policynumber, to_char(pf.policydatefrom,'dd.MM.yyyy') as polFrom, to_char(pf.policydateto,'dd.MM.yyyy') as polTo, pf.companycode, to_char(pf.birthday,'dd.MM.yyyy')as birthday, pf.patient_id" +
					" ,to_char(pf.checkDate,'dd.MM.yyyy') as checkDate " +
					" from patientfond pf where pf.id='"+aPatientFondId+"' ").getResultList();
			if (!listZ.isEmpty()) {
			Object[] arr = listZ.get(0);
			PatientFond patF = theManager.find(PatientFond.class, aPatientFondId);
			String aLastname = toStr(arr[1]), aFirstname = toStr(arr[2]), aMiddlename=toStr(arr[3]),aRz = toStr(arr[4])
					,aSnils = toStr(arr[5]),aDocNumber = toStr(arr[6]),aDocSeries = toStr(arr[7]),aDocType = toStr(arr[8])
					,aDocDateIssued = toStr(arr[9]), aDocWhomIssued =toStr(arr[10])
					,aAttachedLpu=toStr(arr[11]),aAttachedType=toStr(arr[12]),aAttachedDate=toStr(arr[13])
					,aPolicySeries=toStr(arr[14]),aPolicyNumber=toStr(arr[15]),aPolicyDateFrom=toStr(arr[16])
					,aPolicyDateTo=toStr(arr[17]),aCompany=toStr(arr[18]), aBirthday=toStr(arr[19]), aCheckDate = toStr(arr[21]);
					Long aPatientId =Long.valueOf(arr[20].toString()); 
			if (needUpdatePatient) {
			//	System.out.println("++++ UPDATE PATIENT! = "+aLastname + " "+ aFirstname);
				o=1;
				if (aSnils!=null&&aSnils.equals("")) {
					str.append(prepSql("snils",aSnils));
				}
				str.append(prepSql(str.length()>23?", commonnumber":"commonnumber",aRz));
				patF.setIsPatientUpdate(true);
			} else {patF.setIsPatientUpdate(false);}
			
			if (needUpdateDocuments) {
				System.out.println("++++ UPDATE DOCUMENT! = "+aLastname + " "+ aFirstname);
				if (aDocNumber!=null&&aDocSeries!=null&&aDocType!=null&&aDocDateIssued!=null) {
					if (o==1) {
						str.append(",");
					}
					str.append(prepSql("passportnumber",aDocNumber)).append(prepSql(",passportseries",aDocSeries,"",","))
					.append(prepSql("passporttype_id",aDocType,"(select v.id from vocidentitycard v where v.omccode=",")"))
					.append(prepSql(",passportdateissued",aDocDateIssued,"to_date(",",'dd.MM.yyyy')"))
					.append(prepSql(",passportwhomissued",aDocWhomIssued));
					o=1;
					patF.setIsDocumentUpdate(true);
				} else {patF.setIsDocumentUpdate(false);}
				
			} else {patF.setIsDocumentUpdate(false);}
			if (o==1) {
				str.append(" where id=").append(aPatientId);
				System.out.println("------------- UPDATE SQL STRING = "+str.toString());
				
				theManager.createNativeQuery(str.toString()).executeUpdate();
			}
			if (needUpdatePolicy) {
				System.out.println("++++ UPDATE POLICY! = "+aLastname + " "+ aFirstname);
				patF.setIsPolicyUpdate(updateOrCreatePolicyByFond(aPatientId, aRz, aLastname, aFirstname, aMiddlename, aBirthday, aCompany
						, aPolicySeries, aPolicyNumber, aPolicyDateFrom, aPolicyDateTo, aCheckDate));
				
				
			} else {patF.setIsPolicyUpdate(false);}
			if (needUpdateAttachment) {
				System.out.println("++++ UPDATE ATTACHMENT! = "+aLastname + " "+ aFirstname);
				patF.setIsAttachmentUpdate(updateOrCreateAttachment(aPatientId, aCompany, aAttachedLpu, aAttachedType, aAttachedDate,true));
				
			} else {patF.setIsAttachmentUpdate(false);}
			theManager.persist(patF);
			
			if (patF.getIsPatientUpdate()&&patF.getIsDocumentUpdate()&&patF.getIsPolicyUpdate()&&patF.getIsAttachmentUpdate()) {
				patF.setIsDifference(false);
			} else {
				patF.setIsDifference(needUpdatePatient(aPatientId, aPatientFondId));
			}
			
			
			theManager.persist(patF);
			return true;
			
			} else { 
				return false; 
			}
			
		}
	} catch (Exception e) {
		e.printStackTrace();
		
	}
		return false;
		
		
		
	}
	public boolean updateOrCreateAttachment(Long aPatientId, String aCompany, String aLpu, String aAttachedType, String aAttachedDate, boolean ignoreType) {
		SoftConfig sc = (SoftConfig) theManager.createQuery("from SoftConfig sc where sc.key='DEFAULT_LPU_OMCCODE'").getResultList().get(0);
		//String lpu = fiodr[7], attachedType=fiodr[8], attachedDate = fiodr[9];
		String lpu = aLpu, attachedType=aAttachedType, attachedDate = aAttachedDate;
		
//		RegInsuranceCompany insCompany =null; 
		RegInsuranceCompany insCompany =(RegInsuranceCompany) theManager.createQuery("from RegInsuranceCompany where omcCode = :code and (deprecated is null or deprecated='0')")
				.setParameter("code", aCompany).getSingleResult(); 
		
		
if (sc!=null && sc.getKeyValue().equals(lpu) && insCompany!=null) { //Создаем прикрепления только своей ЛПУ
		System.out.println("-----------Создаем прикрепления!!");
		List<LpuAttachedByDepartment> attachments = theManager.createQuery("from LpuAttachedByDepartment where patient_id=:pat and dateTo is null")
				.setParameter("pat", aPatientId).getResultList();
		VocAttachedType attType = (VocAttachedType) theManager.createQuery("from VocAttachedType where code=:code")
				.setParameter("code", attachedType).getResultList().get(0);
		
		if (attachments.isEmpty()) { // Создаем новое 
			System.out.println("--------------- ATT NOT FOUND, Создаем новое!!!");
			String lpuId = ((SoftConfig)theManager.createQuery("from SoftConfig sc where sc.key='DEFAULT_LPU'").getResultList().get(0)).getKeyValue();
			MisLpu lpuAtt = null;
			if (lpuId!=null&&!lpuId.equals("")) {
				lpuAtt = (MisLpu) theManager.find(MisLpu.class, Long.valueOf(lpuId));
			}
			
			if (lpuAtt!=null) {
				System.out.println("+++++++++++++++++++Найденное ЛПУ - "+lpuAtt.getFullname());
				LpuAttachedByDepartment att = new LpuAttachedByDepartment();
				att.setPatient(theManager.find(Patient.class, aPatientId));
				att.setLpu(lpuAtt);
				att.setAttachedType(attType);
				try {
					Date dat = DateFormat.parseSqlDate(attachedDate) ;
					att.setDateFrom(dat);
					att.setCompany(insCompany);
					att.setCreateUsername("fond_check");
					//Указать участок!!!
					theManager.persist(att);
					System.out.println("------- Прикрепление создано! Пациент = "+aPatientId);
					return true;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.out.println("Дата не распознана "+attachedDate);
					e.printStackTrace();
				}
			} else {
				System.out.println("ЛПУ с кодом '"+lpu+"' не найдено, прикрепление не создано");
			}
			
		} else  { // Обновляем существующее 
			for (LpuAttachedByDepartment a: attachments) {
				if (a.getAttachedType().equals(attType)||ignoreType) {
//						System.out.println("------ ATT FOUND!!!, обновляем существующее, "+a.getId());
					StringBuilder str = new StringBuilder();
					str.append("update LpuAttachedByDepartment set dateFrom=to_date('").append(attachedDate)
						.append("','dd.mm.yyyy'), company_id='"+insCompany.getId()+"',editusername='fond_check', attachedtype_id=(select id from vocattachedtype where code='"+attachedType+"') where id='").append(a.getId()).append("'");
					theManager.createNativeQuery(str.toString()).executeUpdate();
					System.out.println("Прикрепление Обновлено! Пациент = "+a.getPatient().getPatientInfo());
					return true;
					} else {
						System.out.println("Найдено прикрепление с другим типом, прикрепление не создано");
					}
			}
		}
	}
return true;
	}
	
	public boolean updateDataByFond(String aUsername, Long aPatientId, String aFiodr
			,String aDocument,String aPolicy,String aAddress
			,boolean aIsPatient, boolean aIsPolicy
			, boolean aIsDocument, boolean aIsAddress, boolean aIsAttachment) {
		String[] fiodr =null;
		String curDate = DateFormat.formatToDate(new java.util.Date()) ;
		
		if (aFiodr!=null && !aFiodr.equals("")) {
			fiodr = aFiodr.split("#") ;
			if ( aIsPatient) {
				if (aPatientId!=null &&aPatientId>Long.valueOf(0) &&(aIsPolicy||(fiodr.length>6 &&fiodr[6].length()==10))) {
					StringBuilder sql = new StringBuilder() ;
					if (!fiodr[0].startsWith("?")) {
						sql.append("update Patient set lastname='").append(fiodr[0]).append("'") ;
						sql.append(", firstname='").append(fiodr[1]).append("'") ;
						sql.append(", middlename='").append(fiodr[2]).append("'") ;
						sql.append(", birthday=to_date('").append(fiodr[3]).append("','dd.mm.yyyy')") ;
						sql.append(", snils='").append(fiodr[4]).append("'") ;
						sql.append(", commonNumber='").append(fiodr[5]).append("'") ;
						sql.append(", editDate=to_date('").append(curDate).append("','dd.mm.yyyy')") ;
						sql.append(", editUsername='").append(aUsername).append("'") ;
						if (fiodr.length>6 &&fiodr[6].length()==10) {
							sql.append(", deathDate=to_date('").append(fiodr[6]).append("','dd.mm.yyyy')") ;
						}
						sql.append(" where id='").append(aPatientId).append("'") ;
						theManager.createNativeQuery(sql.toString()).executeUpdate() ;
					} else {
						sql.append("update Patient set ") ;
						//sql.append("lastname='").append(fiodr[0]).append("'") ;
						//sql.append(", firstname='").append(fiodr[1]).append("'") ;
						//sql.append(", middlename='").append(fiodr[2]).append("'") ;
						sql.append(" birthday=to_date('").append(fiodr[3]).append("','dd.mm.yyyy')") ;
						sql.append(", snils='").append(fiodr[4]).append("'") ;
						sql.append(", editDate=to_date('").append(curDate).append("','dd.mm.yyyy')") ;
						sql.append(", editUsername='").append(aUsername).append("'") ;
						if (fiodr.length>6 &&fiodr[6].length()==10) {
							sql.append(", deathDate=to_date('").append(fiodr[6]).append("','dd.mm.yyyy')") ;
						}
						sql.append(" where id='").append(aPatientId).append("'") ;
						theManager.createNativeQuery(sql.toString()).executeUpdate() ;
						
					}
				}				
				
			}
			if (aIsAttachment){
				if (fiodr.length>7 && fiodr[7]!=null&&!fiodr[7].equals("")) { //Импорт данных прикрепления
					String aCompany = null;
					if (aPolicy!=null&&!aPolicy.equals("")) {
						String[] policies = aPolicy.split("&");
						for (String policy: policies) {
							String[] policyInfo = policy.split("#");
							if (policyInfo[6].equals("1")) {
								 aCompany = policyInfo[0];
								break;								
							}
						}
					}
					updateOrCreateAttachment(aPatientId, aCompany, fiodr[7], fiodr[8], fiodr[9],false);
				
			}
		}
			
		}
		if (aDocument!=null &&!aDocument.equals("") &&aIsDocument) {
			String[] doc = aDocument.split("#") ;
			StringBuilder sql = new StringBuilder() ;
			if (!doc[0].startsWith("?")) {
				sql.append("update Patient set passportSeries='").append(doc[1]).append("'") ;
				sql.append(", passportNumber='").append(doc[2]).append("'") ;
				sql.append(", passportDateIssued=to_date('").append(doc[3]).append("','dd.mm.yyyy')") ;
				sql.append(", passportWhomIssued='").append(doc[4]).append("'") ;
				sql.append(", passportType_id=(select id from VocIdentityCard where omcCode='").append(doc[0]).append("')") ;
				sql.append(", editDate=to_date('").append(curDate).append("','dd.mm.yyyy')") ;
				sql.append(", editUsername='").append(aUsername).append("'") ;
				sql.append(" where id='").append(aPatientId).append("'") ;
				theManager.createNativeQuery(sql.toString()).executeUpdate() ;
			} else {
				sql.append("update Patient set passportSeries='").append(doc[1]).append("'") ;
				sql.append(", passportNumber='").append(doc[2]).append("'") ;
				sql.append(", passportDateIssued=to_date('").append(doc[3]).append("','dd.mm.yyyy')") ;
				//sql.append(", passportWhomIssued='").append(doc[4]).append("'") ;
				sql.append(", passportType_id=(select id from VocIdentityCard where omcCode='").append(doc[0]).append("')") ;
				sql.append(", editDate=to_date('").append(curDate).append("','dd.mm.yyyy')") ;
				sql.append(", editUsername='").append(aUsername).append("'") ;
				sql.append(" where id='").append(aPatientId).append("'") ;
				theManager.createNativeQuery(sql.toString()).executeUpdate() ;
				
			}
		}
		if (aAddress!=null &&!aAddress.equals("") &&aIsAddress) {
			String[] adr = aAddress.split("#") ;
			StringBuilder sql = new StringBuilder() ;
			String kladr = adr[0] ;
			String sity = adr[5].toUpperCase() ;
			String street = adr[6].toUpperCase() ;
			String rayon = adr[4].toUpperCase() ;
			String region = adr[8].toUpperCase() ;
			String addressid=getAddressByKladr(kladr,region,rayon, sity, street) ;
			
			sql = new StringBuilder() ;
			sql.append("update Patient set ") ;
			if (addressid!=null&&!addressid.equals("")) sql.append(" address_addressid='").append(addressid).append("' , ") ;
			sql.append(" houseNumber='").append(adr[1]).append("'") ;
			sql.append(", houseBuilding='").append(adr[2]).append("'") ;
			sql.append(", flatNumber='").append(adr[3]).append("'") ;
			sql.append(", rayon_id=(select id from VocRayon where code='").append(rayon).append("')") ;
			sql.append(", editDate=to_date('").append(curDate).append("','dd.mm.yyyy')") ;
			sql.append(", editUsername='").append(aUsername).append("'") ;
			sql.append(" where id='").append(aPatientId).append("'") ;
			theManager.createNativeQuery(sql.toString()).executeUpdate() ;
		}
		if (aPolicy!=null && !aPolicy.equals("") &&aIsPolicy) {
			String[] pols = aPolicy.split("&") ;
			
			for (String p:pols) {
				String[] pol = p.split("#") ;
				System.out.println(pol.length) ;
				System.out.println(p) ;
				
				updateOrCreatePolicyByFond(aPatientId, pol[5], fiodr[0], fiodr[1], fiodr[2], fiodr[3], pol[0], pol[1], pol[2],pol[3],pol[4],curDate) ;
			}
		}
		return true ;
	}
	public boolean updateOrCreatePolicyByFond(Long aPatientId, String aRz, String aLastname, String aFirstname
			, String aMiddlename, String aBirthday, String aComp, String aSeries
			, String aNumber, String aDateFrom, String aDateTo,String aCurrentDate) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select count(*) from medpolicy ") ;
		sql.append(" where patient_id='").append(aPatientId).append("'");
		sql.append(" and series='").append(aSeries).append("'") ;
		sql.append(" and polNumber='").append(aNumber).append("'") ;
		Object cnt =theManager.createNativeQuery(sql.toString()).getSingleResult() ;
		Long cntL = ConvertSql.parseLong(cnt) ;
		
		String type = getTypePolicy(aSeries) ;
		if (cntL!=null &&cntL>Long.valueOf(0)) {
			sql = new StringBuilder() ;
			sql.append("update MedPolicy set lastname='").append(aLastname).append("'") ;
			sql.append(", firstname='").append(aFirstname).append("'") ;
			sql.append(", middlename='").append(aMiddlename).append("'") ;
			sql.append(", birthday=to_date('").append(aBirthday).append("','dd.mm.yyyy')") ;
			sql.append(", company_id=(select id from REG_IC where omcCode='").append(aComp).append("')") ;
			sql.append(", actualDateFrom=to_date('").append(aDateFrom).append("','dd.mm.yyyy')") ;
			if (aDateTo!=null && !aDateTo.equals("")) {
				sql.append(", actualDateTo=to_date('").append(aDateTo).append("','dd.mm.yyyy')") ;
			} else {
				sql.append(", actualDateTo=null") ;
			}
			sql.append(", commonNumber='").append(aRz).append("'") ;
			sql.append(", type_id=(select id from VocMedPolicyOmc where code='").append(type).append("')") ;
			sql.append(", confirmationDate=to_date('").append(aCurrentDate).append("','dd.mm.yyyy')") ;
			sql.append(" where patient_id='").append(aPatientId).append("'");
			sql.append(" and series='").append(aSeries).append("'") ;
			sql.append(" and polNumber='").append(aNumber).append("'") ;
			
			theManager.createNativeQuery(sql.toString()).executeUpdate() ;
			try {
				Date dat = DateFormat.parseSqlDate(aDateFrom) ;
				Calendar cal = Calendar.getInstance() ;
				cal.setTime(dat) ;
				cal.add(Calendar.DAY_OF_MONTH, -1) ;
				SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
				String dateFrom = format.format(cal.getTime()) ;
				sql = new StringBuilder() ;
				sql.append("update medpolicy set actualdateto=(to_date('").append(dateFrom).append("','dd.mm.yyyy')) where patient_id='").append(aPatientId)
				.append("' and actualdateFrom<to_date('").append(aDateFrom)
				.append("','dd.mm.yyyy') and (actualdateTo>=to_date('").append(aDateFrom)
				.append("','dd.mm.yyyy') or actualdateTo is null)") ;
			theManager.createNativeQuery(sql.toString()).executeUpdate() ;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			sql = new StringBuilder() ;
			sql.append("select count(*) from medpolicy ") ;
			sql.append(" where series='").append(aSeries).append("'") ;
			sql.append(" and polNumber='").append(aNumber).append("'") ;
			Object cnt1 =theManager.createNativeQuery(sql.toString()).getSingleResult() ;
			Long cntL1 = ConvertSql.parseLong(cnt1) ;
			if (cntL1.equals(Long.valueOf(0))) {
				sql = new StringBuilder() ;
				sql = sql.append("select id,omcCode from REG_IC where omcCode='").append(aComp).append("' order by id desc") ;
				List<Object[]> idS = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
				sql = new StringBuilder() ;
				sql = sql.append("select id,code from VocMedPolicyOmc where code='").append(type).append("' order by id desc") ;
				List<Object[]> idT = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
				if (idS.size()>0 && idT.size()>0) {
					sql = new StringBuilder() ;
					sql.append("insert into MedPolicy (dtype,company_id,actualDateFrom,actualDateTo,commonNumber,patient_id,series,polNumber,type_id,lastname,firstname,middlename,birthday, confirmationDate) values ('MedPolicyOmc'") ;
					sql.append(", '").append(idS.get(0)[0]).append("'") ;
					sql.append(", to_date('").append(aDateFrom).append("','dd.mm.yyyy')") ;
					
					if (aDateTo!=null && !aDateTo.equals("")) {
						sql.append(", to_date('").append(aDateTo).append("','dd.mm.yyyy')") ;
					} else {
						sql.append(", null") ;
					}
					//sql.append(", to_date('").append(pol[4]).append("','dd.mm.yyyy')") ;
					sql.append(", '").append(aRz).append("'") ;
					sql.append(", '").append(aPatientId).append("'");
					sql.append(", '").append(aSeries).append("'") ;
					sql.append(", '").append(aNumber).append("','").append(idT.get(0)[0]).append("','").append(aLastname).append("'") ;
				sql.append(",'").append(aFirstname).append("'") ;
				sql.append(",'").append(aMiddlename).append("'") ;
				sql.append(", to_date('").append(aBirthday).append("','dd.mm.yyyy'),to_date('").append(aCurrentDate).append("','dd.mm.yyyy'))") ;
				theManager.createNativeQuery(sql.toString()).executeUpdate() ;

				try {
					Date dat = DateFormat.parseSqlDate(aDateFrom) ;
					Calendar cal = Calendar.getInstance() ;
					cal.setTime(dat) ;
					cal.add(Calendar.DAY_OF_MONTH, -1) ;
					SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
					String dateFrom = format.format(cal.getTime()) ;
					sql = new StringBuilder() ;
					sql.append("update medpolicy set actualdateto=(to_date('").append(dateFrom).append("','dd.mm.yyyy')-1) where patient_id='").append(aPatientId)
						.append("' and actualdateFrom<to_date('").append(aDateFrom)
						.append("','dd.mm.yyyy') and (actualdateTo>=to_date('").append(aDateFrom)
						.append("','dd.mm.yyyy') or actualdateTo is null)") ;
					theManager.createNativeQuery(sql.toString()).executeUpdate() ;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			} else {
				return false ;
			}
		}
		return true ;
	}
	public void insertCheckFondData(
			String aLastname,String aFirstname,String aMiddlename,String aBirthday
			,String aSnils
			,String aCommonNumber,String aPolicySeries,String aPolicyNumber
			,String aPolicyDateFrom, String aPolicyDateTo
			,String aUsername, String aCheckType
			,String aCompanyCode ,String aCompabyCodeF,String aCompanyOgrn, String aCompanyOkato
			,String aDocumentType, String aDocumentSeries,String aDocumentNumber
			,String aKladr,String aHouse, String aHouseBuilding, String aFlat
			,String aLpuAttached, String aAttachedDate, String aAttachedType
			) throws ParseException {
		insertCheckFondData(aLastname,aFirstname,aMiddlename,aBirthday,aSnils,aCommonNumber,aPolicySeries,aPolicyNumber
				,aPolicyDateFrom,aPolicyDateTo,aUsername,aCheckType,aCompanyCode,aCompabyCodeF,aCompanyOgrn, aCompanyOkato
				,aDocumentType,aDocumentSeries,aDocumentNumber, aKladr,aHouse,aHouseBuilding,aFlat
				,aLpuAttached,aAttachedDate,aAttachedType,null);
	}
	public void insertCheckFondData(
			String aLastname,String aFirstname,String aMiddlename,String aBirthday
			,String aSnils
			,String aCommonNumber,String aPolicySeries,String aPolicyNumber
			,String aPolicyDateFrom, String aPolicyDateTo
			,String aUsername, String aCheckType
			,String aCompanyCode ,String aCompabyCodeF,String aCompanyOgrn, String aCompanyOkato
			,String aDocumentType, String aDocumentSeries,String aDocumentNumber
			,String aKladr,String aHouse, String aHouseBuilding, String aFlat
			,String aLpuAttached, String aAttachedDate, String aAttachedType, String dateDeath
			) throws ParseException {
		insertCheckFondData(aLastname,aFirstname,aMiddlename,aBirthday,aSnils,aCommonNumber,aPolicySeries,aPolicyNumber
				,aPolicyDateFrom,aPolicyDateTo,aUsername,aCheckType,aCompanyCode,aCompabyCodeF,aCompanyOgrn, aCompanyOkato
				,aDocumentType,aDocumentSeries,aDocumentNumber, aKladr,aHouse,aHouseBuilding,aFlat
				,aLpuAttached,aAttachedDate,aAttachedType,null,null,null,null,null,null,null);
	}
		
	
	public Long insertCheckFondData(
			String aLastname,String aFirstname,String aMiddlename,String aBirthday
			,String aSnils
			,String aCommonNumber,String aPolicySeries,String aPolicyNumber
			,String aPolicyDateFrom, String aPolicyDateTo
			,String aUsername, String aCheckType
			,String aCompanyCode ,String aCompabyCodeF,String aCompanyOgrn, String aCompanyOkato
			,String aDocumentType, String aDocumentSeries,String aDocumentNumber
			,String aKladr,String aHouse, String aHouseBuilding, String aFlat
			,String aLpuAttached, String aAttachedDate, String aAttachedType, String dateDeath
			,String aDocumentDateIssued, String aDocumentWhomIssued, String aDoctorSnils, String aCodeDepartment, String aPatientId
			,PatientFondCheckData aCheckTime
			) throws ParseException {
		PatientFond fond = new PatientFond() ;
		fond.setLastname(aLastname) ;
		fond.setFirstname(aFirstname) ;
		fond.setMiddlename(aMiddlename) ;
		fond.setLastname(aLastname) ;
		fond.setBirthday(DateFormat.parseSqlDate(aBirthday)) ;
		fond.setCommonNumber(aCommonNumber) ;
		fond.setPolicyNumber(aPolicyNumber) ;
		fond.setPolicySeries(aPolicySeries) ;
		fond.setPolicyDateFrom(DateFormat.parseSqlDate(aPolicyDateFrom));
		fond.setPolicyDateTo(DateFormat.parseSqlDate(aPolicyDateTo)) ;
		fond.setCheckDate(new java.sql.Date(new java.util.Date().getTime())) ;
		fond.setChecker(aUsername) ;
		fond.setCheckType(aCheckType) ;
		fond.setCompanyCode(aCompanyCode) ;
		fond.setCompabyCodeF(aCompabyCodeF) ;
		fond.setCompanyOgrn(aCompanyOgrn);
		fond.setCompanyOkato(aCompanyOkato);
		fond.setDocumentNumber(aDocumentNumber);
		fond.setDocumentSeries(aDocumentSeries);
		fond.setDocumentType(aDocumentType);
		fond.setKladr(aKladr);
		fond.setSnils(aSnils);
		fond.setHouse(aHouse) ;
		fond.setHouseBuilding(aHouseBuilding);
		fond.setFlat(aFlat) ;
		fond.setAttachedType(aAttachedType);
		fond.setAttachedDate(DateFormat.parseSqlDate(aAttachedDate));
		fond.setLpuAttached(aLpuAttached);
		fond.setDeathDate(DateFormat.parseSqlDate(dateDeath));
		fond.setDocumentDateIssued(aDocumentDateIssued);
		fond.setDocumentWhomIssued(aDocumentWhomIssued);
		fond.setDoctorSnils(aDoctorSnils);
		fond.setDepartment(aCodeDepartment);
		Patient pat = null;
		if (aPatientId!=null&&aPatientId!=""&&Long.valueOf(aPatientId)!=0) {
			pat = theManager.find(Patient.class, Long.valueOf(aPatientId));
			fond.setPatient(pat);
		}
		if (aCheckTime!=null) {
			fond.setCheckTime(aCheckTime);
		}
		theManager.persist(fond) ;
		if (pat!=null) {
			fond.setIsDifference(needUpdatePatient(Long.valueOf(aPatientId), fond.getId()));
			theManager.persist(fond) ;
		}
		
		return fond.getId();
	}
	public PatientForm getPatientById(Long aId) {
		//Patient p = theManager.find(Patient.class, aId) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select p.lastname,p.firstname,p.middlename,to_char(p.birthday,'dd.mm.yyyy') as birthday,p.snils ")
			.append(" ,p.passportNumber,p.passportSeries,p.passportType_id,vic.omcCode as vicomccode")
			.append(" ,mp.series,mp.polNumber, ri.omcCode as riomccode,mp.commonNumber,a.kladr")
			.append(" ,p.houseNumber,p.houseBuilding,flatNumber")
			.append(" from patient p ")
			.append(" left join Address2 a on a.addressid=p.address_addressid")
			.append(" left join VocIdentityCard vic on vic.id=p.passportType_id")
			.append(" left join MedPolicy mp on mp.patient_id=p.id")
			.append(" left join REG_IC ri on ri.id=mp.company_id")
			.append(" where p.id='").append(aId).append("' order by mp.actualDateFrom desc");
		PatientForm frm = new PatientForm() ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		if (list.size()>0) {
			Object[] objs = list.get(0) ;
			frm.setLastname((String)objs[0]) ;
			frm.setFirstname((String)objs[1]) ;
			frm.setMiddlename((String)objs[2]) ;
			frm.setBirthday((String)objs[3]) ;
			frm.setSnils((String)objs[4]) ;
			
			frm.setPassportNumber(""+objs[5]) ;
			frm.setPassportSeries(""+objs[6]) ;
			
			frm.setPassportType(ConvertSql.parseLong(objs[7])) ;
			
			frm.setPassportWhomIssued(""+objs[8]) ;
			frm.setAddressInfo(""+(objs[13]!=null?objs[13]:"")) ;
			frm.setHouseNumber(""+(objs[14]!=null?objs[14]:"")) ;
			frm.setHouseBuilding(""+(objs[15]!=null?objs[15]:"")) ;
			frm.setFlatNumber(""+(objs[16]!=null?objs[16]:"")) ;
			
			MedPolicyOmcForm policy = new MedPolicyOmcForm() ;
			
			policy.setSeries(""+objs[9]);
			policy.setPolNumber(""+objs[10]);
			policy.setText(""+objs[11]);
			policy.setCommonNumber(""+(objs[12]!=null?objs[12]:""));
			
			frm.setPolicyOmcForm(policy) ;
			
		}
		return frm ;
	}
	private final static Logger LOG = Logger
			.getLogger(PatientServiceBean.class);

	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	public String getOmcCodeByPassportType(Long aPassportType) {
		VocIdentityCard vic = theManager.find(VocIdentityCard.class, aPassportType) ;
		return vic!=null?vic.getOmcCode():"" ;
	}
	public void setAddParamByMedCase(String aParam,Long aMedCase,Long aStatus)  {
		MedCase mc = theManager.find(MedCase.class, aMedCase) ;
		String method = new StringBuilder().append("set").append(Character.toUpperCase(aParam.charAt(0))).append(aParam.substring(1)).toString() ;
		try {
			Method ejbSetterMethod =mc.getClass().getMethod(method,Long.class) ;
			ejbSetterMethod.invoke(mc, aStatus);
		} catch (Exception e) {
			
		}
	}
    public void movePatientDoubleDataAndDeleteOld(Long aIdNew, Long aIdOld) {
		Patient newpat = theManager.find(Patient.class, aIdNew) ;
		Patient oldpat = theManager.find(Patient.class, aIdOld) ;
		if (newpat.getAddress()==null && oldpat.getAddress()!=null) {
			newpat.setAddress(oldpat.getAddress()) ;
		}
		movePatientDoubleData(aIdNew, aIdOld) ;
		theManager.remove(oldpat) ;
		
    }
    public void movePatientDoubleData(Long aIdNew, Long aIdOld)  {
		Patient newpat = theManager.find(Patient.class, aIdNew) ;
		Patient oldpat = theManager.find(Patient.class, aIdOld) ;
		if (newpat!=null && oldpat!=null) {
			theManager.createNativeQuery("	update Patient set attachedOmcPolicy_id = null where id =:idold	").setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Award set person_id =:idnew where person_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update BirthCase set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update BirthCase set mother_id =:idnew where mother_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			//theManager.createNativeQuery("	update Customer set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update DeathCase set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update DeathCase set mother_id =:idnew where mother_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Diagnosis set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update DisabilityCase set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update DisabilityDocument set nursedPatient_id =:idnew where nursedPatient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update DisabilityDocument set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Education set person_id =:idnew where person_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update IdentityCard set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Kinsman set person_id =:idnew where person_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Kinsman set kinsman_id =:idnew where kinsman_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update LanguageSkill set person_id =:idnew where person_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update LpuAttachedByDepartment set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Medcard set person_id =:idnew where person_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update MedCase set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update MedPolicy set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update NewBorn set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Pregnancy set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Privilege set person_id =:idnew where person_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Qualification set person_id =:idnew where person_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update SurgicalOperation set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Vaccination set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update WorkBook set person_id =:idnew where person_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Worker set person_id =:idnew where person_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Kinsman set person_id =:idnew where person_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update PsychiatricCareCard set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update WorkCalendarTime set prepatient_id =:idnew where prepatient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update WorkCalendarTime set PersonReserve_id =:idnew where PersonReserve_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Document set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update ContractPerson set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update WorkCalendarHospitalBed set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update ExtDispCard set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update ClinicExpertCard set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("  update PatientFond set patient_id =:idnew where patient_id =:idold ").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("  update JournalPatientFondCheck set patient_id =:idnew where patient_id =:idold ").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("  update JournalChangePatient set patient_id =:idnew where patient_id =:idold ").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			
		}
	}
	
	public List<VocOrgForm> findOrg(String aNewNumber, String aOldNumber, String aName) {
		QueryClauseBuilder builder = new QueryClauseBuilder();
		builder.addLike("oldFondNumber", aOldNumber) ;
		builder.addLike("fondNumber", aNewNumber) ;
		builder.addLike("name", aName) ;
		Query query = builder.build(theManager, "from VocOrg where",
		" order by name,fondNumber,oldFondNumber");
		List<VocOrgForm> ret = new LinkedList<VocOrgForm>();
		StringBuilder sql = new StringBuilder() ;
		sql.append("from VocOrg") ;
		boolean where = false ;
		if (!StringUtil.isNullOrEmpty(aOldNumber)) {
			where =true ;
			sql.append(" where ") ;
			sql.append(" oldFondNumber like '%").append(aOldNumber).append("%'") ;
		}
		if (!StringUtil.isNullOrEmpty(aNewNumber)) {
			if (where) {
				sql.append(" and ") ;
			} else {
				sql.append(" where ") ;
				where = true ;
			}
			sql.append(" fondNumber like '%").append(aNewNumber).append("%'") ;
		}
		if (!StringUtil.isNullOrEmpty(aName)) {
			if (where) {
				sql.append(" and ") ;
			} else {
				sql.append(" where ") ;
				where = true ;
			}
			sql.append(" name like '%").append(aName).append("%'") ;
		}
		List<VocOrg> orgs = theManager.createQuery(sql.toString())
			.setMaxResults(50).getResultList() ;
		for (VocOrg org : orgs) {
			try {
				ret.add(theEntityFormService.loadForm(VocOrgForm.class,
						org));
			} catch (EntityFormException e) {
				throw new IllegalStateException(e);
			}
		}
		return ret ;
	}

	/**
	 * Поиск пациента
	 */
	public List<PatientForm> findPatient(Long aLpuId, Long aLpuAreaId,
			String aLastname) {
		String defaultLpu = SoftConfigServiceBean.getDefaultParameterByConfig("DEFAULT_LPU_OMCCODE", "-", theManager);
		boolean isEnableLimitAreas = theSessionContext.isCallerInRole("/Policy/Mis/Patient/EnableLimitPsychAreas") ;
		System.out.print("/Policy/Mis/Patient/EnableLimitPsychAreas") ;
		System.out.print(isEnableLimitAreas) ;
		System.out.print(theSessionContext.getCallerPrincipal().toString()) ;
		
		List<PatientForm> ret = new LinkedList<PatientForm>();
		StringBuilder sqlFld = new StringBuilder() ;
		sqlFld.append(" select p.id,p.lastname,p.firstname,p.middlename,p.birthday") ;
		sqlFld.append(" ,p.patientSync,case when p.colorType='1' then p.ColorType else null end as ColorType ") ;
		sqlFld.append(" ,list(case when att.dateto is null then to_char(att.datefrom,'dd.mm.yyyy')||' ('||vat.code||') '||ml.name else null end) as lpuname") ;
		sqlFld.append(" ,list(case when att.dateto is null then ma.number else null end) as areaname") ;
		sqlFld.append(" ,(select case when pf1.id is null then '-' else 'от '||to_char(pf1.checkdate,'dd.mm.yyyy') ||") ;
		sqlFld.append(" coalesce(' дата смерти: '||to_char(pf1.deathdate,'dd.mm.yyyy'),'') ");
		sqlFld.append(" ||case when pf1.lpuattached!='").append(defaultLpu).append("' then ' прикреплен к ЛПУ ' ||pf1.lpuattached ||' с '||to_char(pf1.attacheddate,'dd.mm.yyyy') ");
		sqlFld.append(" when pf1.lpuattached='").append(defaultLpu).append("' then ' прикреплен к ЛПУ с '||to_char(pf1.attacheddate,'dd.mm.yyyy') ");
		sqlFld.append(" else '' end end from PatientFond pf1 ");
		sqlFld.append(" where pf1.id=(select max(pf.id) from PatientFond pf where pf.lastname=p.lastname and pf.firstname=p.firstname and pf.middlename=p.middlename and pf.birthday=p.birthday ) ");
		sqlFld.append(" ) as fondinfo ");
		
		if (isEnableLimitAreas) {
			String username = theSessionContext.getCallerPrincipal().toString() ;
			QueryClauseBuilder builder = new QueryClauseBuilder();
			StringBuilder sql = new StringBuilder() ;
			sqlFld = new StringBuilder() ;
			sqlFld.append("select p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.patientSync,case when p.colorType='1' then p.ColorType else null end as ColorType");
			sqlFld.append(" ,cast('' as varchar(1)) as d1, cast('' as varchar(1)) as d2, cast('' as varchar(1)),(select case when pf1.id is null then '-' else 'от '||to_char(pf1.checkdate,'dd.mm.yyyy') ||") ;
			sqlFld.append(" coalesce(' дата смерти: '||to_char(pf1.deathdate,'dd.mm.yyyy'),'') ");
			sqlFld.append(" ||case when pf1.lpuattached!='").append(defaultLpu).append("' then ' прикреплен к ЛПУ ' ||pf1.lpuattached ||' с '||to_char(pf1.attacheddate,'dd.mm.yyyy') ");
			sqlFld.append(" when pf1.lpuattached='").append(defaultLpu).append("' then ' прикреплен к ЛПУ с '||to_char(pf1.attacheddate,'dd.mm.yyyy') ");
			sqlFld.append(" else '' end end from PatientFond pf1 ");
			sqlFld.append(" where pf1.id=(select max(pf.id) from PatientFond pf where pf.lastname=p.lastname and pf.firstname=p.firstname and pf.middlename=p.middlename and pf.birthday=p.birthday ) ");
			sqlFld.append(" ) as fondinfo ");
			//sql ;
			//sql. ;
			sql.append(" left join psychiatricCareCard pcc on pcc.patient_id=p.id") ;
			sql.append(" left join lpuareapsychcarecard lpcc on lpcc.careCard_id=pcc.id") ;
			sql.append(" left join lpuarea la on la.id = lpcc.lpuarea_id") ;
			sql.append(" left join mislpu ml on ml.id=la.lpu_id") ;
			sql.append(" left join worker w on w.lpu_id=ml.id") ;
			sql.append(" left join workfunction wf on wf.worker_id=w.id") ;
			sql.append(" left join secuser su on su.id=wf.secuser_id") ;
			
			sql.append(" where ((su.login='").append(username).append("' or p.createUsername='").append(username).append("') and lpcc.transferDate is null and lpcc.finishDate is null)") ;

			if (!StringUtil.isNullOrEmpty(aLastname)) {
				StringTokenizer st = new StringTokenizer(aLastname, " \t;,.");
				StringBuilder p1 = new StringBuilder() ;
				StringBuilder p2 = new StringBuilder() ;
				if (st.hasMoreTokens()) {
					p1.append(" p.lastname like :plastname ");
					p2.append(" jcp.lastname like :plastname ");
					builder.addParameter("plastname", st.nextToken() + "%");
				}
				if (st.hasMoreTokens()) {
					p1.append(" and p.firstname like :pfirstname ");
					p2.append(" and jcp.firstname like :pfirstname ");
					builder.addParameter("pfirstname", st.nextToken() + "%");
				}
				if (st.hasMoreTokens()) {
					p1.append(" and p.middlename like :pmiddlename ");
					p2.append(" and jcp.middlename like :pmiddlename ");
					builder.addParameter("pmiddlename", st.nextToken() + "%");
				}
				Query query = builder.buildNative(theManager, new StringBuilder().append(sqlFld)
						.append(" from patient p ").append(sql)
						.append(" and ").append(p1).toString()," group by p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.patientSync,p.ColorType order by p.lastname,p.firstname");
				appendNativeToList(query, ret,null);
				sqlFld = new StringBuilder() ;
				sqlFld.append("select p.id,p.lastname||' '||list(case when p.lastname!=jcp.lastname then '('||jcp.lastname||')' else '' end) as lastname");
				sqlFld.append(",p.firstname||' '||list(case when p.firstname!=jcp.firstname then '('||jcp.firstname||')' else '' end) as firstname");
				sqlFld.append(",p.middlename||' '||list(case when p.middlename!=jcp.middlename then '('||jcp.middlename||')' else '' end) as middlename,p.birthday,p.patientSync,case when p.colorType='1' then p.ColorType else null end as ColorType");
				sqlFld.append(" ,cast('' as varchar(1)) as d1, cast('' as varchar(1)) as d2, cast('' as varchar(1)),(select case when pf1.id is null then '-' else 'от '||to_char(pf1.checkdate,'dd.mm.yyyy') ||") ;
				sqlFld.append(" coalesce(' дата смерти: '||to_char(pf1.deathdate,'dd.mm.yyyy'),'') ");
				sqlFld.append(" ||case when pf1.lpuattached!='").append(defaultLpu).append("' then ' прикреплен к ЛПУ ' ||pf1.lpuattached ||' с '||to_char(pf1.attacheddate,'dd.mm.yyyy') ");
				sqlFld.append(" when pf1.lpuattached='").append(defaultLpu).append("' then ' прикреплен к ЛПУ с '||to_char(pf1.attacheddate,'dd.mm.yyyy') ");
				sqlFld.append(" else '' end end from PatientFond pf1 ");
				sqlFld.append(" where pf1.id=(select max(pf.id) from PatientFond pf where pf.lastname=p.lastname and pf.firstname=p.firstname and pf.middlename=p.middlename and pf.birthday=p.birthday ) ");
				sqlFld.append(" ) as fondinfo ");
				Query query1 = builder.buildNative(theManager, new StringBuilder()
				.append(sqlFld)
				.append(" from JournalChangePatient jcp ")
				.append(" left join patient p on jcp.patient_id=p.id ")
				.append(sql)
				.append(" and ").append(p2).append(" and (jcp.lastname!=p.lastname or jcp.firstname!=p.firstname or jcp.middlename!=p.middlename)").toString()," group by p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.patientSync,p.ColorType order by p.lastname,p.firstname");
				appendNativeToList(query1, ret,"Изменены персональные данные");
			}
		} else {
			if (CAN_DEBUG) {
				LOG.debug("findPatient() aLpuId = " + aLpuId + ", aLpuAreaId = "
						+ aLpuAreaId + ", aLastname = " + aLastname);
			}
			StringBuilder sql = new StringBuilder() ;
			StringBuilder sqlFld1 = new StringBuilder() ;
			sqlFld1.append("select p.id,p.lastname||' '||list(case when p.lastname!=jcp.lastname then '('||jcp.lastname||')' else '' end) as lastname");
			sqlFld1.append(",p.firstname||' '||list(case when p.firstname!=jcp.firstname then '('||jcp.firstname||')' else '' end) firstname");
			sqlFld1.append(",p.middlename||' '||list(case when p.middlename!=jcp.middlename then '('||jcp.middlename||')' else '' end) as middlename");
			sqlFld1.append(" ,p.birthday") ;
			sqlFld1.append(" ,p.patientSync,case when p.colorType='1' then p.ColorType else null end as ColorType ") ;
			sqlFld1.append(" ,list(case when att.dateto is null then to_char(att.datefrom,'dd.mm.yyyy')||' ('||vat.code||') '||ml.name else null end) as lpuname") ;
			sqlFld1.append(" ,list(case when att.dateto is null then ma.number else null end) as areaname") ;
			sqlFld1.append(" ,(select case when pf1.id is null then '-' else 'от '||to_char(pf1.checkdate,'dd.mm.yyyy') ||") ;
			sqlFld1.append(" coalesce(' дата смерти: '||to_char(pf1.deathdate,'dd.mm.yyyy'),'') ");
			sqlFld1.append(" ||case when pf1.lpuattached!='").append(defaultLpu).append("' then ' прикреплен к ЛПУ ' ||pf1.lpuattached ||' с '||to_char(pf1.attacheddate,'dd.mm.yyyy') ");
			sqlFld1.append(" when pf1.lpuattached='").append(defaultLpu).append("' then ' прикреплен к ЛПУ с '||to_char(pf1.attacheddate,'dd.mm.yyyy') ");
			sqlFld1.append(" else '' end end from PatientFond pf1 ");
			sqlFld1.append(" where pf1.id=(select max(pf.id) from PatientFond pf where pf.lastname=p.lastname and pf.firstname=p.firstname and pf.middlename=p.middlename and pf.birthday=p.birthday ) ");
			sqlFld1.append(" ) as fondinfo ");

			//sql.append(" from Patient p") ;
			//sql.append(" left join JournalChangePatient jcp on jcp.patient_id=p.id") ;
			sql.append(" left join LpuAttachedByDepartment att on att.patient_id=p.id") ;
			sql.append(" left join Mislpu ml on ml.id=att.lpu_id") ;
			sql.append(" left join lpuarea ma on ma.id=att.area_id") ;
			sql.append(" left join VocAttachedType vat on vat.id=att.AttachedType_id") ;
			sql.append(" where") ;
			StringBuilder p1 = new StringBuilder() ;
			StringBuilder p2 = new StringBuilder() ;
	
			QueryClauseBuilder builder = new QueryClauseBuilder();
			if (!StringUtil.isNullOrEmpty(aLastname)) {
				StringTokenizer st = new StringTokenizer(aLastname, " \t;,.");
				if (st.hasMoreTokens()) {
					p1.append(" p.lastname like :plastname ");
					p2.append(" jcp.lastname like :plastname ");
					builder.addParameter("plastname", st.nextToken() + "%");
				}
				if (st.hasMoreTokens()) {
					p1.append(" and p.firstname like :pfirstname ");
					p2.append(" and jcp.firstname like :pfirstname ");
					builder.addParameter("pfirstname", st.nextToken() + "%");
				}
				if (st.hasMoreTokens()) {
					p1.append(" and p.middlename like :pmiddlename ");
					p2.append(" and jcp.middlename like :pmiddlename ");
					builder.addParameter("pmiddlename", st.nextToken() + "%");
				}
				//sql.append(" ((").append(p1).append(") or (").append(p2).append("))") ;
			}
			
			builder.add("att.lpu_id", aLpuId);
			builder.add("att.area_id", aLpuAreaId);
			Query query = builder.buildNative(theManager, new StringBuilder().append(sqlFld)
					.append(" from patient p ").append(sql)
					.append(p1).toString(),
					"group by p.id,p.lastname,p.firstname,p.middlename,p.birthday ,p.patientSync, p.colorType order by p.lastname,p.firstname");
			appendNativeToList(query, ret,null);
			Query query1 = builder.buildNative(theManager, new StringBuilder().append(sqlFld1)
					.append(" from JournalChangePatient jcp ")
					.append(" left join patient p on jcp.patient_id=p.id ")
					.append(sql)
					.append(p2)
					.append(" and (jcp.lastname!=p.lastname or jcp.firstname!=p.firstname or jcp.middlename!=p.middlename)")
					.append(" and not (").append(p1).append(")") 
					.toString(),
					"group by p.id,p.lastname,p.firstname,p.middlename,p.birthday ,p.patientSync, p.colorType order by p.lastname,p.firstname");
			appendNativeToList(query1, ret,null);
	
			
			// поиск по полису
			if(!StringUtil.isNullOrEmpty(aLastname) && ret.isEmpty()) {
				appendNativeToList(findByMedCardNumber(sqlFld,aLastname), ret,null);
			}
			// Поиск по коду синхронизации
			if(!StringUtil.isNullOrEmpty(aLastname) && ret.isEmpty()) {
				appendNativeToList(findByPatientSync(sqlFld,aLastname), ret,null);
			}
			if(!StringUtil.isNullOrEmpty(aLastname) && ret.isEmpty()) {
				appendNativeToList(findByPolicy(sqlFld,aLpuId, aLpuAreaId, aLastname), ret,null);
			}
		}
		return ret;
		
	}

	private Query findByPolicy(StringBuilder aSqlFld, Long aLpuId, Long aLpuAreaId, String aPolicyQuery) {
		QueryClauseBuilder b = new QueryClauseBuilder() ;
		StringBuilder sql = new StringBuilder() ;
		sql.append(aSqlFld);
		sql.append(" from MedPolicy mp") ;
		sql.append(" left join Patient p on p.id=mp.patient_id") ;
		sql.append(" left join LpuAttachedByDepartment att on att.patient_id=p.id") ;
		sql.append(" left join Mislpu ml on ml.id=att.lpu_id") ;
		sql.append(" left join lpuarea ma on ma.id=att.area_id") ;
		sql.append(" left join VocAttachedType vat on vat.id=att.AttachedType_id") ;
		sql.append(" where") ;
		//b.add("MedPolicy.patient.lpu_id", aLpuId) ;
		//b.add("MedPolicy.patient.lpuArea_id", aLpuAreaId) ;
		StringTokenizer st = new StringTokenizer(aPolicyQuery, " ") ;
		String series = st.hasMoreTokens() ? st.nextToken() : null ;
		String number = st.hasMoreTokens() ? st.nextToken() : null ;
		if(number==null) {
			number = series ;
			series = null ;
		}
		b.add("mp.series", series);
		b.add("mp.polNumber", number);
		return b.buildNative(theManager, sql.toString(), "group by p.id, p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.patientSync, p.colorType order by p.lastname, p.firstname") ;//order by MedPolicy.patient.lastname, MedPolicy.patient.firstname");
		// from MedPolicy where series = :series and 
	}

	private Query findByMedCardNumber(StringBuilder aSqlFld,String aPolicyQuery) {
		QueryClauseBuilder b = new QueryClauseBuilder() ;
		StringBuilder sql = new StringBuilder() ;
		sql.append(aSqlFld);
		sql.append(" from Medcard m") ;
		sql.append(" left join Patient p on m.person_id = p.id") ;
		sql.append(" left join LpuAttachedByDepartment att on att.patient_id=p.id") ;
		sql.append(" left join Mislpu ml on ml.id=att.lpu_id") ;
		sql.append(" left join lpuarea ma on ma.id=att.area_id") ;
		sql.append(" left join VocAttachedType vat on vat.id=att.AttachedType_id") ;
		sql.append(" where") ;
		
		StringTokenizer st = new StringTokenizer(aPolicyQuery, " ") ;
		String number = st.hasMoreTokens() ? st.nextToken() : null ;
		b.add("m.number", number);
		return b.buildNative(theManager, sql.toString(), "group by p.id, p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.patientSync, p.colorType order by p.lastname, p.firstname") ;//order by MedPolicy.patient.lastname, MedPolicy.patient.firstname");
		// from MedPolicy where series = :series and 
	}
	private Query findByPatientSync(StringBuilder aSqlFld, String aPolicyQuery) {
		QueryClauseBuilder b = new QueryClauseBuilder() ;
		StringBuilder sql = new StringBuilder() ;
		sql.append(aSqlFld);
		sql.append(" from Patient p") ;
		sql.append(" left join LpuAttachedByDepartment att on att.patient_id=p.id") ;
		sql.append(" left join Mislpu ml on ml.id=att.lpu_id") ;
		sql.append(" left join lpuarea ma on ma.id=att.area_id") ;
		sql.append(" left join VocAttachedType vat on vat.id=att.AttachedType_id") ;
		sql.append(" where") ;
		//b.add("MedPolicy.patient.lpu_id", aLpuId) ;
		//b.add("MedPolicy.patient.lpuArea_id", aLpuAreaId) ;
		StringTokenizer st = new StringTokenizer(aPolicyQuery, " ") ;
		String number = st.hasMoreTokens() ? st.nextToken() : null ;
		b.add("patientSync", number);
		return b.buildNative(theManager, sql.toString(), "group by p.id, p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.patientSync, p.colorType order by p.lastname, p.firstname") ;//order by MedPolicy.patient.lastname, MedPolicy.patient.firstname");
		// from MedPolicy where series = :series and 
	}
	
	@SuppressWarnings("unchecked")
	private void appendNativeToList(Query aQuery, List<PatientForm> ret, String aAddInfo) {
		List<Object[]> list = aQuery.setMaxResults(50).getResultList();
		for (Object[] arr : list) {
			PatientForm f = new PatientForm();
			f.setId(((Number) arr[0]).longValue());
			if (arr.length>6 && arr[6]!=null) {
				f.setLastname(new StringBuilder().append("<font color='red'>").append(arr[1]).append("</font>").toString());
				f.setFirstname(new StringBuilder().append("<font color='red'>").append(arr[2]).append("</font>").toString());
				f.setMiddlename(new StringBuilder().append("<font color='red'>").append(arr[3]).append("</font>").toString());
				if(arr[4]!=null) {
					f.setBirthday(DateFormat.formatToDate((java.util.Date) arr[4])) ;
				}
				f.setPatientSync(arr[5]!=null?(String) arr[5]:"") ;
			} else if (aAddInfo!=null) {
				f.setLastname(new StringBuilder().append(aAddInfo).append("<font color='blue'>").append(arr[1]).append("</font>").toString());
				f.setFirstname(new StringBuilder().append("<font color='blue'>").append(arr[2]).append("</font>").toString());
				f.setMiddlename(new StringBuilder().append("<font color='blue'>").append(arr[3]).append("</font>").toString());
				if(arr[4]!=null) {
					f.setBirthday(DateFormat.formatToDate((java.util.Date) arr[4])) ;
				}
				f.setPatientSync(arr[5]!=null?(String) arr[5]:"") ;
			} else {
				f.setLastname((String) arr[1]);
				f.setFirstname((String) arr[2]);
				f.setMiddlename((String) arr[3]);
				if(arr[4]!=null) {
					f.setBirthday(DateFormat.formatToDate((java.util.Date) arr[4])) ;
				}
				f.setPatientSync(arr[5]!=null?(String) arr[5]:"") ;
			}
			if (arr.length>7 && arr[7]!=null) {f.setLpuName((String)arr[7]) ;}
			if (arr.length>8 && arr[8]!=null) {f.setLpuAreaName((String)arr[8]) ;}
			if (arr.length>9 && arr[9]!=null) {f.setPatientInfo((String)arr[9]) ;}
			ret.add(f);
		}

	}

	

	/**
	 * Обновление прикрепления
	 */
	@RolesAllowed("/Policy/Mis/Patient/Edit")
	public void updatePatientLpuByAddress(Patient aPatient) {
		// если не прикреплен по полису и все-таки есть адрес
		if (aPatient.getAttachedOmcPolicy() == null && aPatient.getAddress() != null) {
				// берем первую попавшуюся точку прикрепления
				LpuAreaAddressPoint p = findPoint(aPatient.getAddress(),
						aPatient.getHouseNumber(), aPatient.getHouseBuilding()
						, aPatient.getBirthday(), aPatient.getFlatNumber());
				if (CAN_DEBUG)	LOG.debug("point = " + p);
				
				// из этой точки последовательно выбираем ЛПУ, участок и адрес участка
				LpuAreaAddressText text = p != null ? p.getLpuAreaAddressText()	: null;
				LpuArea area = text != null ? text.getArea() : null;
				MisLpu lpu = area != null ? area.getLpu() : null;

				// и устанавливаем их
				aPatient.setLpu(lpu);
				aPatient.setLpuArea(area);
				aPatient.setLpuAreaAddressText(text);
		}
	}

	@RolesAllowed("/Policy/Mis/Patient/Edit")
	public String findPatientLpuInfo(Long aAddressId, String aNumber,
			String aBuilding, Date aDate, String aFlat) {
		if (aAddressId == null || aAddressId == 0)
			throw new IllegalArgumentException("Нет адреса у пациента");
		Address address = theManager.find(Address.class, aAddressId);
		LpuAreaAddressPoint point = findPoint(address, aNumber, aBuilding, aDate, aFlat);

		if (point == null) {
			throw new IllegalStateException("Нет прикрепленного ЛПУ");
		} else {
			try {
				theLpuAreaDynamicSecurity.check("Edit", point
						.getLpuAreaAddressText().getArea().getId(),
						new InterceptorContext(theManager, theSessionContext));
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException(e);
			}
			StringBuilder sb = new StringBuilder();
			sb.append(point.getLpuAreaAddressText().getAddressText());
			sb.append(" ");
			sb.append(point.getLpuAreaAddressText().getArea().getName());
			sb.append(" ");
			sb.append(point.getLpuAreaAddressText().getArea().getLpu()
					.getName());
			return sb.toString();
		}
	}

	/**
	 * Ищем первую попавшуюся точку прикрепления.
	 * Теперь и в зависимости от возраста 
	 */
	public LpuAreaAddressPoint findPoint(Address aAddress, String aNumber,
			String aBuilding, Date aBirthday, String aFlat) {
		if (CAN_DEBUG) {
			LOG.debug("findPoint() aAddress = " + aAddress.getId()
					+ ", aNumber=" + aNumber + ", aBuilding = " + aBuilding);
		}
		// ребенок, если < 18 и есть дата рождения
		boolean isChild = aBirthday!=null
		    ? theIsChildUtil.isChild(aBirthday, new java.util.Date())
		    : false	;
		EntityManager manager = theManager; // theFactory.createEntityManager();
		StringBuilder sb = new StringBuilder();
		sb.append("from LpuAreaAddressPoint where address = :address ");
		if(isChild) {
			sb.append(" and lpuAreaAddressText.area.type.code = '2'") ; // для педиатрического
		} else {
			sb.append(" and lpuAreaAddressText.area.type.code <> '2'") ; // для терапевтического
		}
		sb.append(" and houseNumber ").append(
				StringUtil.isNullOrEmpty(aNumber) ? " is null "
						: " = :number ");
		sb.append(" and houseBuilding ").append(
				StringUtil.isNullOrEmpty(aBuilding) ? " is null "
						: " = :building ");
		sb.append(" and flat ").append(
				StringUtil.isNullOrEmpty(aFlat) ? " is null "
						: " = :flat ");

		if (CAN_DEBUG) LOG.debug("query = "+sb);

		Query query = manager.createQuery(sb.toString());
		if (!StringUtil.isNullOrEmpty(aNumber))
			query.setParameter("number", aNumber);
		if (!StringUtil.isNullOrEmpty(aBuilding))
			query.setParameter("building", aBuilding);
		if (!StringUtil.isNullOrEmpty(aFlat))
			query.setParameter("flat", aFlat);
		query.setParameter("address", aAddress);

		LpuAreaAddressPoint point = (LpuAreaAddressPoint) getFirst(query);
		
		// дом корпус кв
		if (point == null && !StringUtil.isNullOrEmpty(aNumber)	&& !StringUtil.isNullOrEmpty(aBuilding)
				&& !StringUtil.isNullOrEmpty(aFlat) ) {
			point = findPoint(aAddress, aNumber, aBuilding, aBirthday, null);
		// дом кв
		} else if(point == null && !StringUtil.isNullOrEmpty(aNumber)	&& StringUtil.isNullOrEmpty(aBuilding)
				&& !StringUtil.isNullOrEmpty(aFlat) ) {
			point = findPoint(aAddress, aNumber, null, aBirthday, null);
		// дом копус	
		} else if (point == null && !StringUtil.isNullOrEmpty(aNumber)	&& !StringUtil.isNullOrEmpty(aBuilding)) {
			point = findPoint(aAddress, aNumber, null, aBirthday, null);
		// дом	
		} else if (point == null && !StringUtil.isNullOrEmpty(aNumber)) {
			point = findPoint(aAddress, null, null, aBirthday, null);
		}

		return point;
	}
	
	public String getDoubleByBaseData(String aId, String aLastname, String aFirstname, String aMiddlename,
			String aSnils, String aBirthday, String aPassportNumber, String aPassportSeries) throws ParseException {
		return getDoubleByBaseData(aId, aLastname, aFirstname, aMiddlename, aSnils, aBirthday, aPassportNumber, aPassportSeries, "entityView-mis_patient.do") ;
	}
	
	public String addPatient(String aLastname, String aFirstname, String aMiddlename,
			String aBirthday, Long aSex, Long aSocialStatus, String aSnils){
		VocSocialStatus statusSocial = theManager.find(VocSocialStatus.class, aSocialStatus) ;
		VocSex sex = theManager.find(VocSex.class, aSex) ;
		Date birthday;
		Patient pat = new Patient() ;
		String date ="" ;
		try {
			birthday = DateFormat.parseSqlDate(aBirthday);
			pat.setBirthday(birthday) ;
			SimpleDateFormat dat = new SimpleDateFormat("dd.MM.yyyy") ;
			date = dat.format(birthday) ;
		} catch (ParseException e) {
			new ParseException("Неправильно введена дата рождения пациента", 0) ;
		}
		pat.setLastname(aLastname.toUpperCase()) ;
		pat.setMiddlename(aMiddlename.toUpperCase()) ;
		pat.setFirstname(aFirstname.toUpperCase()) ;
		pat.setSex(sex) ;
		pat.setSocialStatus(statusSocial) ;
		pat.setSnils(aSnils) ;
		theManager.persist(pat) ;
		return new StringBuilder().append(pat.getId()).append("#").append(pat.getLastname())
			.append(" ").append(pat.getFirstname()).append(" ").append(pat.getMiddlename())
			.append(" ").append(date).toString() ;
		
	}
	public String getDoubleByBaseData(String aId, String aLastname, String aFirstname, String aMiddlename,
			String aSnils, String aBirthday, String aPassportNumber, String aPassportSeries, String aAction) throws ParseException {
		return getDoubleByBaseData(aId, aLastname, aFirstname, aMiddlename, aSnils, aBirthday, aPassportNumber, aPassportSeries, aAction, false) ;
	}
	public String getDoubleByBaseData(String aId, String aLastname, String aFirstname, String aMiddlename,
			String aSnils, String aBirthday, String aPassportNumber, String aPassportSeries, String aAction,boolean aIsFullBirthdayCheck) throws ParseException {
		StringBuilder sql = new StringBuilder() ;
		aFirstname = aFirstname.toUpperCase().trim() ;
		aMiddlename = aMiddlename.toUpperCase().trim() ;
		aLastname = aLastname.toUpperCase().trim() ;
		sql.append("")
			.append(" from Patient p")
			.append(" where (")
			.append(" (p.lastname =:lastname and p.firstname = :firstname and p.middlename=:middlename and ");
		String birthyear ;
		if (!aIsFullBirthdayCheck) {
			birthyear= aBirthday.substring(6) ;
			System.out.println("birthyear="+birthyear) ;
			sql.append("to_char(p.birthday,'yyyy')=:birthyear)") ;
		} else {
			birthyear= aBirthday ;
			System.out.println("birthyear="+birthyear) ;
			sql.append("p.birthday=to_date(:birthyear,'dd.mm.yyyy'))") ;
		}
		if (aSnils!=null && !aSnils.equals("") && !aSnils.equals("999-999-999 99")) {
			sql.append(" or (p.snils='").append(aSnils).append("')") ;
		}
		if (aPassportNumber!=null && !aPassportNumber.equals("") && aPassportSeries!=null && !aPassportSeries.equals("") ) {
			sql.append(" or (p.passportNumber='").append(aPassportNumber).append("' and p.passportSeries='").append(aPassportSeries).append("')") ;
		}
		
		sql.append(")") ;
			
		if (aId!=null && !aId.equals("") && !aId.equals("0")) {
			sql.append(" and p.id!='").append(aId).append("'") ;
		}
		List<Object[]> doubles = theManager.createNativeQuery(
				"select p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.snils "+sql.toString())
				.setParameter("lastname", aLastname)
				.setParameter("firstname", aFirstname)
				.setParameter("middlename", aMiddlename)
				.setParameter("birthyear", birthyear)
	//			.setParameter("snils", aSnils)
	//			.setParameter("pnumber", aPassportNumber)
	//			.setParameter("pseries", aPassportSeries)
				.setMaxResults(20)
				.getResultList() ;
		
		if (doubles.size()>0) {
			StringBuilder ret = new StringBuilder() ;
			ret.append("<br/><ol>") ;
			Object cntdoubles = theManager.createNativeQuery(
			"select count(*) "+sql.toString())
			.setParameter("lastname", aLastname)
			.setParameter("firstname", aFirstname)
			.setParameter("middlename", aMiddlename)
			.setParameter("birthyear", birthyear)
			//			.setParameter("snils", aSnils)
			//			.setParameter("pnumber", aPassportNumber)
			//			.setParameter("pseries", aPassportSeries)
			.getSingleResult() ;
			ret.append("<li>Количество найденных дублей: <b>").append(cntdoubles).append("</b></li>") ;
			for (Object[] res:doubles) {
				ret.append("<li>")
				.append("<a href='") ;
				if (aAction.toLowerCase().indexOf("javascript")!=-1) {
					ret.append(aAction).append("(\"").append(res[0]).append("\",\"").append(res[1]).append(" ").append(res[2]).append(" ").append(res[3])
						.append(" ").append(res[4]).append("\")'") ;
				} else {
					ret.append(aAction) ;
					if (aAction.toLowerCase().indexOf(".do")==-1) {
						ret.append(".do") ;
					}
					if (aAction.indexOf("?")>0) {
						ret.append("&") ;
					} else {
						ret.append("?") ;
					}
					ret.append("id=").append(res[0]).append("'");
				}
				ret.append(res[0]).append("'>")
				.append(res[1]).append(" ").append(res[2]).append(" ").append(res[3])
				.append(" г.р.").append(res[4]).append(" СНИЛС: ").append(res[5]).append(" ")
				.append("</a>")
				.append("</li>") ;
			}
			ret.append("</ol><br/>") ;
			return ret.toString() ;
		} else {
			return null ;
		}
	}
	private String getTypePolicy(String pol) {
		String type="" ;
		if (pol!=null) {
			if ((pol.startsWith("0")|| pol.startsWith("1"))&&  pol.length()<4) {
				type = "2" ;
			} else if ((pol.startsWith("0") || pol.startsWith("1")) && pol.length()>3) {
				type = "3" ;
			} else {
				type ="1" ;
			}
		}
		return type ;

	}

	private static Object getFirst(Query aQuery) {
		List list = aQuery.setMaxResults(1).getResultList();
		return list != null && !list.isEmpty() ? list.iterator().next() : null;
	}

	private @EJB ILocalEntityFormService theEntityFormService;

	private @PersistenceContext EntityManager theManager;

	private final LpuAreaDynamicSecurity theLpuAreaDynamicSecurity = new LpuAreaDynamicSecurity();

	private @Resource SessionContext theSessionContext;
	private final IsChild theIsChildUtil = new IsChild() ;  

}
