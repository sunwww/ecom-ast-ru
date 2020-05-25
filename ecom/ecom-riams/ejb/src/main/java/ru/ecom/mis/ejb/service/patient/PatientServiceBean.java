package ru.ecom.mis.ejb.service.patient;

import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;
import org.jdom.Element;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.ejb.xml.XmlUtil;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKodTer;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.jaas.ejb.domain.SoftConfig;
import ru.ecom.jaas.ejb.service.SoftConfigServiceBean;
import ru.ecom.mis.ejb.domain.contract.NaturalPerson;
import ru.ecom.mis.ejb.domain.licence.ExternalDocument;
import ru.ecom.mis.ejb.domain.licence.TemplateExternalDocument;
import ru.ecom.mis.ejb.domain.licence.voc.VocExternalDocumentType;
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressPoint;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.*;
import ru.ecom.mis.ejb.domain.patient.voc.*;
import ru.ecom.mis.ejb.form.lpu.interceptors.LpuAreaDynamicSecurity;
import ru.ecom.mis.ejb.form.patient.MedPolicyOmcForm;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.form.patient.VocOrgForm;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.date.AgeUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Сервис пациента
 */
@Stateless
@Remote(IPatientService.class)
@Local(IPatientService.class)
@SecurityDomain("other")
public class PatientServiceBean implements IPatientService {

	private static final Logger LOG = Logger.getLogger(PatientServiceBean.class);

	/**Выгружаем список карт Д наблюдения */
	public String exportDispensaryCard(java.util.Date aDateFrom, java.util.Date aDateTo, java.util.Date aDateChanged, String aPacketNumber)  {
		JSONObject ret = new JSONObject();
		try {
			StringBuilder sqlWhere = new StringBuilder();
			if (aDateFrom!=null) {
				SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
				sqlWhere.append(" where coalesce(d.finishDate, d.startDate) ");
				if (aDateTo!=null) {
					sqlWhere.append(" between '").append(yyyyMMdd.format(aDateFrom)).append("' and '").append(yyyyMMdd.format(aDateTo)).append("'");
				} else{
					sqlWhere.append(">='").append(yyyyMMdd.format(aDateFrom)).append("'");
				}
				if (aDateChanged!=null) {
				    sqlWhere.append(" and coalesce(d.editDate, d.createDate)>='").append(yyyyMMdd.format(aDateChanged)).append("'");
                }
			}
			while(aPacketNumber.length()<4) {aPacketNumber="0"+aPacketNumber;}
			SimpleDateFormat yyddmm = new SimpleDateFormat("yyMMdd");
			String defaultLpuCode = SoftConfigServiceBean.getDefaultParameterByConfig("DEFAULT_LPU_OMCCODE","123456",theManager);
			String filename ="DNM"+defaultLpuCode+"T30_"+yyddmm.format(aDateTo)+"_"+aPacketNumber;
			String[] flds = {"N_ZAP", "FAM",  "IM", "OT", "DR", "TEL", "IDCASE", "PROFIL", "DS", "D_BEG", "D_END", "END_RES","YEAR"};
			String sql = "select pat.id as N_ZAP, pat.lastname as FAM ,pat.firstname AS IM ,pat.middlename AS OT ,to_char(pat.birthday,'yyyy-MM-dd') as DR  ,coalesce(pat.phone,'') as TEL ,d.id as IDCASE" +
					" ,coalesce(profile.profilek,'') as PROFIL ,coalesce(mkb.code,'') as DS ,coalesce(to_char(d.startdate,'yyyy-MM-dd'),'') AS D_BEG ,coalesce(to_char(d.finishdate,'yyyy-MM-dd'),'') AS D_END" +
					" ,coalesce(vde.code,'') AS END_RES ,to_char(coalesce(d.finishDate, d.startDate),'yyyy') as YEAR" +
					" from dispensarycard d" +
					" left join vocdispensaryend vde on vde.id=d.endreason_id" +
					" left join workfunction wf on wf.id=d.workfunction_id" +
					" left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
					" left join voce2medhelpprofile profile on profile.id=vwf.medhelpprofile_id" +
					" left join patient pat on pat.id=d.patient_id" +
					" left join vocidc10 mkb on mkb.id=d.diagnosis_id" +
					sqlWhere.toString() + " order by pat.id";
			JSONArray arr = new JSONArray(theWebQueryService.executeNativeSqlGetJSON(flds, sql,null));
			EjbEcomConfig config = EjbEcomConfig.getInstance() ;
			String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
			Element dn = new Element("DN");
			dn.addContent(new Element("FNAME").setText(filename));
			filename+=".xml";
			if (arr.length()==0) {ret.put("status","error").put("errorCode","Записей не найдено"); return ret.toString();}
			Element zap= null;
			String lastPatId = null;
			for (int i=0;i<arr.length();i++) {
				JSONObject pat = arr.getJSONObject(i);
				String patId =pat.getString("N_ZAP");

				if (lastPatId!=null && lastPatId.equals(patId)) { //Создаем несколько карт Д учета одному пациенту.
					LOG.info("У пациента несколько Д карт, попробуем стримы");
					//List<Element> list =dn.getChildren("ZAP");
				//	zap = new Element("ZAP"); //list.stream().filter(z->z.getChildText("N_ZAP").equals(patId)).findFirst().get();
					LOG.info("Hello, i found element by stream!!!");
				} else {
				//	patients.add(patId);
					zap = new Element("ZAP");
					zap.addContent(new Element("N_ZAP").setText(patId));
					zap.addContent(new Element("YEAR").setText(pat.getString("YEAR")));
					zap.addContent(new Element("FAM").setText(pat.getString("FAM")));
					zap.addContent(new Element("IM").setText(pat.getString("IM")));
					zap.addContent(new Element("OT").setText(pat.getString("OT")));
					zap.addContent(new Element("DR").setText(pat.getString("DR")));
					zap.addContent(new Element("TEL").setText(pat.getString("TEL")));
					dn.addContent(zap);
					lastPatId=patId;
				}
				Element zapDn = new Element("DN");
				zapDn.addContent(new Element("IDCASE").setText(pat.getString("IDCASE")));
				zapDn.addContent(new Element("PROFIL").setText(pat.getString("PROFIL")));
				zapDn.addContent(new Element("DS").setText(pat.getString("DS")));
				zapDn.addContent(new Element("D_BEG").setText(pat.getString("D_BEG")));
				zapDn.addContent(new Element("D_END").setText(pat.getString("D_END")));
				zapDn.addContent(new Element("END_RES").setText(pat.getString("END_RES")));
				zap.addContent(zapDn);
			}
			XmlUtil.createXmlFile(dn,workDir+"/"+filename);
			ret.put("status","ok").put("filename",filename);
		} catch (JSONException e) {
			ret.put("status","error").put("errorCode",e.toString());
			LOG.error(e);
		}
		return ret.toString();
	}
	public void changeMedPolicyType (Long aPolicyId, Long aNewPolicyTypeId) {
		theManager.createNativeQuery("update medpolicy set dtype=(select vmp.code from vocmedpolicy vmp" +
				" where vmp.id="+aNewPolicyTypeId+") where id="+aPolicyId).executeUpdate();
	}

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

				if (aLpuAttached!=null&&aAttachedDate!=null&&aAttachedType!=null) {
					String aSql = "select " +
							" case when (select max(sc.keyvalue) from softconfig sc where sc.key='DEFAULT_LPU_OMCCODE')!='"+aLpuAttached+"' then 1" +
							" else (select count(att.id) from lpuattachedbydepartment att where (att.patient_id="+aPatientId+
							" and att.attachedtype_id=(select id from vocattachedtype where code='"+aAttachedType+"')" +
							" and att.dateFrom=to_date('"+aAttachedDate+"','dd.MM.yyyy')and att.dateTo is null" +
							" and (select max(sc.keyvalue) from softconfig sc where sc.key='DEFAULT_LPU_OMCCODE')='"+aLpuAttached+"')) end";
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

			return isDifference;
	}

