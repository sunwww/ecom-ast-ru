package ru.ecom.mis.ejb.service.medcase;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;
import org.jdom.IllegalDataException;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.expomc.ejb.domain.med.VocDiagnosis;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameter;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentParameterConfig;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.ExtHospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCaseMedPolicy;
import ru.ecom.mis.ejb.domain.medcase.SurgicalOperation;
import ru.ecom.mis.ejb.domain.medcase.hospital.TemperatureCurve;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAcuityDiagnosis;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPrimaryDiagnosis;
import ru.ecom.mis.ejb.domain.patient.MedPolicy;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.form.medcase.hospital.ExtHospitalMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.SurgicalOperationForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.StatisticStubStac;
import ru.ecom.mis.ejb.form.patient.MedPolicyForm;
import ru.ecom.mis.ejb.service.patient.QueryClauseBuilder;
import ru.ecom.poly.ejb.services.GroupByDate;
import ru.ecom.poly.ejb.services.MedcardServiceBean;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;
/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 26.01.2007
 */
@Stateless
@Remote(IHospitalMedCaseService.class)
@SecurityDomain("other")
public class HospitalMedCaseServiceBean implements IHospitalMedCaseService {
	
    private final static Logger LOG = Logger.getLogger(MedcardServiceBean.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    public void createNewDiary(String aTitle, String aText, String aUsername) {
    	TemplateProtocol protocol = new TemplateProtocol() ;
    	protocol.setText(aText) ;
    	protocol.setTitle(aTitle) ;
    	protocol.setUsername(aUsername) ;
    	protocol.setDate(new java.sql.Date(new java.util.Date().getTime()));
    	theManager.persist(protocol) ;
    }
	public void updateDataFromParameterConfig(Long aDepartment, boolean aIsLowerCase, String aIds, boolean aIsRemoveExist) {
		String[] obj = aIds.split(",") ;
		String aTableSql = "VocDocumentParameterConfig where department_id='"+aDepartment+"' and documentParameter_id " ;
		MisLpu department = theManager.find(MisLpu.class, aDepartment) ;
		for (int i = 0; i < obj.length; i++) {
			String jsId = obj[i];
			if (jsId!=null && jsId!="" || jsId=="0") {
				//System.out.println("    id="+jsonId) ;
				
				Long jsonId=java.lang.Long.valueOf(jsId) ;
				
				String sql ="from "+aTableSql+" ='"+jsonId+"' " ;
				List<VocDocumentParameterConfig> count = theManager.createQuery(sql).setMaxResults(1).getResultList() ;
				VocDocumentParameterConfig vdpc ;
				if (count.isEmpty()) {
					VocDocumentParameter documentParameter = theManager.find(VocDocumentParameter.class, jsonId) ;
					vdpc = new VocDocumentParameterConfig() ; 
					vdpc.setDepartment(department) ;
					vdpc.setDocumentParameter(documentParameter) ;
				} else {
					vdpc = count.get(0) ;
				}
				vdpc.setIsLowerCase(aIsLowerCase) ;
				theManager.persist(vdpc) ;
			}
		}
		if (aIsRemoveExist && aIds.length()>0 ) {
			String sql = "delete "+aTableSql+" not in ("+aIds+") " ;
			theManager.createNativeQuery(sql).executeUpdate();
		} else {
		}
	}
	public void removeDataFromParameterConfig(Long aDepartment, String aIds) {
		String aTableSql = "VocDocumentParameterConfig where department_id='"+aDepartment+"' and documentParameter_id " ;
		String sql = "delete from "+aTableSql+" in ("+aIds+") " ;
		theManager.createNativeQuery(sql).executeUpdate();
	}
	
	
    public void changeServiceStreamBySmo(Long aSmo,Long aServiceStream) {
    	List<Object[]> list = theManager.createNativeQuery("select sls.dtype,count(distinct slo.id) from medcase sls left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase' where sls.id="+aSmo+" group by sls.id,sls.dtype").getResultList() ;
    	if (!list.isEmpty()) {
    		Object[] obj =list.get(0) ;
    		if (obj[0]!=null) {
    			String dtype=new StringBuilder().append(obj[0]).toString() ;
	    		if (dtype.equals("HospitalMedCase")) {
	    			List<Object[]> listBedFund = theManager.createNativeQuery("select slo.id as sloid,bfNew.id as bfNewid from MedCase slo" 
	    				+" left join BedFund bf on bf.id=slo.bedFund_id"
	    				+" left join BedFund bfNew on bfNew.lpu_id=bf.lpu_id"
	    				+" where slo.parent_id='"+aSmo+"' and slo.dtype='DepartmentMedCase'"
	    				+" and bfNew.bedSubType_id = bf.bedSubType_id"
	    				+" and bfNew.bedType_id = bf.bedType_id"
	    				+" and bfNew.serviceStream_id = '"+aServiceStream+"' and slo.dateStart between bfNew.dateStart and coalesce(bfNew.dateFinish,CURRENT_DATE)")
	    				.getResultList() ;
	    			Long cntSlo=ConvertSql.parseLong(obj[1]) ;
	    			if (cntSlo.intValue()==listBedFund.size()) {
	    				for (Object[] slo:listBedFund) {
	    					theManager.createNativeQuery("update MedCase set serviceStream_id='"+aServiceStream+"',bedFund_id='"+slo[1]+"' where id='"+slo[0]+"'").executeUpdate() ;
	    				}
	    				theManager.createNativeQuery("update MedCase set serviceStream_id='"+aServiceStream+"' where id='"+aSmo+"'").executeUpdate() ;
	    			} else {
	    				throw new IllegalArgumentException("По данному потоку обслуживания не во всех отделениях, в которых производилось лечение, заведен коечный фонд"); 
	    			}
	    		} else if (dtype.equals("DepartmentMedCase")) {
	    			List<Object[]> listBedFund = theManager.createNativeQuery("select slo.id as sloid,bfNew.id as bfNewid from MedCase slo" 
		    				+" left join BedFund bf on bf.id=slo.bedFund_id"
		    				+" left join BedFund bfNew on bfNew.lpu_id=bf.lpu_id"
		    				+" where slo.id='"+aSmo+"' and slo.dtype='DepartmentMedCase'"
		    				+" and bfNew.bedSubType_id = bf.bedSubType_id"
		    				+" and bfNew.bedType_id = bf.bedType_id"
		    				+" and bfNew.serviceStream_id = '"+aServiceStream+"' and slo.dateStart between bfNew.dateStart and coalesce(bfNew.dateFinish,CURRENT_DATE)")
		    				.getResultList() ;
	    			Object[] slo=listBedFund.get(0) ;
	    			if (listBedFund.size()==1) {
	    				theManager.createNativeQuery("update MedCase set serviceStream_id='"+aServiceStream+"',bedFund_id='"+slo[1]+"' where id='"+aSmo+"'").executeUpdate() ;
	    			} else {
	    				throw new IllegalArgumentException("По данному потоку обслуживания в отделение не заведен коечный фонд"); 
	    			}
	    		} else if (dtype.equals("Visit")) {
    				theManager.createNativeQuery("update MedCase set serviceStream_id='"+aServiceStream+"' where id='"+aSmo+"'").executeUpdate() ;	    			
	    		} else if (dtype.equals("PolyclinicMedCase")) {
    				theManager.createNativeQuery("update MedCase set serviceStream_id='"+aServiceStream+"' where parent_id='"+aSmo+"' and dtype='Visit'").executeUpdate() ;	    			
	    		}
    		} else {
    			
    		}
    	}
    }
    public void unionSloWithNextSlo(Long aSlo) {
    	List<Object[]> list = theManager.createNativeQuery("select  "
			+"case when sloNext1.department_id is not null and" 
			+" sloNext1.department_id=slo.department_id then '1' else null end equalsDep"
			+" ,sloNext.id as sloNext,sloNext1.id as sloNext1,sloNext2.id as sloNext2"
			+" ,sloNext.dateFinish as sloNextDateFinish,sloNext.dischargeTime as sloNextDischargeTime"
			+" ,sloNext.transferDate as sloNextTransferDate,sloNext.transferTime as sloNextTransferTime"
			+" ,sloNext1.dateFinish as sloNext1DateFinish,sloNext1.dischargeTime as sloNext1DischargeTime"
			+" ,sloNext1.transferDate as sloNext1TransferDate,sloNext1.transferTime as sloNext1TransferTime"

			+" from medcase slo"
			+" left join MedCase sloNext on sloNext.prevMedCase_id=slo.id"
			+" left join MedCase sloNext1 on sloNext1.prevMedCase_id=sloNext.id"
			+" left join MedCase sloNext2 on sloNext2.prevMedCase_id=sloNext1.id"
			+" where slo.id='"+aSlo+"'")
			.getResultList() ;
    	if (!list.isEmpty()) {
    		Object[] obj = list.get(0) ;
    		if (obj[1]!=null) {
	    		if (obj[0]!=null) {
	    			// Отд next1=current (объединять 2 отделения)
	    	    	theManager.createNativeQuery("update diary d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"'").executeUpdate() ;
	    	    	theManager.createNativeQuery("update diagnosis d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"'").executeUpdate() ;
	    	    	theManager.createNativeQuery("update SurgicalOperation d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"'").executeUpdate() ;
	    	    	theManager.createNativeQuery("update diary d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[2]+"'").executeUpdate() ;
	    	    	theManager.createNativeQuery("update diagnosis d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[2]+"'").executeUpdate() ;
	    	    	theManager.createNativeQuery("update SurgicalOperation d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[2]+"'").executeUpdate() ;
	    	    	theManager.createNativeQuery("update ClinicExpertCard d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[2]+"'").executeUpdate() ;
	    	    	theManager.createNativeQuery("update medcase set dateFinish=(select dateFinish from medcase where id='"+obj[2]+"') "
	    	    			+" ,transferDate=(select transferDate from medcase where id='"+obj[2]+"')"
	    	    			+" ,transferTime=(select transferTime from medcase where id='"+obj[2]+"')"
	    	    			+" ,dischargeTime=(select dischargeTime from medcase where id='"+obj[2]+"')"
	    	    			+" ,transferDepartment_id=(select transferDepartment_id from medcase where id='"+obj[2]+"')"
	    	    			+" ,targetHospType_id=(select targetHospType_id from medcase where id='"+obj[2]+"')"
	    	    			+" where id='"+aSlo+"'").executeUpdate() ;
	    	    	if (obj[3]!=null) {
	    	    		theManager.createNativeQuery("update MedCase set prevMedCase_id='"+aSlo+"' where id='"+obj[3]+"'").executeUpdate() ;
	    	    	}
	    	    	theManager.createNativeQuery("delete from medcase m where m.id='"+obj[2]+"'").executeUpdate() ;
	    	    	theManager.createNativeQuery("delete from medcase m where m.id='"+obj[1]+"'").executeUpdate() ;
	    		} else {
	    			//
	    	    	theManager.createNativeQuery("update diary d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"'").executeUpdate() ;
	    	    	theManager.createNativeQuery("update diagnosis d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"'").executeUpdate() ;
	    	    	theManager.createNativeQuery("update SurgicalOperation d set medcase_id='"+aSlo+"' where d.medCase_id='"+obj[1]+"'").executeUpdate() ;
	     	    	theManager.createNativeQuery("update medcase set dateFinish=(select dateFinish from medcase where id='"+obj[1]+"') "
	    	    			+" ,transferDate=(select transferDate from medcase where id='"+obj[1]+"')"
	    	    			+" ,transferTime=(select transferTime from medcase where id='"+obj[1]+"')"
	    	    			+" ,dischargeTime=(select dischargeTime from medcase where id='"+obj[1]+"')"
	    	    			+" ,transferDepartment_id=(select transferDepartment_id from medcase where id='"+obj[1]+"')"
	    	    			+" ,targetHospType_id=(select targetHospType_id from medcase where id='"+obj[1]+"')"
	    	    			+" where id='"+aSlo+"'").executeUpdate() ;
	    	    	if (obj[2]!=null) {
	    	    		theManager.createNativeQuery("update MedCase set prevMedCase_id='"+aSlo+"' where id='"+obj[2]+"'").executeUpdate() ;
	    	    	}
	    	    	theManager.createNativeQuery("delete from medcase m where m.id='"+obj[1]+"'").executeUpdate() ;
	    		}
    		} else {
    			throw new IllegalArgumentException("Нет след. СЛО"); 
    		}
    	}
    }
    public void deniedHospitalizatingSls(Long aMedCaseId, Long aDeniedHospitalizating) {
    	theManager.createNativeQuery("update diary d set medcase_id='"+aMedCaseId+"' where d.medCase_id in (select m.id from medcase m where m.parent_id='"+aMedCaseId+"')").executeUpdate() ;
    	theManager.createNativeQuery("update diagnosis d set medcase_id='"+aMedCaseId+"' where d.medCase_id in (select m.id from medcase m where m.parent_id='"+aMedCaseId+"')").executeUpdate() ;
    	theManager.createNativeQuery("update SurgicalOperation d set medcase_id='"+aMedCaseId+"' where d.medCase_id in (select m.id from medcase m where m.parent_id='"+aMedCaseId+"')").executeUpdate() ;
    	theManager.createNativeQuery("delete from medcase m where m.parent_id='"+aMedCaseId+"' and m.dtype='DepartmentMedCase'").executeUpdate() ;
    	theManager.createNativeQuery("update medcase set deniedHospitalizating_id='"+aDeniedHospitalizating+"',ambulanceTreatment='1' where id='"+aMedCaseId+"'").executeUpdate() ;
    	HospitalMedCase medCase = theManager.find(HospitalMedCase.class, aMedCaseId) ;
    	StatisticStubStac.removeStatCardNumber(theManager, theContext,medCase);
    }
    public void preRecordDischarge(Long aMedCaseId, String aDischargeEpicrisis) {
    	HospitalMedCase sls = theManager.find(HospitalMedCase.class, aMedCaseId) ;
    	sls.setDischargeEpicrisis(aDischargeEpicrisis) ;
    	theManager.persist(sls) ;
    }
    public void updateDischargeDateByInformationBesk(String aIds, String aDate) throws ParseException {
    	String[] ids = aIds.split(",") ;
    	//Date date = DateFormat.parseSqlDate(aDate) ;
    	
    	for (String id :ids) {
    		Object cnt = theManager.createNativeQuery("select count(*) from medcase where id='"+id+"' and dateStart<=to_date('"+aDate+"','dd.mm.yyyy') and dischargeTime is null and deniedHospitalizating_id is null")
    		//.setParameter("dat", date)
    		.getSingleResult() ;
    		Long cntL = ConvertSql.parseLong(cnt) ;
    		if (cntL>Long.valueOf(0)) {
        		theManager.createNativeQuery("update MedCase set dateFinish=to_date('"+aDate+"','dd.mm.yyyy') where id='"+id+"' and dtype='HospitalMedCase' and dischargeTime is null")
    				//.setParameter("dat", date)
    				.executeUpdate() ;
    		} else {
        		List<Object[]> list = theManager.createNativeQuery("select p.lastname||' '||p.firstname||' '||coalesce(p.middlename)||' '||to_char(p.birthday,'dd.mm.yyyy'),ss.code,case when sls.deniedHospitalizating_id is not null then 'при отказе от госпитализации дата выписки не ставится' when sls.dischargeTime is not null then 'Изменение даты выписки у оформленных историй болезни производится через выписку' when sls.dateStart>to_date('"+aDate+"','dd.mm.yyyy') then 'Дата выписки должна быть больше, чем дата поступления' else '' end from medcase sls left join patient p on p.id=sls.patient_id left join statisticstub ss on ss.id=sls.statisticstub_id where sls.id='"+id+"'")
        	    		//.setParameter("dat", date)
        	    		.getResultList() ;
        		if (list.size()>0) {
        			Object[] objs = list.get(0) ;
        			throw new IllegalArgumentException(
        					new StringBuilder().append("Пациент:").append(objs[0])
        					.append(objs[1]!=null?" стат.карта №"+objs[1]:"")
        					.append(" ОШИБКА:").append(objs[2]).toString() 
        					); 
        		}
    		}
    	}
    	//return "" ;
    }
    public String addressInfo(EntityManager aManager,Long aAddressId, Object[] aAddress) {
        StringBuilder sb = new java.lang.StringBuilder();
        String fullname = new StringBuilder().append(aAddress[1]).toString().trim() ;
        
        //Address a = aAddress ;
        //Long id = a.getId() ;
        if (aAddress[1]!=null && !fullname.equals("")) return fullname;
        
        sb.insert(0, aAddress[2]) ;
        if (aAddress[3]!=null) {
        	String shortName = aAddress[3]+" " ;
            sb.insert(0,  shortName) ;
        	
        }
        
        //long oldId = a.getId() ;
        ////a = a.getParent() ;
        
        //sb.insert(0, aAddress[2]) ;
        //System.out.println(aAddress) ;
            if(aAddress[4]!=null) {
            	Long id1 = ConvertSql.parseLong(aAddress[4]) ;
                //System.out.println("parent="+id1) ;
            	List<Object[]> list = theManager.createNativeQuery("select a.addressid,a.fullname,a.name,att.shortName,a.parent_addressid from address2 a left join AddressType att on att.id=a.type_id where a.addressid="+id1+" order by a.addressid")
        				.setMaxResults(1)	
        				.getResultList() ;
            	if (list.size()>0) {
            		
                	sb.insert(0, ", ") ;
                	sb.insert(0, addressInfo(aManager,id1,list.get(0))) ;
            	}
            }
        
        
        //throw "address"+aAddress.id+" "+sb ;
        //throw sb.toString() ;
        //aAddress.setFullname(sb.toString()) ;
        
        //theManager.persist(aAddress) ;
        Address a =theManager.find(Address.class, aAddressId) ;
        a.setFullname(sb.toString()) ;
        theManager.persist(a) ;
    	//aManager.createNativeQuery("update Address2 set fullname='"+sb.toString().trim()+"' where addressid="+aAddressId).executeUpdate() ;
        return sb.toString().trim() ;
    }

    public void addressClear() {
    	theManager.createNativeQuery("update Address2 set fullname=null").executeUpdate() ;
    }
    public long addressUpdate(long id) {
    	//long id=0 ;
    	List<Object[]> list ;

    		//String sql = "from Address where id>"+id+" and fullname is null order by id" ;
    		//if (id>0) throw sql ;
    		//list = theManager.createQuery(sql)
    		//	.setMaxResults(450)
    		//	.getResultList() ;
    		list = theManager.createNativeQuery("select a.addressid,a.fullname,a.name,att.shortName,a.parent_addressid from address2 a left join AddressType att on att.id=a.type_id where a.addressid>"+id+" and a.fullname is null order by a.addressid")
    				.setMaxResults(450)	
    				.getResultList() ;
    		if (list.size()>0) {
    			for (Object[] adr:list) {
    				
    				//Address adr = list.get(i) ;
    				id = ConvertSql.parseLong(adr[0]);
    				addressInfo(theManager,id,adr) ;
    				//adr.setFullname() ;
    				//aCtx.manager.persist(adr) ;
    				
    				//throw id ;
    			}
    			//list.clear() ;
    		} else {
    			id=-1 ;
    		}
    		
    	return id ;
    }
    
    public String getIdc10ByDocDiag(Long aIdDocDiag){
    	VocDiagnosis diag = theManager.find(VocDiagnosis.class, aIdDocDiag) ;
    	VocIdc10 mkb = diag.getIdc10() ;
    	if (mkb!=null) {
    		return new StringBuilder().append(mkb.getId()).append("#").append(mkb.getCode()).append("#").append(mkb.getName()).toString() ;
    	}
    	return "" ;
    }
    public String getOperationsText(Long aPatient, String aDateStart
    		,String aDateFinish) {
    	if (aDateFinish==null || aDateFinish.equals("")) {
    		aDateFinish = "CURRENT_DATE";
    	} else {
    		aDateFinish = new StringBuilder().append("to_date('").append(aDateFinish).append("','dd.mm.yyyy')").toString();
    	}
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select to_char(operationDate,'DD.MM.YYYY') as operDate1,cast(operationTime as varchar(5)) as timeFrom,cast(operationTimeTo as varchar(5)) as timeTo,vo.name as voname from SurgicalOperation so left join MedService vo on vo.id=so.operation_id where so.patient_id='")
    		.append(aPatient)
    		.append("' and so.operationDate between to_date('").append(aDateStart).append("','dd.mm.yyyy') and ").append(aDateFinish).append(" order by so.operationDate") ;
    	List<Object[]> opers = theManager.createNativeQuery(sql.toString()).getResultList() ;
    	StringBuilder res = new StringBuilder() ;
    	for (Object[] obj :opers) {
    		if (res.length()>0) res.append("; ") ;
    		res.append("").append(obj[3]).append(" ").append(obj[0]).append(" ").append(obj[1]).append("-").append(obj[2]) ;
    		
    	}
    	return res.toString() ;
    }
    public String getnvestigationsTextDTM(Long aPatient, String aDateStart
			,String aDateFinish,boolean aLabsIs,boolean aFisioIs,boolean aFuncIs,boolean aConsIs
			, boolean aLuchIs) {
    	try {
	    	if (aDateFinish==null || aDateFinish.equals("")) {
	    		aDateFinish = "CURRENT_DATE";
	    	} else {
	    		aDateFinish = new StringBuilder().append("convert(DATE,'").append(aDateFinish).append("',104)").toString();
	    	}
	    	StringBuilder sql = new StringBuilder() ;
	    	sql.append("select top 1 code,$$getUslByPatient^ZLinkPol('")
	    		.append(aPatient)
	    		.append("',TO_DATE(convert(DATE,'").append(aDateStart).append("',104),'YYYYMMDD'),TO_DATE(").append(aDateFinish).append(",'YYYYMMDD')")
	    		.append(",").append(aLabsIs?1:0)
	    		.append(",").append(aFisioIs?1:0)
	    		.append(",").append(aFuncIs?1:0)
	    		.append(",").append(aConsIs?1:0)
	    		.append(",").append(aLuchIs?1:0)
	    		.append(") ")
	    		
	    		.append("from VocSex") ;
	    	System.out.println(sql) ;
	    	List<Object[]> usls  = theManager.createNativeQuery(sql.toString()).getResultList() ;
	    	
	    	return usls.size()>0?new StringBuilder().append(usls.get(0)[1]).toString():"" ;
    	} catch (Exception e) {
    		return "" ;
    	}
    }
    
    public String getTypeDiagByAccoucheur() {
    	StringBuilder ret= new StringBuilder() ;
    	List<VocPrimaryDiagnosis> prDiag = theManager.createQuery("from VocPrimaryDiagnosis where code=1").getResultList();
    	List<VocAcuityDiagnosis> actDiag = theManager.createQuery("from VocAcuityDiagnosis where code=1 or omcCode=1").getResultList();
    	List<VocDiagnosisRegistrationType> regTypeDiag = theManager.createQuery("from VocDiagnosisRegistrationType where code=4").getResultList();
    	if (prDiag.size()>0) {
    		VocPrimaryDiagnosis pr = prDiag.get(0) ;
    		ret.append(pr.getId()).append("#").append(pr.getName()).append("#") ;
    	} else {
    		ret.append("##") ;
    	}
    	if (actDiag.size()>0) {
    		VocAcuityDiagnosis act = actDiag.get(0) ;
    		ret.append(act.getId()).append("#").append(act.getName()).append("#") ;
    	} else {
    		ret.append("##") ;
    	}
    	if (regTypeDiag.size()>0) {
    		VocDiagnosisRegistrationType regType = regTypeDiag.get(0) ;
    		ret.append(regType.getId()).append("#").append(regType.getName()) ;
    	} else {
    		ret.append("#") ;
    	}
    	return ret.toString();
    }
    
    public String findDoubleServiceByPatient(Long aMedService, Long aPatient, Long aService, String aDate) throws ParseException {
    	StringBuilder sql = new StringBuilder() ;
    	Date date=DateFormat.parseSqlDate(aDate) ;
    	sql.append("select smc.id,convert(varchar(20),smc.dateExecute,104),smc.timeExecute,vss.name,'Оказана в '|| case when p.DTYPE='DepartmentMedCase' then ' отделении '||d.name when p.DTYPE='HospitalMedCase' then 'приемном отделении ' else 'поликлинике' end from medcase as smc ")
    			.append(" left join medcase as p on p.id=smc.parent_id ")
    			.append(" left join mislpu as d on d.id=p.department_id ")
    			.append(" left join vocservicestream as vss on vss.id=smc.servicestream_id")
    			.append(" where smc.patient_id=:pat and smc.DTYPE='ServiceMedCase' and smc.medService_id=:usl and smc.dateExecute=:dat") ;
    	System.out.println("sql="+sql) ;
    	System.out.println("pat="+aPatient) ;
    	System.out.println("medservice="+aMedService) ;
    	System.out.println("service="+aService) ;
    	System.out.println("date="+aDate) ;
    	if (aMedService!=null && aMedService>0) {
    		sql.append(" and smc.id!='").append(aMedService).append("'") ;
    	}
    	
    	List<Object[]> doubles = theManager.createNativeQuery(
				sql.toString())
				.setParameter("pat", aPatient)
				.setParameter("usl", aService)
				.setParameter("dat", date)
				.getResultList() ;
		
		if (doubles.size()>0) {
			StringBuilder ret = new StringBuilder() ;
			ret.append("<br/><ol>") ;
			for (Object[] res:doubles) {
				ret.append("<li>")
				.append("<a href='entitySubclassView-mis_medCase.do?id=").append(res[0]).append("'>")
				.append(res[1]).append(" ").append(res[2]).append(" ").append(res[3]).append(" ").append(res[4])
				.append("</a>")
				.append("</li>") ;
			}
			ret.append("</ol><br/>") ;
			return ret.toString() ;
		} else {
			return null ;
		}
    	
    	
    }
    public String findDoubleOperationByPatient(Long aSurOperation, Long aParentMedCase, Long aOperation, String aDate) throws ParseException {
    	StringBuilder sql = new StringBuilder() ;
    	Date date=DateFormat.parseSqlDate(aDate) ;
    	sql.append("select so.id,to_char(so.operationDate,'dd.mm.yyyy'),so.operationTime,to_char(so.operationDateTo,'dd.mm.yyyy'),so.operationTimeTo,'Зарегистрирована в '|| case when p.DTYPE='DepartmentMedCase' then ' отделении '||d.name when p.DTYPE='HospitalMedCase' then 'приемном отделении ' else 'поликлинике' end ")
    			.append(" from medcase as mc")
    			.append(" left join SurgicalOperation as so on so.patient_id=mc.patient_id")
    			.append(" left join medcase as p on p.id=so.medcase_id ")
    			.append(" left join mislpu as d on d.id=p.department_id ")
//    			.append(" left join vocservicestream as vss on vss.id=so.servicestream_id")
    			.append(" where mc.id=:mcid and so.medService_id=:usl and so.operationDate=:dat") ;
    	//System.out.println("sql="+sql) ;
    	//System.out.println("parentmedcase="+aParentMedCase) ;
    	//System.out.println("suroperation="+aSurOperation) ;
    	//System.out.println("operation="+aOperation) ;
    	//System.out.println("date="+aDate) ;
    	if (aSurOperation!=null && aSurOperation>0) {
    		sql.append(" and so.id!='").append(aSurOperation).append("'") ;
    	}
    	
    	List<Object[]> doubles = theManager.createNativeQuery(
				sql.toString())
				.setParameter("mcid", aParentMedCase)
				.setParameter("usl", aOperation)
				.setParameter("dat", date)
				.getResultList() ;
		
		if (doubles.size()>0) {
			StringBuilder ret = new StringBuilder() ;
			ret.append("<br/><ol>") ;
			for (Object[] res:doubles) {
				ret.append("<li>")
				.append("<a href='entitySubclassView-mis_medCase.do?id=").append(res[0]).append("'>")
				.append(res[1]).append(" ").append(res[2]).append("-").append(res[3]).append(" ").append(res[4]).append(" ").append(res[5])
				.append("</a>")
				.append("</li>") ;
			}
			ret.append("</ol><br/>") ;
			return ret.toString() ;
		} else {
			return null ;
		}
    }
    public String deleteDataDischarge(Long aMedCaseId) {
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("update MedCase set dischargeTime=null,dateFinish=null")
    		.append(" where (id=:idMC and DTYPE='HospitalMedCase')")
    		.append(" or (parent_id=:idMC and DTYPE='DepartmentMedCase' and dateFinish is not null)");
    	LOG.info("SQL delete discharge: "+sql) ;
    	int result = theManager.createNativeQuery(sql.toString()).setParameter("idMC", aMedCaseId).executeUpdate() ;
    	return "Запрос выполнен: "+result ;
    }
    
    public List<HospitalMedCaseForm>findSlsByStatCard(String aNumber) {
    	 if (CAN_DEBUG) {
             LOG.debug("findStatCard Number = " + aNumber );
         }
 		//Patient patient = theManager.find(Patient.class, aParentId) ;
 		StringBuilder query = new StringBuilder() ;
 		query.append("from HospitalMedCase c")
 		.append(" where statisticStub.code like :number order by dateStart");
 		 Query query2 = theManager.createQuery(query.toString()) ;
          query2.setParameter("number", "%"+aNumber+"%") ;
          System.out.println("Запрос по medCase: ");
	        System.out.println(query.toString()) ;
	        return createHospitalList(query2);
    }
    public String getRW(long aIdSls) {
    	HospitalMedCase hospital = theManager.find(HospitalMedCase.class, aIdSls) ;
    	StringBuilder ret = new StringBuilder() ;
    	if(hospital.getRwDate()!=null) ret.append(DateFormat.formatToDate(hospital.getRwDate())) ;
    	ret.append("#") ;
    	if (hospital.getRwNumber()!=null) ret.append(hospital.getRwNumber()) ;
    	return ret.toString() ;
    }
    public void setRW(long aIdSls, String aRwDate, String aRwNumber) throws ParseException {
    	HospitalMedCase hospital = theManager.find(HospitalMedCase.class, aIdSls) ;
    	hospital.setRwNumber(aRwNumber);
    	hospital.setRwDate(DateFormat.parseSqlDate(aRwDate)) ;
    	theManager.persist(hospital) ;
    }
    public void setPatientByExternalMedservice(String aNumberDoc, String aOrderDate, String aPatient) {
    	theManager.createNativeQuery("update Document set patient_id='"+aPatient+"' where NumberDoc='"+aNumberDoc+"' and OrderDate=to_date('"+aOrderDate+"','dd.mm.yyyy')").executeUpdate() ;
    }

    /**
     * Есть ли открытый случай лечения в отделении
     * @param aIdSls
     * @return
     */
    public String isOpenningSlo(long aIdSls) {
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select mc.id,ml.name from MedCase as mc")
    			.append(" left join MisLpu as ml on ml.id=mc.department_id")
    			.append( " where mc.parent_id=:idsls and mc.DTYPE='DepartmentMedCase' and mc.dateFinish is null and mc.transferDate is null") ;
    	List<Object[]> list = theManager.createNativeQuery(sql.toString()).setParameter("idsls", aIdSls).getResultList() ;
    	if (list.size()>0) {
    		StringBuilder ret = new StringBuilder() ;
    		Object[] row = list.get(0) ;
    		ret.append(" СЛО №").append(row[0]).append(" отделение: ").append(row[1]) ;
    		return ret.toString() ;
    	}
    	return "" ;
    	
    }
    public Long getPatient(long aIdSsl) {
        HospitalMedCase hospital = theManager.find(HospitalMedCase.class, aIdSsl) ;
        if(hospital==null) throw new IllegalArgumentException("Нет стационарного случая лечения с ИД "+aIdSsl) ;
        return hospital.getPatient().getId() ;
    }
    
    public String getChangeStatCardNumber(Long aMedCase, String aNewStatCardNumber, boolean aAlwaysCreate){
    	HospitalMedCase hospital = theManager.find(HospitalMedCase.class,aMedCase);
    	try {
    		if (!aAlwaysCreate) {
    			if (hospital.getDeniedHospitalizating()!=null) {
    				throw new IllegalArgumentException("Нельзя изменить номер стат.карты при отказе госпитализации");
    			}
    		}
    		StatisticStubStac.changeStatCardNumber(aMedCase, aNewStatCardNumber, theManager, theContext);
    	} catch(Exception e) {
    		throw new IllegalArgumentException(e.getMessage());
    	}
		return hospital.getStatCardNumber();
	}
	
	public Collection<MedPolicyForm> listPolicies(Long aMedCase) {
		HospitalMedCase hospital = theManager.find(HospitalMedCase.class,aMedCase);
		//List<MedCaseMedPolicy> listPolicies = hospital.getPolicies() ;
		List<MedPolicy> listPolicies =new ArrayList<MedPolicy>() ;
		for (MedCaseMedPolicy mp : hospital.getPolicies()) {
			listPolicies.add(mp.getPolicies()) ;
		}
		return convert(listPolicies);
	}

	public Collection<MedPolicyForm> listPoliciesToAdd(Long aMedCase) {
		HospitalMedCase hospital = theManager.find(HospitalMedCase.class,aMedCase);
		List<Object[]> listPolicies = theManager.createNativeQuery("select p.id,count(case when mp.medCase_id='"
				+aMedCase
				+"' then 1 else null end) from MedPolicy p left join MedCase_MedPolicy mp on p.id=mp.policies_id left join MedCase m on m.id=mp.medCase_id where p.patient_id='"
				+hospital.getPatient().getId()+"' group by p.id")
				.getResultList();
		//List<MedPolicy> allPolicies = theManager.createQuery("from MedPolicy where patient_id=:pat").setParameter("pat", hospital.getPatient().getId()).getResultList();
		List<MedPolicy> allPolicies = new ArrayList<MedPolicy>() ;
		for (Object[] obj:listPolicies) {
			Long cnt = ConvertSql.parseLong(obj[1]) ;
			if (cnt==null || cnt.equals(Long.valueOf(0))) {
				Long pId = ConvertSql.parseLong(obj[0]) ;
				MedPolicy p = theManager.find(MedPolicy.class, pId) ;
				allPolicies.add(p) ;
			}
			//allPolicies.remove(pol);
		}
		return convert(allPolicies);
	}
	
	private static Collection<MedPolicyForm> convert(Collection<MedPolicy> aPolicies) {
		LinkedList<MedPolicyForm> list = new LinkedList<MedPolicyForm>();
		for (MedPolicy policy:aPolicies) {
			MedPolicyForm frm = new MedPolicyForm() ;
			frm.setId(policy.getId());
			frm.setActualDateFrom(DateFormat.formatToDate(policy.getActualDateFrom()));
			frm.setActualDateTo(DateFormat.formatToDate(policy.getActualDateTo()));
			frm.setText(policy.getText());
			list.add(frm);
		}
		return list;
		
	}
	
	public void addPolicies(long aMedCaseId, long[] aPolicies) {
		HospitalMedCase hospital = theManager.find(HospitalMedCase.class,aMedCaseId);
		List<MedCaseMedPolicy> listPolicies = hospital.getPolicies() ;
		for (long policyId: aPolicies) {
			
			MedPolicy policy= theManager.find(MedPolicy.class, policyId);
			System.out.println("adding="+policy.getId());
			if (!checkExistsAttachedPolicy(aMedCaseId, policyId)) {
				MedCaseMedPolicy mp = new MedCaseMedPolicy() ;
				mp.setMedCase(hospital) ;
				MedPolicy p = theManager.find(MedPolicy.class, policyId) ;
				mp.setPolicies(p) ;
				theManager.persist(mp) ;
			}
		}
		theManager.persist(hospital) ;
		//theManager.refresh(hospital);
	}
	private boolean checkExistsAttachedPolicy(long aMedCaseId,long aPolicy) {
		StringBuilder sql = new StringBuilder() ;
		sql.append(" select count(*) from MedCase_MedPolicy where medCase_id='")
			.append(aMedCaseId)
			.append("' and policies_id='").append(aPolicy).append("'") ;
		Object res= theManager.createNativeQuery(sql.toString()).getSingleResult() ;
		Long cnt=ConvertSql.parseLong(res) ;
		return cnt>Long.valueOf(0)?true:false ;
	}

	public void removePolicies(long aMedCaseId, long[] aPolicies) {
		//HospitalMedCase hospital = theManager.find(HospitalMedCase.class,aMedCaseId);
		//List<MedCaseMedPolicy> listPolicies = hospital.getPolicies() ;
		for (long policyId:aPolicies) {
			//MedPolicy policy= theManager.find(MedPolicy.class, policyId);
			//System.out.println("remove="+policy.getId());
			//listPolicies.remove(policy) ;
			theManager.createNativeQuery(new StringBuilder().append("delete from MedCase_MEdPolicy where medCase_id='")
			.append(aMedCaseId).append("' and policies_id='").append(policyId).append("'").toString()).executeUpdate();
		}
		
		//theManager.persist(hospital);
		//theManager.refresh(hospital);
		
	}
	
	public String getTemperatureCurve(long aMedCaseId) {
		HospitalMedCase hospital = theManager.find(HospitalMedCase.class,aMedCaseId);
		List<TemperatureCurve> list = hospital.getTemperatureCurves() ;
		StringBuilder json = new StringBuilder() ;
		json.append("{\"childs\":[") ;
		boolean isFirst = true ;
		for (TemperatureCurve curve:list) {
			if (!isFirst) {
				json.append(",") ;
				//isFirst =  ;
			} else {
				isFirst=false ;
			}
			json.append("{");
			if (curve.getTakingDate()!=null) {
				SimpleDateFormat FORMAT_1 = new SimpleDateFormat("yyyy/MM/dd");
				String date = FORMAT_1.format(curve.getTakingDate().getTime()) ;
				json.append("\"date\":")
						.append("\"")
						.append(date)
						.append(" ") ;
				if (curve.getDayTime()!=null && curve.getDayTime().getCode()!=null && curve.getDayTime().getCode().equals("2")) {
					json.append("20") ;
				} else {
					json.append("8") ;
				}
				json.append(":00")
						.append("\",") ;
						
				
			}
			json.append("\"id\":").append(curve.getId()).append(",") ;
			json.append("\"pulse\":")
				.append(curve.getPulse())
				.append(",");
			json.append("\"bloodPressureDown\":")
				.append(curve.getBloodPressureDown())
				.append(",");
			json.append("\"bloodPressureUp\":")
				.append(curve.getBloodPressureUp())
				.append(",");
			json.append("\"weight\":")
				.append(curve.getWeight())
				.append(",");
			json.append("\"respirationRate\":")
				.append(curve.getRespirationRate())
				.append(",");
			json.append("\"degree\":")
				.append(curve.getDegree())
				.append("}");
		}
		json.append("]}");
		// TODO Auto-generated method stub
		return json.toString();
	}
	
	public List<IEntityForm> listAll(Long aParentId) throws EntityFormException {
		Collection<HospitalMedCase> results = null ;
		//Patient patient = theManager.find(Patient.class, aParentId) ;
		StringBuilder query = new StringBuilder() ;
		query.append("from MedCase c where (DTYPE='HospitalMedCase' or DTYPE='ExtHospitalMedCase') and patient_id=:patient order by dateStart");
		 Query query2 = theManager.createQuery(query.toString()) ;
         query2.setParameter("patient", aParentId) ;
         results = query2.setMaxResults(1000).getResultList();
         //System.out.println("RESULT == "+results.size()) ;
         LinkedList<IEntityForm> ret = new LinkedList<IEntityForm>();
         for (HospitalMedCase hospit : results) {
             HospitalMedCaseForm form ;
        	 if (hospit instanceof ExtHospitalMedCase) {
        		 form = new ExtHospitalMedCaseForm() ;
			} else {
				form = new HospitalMedCaseForm() ;
			}
             form.setId(hospit.getId()) ;
             form.setIsDeniedHospitalizating(hospit.getIsDeniedHospitalizating()) ;
             form.setDateStart(DateFormat.formatToDate(hospit.getDateStart()));
             form.setDateFinish(DateFormat.formatToDate(hospit.getDateFinish()));
             //form.setFinishWorkerText(hospit.getFinishWorkerText());
             form.setUsername(hospit.getUsername());
             form.setDaysCount(hospit.getDaysCount()) ;
             form.setStatCardNumber(hospit.getStatCardNumber()) ;
             form.setEmergency(hospit.getEmergency());
             ret.add(form);
             
         }
        return ret;
	}
	
	public List<SurgicalOperationForm> getSurgicalOperationByDate(String aDate) {
        if (CAN_DEBUG) {
            LOG.debug("findAllSpecialistTickets() aSpecialist = " + aDate);
        }
        QueryClauseBuilder builder = new QueryClauseBuilder();
        //builder.add("operationDate", aDate);
        Query query = builder.build(theManager, "from SurgicalOperation where operationDate='"+aDate+"' ", " order by operationTime");
        return createList(query);
	}
    private List<SurgicalOperationForm> createList(Query aQuery) {
        List<SurgicalOperation> list = aQuery.getResultList();
        List<SurgicalOperationForm> ret = new LinkedList<SurgicalOperationForm>();
        for (SurgicalOperation surOper : list) {
            try {
                ret.add(theEntityFormService.loadForm(SurgicalOperationForm.class, surOper));
            } catch (EntityFormException e) {
                throw new IllegalStateException(e);
            }
        }
        return ret;
    }
    // Открытые случаи госпитализации по дате поступления
    public List<GroupByDate> findOpenHospitalGroupByDate() {
		StringBuilder sql = new StringBuilder();
		sql.append("select to_char(t.dateStart,'dd.mm.yyyy') as dateStart,count(t.id) as cnt1,count(t1.id) as cnt2,CURRENT_DATE-t.dateStart as cntDays from MedCase as t left join MedCase as t1 on t1.parent_id=t.id and t1.dateStart=t.dateStart where t.DTYPE='HospitalMedCase' and (cast(t.noActuality as int)=0 or t.noActuality is null) and t.dateFinish is null and t.deniedHospitalizating_id is null and (t.ambulanceTreatment is null or cast(t.ambulanceTreatment as int)=0) and t1.prevMEdCase_id is null group by t.dateStart order by t.dateStart") ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString())
				.getResultList() ;
		LinkedList<GroupByDate> ret = new LinkedList<GroupByDate>() ;
		long i =0 ;
		for (Object[] row: list ) {
			GroupByDate result = new GroupByDate() ;
			String date = new StringBuilder().append(row[0]).toString() ;
			result.setCnt(ConvertSql.parseLong(row[1])) ;
			result.setCnt1(ConvertSql.parseLong(row[2])) ;
			//result.setCnt2(ConvertSql.parseLong(row[3])) ;
			//result.setDate(date) ;
			result.setDateInfo(date) ;
			result.setComment(new StringBuilder().append(row[2]).toString()) ;
			result.setSn(++i) ;
			result.setCntDays(ConvertSql.parseLong(row[3]));
			ret.add(result) ;
		}
		return ret ;
	}
    
    public List<HospitalMedCaseForm> findOpenHospitalByDate(String aDate) {
	       QueryClauseBuilder builder = new QueryClauseBuilder();
	        Date date = null;
	        if(!StringUtil.isNullOrEmpty(aDate)) {
	            try {
	                date = DateFormat.parseSqlDate(aDate);
	            } catch (Exception e) {
	                LOG.warn("Ошибка преобразования даты "+aDate, e);
	            }
	        }
	        if (date != null){
	        	builder.add("dateStart", date);
	        } else {
	        	throw new IllegalDataException("Неправильная дата") ;
	        }
	        Query query = builder.build(theManager, "from MedCase where DTYPE='HospitalMedCase' and dateFinish is null  and deniedHospitalizating_id is null and (ambulanceTreatment is null or cast(ambulanceTreatment as int)=0)", " order by entranceTime");
	        System.out.println("Запрос по medCase: ");
	        System.out.println(query.toString()) ;
	        return createHospitalList(query);
	}
    
    private List<HospitalMedCaseForm> createHospitalList(Query aQuery) {
        List<HospitalMedCase> list = aQuery.getResultList();
        List<HospitalMedCaseForm> ret = new LinkedList<HospitalMedCaseForm>();
        for (HospitalMedCase medCase : list) {
            try {
                ret.add(theEntityFormService.loadForm(HospitalMedCaseForm.class, medCase));
            } catch (EntityFormException e) {
                throw new IllegalStateException(e);
            }
        }
        return ret;
    }
    
	@EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext EntityManager theManager ;
    @Resource SessionContext theContext ;



}
