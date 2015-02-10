package ru.ecom.mis.ejb.service.disability;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.IllegalDataException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.util.ApplicationDataSourceHelper;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.mis.ejb.domain.disability.DisabilityDocument;
import ru.ecom.mis.ejb.domain.disability.DisabilityRecord;
import ru.ecom.mis.ejb.domain.disability.DisabilityReport;
import ru.ecom.mis.ejb.domain.disability.RegimeViolationRecord;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityDocumentCloseReason;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityStatus;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.disability.DisabilityDocumentForm;
import ru.ecom.mis.ejb.service.patient.QueryClauseBuilder;
import ru.ecom.poly.ejb.services.GroupByDate;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.date.AgeUtil;
import ru.nuzmsh.util.format.DateFormat;

/**
 * Сервис для работы с нетрудоспобностью
 * @author stkacheva
 *
 */
@Stateless
@Remote(IDisabilityService.class)
public class DisabilityServiceBean implements IDisabilityService  {

    private final static Logger LOG = Logger.getLogger(DisabilityServiceBean.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    
    public String exportLNByDate(String aDateStart, String aDateFinish, String aSocCode, String aSocPhone, String aSocEmail, String aOgrnCode, String aPacketNumber) throws ParseException, NamingException {
    	if (aDateFinish==null || aDateFinish.equals("")) {
    		aDateFinish = aDateStart;
    	}
    	String sqlAdd = "dd.issuedate between to_date('"+aDateStart+"','dd.mm.yyyy') and to_date('"+aDateFinish+"','dd.mm.yyyy') ";
    	return exportLN(sqlAdd, aSocCode, aSocPhone, aSocEmail, aOgrnCode, aPacketNumber);
    }
    
    public String exportLNByNumber (String aNumber) throws ParseException, NamingException {
    	String sqlAdd = "dd.number = '"+aNumber+"' ";
    	return exportLN(sqlAdd,"","","","","1");
    }
    
    public String exportLN(String sqlAdd, String aSocCode, String aSocPhone, String aSocEmail, String aOgrnCode, String aPacketNumber) throws ParseException, NamingException {
	String SQLreq; 
		
	SQLreq ="select dd.id as DDID"+
	",dd.patient_id as DD_PAT"+
	",dc.patient_id as DC_PAT"+
	",p.lastname as lastname"+
	",p.firstname as firstname"+
	",p.middlename as middlename"+
	",p.snils as snils"+
	",sex.omccode as sex"+
	",p.birthday as birthday"+
	",dd.number as ln_number"+
	",dd.issuedate as issuedate"+
	",dd.isclose,dd.closereason_id"+
	",dd.idc10_id as none_giagnosis"+
	",case when (dc.earlypregnancyregistration is true or dc.earlypregnancyregistration ='1') then '1' else '' end as preg12week"+
	",vdr.codef as REASON_1"+
	",dd.disabilitycase_id as none_discase"+
	",dd.documenttype_id as none_ln_type"+
	",case when (dc.placementservice is true or dc.placementservice ='1') then '1' else '' end as placementservice"+
	",dd.hospitalizedfrom as HOSPITAL_DT1"+
	",dd.hospitalizedto as HOSPITAL_DT2"+
	",dd.job as employer"+
	",mkb.code as diagnosis"+
	",dd.hospitalizednumber, dd.status_id"+
	",dd.duplicate_id as f_duplicate"+
	",vddp.code as f_primary"+
	",p1.lastname||' '||p1.firstname||' '||p1.middlename as serv1_fio"+
	",cast(to_char(dd.hospitalizedto,'yyyy')as int)-cast(to_char(p1.birthday,'yyyy')as int)+"+
	"	case when ((cast(to_char(dd.hospitalizedto,'MM')as int))-cast(to_char(p1.birthday,'MM')as int)<0) or"+
	"	((cast(to_char(dd.hospitalizedto,'MM')as int))-cast(to_char(p1.birthday,'MM')as int)=0 "+
	"	and ((cast(to_char(dd.hospitalizedto,'dd')as int))-cast(to_char(p1.birthday,'dd')as int)<0)) then -1 else 0 end as serv1_age"+
	",vkr1.code as serv1_relation"+
	",p2.lastname||' '||p2.firstname||' '||p2.middlename as serv2_fio"+
	",cast(to_char(dd.hospitalizedto,'yyyy')as int)-cast(to_char(p2.birthday,'yyyy')as int)+"+
	"	case when ((cast(to_char(dd.hospitalizedto,'MM')as int))-cast(to_char(p2.birthday,'MM')as int)<0) or"+
	"	((cast(to_char(dd.hospitalizedto,'MM')as int))-cast(to_char(p2.birthday,'MM')as int)=0 "+
	"	and ((cast(to_char(dd.hospitalizedto,'dd')as int))-cast(to_char(p2.birthday,'dd')as int)<0)) then -1 else 0 end as serv2_age "+
	",vkr2.code as serv2_relation "+
	",case when ml2.id is not null then ml2.name else ml1.name end as lpu_name "+
	",case when ml2.id is not null then ml2.printaddress else ml1.printaddress end as lpu_address "+
	",case when ml2.id is not null then ml2.ogrn else ml1.ogrn end as lpu_ogrn "+
	",vdr2.code as REASON2 "+
	",vdr3.code as REASON3 "+
	",dd.sanatoriumogrn as sanatoriumOgrn "+
	",dd.sanatoriumticketnumber as ticketNumber "+
	",dd.sanatoriumdatefrom as sanDateFrom "+
	",dd.sanatoriumdateto as sanDateTo "+
	",dd.mainworkdocumentnumber as osnWorkplaceNumber "+
	",dd2.number as prevDocument "+
	",dd3.number as nextDocument "+
	",dd.workcombotype_id as workcombotypeid "+
	",cast('' as varchar(1)) as serv1_mm "+ //заглушка
	",cast('' as varchar(1)) as serv2_mm "+ //заглушка
	",vddcr.codef as mseResult "+
	"from disabilitydocument dd " +
	"left join vocdisabilitydocumentclosereason vddcr on vddcr.id = dd.closereason_id "+
	"left join disabilitydocument dd3 on dd3.prevdocument_id=dd.id "+
	"left join disabilitydocument dd2 on dd2.id=dd.prevdocument_id "+
	"left join disabilitycase dc on dc.id=dd.disabilitycase_id "+
	"left join patient p on p.id=dc.patient_id "+
	"left join vocsex sex on sex.id=p.sex_id "+
	"left join vocidc10 mkb on mkb.id=dd.idc10final_id "+
	"left join vocdisabilityreason vdr on vdr.id=dd.disabilityreason_id "+
	"left join vocdisabilityreason vdr3 on vdr3.id=dd.disabilityreasonchange_id "+
	"left join kinsman k1 on k1.id=dc.nursingperson1_id "+
	"left join vockinsmanrole vkr1 on vkr1.id=k1.kinsmanrole_id "+
	"left join patient p1 on p1.id=k1.kinsman_id "+
	"left join kinsman k2 on k2.id=dc.nursingperson2_id "+
	"left join vockinsmanrole vkr2 on vkr2.id=k2.kinsmanrole_id "+
	"left join patient p2 on p2.id=k2.kinsman_id "+
	"left join statisticstub ss on ss.code=dd.hospitalizednumber and ss.year=cast(to_char(dd.hospitalizedfrom,'yyyy')as int) "+
	"left join medcase mc on mc.id=ss.medcase_id "+
	"left join mislpu ml1 on ml1.id=mc.lpu_id "+
	"left join mislpu ml2 on ml2.id=ml1.parent_id "+
	"left join vocdisabilityreason2 vdr2 on vdr2.id=dd.disabilityreason2_id "+
	"left join vocdisabilitydocumentprimarity vddp on vddp.id=dd.primarity_id "+
	"where "+sqlAdd +
	"order by dd.issuedate desc";
		
			System.out.println("Поиск записей:");
			System.out.println(SQLreq);
			
			return find_data(SQLreq, aSocCode, aSocPhone, aSocEmail, aOgrnCode, aPacketNumber);
	}
    
    private DataSource findDataSource() throws NamingException {
		return ApplicationDataSourceHelper.getInstance().findDataSource();
	}
    public String find_data(String SQLReq, String aSocCode, String aSocPhone, String aSocEmail, String aOgrnCode, String aPacketNumber) throws ParseException, NamingException {
		Statement statement = null;
			
			Element rootElement = new Element("LPU");
			Element rowOperation = new Element("OPERATION");
			Element rowSet = new Element("ROWSET");
			rowSet.setAttribute("LPU_OGRN",aOgrnCode).setAttribute("email", aSocEmail)
				.setAttribute("phone", aSocPhone).setAttribute("version_software","02.2015")
				.setAttribute("author","MedOS_team").setAttribute("software","MedOS")
				.setAttribute("version","");
			rootElement.addContent(rowOperation.addContent("SET"));
			rootElement.addContent(rowSet);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			try
			{
				DataSource ds = findDataSource();
				Connection dbh = ds.getConnection();
				statement = dbh.createStatement();
				ResultSet rs = statement.executeQuery(SQLReq);
				int numAll = 0;
				int rightNum=0;
				
				StringBuilder defect = new StringBuilder();	
				StringBuilder breach = new StringBuilder();
				breach.append("select rvr.datefrom as date, vrvt.codef as code from regimeviolationrecord rvr "+
						"left join vocregimeviolationtype vrvt on vrvt.id = regimeviolationtype_id "+
						"where rvr.disabilitydocument_id='");
				StringBuilder record = new StringBuilder();
				record.append("select wf.id as wfid, wfAdd.id as wfidAdd,dr.datefrom, dr.dateto, dr.regime_id "
						+ ", pw.lastname ||' '||pw.firstname||' '||pw.middlename as name, vwf.name as wf "
						+ ", pwAdd.lastname ||' '||pwAdd.firstname||' '||pwAdd.middlename as nameAdd, vwfAdd.name as wfAdd "
						+ ",ml.printname as lpuName, ml.printAddress as lpuAddress, ml.ogrn "
						+ "from disabilityrecord dr "
						+ "left join workfunction wf on wf.id=dr.workfunction_id "
						+ "left join vocworkfunction vwf on vwf.id=wf.workfunction_id "
						+ "left join worker w on w.id=wf.worker_id "
						+ "left join mislpu ml on ml.id=w.lpu_id "
						+ "left join patient pw on pw.id=w.person_id "
						+ "left join workfunction wfAdd on wfAdd.id=dr.workfunctionadd_id "
						+ "left join vocworkfunction vwfAdd on vwfAdd.id=wfAdd.workfunction_id "
						+ "left join worker wAdd on wAdd.id=wfAdd.worker_id "
						+ "left join patient pwAdd on pwAdd.id=wAdd.person_id "
						+ "where dr.disabilitydocument_id='");												
			while (rs.next()) 
				{
				numAll++;
				String ln = rs.getString("ln_number");
				String ln_id=rs.getString("ddid");
				String prevDocument= rs.getString("prevDocument");
				String surname = rs.getString("lastname");
				String name = rs.getString("firstname");
				String patronimic = rs.getString("middlename");
				String workcombotypeid = rs.getString("workcombotypeid");
				String placementService = rs.getString("placementService");
				String snils = rs.getString("snils");
				if (snils!=null) {
					snils=snils.replace("-", "");
					snils=snils.replace(" ", "");
				}
				Element rowRow = new Element("ROW");
				Element rowLpuLn = new Element("LpuLn");
				
			//	rowLpuLn.setAttribute("id", rs.getString("DDID"));
			//	rowLpuLn.setAttribute("_DELETE_", "THIS COMMENT!!!!!!!!!!");
				rowLpuLn.addContent(new Element("SNILS").addContent(snils));
				rowLpuLn.addContent(new Element("SURNAME").addContent(rs.getString("lastname")));
				rowLpuLn.addContent(new Element("NAME").addContent(rs.getString("firstname")));
				rowLpuLn.addContent(new Element("PATRONIMIC").addContent(rs.getString("middlename")));
				if (placementService!=null &&placementService.equals("1")) {
					rowLpuLn.addContent(new Element("EMPL_FLAG").addContent("3"));
				} else {
				if (workcombotypeid!=null &&!workcombotypeid.equals("")) {
					rowLpuLn.addContent(new Element("EMPLOYER").addContent(rs.getString("employer")));
					rowLpuLn.addContent(new Element("EMPL_FLAG").addContent("2"));
				}else {
					rowLpuLn.addContent(new Element("EMPLOYER").addContent(rs.getString("employer")));
					rowLpuLn.addContent(new Element("EMPL_FLAG").addContent("1"));
				}					
				} 
				rowLpuLn.addContent(new Element("LN_CODE").addContent(ln));
				if (prevDocument!=null &&!prevDocument.equals("")) {
					rowLpuLn.addContent(new Element("PREV_LN_CODE").addContent(prevDocument));
				}
				String primaryFlag = rs.getString("f_primary");
				if (primaryFlag.equals("1")) {
					rowLpuLn.addContent(new Element("PRIMARY_FLAG").addContent("1"));
				} else {
					rowLpuLn.addContent(new Element("PRIMARY_FLAG").addContent("0"));
				}
				String duplicateFlag = rs.getString("f_duplicate");
				if (duplicateFlag!=null&&!duplicateFlag.equals("")) {
					rowLpuLn.addContent(new Element("DUPLICATE_FLAG").addContent("1"));
				} else {
					rowLpuLn.addContent(new Element("DUPLICATE_FLAG").addContent("0"));
				}
				Element lnDate = new Element("LN_DATE").addContent(rs.getString("issuedate"));
				rowLpuLn.addContent(lnDate);
				
			//	rowLpuLn.addContent(new Element("LPU_NAME").addContent(rs.getString("lpu_Name")));
			//	rowLpuLn.addContent(new Element("LPU_ADDRESS").addContent(rs.getString("lpu_Address")));
			//	rowLpuLn.addContent(new Element("LPU_OGRN").addContent(rs.getString("lpu_OGRN")));
				rowLpuLn.addContent(new Element("BIRTHDAY").addContent(rs.getString("birthday")));
				rowLpuLn.addContent(new Element("GENDER").addContent(rs.getString("sex")!=null&&rs.getString("sex").equals("2")?"1":"0"));
				String reason1=rs.getString("reason_1");
				rowLpuLn.addContent(new Element("REASON1").addContent(reason1));
				
				String reason2 = rs.getString("reason2");
				if (reason2!=null&&!reason2.equals("")) {
					rowLpuLn.addContent(new Element("REASON2").addContent(reason2));
				}
				String reason3 = rs.getString("reason3");
				if (reason3!=null&&!reason3.equals("")) {
					rowLpuLn.addContent(new Element("REASON3").addContent(reason2));
				}
				String diagnosis = rs.getString("diagnosis");
				if (diagnosis!=null&&!diagnosis.equals("")) {
					rowLpuLn.addContent(new Element("DIAGNOS").addContent(diagnosis));
				}
				
				String parentCode = rs.getString("osnWorkplaceNumber");
				if (parentCode!=null&&!parentCode.equals("")){
					rowLpuLn.addContent(new Element("PARENT_CODE").addContent(parentCode));
				}
				String date1 = rs.getString("sanDateFrom");
				if (reason3!=null&&!reason3.equals("")) {
					if (date1!=null&&!date1.equals("")) {
						rowLpuLn.addContent(new Element("DATE1").addContent(parentCode));
					} else {
						defect.append(ln).append(":").append(ln_id).append(":Нет даты начала путевки, reason3=").append(reason3).append("#");
						continue;
					}
				}
				String date2=rs.getString("sanDateTo");
				if (date1!=null&&!date1.equals("")) {
					if (reason2!=null&& (reason2.equals("017")||reason2.equals("018")||reason2.equals("019"))) {
						rowLpuLn.addContent(new Element("DATE2").addContent(date2));
						rowLpuLn.addContent(new Element("VOUCHER_NO").addContent(rs.getString("ticketNumber")));
						rowLpuLn.addContent(new Element("VOUCHER_OGRN").addContent(rs.getString("sanatoriumOgrn")));
					}
				}
				//Родственник 1
				String serv1_age = rs.getString("serv1_age");
				String serv1_mm = rs.getString("serv1_mm");
				
				if (reason1!=null&& (reason1.equals("09")||reason1.equals("12")
						||reason1.equals("13")||reason1.equals("14")||reason1.equals("15"))) {
					if (serv1_age!=null&&!serv1_age.equals("")) {
						rowLpuLn.addContent(new Element("SERV1_AGE").addContent(serv1_age));
					} else {
						rowLpuLn.addContent(new Element("SERV1_MM").addContent(serv1_mm));
					}
					String serv1_rel_code = rs.getString("serv1_relation");
					if (serv1_rel_code!=null&&!serv1_rel_code.equals("")) {
						rowLpuLn.addContent(new Element("SERV1_RELATION_CODE").addContent(serv1_rel_code));	
					} else {
						defect.append(ln).append(":").append(ln_id).append("Не указана родственная связь!, reason1=").append(reason1).append("#");
						continue;
					}
					String serv1_fio = rs.getString("serv1_fio");
					if (serv1_fio!=null&&!serv1_fio.equals("")) {
						rowLpuLn.addContent(new Element("SERV1_FIO").addContent(serv1_fio));
					} else {
						defect.append(ln).append(":").append(ln_id).append(":Не указаны ФИО родственника №1!, reason1=").append(reason1).append("#");
						continue;
					}
					//Родственник 2
					String serv2_fio = rs.getString("serv2_fio");
					if (serv2_fio!=null&&!serv2_fio.equals("")) {
						String serv2_age = rs.getString("serv2_age");
						String serv2_mm = rs.getString("serv2_mm");
						if (serv2_age!=null&&!serv2_age.equals("0")) {
							rowLpuLn.addContent(new Element("SERV2_AGE").addContent(serv2_age));
						} else {
							rowLpuLn.addContent(new Element("SERV2_MM").addContent(serv2_mm));
						}
						String serv2_rel_code = rs.getString("serv2_relation");
						if (serv2_rel_code!=null&&!serv2_rel_code.equals("")) {
							rowLpuLn.addContent(new Element("SERV2_RELATION_CODE").addContent(serv2_rel_code));	
						} else {
							defect.append(ln).append(":").append(ln_id).append(":Не указана родственная связь родственника №2!, reason1=").append(reason1).append("#");
							continue;
						}
						rowLpuLn.addContent(new Element("SERV2_FIO").addContent(serv2_fio));
					}
					if (rs.getString("preg12week")!=null&&rs.getString("preg12week").equals("1")) {
						rowLpuLn.addContent(new Element("PREGN12W_FLAG").addContent("1"));
					}
				}
				String hospital_Dt1 = rs.getString("HOSPITAL_DT1");
				String hospital_Dt2 = rs.getString("HOSPITAL_DT2");	
				if ((hospital_Dt1!=null &&!hospital_Dt1.equals(""))&&
						(reason1!=null && (reason1.equals("09") ||reason1.equals("12") ||reason1.equals("13") ||
						reason1.equals("14") ||reason1.equals("15")))) {
					rowLpuLn.addContent(new Element("HOSPITAL_DT1").addContent(rs.getString("hospital_Dt1")));
					if (hospital_Dt2!=null&&!hospital_Dt2.equals("")) {
						rowLpuLn.addContent(new Element("HOSPITAL_DT2").addContent(rs.getString("hospital_Dt2")));
					} else {
						defect.append(ln).append(":").append(ln_id).append(":Не указана дата выписки (HOSPITAL_DT2), reason1=").append(reason1).append("#");
						continue;
						
					}
				}
				//Нарушение режима
				statement = dbh.createStatement();
				String SQLBreach = breach.toString()+ln_id+"' limit 1 ";
				ResultSet rs_breach = statement.executeQuery(SQLBreach);
				while (rs_breach.next()) {
					rowLpuLn.addContent(new Element("HOSPITAL_BREACH_CODE").addContent(rs_breach.getString("code")));
					rowLpuLn.addContent(new Element("HOSPITAL_BREACH_DT").addContent(rs_breach.getString("date")));
					break;
				}
				
				
				//МСЭ
				 
				statement = dbh.createStatement();
				ResultSet rsRecord = statement.executeQuery(record.toString()+ln_id+"' order by datefrom limit 3");
				 int i=0;
				 String returnDate = null;
				 String lpuName = null, lpuAddress=null, lpuOgrn=null;
				while (rsRecord.next()) {
					i++;
					String doc = rsRecord.getString("name");
					String docId = rsRecord.getString("wfid");
					String dateFrom = rsRecord.getString("dateFrom");
					String dateTo = rsRecord.getString("dateTo");
					String docWorkfunction = rsRecord.getString("wf");
					String doc2role = rsRecord.getString("wfAdd");
					String chairman = rsRecord.getString("nameAdd");
					String doc2id = rsRecord.getString("wfidAdd");
					rowLpuLn.addContent(new Element("TREAT"+i+"_DT1").addContent(dateFrom));
					rowLpuLn.addContent(new Element("TREAT"+i+"_DT2").addContent(dateTo));
					rowLpuLn.addContent(new Element("TREAT"+i+"_DOCTOR_ROLE").addContent(docWorkfunction));
					rowLpuLn.addContent(new Element("TREAT"+i+"_DOCTOR").addContent(doc));
					rowLpuLn.addContent(new Element("TREAT"+i+"_DOC_ID").addContent(docId));
					lpuName = rsRecord.getString("lpuName");
					lpuAddress = rsRecord.getString("lpuAddress");
					lpuOgrn = rsRecord.getString("ogrn");
					if (doc2id!=null&&!doc2id.equals("")){
						rowLpuLn.addContent(new Element("TREAT"+i+"_DOCTOR2_ROLE").addContent(doc2role));
						rowLpuLn.addContent(new Element("TREAT"+i+"_CHAIRMAN_VK").addContent(chairman));
						rowLpuLn.addContent(new Element("TREAT"+i+"_DOC2_ID").addContent(doc2id));
					}
					returnDate = dateTo;
					}
				if (lpuName!=null) {
					rowLpuLn.addContent(rowLpuLn.indexOf(lnDate)+1,new Element("LPU_OGRN").addContent(lpuOgrn));
					rowLpuLn.addContent(rowLpuLn.indexOf(lnDate)+1,new Element("LPU_ADDRESS").addContent(lpuAddress));
					rowLpuLn.addContent(rowLpuLn.indexOf(lnDate)+1,new Element("LPU_NAME").addContent(lpuName));
				} else {
					defect.append(ln).append(":").append(ln_id).append(":Не заполнено поле название ЛПУ!!!#");
					continue;
				}
				if (returnDate!=null) {
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(format.parse(returnDate));
					cal.add(Calendar.DAY_OF_MONTH, 1);
					returnDate = new java.sql.Date(cal.getTime().getTime()).toString();
					rowLpuLn.addContent(new Element("RETURN_DATE_LPU").addContent(returnDate));
				} else {
					if (rs.getString("mseResult")!=null&&!rs.getString("mseResult").equals("")) {
						rowLpuLn.addContent(rowLpuLn.indexOf(rowLpuLn.getChild("TREAT1_DT1"))-1, new Element("MSE_RESULT").addContent(rs.getString("mseResult")));
					} else {
						defect.append(ln).append(":").append(ln_id).append(":Return date = null & mseResult=null!!!#");
						continue;
					}
				}
				
				String nextDocument = rs.getString("nextDocument");
				if (nextDocument!=null &&!nextDocument.equals("")) {
					rowLpuLn.addContent(new Element("NEXT_LN_CODE").addContent(nextDocument));
				}
				
			//	rowLpuLn.addContent(new Element("LPU_OGRN").addContent(rs.getString("lpu_OGRN")));
				
				rowLpuLn.addContent(new Element("LN_VERSION").addContent("1"));
				rowRow.addContent(rowLpuLn);
				rowSet.addContent(rowRow);
				//rootElement.addContent(rowRow);
			rightNum++;
				}
			dbh.close();
			System.out.println("Всего записей = " + numAll);
			System.out.println("Верных записей = " + rightNum);
			if (defect.length()>0) {
				System.out.println("Дефекты: "+defect.toString());
			}
			System.out.println();
			
		//	return rootElement;
			if (rootElement.getChildren().isEmpty()) {
				return "Записей не найдено";
			} else {
				return createNewFile(rootElement, aSocCode, aPacketNumber)+"@"+defect.toString();
			}
			
			}
			
		catch (SQLException e)
			{
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	    	return "Find_data: SQLException";
			}	
		
		}
    
	public static String createNewFile(Element rootElement, String aSocCode, String aPacketNumber) {
		try 
		{
			EjbEcomConfig config = EjbEcomConfig.getInstance() ;
			String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
	    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
			System.out.println("CreateNewFile, hashcode="+rootElement.hashCode());
			XMLOutputter outputter = new XMLOutputter();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
			format.format(new java.sql.Date(cal.getTime().getTime()));
			
			
			String filename="L_"+aSocCode+"_"+format.format(new java.sql.Date(cal.getTime().getTime()))+"_"+aPacketNumber+".xml";
			String outputFile=workDir+"/"+filename;
			FileWriter fwrt = new FileWriter(outputFile);
			Document pat = new Document(rootElement);
			outputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
			outputter.output(pat, fwrt);
			fwrt.close();
			System.out.println("CreateNewFile, fileName="+filename);
			return filename;
		}
	
		 catch (Exception ex) 
		{
			 System.out.println("E_CreateNewfile: "+ex.getMessage());
			 return "ERROR";
	        }
	}
    		
    public void createF16vn(String aDateStart,String aDateEnd) {
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select ") ;
    	sql.append(" dc.id ") ;
    	sql.append(" , coalesce(mkb.code,mkb1.code) as mkb") ;
    	sql.append(" , coalesce(vdr1.codeF,vdr.codeF) as reason") ;
    	sql.append(" , vs.omcCode") ;
    	sql.append(" , p.birthDay");
    	sql.append(" , (select max(dr1.dateTo) from DisabilityRecord dr1 left join DisabilityDocument dd1 on dd1.id=dr1.disabilityDocument_id where dd1.disabilityCase_id=dd.disabilityCase_id)") ;
    	sql.append(" - (select min(dr2.dateFrom) from DisabilityRecord dr2 left join DisabilityDocument dd1 on dd1.id=dr2.disabilityDocument_id where dd1.disabilityCase_id=dd.disabilityCase_id)") ;
    	sql.append(" + 1 as duration") ;
    	sql.append(" ,dd.issueDate") ;
    	sql.append(" from DisabilityDocument dd") ;
    	sql.append(" left join DisabilityCase dc on dc.id=dd.disabilityCase_id") ;
    	sql.append(" left join Patient p on p.id=dc.patient_id") ;
    	sql.append(" left join VocSex vs on vs.id=p.sex_id") ;
    	sql.append(" left join VocDisabilityStatus vds on vds.id=dd.status_id") ;
    	sql.append(" left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id") ;
    	sql.append(" left join VocDisabilityReason vdr1 on vdr.id=dd.disabilityReasonChange_id") ;
    	sql.append(" left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id") ;
    	sql.append(" left join VocIdc10 mkb on mkb.id=dd.idc10Final_id") ;
    	sql.append(" left join VocIdc10 mkb1 on mkb1.id=dd.idc10_id") ;
    	sql.append(" where dd.issueDate between to_date('").append(aDateStart)
    		.append("','dd.mm.yyyy') and to_date('").append(aDateEnd).append("','dd.mm.yyyy')") ;
    	sql.append("  and duplicate_id is null and vddcr.code='1' and workComboType_id is null") ;
    	sql.append(" order by dc.id,dd.issueDate desc") ;
    	System.out.println("sql--->"+sql) ;
    	List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
    	
    	System.out.println("cnt--->"+list.size()) ;
    	Long dcaseold=Long.valueOf(0) ;
    	for (Object[] obj:list) {
    		String str = "" ;
    		Long dcase = ConvertSql.parseLong(obj[0]) ;
    		if (dcase!=dcaseold) {
    			dcaseold=dcase ;
    		
    		String mkb = ""+obj[1] ;
    		String sex = ""+obj[3] ;
    		String reason = ""+obj[2] ;
    		System.out.println("mkb--->"+mkb) ;
    		System.out.println("sex--->"+sex) ;
    		System.out.println("reason--->"+reason) ;
    		if (reason.equals("09")) {
    			if (sex.equals("1")) {str=str+","+"96,102" ;} else {str=str+","+"97,103" ;}
    		} else {if (reason.equals("08")) {
    			if (sex.equals("1")) {str=str+","+"98,102" ;} else {str=str+","+"99,103" ;}
    		} else {if (reason.equals("03")) {
    			if (sex.equals("1")) {str=str+","+"100,102" ;} else {str=str+","+"101,103" ;}
    		} else {if (reason.equals("05")) {
    			if (sex.equals("1")) {str=str+","+"104" ;} else {str=str+","+"104" ;}
    		} else { 
        		if (ConvertSql.ChInt("A00-B99",mkb)) {if (sex.equals("1")) {str=str+","+"1" ;} else {str=str+","+"2" ;}}
        		if (ConvertSql.ChInt("A00-A09",mkb)) {if (sex.equals("1")) {str=str+","+"3" ;} else {str=str+","+"4" ;}}
        		if (ConvertSql.ChInt("A15-A19",mkb)) {if (sex.equals("1")) {str=str+","+"5" ;} else {str=str+","+"6" ;}}
        		if (ConvertSql.ChInt("B15-B19",mkb)) {if (sex.equals("1")) {str=str+","+"7" ;} else {str=str+","+"8" ;}}
        		if (ConvertSql.ChInt("C00-D48",mkb)) {if (sex.equals("1")) {str=str+","+"9" ;} else {str=str+","+"10" ;}}
        		if (ConvertSql.ChInt("C00-D09",mkb)) {if (sex.equals("1")) {str=str+","+"11" ;} else {str=str+","+"12" ;}}
        		if (ConvertSql.ChInt("D50-D89",mkb)) {if (sex.equals("1")) {str=str+","+"13" ;} else {str=str+","+"14" ;}}
        		if (ConvertSql.ChInt("E00-E90",mkb)) {if (sex.equals("1")) {str=str+","+"15" ;} else {str=str+","+"16" ;}}
        		if (ConvertSql.ChInt("E10-E14",mkb)) {if (sex.equals("1")) {str=str+","+"17" ;} else {str=str+","+"18" ;}}
        		if (ConvertSql.ChInt("E10",mkb)) {if (sex.equals("1")) {str=str+","+"19" ;} else {str=str+","+"20" ;}}
        		if (ConvertSql.ChInt("F00-F99",mkb)) {if (sex.equals("1")) {str=str+","+"21" ;} else {str=str+","+"22" ;}}
        		if (ConvertSql.ChInt("G00-G99",mkb)) {if (sex.equals("1")) {str=str+","+"23" ;} else {str=str+","+"24" ;}}
        		if (ConvertSql.ChInt("G50-G72",mkb)) {if (sex.equals("1")) {str=str+","+"25" ;} else {str=str+","+"26" ;}}
        		if (ConvertSql.ChInt("H00-H59",mkb)) {if (sex.equals("1")) {str=str+","+"27" ;} else {str=str+","+"28" ;}}
        		if (ConvertSql.ChInt("H60-H95",mkb)) {if (sex.equals("1")) {str=str+","+"29" ;} else {str=str+","+"30" ;}}
        		if (ConvertSql.ChInt("J00-J99",mkb)) {if (sex.equals("1")) {str=str+","+"31" ;} else {str=str+","+"32" ;}}
        		if (ConvertSql.ChInt("J00-J09",mkb)) {if (sex.equals("1")) {str=str+","+"33" ;} else {str=str+","+"34" ;}}
        		if (ConvertSql.ChInt("J10-J13",mkb)) {if (sex.equals("1")) {str=str+","+"35" ;} else {str=str+","+"36" ;}}
        		if (ConvertSql.ChInt("J20-J25",mkb)) {if (sex.equals("1")) {str=str+","+"37" ;} else {str=str+","+"38" ;}}
        		if (ConvertSql.ChInt("J60-J69",mkb)) {if (sex.equals("1")) {str=str+","+"39" ;} else {str=str+","+"40" ;}}
        		if (ConvertSql.ChInt("J00-J99",mkb)) {if (sex.equals("1")) {str=str+","+"41" ;} else {str=str+","+"42" ;}}
        		if (ConvertSql.ChInt("J00,J01,J04,J05,J06",mkb)) {if (sex.equals("1")) {str=str+","+"43" ;} else {str=str+","+"44" ;}}
        		if (ConvertSql.ChInt("J02-J03",mkb)) {if (sex.equals("1")) {str=str+","+"45" ;} else {str=str+","+"46" ;}}
        		if (ConvertSql.ChInt("J10-J11",mkb)) {if (sex.equals("1")) {str=str+","+"47" ;} else {str=str+","+"48" ;}}
        		if (ConvertSql.ChInt("J12-J18",mkb)) {if (sex.equals("1")) {str=str+","+"49" ;} else {str=str+","+"50" ;}}
        		if (ConvertSql.ChInt("J40-J43",mkb)) {if (sex.equals("1")) {str=str+","+"51" ;} else {str=str+","+"52" ;}}
        		if (ConvertSql.ChInt("J45,J46",mkb)) {if (sex.equals("1")) {str=str+","+"53" ;} else {str=str+","+"54" ;}}
        		if (ConvertSql.ChInt("J60,J66",mkb)) {if (sex.equals("1")) {str=str+","+"55" ;} else {str=str+","+"56" ;}}
        		if (ConvertSql.ChInt("K00-K93",mkb)) {if (sex.equals("1")) {str=str+","+"57" ;} else {str=str+","+"58" ;}}
        		if (ConvertSql.ChInt("K25-K26",mkb)) {if (sex.equals("1")) {str=str+","+"59" ;} else {str=str+","+"60" ;}}
        		if (ConvertSql.ChInt("K29",mkb)) {if (sex.equals("1")) {str=str+","+"61" ;} else {str=str+","+"62" ;}}
        		if (ConvertSql.ChInt("K70-K86",mkb)) {if (sex.equals("1")) {str=str+","+"63" ;} else {str=str+","+"64" ;}}
        		if (ConvertSql.ChInt("L00-L99",mkb)) {if (sex.equals("1")) {str=str+","+"65" ;} else {str=str+","+"66" ;}}
        		if (ConvertSql.ChInt("L00-L08",mkb)) {if (sex.equals("1")) {str=str+","+"67" ;} else {str=str+","+"68" ;}}
        		if (ConvertSql.ChInt("M00-M99",mkb)) {if (sex.equals("1")) {str=str+","+"69" ;} else {str=str+","+"70" ;}}
        		if (ConvertSql.ChInt("M05-M06,M08.0",mkb)) {if (sex.equals("1")) {str=str+","+"71" ;} else {str=str+","+"72" ;}}
        		if (ConvertSql.ChInt("N00-N99",mkb)) {if (sex.equals("1")) {str=str+","+"73" ;} else {str=str+","+"74" ;}}
        		if (ConvertSql.ChInt("N00-N39",mkb)) {if (sex.equals("1")) {str=str+","+"75" ;} else {str=str+","+"76" ;}}
        		if (ConvertSql.ChInt("N70-N76",mkb)) {str=str+","+"77" ;}
        		if (ConvertSql.ChInt("Q00-Q99",mkb)) {str=str+","+"78" ;}
        		if (ConvertSql.ChInt("O00-O99",mkb)) {if (sex.equals("1")) {str=str+","+"79" ;} else {str=str+","+"80" ;}}
        		if (ConvertSql.ChInt("R00-R99",mkb)) {if (sex.equals("1")) {str=str+","+"81" ;} else {str=str+","+"82" ;}}
        		if (ConvertSql.ChInt("S00-T98",mkb)) {if (sex.equals("1")) {str=str+","+"83" ;} else {str=str+","+"84" ;}}
        		if (ConvertSql.ChInt("S00,S10,S20,S30,S40,S50,S60,S70,S80,S90,T00,T09.0,T11.0,T13.0,T14.0",mkb)) {if (sex.equals("1")) {str=str+","+"85" ;} else {str=str+","+"86" ;}}
        		if (ConvertSql.ChInt("S02,S06",mkb)) {if (sex.equals("1")) {str=str+","+"87" ;} else {str=str+","+"88" ;}}
        		if (ConvertSql.ChInt("S42,S52,S62,S72,S82,S92,T02.2-T02.6,T10,T12",mkb)) {if (sex.equals("1")) {str=str+","+"89" ;} else {str=str+","+"90" ;}}
        		if (ConvertSql.ChInt("S03,S13,S23,S33,S43,S53,S63,S73,S83,S93,T03,T09.2,T11.2,T13.2,T14.3",mkb)) {if (sex.equals("1")) {str=str+","+"91" ;} else {str=str+","+"92" ;}}
        		if (sex.equals("1")) {str=str+","+"93,102" ;} else {str=str+","+"94,103" ;}
        		if (ConvertSql.ChInt("O03-O07",mkb)) {str=str+","+"95" ;}
	    		//if (ConvertSql.ChInt("",mkb)) {if (sex.equals("1")) {str=str+","+"" ;} else {str=str+","+"" ;}}
	    		//if (ConvertSql.ChInt("",mkb)) {if (sex.equals("1")) {str=str+","+"" ;} else {str=str+","+"" ;}}
	    		//if (ConvertSql.ChInt("",mkb)) {if (sex.equals("1")) {str=str+","+"" ;} else {str=str+","+"" ;}}
    		}}}}
    		System.out.println("str--->"+str) ;
    		if (!str.equals("")) {
    			str = str.substring(1) ;
    			String[] strs = str.split(",") ;
    			theManager.createNativeQuery("delete from DisabilityReport where caseR='"+obj[0]+"'").executeUpdate() ;
    			if (strs.length>0) {
	    			for (String s:strs) {
		    			System.out.println("line--->"+s) ;
		    			System.out.println("case--->"+dcase) ;
	    				DisabilityReport dr = new DisabilityReport() ;
	    				dr.setLineR(Long.valueOf(s)) ;
	    				dr.setCaseR(dcase) ;
	    				theManager.persist(dr) ;
	    			}
	    			String age = AgeUtil.getAgeCache((java.sql.Date)obj[6], (java.sql.Date)obj[4], 0);
	    			System.out.println("age--->"+age) ;
	    			System.out.println("duration--->"+obj[5]) ;
	    			theManager.createNativeQuery("update DisabilityCase set agePatient='"+age+"', durationCase='"+obj[5]+"' where id='"+obj[0]+"'").executeUpdate() ;
    			}
    		}
    		}
    			
    		
    	}
    }
    public Long createDuplicateDocument( Long aDocId,Long aReasonId, String aSeries, String aNumber,Long aWorkFunction2
    		,String aJob, Boolean aUpdateJob){
    	DisabilityDocument doc = theManager.find(DisabilityDocument.class, aDocId) ;
    	if (doc.getIsClose()==null || doc.getIsClose()==false) {
    		throw new IllegalDataException("ДУБЛИКАТ МОЖНО СДЕЛАТЬ ТОЛЬКО ЗАКРЫТОГО ДОКУМЕНТА!!!") ;
    	}
    	if (doc.getStatus()==null || !doc.getStatus().getCode().equals("0")) {
    		throw new IllegalDataException("ДУБЛИКАТ МОЖНО СДЕЛАТЬ ТОЛЬКО ДЕЙСТВУЮЩЕГО ДОКУМЕНТА!!!") ;
    	}
    	
    	VocDisabilityStatus stat = theManager.find(VocDisabilityStatus.class, aReasonId) ;
    	DisabilityDocument newDoc = copyDocument(doc, aSeries, aNumber,new java.sql.Date(new java.util.Date().getTime()),aWorkFunction2) ;
    	if (aJob!=null && !aJob.equals("")) {
    		aJob = aJob.trim().toUpperCase() ;
    		newDoc.setJob(aJob) ;
    		if (aUpdateJob!=null &&aUpdateJob==true) {
    			Patient pat = doc.getDisabilityCase()!=null && doc.getDisabilityCase().getPatient()!=null?doc.getDisabilityCase().getPatient():null ;
    			//VocOrg org= pat.getWorks() ;
    			//org.setCode(aJob) ;
    			pat.setWorks(aJob) ;
    			theManager.persist(pat) ;
    		}
        	
    	}
    	
    	doc.setStatus(stat) ;
    	doc.setNoActuality(true) ;
    	doc.setDuplicate(newDoc) ;
    	//if (stat!=null && stat.getCode().trim().equals("2")) {
    	//	List<VocDisabilityDocumentPrimarity> primarity = theManager.createQuery("from VocDisabilityDocumentPrimarity where code='2'").getResultList() ;
    	//	newDoc.setPrimarity(primarity.size()>0?primarity.get(0):null);
    	//	theManager.persist(newDoc) ;
    	//}
    	theManager.persist(doc) ;
    	return newDoc.getId() ;
    	
    }
    public Long createWorkComboDocument(Long aDocId,String aJob, String aSeries, String aNumber){
    	DisabilityDocument doc = theManager.find(DisabilityDocument.class, aDocId) ;
    			
    	if (doc.getWorkComboType()!=null) {
    		throw new IllegalDataException("БЛАНК ПО СОВМЕСТИТЕЛЬСТВУ МОЖНО ДОБАВИТЬ ТОЛЬКО ПО ОСНОВНОМУ МЕСТУ РАБОТЫ") ;
    	}
    	if (doc.getIsClose()==null || doc.getIsClose()==false) {
    		throw new IllegalDataException("БЛАНК ПО СОВМЕСТИТЕЛЬСТВУ МОЖНО СДЕЛАТЬ ТОЛЬКО ЗАКРЫТОГО ДОКУМЕНТА!!!") ;
    	}
    	if (doc.getStatus()==null || !doc.getStatus().getCode().equals("0")) {
    		throw new IllegalDataException("БЛАНК ПО СОВМЕСТИТЕЛЬСТВУ МОЖНО СДЕЛАТЬ ТОЛЬКО ДЕЙСТВУЮЩЕГО ДОКУМЕНТА!!!") ;
    	}
    	
    	DisabilityDocument newDoc = copyDocument(doc, aSeries, aNumber,new java.sql.Date(new java.util.Date().getTime()),null) ;
    	
    	newDoc.setJob(aJob.trim().toUpperCase()) ;
    	newDoc.setMainWorkDocumentNumber(doc.getNumber()) ;
    	newDoc.setMainWorkDocumentSeries(doc.getSeries()) ;
    	//newDoc.setNoActuality(doc.getNoActuality()) ;   	
    	theManager.persist(newDoc) ;
    	return newDoc.getId() ;
    }
    
    private DisabilityDocument copyDocument(DisabilityDocument aDocument, String aSeries, String aNumber,Date aIssuedDate,Long aWorkFunction2) {
    	WorkFunction wf2 = aWorkFunction2!=null?theManager.find(WorkFunction.class,aWorkFunction2):null ;
    	DisabilityDocument newDoc = new DisabilityDocument() ;
    	newDoc.setAnotherLpu(aDocument.getAnotherLpu()) ;
    	newDoc.setBeginWorkDate(aDocument.getBeginWorkDate()) ;
    	newDoc.setCloseReason(aDocument.getCloseReason()) ;
    	newDoc.setCreateDate(new java.sql.Date(new java.util.Date().getTime())) ;
    	newDoc.setCreateUsername(theContext.getCallerPrincipal().toString() ) ;
    	newDoc.setDiagnosis(aDocument.getDiagnosis()) ;
    	newDoc.setDisabilityCase(aDocument.getDisabilityCase()) ;
    	newDoc.setDisabilityReason(aDocument.getDisabilityReason()) ;
    	newDoc.setDisabilityReason2(aDocument.getDisabilityReason2()) ;
    	newDoc.setDisabilityReasonChange(aDocument.getDisabilityReasonChange()) ;
    	//TODO newDoc.setDisabilityRecords(doc.getDisabilityRecords()) ;
    	newDoc.setDisabilityRegime(aDocument.getDisabilityRegime()) ;
    	newDoc.setDocumentType(aDocument.getDocumentType()) ;
    	//newDoc.setEditDate(doc.getEditDate()) ;
    	//newDoc.setEditUsername(doc.getEditUsername()) ;
    	newDoc.setHospitalizedFrom(aDocument.getHospitalizedFrom()) ;
    	newDoc.setHospitalizedNumber(aDocument.getHospitalizedNumber()) ;
    	newDoc.setHospitalizedTo(aDocument.getHospitalizedTo()) ;
    	newDoc.setHospitalizedNumber(aDocument.getHospitalizedNumber()) ;
    	newDoc.setIdc10(aDocument.getIdc10()) ;
    	newDoc.setIsClose(aDocument.getIsClose()) ;
    	if (aIssuedDate!=null) {
    		newDoc.setIssueDate(aIssuedDate) ;
    	} else {
    		newDoc.setIssueDate(aDocument.getIssueDate()) ;
    	}
    	
    	newDoc.setJob(aDocument.getJob()) ;
    	newDoc.setMainWorkDocumentNumber(aDocument.getMainWorkDocumentNumber()) ;
    	newDoc.setMainWorkDocumentSeries(aDocument.getMainWorkDocumentSeries()) ;
    	newDoc.setNoActuality(aDocument.getNoActuality()) ;
    	newDoc.setNumber(aNumber) ;
    	newDoc.setNursedPatient(aDocument.getNursedPatient()) ;
    	newDoc.setPatient(aDocument.getPatient()) ;
    	newDoc.setPrevDocument(aDocument.getPrevDocument()) ;
    	newDoc.setPrimarity(aDocument.getPrimarity()) ;
    	// newDoc.setRegimeViolationRecords(doc.getRegimeViolationRecords()) ;
    	newDoc.setSanatoriumDateFrom(aDocument.getSanatoriumDateFrom()) ;
    	newDoc.setSanatoriumDateTo(aDocument.getSanatoriumDateTo()) ;
    	newDoc.setSanatoriumOgrn(aDocument.getSanatoriumOgrn()) ;
    	newDoc.setSanatoriumPlace(aDocument.getSanatoriumPlace()) ;
    	newDoc.setSanatoriumTicketNumber(aDocument.getSanatoriumTicketNumber()) ;
    	newDoc.setSeries(aSeries) ;
    	newDoc.setStatus(aDocument.getStatus()) ;
    	newDoc.setSupposeBirthDate(aDocument.getSupposeBirthDate()) ;
    	newDoc.setWorkComboType(aDocument.getWorkComboType()) ;
    	newDoc.setWorks(aDocument.getWorks()) ;
    	theManager.persist(newDoc) ;
    	//newDoc.set(doc.get) ;
    	//newDoc.set(doc.get) ;
    	List<DisabilityRecord> list1 = new ArrayList<DisabilityRecord>() ;
    	for (int i=0;i<aDocument.getDisabilityRecords().size();i++) {
    		DisabilityRecord old = aDocument.getDisabilityRecords().get(i) ;
    		DisabilityRecord record = new DisabilityRecord() ;
    		record.setDateFrom(old.getDateFrom()) ;
    		record.setDateTo(old.getDateTo()) ;
    		record.setDisabilityDocument(newDoc) ;
    		record.setRegime(old.getRegime()) ;
    		record.setWorkFunction(old.getWorkFunction()) ;
    		if (wf2==null){
    			record.setWorkFunctionAdd(old.getWorkFunctionAdd()) ;
    		} else {
    			record.setWorkFunctionAdd(wf2) ;
    		}
    		record.setCreateMedCase(old.getCreateMedCase()) ;
    		//record.set(old.get) ;
    		//record.set(old.get) ;
    		list1.add(record) ;
    	}
    	List<RegimeViolationRecord> list2 = new ArrayList<RegimeViolationRecord>() ;
    	for (int i=0;i<aDocument.getRegimeViolationRecords().size();i++) {
    		RegimeViolationRecord old = aDocument.getRegimeViolationRecords().get(0) ;
    		RegimeViolationRecord record = new RegimeViolationRecord() ;
    		record.setComment(old.getComment()) ;
    		record.setDateFrom(old.getDateFrom()) ;
    		record.setDateTo(old.getDateTo()) ;
    		record.setDisabilityDocument(old.getDisabilityDocument()) ;
    		record.setRegimeViolationType(old.getRegimeViolationType()) ;
    		//record.set(old.get) ;
    		list2.add(record) ;
    	}
    	newDoc.setDisabilityRecords(list1) ;
    	newDoc.setRegimeViolationRecords(list2) ;
    	theManager.persist(newDoc) ;
    	return newDoc ;

    }

    /**
     * Получить основные сведения для закрытия документа
     */
    public String getDataByClose(Long aDocumentId) {
    	DisabilityDocument doc = theManager.find(DisabilityDocument.class, aDocumentId) ;
    	StringBuilder ret = new StringBuilder() ;
    	ret.append(doc.getSeries()).append("#") ;
    	
    	if (doc.getNumber()!=null) ret.append(doc.getNumber()) ;
    	ret.append("#") ;
    	if (doc.getCloseReason()!=null) {
    		ret.append(doc.getCloseReason().getId()).append("#").append(doc.getCloseReason().getName()) ;
    	} else {
    		ret.append(0).append("#Выберите причину закрытия") ;
    	}
    	return ret.toString() ;
    	
    }
    
    public String closeDisabilityDocument(Long aDocumentId, Long aReasonId,String aSeries,String aNumber) {
		DisabilityDocument doc = theManager.find(DisabilityDocument.class, aDocumentId) ;
		if (doc.getDateTo()==null) {
			throw new IllegalStateException("Нельзя закрыть документ, так как есть не закрытое продление!") ;  
		}
		VocDisabilityDocumentCloseReason reason = theManager.find(VocDisabilityDocumentCloseReason.class, aReasonId) ;
		doc.setCloseReason(reason) ;
		doc.setSeries(aSeries) ;
		doc.setNumber(aNumber) ;
		doc.setIsClose(Boolean.TRUE) ;
		theManager.persist(doc) ;
		return reason.getName() ;
	}
	public List<DisabilityDocumentForm> findDocumentBySeriesAndNumber(String aFind) {
        if (CAN_DEBUG) {
            LOG.debug("findMedcard() Number and series = " + aFind );
        }
        
        if (aFind ==null || aFind.trim().equals(""))  { 
        	return new LinkedList<DisabilityDocumentForm>();
        } else {
        	QueryClauseBuilder builder = new QueryClauseBuilder();
        	aFind = aFind.trim() ;
        	int ind = aFind.indexOf(" ") ;
        	String series = null ;
        	String number = "" ;
        	if (ind==-1) {
        		number = aFind ;
        	} else {
    	        series =  aFind.substring(0,ind) ;
    	        number = aFind.substring(aFind.indexOf(" ")+1);
        		
        	}
	        if (series!=null) builder.addLike("series", "%"+series+"%");
	        builder.addLike("number", number+"%");
	        Query query = builder.build(theManager, "from DisabilityDocument where", " order by Number");
	        return createList(query);
        }
	}
	
	private List<DisabilityDocumentForm> createList(Query aQuery) {
		List<DisabilityDocument> list = aQuery.setMaxResults(50).getResultList();
		List<DisabilityDocumentForm> ret = new LinkedList<DisabilityDocumentForm>();
		for (DisabilityDocument doc : list) {
			try {
				DisabilityDocumentForm frm = theEntityFormService.loadForm(DisabilityDocumentForm.class, doc) ;
				if (frm.getPatientFio()==null || frm.getPatientFio().equals("")) {
					frm.setPatientFio(doc.getDisabilityCase()!=null&&doc.getDisabilityCase().getPatient()!=null?
							doc.getDisabilityCase().getPatient().getFio():"") ;
				}
				ret.add(frm);
				
			} catch (EntityFormException e) {
				throw new IllegalStateException(e);
			}
		}
		return ret;
	}
    private @EJB ILocalEntityFormService theEntityFormService;
    @PersistenceContext EntityManager theManager ;

    public  List<GroupByDate> findOpenDocumentGroupByDate() {
		StringBuilder sql = new StringBuilder();
		sql.append("select min(dr.dateFrom),count(dd.id) from DisabilityDocument dd")
				.append(" left join DisabilityRecord as dr on dr.disabilityDocument_id=dd.id")
				.append(" where ")
				.append(" (dd.isclose is null or cast(dd.isclose as int)=0)") 
				.append(" and ")
				.append(" (dr.id = (select min(dr2.id) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id)) ")
				.append(" group by dr.dateFrom ")
				.append(" order by dr.dateFrom") ;
		System.out.println(sql) ;
    	return findDocumentGroupByDate(sql) ;
    }
    
	private List<GroupByDate> findDocumentGroupByDate(StringBuilder aSql) {
		List<Object[]> list = theManager.createNativeQuery(aSql.toString())
				.getResultList() ;
		LinkedList<GroupByDate> ret = new LinkedList<GroupByDate>() ;
		long i =0 ;
		for (Object[] row: list ) {
			GroupByDate result = new GroupByDate() ;
			Date date = (Date)row[0] ;
			result.setCnt(ConvertSql.parseLong(row[1])) ;
			result.setDate(date) ;
			result.setDateInfo(DateFormat.formatToDate(date)) ;
			result.setSn(++i) ;
			ret.add(result) ;
		}
		return ret ;
	}
	public List<DisabilityDocumentForm> findOpenTicketByDate(String aDate) {
		
		//QueryClauseBuilder builder = new QueryClauseBuilder();
        Date date = null;
        if(!StringUtil.isNullOrEmpty(aDate)) {
            try {
                date = DateFormat.parseSqlDate(aDate);
            } catch (Exception e) {
                LOG.warn("Ошибка преобразования даты "+aDate, e);
            }
        }
        if (date == null) throw new IllegalDataException("Неправильная дата") ;
        
        List<Object> idlist = theManager.createNativeQuery("select dd.id from DisabilityDocument as dd"
        		+ " left join DisabilityRecord as dr on dr.disabilityDocument_id=dd.id"
        		+" where ((dd.isclose is null) or (cast(dd.isclose as int)=0))  and dr.dateFrom=:dateFrom").setParameter("dateFrom",date).setMaxResults(50).getResultList();
        List<DisabilityDocumentForm> ret = new LinkedList<DisabilityDocumentForm>();
        if (idlist.size()>0) {
        	StringBuilder ids = new StringBuilder() ;
        	StringBuilder sql = new StringBuilder() ;
	        for (Object obj:idlist) {
	        	Long iddoc = ConvertSql.parseLong(obj) ;
	        	ids.append(",").append(obj) ;
	        }
	        ids.substring(1) ;
	        System.out.println(ids.substring(1)) ;
	        sql.append("from DisabilityDocument where id in (").append(ids.substring(1)).append(")") ;
	        System.out.println(sql.toString()) ;
	        
	        List<DisabilityDocument> list = theManager.createQuery(sql.toString()).setMaxResults(50).getResultList() ;
	        for (DisabilityDocument doc : list) {
	            try {
	                ret.add(theEntityFormService.loadForm(DisabilityDocumentForm.class, doc));
	            } catch (EntityFormException e) {
	                throw new IllegalStateException(e);
	            }
	        }
        }
        return ret;
	}
	public List<DisabilityDocumentForm> findCloseTicketByDate(String aDate, String aType) {
		
		QueryClauseBuilder builder = new QueryClauseBuilder();
        Date date = null;
        if(!StringUtil.isNullOrEmpty(aDate)) {
            try {
                date = DateFormat.parseSqlDate(aDate);
            } catch (Exception e) {
                LOG.warn("Ошибка преобразования даты "+aDate, e);
            }
        }
        if (date == null) throw new IllegalDataException("Неправильная дата") ;
        StringBuilder sql = new StringBuilder() ;
        if (aType!=null && aType.equals("max")) {
        	sql.append("select dd.id from disabilitydocument as dd ")
        		.append("where cast(dd.isclose as int)=1 ")
        		.append("and (select max(dr2.dateTo) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id) = cast(:date as date)") ;
        	
        } else {
        	sql.append("select dd.id from disabilitydocument as dd ")
        		.append("where cast(dd.isclose as int)=1 ")
        		.append("and (select min(dr2.dateFrom) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id) = cast(:date as date)") ;
        }
        List<Object> idlist = theManager.createNativeQuery(sql.toString()).setParameter("date",date).setMaxResults(50).getResultList();
        List<DisabilityDocumentForm> ret = new LinkedList<DisabilityDocumentForm>();
        if (idlist.size()>0) {
        	StringBuilder ids = new StringBuilder() ;
        	sql = null ;
        	sql = new StringBuilder() ;
	        for (Object obj:idlist) {
	        	Long iddoc = ConvertSql.parseLong(obj) ;
	        	ids.append(",").append(obj) ;
	        }
	        ids.substring(1) ;
	        System.out.println(ids.substring(1)) ;
	        sql.append("from DisabilityDocument where id in (").append(ids.substring(1)).append(")") ;
	        System.out.println(sql.toString()) ;
	        
	        List<DisabilityDocument> list = theManager.createQuery(sql.toString()).setMaxResults(50).getResultList() ;
	        for (DisabilityDocument doc : list) {
	            try {
	                ret.add(theEntityFormService.loadForm(DisabilityDocumentForm.class, doc));
	            } catch (EntityFormException e) {
	                throw new IllegalStateException(e);
	            }
	        }
        }
        return ret;
	}
	public List<GroupByDate> findCloseDocumentGroupByDate(String aDateFrom, String aDateTo) {
		// TODO Auto-generated method stub
		return null;
	}
	@Resource SessionContext theContext ;
}
