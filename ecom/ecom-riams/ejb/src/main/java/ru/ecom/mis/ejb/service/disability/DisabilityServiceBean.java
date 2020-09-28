package ru.ecom.mis.ejb.service.disability;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.IllegalDataException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.mis.ejb.domain.disability.*;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityDocumentCloseReason;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityStatus;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.voc.VocCombo;
import ru.ecom.mis.ejb.form.disability.DisabilityDocumentForm;
import ru.ecom.mis.ejb.service.patient.QueryClauseBuilder;
import ru.ecom.poly.ejb.services.GroupByDate;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.date.AgeUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Сервис для работы с нетрудоспобностью
 * @author stkacheva
 *
 */
@Stateless
@Remote(IDisabilityService.class)
public class DisabilityServiceBean implements IDisabilityService {

	private static final Logger LOG = Logger.getLogger(DisabilityServiceBean.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();

	/**
	 * Импорт листа нетрудоспособности с ФСС
	 * @param aDisabilityNumber Номер ЭЛН
	 * @param aSnils СНИЛС пациента
	 * @param aPatientId ИД пациента
	 * @param aMethod Метод ('import' для импорта, null либо все остальное - только просмотр)
	 * @return HTML страницы с ответом
	 */
	public String importDisabilityDocument(String aDisabilityNumber, String aSnils, Long aPatientId, String aMethod) {
		Map<String, String> parameters = getDefaultParametersForFSS();
		String address = parameters.get("address");
		String ogrn = parameters.get("ogrn");

		if ("import".equals(aMethod)) {
			aMethod="sImportLNN";
		} else {
			aMethod="sLnDate";
		}
		return makeHttpGetRequest(address,aMethod+"?ogrn="+ogrn+"&eln="+aDisabilityNumber+"&snils="+aSnils+"&pid="+aPatientId);
	}

	/**
	 * Делаем просто GET запрос, возвращаем ответ сервера
	 *
	 * @param aAddress
	 * @param aMethod
	 * @return
	 */
	public String makeHttpGetRequest(String aAddress, String aMethod) {
		try {
			LOG.info("makeHttpGetRequest, "+aAddress+"/"+aMethod);
			URL url = new URL(aAddress + "/" + aMethod);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");

			StringBuilder response;
			try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
				response = new StringBuilder();
				String s;
				while ((s = in.readLine()) != null) {
					response.append(s);
				}
			}
			connection.disconnect();
			return response.length() > 0 ? response.toString() : "Ошибка! Не получен ответ от сервера";
		} catch (IOException e) {
			e.printStackTrace();
			return ""+e;
		}
	}

	public String exportDisabilityDocument(Long aDocumentId) {
		return exportDisabilityDocumentOrGetNumberRangeOrAnnulSheet("exportDocument", aDocumentId, null, null, null, null);
	}

	public String getLNNumberRange(Long aCount) {
		return exportDisabilityDocumentOrGetNumberRangeOrAnnulSheet("getNumberRange", null, aCount, null, null, null);
	}

	public String annulDisabilityDocument(Long aDocumentId, String aReasonAnnulId, String textReason, String snils) {
		return exportDisabilityDocumentOrGetNumberRangeOrAnnulSheet("annullSheet", aDocumentId, null, aReasonAnnulId, textReason, snils);
	}

	public String getSoftConfigValue(String aKey, String aDefaultValue) {
		List<Object[]> list = theManager.createNativeQuery("select sc.id, sc.keyvalue from softconfig sc where sc.key=:key").setParameter("key",aKey).getResultList();
		return  list.isEmpty() ? aDefaultValue : list.get(0)[1].toString();
	}

	private Map<String, String> getDefaultParametersForFSS() {
		String address = getSoftConfigValue("FSS_PROXY_SERVICE", null);
		String lpuId = getSoftConfigValue("DEFAULT_LPU", null);
		String ogrn = null;
		if (lpuId != null) {
			List<Object[]> list = theManager.createNativeQuery("select id,coalesce(ogrn,0) from mislpu where id = " + lpuId).getResultList();
			if (!list.isEmpty()) {
				ogrn = list.get(0)[1].toString();
			}
		}
		Map<String, String> map = new HashMap<>();
		map.put("address",address);
		map.put("lpuId",lpuId);
		map.put("ogrn",ogrn);
		return map;

	}



