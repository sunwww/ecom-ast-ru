package ru.ecom.mis.ejb.service.medcase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.Length;
import org.jboss.annotation.security.SecurityDomain;
import org.jdom.IllegalDataException;
import org.w3c.dom.Element;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.sequence.service.ISequenceService;
import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.ejb.xml.XmlUtil;
import ru.ecom.expomc.ejb.domain.med.VocDiagnosis;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.jaas.ejb.domain.SecPolicy;
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
import ru.ecom.mis.ejb.domain.report.AggregateHospitalReport;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.form.medcase.hospital.ExtHospitalMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.SurgicalOperationForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.StatisticStubStac;
import ru.ecom.mis.ejb.form.patient.MedPolicyForm;
import ru.ecom.mis.ejb.service.patient.QueryClauseBuilder;
import ru.ecom.poly.ejb.services.GroupByDate;
import ru.ecom.poly.ejb.services.MedcardServiceBean;
import ru.ecom.report.util.XmlDocument;
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
    
    public void refreshReportByPeriod(String aEntranceDate,String aDischargeDate,long aIdMonitor) {
    	IMonitor monitor = null;
    	try {
	    	monitor = theMonitorService.startMonitor(aIdMonitor, "Обработка данных за период: "+aEntranceDate+" "+aDischargeDate, 100);
	    	
	    	StringBuilder sqlD = new StringBuilder() ;
	    	sqlD.append(" delete from AggregateHospitalReport where entrancedate24 <= (to_date('").append(aDischargeDate).append("','dd.mm.yyyy')-1)") ;
	    	sqlD.append(" and (dischargedate24 >= (to_date('").append(aEntranceDate).append("','dd.mm.yyyy')-1) or dischargedate24 is null)") ;
	    	theManager.createNativeQuery(sqlD.toString()).executeUpdate() ;
	    	StringBuilder sql = new StringBuilder() ;
	    	sql.append(" select pat.id as f0patid") ;
	    	sql.append(" ,sls.id as f1slsid") ;
	    	sql.append(" ,slo.id as f2sloid") ;
	    	sql.append(" ,vs.omccode as f3assex") ;
	    	sql.append(" ,slo.datestart as f4entrancedate") ;
	    	sql.append(" ,coalesce(slo.datefinish,slo.transferdate) as f5dischargedate") ;
	    	sql.append(" ,case when a.addressisvillage='1' then cast('1' as int) else null end as f6isvillage") ;
	    	sql.append(" ,case when sls.admissionInHospital_id=1 then cast('1' as int) else null end as f7isFirstLife") ;
	    	sql.append(" ,case when sls.hospitalization_id=1 then cast('1' as int) else null end as f8isFirstCurrentYear") ;
	    	sql.append(" ,case when sls.admissionOrder_id in (2,4,5,6,7,8,9) then cast('1' as int) else null end as f9isIncompetent") ;
	    	sql.append(" ,case when vhr.code='6' then cast('1' as int) else null end as f10isDeath") ;
	    	sql.append(" ,slo.department_id as f11slodepartment") ;
	    	sql.append(" ,nextslo.department_id as f12nextslodepartment") ;
	    	sql.append(" ,prevslo.department_id as f13prevslodepartment") ;
	    	sql.append(" ,list(distinct case when vdrtD.code='4' and vpdD.code='1' then mkbD.code else null end) as f14depDiag") ;
	    	sql.append(" ,list(distinct case when vdrt.code='2' then mkb.code else null end) as f15orderDiag") ;
	    	sql.append(" ,list(distinct case when vdrt.code='3' and vpd.code='1' then mkb.code else null end) as f16dischargeDiag") ;
	    	sql.append(" ,case when count(ahr.id)>0 then cast('1' as int) else null end as f17cntAggregate") ;
	    	sql.append(" ,bf.bedType_id as f18bedType") ;
	    	sql.append(" ,bf.bedSubType_id as f19bedSubType") ;
	    	sql.append(" ,sls.serviceStream_id as f20serviceStream") ;
	    	sql.append(" , case when slo.entranceTime<cast('07:00' as time) then cast('1' as int) else null end as f21entranceTime7") ;
	    	sql.append(" , case when slo.entranceTime<cast('09:00' as time) then cast('1' as int) else null end as f22entranceTime9") ;
	    	sql.append(" , case when coalesce(slo.dischargeTime,slo.transferTime)<cast('07:00' as time) then cast('1' as int) else null end as f23entranceTime7") ;
	    	sql.append(" , case when coalesce(slo.dischargeTime,slo.transferTime)<cast('09:00' as time) then cast('1' as int) else null end as f24entranceTime9") ;
	    	sql.append(" , case when sls.emergency='1' then cast('1' as int) else null end as f25emergency") ;
	    	sql.append(" , cast('0' as int) as f26operation") ;
	    	sql.append(" , vht.code as f27transferLpu") ;
	    	sql.append(" , vhtHosp.id as f28hospTypeId") ;
	    	sql.append(" , vs.id as f29sexId") ;
	    	sql.append(" from medcase sls") ;
	    	sql.append(" left join medcase slo on sls.id=slo.parent_id") ;
	    	sql.append(" left join AggregateHospitalReport ahr on ahr.slo=slo.id") ;
	    	sql.append(" left join medcase prevSlo on prevSlo.id=slo.prevMedCase_id") ;
	    	sql.append(" left join medcase nextSlo on nextSlo.prevmedcase_id=slo.id") ;
	    	sql.append(" left join patient pat on pat.id=sls.patient_id") ;
	    	sql.append(" left join address2 a on a.addressid=pat.address_addressid") ;
	    	sql.append(" left join vocsex vs on vs.id=pat.sex_id") ;
	    	sql.append(" left join bedfund bf on bf.id=slo.bedfund_id") ;
	    	sql.append(" left join mislpu ml on ml.id=slo.department_id") ;
	    	sql.append(" left join mislpu mlN on mlN.id=nextSlo.department_id") ;
	    	sql.append(" left join mislpu mlP on mlP.id=prevSlo.department_id") ;
	    	sql.append(" left join diagnosis diag on sls.id=diag.medcase_id") ;
	    	sql.append(" left join vocidc10 mkb on mkb.id=diag.idc10_id") ;
	    	sql.append(" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id") ;
	    	sql.append(" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id") ;
	    	sql.append(" left join diagnosis diagD on slo.id=diagD.medcase_id") ;
	    	sql.append(" left join vocidc10 mkbD on mkbD.id=diagD.idc10_id") ;
	    	sql.append(" left join VocDiagnosisRegistrationType vdrtD on vdrtD.id=diagD.registrationType_id") ;
	    	sql.append(" left join VocPriorityDiagnosis vpdD on vpdD.id=diagD.priority_id") ;
	    	sql.append(" left join VocHospitalizationResult  vhr on vhr.id=sls.result_id") ;
	    	sql.append(" left join VocHospType vht on vht.id=sls.targetHospType_id") ;
	    	sql.append(" left join VocHospType vhtHosp on vhtHosp.id=sls.hospType_id") ;
	    	sql.append(" where sls.dtype='HospitalMedCase' and sls.dateStart <= (to_date('").append(aDischargeDate).append("','dd.mm.yyyy')-1)") ;
	    	sql.append(" and (sls.dateFinish >= (to_date('").append(aEntranceDate).append("','dd.mm.yyyy')-1) or sls.dateFinish is null)") ;
	    	sql.append(" and sls.deniedHospitalizating_id is null") ;
	    	sql.append(" group by vs.omccode,vs.id,slo.datestart,slo.datefinish,slo.transferdate") ;
	    	sql.append(" ,pat.id,sls.id,slo.id,a.addressisvillage") ;
	    	sql.append(" ,sls.admissionInHospital_id,sls.hospitalization_id") ;
	    	sql.append(" ,sls.admissionOrder_id,vhr.code") ;
	    	sql.append(" ,slo.department_id,nextslo.department_id,prevslo.department_id") ;
	    	sql.append(" ,sls.serviceStream_id,bf.bedType_id,bf.bedSubType_id") ;
	    	sql.append(" ,slo.entranceTime, slo.dischargeTime,sls.emergency,vht.code,slo.transferTime,vhtHosp.id ") ;
	    	sql.append(" order by sls.id") ;
	    	monitor.advice(20) ;
	    	
	    	List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
	    	int size = list.size()/80 ;
	    	
	    	for (int i=0;i<list.size();i++) {
	    		Object[] obj = list.get(i) ;
	    		if (monitor.isCancelled()) {
	                throw new IllegalMonitorStateException("Прервано пользователем");
	            }
	    		AggregateHospitalReport ahr = new AggregateHospitalReport() ;
	    		ahr.setAgeDischargeSlo(ConvertSql.parseLong(0));
	    		ahr.setAgeDischargeSls(ConvertSql.parseLong(0));
	    		ahr.setAgeEntranceSlo(ConvertSql.parseLong(0));
	    		ahr.setAgeEntranceSls(ConvertSql.parseLong(0));
	    		ahr.setBedSubType(ConvertSql.parseLong(obj[19])) ;
	    		ahr.setBedType(ConvertSql.parseLong(obj[18])) ;
	    		ahr.setCntDaysSls(ConvertSql.parseLong(0)) ;
	    		ahr.setDepartment(ConvertSql.parseLong(obj[11])) ;
	    		ahr.setDischargeDate24(ConvertSql.parseDate(obj[5])) ;
	    		ahr.setDischargeDate7(ConvertSql.parseDate(obj[5],ConvertSql.parseBoolean(obj[21])?-1:0)) ;
	    		ahr.setDischargeDate9(ConvertSql.parseDate(obj[5],ConvertSql.parseBoolean(obj[22])?-1:0)) ;
	    		ahr.setEntranceDate24(ConvertSql.parseDate(obj[4])) ;
	    		ahr.setEntranceDate7(ConvertSql.parseDate(obj[4],ConvertSql.parseBoolean(obj[23])?-1:0)) ;
	    		ahr.setEntranceDate9(ConvertSql.parseDate(obj[4],ConvertSql.parseBoolean(obj[24])?-1:0)) ;
	    		ahr.setIdcDepartmentCode(ConvertSql.parseString(obj[14])) ;
	    		ahr.setIdcDischarge(ConvertSql.parseString(obj[15])) ;
	    		ahr.setIdcEntranceCode(ConvertSql.parseString(obj[16])) ;
	    		ahr.setIsDeath(ConvertSql.parseBoolean(obj[10])) ;
	    		ahr.setIsEmergency(ConvertSql.parseBoolean(obj[25])) ;
	    		ahr.setIsFirstCurrentYear(ConvertSql.parseBoolean(obj[8])) ;
	    		ahr.setIsFirstLife(ConvertSql.parseBoolean(obj[7])) ;
	    		ahr.setIsIncompetent(ConvertSql.parseBoolean(obj[9])) ;
	    		ahr.setIsOperation(ConvertSql.parseBoolean(obj[26])) ;
	    		ahr.setIsVillage(ConvertSql.parseBoolean(obj[8])) ;
	    		ahr.setPatient(ConvertSql.parseLong(obj[0])) ;
	    		ahr.setServiceStream(ConvertSql.parseLong(obj[20])) ;
	    		ahr.setSexCode(ConvertSql.parseString(obj[3])) ;
	    		ahr.setSlo(ConvertSql.parseLong(obj[2])) ;
	    		ahr.setSls(ConvertSql.parseLong(obj[1])) ;
	    		ahr.setTransferDepartmentFrom(ConvertSql.parseLong(obj[13])) ;
	    		ahr.setTransferDepartmentIn(ConvertSql.parseLong(obj[12])) ;
	    		ahr.setTransferLpuCode(ConvertSql.parseString(obj[27])) ;
	    		ahr.setHospType(ConvertSql.parseLong(obj[28])) ;
	    		ahr.setSex(ConvertSql.parseLong(obj[29])) ;
	    		theManager.persist(ahr);
	    		if(i%10==0) monitor.setText(new StringBuilder().append("Импортируется: ").append(ConvertSql.parseLong(obj[0])).append(" ").append(ConvertSql.parseLong(obj[2])).append("...").toString());
	    		if(i%size==0) monitor.advice(1);

	            if (i % 300 == 0) {
	//              theUserTransaction.commit();
	//              theUserTransaction.begin();
	              monitor.setText("Импортировано " + i);
	          }
	    	}
	    	monitor.finish("");
    	} catch (Exception e) {
            if(monitor!=null) monitor.setText(e+"");
            throw new IllegalStateException(e) ;
        }
    }
    
    private String getTitleFile(String aReestr,String aLpu, String aPeriodByReestr,String aNPackage) {
    	aLpu="300001";
    	String filename = "N"+aReestr+"M"+aLpu+"T30"+aPeriodByReestr+XmlUtil.namePackage(aNPackage) ;
    	return filename ;
    }
    public String[] exportFondZip(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu) 
    		throws ParserConfigurationException, TransformerException {
    	String nPackage = EjbInjection.getInstance()
    			.getLocalService(ISequenceService.class)
    			.startUseNextValueNoCheck("PACKAGE_HOSP","number");
    	String[] fileExpList = {exportN1(aDateFrom,aDateTo,aPeriodByReestr,aLpu,nPackage)
    			, exportN2(aDateFrom,aDateTo,aPeriodByReestr,aLpu,nPackage)
    			, exportN3(aDateFrom,aDateTo,aPeriodByReestr,aLpu,nPackage)
    			, exportN4(aDateFrom,aDateTo,aPeriodByReestr,aLpu,nPackage)
    			, exportN5(aDateFrom,aDateTo,aPeriodByReestr,aLpu,nPackage)
    			, exportN6(aDateFrom,aDateTo,aPeriodByReestr,aLpu,nPackage)
    			, ""
    	};
    	
    	EjbEcomConfig config = EjbEcomConfig.getInstance() ;
    	String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
    	fileExpList[6]=fileExpList[0].substring(2).replaceAll("\\.xml", "")+".263" ;
    	/*
    	String fileZip = workDir+"/"+fileExpList[6] ;
    	
    	byte[] readBuf = new byte[2048] ;
    	FileOutputStream fos =null ;
    	try {
    		fos= new FileOutputStream(new File(fileZip)) ;
			ZipOutputStream zos = new ZipOutputStream(fos);
			zos.setLevel(Deflater.DEFAULT_COMPRESSION) ;
			
			for (int i = 0 ; i<(fileExpList.length-1); i++) {
				String fileExp = fileExpList[i] ; 
				FileInputStream fis = null ;
				try {
					fis = new FileInputStream(new File(workDir+"/"+fileExp)) ;
					ZipEntry anEntry = new ZipEntry(fileExp) ;
					zos.putNextEntry(anEntry) ;
					
					int bytesIn;
					while ((bytesIn = fis.read(readBuf))!=-1){
						zos.write(readBuf,0,bytesIn) ;
					}
				}catch (Exception e) {
					
				} finally {
					if (fis!=null) fis.close() ;
				}
				
			}
		} catch (IOException e) {
		} finally {
			if (fos!=null)
				try {
					fos.close() ;
				} catch (IOException e) {
					
					e.printStackTrace();
				}
		}
    	File file = new File(fileZip) ;
    	file.renameTo(new File(workDir+"/"+fileExpList[0].substring(2).replaceAll("\\.xml", "")+".263")) ;
    	*/
    	StringBuilder sb=new StringBuilder();
    	sb.append("zip -r -9 ") ;
    	for (int i=6;i>-1;i--){
    		sb.append(fileExpList[i]).append(" ");
    	}
    	System.out.println(sb) ;
    	try {
    		String[] arraCmd = {new StringBuilder().append("cd ").append(workDir).append("").toString(),sb.toString()} ;
			Runtime.getRuntime().exec(arraCmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return fileExpList ;
    }
    public String exportN1(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage) 
    		throws ParserConfigurationException, TransformerException {
    	EjbEcomConfig config = EjbEcomConfig.getInstance() ;
    	
    	String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
    	
    	
    	String filename = getTitleFile("1",aLpu,aPeriodByReestr,aNPackage) ;
    	
    	File outFile = new File(workDir+"/"+filename+".xml") ;
    	XmlDocument xmlDoc = new XmlDocument() ;
    	Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
    	StringBuilder sql = new StringBuilder() ;
    	sql.append(" select to_char(wchb.createDate,'yyyy-MM-dd') as wchbcreatedate");
    	sql.append(" ,cast('1' as varchar(1)) as forPom");
    	sql.append(" ,cast('300001' as varchar(6)) as lpuSent");
    	sql.append(" ,cast('300001' as varchar(6)) as lpuDirect");
    	sql.append(" ,vmc.code as medpolicytype");
    	sql.append(" ,mp.series as mpseries");
    	sql.append(" , mp.polnumber as polnumber");
    	sql.append(" , case when oss.smocode is null or oss.smocode='' then ri.smocode else oss.smoCode end as oossmocode");
    	sql.append(" , ri.ogrn as ogrnSmo");
    	sql.append(" ,case when mp.dtype='MedPolicyOmc' then '12000' else okt.okato end as okatoSmo");
    	sql.append(" ,p.lastname as lastname");
    	sql.append(" ,p.firstname as firstname");
    	sql.append(" ,p.middlename as middlename");
    	sql.append(" ,vs.omcCode as vsomccode");
    	sql.append(" ,to_char(p.birthday,'yyyy-mm-dd') as birthday");
    	sql.append(" ,wchb.phone as phonepatient");
    	sql.append(" ,mkb.code as mkbcode");
    	sql.append(" ,vbt.codeF as vbtcodef");
    	sql.append(" ,wp.snils as wpsnils");
    	sql.append(" ,wchb.dateFrom as wchbdatefrom");
    	sql.append(" from WorkCalendarHospitalBed wchb");
    	sql.append(" left join VocBedType vbt on vbt.id=wchb.bedType_id");
    	sql.append(" left join Patient p on p.id=wchb.patient_id");
    	sql.append(" left join VocSex vs on vs.id=p.sex_id");
    	sql.append(" left join VocServiceStream vss on vss.id=wchb.serviceStream_id");
    	sql.append(" left join MedCase mc on mc.id=wchb.visit_id");
    	sql.append(" left join medpolicy mp on mp.patient_id=wchb.patient_id and mp.actualdatefrom<=wchb.createDate and coalesce(mp.actualdateto,current_date)>=wchb.createDate");
    	sql.append(" left join VocIdc10 mkb on mkb.id=wchb.idc10_id");
    	sql.append(" left join MisLpu ml on ml.id=wchb.department_id");
    	sql.append(" left join Vocmedpolicyomc vmc on mp.type_id=vmc.id");
    	sql.append(" left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id");
    	sql.append(" left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id");
    	sql.append(" left join reg_ic ri on ri.id=mp.company_id");
    	sql.append(" left join WorkFunction wf on wf.id=mc.workFunctionExecute_id");
    	sql.append(" left join Worker w on w.id=wf.worker_id");
    	sql.append(" left join Patient wp on wp.id=w.person_id");
    	sql.append(" where wchb.visit_id is not null");
    	sql.append(" and wchb.createDate between to_date('").append(aDateFrom).append("','yyyy-MM-dd') and to_date('").append(aDateFrom).append("','yyyy-MM-dd')");
    	sql.append(" and vss.code in ('OBLIGATORYINSURANCE','OTHER')");
    	/*
    	group by wchb.id,wchb.createDate,ml.name,p.id,p.lastname,p.firstname,p.middlename,p.birthday
    	,mkb.code,wchb.diagnosis,wchb.dateFrom,mc.dateStart,mc.dateFinish,wchb.phone,wchb.department_id,wchb.serviceStream_id
    	,oss.smocode,vbt.codeF,vmc.code,mp.series,mp.polnumber,ri.smocode,ri.ogrn,mp.dtype,okt.okato
    	,vs.omcCode*/
    	sql.append(" order by p.lastname,p.firstname,p.middlename");
    	/*
    	 group by wchb.id,wchb.createDate,ml.name,p.id,p.lastname,p.firstname,p.middlename,p.birthday
		,mkb.code,wchb.diagnosis,wchb.dateFrom,mc.dateStart,mc.dateFinish,wchb.phone,wchb.department_id,wchb.serviceStream_id
		,oss.smocode,vbt.codeF,vmc.code,mp.series,mp.polnumber,ri.smocode,ri.ogrn,mp.dtype,okt.okato
		,vs.omcCode,wp.snils

    	 */
    	
    	List<Object[]> list = theManager.createNativeQuery(sql.toString())
    			.setMaxResults(70000).getResultList() ;
    	Element title = xmlDoc.newElement(root, "ZGLV", null);
    	xmlDoc.newElement(title, "VERSION", "1.0");
    	xmlDoc.newElement(title, "DATA", aDateFrom);
    	xmlDoc.newElement(title, "FILENAME", filename);
    	int i=0 ;
    	for (Object[] obj:list) {
    		Element zap = xmlDoc.newElement(root, "NPR", null);
    		//xmlDoc.newElement(zap, "IDCASE", AddressPointServiceBean.getStringValue(++i)) ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"N_NPR","",true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"D_NPR",obj[0],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FOR_POM",obj[1],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NCODE_MO",obj[2],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NLPU_1","",false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DCODE_MO",obj[3],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DLPU_1",null,false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"VPOLIS",obj[4],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SPOLIS",obj[5],false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NPOLIS",obj[6],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO",obj[7],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO_OGRN",obj[8],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO_OK",obj[9],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO_NAM",null,false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FAM",obj[10],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"IM",obj[11],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"OT",obj[12],false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"W",obj[13],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DR",obj[14],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"CT",obj[15],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DS1",obj[16],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PROFIL",obj[17],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PODR",null,false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"IDDOKT",obj[18],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DATE_1",obj[19],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"REFREASON",null,false,"") ;
    	}
    	XmlUtil.saveXmlDocument(xmlDoc,outFile) ;
    	//saveXmlDocument(xmlDoc,outFile) ;
    	return filename+".xml";
    }
    public String exportN2(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage) 
    		throws ParserConfigurationException, TransformerException {
    	EjbEcomConfig config = EjbEcomConfig.getInstance() ;
    	
    	String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;

    	
    	String filename = getTitleFile("2",aLpu,aPeriodByReestr,aNPackage) ;
    	
    	File outFile = new File(workDir+"/"+filename+".xml") ;
    	XmlDocument xmlDoc = new XmlDocument() ;
    	Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select to_char(sls.dateStart,'yyyy-mm-dd') as datestart");
    	sql.append(" ,cast(sls.entranceTime as varchar(5)) as entrancetime");
    	sql.append(" ,vmc.code as medpolicytype");
    	sql.append(" ,mp.series as mpseries");
    	sql.append(" , mp.polnumber as polnumber");
    	sql.append(" , case when oss.smocode is null or oss.smocode='' then ri.smocode else oss.smoCode end as oossmocode");
    	sql.append(" , ri.ogrn as ogrnSmo");
    	sql.append(" ,case when mp.dtype='MedPolicyOmc' then '12000' else okt.okato end as okatoSmo");
    	sql.append(" ,p.lastname as lastname");
    	sql.append(" ,p.firstname as firstname");
    	sql.append(" ,p.middlename as middlename");
    	sql.append(" ,vs.omcCode as vsomccode");
    	sql.append(" ,to_char(p.birthday,'yyyy-mm-dd') as birthday");
    	sql.append(" ,vbt.codeF as vbtomccode");
    	sql.append(" ,ss.code as sscode");
    	sql.append(" ,mkb.code as mkbcode");
    	sql.append("  from medcase sls");
    	sql.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id");
    	sql.append(" left join medpolicy mp on mp.id=mcmp.policies_id");
    	sql.append(" left join Vocmedpolicyomc vmc on mp.type_id=vmc.id");
    	sql.append(" left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id");
    	sql.append(" left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id");
    	sql.append(" left join reg_ic ri on ri.id=mp.company_id");
    	
    	sql.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id");
    	sql.append(" left join Patient p on p.id=sls.patient_id");
    	sql.append(" left join VocSex vs on vs.id=p.sex_id");
    	sql.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'");
    	sql.append(" left join diagnosis diag on diag.medcase_id=slo.id and diag.priority_id='1' and diag.registrationType_id = '4'");
    	sql.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id") ;
    	sql.append(" left join BedFund bf on bf.id=slo.bedFund_id");
    	sql.append(" left join VocBedType vbt on vbt.id=bf.bedType_id");
    	sql.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id");
    	sql.append(" where sls.dtype='HospitalMedCase' and sls.dateStart = to_date('").append(aDateFrom).append("','yyyy-mm-dd')");
    	sql.append(" and sls.deniedHospitalizating_id is null and sls.emergency='1' and slo.prevMedCase_id is null");
    	sql.append(" and vss.code in ('OBLIGATORYINSURANCE','OTHER')") ;
    	sql.append(" and mkb.code is not null") ;
    	sql.append(" order by p.lastname,p.firstname,p.middlename") ;
    	
    	List<Object[]> list = theManager.createNativeQuery(sql.toString())
    			.setMaxResults(70000).getResultList() ;
    	Element title = xmlDoc.newElement(root, "ZGLV", null);
    	xmlDoc.newElement(title, "VERSION", "1.0");
    	xmlDoc.newElement(title, "DATA", aDateFrom);
    	xmlDoc.newElement(title, "FILENAME", filename);
    	int i=0 ;
    	for (Object[] obj:list) {
    		Element zap = xmlDoc.newElement(root, "NPR", null);
    		//xmlDoc.newElement(zap, "IDCASE", AddressPointServiceBean.getStringValue(++i)) ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"N_NPR",null,true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"D_NPR",obj[0],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FOR_POM","1",true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DCODE_MO","300001",true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DLPU_1",null,false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NCODE_MO","300001",true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NLPU_1",null,false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DATE_1",obj[0],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"TIME_1",XmlUtil.formatTime(obj[1]),true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"VPOLIS",obj[2],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SPOLIS",obj[3],false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NPOLIS",obj[4],true,"") ;
    		/*xmlDoc.newElement(zap, "SMO", AddressPointServiceBean.getStringValue(obj[5])) ;
    		xmlDoc.newElement(zap, "SMO_OGRN", AddressPointServiceBean.getStringValue(obj[6])) ;
    		xmlDoc.newElement(zap, "SMO_OK", AddressPointServiceBean.getStringValue(obj[7])) ;
    		xmlDoc.newElement(zap, "SMO_NAM", AddressPointServiceBean.getStringValue("")) ;*/
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FAM",obj[8],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"IM",obj[9],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"OT",obj[10],false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"W",obj[11],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DR",obj[12],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PROFIL",obj[13],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PODR",null,false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NHISTORY",obj[14],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DS1",obj[15],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"REFREASON",null,false,"") ;
    	}
    	XmlUtil.saveXmlDocument(xmlDoc,outFile) ;
    	return filename+".xml";
    }
    public String exportN3(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage) 
    		throws ParserConfigurationException, TransformerException {
    	EjbEcomConfig config = EjbEcomConfig.getInstance() ;
    	Map<SecPolicy, String> hash = new HashMap<SecPolicy,String>() ;
    	String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
    	
    	String filename = getTitleFile("3",aLpu,aPeriodByReestr,aNPackage) ; ;
    	
    	File outFile = new File(workDir+"/"+filename+".xml") ;
    	XmlDocument xmlDoc = new XmlDocument() ;
    	Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select to_char(sls.dateStart,'yyyy-mm-dd') as datestart");
    	sql.append(" ,cast(sls.entranceTime as varchar(5)) as entrancetime");
    	sql.append(" ,vmc.code as medpolicytype");
    	sql.append(" ,mp.series as mpseries");
    	sql.append(" , mp.polnumber as polnumber");
    	sql.append(" , case when oss.smocode is null or oss.smocode='' then ri.smocode else oss.smoCode end as oossmocode");
    	sql.append(" , ri.ogrn as ogrnSmo");
    	sql.append(" ,case when mp.dtype='MedPolicyOmc' then '12000' else okt.okato end as okatoSmo");
    	sql.append(" ,p.lastname as lastname");
    	sql.append(" ,p.firstname as firstname");
    	sql.append(" ,p.middlename as middlename");
    	sql.append(" ,vs.omcCode as vsomccode");
    	sql.append(" ,to_char(p.birthday,'yyyy-mm-dd') as birthday");
    	sql.append(" ,vbt.codeF as vbtomccode");
    	sql.append(" ,ss.code as sscode");
    	sql.append(" ,mkb.code as mkbcode");
    	sql.append("  from medcase sls");
    	sql.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id");
    	sql.append(" left join medpolicy mp on mp.id=mcmp.policies_id");
    	sql.append(" left join Vocmedpolicyomc vmc on mp.type_id=vmc.id");
    	sql.append(" left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id");
    	sql.append(" left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id");
    	sql.append(" left join reg_ic ri on ri.id=mp.company_id");
    	
    	sql.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id");
    	sql.append(" left join Patient p on p.id=sls.patient_id");
    	sql.append(" left join VocSex vs on vs.id=p.sex_id");
    	sql.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'");
    	sql.append(" left join diagnosis diag on diag.medcase_id=slo.id and diag.priority_id='1' and diag.registrationType_id = '4'");
    	sql.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id") ;
    	sql.append(" left join BedFund bf on bf.id=slo.bedFund_id");
    	sql.append(" left join VocBedType vbt on vbt.id=bf.bedType_id");
    	sql.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id");
    	sql.append(" where sls.dtype='HospitalMedCase' and sls.dateStart = to_date('").append(aDateFrom).append("','yyyy-mm-dd')");
    	sql.append(" and sls.deniedHospitalizating_id is null and sls.emergency='1' and slo.prevMedCase_id is null");
    	sql.append(" and vss.code in ('OBLIGATORYINSURANCE','OTHER')") ;
    	sql.append(" and mkb.code is not null") ;
    	sql.append(" order by p.lastname,p.firstname,p.middlename") ;
    	
    	List<Object[]> list = theManager.createNativeQuery(sql.toString())
    			.setMaxResults(70000).getResultList() ;
    	Element title = xmlDoc.newElement(root, "ZGLV", null);
    	xmlDoc.newElement(title, "VERSION", "1.0");
    	xmlDoc.newElement(title, "DATA", aDateFrom);
    	xmlDoc.newElement(title, "FILENAME", filename);
    	int i=0 ;
    	for (Object[] obj:list) {
    		Element zap = xmlDoc.newElement(root, "NPR", null);
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DCODE_MO","300001",true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DLPU_1",null,false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DATE_1",obj[0],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"TIME_1",XmlUtil.formatTime(obj[1]),true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"VPOLIS",obj[2],false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SPOLIS",obj[3],false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NPOLIS",obj[4],false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO",obj[5],false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO_OGRN",obj[6],false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO_OK",obj[7],false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMO_NAM",null,false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FAM",obj[8],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"IM",obj[9],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"OT",obj[10],false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"W",obj[11],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DR",obj[12],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PROFIL",obj[13],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PODR",null,false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NHISTORY",obj[14],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DS1",obj[15],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"REFREASON",null,false,"") ;
    	}
    	XmlUtil.saveXmlDocument(xmlDoc,outFile) ;
    	return filename+".xml";
    }
    public String exportN4(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage) 
    		throws ParserConfigurationException, TransformerException {
    	EjbEcomConfig config = EjbEcomConfig.getInstance() ;
    	Map<SecPolicy, String> hash = new HashMap<SecPolicy,String>() ;
    	String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
   	
    	String filename = getTitleFile("4",aLpu,aPeriodByReestr,aNPackage) ; ;
    	
    	File outFile = new File(workDir+"/"+filename+".xml") ;
    	XmlDocument xmlDoc = new XmlDocument() ;
    	
    	Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select sls.orderNumber as orderNumber");
    	sql.append(" ,case when sls.emergency='1' then to_char(sls.dateStart,'yyyy-mm-dd') else to_char(sls.orderDate,'yyyy-mm-dd') end as orderDate");
    	sql.append(" ,case when sls.emergency='1' then cast('3' as varchar(1)) else cast('1' as varchar(1)) end as emergency");
    	sql.append(" ,to_char(sls.dateStart,'yyyy-mm-dd') as datestart");
    	sql.append(" ,to_char(sls.dateFinish,'yyyy-mm-dd') as dateFinish");
    	sql.append(" ,cast(sls.entranceTime as varchar(5)) as entrancetime");
    	sql.append(" ,vmc.code as medpolicytype");
    	sql.append(" ,mp.series as mpseries");
    	sql.append(" , mp.polnumber as polnumber");
    	sql.append(" , case when oss.smocode is null or oss.smocode='' then ri.smocode else oss.smoCode end as oossmocode");
    	sql.append(" , ri.ogrn as ogrnSmo");
    	sql.append(" ,case when mp.dtype='MedPolicyOmc' then '12000' else okt.okato end as okatoSmo");
    	sql.append(" ,p.lastname as lastname");
    	sql.append(" ,p.firstname as firstname");
    	sql.append(" ,p.middlename as middlename");
    	sql.append(" ,vs.omcCode as vsomccode");
    	sql.append(" ,to_char(p.birthday,'yyyy-mm-dd') as birthday");
    	sql.append(" ,vbt.codeF as vbtomccode");
    	sql.append(" ,ss.code as sscode");
    	sql.append(" ,mkb.code as mkbcode");
    	sql.append("  from medcase sls");
    	sql.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id");
    	sql.append(" left join medpolicy mp on mp.id=mcmp.policies_id");
    	sql.append(" left join Vocmedpolicyomc vmc on mp.type_id=vmc.id");
    	sql.append(" left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id");
    	sql.append(" left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id");
    	sql.append(" left join reg_ic ri on ri.id=mp.company_id");
    	
    	sql.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id");
    	sql.append(" left join Patient p on p.id=sls.patient_id");
    	sql.append(" left join VocSex vs on vs.id=p.sex_id");
    	sql.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'");
    	sql.append(" left join diagnosis diag on diag.medcase_id=sls.id and diag.priority_id='1' ");
    	sql.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id") ;
    	sql.append(" left join BedFund bf on bf.id=slo.bedFund_id");
    	sql.append(" left join VocBedType vbt on vbt.id=bf.bedType_id");
    	sql.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id");
    	sql.append(" where sls.dtype='HospitalMedCase' and sls.dateStart = to_date('").append(aDateFrom).append("','yyyy-mm-dd')");
    	sql.append(" and sls.deniedHospitalizating_id is not null ");
    	sql.append(" and vss.code in ('OBLIGATORYINSURANCE','OTHER')") ;
    	sql.append(" and mkb.code is not null") ;
    	sql.append(" order by p.lastname,p.firstname,p.middlename") ;
    	
    	List<Object[]> list = theManager.createNativeQuery(sql.toString())
    			.setMaxResults(70000).getResultList() ;
    	Element title = xmlDoc.newElement(root, "ZGLV", null);
    	xmlDoc.newElement(title, "VERSION", "1.0");
    	xmlDoc.newElement(title, "DATA", aDateFrom);
    	xmlDoc.newElement(title, "FILENAME", filename);
    	int i=0 ;
    	for (Object[] obj:list) {
    		Element zap = xmlDoc.newElement(root, "NPR", null);
    		//xmlDoc.newElement(zap, "IDCASE", AddressPointServiceBean.getStringValue(++i)) ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"N_NPR","0",true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"D_NPR",obj[1],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"ISTNPR","2",true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"SMOLPU","300001",true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"LPU_1",null,false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PRNPR","5",true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"REFREASON",null,false,"") ;
    	}
    	XmlUtil.saveXmlDocument(xmlDoc,outFile) ;
    	return filename+".xml";
    }
    public String exportN5(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage) 
    		throws ParserConfigurationException, TransformerException {
    	EjbEcomConfig config = EjbEcomConfig.getInstance() ;
    	Map<SecPolicy, String> hash = new HashMap<SecPolicy,String>() ;
    	String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
   	
    	String filename = getTitleFile("5",aLpu,aPeriodByReestr,aNPackage) ;
    	
    	File outFile = new File(workDir+"/"+filename+".xml") ;
    	XmlDocument xmlDoc = new XmlDocument() ;
    	Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select sls.orderNumber as orderNumber");
    	sql.append(" ,case when sls.emergency='1' then to_char(sls.dateFinish,'yyyy-mm-dd') else to_char(sls.orderDate,'yyyy-mm-dd') end as orderDate");
    	sql.append(" ,case when sls.emergency='1' then cast('3' as varchar(1)) else cast('1' as varchar(1)) end as pokaz");
    	sql.append(" ,cast('300001' as varchar(6)) as lpuSent");
    	sql.append(" ,to_char(sls.dateStart,'yyyy-mm-dd') as datestart");
    	sql.append(" ,to_char(sls.dateFinish,'yyyy-mm-dd') as dateFinish");
    	sql.append(" ,cast(sls.entranceTime as varchar(5)) as entrancetime");
    	sql.append(" ,vmc.code as medpolicytype");
    	sql.append(" ,mp.series as mpseries");
    	sql.append(" , mp.polnumber as polnumber");
    	sql.append(" , case when oss.smocode is null or oss.smocode='' then ri.smocode else oss.smoCode end as oossmocode");
    	sql.append(" , ri.ogrn as ogrnSmo");
    	sql.append(" ,case when mp.dtype='MedPolicyOmc' then '12000' else okt.okato end as okatoSmo");
    	sql.append(" ,p.lastname as lastname");
    	sql.append(" ,p.firstname as firstname");
    	sql.append(" ,p.middlename as middlename");
    	sql.append(" ,vs.omcCode as vsomccode");
    	sql.append(" ,to_char(p.birthday,'yyyy-mm-dd') as birthday");
    	sql.append(" ,vbt.codeF as vbtomccode");
    	sql.append(" ,ss.code as sscode");
    	sql.append(" ,mkb.code as mkbcode");
    	sql.append("  from medcase sls");
    	sql.append(" left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id");
    	sql.append(" left join medpolicy mp on mp.id=mcmp.policies_id");
    	sql.append(" left join Vocmedpolicyomc vmc on mp.type_id=vmc.id");
    	sql.append(" left join Omc_kodter okt on okt.id=mp.insuranceCompanyArea_id");
    	sql.append(" left join Omc_SprSmo oss on oss.id=mp.insuranceCompanyCode_id");
    	sql.append(" left join reg_ic ri on ri.id=mp.company_id");
    	
    	sql.append(" left join StatisticStub ss on ss.id=sls.statisticStub_id");
    	sql.append(" left join Patient p on p.id=sls.patient_id");
    	sql.append(" left join VocSex vs on vs.id=p.sex_id");
    	sql.append(" left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'");
    	sql.append(" left join diagnosis diag on diag.medcase_id=slo.id and diag.priority_id='1' and diag.registrationType_id = '4'");
    	sql.append(" left join VocIdc10 mkb on mkb.id=diag.idc10_id") ;
    	sql.append(" left join BedFund bf on bf.id=slo.bedFund_id");
    	sql.append(" left join VocBedType vbt on vbt.id=bf.bedType_id");
    	sql.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id");
    	sql.append(" where sls.dtype='HospitalMedCase' and sls.dateFinish = to_date('").append(aDateFrom).append("','yyyy-mm-dd')");
    	sql.append(" and sls.deniedHospitalizating_id is null and sls.emergency='1' and slo.prevMedCase_id is null");
    	sql.append(" and vss.code in ('OBLIGATORYINSURANCE','OTHER')") ;
    	sql.append(" and mkb.code is not null") ;
    	sql.append(" order by p.lastname,p.firstname,p.middlename") ;
    	
    	List<Object[]> list = theManager.createNativeQuery(sql.toString())
    			.setMaxResults(70000).getResultList() ;
    	Element title = xmlDoc.newElement(root, "ZGLV", null);
    	xmlDoc.newElement(title, "VERSION", "1.0");
    	xmlDoc.newElement(title, "DATA", aDateFrom);
    	xmlDoc.newElement(title, "FILENAME", filename);
    	int i=0 ;
    	for (Object[] obj:list) {
    		Element zap = xmlDoc.newElement(root, "NPR", null);
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"N_NPR","0",true,"") ;
    		//xmlDoc.newElement(zap, "IDCASE", AddressPointServiceBean.getStringValue(++i)) ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"D_NPR",obj[1],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FOR_POM",obj[2],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"LPU","300001",true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"LPU_1",null,false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DATE_1",obj[4],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DATE_2",obj[5],true,"") ;
    		/*
    		xmlDoc.newElement(zap, "VPOLIS", AddressPointServiceBean.getStringValue(obj[2])) ;
    		xmlDoc.newElement(zap, "SPOLIS", AddressPointServiceBean.getStringValue(obj[3])) ;
    		xmlDoc.newElement(zap, "NPOLIS", AddressPointServiceBean.getStringValue(obj[4])) ;
    		xmlDoc.newElement(zap, "SMO", AddressPointServiceBean.getStringValue(obj[5])) ;
    		xmlDoc.newElement(zap, "SMO_OGRN", AddressPointServiceBean.getStringValue(obj[6])) ;
    		xmlDoc.newElement(zap, "SMO_OK", AddressPointServiceBean.getStringValue(obj[7])) ;
    		xmlDoc.newElement(zap, "SMO_NAM", AddressPointServiceBean.getStringValue("")) ;
    		xmlDoc.newElement(zap, "FAM", AddressPointServiceBean.getStringValue(obj[8])) ;
    		xmlDoc.newElement(zap, "IM", AddressPointServiceBean.getStringValue(obj[9])) ;
    		xmlDoc.newElement(zap, "OT", AddressPointServiceBean.getStringValue(obj[10])) ;
    		 */
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"W",obj[16],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DR",obj[17],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PROFIL",obj[18],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PODR",null,false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"NHISTORY",obj[19],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"REFREASON",null,false,"") ;
    	}
    	XmlUtil.saveXmlDocument(xmlDoc,outFile) ;
    	return filename+".xml";
    }
    public String exportN6(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage) 
    			throws ParserConfigurationException, TransformerException {
    	EjbEcomConfig config = EjbEcomConfig.getInstance() ;
    	Map<SecPolicy, String> hash = new HashMap<SecPolicy,String>() ;
    	String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
		
    	String filename = getTitleFile("6",aLpu,aPeriodByReestr,aNPackage) ;
    	
    	File outFile = new File(workDir+"/"+filename+".xml") ;
    	XmlDocument xmlDoc = new XmlDocument() ;
    	Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select vbt.codef,vbt.name") ;
    	sql.append(" ,count(distinct sls.id) as cntHosp") ;
    	sql.append(" ,count(distinct case when sls.dateStart=to_date('").append(aDateFrom).append("','yyyy-mm-dd') then sls.id else null end) as cntEnter") ;
    	sql.append(" ,count(distinct case when sls.dateFinish=to_date('").append(aDateFrom).append("','yyyy-mm-dd') then sls.id else null end) as cntDischarge") ;
    	sql.append(" ,count(distinct case when sls.dateFinish is null or sls.datefinish>=to_date('").append(aDateFrom).append("','yyyy-mm-dd') then sls.id else null end) as cntCurrent") ;
    	sql.append(" ,(select sum(bf1.amount) from bedfund bf1 left join VocBedType vbt1 on bf1.bedtype_id=vbt1.id where vbt1.codef=vbt.codef and bf1.bedsubtype_id=bf1.bedsubtype_id) as sumBed") ;
    	sql.append(" from medcase sls") ;
    	sql.append(" left join medcase sloF on sloF.parent_id = sls.id and sloF.dtype='DepartmentMedCase' and sloF.prevMedCase_id is null") ;
    	sql.append(" left join medcase sloL on sloL.parent_id = sls.id and sloF.dtype='DepartmentMedCase' and sloL.dateFinish is not null ") ;
    	sql.append(" left join BedFund bf on bf.id=sloF.bedFund_id") ;
    	sql.append(" left join VocBedType vbt on vbt.id=bf.bedType_id") ;
    	sql.append(" left join VocServiceStream vss on vss.id=sls.serviceStream_id") ;
    	sql.append(" where sls.dtype='HospitalMedCase' and sls.dateStart>=to_date('").append(aDateFrom).append("','yyyy-mm-dd')") ;
    	sql.append(" and sls.dateFinish>=coalesce(to_date('").append(aDateFrom).append("','yyyy-mm-dd'),current_date)") ;
    	sql.append(" and vss.code in ('OBLIGATORYINSURANCE','OTHER')") ;
    	sql.append(" group by vbt.codef,vbt.name,bf.bedsubtype_id") ;
    	sql.append(" order by vbt.name");
    	
    	List<Object[]> list = theManager.createNativeQuery(sql.toString())
    			.setMaxResults(70000).getResultList() ;
    	Element title = xmlDoc.newElement(root, "ZGLV", null);
    	xmlDoc.newElement(title, "VERSION", "1.0");
    	xmlDoc.newElement(title, "DATA", aDateFrom);
    	xmlDoc.newElement(title, "FILENAME", filename);
    	int i=0 ;
    	for (Object[] obj:list) {
    		Element zap = xmlDoc.newElement(root, "NPR", null);
    		//xmlDoc.newElement(zap, "IDCASE", AddressPointServiceBean.getStringValue(++i)) ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"DATA",aDateFrom,true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"LPU","300001",true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"LPU_1",null,false,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PROFIL",obj[0],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"COUNTP",obj[5],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"POSTP",obj[3],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"VIBP",obj[4],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"PLANP",0,true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FREEK",obj[6],true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FREEM",0,true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FREEW",0,true,"") ;
    		XmlUtil.recordElementInDocumentXml(xmlDoc,zap,"FREED",0,true,"") ;
    		String[] smoCodes = {"30002","30004"} ;
    		for (String smoCode:smoCodes) {
        		Element obsmo = xmlDoc.newElement(zap, "OBSMO", null) ;
        		XmlUtil.recordElementInDocumentXml(xmlDoc,obsmo,"SMO",smoCode,true,"") ;
        		XmlUtil.recordElementInDocumentXml(xmlDoc,obsmo,"SMOSL",0,true,"") ;
        		XmlUtil.recordElementInDocumentXml(xmlDoc,obsmo,"SMOKD",0,true,"") ;
    		}
    	}
    	XmlUtil.saveXmlDocument(xmlDoc,outFile) ;
    	return filename+".xml";
    }
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
	    					theManager.createNativeQuery("update SurgicalOperation set serviceStream_id='"+aServiceStream+"' where medCase_id='"+slo[0]+"'").executeUpdate() ;
	    				}
	    				theManager.createNativeQuery("update MedCase set serviceStream_id='"+aServiceStream+"' where id='"+aSmo+"'").executeUpdate() ;
    					theManager.createNativeQuery("update SurgicalOperation set serviceStream_id='"+aServiceStream+"' where medCase_id='"+aSmo+"'").executeUpdate() ;
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
    					theManager.createNativeQuery("update SurgicalOperation set serviceStream_id='"+aServiceStream+"' where medCase_id='"+aSmo+"'").executeUpdate() ;
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
    @EJB ILocalMonitorService theMonitorService;



}
