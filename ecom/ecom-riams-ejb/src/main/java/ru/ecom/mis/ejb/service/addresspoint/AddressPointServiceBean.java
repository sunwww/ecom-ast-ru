package ru.ecom.mis.ejb.service.addresspoint;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.DividePtg;
import org.w3c.dom.Element;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.address.ejb.service.AddressPointCheck;
import ru.ecom.address.ejb.service.AddressPointCheckHelper;
import ru.ecom.address.ejb.service.IAddressService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.ejb.xml.XmlUtil;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressPoint;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressText;
import ru.ecom.mis.ejb.domain.patient.LpuAttachedByDepartment;
import ru.ecom.mis.ejb.domain.patient.MedPolicy;
import ru.ecom.mis.ejb.domain.patient.MedPolicyOmc;
import ru.ecom.mis.ejb.service.patient.QueryClauseBuilder;
import ru.ecom.report.util.XmlDocument;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

/**
 * @author  azviagin
 */
@Stateless
@Local(IAddressPointService.class)
@Remote(IAddressPointService.class)
public class AddressPointServiceBean implements IAddressPointService {

    private final static Logger LOG = Logger.getLogger(AddressPointServiceBean.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();

	Collection<WebQueryResult> errList = new ArrayList<WebQueryResult>();

    StringBuilder def = new StringBuilder();
    WebQueryResult result = new WebQueryResult();
    //Создаем lpuAttachedByDepartment у пациентов, у которых нет спец. прикреплений
    //Передаем YES - еще и обновляем поле "Страх. компания"
    public String createAttachmentFromPatient(String needUpdateIns) {
    	try{
    	StringBuilder sql = new StringBuilder();
    	sql.append("insert into lpuattachedbydepartment  (lpu_id, area_id, patient_id, datefrom, attachedtype_id,  createusername) "+
    	"select p.lpu_id, p.lpuarea_id, p.id, to_date('01.01.2013', 'dd.MM.yyyy'),'1','_system' from patient p "+
    	"where p.lpu_id is not null and not exists (select att.id from lpuattachedbydepartment att where att.patient_id=p.id) ");
    	if (needUpdateIns!=null && needUpdateIns.equals("YES")) {
    		setInsuranceCompany("");
    	}
    	int i = theManager.createNativeQuery(sql.toString()).executeUpdate();
    	return "Прикрепления успешно созданы, изменено "+i+" записей";
    	} catch (Exception e) {
    		e.printStackTrace();
    		return "Ошибка:" +e.toString();
    	}
    	
    }
    
    public String setInsuranceCompany(String needUpdateAll) {
    	
    		StringBuilder sql = new StringBuilder();
    		try{
    		sql.append("update lpuattachedbydepartment att set company_id=(select smo.id from medpolicy mp left join reg_ic smo on smo.id=mp.company_id "+ 
    				"where mp.patient_id=att.patient_id and mp.actualdatefrom is not null and mp.dtype='MedPolicyOmc' "+
    				"and smo.omccode !=''order by actualdatefrom desc limit 1) ");
    		if (needUpdateAll!=null && needUpdateAll.equals("YES")) {
    			
    		} else {
    			 sql.append(" where company_id is null");
    		}
        	int i = theManager.createNativeQuery(sql.toString()).executeUpdate();
        	return "Успешно обновлено "+i+ " записей";
    			
        	} catch (Exception e) {
        		sql.setLength(0);
        		sql.append("from LpuAttachedByDepartment ");
        		if (needUpdateAll!=null && needUpdateAll.equals("YES")) {
        		} else {
        			 sql.append(" where company_id is null");
        		}
        		List<LpuAttachedByDepartment> attList = theManager.createQuery(sql.toString()).getResultList();
        		int i =0;
        		for (LpuAttachedByDepartment att: attList) {
        			try {
						List<MedPolicyOmc> mpList =  theManager.createQuery("from MedPolicyOmc where patient_id=:pat and actualDateFrom is not null order by actualdatefrom desc")
								.setParameter("pat", att.getPatient().getId())
								.getResultList();
						if (!mpList.isEmpty()) {
							i++;
							att.setCompany(mpList.get(0).getCompany());
							theManager.persist(att);	
						}
					} catch (javax.persistence.NoResultException ee) {
						// TODO Auto-generated catch block
						System.out.println("NoResultExceprion: "+att.getPatient().getPatientInfo());
						ee.printStackTrace();
					}
        		}
        		return "Обновлено " + i + " записей.";
        	}
    }
    public WebQueryResult exportAll(String aAge, String aFilenameAddSuffix
    		, String aAddSql, boolean aLpuCheck, Long aLpu, Long aArea
    		, String aDateFrom, String aDateTo, String aPeriodByReestr
    		, String aNReestr, String aNPackage) throws ParserConfigurationException, TransformerException {
    	return exportAll(aAge, aFilenameAddSuffix, aAddSql, aLpuCheck, aLpu, aArea, aDateFrom, aDateTo, aPeriodByReestr
    			, aNReestr, aNPackage,null);
    }
    public WebQueryResult exportAll(String aAge, String aFilenameAddSuffix
    		, String aAddSql, boolean aLpuCheck, Long aLpu, Long aArea
    		, String aDateFrom, String aDateTo, String aPeriodByReestr
    		, String aNReestr, String aNPackage, Long aCompany) throws ParserConfigurationException, TransformerException {
    	return exportAll(aAge, aFilenameAddSuffix, aAddSql, aLpuCheck, aLpu, aArea, aDateFrom, aDateTo, aPeriodByReestr
    			, aNReestr, aNPackage,null,true,"1");
    }
    public WebQueryResult exportFondAll(String aAge, String aFilenameAddSuffix
    		, String aAddSql, boolean aLpuCheck, Long aLpu, Long aArea
    		, String aDateFrom, String aDateTo, String aPeriodByReestr
    		, String aNReestr, String aNPackage, Long aCompany, boolean needDivide) throws ParserConfigurationException, TransformerException {
    	StringBuilder addSql=new StringBuilder().append(aAddSql) ;
    	StringBuilder filenames = new StringBuilder() ;
    	if (aAge!=null) {
    		addSql.append("and cast(to_char(to_date('").append(aDateTo).append("','dd.mm.yyyy'),'yyyy') as int) -cast(to_char(p.birthday,'yyyy') as int) +(case when (cast(to_char(to_date('").append(aDateTo).append("','dd.mm.yyyy'), 'mm') as int) -cast(to_char(p.birthday, 'mm') as int) +(case when (cast(to_char(to_date('").append(aDateTo).append("','dd.mm.yyyy'),'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) ").append(aAge) ;
    	}
    	EjbEcomConfig config = EjbEcomConfig.getInstance() ;
    	//Map<SecPolicy, String> hash = new HashMap<SecPolicy,String>() ;
    	String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
    	String filename=null;
    	StringBuilder sql = new StringBuilder() ;
    	//	File outFile = null;
    	List<Object[]> listPat = null;
    	String[][] props = new String[][] {
    			{"pai.lastname","FAM","pai.lastname","1","Фамилия"},				{"pai.firstname","IM","pai.firstname","1","Имя"}
    	    	,		{"case when pai.middlename='' or pai.middlename='Х' or pai.middlename is null then '' else pai.middlename end","OT" ,"pai.middlename",null,"Отчество"} 
    	    	,		{"to_char(pai.birthday,'yyyy-mm-dd')","DR" ,"pai.birthday","1","Дата рождение"} ,		{"pai.snils","SNILS" ,"pai.snils",null,"СНИЛС"}
    	    	,		{"pai.docType","DOCTYPE" ,"pai.docType",null,"Тип документа"} ,		{"pai.docSeries","DOCSER" ,"pai.docSeries",null,"Серия документа"}
    	    	,		{"pai.docNumber","DOCNUM" ,"pai.docNumber",null,"Номер паспорта"} ,		{"to_char(pai.docdateissued,'yyyy-mm-dd')","DOCDT" ,"pai.docdateissued",null,"Дата выдачи документа"}
    	    	,		{"coalesce(pai.phone,'')","TEL" ,"pai.phone",null,"Телефон"} ,		{"pai.commonNumber","RZ" ,"pai.commonNumber","1","ЕПН"}
    	    	,		{"pai.lpuauto","SP_PRIK" ,"pai.lpuauto","1","Тип прикрепления"} 
    	    	,		{"cast('1' as varchar(1))","T_PRIK" ,"pai.lpuauto","1","прикреплениt/открепление"}
    	    	,		{"to_char(pai.lpuDateFrom,'yyyy-MM-dd')","DATE_1" ,"pai.lpuDateFrom","1","Дата прикрепления"} 
    	    	,		{"cast('0' as varchar(1))","N_ADR" ,"pai.lpudatefrom",null,""}
    	    	,		{"pai.department","KODPODR" ,"pai.department","1","Код подразделения"}
    	    	,		{"coalesce(pai.areaNumber,'')", "LPUUCH", "pai.areaNumber", null},{"pai.doctorsnils","SSD" ,"pai.doctorsnils","1","СНИЛС врача"}
    	    	, {"cast('1' as varchar(1))","MEDRAB","","1","Врач/медработник"}
    	    	
    	    	
    	    } ;
    	/*
    	String[][] props = {
    			{"pai.fondId","pid","pai.fondId"},				{"pai.commonNumber","ENP","pai.commonNumber"}
    	,		{"pai.lastname","FAM" ,"pai.lastname"} ,		{"pai.firstname","IM" ,"pai.firstname"}
    	,		{"pai.middlename","OT" ,"pai.middlename"} ,		{"to_char(pai.birthday,'yyyy-MM-dd')","DR" ,"pai.birthday"}
    	,		{"pai.sex","W" ,"pai.sex"} ,				{"pai.snils","SS" ,"pai.snils"}
    	,		{"pai.birthPlace","MR" ,"pai.birthPlace"} ,		{"pai.insCompName","Q" ,"pai.insCompName"}
    	,		{"pai.policyType","OPDOC" ,"pai.policyType"} ,	{"pai.policySeries","SPOL" ,"pai.policySeries"}
    	,		{"pai.policyNumber","NPOL" ,"pai.policyNumber"} ,	{"pai.docType","DOCTP" ,"pai.docType"}
    	,		{"pai.docSeries","DOCS" ,"pai.docSeries"} ,		{"pai.docNumber","DOCN" ,"pai.docNumber"}
    	,		{"to_char(pai.docDateIssued,'yyyy-MM-dd')","DOCDT","pai.docDateIssued"}
    	,		{"pai.docWhom","DOCORG" ,"pai.docWhom"} ,		{"pai.country","CN" ,"pai.country"}
    	,		{"pai.region","SUBJ","pai.region"}
    	,		{"pai.rn","RN" ,"pai.rn"} ,				{"pai.index","INDX" ,"pai.index"}
    	,		{"pai.rayonName","RNNAME" ,"pai.rayonName"} ,	{"pai.city","CITY" ,"pai.city"}
    	,		{"pai.np","NP" ,"pai.np"} ,				{"pai.street","UL" ,"pai.street"}
    	,		{"pai.house","DOM" ,"pai.house"} ,			{"pai.housing","KOR" ,"pai.housing"}
    	,		{"pai.apartment","KV" ,"pai.apartment"} ,		{"pai.lpu","LPU" ,"pai.lpu"}
    	,		{"pai.lpuauto","LPUAUTO" ,"pai.lpuauto"} ,	{"to_char(pai.lpuDateFrom,'yyyy-MM-dd')","LPUDT" ,"pai.lpuDateFrom"}
    	,		{"pai.department","KODPODR","pai.department"},		{"pai.doctorSnils","SSD","pai.doctorSnils"} 
    } ;
    */
    	StringBuilder fld = new StringBuilder() ;
    	StringBuilder fldGroup = new StringBuilder() ;
    	for (int ind =0;ind<props.length;ind++) {
    		String[] p=props[ind];
    		if (ind!=0&&!p[0].equals("")) {
    			fld.append(",") ;
    			if (!p[2].equals("")) {
    				fldGroup.append(",");
    			} 
    			
    		}
    		fld.append(" ").append(p[0]).append(" as ").append(" fld").append(ind).append("_") ;
    		fldGroup.append(" ").append(p[2]) ;
    	}
    	if (needDivide) {
    		StringBuilder sqlGroup = new StringBuilder() ;
    		sqlGroup.append("select pai.insCompName") ;
    		sqlGroup.append(" from PatientAttachedImport pai") ;
    		
    		sqlGroup.append(" left join REG_IC vri on vri.smocode=pai.insCompName") ;
    		sqlGroup.append(" where pai.time=(select max(pai1.time) from patientattachedimport pai1 )") ;
    		
    		if (aCompany!=null&&aCompany!=0) sqlGroup.append(" and vri.id='").append(aCompany).append("'  ");
    		sqlGroup.append(" group by pai.insCompName") ;
    		sqlGroup.append(" order by pai.insCompName") ;
    		System.out.println("------------------- Need_DIVIDE_COMP = "+sqlGroup.toString());
    		List<Object> listComp = theManager.createNativeQuery(sqlGroup.toString())
    				.getResultList() ;
    		
    		for (Object comp:listComp) {
    			filename = "P"+aFilenameAddSuffix+aNReestr+"S"+(comp==null?"-":comp)
    	        		+"_"+aPeriodByReestr+XmlUtil.namePackage(aNPackage) ;
    	        filenames.append("#").append(filename+".xml") ;
    	        
    			sql.setLength(0);
    			sql.append("select ").append(fld) ;
    			sql.append(" from PatientAttachedImport pai") ;
    			sql.append(" left join Patient p on p.id=pai.patient_id") ;
    			
    			sql.append(" where ") ;
    			sql.append("  pai.time=(select max(pai1.time) from patientattachedimport pai1 ) and (pai.insCompName") ;
    			if (comp!=null) {sql.append("='").append(comp).append("'") ;} else {sql.append(" is null or pai.insCompName=''") ;}
    			sql.append(") and ");
    			sql.append(" (p.noActuality='0' or p.noActuality is null) and p.deathDate is null ");
    			sql.append(" ").append(addSql) ;
        		sql.append(" group by ").append(fldGroup) ;
        		sql.append(" order by pai.lastname,pai.firstname,pai.middlename,pai.birthday") ;
    			System.out.println("------------------- Need_DIVIDE_PAT = "+sql.toString());
    			listPat = theManager.createNativeQuery(sql.toString())
    					.getResultList() ;
    			createFondXml(workDir, filename,aPeriodByReestr,aNReestr, listPat,props);
    			//xmlDoc.saveDocument(outFile) ;
    		}
    	} else {
    		filename = aFilenameAddSuffix+"_"+aNReestr+"_"+aPeriodByReestr+XmlUtil.namePackage(aNPackage) ;
    		filenames.append("#").append(filename+".xml") ;
    		sql.setLength(0);
    		sql.append("select ").append(fld) ;
    		sql.append(" from PatientAttachedImport pai") ;
    		sql.append(" left join Patient p on pai.patient_id=p.id") ;
    		sql.append(" left join MisLpu ml1 on ml1.id=p.lpu_id") ;
    		sql.append(" left join LpuAttachedByDepartment lp on lp.patient_id=p.id") ;
    		sql.append(" left join MisLpu ml2 on ml2.id=lp.lpu_id") ;
    		sql.append(" where  pai.time=(select max(pai1.time) from patientattachedimport pai1 ) ") ;
    		
    		if (aLpuCheck) sql.append(" and (p.lpu_id='").append(aLpu).append("' or lp.lpu_id='").append(aLpu).append("' or ml1.parent_id='").append(aLpu).append("' or ml2.parent_id='").append(aLpu).append("')  ") ;
    		if (aLpuCheck && aArea!=null &&aArea.intValue()>0) sql.append(" and (p.lpuArea_id='").append(aArea).append("' or lp.area_id='").append(aArea).append("')  ") ;
    		sql.append(" and (p.noActuality='0' or p.noActuality is null) and p.deathDate is null ");
    		sql.append(" ").append(addSql) ;
    		sql.append(" group by ").append(fldGroup) ;
    		sql.append(" order by pai.lastname,pai.firstname,pai.middlename,pai.birthday") ;
    		System.out.println("-------------------NO Need_DIVIDE_PAT = "+sql.toString());
    		listPat = theManager.createNativeQuery(sql.toString())
    				.getResultList() ;
    		createFondXml(workDir, filename,aPeriodByReestr,aNReestr, listPat,props);
    	}
    	
    	WebQueryResult r = new WebQueryResult();
    	r.set1(filenames.length()>0?filenames.substring(1):"");
    	return r;
    }
    public WebQueryResult exportAll(String aAge, String aFilenameAddSuffix
    		, String aAddSql, boolean aLpuCheck, Long aLpu, Long aArea
    		, String aDateFrom, String aDateTo, String aPeriodByReestr
    		, String aNReestr, String aNPackage, Long aCompany, boolean needDivide, String xmlFormat) throws ParserConfigurationException, TransformerException {
    	StringBuilder addSql=new StringBuilder().append(aAddSql) ;
    	StringBuilder filenames = new StringBuilder() ;
    	errList.clear();
    	if (aAge!=null) {
    		addSql.append("and cast(to_char(to_date('").append(aDateTo).append("','dd.mm.yyyy'),'yyyy') as int) -cast(to_char(p.birthday,'yyyy') as int) +(case when (cast(to_char(to_date('").append(aDateTo).append("','dd.mm.yyyy'), 'mm') as int) -cast(to_char(p.birthday, 'mm') as int) +(case when (cast(to_char(to_date('").append(aDateTo).append("','dd.mm.yyyy'),'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) ").append(aAge) ;
    	}
    	EjbEcomConfig config = EjbEcomConfig.getInstance() ;
    	String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
    	String filename=null;
    	StringBuilder sql = new StringBuilder() ;
    	List<Object[]> listPat = null;
    	String[][] props = null;
    	if (xmlFormat!=null&&xmlFormat.equals("0")){ //OLD FORMAT  //Не используем
    		props = new String[][] {
    				{"p.lastname","FAM","p.lastname","1","Фамилия"},				{"p.firstname","IM","p.firstname","1","Имя"}
    				,		{"case when p.middlename='' or p.middlename='Х' or p.middlename is null then '' else p.middlename end","OT" ,"p.middlename",null,"Отчество"} 
    				,		{"to_char(p.birthday,'yyyy-mm-dd')","DR" ,"p.birthday","1","Дата рождение"} ,		{"p.snils","SNILS" ,"p.snils",null,"СНИЛС"}
    				,		{"vic.omcCode","DOCTYPE" ,"vic.omcCode",null,"Тип документа"} ,		{"p.passportSeries","DOCSER" ,"p.passportSeries",null,"Серия документа"}
    				,		{"p.passportNumber","DOCNUM" ,"p.passportNumber",null,"Номер паспорта"}
    				,		{"p.commonNumber","RZ" ,"p.commonNumber",null,"ЕПН"}
    				,		{" case when lp.id is null then '1' else coalesce(vat.code,'2') end","SP_PRIK" ,"lp.id,vat.code","1","Тип прикрепления"} 
    				,		{"case when lp.dateTo is null then '1' else '2' end","T_PRIK" ,"lp.dateTo","1","прикреплениt/открепление"}
    				,		{"coalesce(to_char(lp.dateTo,'yyyy-mm-dd'),to_char(lp.dateFrom,'yyyy-mm-dd'),'2013-04-01')","DATE_1" ,"lp.dateTo,lp.dateFrom","1","Дата прикрепления"} 
    				
    				 
    				
    				
    		} ;
    	} else if (xmlFormat!=null&&xmlFormat.equals("1")){ //NEW FORMAT (01.07.2015)
    		props = new String[][] {
        			{"p.lastname","FAM","p.lastname","1","Фамилия"},				{"p.firstname","IM","p.firstname","1","Имя"}
        	    	,		{"case when p.middlename='' or p.middlename='Х' or p.middlename is null then '' else p.middlename end","OT" ,"p.middlename",null,"Отчество"} 
        	    	,		{"to_char(p.birthday,'yyyy-mm-dd')","DR" ,"p.birthday","1","Дата рождение"} ,		{"p.snils","SNILS" ,"p.snils",null,"СНИЛС"}
        	    	,		{"vic.omcCode","DOCTYPE" ,"vic.omcCode","1","Тип документа"} ,		{"p.passportSeries","DOCSER" ,"p.passportSeries",null,"Серия документа"}
        	    	,		{"p.passportNumber","DOCNUM" ,"p.passportNumber","1","Номер паспорта"} ,		{"to_char(p.passportdateissued,'yyyy-mm-dd')","DOCDT" ,"p.passportdateissued","1","Дата выдачи документа"}
        	    	,		{"cast('' as varchar(1))","TEL" ,"p.phone",null,"Телефон"} ,		{"p.commonNumber","RZ" ,"p.commonNumber",null,"ЕПН"}
        	    	,		{" case when lp.id is null then '1' else coalesce(vat.code,'2') end","SP_PRIK" ,"lp.id,vat.code","1","Тип прикрепления"} 
        	    	,		{"case when lp.dateTo is null then '1' else '2' end","T_PRIK" ,"lp.dateTo","1","прикреплениt/открепление"}
        	    	,		{"coalesce(to_char(lp.dateTo,'yyyy-mm-dd'),to_char(lp.dateFrom,'yyyy-mm-dd'),'2013-04-01')","DATE_1" ,"lp.dateTo,lp.dateFrom","1","Дата прикрепления"} 
        	    	,		{"case when lp.newAddress='1' then '1' else '0' end","N_ADR" ,"lp.newAddress",null,""}
        	    	,		{"case when la.codeDepartment!='' then la.codeDepartment else ml3.codeDepartment end","KODPODR" ,"la.codeDepartment,ml3.codeDepartment","1","Код подразделения"}
        	    	,		{"la.number", "LPUUCH", "la.number", null},{"wp.snils","SSD" ,"wp.snils","1","СНИЛС врача"}
        	    	, {"cast('1' as varchar(1))","MEDRAB","",null,"Врач/медработник"}
        	    	
        	    	
        	    } ;
    	}
    	
    	StringBuilder fld = new StringBuilder() ;
    	StringBuilder fldGroup = new StringBuilder() ;
    	for (int ind =0;ind<props.length;ind++) {
    		String[] p=props[ind];
    		if (ind!=0&&!p[0].equals("")) {
    			fld.append(",") ;
    			if (!p[2].equals("")){
    				fldGroup.append(",");
    			}
    		}
    		if (!p[0].equals("")) {
    			fld.append(" ").append(p[0]).append(" as ").append(" fld").append(ind).append("_") ;
    			fldGroup.append(" ").append(p[2]) ;
    		}    		
    		
    	}
    	if (needDivide) {
    		StringBuilder sqlGroup = new StringBuilder() ;
        	sqlGroup.append("select lp.company_id,vri.smocode") ;
        	sqlGroup.append(" from Patient p") ;
        	sqlGroup.append(" left join MisLpu ml1 on ml1.id=p.lpu_id") ;
        	sqlGroup.append(" left join LpuAttachedByDepartment lp on lp.patient_id=p.id") ;
        	sqlGroup.append(" left join MisLpu ml2 on ml2.id=lp.lpu_id") ;
        	sqlGroup.append(" left join VocAttachedType vat on lp.attachedType_id=vat.id") ;
        	sqlGroup.append(" left join VocIdentityCard vic on vic.id=p.passportType_id") ;
        	sqlGroup.append(" left join REG_IC vri on vri.id=lp.company_id") ;
        	sqlGroup.append(" where ") ;
        	if (aCompany!=null&&aCompany!=0) sqlGroup.append("lp.company_id='").append(aCompany).append("' and ");
        	if (aLpuCheck) sqlGroup.append(" (p.lpu_id='").append(aLpu).append("' or lp.lpu_id='").append(aLpu).append("' or ml1.parent_id='").append(aLpu).append("' or ml2.parent_id='").append(aLpu).append("') and ") ;
        	if (aLpuCheck && aArea!=null &&aArea.intValue()>0) sqlGroup.append(" (p.lpuArea_id='").append(aArea).append("' or lp.area_id='").append(aArea).append("') and ") ;
        	sqlGroup.append(" (p.noActuality='0' or p.noActuality is null) and p.deathDate is null ");
        	sqlGroup.append(" ").append(addSql) ;
        	sqlGroup.append(" group by lp.company_id,vri.smocode") ;
        	sqlGroup.append(" order by lp.company_id,vri.smocode") ;
        	System.out.println("------------------- Need_DIVIDE_COMP = "+sqlGroup.toString());
        	List<Object[]> listComp = theManager.createNativeQuery(sqlGroup.toString())
        			.setMaxResults(90000).getResultList() ;
        	
        	for (Object[] comp:listComp) {
        	filename = "P"+aFilenameAddSuffix+aNReestr+"S"+(comp[1]==null?"-":comp[1])
        		+"_"+aPeriodByReestr+XmlUtil.namePackage(aNPackage) ;
        	filenames.append("#").append(filename+".xml") ;
        
        	sql.setLength(0);
        	sql.append("select ").append(fld);
        	sql.append(" ,p.id as pid, lp.id as lpid");
        	sql.append(" from Patient p") ;
        	sql.append(" left join MisLpu ml1 on ml1.id=p.lpu_id") ;
        	sql.append(" left join LpuAttachedByDepartment lp on lp.patient_id=p.id") ;
        	sql.append(" left join MisLpu ml2 on ml2.id=lp.lpu_id") ;
            sql.append(" left join VocAttachedType vat on lp.attachedType_id=vat.id") ;
        	sql.append(" left join VocIdentityCard vic on vic.id=p.passportType_id") ;
        	sql.append(" left join lpuArea la on la.id=lp.area_id") ;
        	sql.append(" left join mislpu ml3 on ml3.id= la.lpu_id");
        	sql.append(" left join workfunction wf on wf.id=la.workfunction_id");
        	sql.append(" left join worker w on w.id=wf.worker_id");
        	sql.append(" left join patient wp on wp.id=w.person_id");
        	
        	sql.append(" where ") ;
        	sql.append(" lp.company_id") ;
        	if (comp[0]!=null) {sql.append("=").append(comp[0]) ;} else {sql.append(" is null ") ;}
        	sql.append(" and ");
        	if (aLpuCheck) sql.append(" (p.lpu_id='").append(aLpu).append("' or lp.lpu_id='").append(aLpu).append("' or ml1.parent_id='").append(aLpu).append("' or ml2.parent_id='").append(aLpu).append("') and ") ;
        	if (aLpuCheck && aArea!=null &&aArea.intValue()>0) sql.append(" (p.lpuArea_id='").append(aArea).append("' or lp.area_id='").append(aArea).append("') and ") ;
        	sql.append(" (p.noActuality='0' or p.noActuality is null) and p.deathDate is null ");
        	sql.append(" ").append(addSql) ;
        	sql.append(" group by p.id, lp.id, ").append(fldGroup) ;
        	sql.append(" order by p.lastname,p.firstname,p.middlename,p.birthday") ;
        	System.out.println("------------------- Need_DIVIDE_PAT = "+sql.toString());
        	 listPat = theManager.createNativeQuery(sql.toString())
        			.setMaxResults(90000).getResultList() ;
        	 createXml(workDir, filename,aPeriodByReestr,aNReestr, listPat,props);
        	//xmlDoc.saveDocument(outFile) ;
        	}
    	} else {
    		filename = "P_"+aFilenameAddSuffix+aNReestr+"_"+aPeriodByReestr+XmlUtil.namePackage(aNPackage) ;
    		filenames.append("#").append(filename+".xml") ;
    		sql.setLength(0);
    		sql.append("select ").append(fld);
    		sql.append(" ,p.id as pid, lp.id as lpid");
            sql.append(" from Patient p") ;
        	sql.append(" left join MisLpu ml1 on ml1.id=p.lpu_id") ;
        	sql.append(" left join LpuAttachedByDepartment lp on lp.patient_id=p.id") ;
        	sql.append(" left join MisLpu ml2 on ml2.id=lp.lpu_id") ;
            sql.append(" left join VocAttachedType vat on lp.attachedType_id=vat.id") ;
        	sql.append(" left join VocIdentityCard vic on vic.id=p.passportType_id") ;
        	sql.append(" left join lpuArea la on la.id=lp.area_id") ;
        	sql.append(" left join mislpu ml3 on ml3.id=la.lpu_id");
        	sql.append(" left join workfunction wf on wf.id=la.workfunction_id");
        	sql.append(" left join worker w on w.id=wf.worker_id");
        	sql.append(" left join patient wp on wp.id=w.person_id");
        	
        	sql.append(" where ") ;
        	
        	if (aLpuCheck) sql.append(" (p.lpu_id='").append(aLpu).append("' or lp.lpu_id='").append(aLpu).append("' or ml1.parent_id='").append(aLpu).append("' or ml2.parent_id='").append(aLpu).append("') and ") ;
        	if (aLpuCheck && aArea!=null &&aArea.intValue()>0) sql.append(" (p.lpuArea_id='").append(aArea).append("' or lp.area_id='").append(aArea).append("') and ") ;
        	sql.append(" (p.noActuality='0' or p.noActuality is null) and p.deathDate is null ");
        	sql.append(" ").append(addSql) ;
        	sql.append(" group by p.id, lp.id, ").append(fldGroup) ;
        	sql.append(" order by p.lastname,p.firstname,p.middlename,p.birthday") ;
        	System.out.println("-------------------NO Need_DIVIDE_PAT = "+sql.toString());
        	listPat = theManager.createNativeQuery(sql.toString())
        			.setMaxResults(90000).getResultList() ;
        	 createXml(workDir, filename,aPeriodByReestr,aNReestr, listPat,props);
    	}
    	
    	res.set1(filenames.length()>0?filenames.substring(1):"");
    	return res;
    	//     	return (filenames.length()>0?filenames.substring(1):"")+(def.length()>0?"@"+def.toString():"");
    }
    public void createFondXml (String workDir, String filename, String aPeriodByReestr,String aNReestr, List<Object[]> listPat,String[][] aProps) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
    	XmlDocument xmlDoc = new XmlDocument() ;
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat format2 = new SimpleDateFormat("yyyy");
    	
    	Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
    	File outFile = new File(workDir+"/"+filename+".xml") ;
    	Element title = xmlDoc.newElement(root, "ZGLV", null);
 
    	xmlDoc.newElement(title, "PERIOD", aPeriodByReestr.substring(2,4));
    	xmlDoc.newElement(title, "N_REESTR", aNReestr);
    	xmlDoc.newElement(title, "FILENAME", filename);
    	/*
    	Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
    	File outFile = new File(workDir+"/"+filename+".xml") ;
    	Element title = xmlDoc.newElement(root, "ZGLV", null);
       	xmlDoc.newElement(title, "VERSION","1.0");
    	xmlDoc.newElement(title, "DATE", format.format(new java.util.Date()));
    	xmlDoc.newElement(title, "YEAR", format2.format(new java.util.Date()));
    	xmlDoc.newElement(title, "PERIOD", aPeriodByReestr.substring(2,4));
 //   	xmlDoc.newElement(title, "N_REESTR", aNReestr);
    	xmlDoc.newElement(title, "FILENAME", filename);
    	*/
    	int i=0 ;
    	for (Object[] pat:listPat) {
    		Element zap = xmlDoc.newElement(root, "ZAP", null);
    		xmlDoc.newElement(zap, "IDCASE", XmlUtil.getStringValue(++i)) ;
    		for(int ind=0;ind<aProps.length; ind++) {
    			String[] prop = aProps[ind] ; 
				xmlDoc.newElement(zap, prop[1], XmlUtil.getStringValue(pat[ind])) ;
				
			}
    		xmlDoc.newElement(zap, "REFREASON", "");
    	}
    	XmlUtil.saveXmlDocument(xmlDoc, outFile) ;
    }
    WebQueryResult res = new WebQueryResult();
    public void setExportDate (String aDate, String aAttachmentId) {
    	if (aDate==null||aDate.equals("")) {
    		aDate=DateFormat.formatToDate(new java.util.Date());
    	}
    	theManager.createNativeQuery("update lpuattachedbydepartment set exportDate=to_date('"+aDate+"','dd.MM.yyyy') where id="+aAttachmentId).executeUpdate();
    }
    public void createXml (String workDir, String filename, String aPeriodByReestr,String aNReestr
    		, List<Object[]> listPat, String[][] aProps
    		) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
    	XmlDocument xmlDoc = new XmlDocument() ;
    	
    	Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
    	File outFile = new File(workDir+"/"+filename+".xml") ;
    	Element title = xmlDoc.newElement(root, "ZGLV", null);
 
