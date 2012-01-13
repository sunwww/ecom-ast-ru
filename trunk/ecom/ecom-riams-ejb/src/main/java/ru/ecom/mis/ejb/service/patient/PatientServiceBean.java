package ru.ecom.mis.ejb.service.patient;

import java.lang.reflect.Method;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import ru.ecom.mis.ejb.domain.contract.NaturalPerson;
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressPoint;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressText;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocIdentityCard;
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
	
	private String getAddressByKladr(String aKladr,String aRayon, String aSity, String aStreet) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select addressid,kladr from Address2 where kladr='").append(aKladr).append("'" ) ;
		StringBuilder res = new StringBuilder() ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		if (list.size()>0) {
			res.append(list.get(0)[0]) ;
		} else {
			if (aStreet.startsWith("?")) return res.toString() ;
			String lastKl = aKladr.equals("")?"30":aKladr.substring(aKladr.length()-1, aKladr.length()) ;
			while (lastKl!=null&&lastKl.equals("0")) {
				aKladr = aKladr.substring(0,aKladr.length()-1) ;
				lastKl = aKladr.substring(aKladr.length()-1, aKladr.length()) ;
			}
			
			sql = new StringBuilder() ;
			sql.append("select addressid,kladr from Address2 where kladr like '").append(aKladr).append("%' and UPPER(name)='").append(aSity).append("'" ) ;
			list.clear() ;
			list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			if (list.size()>0) {
				aKladr = ""+list.get(0)[1] ;
				lastKl = aKladr.substring(aKladr.length()-1, aKladr.length()) ;
				while (lastKl!=null&&lastKl.equals("0")) {
					aKladr = aKladr.substring(0,aKladr.length()-1) ;
					lastKl = aKladr.substring(aKladr.length()-1, aKladr.length()) ;
				}
				sql = new StringBuilder() ;
				sql.append("select addressid,kladr from Address2 where kladr like '").append(aKladr).append("%' and UPPER(name)='").append(aStreet).append("'" ) ;
				list.clear() ;
				list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
				res.append(list.get(0)[0]) ;
			} else {
				sql = new StringBuilder() ;
				sql.append("select id, kladr from VocRayon where code='").append(aRayon).append("'" ) ;
				list.clear() ;
				list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
				if (list.size()>0) {
					aKladr = ""+list.get(0)[1] ;
				}
				sql = new StringBuilder() ;
				sql.append("select addressid,kladr from Address2 where kladr like '").append(aKladr).append("%' and UPPER(name)='").append(aSity).append("'" ) ;
				list.clear() ;
				list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
				if (list.size()>0) {
					aKladr = ""+list.get(0)[1] ;
					lastKl = aKladr.substring(aKladr.length()-1, aKladr.length()) ;
					while (lastKl!=null&&lastKl.equals("0")) {
						aKladr = aKladr.substring(0,aKladr.length()-1) ;
						lastKl = aKladr.substring(aKladr.length()-1, aKladr.length()) ;
					}
					sql = new StringBuilder() ;
					sql.append("select addressid,kladr from Address2 where kladr like '").append(aKladr).append("%' and UPPER(name)='").append(aStreet).append("'" ) ;
					list.clear() ;
					list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
					res.append(list.get(0)[0]) ;
				}
				

			}
			
		}

		return res.toString() ;
	}
	public String getInfoVocForFond(String aPassportType,String aAddress) {
		StringBuilder sql = new StringBuilder() ;
		StringBuilder res = new StringBuilder() ;
		sql.append("select id,name from VocIdentityCard where omcCode='").append(aPassportType).append("'" ) ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		if (list.size()>0) {
			res.append(list.get(0)[0]).append("#").append(list.get(0)[1]).append("#") ;
		} else {
			res.append("##");
		}
		sql = new StringBuilder() ;
		//res = new StringBuilder() ;
		String[] adr = aAddress.split("#") ;
		String kladr = adr[0] ;
		String rayon = adr[1].toUpperCase() ;
		String sity = adr[2].toUpperCase() ;
		String street = adr[3].toUpperCase() ;
		res.append(getAddressByKladr(kladr,rayon, sity, street)) ;
		res.append("#");
		sql = new StringBuilder() ;
		//res = new StringBuilder() ;
		
		sql.append("select id,code||' '||name from VocRayon where code='").append(rayon).append("'" ) ;
		list.clear() ;
		list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
		if (list.size()>0) {
			res.append(list.get(0)[0]).append("#").append(list.get(0)[1]).append("#") ;
		} else {
			res.append("##");
		}
		return res.toString() ;
	}
	public boolean updateDataByFond(Long aPatientId, String aFiodr
			,String aDocument,String aPolicy,String aAddress) {
		if (aFiodr!=null && !aFiodr.equals("")) {
			String[] fiodr = aFiodr.split("#") ;
			if (aPatientId!=null &&aPatientId>Long.valueOf(0)) {
				StringBuilder sql = new StringBuilder() ;
				
				if (!fiodr[0].startsWith("?")) {
					sql.append("update Patient set lastname='").append(fiodr[0]).append("'") ;
					sql.append(", firstname='").append(fiodr[1]).append("'") ;
					sql.append(", middlename='").append(fiodr[2]).append("'") ;
					sql.append(", birthday=to_date('").append(fiodr[3]).append("','dd.mm.yyyy')") ;
					sql.append(", snils='").append(fiodr[4]).append("'") ;
					sql.append(" where id='").append(aPatientId).append("'") ;
					theManager.createNativeQuery(sql.toString()).executeUpdate() ;
				} else {
					sql.append("update Patient set ") ;
					//sql.append("lastname='").append(fiodr[0]).append("'") ;
					//sql.append(", firstname='").append(fiodr[1]).append("'") ;
					//sql.append(", middlename='").append(fiodr[2]).append("'") ;
					sql.append(" birthday=to_date('").append(fiodr[3]).append("','dd.mm.yyyy')") ;
					sql.append(", snils='").append(fiodr[4]).append("'") ;
					sql.append(" where id='").append(aPatientId).append("'") ;
					theManager.createNativeQuery(sql.toString()).executeUpdate() ;
					
				}
			}
		}
		if (aDocument!=null &&!aDocument.equals("")) {
			String[] doc = aDocument.split("#") ;
			StringBuilder sql = new StringBuilder() ;
			if (!doc[0].startsWith("?")) {
				sql.append("update Patient set passportSeries='").append(doc[1]).append("'") ;
				sql.append(", passportNumber='").append(doc[2]).append("'") ;
				sql.append(", passportDateIssued=to_date('").append(doc[3]).append("','dd.mm.yyyy')") ;
				sql.append(", passportWhomIssued='").append(doc[4]).append("'") ;
				sql.append(", passportType_id=(select id from VocIdentityCard where omcCode='").append(doc[0]).append("')") ;
				sql.append(" where id='").append(aPatientId).append("'") ;
				theManager.createNativeQuery(sql.toString()).executeUpdate() ;
			} else {
				sql.append("update Patient set passportSeries='").append(doc[1]).append("'") ;
				sql.append(", passportNumber='").append(doc[2]).append("'") ;
				sql.append(", passportDateIssued=to_date('").append(doc[3]).append("','dd.mm.yyyy')") ;
				//sql.append(", passportWhomIssued='").append(doc[4]).append("'") ;
				sql.append(", passportType_id=(select id from VocIdentityCard where omcCode='").append(doc[0]).append("')") ;
				sql.append(" where id='").append(aPatientId).append("'") ;
				theManager.createNativeQuery(sql.toString()).executeUpdate() ;
				
			}
		}
		if (aAddress!=null &&!aAddress.equals("")) {
			String[] adr = aAddress.split("#") ;
			StringBuilder sql = new StringBuilder() ;
			String kladr = adr[0] ;
			String sity = adr[5].toUpperCase() ;
			String street = adr[6].toUpperCase() ;
			String rayon = adr[4].toUpperCase() ;
			String addressid=getAddressByKladr(kladr,rayon, sity, street) ;
			
			sql = new StringBuilder() ;
			sql.append("update Patient set ") ;
			if (addressid!=null&&!addressid.equals("")) sql.append(" address_addressid='").append(addressid).append("' , ") ;
			sql.append(" houseNumber='").append(adr[1]).append("'") ;
			sql.append(", houseBuilding='").append(adr[2]).append("'") ;
			sql.append(", flatNumber='").append(adr[3]).append("'") ;
			sql.append(", rayon_id=(select id from VocRayon where code='").append(rayon).append("')") ;
			sql.append(" where id='").append(aPatientId).append("'") ;
			theManager.createNativeQuery(sql.toString()).executeUpdate() ;
		}
		if (aPolicy!=null && !aPolicy.equals("")) {
			String[] pols = aPolicy.split("&") ;
			for (String p:pols) {
				String[] pol = p.split("#") ;
				StringBuilder sql = new StringBuilder() ;
				sql.append("select count(*) from medpolicy ") ;
				sql.append(" where patient_id='").append(aPatientId).append("'");
				sql.append(" and series='").append(pol[1]).append("'") ;
				sql.append(" and polNumber='").append(pol[2]).append("'") ;
				Object cnt =theManager.createNativeQuery(sql.toString()).getSingleResult() ;
				Long cntL = ConvertSql.parseLong(cnt) ;
				String type = "" ;
				if (pol[1]!=null) {
					if (pol[1].startsWith("0") && pol[1].length()<4) {
						type = "2" ;
					} else if (pol[1].startsWith("0") && pol[1].length()>3) {
						type = "3" ;
					} else {
						type ="1" ;
					}
				}
				if (cntL!=null &&cntL>Long.valueOf(0)) {
					sql = new StringBuilder() ;
					sql.append("update MedPolicy set ") ;
					sql.append(" company_id=(select id from REG_IC where omcCode='").append(pol[0]).append("')") ;
					sql.append(", actualDateFrom=to_date('").append(pol[3]).append("','dd.mm.yyyy')") ;
					sql.append(", actualDateTo=to_date('").append(pol[4]).append("','dd.mm.yyyy')") ;
					sql.append(", commonNumber='").append(pol[5]).append("'") ;
					sql.append(", type_id=(select id from VocMedPolicyOmc where code='").append(type).append("')") ;
					sql.append(" where patient_id='").append(aPatientId).append("'");
					sql.append(" and series='").append(pol[1]).append("'") ;
					sql.append(" and polNumber='").append(pol[2]).append("'") ;
					theManager.createNativeQuery(sql.toString()).executeUpdate() ;
				} else {
					
					sql = new StringBuilder() ;
					sql.append("insert into MedPolicy (dtype,company_id,actualDateFrom,actualDateTo,commonNumber,patient_id,series,polNumber,type_id) values ('MedPolicyOmc'") ;
					sql.append(", (select id from REG_IC where omcCode='").append(pol[0]).append("')") ;
					sql.append(", to_date('").append(pol[3]).append("','dd.mm.yyyy')") ;
					sql.append(", to_date('").append(pol[4]).append("','dd.mm.yyyy')") ;
					sql.append(", '").append(pol[5]).append("'") ;
					sql.append(", '").append(aPatientId).append("'");
					sql.append(", '").append(pol[1]).append("'") ;
					sql.append(", '").append(pol[2]).append("',(select id from VocMedPolicyOmc where code='").append(type).append("'))") ;
					theManager.createNativeQuery(sql.toString()).executeUpdate() ;
				}
				
			}
		}
		return true ;
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
    
    public String infoMedPolicy(Long aPatient) {
    	try {
			//Date date = new Date() ;
			Object[] polerr = (Object[]) theManager.createNativeQuery("SELECT TOP 1 " 
			                              +"$$CheckPatientOMC^ZMedPolicy('"+ aPatient+"'),id " 
			                              +"FROM vocSex")
				//.setParameter("patid", form.getId())
				.getSingleResult();
			//form.setNotice(form.getNotice()+form.getId() +polerr[0]+"----"+polerr.length+polerr.toString()) ;
			String err =( polerr[0]==null?"": (String) polerr[0]);
			return err ;
		
		} catch(Exception e){
		}
		return "" ;
    }
	public void movePatientDoubleData(Long aIdNew, Long aIdOld)  {
		Patient newpat = theManager.find(Patient.class, aIdNew) ;
		Patient oldpat = theManager.find(Patient.class, aIdOld) ;
		if (newpat!=null && oldpat!=null) {
			theManager.createNativeQuery("	update Patient set attachedOmcPolicy_id = null where person_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
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
			theManager.createNativeQuery("	update Kinsman set kinsman_id =:idnew where kinsman_id =:idold	").setParameter("idnew", aIdNew).setParameter("idold", aIdOld).executeUpdate();
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
		
		if (CAN_DEBUG) {
			LOG.debug("findPatient() aLpuId = " + aLpuId + ", aLpuAreaId = "
					+ aLpuAreaId + ", aLastname = " + aLastname);
		}
		QueryClauseBuilder builder = new QueryClauseBuilder();
		builder.add("lpu_id", aLpuId);
		builder.add("lpuArea_id", aLpuAreaId);
		if (!StringUtil.isNullOrEmpty(aLastname)) {
			StringTokenizer st = new StringTokenizer(aLastname, " \t;,.");
			if (st.hasMoreTokens())
				builder.addLike("lastname", st.nextToken() + "%");
			if (st.hasMoreTokens())
				builder.addLike("firstname", st.nextToken() + "%");
			if (st.hasMoreTokens())
				builder.addLike("middlename", st.nextToken() + "%");
		}
		Query query = builder.build(theManager, "from Patient where",
				" order by lastname,firstname");

		List<PatientForm> ret = new LinkedList<PatientForm>();
		appendToList(query, ret);

		// Поиск по специальному прикреплению
		builder = new QueryClauseBuilder();
		builder.add("LpuAttachedByDepartment.lpu_id", aLpuId);
		// builder.add("LpuAttachedByDepartment.area_id", aLpuAreaId);
		if (!StringUtil.isNullOrEmpty(aLastname)) {
			StringTokenizer st = new StringTokenizer(aLastname, " \t;,.");
			if (st.hasMoreTokens())
				builder.addLike("lastname", st.nextToken() + "%");
			if (st.hasMoreTokens())
				builder.addLike("firstname", st.nextToken() + "%");
			if (st.hasMoreTokens())
				builder.addLike("middlename", st.nextToken() + "%");
		}

		if (aLpuId != null && aLpuId != 0 && ret.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb
					.append("SELECT patient.id, lastname, firstname, middlename 					");
			sb.append("       , MisLpu.name   					");
			sb.append("  FROM Patient, LpuAttachedByDepartment, MisLpu ");
			sb.append(" WHERE 													");
			sb
					.append("       MisLpu.id      = LpuAttachedByDepartment.lpu_id	");
			// sb.append(" AND LpuArea.id = LpuAttachedByDepartment.area_id ") ;
			sb
					.append("   AND Patient.id = LpuAttachedByDepartment.patient_id 	");
			// sb.append(" AND LpuAttachedByDepartment.lpu_id = :lpu ") ;
			// sb.append(" ORDER BY lastname, firstname ") ;

			Query query2 = builder.buildNative(theManager, sb.toString(),
					"ORDER BY lastname, firstname");

			// , "select p.lastname, p.firstname, from Patient p,
			// LpuAttachedByDepartment lpuat" +
			// " where p.lpu=lpuat.lpu"
			// , " order by p.lastname,p.firstname");
			// appendToList(query2, ret);
			// Query query2 = theManager.createNativeQuery(sb.toString());
			// query2.setParameter("lpu", aLpuId);
			// List l = query2.getResultList();
			appendNativeToList(query2, ret);

		}
		
		// поиск по полису
		if(!StringUtil.isNullOrEmpty(aLastname) && ret.isEmpty()) {
			appendNativeToList(findByMedCardNumber(aLastname), ret);
		}
		// Поиск по коду синхронизации
		if(!StringUtil.isNullOrEmpty(aLastname) && ret.isEmpty()) {
			appendNativeToList(findByPatientSync(aLastname), ret);
		}
		if(!StringUtil.isNullOrEmpty(aLastname) && ret.isEmpty()) {
			appendNativeToList(findByPolicy(aLpuId, aLpuAreaId, aLastname), ret);
		}
		return ret;
		// new query
		// StringBuilder sb = new StringBuilder() ;
		// sb.append("select OBJECT(p) from Patient p"
		// +" left join p.attachedByDepartments lpuat "
		// +" where p.lastname like 'ТЕС%' "
		// +" and lpuat.lpu=1113654"
		// ) ;
		// return createList(theManager.createQuery(sb.toString()));
	}

	private Query findByPolicy(Long aLpuId, Long aLpuAreaId, String aPolicyQuery) {
		QueryClauseBuilder b = new QueryClauseBuilder() ;
		String query = 
			  "select Patient.id, Patient.lastname, Patient.firstname, Patient.middlename, Patient.birthday from MedPolicy " 
			+ " inner join Patient on MedPolicy.patient_id = Patient.id"
			+ " where"
			;
		//b.add("MedPolicy.patient.lpu_id", aLpuId) ;
		//b.add("MedPolicy.patient.lpuArea_id", aLpuAreaId) ;
		StringTokenizer st = new StringTokenizer(aPolicyQuery, " ") ;
		String series = st.hasMoreTokens() ? st.nextToken() : null ;
		String number = st.hasMoreTokens() ? st.nextToken() : null ;
		if(number==null) {
			number = series ;
			series = null ;
		}
		b.add("MedPolicy.series", series);
		b.add("MedPolicy.polNumber", number);
		return b.buildNative(theManager, query, "") ;//order by MedPolicy.patient.lastname, MedPolicy.patient.firstname");
		// from MedPolicy where series = :series and 
	}

	private Query findByMedCardNumber(String aPolicyQuery) {
		QueryClauseBuilder b = new QueryClauseBuilder() ;
		String query = 
				"select Patient.id, Patient.lastname, Patient.firstname, Patient.middlename, Patient.birthday from Medcard " 
						+ " inner join Patient on Medcard.person_id = Patient.id"
						+ " where"
						;
		//b.add("MedPolicy.patient.lpu_id", aLpuId) ;
		//b.add("MedPolicy.patient.lpuArea_id", aLpuAreaId) ;
		StringTokenizer st = new StringTokenizer(aPolicyQuery, " ") ;
		String number = st.hasMoreTokens() ? st.nextToken() : null ;
		b.add("\"number\"", number);
		return b.buildNative(theManager, query, "") ;//order by MedPolicy.patient.lastname, MedPolicy.patient.firstname");
		// from MedPolicy where series = :series and 
	}
	private Query findByPatientSync(String aPolicyQuery) {
		QueryClauseBuilder b = new QueryClauseBuilder() ;
		String query = 
			  "select id, lastname, firstname, middlename, birthday from Patient "
			+ " where"
			;
		//b.add("MedPolicy.patient.lpu_id", aLpuId) ;
		//b.add("MedPolicy.patient.lpuArea_id", aLpuAreaId) ;
		StringTokenizer st = new StringTokenizer(aPolicyQuery, " ") ;
		String number = st.hasMoreTokens() ? st.nextToken() : null ;
		b.add("patientSync", number);
		return b.buildNative(theManager, query, "") ;//order by MedPolicy.patient.lastname, MedPolicy.patient.firstname");
		// from MedPolicy where series = :series and 
	}
	
	@SuppressWarnings("unchecked")
	private void appendNativeToList(Query aQuery, List<PatientForm> ret) {
		List<Object[]> list = aQuery.setMaxResults(50).getResultList();
		for (Object[] arr : list) {
			PatientForm f = new PatientForm();
			f.setId(((Number) arr[0]).longValue());
			f.setLastname((String) arr[1]);
			f.setFirstname((String) arr[2]);
			f.setMiddlename((String) arr[3]);
			if(arr[4]!=null) {
				f.setBirthday(DateFormat.formatToDate((java.util.Date) arr[4])) ;
			}
			ret.add(f);
		}

	}

	@SuppressWarnings("unchecked")
	private List<PatientForm> appendToList(Query aQuery, List<PatientForm> ret) {
		List<Patient> list = aQuery.setMaxResults(50).getResultList();

		for (Patient patient : list) {
			try {
				ret.add(theEntityFormService.loadForm(PatientForm.class,
						patient));
			} catch (EntityFormException e) {
				throw new IllegalStateException(e);
			}
		}
		return ret;
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
			SimpleDateFormat dat = new SimpleDateFormat("yyyy-MM-dd") ;
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
		StringBuilder sql = new StringBuilder() ;
		aFirstname = aFirstname.toUpperCase().trim() ;
		aMiddlename = aMiddlename.toUpperCase().trim() ;
		aLastname = aLastname.toUpperCase().trim() ;
		String birthyear = aBirthday.substring(6) ;
		System.out.println("birthyear="+birthyear) ;
		sql.append("")
			.append(" from Patient p")
			.append(" where (")
			.append(" (UPPER(p.lastname) =:lastname and UPPER(p.firstname) = :firstname and UPPER(p.middlename)=:middlename and to_char(p.birthday,'yyyy')=:birthyear)") ;
		if (aSnils!=null && !aSnils.equals("") && !aSnils.equals("999-999-999 99")) {
			sql.append(" or (p.snils='").append(aSnils).append("')") ;
		}
		if (aPassportNumber!=null && !aPassportNumber.equals("") && aPassportSeries!=null && !aPassportSeries.equals("") ) {
			sql.append(" or (p.passportNumber='").append(aPassportNumber).append("' and p.passportSeries='").append(aPassportSeries).append("')") ;
		}
		
		sql.append(")") ;
			
		if (aId!=null && !aId.equals("")) {
			sql.append(" and p.id!='").append(aId).append("'") ;
		}
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
			for (Object[] res:doubles) {
				ret.append("<li>Количество найденных дублей: <b>").append(cntdoubles).append("</b></li>") ;
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