	/**
	 * Выгружаем ЭЛН на сервис экспорта в ФСС, отправляем запрос на получения списка номеров электронных ЛН. Получаем ИД документа нетрудоспособности, возвращаем ответ сервиса
	 * @param aDocumentId - ИД документа
	 * @param aRangeCount - Количество номеров для импорта
	 * @param aMethod- Метод, на который отправляется запрос
	 * @return Результат экспорта
	 */
	private String exportDisabilityDocumentOrGetNumberRangeOrAnnulSheet(String aMethod, Long aDocumentId, Long aRangeCount, String aReasonAnnulId, String textReason, String snils) {
		//Формируем строку для отправки на сервис Руслана
		Map<String, String> parameters = getDefaultParametersForFSS();
		String address = parameters.get("address");
		String lpuId = parameters.get("lpuId");
		String ogrn = parameters.get("ogrn");
		if (lpuId==null||ogrn==null){
			return "Не найдено ЛПУ для отправки больничного листа";
		} else {
		String method;
		if (textReason != null) {
			try {
				textReason = URLEncoder.encode(textReason, "UTF-8");

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (snils != null) {
			snils = snils.replace("-", "").replace(" ", "");

		}
		if (address == null ) {
			LOG.error("Нет необходимых данных для экспорта ЭЛН: Адрес сервиса = NULL, ЛПУ = " + lpuId);
			return "Нет необходимых данных для экспорта ЭЛН: Адрес сервиса = NULL, ЛПУ = " + lpuId;
		}

		if (ogrn.equals("0")) {
			LOG.error("У ЛПУ не указан ОГРН. ЛПУ = " + lpuId);
			return "У ЛПУ не указан ОГРН. ЛПУ = " + lpuId;
		}
		if ("exportDocument".equals(aMethod)) {
			if (aDocumentId != null && aDocumentId > 0) {
				method = "SetLnData?id=" + aDocumentId + "&ogrn=" + ogrn;
			} else {
				return "Ошибка! При экспорте документа не указан ИД документа";
			}
		} else if ("getNumberRange".equals(aMethod)) {
			if (aRangeCount != null && aRangeCount > 0) {
				method = "sNewLnNumRange?ogrn=" + ogrn + "&count=" + aRangeCount;
			} else {
				return "Ошибка! При получении номеров ЭЛН не указано кол-во номеров";
			}
		} else if ("annullSheet".equals(aMethod)) {
			if (aDocumentId != null && aDocumentId > 0 && aReasonAnnulId != null && textReason != null && snils != null) {
				method = "sDisableLn?ogrn=" + ogrn + "&lnCode=" + aDocumentId + "&snils=" + snils + "&reasonCode=" + aReasonAnnulId + "&reason=" + textReason;
			} else {
				return "Ошибка! При аннулировании ЭЛН не указан ИД документа либо причина аннулирования";
			}
		} else {
			return "Ошибка! Не указана функция для работы с ЭЛН";
		}
		return makeHttpGetRequest(address, method);
	}

	}
   public boolean isRightSnils (String aSNILS) {
		String currentSnils = aSNILS.replace("-", "").replace(" ", "").replace("\t","");
		int snilsCN = Integer.parseInt(currentSnils.substring(currentSnils.length()-2));
		if (currentSnils.length()!=11) {
			return false;
		} 
		int sum = 0;
		int controlNumber = 0;
		for (int i=0;i<9;i++) {
			sum+=Integer.valueOf(currentSnils.substring(i, i+1))*(9-i);
		}
		if (sum>101) {
			sum=sum%101;			
		}
		if (sum<100) {
			controlNumber=sum;
		}
		return snilsCN==controlNumber;
   }
    public DisabilityDocument getDocument (String aNumber) {
		try {
			return (DisabilityDocument ) theManager.createQuery("from DisabilityDocument where number=:num").setParameter("num", aNumber).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Deprecated
	private static String createNewFile(Element rootElement, String aSocCode, String aPacketNumber) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
		Calendar cal = Calendar.getInstance();
		String filename="L_"+aSocCode+"_"+format.format(new java.sql.Date(cal.getTime().getTime()))+"_"+aPacketNumber+".xml";
		String outputFile=workDir+"/"+filename;
		try (FileWriter fwrt = new FileWriter(outputFile)){
			LOG.info("CreateNewFile, hashcode="+rootElement.hashCode());
			XMLOutputter outputter = new XMLOutputter();
			format.format(new java.sql.Date(cal.getTime().getTime()));
			Document pat = new Document(rootElement);
			outputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
			outputter.output(pat, fwrt);
			LOG.info("CreateNewFile, fileName="+filename);
			return filename;
		} catch (Exception ex) {
			 LOG.error("E_CreateNewfile: "+ex.getMessage());
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
    	LOG.info("sql--->"+sql) ;
    	List<Object[]> list = theManager.createNativeQuery(sql.toString()).getResultList() ;
    	
    	Long dcaseold=0L ;
    	for (Object[] obj:list) {
    		String str = "" ;
    		Long dcase = ConvertSql.parseLong(obj[0]) ;
    		if (!dcase.equals(dcaseold)) {
    			dcaseold=dcase ;
    		
    		String mkb = ""+obj[1] ;
    		String sex = ""+obj[3] ;
    		String reason = ""+obj[2] ;
    		if (reason.equals("09")) {
    			if (sex.equals("1")) {str=str+","+"96,102" ;} else {str=str+","+"97,103" ;}
    		} else {if (reason.equals("08")) {
    			if (sex.equals("1")) {str=str+","+"98,102" ;} else {str=str+","+"99,103" ;}
    		} else {if (reason.equals("03")) {
    			if (sex.equals("1")) {str=str+","+"100,102" ;} else {str=str+","+"101,103" ;}
    		} else {if (reason.equals("05")) {
				str=str+","+"104" ;
    			//if (sex.equals("1")) {str=str+","+"104" ;} else {str=str+","+"104" ;}
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
    		LOG.info("str--->"+str) ;
    			str = str.substring(1) ;
    			String[] strs = str.split(",") ;
    			theManager.createNativeQuery("delete from DisabilityReport where caseR='"+obj[0]+"'").executeUpdate() ;
    			if (strs.length>0) {
	    			for (String s:strs) {
		    			LOG.info("line--->"+s) ;
		    			LOG.info("case--->"+dcase) ;
	    				DisabilityReport dr = new DisabilityReport() ;
	    				dr.setLineR(Long.valueOf(s)) ;
	    				dr.setCaseR(dcase) ;
	    				theManager.persist(dr) ;
	    			}
	    			String age = AgeUtil.getAgeCache((java.sql.Date)obj[6], (java.sql.Date)obj[4], 0);
	    			LOG.info("age--->"+age) ;
	    			LOG.info("duration--->"+obj[5]) ;
	    			theManager.createNativeQuery("update DisabilityCase set agePatient='"+age+"', durationCase='"+obj[5]+"' where id='"+obj[0]+"'").executeUpdate() ;
    			}
    		}
    			

    	}
    }
    public Long createDuplicateDocument( Long aDocId,Long aReasonId, String aSeries, String aNumber,Long aWorkFunction2
    		,String aJob, Boolean aUpdateJob){
    	DisabilityDocument doc = theManager.find(DisabilityDocument.class, aDocId) ;
		boolean isPaperDocument = theManager.createNativeQuery("select id from electronicdisabilitydocumentnumber where disabilitydocument_id=:docId").setParameter("docId",doc.getId()).getResultList().isEmpty();

    	if (isPaperDocument && (doc.getIsClose()==null || !doc.getIsClose())) {
    		throw new IllegalDataException("ДУБЛИКАТ МОЖНО СДЕЛАТЬ ТОЛЬКО ЗАКРЫТОГО ДОКУМЕНТА!!!") ;
    	}
    	if (isPaperDocument && (doc.getStatus()==null || !doc.getStatus().getCode().equals("0"))) {
    		throw new IllegalDataException("ДУБЛИКАТ МОЖНО СДЕЛАТЬ ТОЛЬКО ДЕЙСТВУЮЩЕГО ДОКУМЕНТА!!!") ;
    	}
    	
    	VocDisabilityStatus stat = theManager.find(VocDisabilityStatus.class, aReasonId) ;
    	DisabilityDocument newDoc = copyDocument(doc, aSeries, aNumber,new java.sql.Date(new java.util.Date().getTime()),aWorkFunction2, true) ;
    	if (aJob!=null && !aJob.equals("")) {
    		aJob = aJob.trim().toUpperCase() ;
    		newDoc.setJob(aJob) ;
    		if (aUpdateJob!=null && aUpdateJob) {
    			Patient pat = doc.getDisabilityCase()!=null && doc.getDisabilityCase().getPatient()!=null ? doc.getDisabilityCase().getPatient():null ;
				if (pat!=null) {
					pat.setWorks(aJob) ;
					theManager.persist(pat) ;
				}
    		}
    	}
    	doc.setStatus(stat) ;
    	doc.setNoActuality(true) ;
    	doc.setDuplicate(newDoc) ;
    	theManager.persist(doc) ;
    	return newDoc.getId() ;
    	
    }
    public Long createWorkComboDocument(Long aDocId,String aJob, String aSeries, String aNumber, Long aVocCombo, Long aPrevDocument){
    	DisabilityDocument doc = theManager.find(DisabilityDocument.class, aDocId) ;
    	DisabilityDocument docPrev = aPrevDocument!=null&&!aPrevDocument.equals(0L)?theManager.find(DisabilityDocument.class, aPrevDocument):null ;
    	VocCombo newVocComb = theManager.find(VocCombo.class, aVocCombo) ;

		boolean isPaperDocument = theManager.createNativeQuery("select id from electronicdisabilitydocumentnumber where disabilitydocument_id=:docId").setParameter("docId",aDocId).getResultList().isEmpty();//Уберем проверки для ЭЛН
    	if (isPaperDocument && doc.getWorkComboType()!=null) {
    		throw new IllegalDataException("БЛАНК ПО СОВМЕСТИТЕЛЬСТВУ МОЖНО ДОБАВИТЬ ТОЛЬКО ПО ОСНОВНОМУ МЕСТУ РАБОТЫ") ;
    	}
    	if (isPaperDocument && (doc.getIsClose()==null || !doc.getIsClose())) {
    		throw new IllegalDataException("БЛАНК ПО СОВМЕСТИТЕЛЬСТВУ МОЖНО СДЕЛАТЬ ТОЛЬКО ЗАКРЫТОГО ДОКУМЕНТА!!!") ;
    	}
    	if (isPaperDocument && (doc.getStatus()==null || !doc.getStatus().getCode().equals("0"))) {
    		throw new IllegalDataException("БЛАНК ПО СОВМЕСТИТЕЛЬСТВУ МОЖНО СДЕЛАТЬ ТОЛЬКО ДЕЙСТВУЮЩЕГО ДОКУМЕНТА!!!") ;
    	}
    	
    	DisabilityDocument newDoc = copyDocument(doc, aSeries, aNumber,new java.sql.Date(new java.util.Date().getTime()),null) ;
    	
    	newDoc.setJob(aJob.trim().toUpperCase()) ;
    	newDoc.setMainWorkDocumentNumber(doc.getNumber()) ;
    	newDoc.setMainWorkDocumentSeries(doc.getSeries()) ;
    	newDoc.setWorkComboType(newVocComb);
    	newDoc.setPrevDocument(docPrev) ;
    	theManager.persist(newDoc) ;
    	return newDoc.getId() ;
    }
    private DisabilityDocument copyDocument(DisabilityDocument aDocument, String aSeries, String aNumber,Date aIssuedDate,Long aWorkFunction2) {
    	return copyDocument( aDocument,  aSeries,  aNumber, aIssuedDate, aWorkFunction2, false);
    }
    
    private DisabilityDocument copyDocument(DisabilityDocument aDocument, String aSeries, String aNumber,Date aIssuedDate,Long aWorkFunction2, boolean isDuplicate) {
    	WorkFunction wf2 = aWorkFunction2!=null?theManager.find(WorkFunction.class,aWorkFunction2):null ;
    	String username = theContext.getCallerPrincipal().toString();
		DisabilityDocument newDoc = new DisabilityDocument() ;
    	newDoc.setAnotherLpu(aDocument.getAnotherLpu()) ;
    	newDoc.setBeginWorkDate(aDocument.getBeginWorkDate()) ;
    	newDoc.setCloseReason(aDocument.getCloseReason()) ;
    	newDoc.setCreateDate(new java.sql.Date(new java.util.Date().getTime())) ;
    	newDoc.setCreateUsername(username) ;
    	newDoc.setDiagnosis(aDocument.getDiagnosis()) ;
    	newDoc.setDisabilityCase(aDocument.getDisabilityCase()) ;
    	newDoc.setDisabilityReason(aDocument.getDisabilityReason()) ;
    	newDoc.setDisabilityReason2(aDocument.getDisabilityReason2()) ;
    	newDoc.setDisabilityReasonChange(aDocument.getDisabilityReasonChange()) ;
    	newDoc.setOtherCloseDate(aDocument.getOtherCloseDate()) ;
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
    	List<DisabilityRecord> list1 = new ArrayList<>() ;
    	Date startRecordDate = null;
    	Date finishRecordDate = null;
    	for (int i=0;i<aDocument.getDisabilityRecords().size();i++) {
    		DisabilityRecord old = aDocument.getDisabilityRecords().get(i) ;
    		DisabilityRecord record = new DisabilityRecord() ;
    		if (startRecordDate==null||startRecordDate.getTime()>old.getDateFrom().getTime()) {
    			startRecordDate = old.getDateFrom();
    		}
    		if (finishRecordDate ==null||finishRecordDate.getTime()<old.getDateTo().getTime()) {
    			finishRecordDate = old.getDateTo();
    		}
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
    	List<RegimeViolationRecord> list2 = new ArrayList<>() ;
    	for (int i=0;i<aDocument.getRegimeViolationRecords().size();i++) {
    		RegimeViolationRecord old = aDocument.getRegimeViolationRecords().get(i) ;
    		RegimeViolationRecord record = new RegimeViolationRecord() ;
    		record.setComment(old.getComment()) ;
    		record.setDateFrom(old.getDateFrom()) ;
    		record.setDateTo(old.getDateTo()) ;
    		record.setDisabilityDocument(old.getDisabilityDocument()) ;
    		record.setRegimeViolationType(old.getRegimeViolationType()) ;
    		//record.set(old.get) ;
    		list2.add(record) ;
    	}
		if (isDuplicate && !list1.isEmpty()) {
			DisabilityRecord record = list1.get(0);
			record.setDateFrom(startRecordDate);
			record.setDateTo(finishRecordDate);
			list1.clear();
			list1.add(record);
		}
    	newDoc.setDisabilityRecords(list1) ;
    	newDoc.setRegimeViolationRecords(list2) ;
    	theManager.persist(newDoc) ;
		//Проверяем, является ли больничный лист - электронным
		List<ElectronicDisabilityDocumentNumber> elns = theManager.createQuery(" from ElectronicDisabilityDocumentNumber where number=:num").setParameter("num",aNumber).getResultList();
		if (elns!=null && !elns.isEmpty()) {
			LOG.warn("При создании дубликата выявилось что больничный лист - электронный");
			ElectronicDisabilityDocumentNumber eln = elns.get(0);
			eln.setDisabilityDocument(newDoc);
			eln.setUsername(username);
			theManager.persist(eln);
		}
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
  		if (doc.getOtherCloseDate()!=null) {
			ret.append(DateFormat.formatToDate(doc.getOtherCloseDate())) ;
		}
  		ret.append("#") ;
  		if (doc.getCloseReason()!=null) {
    		ret.append(doc.getCloseReason().getId()).append("#").append(doc.getCloseReason().getName()) ;
    	} else {
    		ret.append(0).append("#Выберите причину закрытия") ;
    	}
    	return ret.toString() ;
    	
    }
    
    public String closeDisabilityDocument(Long aDocumentId, Long aReasonId,String aSeries,String aNumber,String aOtherCloseDate, String dateGoToWork) {
		DisabilityDocument doc = theManager.find(DisabilityDocument.class, aDocumentId) ;
		if (doc.getDateTo()==null) {
			throw new IllegalStateException("Нельзя закрыть документ, так как есть не закрытое продление!") ;  
		}
		VocDisabilityDocumentCloseReason reason = theManager.find(VocDisabilityDocumentCloseReason.class, aReasonId) ;
		doc.setCloseReason(reason) ;
		doc.setSeries(aSeries) ;
		doc.setNumber(aNumber) ;
		doc.setIsClose(Boolean.TRUE);



		if (reason.getCodeF()!=null && (reason.getCodeF().equals("32")
				||reason.getCodeF().equals("33")
				||reason.getCodeF().equals("34")
				||reason.getCodeF().equals("36")
				)) {
			if (aOtherCloseDate!=null && !aOtherCloseDate.equals("")) {
				try {
					Date d = DateFormat.parseSqlDate(aOtherCloseDate) ;
					doc.setOtherCloseDate(d) ;
				} catch(Exception e) {
					e.printStackTrace() ;
					throw new IllegalStateException("Нельзя закрыть документ, так как причина закрытия "+reason.getCodeF()+" "+reason.getName()+" должна быть указана корректная иная дата закрытия!") ;
				}
			} else {
				throw new IllegalStateException("Нельзя закрыть документ, так как причина закрытия "+reason.getCodeF()+" "+reason.getName()+" должна быть указана иная дата закрытия!") ;
			}
		} else {
			doc.setOtherCloseDate(null) ;
		}

		if(reason.getCodeF()!=null){
			if(!reason.getCodeF().equals("31") && !reason.getCodeF().equals("37")) {
				if (aOtherCloseDate.equals("")) {
					doc.setBeginWorkDate(Date.valueOf(dateGoToWork));
				}
			}
		}

		theManager.persist(doc) ;
		return reason.getName() ;
	}
	public List<DisabilityDocumentForm> findDocumentBySeriesAndNumber(String aFind) {
        if (CAN_DEBUG) {
            LOG.debug("findMedcard() Number and series = " + aFind );
        }
        
        if (aFind ==null || aFind.trim().equals(""))  { 
        	return new LinkedList<>();
        } else {
        	QueryClauseBuilder builder = new QueryClauseBuilder();
        	aFind = aFind.trim() ;
        	int ind = aFind.indexOf(" ") ;
        	String series = null ;
        	String number;
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
		List<DisabilityDocumentForm> ret = new LinkedList<>();
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
    	return findDocumentGroupByDate(sql) ;
    }
    
	private List<GroupByDate> findDocumentGroupByDate(StringBuilder aSql) {
		List<Object[]> list = theManager.createNativeQuery(aSql.toString())
				.getResultList() ;
		LinkedList<GroupByDate> ret = new LinkedList<>() ;
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
        List<DisabilityDocumentForm> ret = new LinkedList<>();
        if (!idlist.isEmpty()) {
        	StringBuilder ids = new StringBuilder() ;
            for (Object obj:idlist) {
	        	ids.append(",").append(obj) ;
	        }

            List<DisabilityDocument> list = theManager.createQuery("from DisabilityDocument where id in (" + ids.substring(1) + ")").setMaxResults(50).getResultList() ;
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
        List<DisabilityDocumentForm> ret = new LinkedList<>();
        if (!idlist.isEmpty()) {
        	StringBuilder ids = new StringBuilder() ;
        	sql = new StringBuilder() ;
	        for (Object obj:idlist) {
	        	ids.append(",").append(obj) ;
	        }
	        sql.append("from DisabilityDocument where id in (").append(ids.substring(1)).append(")") ;

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
		return new ArrayList<>();
	}
	@Resource SessionContext theContext ;
}