    	xmlDoc.newElement(title, "PERIOD", aPeriodByReestr.substring(2,4));
    	xmlDoc.newElement(title, "N_REESTR", aNReestr);
    	xmlDoc.newElement(title, "FILENAME", filename);
    	int i=0 ;
    	for (Object[] pat:listPat) {
    		int errorZap=0;
    		for(int j=0;j<aProps.length; j++) {
    			String[] prop = aProps[j] ;
    			if (!checkIsRequiedValueIsNotEmpty(pat[j],prop[3])) {
    				WebQueryResult ress = new WebQueryResult();
    				ress.set1(pat[pat.length-2]);
    				ress.set2(pat[pat.length-1]);
    				ress.set3("Пациент - "+pat[0]+" "+pat[1]+" "+pat[2]+". Неверно заполнено поле \""+prop[4]+"\" - "+pat[j]);
    				errList.add(ress);
    				//def.append(pat[pat.length-2]).append(":").append(pat[pat.length-1]).append(":").append("Пациент \"")
    				//.append("\". Неверно заполнено поле ").append(prop[4]).append(" - ").append(pat[ind]).append("#");
    				errorZap=1;
    			}
    		}
    		//System.out.println("Пациент - "+pat[0]+" "+pat[1]+" "+pat[2]+": ErrorZAP="+errorZap);
    		if (errorZap==0) {
    			Element zap = xmlDoc.newElement(root, "ZAP", null);
        		xmlDoc.newElement(zap, "IDCASE", XmlUtil.getStringValue(++i)) ;
	    		for(int ind=0;ind<aProps.length; ind++) {
	    			String[] prop = aProps[ind] ; 
					xmlDoc.newElement(zap, prop[1], XmlUtil.getStringValue(pat[ind])) ;
	    		}
	    		xmlDoc.newElement(zap, "REFREASON", "");
	    		setExportDate(null, pat[pat.length-1].toString());
	    	}
    		
    		
    	}
    	