	private boolean needChangeValue(Object aOldValue, Object aNewValue) {
		if (toStr(aOldValue)==null && toStr(aNewValue)!=null) {return true;} //Старое значение пустое, новое - нет, обновляем.
		if (toStr(aNewValue)==null) {return false;} //На пустое значение не обновляем.
		return !toStr(aOldValue).equals(toStr(aNewValue));

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
		return config.get("tomcat.image.dir", "/opt/tomcat/webapps/docmis/");

	}
	public float getImageCompress() {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String comp = config.get("tomcat.image.compress", "0.5") ;
		return Float.valueOf(comp) ;
	}
	public void insertExternalDocumentByObject(String aObject,Long aObjectId, Long aType,String aReferenceComp,String aReferenceTo, String aComment,String aUsername) {
		ExternalDocument doc;
		if (aObject.equals("Template")) {
			doc = new TemplateExternalDocument() ;
		} else {
			doc = new ExternalDocument() ;
		}
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
		} else if (aObject.equals("Template")) {

		} else {
			throw new IllegalStateException("Не определен object типа:"+aObject);
		}
		if (aType!=null&&aType>0) {
			VocExternalDocumentType type = theManager.find(VocExternalDocumentType.class, aType) ;
			doc.setType(type) ;
		}
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
		String sql = "select count(*) from ContractPerson where dtype='NaturalPerson' and patient_id='" +
				aPatient + "'";
		Object ret = theManager.createNativeQuery(sql).getSingleResult() ;
		return ConvertSql.parseLong(ret)>0L;


	}
	public void createNaturalPerson(Long aPatient) {
		 Patient pat = theManager.find(Patient.class, aPatient) ;
		 NaturalPerson np = new NaturalPerson();
		 np.setPatient(pat) ;
		 theManager.persist(np);
		 //return np.getId();
	}
	private String getAddressByKladr(String aKladr,String aRegion,String aRayon, String aCity, String aStreet) {
		return getAddressByKladr(aKladr, aRegion, aRayon, aCity, aStreet,null);
	}
	private String checkStreet (String aStreet) { //Обрабатываем случаи несоответствия данных адреса ФОМС адресам КЛАДР
		aStreet = aStreet.toUpperCase();
		String [][] streetList = {
				{"Н.ОСТРОВСКОГО ул","Николая Островского ул"}
				,{"В.Комарова ул","Космонавта В.Комарова ул"}
				,{"М.ЛУКОНИНА ул","Михаила Луконина ул"}
				};
			for (String[] strings : streetList) {
				if (strings[0].equalsIgnoreCase(aStreet)) {
					aStreet = strings[1].toUpperCase();
					break;
				}
			}

		return aStreet;
	}
	public String getAddressByOkato (String aOkato, String aStreet) {
		if (StringUtil.isNullOrEmpty(aOkato) || StringUtil.isNullOrEmpty(aStreet)) return null;
		String streetType = "";
		if (aOkato.length()<11) {
			aOkato +="0000000000";
			aOkato = aOkato.substring(0,11);
		}
		aStreet = checkStreet(aStreet.trim());
		if (aStreet.endsWith(" УЛ") || aStreet.endsWith(" ПЛ")) {
			streetType = aStreet.substring(aStreet.length()-2);
			aStreet = aStreet.substring(0,aStreet.length()-2);
		} else if (aStreet.endsWith(" ПЕР") || aStreet.endsWith(" НАБ")) {
			streetType = aStreet.substring(aStreet.length()-3);
			aStreet = aStreet.substring(0,aStreet.length()-3);
		} else if (aStreet.endsWith(" ПРОЕЗД")) {
			streetType = aStreet.substring(aStreet.length()-6);
			aStreet = aStreet.substring(0,aStreet.length()-6);
		} else if (aStreet.endsWith(" ПР")) {
			streetType = "ПРОЕЗД";
			aStreet = aStreet.substring(0,aStreet.length()-2);
		} else if (aStreet.toUpperCase().endsWith(" Ш")) {
			streetType = aStreet.substring(aStreet.length()-1);
			aStreet = aStreet.substring(0,aStreet.length()-1);
		}
		aStreet=aStreet.trim() ;
		streetType = streetType.toUpperCase().trim();
		StringBuilder sql = new StringBuilder();
		sql.append("select a.addressid from kladr k left join address2 a on a.kladr = k.kladrcode" + " left join addresstype atype on atype.id=a.type_id" + " where k.okatd='").append(aOkato).append("' and upper(a.name) = upper('").append(aStreet).append("')");
		if (!streetType.equals("")) {
			sql.append(" and upper(atype.shortname)=upper('").append(streetType).append("')");
		}
		List<Object> listO = theManager.createNativeQuery(sql.toString()).setMaxResults(10).getResultList() ;
		if (!listO.isEmpty()) {
			if (listO.size()>1) {
				LOG.warn("=== 2.5 Найдено несколько подходящих адресов, возвращаем null "+listO.size());
				return null;
			}
			return listO.get(0).toString();
		}
		return null;
	}
	private String getAddressByKladr(String aKladr,String aRegion,String aRayon, String aCity, String aStreet, String aOkato) {
		StringBuilder sql = new StringBuilder() ;
		if (aCity==null) aCity = "";
		if (aCity.contains("АСТРАХАН")) aCity="АСТРАХАНЬ" ;
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
		else if (aCity.endsWith(" РП")) aCity = aCity.substring(0,aCity.length()-2);
		else if (aCity.startsWith("С.")||aCity.startsWith("С ")) aCity = aCity.substring(2) ;
		else if (aCity.startsWith("Г.")||aCity.startsWith("Г ")) aCity = aCity.substring(2) ;
		else if (aCity.startsWith("П.")||aCity.startsWith("П ")) aCity = aCity.substring(2) ;
		else if (aCity.startsWith("ГОР.")) aCity = aCity.substring(4) ;
		else if (aCity.startsWith("ПОС.")) aCity = aCity.substring(4) ;
		else if (aCity.startsWith("ГОР ")) aCity = aCity.substring(4) ;
		else if (aCity.startsWith("ПОС ")) aCity = aCity.substring(4) ;

		aCity=aCity.trim().toUpperCase().replaceAll("-", "").replaceAll(" ", "").replaceAll("№", "N") ;

		if (!StringUtil.isNullOrEmpty(aOkato)) {
			String s = getAddressByOkato(aOkato, aStreet);
			if (!StringUtil.isNullOrEmpty(s)) {
				return s;
			}
		}

		sql.append("select addressid,kladr from Address2 where kladr='").append(aKladr).append("'" ) ;
		StringBuilder res = new StringBuilder() ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		if (!list.isEmpty()) {
			res.append(list.get(0)[0]) ;
		} else {
			if (aStreet.startsWith("?")) return res.toString() ;
			if (!StringUtil.isNullOrEmpty(aKladr)) {
				String lastKl = aKladr.equals("") ? aRegion : aKladr.substring(aKladr.length()-1) ;

				while ("0".equals(lastKl)) {
					aKladr = aKladr.substring(0,aKladr.length()-1) ;
					lastKl = aKladr.length()>1 ? aKladr.substring(aKladr.length()-1) : null ;
				}
				sql = new StringBuilder() ;
				sql.append("select addressid,kladr from Address2 where kladr like '").append(aKladr).append("%' and UPPER(name)='").append(aCity).append("'" ) ;
				list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
				if (!list.isEmpty()) {
					aKladr = ""+list.get(0)[1] ;
					lastKl = aKladr.substring(aKladr.length()-1) ;
					while (lastKl!=null&&lastKl.equals("0")) {
						aKladr = aKladr.substring(0,aKladr.length()-1) ;
						lastKl = aKladr.length()>1?aKladr.substring(aKladr.length()-1):null ;
					}
					sql = new StringBuilder() ;
					sql.append("select addressid,kladr from Address2 where kladr like '").append(aKladr).append("%' and replace(replace(replace(UPPER(name),'-',''),' ',''),'№','N')='").append(aStreet).append("'" ) ;
					list.clear() ;
					list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
					if (!list.isEmpty()) {
						res.append(list.get(0)[0]) ;
					} else {
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
		if (StringUtil.isNullOrEmpty(aRayon)) {return null;}
		StringBuilder sql = new StringBuilder() ;
		StringBuilder res = new StringBuilder() ;
		sql.append("select id, kladr from VocRayon where code='").append(aRayon).append("'" ) ;
		List<Object[]> list ;
		list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		String kladr=aRegion;
		if (!list.isEmpty()) {
			kladr = ""+(list.get(0)[1]!=null ? list.get(0)[1] : aRegion) ;
		}
		sql = new StringBuilder() ;
		sql.append("select addressid,kladr from Address2 where kladr like '").append(kladr).append("%' and domen<6 and replace(replace(replace(UPPER(name),'-',''),' ',''),'№','N')='").append(aCity).append("'" ) ;
		list.clear() ;
		list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;

		if (!list.isEmpty()) {
			String kladrId=kladr = ""+list.get(0)[0] ;
			kladr = ""+list.get(0)[1] ;
			String lastKl = kladr.substring(kladr.length()-1) ;
			while (lastKl!=null&&lastKl.equals("0")) {
				kladr = kladr.substring(0,kladr.length()-1) ;
				lastKl = kladr.length()>1 ? kladr.substring(kladr.length()-1) : null ;
			}
			sql = new StringBuilder() ;
			sql.append("select addressid,kladr from Address2 where kladr like '").append(kladr).append("%' and replace(replace(replace(UPPER(name),'-',''),' ',''),'№','N')='").append(aStreet).append("'" ) ;
			list.clear() ;
			list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			if (!list.isEmpty()) {
				res.append(list.get(0)[0]) ;
			} else {
				res.append(kladrId) ;
			}
		}
		return res.toString() ;
	}

	/*Возвращаем json, а не херню*/
	public String getInfoVocForFond(String aPassportType,String aAddress, String aPolicy) {
		StringBuilder sql = new StringBuilder() ;
		List<Object[]> list = null;
		JSONObject ret = new JSONObject();
		if (!StringUtil.isNullOrEmpty(aPassportType)) {
			sql.append("select id,name from VocIdentityCard where omcCode='").append(aPassportType).append("'" ) ;
			list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		}
		if (list!=null && !list.isEmpty()) {
			ret.put("passportType",list.get(0)[0]);
			ret.put("passportName",list.get(0)[1]);
		}
		if (!StringUtil.isNullOrEmpty(aAddress)) {
			String[] adr = aAddress.split("#") ;
			String kladr = adr[0] ;
			String rayon = adr[1].toUpperCase() ;
			String sity = adr[2].toUpperCase() ;
			String street = adr[3].toUpperCase() ;
			String region = adr[4].toUpperCase() ;
			String okato = adr.length>5 ? adr[5] : "";
			ret.put("address",getAddressByKladr(kladr,region,rayon, sity, street, okato));
			sql = new StringBuilder() ;

			sql.append("select id,code||' '||name from VocRayon where code='").append(rayon).append("' or upper(name) like '%").append(rayon).append("%'" ) ;
			list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			if (!list.isEmpty()) {
				ret.put("rayonId",list.get(0)[0]);
				ret.put("rayonName",list.get(0)[1]);
			}
		}
		if (aPolicy!=null &&!aPolicy.equals("")) {
			String[] pol = aPolicy.split("#") ;
			sql = new StringBuilder() ;
			sql.append("select id,omcCode||' '||name from REG_IC where omcCode='").append(pol[0]).append("'" ) ;
			list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			if (!list.isEmpty()) {
				ret.put("companyId",list.get(0)[0]);
				ret.put("companyName",list.get(0)[1]);
			}
			String type=getTypePolicy(pol[1]) ;
			sql = new StringBuilder() ;
			sql.append("select id,id||' '||name from VocMedPolicyOmc where code='").append(type).append("'" ) ;
			list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			if (!list.isEmpty()) {
				ret.put("policyId",list.get(0)[0]);
				ret.put("policyName",list.get(0)[1]);
			}
		}
		return ret.toString() ;
	}
	private String toStr(Object o) {
		return o==null || o.toString().trim().equals("") ? null : o.toString();
	}

	public boolean updateDataByFondAutomatic (Long aPatientFondId, Long aCheckId
			,boolean needUpdatePatient, boolean needUpdateDocuments, boolean needUpdatePolicy, boolean needUpdateAttachment) {
		try{
		if ( aPatientFondId!=null && (needUpdateAttachment || needUpdateDocuments
				|| needUpdatePatient || needUpdatePolicy)) {
			SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd.MM.yyyy");
			PatientFond patF = theManager.find(PatientFond.class, aPatientFondId);
			String rz = patF.getCommonNumber()

					,aPolicyNumber=patF.getPolicyNumber(),aCompany=patF.getCompanyCode() , doctorSnils = patF.getDoctorSnils();
			Long patientId =patF.getPatient();
			if (patientId == null) return false;
			Patient patient = theManager.find(Patient.class,patientId);

			if (needUpdatePatient) {
				String snils = patF.getSnils();
				if (!StringUtil.isNullOrEmpty(snils)) {
					patient.setSnils(snils);
				}
				patient.setCommonNumber(rz);
				String aStreet = patF.getStreet(), aOkato = patF.getOkato();
				String address = getAddressByOkato(aOkato, aStreet);
				if (address != null) {
					patient.setAddress(theManager.find(Address.class, Long.parseLong(address)));
					patient.setHouseBuilding(patF.getHouseBuilding());
					patient.setFlatNumber(patF.getFlat());
					patient.setHouseNumber(patF.getHouse());
				}
			}
			patF.setIsPatientUpdate(needUpdatePatient);

			if (needUpdateDocuments) {
				String docType = patF.getDocumentType(),docNumber = patF.getDocumentNumber();
				if (!StringUtil.isNullOrEmpty(docType) && !StringUtil.isNullOrEmpty(docNumber)) {
					patient.setPassportNumber(docNumber);
					patient.setPassportSeries(patF.getDocumentSeries());
					patient.setPassportWhomIssued(patF.getDocumentWhomIssued());
					patient.setPassportDateIssued(DateFormat.parseSqlDate(patF.getDocumentDateIssued()));
					List<VocIdentityCard> cards = theManager.createNamedQuery("VocIdentityCard.findByOmcCode")
							.setParameter("code", docType).getResultList();
					patient.setPassportType(cards.isEmpty() ? null : cards.get(0));
					patF.setIsDocumentUpdate(true);
				} else {
					patF.setIsDocumentUpdate(false);
				}
			} else {
				patF.setIsDocumentUpdate(false);
			}

			if (needUpdatePolicy) {
				patF.setIsPolicyUpdate(updateOrCreatePolicyByFond(patientId, rz, patF.getLastname(), patF.getFirstname(), patF.getMiddlename()
						, DateFormat.formatToDate(patF.getBirthday()), aCompany, patF.getPolicySeries()
						, patF.getPolicyNumber(), DateFormat.formatToDate(patF.getPolicyDateFrom()), DateFormat.formatToDate(patF.getPolicyDateTo())
						, DateFormat.formatToDate(patF.getCheckDate())));


			} else {patF.setIsPolicyUpdate(false);}
			if (needUpdateAttachment) {
				String s = updateOrCreateAttachment(patientId, aCompany, patF.getLpuAttached(), patF.getAttachedType()
						, DateFormat.formatToDate(patF.getAttachedDate())
						, doctorSnils,true, false);
				patF.setIsAttachmentUpdate((s!=null && s.length()>0));

			} else {patF.setIsAttachmentUpdate(false);}
			theManager.persist(patF);

			if (patF.getIsPatientUpdate() && patF.getIsDocumentUpdate() && patF.getIsPolicyUpdate() && patF.getIsAttachmentUpdate()) {
				patF.setIsDifference(false);
			} else {
				patF.setIsDifference(needUpdatePatient(patientId, aPatientFondId));
			}
			theManager.persist(patF);
			return true;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		return false;
	}
	public String updateOrCreateAttachment(Long aPatientId, String aCompany, String aLpu, String aAttachedType, String aAttachedDate, String aDoctorSnils
			, boolean ignoreType, boolean updateEditDate) {
		if (aCompany==null || aCompany.equals("") || aLpu==null || aLpu.equals("")) {
			LOG.warn("company or lpu is null: >"+aCompany+" < >"+aLpu+"<");
			return null;
		}
		String updateDate = " editdate=current_date, ";
		SoftConfig sc = (SoftConfig) theManager.createQuery("from SoftConfig sc where sc.key='DEFAULT_LPU_OMCCODE'").getResultList().get(0);
		//String lpu = fiodr[7], attachedType=fiodr[8], attachedDate = fiodr[9];
		StringBuilder ret = new StringBuilder();
		String lpu = aLpu, attachedType=aAttachedType, attachedDate = aAttachedDate;
		RegInsuranceCompany insCompany =null;
		String sqlAdd = "smoCode"; //smoCode - федеральный 5 значный код
		if (aCompany.length()<5) { //В некоторых случаях мы получаем местный код (7, 15) в этом случае, и искать мы будем по местному коду
			sqlAdd="omcCode";
		}
		String sqll = "from RegInsuranceCompany where "+sqlAdd+" = :code and (deprecated is null or deprecated='0')";
		List<RegInsuranceCompany> companies =(List<RegInsuranceCompany>) theManager.createQuery(sqll)
				.setParameter("code", aCompany).getResultList();

		if (!companies.isEmpty()) {
			insCompany=companies.get(0);
		} else {
			LOG.error("Страх. компания не найдена");
		}

	if (sc!=null && sc.getKeyValue().equals(lpu) && insCompany!=null) { //Создаем прикрепления только своей ЛПУ
		//s(" ЛПУ наше, создаем прикрепления!!");
		List<Object> obj ;
		Long areaId = null;
		LpuArea la = null;
		if (aDoctorSnils!=null && !aDoctorSnils.trim().equals("")){ // ищем участок по СНИЛС врача
			obj = theManager.createNativeQuery("select la.id " +
					" from lpuarea la" +
					" left join workfunction wf on wf.id=la.workfunction_id" +
					" left join worker w on w.id=wf.worker_id" +
					" left join patient wpat on wpat.id=w.person_id" +
					" where wpat.snils='"+aDoctorSnils.trim()+"'").getResultList();
			if (obj!=null&&obj.size()==1) { //Не найшли участок или нашли больше 1 участка - не проставляем участок!
				areaId=Long.parseLong(obj.get(0).toString());
			} else {
				LOG.error("НЕ Нашли участок по СНИЛС врача");
			}
		}
		if ("1".equals(attachedType)){
			obj=null;
			try {
				obj = theManager.createNativeQuery("select la.id from patient p" +
					" left join lpuareaaddresspoint laap on laap.address_addressid=p.address_addressid" +
					" left join lpuareaaddresstext laat on laat.id=laap.lpuareaaddresstext_id" +
					" left join lpuarea la on la.id=laat.area_id" +
					" left join vocareatype vat on vat.id=la.type_id" +
					" where p.id=" +aPatientId +
					" and (laap.housenumber is null or laap.housenumber='' or laap.housenumber=p.housenumber )" +
					" and (((p.housebuilding is null or p.housebuilding='') and (laap.housebuilding is null or laap.housebuilding='')) or laap.housebuilding=p.housebuilding)" +
					" and  vat.code=case when cast(to_char(current_date,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int) +(case when (cast(to_char(current_date, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int) +(case when (cast(to_char(current_date,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end) <18 then '2' else '1' end ").getResultList();
			} catch (NoResultException e) {
				LOG.error("Участок по адресу не найден");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (obj!=null&&obj.size()==1) {
				areaId=Long.parseLong(obj.get(0).toString());
			}
		}
		if (areaId!=null) {la = theManager.find(LpuArea.class,areaId);}
		List<LpuAttachedByDepartment> attachments = theManager.createQuery("from LpuAttachedByDepartment where patient_id=:pat and dateTo is null")
			.setParameter("pat", aPatientId).getResultList();
			List<VocAttachedType> attachedTypeList = theManager.createQuery("from VocAttachedType where code=:code").setParameter("code", attachedType).getResultList();


		if (attachedTypeList.isEmpty()) {
			return "Прикрепление не создано, не распознан тип прикрепления: "+attachedType;
		}
		VocAttachedType attType =  attachedTypeList.get(0);
		if (attachments.isEmpty()) { // Создаем новое
			MisLpu lpuAtt ;

			if (la!=null) {
				lpuAtt = la.getLpu();
			} else {
				Long l = Long.valueOf(theManager.createNativeQuery("select keyvalue from SoftConfig sc where sc.key='DEFAULT_LPU'").getResultList().get(0).toString());
				lpuAtt = theManager.find(MisLpu.class, l);
			}
			if (lpuAtt!=null) {
				LpuAttachedByDepartment att = new LpuAttachedByDepartment();
				att.setPatient(theManager.find(Patient.class, aPatientId));
				att.setLpu(lpuAtt);
				att.setAttachedType(attType);
					att.setArea(la);

				try {
					att.setDateFrom(DateFormat.parseSqlDate(attachedDate));
					att.setCompany(insCompany);
					att.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
					att.setCreateUsername("fond_check");


					theManager.persist(att);
				} catch (ParseException e) {
					LOG.error("Дата не распознана "+attachedDate);
					e.printStackTrace();
				}
				ret.append("Создано новое прикрепление. Тип=").append(att.getAttachedType().getCode()).append(", дата: ").append(DateFormat.formatToDate(att.getDateFrom())).append(", участок =  ").append(la != null ? la.getNumber() : "Не определен");
			} else {
				ret.append("0ЛПУ с кодом '").append(lpu).append("' не найдено, прикрепление не создано. ");
			}

		} else  { // Обновляем существующее
			for (LpuAttachedByDepartment a: attachments) {
				StringBuilder str = new StringBuilder();
				String areaSql =""; //= areaId!=null?(", area_id="+areaId):"";
				if (la!=null) {
					areaSql = ", area_id="+la.getId()+", lpu_id="+la.getLpu().getId();
				}
				ret.append("Обновлено прикрепление. Старый тип - ").append(a.getAttachedType() != null ? a.getAttachedType().getCode() : "").append(", дата - ").append(DateFormat.formatToDate(a.getDateFrom())).append(". Новый тип - ").append(attType.getCode()).append(", дата - ").append(attachedDate).append(". ");
				str.append("update LpuAttachedByDepartment set ").append(updateDate).append(" dateFrom=to_date('").append(attachedDate).append("','dd.mm.yyyy'), company_id='").append(insCompany.getId()).append("', editusername='fond_check', attachedtype_id=").append(attType.getId()).append(" ").append(areaSql).append(" where id='").append(a.getId()).append("'");
				theManager.createNativeQuery(str.toString()).executeUpdate();
			}
		}
	}
	return ret.toString();
}

	public boolean updateDataByFond(String aUsername, Long aPatientId, String aFiodr
			,String aDocument,String aPolicy,String aAddress
			,boolean aIsPatient, boolean aIsPolicy
			, boolean aIsDocument, boolean aIsAddress, boolean aIsAttachment) {
		String[] fiodr =null;
		String curDate = DateFormat.formatToDate(new java.util.Date()) ;

		if (aFiodr!=null && !aFiodr.equals("")) {
			fiodr = aFiodr.split("#") ;
				if (aIsPatient && aPatientId!=null &&aPatientId>0L &&(aIsPolicy||(fiodr.length>6 &&fiodr[6].length()==10))) {
					StringBuilder sql = new StringBuilder() ;
					if (!fiodr[0].startsWith("?")) {
						sql.append("update Patient set lastname='").append(fiodr[0]).append("'") ;
						sql.append(", firstname='").append(fiodr[1]).append("'") ;
						sql.append(", middlename='").append(fiodr[2]).append("'") ;
						sql.append(", birthday=to_date('").append(fiodr[3]).append("','dd.mm.yyyy')") ;
						if (fiodr[4]!=null&&!fiodr[4].trim().equals("")) {
							sql.append(", snils='").append(fiodr[4]).append("'") ;
						}
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
		if (aIsAddress && aAddress!=null &&!aAddress.equals("") ) {
			String[] adr = aAddress.split("#") ;
			StringBuilder sql  ;
			String kladr = adr[0] ;
			String sity = adr[5].toUpperCase() ;
			String street = adr[6].toUpperCase() ;
			String rayon = adr[4].toUpperCase() ;
			String region = adr[8].toUpperCase() ;
			String okato = adr[9].toUpperCase();
			String addressid=getAddressByKladr(kladr,region,rayon, sity, street,okato) ;

			sql = new StringBuilder() ;
			sql.append("update Patient set ") ;
			if (!StringUtil.isNullOrEmpty(addressid)) sql.append(" address_addressid='").append(addressid).append("' , ") ;
			sql.append(" houseNumber='").append(adr[1]).append("'") ;
			sql.append(", houseBuilding='").append(adr[2]).append("'") ;
			sql.append(", flatNumber='").append(adr[3]).append("'") ;
			sql.append(", rayon_id=(select id from VocRayon where code='").append(rayon).append("')") ;
			sql.append(", editDate=to_date('").append(curDate).append("','dd.mm.yyyy')") ;
			sql.append(", editUsername='").append(aUsername).append("'") ;
			sql.append(" where id='").append(aPatientId).append("'") ;
			theManager.createNativeQuery(sql.toString()).executeUpdate() ;
		}
		if (aIsAttachment){
			if (fiodr.length>7 && fiodr[7]!=null&&!fiodr[7].equals("")) { //Импорт данных прикрепления
				String doctorSnils = null;
				if (fiodr.length>10&&!(""+fiodr[10]).equals("")) {
					doctorSnils=""+fiodr[10];
				}
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
				updateOrCreateAttachment(aPatientId, aCompany, fiodr[7], fiodr[8], fiodr[9],doctorSnils, false, false);

		}
	}

		if (aPolicy!=null && !aPolicy.equals("") &&aIsPolicy) {
			String[] pols = aPolicy.split("&") ;

			for (String p:pols) {
				String[] pol = p.split("#") ;
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
		if (cntL!=null &&cntL>0L) {
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
			if (cntL1.equals(0L)) {
				sql = new StringBuilder() ;
				sql.append("select id,omcCode from REG_IC where omcCode='").append(aComp).append("' order by id desc") ;
				List<Object[]> idS = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
				sql = new StringBuilder() ;
				sql.append("select id,code from VocMedPolicyOmc where code='").append(type).append("' order by id desc") ;
				List<Object[]> idT = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
				if (!idS.isEmpty() && !idT.isEmpty()) {
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
					LOG.error(e);
				}
				}
			} else {
				return false ;
			}
		}
		return true ;
	}
	public void insertCheckFondData(String aLastname, String aFirstname, String aMiddlename, String aBirthday, String aSnils
			, String aCommonNumber, String aPolicySeries, String aPolicyNumber, String aPolicyDateFrom, String aPolicyDateTo
			, String aUsername, String aCheckType, String aCompanyCode, String aCompabyCodeF, String aCompanyOgrn
			, String aCompanyOkato, String aPatientId) throws ParseException {
		insertCheckFondData(aLastname, aFirstname, aMiddlename, aBirthday, aSnils, aCommonNumber, aPolicySeries, aPolicyNumber
				, aPolicyDateFrom, aPolicyDateTo, aUsername, aCheckType, aCompanyCode, aCompabyCodeF, aCompanyOgrn, aCompanyOkato
				, aPatientId, null, null, null, null, null
				, null, null, null, null, null, null
				, null, null, null, null, null
				, null, null);
	}
	public Long insertCheckFondData(
			String aLastname,String aFirstname,String aMiddlename,String aBirthday
			,String aSnils
			,String aCommonNumber,String aPolicySeries,String aPolicyNumber
			,String aPolicyDateFrom, String aPolicyDateTo
			,String aUsername, String aCheckType
			,String aCompanyCode ,String aCompabyCodeF,String aCompanyOgrn, String aCompanyOkato
			, String aPatientId
			,String aDocumentType, String aDocumentSeries,String aDocumentNumber
			,String aKladr,String aHouse, String aHouseBuilding, String aFlat
			,String aLpuAttached, String aAttachedDate, String aAttachedType, String dateDeath
			,String aDocumentDateIssued, String aDocumentWhomIssued, String aDoctorSnils, String aCodeDepartment
			,PatientFondCheckData aCheckTime, String aStreet, String aOkato
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
		fond.setStreet(aStreet);
		fond.setOkato(aOkato);

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
		//Patient pat = null;
		if (aPatientId!=null&&!aPatientId.equals("")&&!Long.valueOf(aPatientId).equals(0L)) {
			//pat = theManager.find(Patient.class, Long.valueOf(aPatientId));
			fond.setPatient(Long.valueOf(aPatientId));
		}
		if (aCheckTime!=null) {
			fond.setCheckTime(aCheckTime);
		}
		theManager.persist(fond) ;
		if (aPatientId!=null&&!aPatientId.equals("")&&!Long.valueOf(aPatientId).equals(0L)) {
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
		if (!list.isEmpty()) {
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

	public String getOmcCodeByPassportType(Long aPassportType) {
		VocIdentityCard vic = theManager.find(VocIdentityCard.class, aPassportType) ;
		return vic!=null?vic.getOmcCode():"" ;
	}
	public void setAddParamByMedCase(String aParam,Long aMedCase,Long aStatus)  {
		MedCase mc = theManager.find(MedCase.class, aMedCase) ;
		String method = "set" + Character.toUpperCase(aParam.charAt(0)) + aParam.substring(1);
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
		if (!newpat.getSex().getOmcCode().equals(oldpat.getSex().getOmcCode())) throw new IllegalArgumentException("Нельзя данные перенести персонам разных полов!!!") ;
			theManager.createNativeQuery("	update Patient set attachedOmcPolicy_id = null where id =:idold	").setParameter("idold", aIdOld).executeUpdate();
			//theManager.createNativeQuery("	update Customer set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update DeathCase set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update DeathCase set mother_id =:idnew where mother_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Diagnosis set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update DisabilityCase set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update DisabilityDocument set nursedPatient_id =:idnew where nursedPatient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update DisabilityDocument set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Kinsman set person_id =:idnew where person_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Kinsman set kinsman_id =:idnew where kinsman_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update LpuAttachedByDepartment set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Medcard set person_id =:idnew where person_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update MedCase set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update MedPolicy set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update NewBorn set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Pregnancy set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Privilege set person_id =:idnew where person_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update SurgicalOperation set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("	update Vaccination set patient_id =:idnew where patient_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
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
			theManager.createNativeQuery("  update PatientFond set patient =:idnew where patient =:idold ").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("  update JournalPatientFondCheck set patient_id =:idnew where patient_id =:idold ").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("  update JournalChangePatient set patient_id =:idnew where patient_id =:idold ").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("  update SuicideMessage set patient_id =:idnew where patient_id =:idold ").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("  update QualityEstimationCard set patient_id =:idnew where patient_id =:idold ").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
			theManager.createNativeQuery("  update Covid19 set patient_id =:idnew where patient_id =:idold ").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
	}

	public List<VocOrgForm> findOrg(String aNewNumber, String aOldNumber, String aName) {
		QueryClauseBuilder builder = new QueryClauseBuilder();
		builder.addLike("oldFondNumber", aOldNumber) ;
		builder.addLike("fondNumber", aNewNumber) ;
		builder.addLike("name", aName) ;
	//	Query query = builder.build(theManager, "from VocOrg where"," order by name,fondNumber,oldFondNumber");
		List<VocOrgForm> ret = new LinkedList<>();
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
	public WebQueryResult findPatient(Long aLpuId, Long aLpuAreaId,
			String aLastname, String aYear, Boolean aNext, String aIdNext) {
		WebQueryResult wqr = new WebQueryResult() ;
		String defaultLpu = SoftConfigServiceBean.getDefaultParameterByConfig("DEFAULT_LPU_OMCCODE", "-", theManager);
		boolean isEnableLimitAreas = theSessionContext.isCallerInRole("/Policy/Mis/Patient/EnableLimitPsychAreas") ;
		String fiIdprev=null ;
		if (aIdNext!=null) {
			List<Object[]> infoNext = theManager.createNativeQuery("select lastname,firstname,middlename from patient where id="+aIdNext).getResultList() ;
			if (!infoNext.isEmpty()) {
				fiIdprev = ""+infoNext.get(0)[0]+"0"+infoNext.get(0)[1]+"0"+infoNext.get(0)[2]+"0"+aIdNext ;
			}
		}
		List<PatientForm> ret1 = new LinkedList<>() ;
		List<PatientForm> ret2 = new LinkedList<>() ;
		List<PatientForm> ret3 = new LinkedList<>() ;
		StringBuilder sqlFld = new StringBuilder() ;
		sqlFld.append(" select p.id,p.lastname,p.firstname,p.middlename,p.birthday") ;
		sqlFld.append(" ,p.patientSync,case when p.colorType='1' then cast('red' as varchar(200)) else coalesce((select coalesce((select max(pl.colorText) from PatientList pl left join VocPatientListType vplt on vplt.id=pl.type where vplt.code='SUICIDE'),'#01D') from SuicideMessage sui where sui.patient_id=p.id),(select coalesce('background:'||max(pl.colorName)||';','')||coalesce('color:'||max(pl.colorText)||';font-size: 14px;','') from PatientListRecord plr left join PatientList pl on pl.id=plr.PatientList where plr.patient=p.id and pl.isViewWhenSeaching='1' group by plr.patient)) end as ColorType ") ;
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
			sqlFld.append("select p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.patientSync,case when p.colorType='1' then cast('red' as varchar(200)) else coalesce((select coalesce((select max(pl.colorText) from PatientList pl left join VocPatientListType vplt on vplt.id=pl.type where vplt.code='SUICIDE'),'#01D') from SuicideMessage sui where sui.patient_id=p.id),(select coalesce('background:'||max(pl.colorName)||';','')||coalesce('color:'||max(pl.colorText)||';font-size: 14px;','') from PatientListRecord plr left join PatientList pl on pl.id=plr.PatientList where plr.patient=p.id and pl.isViewWhenSeaching='1' group by plr.patient)) end as ColorType");
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
				if (aYear!=null && !aYear.equals("")) {
					p1.append(" and to_char(p.birthday,'yyyy') = :pyear ");
					p2.append(" and to_char(jcp.birthday,'yyyy') = :pyear ");
					builder.addParameter("pyear", aYear);
				}
				if (fiIdprev!=null && !fiIdprev.equals("")) {
					if (aNext) {
						p1.append(" and p.lastname||'0'||p.firstname||'0'||p.middlename||'0'||p.id > :nextid ");
						p2.append(" and p.lastname||'0'||p.firstname||'0'||p.middlename||'0'||p.id > :nextid ");
					} else {
						p1.append(" and p.lastname||'0'||p.firstname||'0'||p.middlename||'0'||p.id < :nextid ");
						p2.append(" and p.lastname||'0'||p.firstname||'0'||p.middlename||'0'||p.id < :nextid ");
					}
					builder.addParameter("nextid", fiIdprev);
				}

				Query query = builder.buildNative(theManager, sqlFld+" from patient p "+sql+" and "+p1," group by p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.patientSync,p.ColorType order by p.lastname "+(aNext?"":" desc")+",p.firstname "+(aNext?"":" desc")+",p.middlename "+(aNext?"":" desc")+",p.id "+(aNext?"":" desc"));
				appendNativeToList(query, ret1,null,aNext);
				sqlFld = new StringBuilder() ;
				sqlFld.append("select p.id,p.lastname||' '||list(case when p.lastname!=jcp.lastname then '('||jcp.lastname||')' else '' end) as lastname");
				sqlFld.append(",p.firstname||' '||list(case when p.firstname!=jcp.firstname then '('||jcp.firstname||')' else '' end) as firstname");
				sqlFld.append(",p.middlename||' '||list(case when p.middlename!=jcp.middlename then '('||jcp.middlename||')' else '' end) as middlename,p.birthday,p.patientSync,case when p.colorType='1' then cast('red' as varchar(200)) else coalesce((select coalesce((select max(pl.colorText) from PatientList pl left join VocPatientListType vplt on vplt.id=pl.type where vplt.code='SUICIDE'),'#01D') from SuicideMessage sui where sui.patient_id=p.id),(select coalesce('background:'||max(pl.colorName)||';','')||coalesce('color:'||max(pl.colorText)||';font-size: 14px;','') from PatientListRecord plr left join PatientList pl on pl.id=plr.PatientList where plr.patient=p.id and pl.isViewWhenSeaching='1' group by plr.patient)) end as ColorType");
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
				.append(" and ").append(p2).append(" and (jcp.lastname!=p.lastname or jcp.firstname!=p.firstname or jcp.middlename!=p.middlename)").toString()," group by p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.patientSync,p.ColorType order by  p.lastname "+(aNext?"":" desc")+",p.firstname "+(aNext?"":" desc")+",p.middlename "+(aNext?"":" desc")+",p.id "+(aNext?"":" desc"));
				appendNativeToList(query1, ret1,"Изменены персональные данные",aNext);
				if (ret1.isEmpty() && aIdNext!=null && !aIdNext.equals("")) return findPatient( aLpuId, aLpuAreaId, aLastname, aYear, !aNext, null) ;
			}
		} else {
			StringBuilder sql = new StringBuilder() ;
			StringBuilder sqlFld1 = new StringBuilder() ;
			sqlFld1.append("select p.id,p.lastname||' '||list(case when p.lastname!=jcp.lastname then '('||jcp.lastname||')' else '' end) as lastname");
			sqlFld1.append(",p.firstname||' '||list(case when p.firstname!=jcp.firstname then '('||jcp.firstname||')' else '' end) firstname");
			sqlFld1.append(",p.middlename||' '||list(case when p.middlename!=jcp.middlename then '('||jcp.middlename||')' else '' end) as middlename");
			sqlFld1.append(" ,p.birthday") ;
			sqlFld1.append(" ,p.patientSync,case when p.colorType='1' then cast('color: red' as varchar(200)) else coalesce((select coalesce((select 'color: '||max(pl.colorText) " +
					" from PatientList pl left join VocPatientListType vplt on vplt.id=pl.type where vplt.code='SUICIDE'),'#01D') from SuicideMessage sui where sui.patient_id=p.id)" +
					",(select coalesce('background:'||max(pl.colorName)||';','')||coalesce('color:'||max(pl.colorText)||';font-size: 14px;','') from PatientListRecord plr left join PatientList pl on pl.id=plr.PatientList where plr.patient=p.id and pl.isViewWhenSeaching='1' group by plr.patient)) end as ColorType ") ;
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
				if (aYear!=null && !aYear.equals("")) {
					p1.append(" and to_char(p.birthday,'yyyy') = :pyear ");
					p2.append(" and to_char(p.birthday,'yyyy') = :pyear ");
					builder.addParameter("pyear", aYear);
				}
				if (fiIdprev!=null && !fiIdprev.equals("")) {
					if (aNext) {
						p1.append(" and p.lastname||'0'||p.firstname||'0'||p.middlename||'0'||p.id > :nextid ");
						p2.append(" and p.lastname||'0'||p.firstname||'0'||p.middlename||'0'||p.id > :nextid ");
					} else {
						p1.append(" and p.lastname||'0'||p.firstname||'0'||p.middlename||'0'||p.id < :nextid ");
						p2.append(" and p.lastname||'0'||p.firstname||'0'||p.middlename||'0'||p.id < :nextid ");
					}
					builder.addParameter("nextid", fiIdprev);
				}
			}

			builder.add("att.lpu_id", aLpuId);
			builder.add("att.area_id", aLpuAreaId);
			Query query = builder.buildNative(theManager,sqlFld+" from patient p "+sql+p1,
					"group by p.id,p.lastname,p.firstname,p.middlename,p.birthday ,p.patientSync, p.colorType order by  p.lastname "+(aNext?"":" desc")+",p.firstname "+(aNext?"":" desc")+",p.middlename "+(aNext?"":" desc")+",p.id "+(aNext?"":" desc"));
			appendNativeToList(query, ret1,null,aNext);
			if (ret1.isEmpty() && aIdNext!=null && !aIdNext.equals("")) return findPatient( aLpuId, aLpuAreaId, aLastname, aYear, !aNext, null) ;
			Query query1 = builder.buildNative(theManager, new StringBuilder().append(sqlFld1)
					.append(" from JournalChangePatient jcp ")
					.append(" left join patient p on jcp.patient_id=p.id ")
					.append(sql)
					.append(p2)
					.append(" and (jcp.lastname!=p.lastname or jcp.firstname!=p.firstname or jcp.middlename!=p.middlename)")
					.append(" and not (").append(p1).append(")")
					.toString(),
					"group by p.id,p.lastname,p.firstname,p.middlename,p.birthday ,p.patientSync, p.colorType order by  p.lastname "+(aNext?"":" desc")+",p.firstname "+(aNext?"":" desc")+",p.middlename "+(aNext?"":" desc")+",p.id "+(aNext?"":" desc"));
			appendNativeToList(query1, ret2,null,aNext);


			// поиск по полису
			if(!StringUtil.isNullOrEmpty(aLastname) && (ret1.isEmpty()&&ret2.isEmpty())) {
				appendNativeToList(findByMedCardNumber(sqlFld,aLastname,aYear), ret3,null,true);
			}
			// Поиск по коду синхронизации
			if(!StringUtil.isNullOrEmpty(aLastname) && (ret1.isEmpty()&&ret2.isEmpty()&&ret3.isEmpty())) {
				appendNativeToList(findByPatientSync(sqlFld,aLastname,aYear), ret3,null,true);
			}
			if (theSessionContext.isCallerInRole("/Policy/Mis/Patient/FindByCommonNumber") ) {
				// Поиск по RZ
				if(!StringUtil.isNullOrEmpty(aLastname) && (ret1.isEmpty()&&ret2.isEmpty()&&ret3.isEmpty())) {
					appendNativeToList(findByPatientRz(sqlFld,aLastname,aYear), ret3,null,true);
				}
			}
			if(!StringUtil.isNullOrEmpty(aLastname) && (ret1.isEmpty()&&ret2.isEmpty()&&ret3.isEmpty())) {
				appendNativeToList(findByPolicy(sqlFld,aLpuId, aLpuAreaId, aLastname,aYear), ret3,null,true);
			}
		}
		wqr.set1(ret1) ;
		wqr.set2(ret2) ;
		wqr.set3(ret3) ;
		return wqr;

	}

	private Query findByPolicy(StringBuilder aSqlFld, Long aLpuId, Long aLpuAreaId, String aPolicyQuery,String aYear) {
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
		if (aYear!=null && !aYear.equals("")) {
			b.add("to_char(p.birthday,'yyyy')", aYear);
		}
		return b.buildNative(theManager, sql.toString(), "group by p.id, p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.patientSync, p.colorType order by p.lastname, p.firstname,p.middlename,p.id") ;//order by MedPolicy.patient.lastname, MedPolicy.patient.firstname");
		// from MedPolicy where series = :series and
	}

	private Query findByMedCardNumber(StringBuilder aSqlFld,String aPolicyQuery,String aYear) {
		QueryClauseBuilder b = new QueryClauseBuilder() ;
		StringBuilder sql = new StringBuilder() ;
		sql.append(aSqlFld);
		sql.append(" from Medcard m") ;
		sql.append(" left join Patient p on m.person_id = p.id") ;
		sql.append(" left join LpuAttachedByDepartment att on att.patient_id=p.id") ;
		sql.append(" left join Mislpu ml on ml.id=att.lpu_id") ;
		sql.append(" left join lpuarea ma on ma.id=att.area_id") ;
		sql.append(" left join VocAttachedType vat on vat.id=att.AttachedType_id") ;
		sql.append(" where ") ;

		StringTokenizer st = new StringTokenizer(aPolicyQuery, " ") ;
		String number = st.hasMoreTokens() ? st.nextToken() : null ;
		b.add("m.number", number);

		if (aYear!=null && !aYear.equals("")) {
			b.add("to_char(p.birthday,'yyyy')", aYear);
		}

		return b.buildNative(theManager, sql.toString(), "group by p.id, p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.patientSync, p.colorType order by p.lastname, p.firstname,p.middlename,p.id") ;//order by MedPolicy.patient.lastname, MedPolicy.patient.firstname");
		// from MedPolicy where series = :series and
	}
	private Query findByPatientSync(StringBuilder aSqlFld, String aPolicyQuery,String aYear) {
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
		b.add("p.patientSync", number);
		if (aYear!=null && !aYear.equals("")) {
			b.add("to_char(p.birthday,'yyyy')", aYear);
		}

		return b.buildNative(theManager, sql.toString(), "group by p.id, p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.patientSync, p.colorType order by p.lastname, p.firstname,p.middlename,p.id") ;//order by MedPolicy.patient.lastname, MedPolicy.patient.firstname");
		// from MedPolicy where series = :series and
	}
	private Query findByPatientRz(StringBuilder aSqlFld, String aPolicyQuery,String aYear) {
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
		b.add("commonNumber", number);
		if (aYear!=null && !aYear.equals("")) {
			b.add("to_char(p.birthday,'yyyy')", aYear);
		}

		return b.buildNative(theManager, sql.toString()
				, "group by p.id, p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.patientSync, p.colorType order by p.lastname, p.firstname,p.middlename,p.id") ;//order by MedPolicy.patient.lastname, MedPolicy.patient.firstname");
		// from MedPolicy where series = :series and
	}

	@SuppressWarnings("unchecked")
	private void appendNativeToList(Query aQuery, List<PatientForm> ret, String aAddInfo, boolean aNext) {
		List<Object[]> list = aQuery.setMaxResults(50).getResultList();
		for (int i=0; i<list.size(); i++) {
			int ind=i ;
			if (!aNext) ind=list.size()-i-1 ;
			Object[] arr = list.get(ind) ;
			PatientForm f = new PatientForm();
			f.setId(((Number) arr[0]).longValue());
			if (arr.length>6 && arr[6]!=null) {
				f.setLastname("<font style='" + arr[6] + "'>" + arr[1] + "</font>");
				f.setFirstname("<font style='" + arr[6] + "'>" + arr[2] + "</font>");
				f.setMiddlename("<font style='" + arr[6] + "'>" + arr[3] + "</font>");
				if(arr[4]!=null) {
					f.setBirthday(DateFormat.formatToDate((java.util.Date) arr[4])) ;
				}
				f.setPatientSync(arr[5]!=null?(String) arr[5]:"") ;
			} else if (aAddInfo!=null) {
				f.setLastname(aAddInfo + "<font color='blue'>" + arr[1] + "</font>");
				f.setFirstname("<font color='blue'>" + arr[2] + "</font>");
				f.setMiddlename("<font color='blue'>" + arr[3] + "</font>");
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
		/*
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
		}*/
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
				LOG.error(e);
				throw new IllegalStateException(e);
			}
			return point.getLpuAreaAddressText().getAddressText() +
					" " + point.getLpuAreaAddressText().getArea().getName() +
					" " + point.getLpuAreaAddressText().getArea().getLpu().getName();
		}
	}

	/**
	 * Ищем первую попавшуюся точку прикрепления.
	 * Теперь и в зависимости от возраста
	 */
	public LpuAreaAddressPoint findPoint(Address aAddress, String aNumber,
			String aBuilding, Date aBirthday, String aFlat) {
		// ребенок, если < 18 и есть дата рождения
		boolean isChild = aBirthday != null && AgeUtil.calcAgeYear(aBirthday, new java.util.Date()) < 18;
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

	public String addPatient(String aLastname, String aFirstname, String aMiddlename,
			String aBirthday, Long aSex, Long aSocialStatus, String aSnils) throws ParseException {
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
			throw new ParseException("Неправильно введена дата рождения пациента", 0) ;
		}
		pat.setLastname(aLastname.toUpperCase()) ;
		pat.setMiddlename(aMiddlename.toUpperCase()) ;
		pat.setFirstname(aFirstname.toUpperCase()) ;
		pat.setSex(sex) ;
		pat.setSocialStatus(statusSocial) ;
		pat.setSnils(aSnils) ;
		theManager.persist(pat) ;
		return pat.getId()+"#"+pat.getLastname()+" "+pat.getFirstname()+" "+pat.getMiddlename()+" "+date ;

	}

	public String getDoubleByBaseData(String aId, String aLastname, String aFirstname, String aMiddlename,
			String aSnils, String aBirthday, String aPassportType, String aPassportNumber, String aPassportSeries, String aAction,boolean aIsFullBirthdayCheck) {
		StringBuilder sql = new StringBuilder() ;
		aFirstname = aFirstname.toUpperCase().trim() ;
		aMiddlename = aMiddlename.toUpperCase().trim() ;
		aLastname = aLastname.toUpperCase().trim() ;
		sql.append(" from Patient p")
			.append(" where (")
			.append(" (p.lastname =:lastname and p.firstname = :firstname and p.middlename=:middlename and ");
		String birthyear ;
		if (!aIsFullBirthdayCheck) {
			birthyear= aBirthday.substring(6) ;
			sql.append("to_char(p.birthday,'yyyy')=:birthyear)") ;
		} else {
			birthyear= aBirthday ;
			sql.append("p.birthday=to_date(:birthyear,'dd.mm.yyyy'))") ;
		}
		if (aSnils!=null && !aSnils.equals("") && !aSnils.equals("999-999-999 99")) {
			sql.append(" or (p.snils='").append(aSnils).append("')") ;
		}
		if (!StringUtil.isNullOrEmpty(aPassportType)
				&& !StringUtil.isNullOrEmpty(aPassportNumber)
				&& !StringUtil.isNullOrEmpty(aPassportSeries)  ) {
			sql.append(" or ( p.passportType_id=").append(aPassportType).append(" and p.passportNumber='").append(aPassportNumber).append("' and p.passportSeries='").append(aPassportSeries).append("')") ;
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
				.setMaxResults(20)
				.getResultList() ;

		if (!doubles.isEmpty()) {
			StringBuilder ret = new StringBuilder() ;
			ret.append("<br/><ol>") ;
			ret.append("<li>Количество найденных дублей: <b>").append(doubles.size()).append("</b></li>") ;
			for (Object[] res:doubles) {
				ret.append("<li>")
				.append("<a href='") ;
				if (aAction.toLowerCase().contains("javascript")) {
					ret.append(aAction).append("(\"").append(res[0]).append("\",\"").append(res[1]).append(" ").append(res[2]).append(" ").append(res[3])
						.append(" ").append(res[4]).append("\")'") ;
				} else {
					ret.append(aAction) ;
					if (!aAction.toLowerCase().contains(".do")) {
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
private @EJB
	IWebQueryService theWebQueryService;
	private @PersistenceContext EntityManager theManager;

	private final LpuAreaDynamicSecurity theLpuAreaDynamicSecurity = new LpuAreaDynamicSecurity();

	private @Resource SessionContext theSessionContext;
	public String getConfigValue(String aConfigName, String aDefaultValue) {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		return config.get(aConfigName, aDefaultValue);
	}

	/**Находим либо создаем пациента*/
	public Patient getPatient(String aLastname, String aFirstname, String aMiddlename, Date aBirthDay, String aSexCode, String aCommonNumber
			,String addDataString)  {
		JSONObject addData = new JSONObject(addDataString);
		List<Patient> patients = theManager.createQuery("from Patient where lastname=:lastname and firstname=:firstname" +
				" and middlename=:middlename  and birthday=:birthday" +
				" and (commonNumber is null or commonNumber=:commonNumber)")
				.setParameter("lastname",aLastname).setParameter("firstname",aFirstname).setParameter("middlename",aMiddlename)
				.setParameter("birthday",aBirthDay).setParameter("commonNumber",aCommonNumber)
				.getResultList();
		if (patients.isEmpty()) { //Создаем нового пациента
			Patient patient = new Patient();
			patient.setLastname(aLastname.toUpperCase());
			patient.setFirstname(aFirstname.toUpperCase());
			patient.setMiddlename(aMiddlename.toUpperCase());
			patient.setBirthday(aBirthDay);
			patient.setCommonNumber(aCommonNumber);
			patient.setSnils(getStr(addData,"snils"));
			patient.setBirthPlace(getStr(addData, "birthPlace"));
			patient.setSex(getByCode(VocSex.class, aSexCode));
			theManager.persist(patient);
			if (addData.has("ogrn")) {
				String okato = addData.getString("okato");
				String ogrn = getStr(addData,"ogrn");
				try {
					List<RegInsuranceCompany> regInsuranceCompanies = theManager.createQuery("from RegInsuranceCompany where ogrn=:ogrn" +
							" and (deprecated is null or deprecated is false)").setParameter("ogrn",ogrn).getResultList();
					if ("12000".equals(okato)) { //местный полис
						MedPolicyOmc policy = new MedPolicyOmc();
						policy.setActualDateFrom(DateFormat.parseSqlDate(addData.getString("policyStartDate"), "ddMMyyyy"));
						policy.setLastname(aLastname);
						policy.setFirstname(aFirstname);
						policy.setMiddlename(aMiddlename);
						policy.setBirthday(aBirthDay);
						policy.setPatient(patient);
						policy.setCommonNumber(aCommonNumber);
						policy.setPolNumber(aCommonNumber);
						if (!regInsuranceCompanies.isEmpty()) policy.setCompany(regInsuranceCompanies.get(0));
						policy.setType(getByCode(VocMedPolicyOmc.class,"3")); //врядли будет другое
						theManager.persist(policy);
					} else if (okato!=null){ //ОМС иногорднего
						MedPolicyOmcForeign policy = new MedPolicyOmcForeign();
						policy.setActualDateFrom(DateFormat.parseSqlDate(addData.getString("policyStartDate"), "ddMMyyyy"));
						if (!regInsuranceCompanies.isEmpty()) policy.setCompany(regInsuranceCompanies.get(0));
						policy.setLastname(aLastname);
						policy.setFirstname(aFirstname);
						policy.setMiddlename(aMiddlename);
						policy.setBirthday(aBirthDay);
						policy.setPatient(patient);
						policy.setCommonNumber(aCommonNumber);
						policy.setPolNumber(aCommonNumber);
						policy.setType(getByCode(VocMedPolicyOmc.class,"3")); //врядли будет другое
						policy.setInsuranceCompanyOgrn(ogrn);
						policy.setInsuranceCompanyRegion(getByCode(OmcKodTer.class,okato,"okato"));
						theManager.persist(policy);
					}
				} catch (ParseException e) {
					LOG.error("some error",e);
				}

			}
			return patient;
		} else if (patients.size()==1) {
			return patients.get(0);
		} else { //Найдено более 1 пациента, возвращаем ссылку на страницу с поиском пациента
			return null;
		}
	}

	private String getStr(JSONObject obj, String aFldName) {
		return obj.has(aFldName) ? obj.getString(aFldName) : null;
	}

	private <T> T getByCode(Class aClass, String aCode) {
		return getByCode(aClass,aCode,null);
	}
	private <T> T getByCode(Class aClass, String aCode, String aFldName) {
		List<T> list = theManager.createQuery("from "+aClass.getName()+" where "+(aFldName!=null?aFldName:"code")+"=:code")
				.setParameter("code",aCode).getResultList();
		return list.isEmpty() ? null : list.get(0);
	}
}