    	XmlUtil.saveXmlDocument(xmlDoc, outFile) ;
    	res.set2(errList);
    }
    private boolean checkIsRequiedValueIsNotEmpty(Object aValue, String isRequid) {
    	if (isRequid!=null&&isRequid.equals("1")) {
    		if (aValue==null||aValue.toString().equals("")) return false;
    	}
    	return true;
    }
    public WebQueryResult export(String aAge, boolean aLpuCheck, Long aLpu, Long aArea, String aDateFrom, String aDateTo, String aPeriodByReestr, String aNReestr, String aNPackage) throws ParserConfigurationException, TransformerException {
    	return exportAll(aAge,"","",aLpuCheck, aLpu, aArea, aDateFrom,aDateTo, aPeriodByReestr, aNReestr, aNPackage);
    }

    public WebQueryResult exportNoAddress(String aAge, boolean aLpuCheck, Long aLpu, Long aArea, String aDateFrom, String aDateTo , String aPeriodByReestr, String aNReestr, String aNPackage) throws ParserConfigurationException, TransformerException {
    	StringBuilder addSql = new StringBuilder().append("and p.address_addressid is null") ;
    	return exportAll(aAge,"_no_addresss",addSql.toString(),aLpuCheck, aLpu, aArea, aDateFrom,aDateTo, aPeriodByReestr, aNReestr, aNPackage);
    }
    public void onRemove(LpuAreaAddressText aLpuAreaAddressText) {
        EntityManager manager = theManager ; //theFactory.createEntityManager() ;
        try {
        int count = manager.createQuery("delete from LpuAreaAddressPoint where lpuAreaAddressText = :lpuAreaAddressText")
                .setParameter("lpuAreaAddressText", aLpuAreaAddressText)
                .executeUpdate();
        if (CAN_DEBUG) LOG.debug("count deleted LpuAreaAddressPoint = " + count);

        // у пациента убрать по адресу участка привязку к ЛПУ и участку
        count = manager.createQuery("update Patient set lpu = null, lpuArea = null, lpuAreaAddressText = null where lpuAreaAddressText = :lpuAreaAddressText")
                .setParameter("lpuAreaAddressText", aLpuAreaAddressText)
                .executeUpdate();
        if (CAN_DEBUG) LOG.debug("count updated Patient = " + count);
        } finally {
            //manager.close() ;
        }
    }
    public void onPersist(LpuAreaAddressText aLpuAreaAddressText) {
        if (CAN_DEBUG) LOG.debug("aLpuAreaAddressText = " + aLpuAreaAddressText.getAddress());
        Address address = aLpuAreaAddressText.getAddress();
        EntityManager manager = theManager ; 
        // весь поселок
        if(address.getDomen()==5) {
            Query query = manager.createQuery("from Address where parent = :parent").setParameter("parent",address) ;
            Iterator<Address> iterator = QueryIteratorUtil.iterate(Address.class, query) ;
            // перебираем все улицы и прикрепляем
            while (iterator.hasNext()) {
                Address child = iterator.next();
                LpuAreaAddressPoint point = new LpuAreaAddressPoint();
                point.setLpuAreaAddressText(aLpuAreaAddressText);
                point.setAddress(child);
                manager.persist(point);
                updatePatients(aLpuAreaAddressText, child, null, null, null);
                manager.flush() ;
                manager.clear() ;
            }
        } else {
            List<AddressPointCheck> checks = thePointCheckHelper.parsePoints(aLpuAreaAddressText.getAddressString());
            // нет домов, прикрепляем по всех улице
            if (checks == null || checks.isEmpty()) {
                LpuAreaAddressPoint point = new LpuAreaAddressPoint();
                point.setLpuAreaAddressText(aLpuAreaAddressText);
                point.setAddress(address);
                manager.persist(point);
                updatePatients(aLpuAreaAddressText, address, null, null, null);
            } else {
            	// по домам , корпусам и квартирам
                for (AddressPointCheck check : checks) {
                    LpuAreaAddressPoint point = new LpuAreaAddressPoint();
                    point.setLpuAreaAddressText(aLpuAreaAddressText);
                    point.setAddress(address);
                    point.setHouseNumber(check.getNumber());
                    point.setHouseBuilding(check.getBuilding());
                    point.setFlat(check.getFlat());
                    manager.persist(point);
                    updatePatients(aLpuAreaAddressText, address, check.getNumber(), check.getBuilding(), check.getFlat());
                }
            }
        }
        manager.flush();
        manager.clear() ;
    }

    /**
     * Определение границы для детей
     * @return
     */
    public static Date getChildBoundaryDate() {
    	Calendar cal = Calendar.getInstance() ;
    	cal.setTime(new java.util.Date());
    	cal.add(Calendar.YEAR, -18);
    	java.util.Date utilDate = cal.getTime();
    	return new Date(utilDate.getTime());
    	
    }
    // todo обновить по адресу, дому и корпусу привязку пациентов
    private void updatePatients(LpuAreaAddressText aText, Address aAddress, String aNumber, String aBuilding, String aFlat) {
        if (CAN_DEBUG) LOG.debug("aText.getId() = " + aText.getId());
        LpuArea area = aText.getArea();
        if (CAN_DEBUG) LOG.debug("area.getId() = " + area.getId());
        StringBuilder sb = new StringBuilder(
                "update Patient set lpu = :lpu, lpuArea = :lpuArea, lpuAreaAddressText = :text " +
                " where attachedOmcPolicy_id is null and address = :address ");
        // дети или взрослые для терап. или педиатрического участков
        if(area.isPediatric()) {
        	sb.append(" and birthday > :childBoundaryDate") ;
        } else {
        	sb.append(" and birthday < :childBoundaryDate") ;
        }
        if (!StringUtil.isNullOrEmpty(aNumber)) {
            sb.append(" and houseNumber = :number ") ;
        }
        if(!StringUtil.isNullOrEmpty(aBuilding)) {
            sb.append(" and houseBuilding = :building") ;
        }
        if(!StringUtil.isNullOrEmpty(aFlat)) {
            sb.append(" and flatNumber = :flat") ;
        }

        EntityManager manager = theManager ;
        if (CAN_DEBUG) LOG.debug("sb = " + sb);
        Query query = manager.createQuery(sb.toString());
        if(!StringUtil.isNullOrEmpty(aNumber)) {
            query.setParameter("number", aNumber) ;
        }
        if(!StringUtil.isNullOrEmpty(aBuilding)) {
            query.setParameter("building", aBuilding) ;
        }
        if(!StringUtil.isNullOrEmpty(aFlat)) {
            query.setParameter("flat", aFlat) ;
        }
        query.setParameter("childBoundaryDate", getChildBoundaryDate());
        query.setParameter("address", aAddress) ;
        query.setParameter("lpu", area.getLpu().getId()) ;
        query.setParameter("lpuArea", area.getId()) ;
        query.setParameter("text", aText) ;

        int count = query.executeUpdate() ;
        if (CAN_DEBUG) LOG.debug("updated patients count = " + count);
    }

    @SuppressWarnings("unchecked")
	public void checkExists(long aLpuAreaId, Long aLpuAddressTextId, long aAddress, String aNumber, String aBuilding, String aFlat) {
        if(!StringUtil.isNullOrEmpty(aBuilding) && StringUtil.isNullOrEmpty(aNumber)) throw new IllegalArgumentException("При вводе корпуса необходимо указать номер дома") ;
        QueryClauseBuilder builder = new QueryClauseBuilder();
        builder.addIsNull("houseNumber",aNumber);
        builder.addIsNull("houseBuilding",aBuilding);
        builder.addIsNull("flat",aFlat);
        builder.addNot("lpuAreaAddressText_id",aLpuAddressTextId);
        builder.add("address_AddressId",aAddress);
        EntityManager manager = theManager ;
        Query query = builder.build(manager,"from LpuAreaAddressPoint where","") ;
        List<LpuAreaAddressPoint> list = query.getResultList();
        if(list!=null && !list.isEmpty()) {
            long areaTypeId = manager.find(LpuArea.class, aLpuAreaId).getType().getId() ;
            for (LpuAreaAddressPoint point : list) {
                if(point.getLpuAreaAddressText().getArea().getType().getId()==areaTypeId) {
                    String addressText = theAddressService.getAddressString(aAddress, aNumber, aBuilding, aFlat,null);
                    throw new IllegalStateException("Адрес " + addressText
                            + " уже существует у участка " + point.getLpuAreaAddressText().getArea().getName()
                            + " в ЛПУ " + point.getLpuAreaAddressText().getArea().getLpu().getName()
                            + " по адресу "+point.getLpuAreaAddressText().getAddressString());
                }
            }
        }

        // дом корпус кв
        if(!StringUtil.isNullOrEmpty(aNumber) && !StringUtil.isNullOrEmpty(aBuilding) && !StringUtil.isNullOrEmpty(aFlat)) {
            checkExists(aLpuAreaId, aLpuAddressTextId, aAddress, aNumber, aBuilding, null);
        // дом кв    
        } else if(!StringUtil.isNullOrEmpty(aNumber) && !StringUtil.isNullOrEmpty(aFlat)) {
            checkExists(aLpuAreaId, aLpuAddressTextId, aAddress, aNumber, null, null);
        // дом корпус    
        } else if(!StringUtil.isNullOrEmpty(aNumber) && !StringUtil.isNullOrEmpty(aBuilding)) {
            checkExists(aLpuAreaId, aLpuAddressTextId, aAddress, aNumber, null, null);
        // дом    
        } else if(!StringUtil.isNullOrEmpty(aNumber)) {
            checkExists(aLpuAreaId, aLpuAddressTextId, aAddress, null, null, null);
        }
    }

    // update Patient set lpuArea_id = null
    public void onUpdate(LpuAreaAddressText aLpuAreaAddressText) {
	//theManager.flush() ;
	//theManager.clear() ;
	
        onRemove(aLpuAreaAddressText);
        onPersist(aLpuAreaAddressText);
    }

    public void refresh() {
    	theManager.createQuery("delete from LpuAreaAddressPoint").executeUpdate() ;
    	Iterator<LpuAreaAddressText> texts = QueryIteratorUtil.iterate(LpuAreaAddressText.class, theManager.createQuery("from LpuAreaAddressText")) ;
    	while( texts.hasNext()) {
    		LpuAreaAddressText text = texts.next() ;
    		onUpdate(text);
    	}
    }

    private AddressPointCheckHelper thePointCheckHelper = new AddressPointCheckHelper();
    private @EJB IAddressService theAddressService;
    private @PersistenceContext EntityManager theManager;
//    private @PersistenceUnit EntityManagerFactory theFactory ;
}
