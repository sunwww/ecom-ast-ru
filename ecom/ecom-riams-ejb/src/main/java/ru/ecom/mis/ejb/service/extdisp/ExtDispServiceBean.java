package ru.ecom.mis.ejb.service.extdisp;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Random;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import ru.ecom.ejb.services.util.ApplicationDataSourceHelper;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.mis.ejb.service.addresspoint.AddressPointServiceBean;
@Stateless
@Local(IExtDispService.class)
@Remote(IExtDispService.class)
/**
 * 
 * @author VTsybulin 19.12.2014
 * 
 * Формирование XML для импорта на сайте мониторинга ДД Росминздрава (orph.rosminzdrav.ru)
 * Версия формата 20140307
 *
 */
public class ExtDispServiceBean implements IExtDispService {
	StringBuilder badCards =new StringBuilder();
	String theFinishDate="";
	String theFileSuffix="";
//	String theArchiveFileName="";
	String aFileNames = "";
	public String getBadCards () {
		if (badCards.length()>0) return badCards.toString();
		else return "";
		
	}
	
	public String exportOrphDefaultValues(String aStartDate, String aFinishDate,
			String aFileNameSuffix, String aSqlAdd) throws ParseException,
			NamingException {
		String aHeight = "150"; //Рост (в см)
		String aWeight = "40"; //Вес (в см)
		String aHeadSize = "30";  //Окружность головы (в см)
		String aReccomend = "_";
		String aZOJReccomend = "Режим дня и отдыха - по возрасту, рациональное питание"
				+ ", закаливание, профилактика вредных привычек."; //Рекомендации
		String aFizGroup = "1"; //"1"; //Группа здоровья для физкультуры
		String aAnalysesText = "Без патологий"; // Результат анализов
		return exportOrph(aStartDate,aFinishDate, aFileNameSuffix, aSqlAdd,aFizGroup, aHeight, aWeight, aHeadSize, aAnalysesText, aZOJReccomend, aReccomend,"200" );
	}
	
	public String createArchive(String archiveName) {
//		System.out.println("DEBUG ----createArchive='"+archiveName+"'");
		if (aFileNames.length()>0) {
			aFileNames = aFileNames.substring(0,aFileNames.length()-1);
		
		String[] fileNames = aFileNames.split(":");
		if (fileNames.length>1) {
			EjbEcomConfig config = EjbEcomConfig.getInstance() ;
			String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
	    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
			StringBuilder sb = new StringBuilder();
			sb.append("zip -r -j -9 ").append(workDir).append("/").append(archiveName).append(" ") ;
			for (int i=0;i<fileNames.length;i++) {				
				sb.append(workDir).append("/").append(fileNames[i]).append(" ");
			//	sb.append(fileNames[i]).append(" ");
			}
			//System.out.println("--------dir: "+sb) ;
	    	try {
	    //		System.out.println("-------------------EXTDISP_createArchive="+sb.toString());
	    //		System.out.println(new StringBuilder().append("-------------------EXTDISP_dir=cd ").append(workDir).append("").toString());
	    //		String[] arraCmd = {new StringBuilder().append("cd ").append(workDir).append("").toString(),sb.toString()} ;
	    		Runtime.getRuntime().exec(sb.toString());//arraCmd);
	    	//	Runtime.getRuntime().exec(arraCmd);//arraCmd);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	aFileNames="";
			return archiveName;
			}
		else {
				return fileNames[0];
		}} return "createArchive ERROR, aFileNames.length=0";
	}
	public String exportOrph(String aStartDate, String aFinishDate,
			String aFileNameSuffix, String aSqlAdd, String aFizGroup, String aHeight,
			String aWeight, String aHeadSize, String aAnalysesText,
			String aZOJReccomend, String aReccomend, String divideNum) throws ParseException,
			NamingException {
		try{
//		System.out.println("DEBUG ------------ExportOrph---------------");
//		System.out.println("DEBUG ----- aHeadSize='"+aHeadSize+"'");
//		System.out.println("DEBUG ----- divideNum='"+divideNum+"'");
		if (aStartDate==null || aStartDate.equals("")||aFinishDate==null || aFinishDate.equals("")) {
			return null;
		}
		if (divideNum==null ||divideNum.equals("")) {
			divideNum="0";
		}
		  theFileSuffix=aFileNameSuffix;
		  theFinishDate=aFinishDate;
		String theArchiveFileName="orph-"+theFileSuffix+theFinishDate;
		  aFileNames="";
	/** Сделано:
	 * Проверка на пасп. данные, номер полиса.
	 * Проверка на наличие даты исследования.	
	 * Проверку на номер полиса (RZ)
	 * Тип документа должен быть только 3 или 14
	 * Проверка на 1 группу здоровья и диагноз, отличный от Z**.*
	*/	
		
	String SQLreq ="select distinct edc.id as did, p.id as pid,p.lastname, p.firstname, p.middlename, p.patientinfo, vs.omccode as sex, p.snils "
				+", to_char(p.birthday,'dd.mm.yyyy') as birthday "
				+",to_char(edc.startDate,'dd.mm.yyyy') as edcBeginDate "
				+",to_char(edc.finishDate,'dd.mm.yyyy') as edcFinishDate "
				+",vedsg.code as socCode "
				+",mkb.code as mkbcode ,vedag.name as vedagname ,vedsg.name as vedsgname ,vedhg.code as vedhgcode "
				+",(select ri.smocode from reg_ic ri left join medpolicy mp on mp.company_id=ri.id where mp.patient_id=p.id order by mp.actualdatefrom desc limit 1) as smoCode "
				+",edc.isObservation as cntDispM "
				+",edc.isTreatment as cntLechM "
				+",edc.isDiagnostics as cntDiagM "
				+",p.commonnumber as RZ "
				+",cast(to_char(edc.finishDate,'yyyy')as int)-cast(to_char(p.birthday,'yyyy')as int)+ "
				+ "case when ((cast(to_char(edc.finishDate,'MM')as int))-cast(to_char(p.birthday,'MM')as int)<0) or "
				+ "((cast(to_char(edc.finishDate,'MM')as int))-cast(to_char(p.birthday,'MM')as int)=0 "
				+ "and ((cast(to_char(edc.finishDate,'dd')as int))-cast(to_char(p.birthday,'dd')as int)<0)) then -1 else 0 end as fullage "
				+",p.passportseries as passSer "
				+",p.passportnumber as passNum "
				+",vic.code as passID "
				+",pwr.lastname as vrach_l " 
				+",pwr.firstname as vrach_f "
				+",pwr.middlename as vrach_m "
				+",edc.isServiceIndication as cntIsServiceIndication " 
				+",case when ml2.id is not null then ml2.name else ml.name end as lpuName " 
				+",case when ml2.id is not null then ml2.printAddress else ml.printAddress end as lpuAddress "
				+",case when p.address_addressid is not null then adr.postindex||' :'||adrPar.kladr ||' :'||adr.kladr||' :'||p.housenumber||' :'||p.housebuilding||' :'||p.flatnumber ||' ' else '0' end as fullAddress "
				+"from ExtDispCard edc " 
				+"left join mislpu ml on ml.id=edc.lpu_id "
				+"left join mislpu ml2 on ml2.id=ml.parent_id "
				+"left join WorkFunction wf on wf.id=edc.workFunction_id "
				+"left join Worker w on w.id=wf.worker_id "
				+"left join Patient pwr on pwr.id=w.person_id "
				+"left join Patient p on p.id=edc.patient_id "
				+"left join Address2 adr on adr.addressid=p.address_addressid "
				+"left join Address2 adrPar on adrPar.addressid=adr.parent_addressid "
				+"left join vocsex vs on vs.id=p.sex_id "
				+"left join vocidentitycard vic on vic.id=p.passporttype_id "
				+"left join VocExtDisp ved on ved.id=edc.dispType_id "
				+"left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id "
				+"left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id "
				+"left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id "
				+"left join ExtDispRisk edr on edr.card_id=edc.id "
				+"left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id "
				+"left join VocIdc10 mkb on mkb.id=edc.idcMain_id "
				+"left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null "
				+"where edc.finishDate between to_date('"+aStartDate+"','dd.mm.yyyy') and to_date('"+aFinishDate+"','dd.mm.yyyy') " 
				+"and ved.code='CHILD_PROF_1' "  
	//			+"and vedsg.code ='4' " //Выбираем тип ДД (4-проф, 5 - предварительные, 6 - периодические)
//				+"and vic.code in ('3','14') " //Только паспорт и свидетельства о рождении!
//				+"and (p.commonnumber is not null and p.commonnumber!='') " 
				+"and cast(to_char(edc.finishDate,'yyyy')as int)-cast(to_char(p.birthday,'yyyy')as int)+ "
				+"case when ((cast(to_char(edc.finishDate,'MM')as int))-cast(to_char(p.birthday,'MM')as int)<0) or "
				+"((cast(to_char(edc.finishDate,'MM')as int))-cast(to_char(p.birthday,'MM')as int)=0 "
				+ "and ((cast(to_char(edc.finishDate,'dd')as int))-cast(to_char(p.birthday,'dd')as int)<0)) then -1 else 0 end <18 " 
				+aSqlAdd 
/*				+"group by edc.id,p.lastname,p.firstname, p.middlename "
				+",p.birthday "
				+",edc.startDate "
				+",edc.finishDate "
				+",edc.dispType_id "
				+",vedag.name,vedhg.code,vedsg.name "
				+",edc.isObservation ,edc.isTreatment "
				+",edc.isDiagnostics ,edc.isSpecializedCare "
				+",edc.isSanatorium ,mkb.code "
				+",edc.isServiceIndication "
				+",p.commonnumber ,p.passportseries ,p.passportnumber "
				+",p.sex_id, p.snils "
				+",p.passporttype_id ,mp.company_id "
				+",p.id "
				+",pwr.lastname " 
				+",pwr.firstname "
				+",pwr.middlename "*/
				+"order by p.lastname,p.firstname,p.middlename ";
				
		
			System.out.println("Поиск записей:");
			System.out.println("sql_orph="+SQLreq);
			find_data(SQLreq, aFizGroup, aHeight,
					aWeight, aHeadSize, aAnalysesText,
					aZOJReccomend, aReccomend, divideNum);
			
			return createArchive(theArchiveFileName+"_"+new Random().nextInt(99999999)+".zip")+"@"+getBadCards();
		} catch (Exception e) {
			System.out.println("Exception happens !199_line ");
			e.printStackTrace();
			return null;
			
		}
					
					
		}
	
	private DataSource findDataSource() throws NamingException {
		return ApplicationDataSourceHelper.getInstance().findDataSource();
	}
	
	public void find_data(String SQLReq,
			String aFizGroup, String aHeight,
			String aWeight, String aHeadSize, String aAnalysesText,
			String aZOJReccomend, String aReccomend, String divideNum) throws ParseException,
			NamingException {
		int divideNumber = Integer.valueOf(divideNum);
		Statement statement = null;
		Element rootElement = new Element("children"); 
		badCards.setLength(0);
		try
		{
			DataSource ds = findDataSource();
			Connection dbh = ds.getConnection();
			statement = dbh.createStatement();
			
			ResultSet rs = statement.executeQuery(SQLReq);
			int numAll = 0;
			int numRight = 0;
			// --------------Значения по умолчания для всех записей.
			 
			String p_idType="3"; //3 - несовершеннолетний. Если сирота - то "1"
			String p_idCategory = "4"; //Категория ребенка. 4 - без категории. 1 - сирота, 2 - ТЖС, 3 - опекаемый
			int card_idType; //1 - ДД сирот, 2-Профосмотры, 3-Предварительные, 4 - Периодические
			
			String card_height = String.valueOf(aHeight); //"150"; //Рост (в см)
			String card_weight = String.valueOf(aWeight); //"40"; //Вес (в см)
			String card_headSize = String.valueOf(aHeadSize); //"30";  //Окружность головы (в см)
			String card_reccomend = aReccomend; //"_";
			String card_recommendZOZH = aZOJReccomend; //"Режим дня и отдыха - по возрасту, рациональное питание"
				//	+ ", закаливание, профилактика вредных привычек."; //Рекомендации
			String fizkult_G = String.valueOf(aFizGroup)!=null?aFizGroup:"1"; //"1"; //Группа здоровья для физкультуры
			String card_issl_result = aAnalysesText;//"Без патологий"; // Результат анализов
			int fiveLet = 5;
			int tenLet = 10;
			while (rs.next()) {
				numAll++;
				String diagnosis = rs.getString("mkbcode");
				String health_G = rs.getString("vedhgcode");
				String card_id = rs.getString("did");
				String passID = rs.getString("passID");
				String commonNumber = rs.getString("RZ");
				String patientInfo = rs.getString("patientinfo");
				String sex = rs.getString("sex");
				int smoCode = rs.getInt("smoCode");
				
				Element card_basic = new Element("basic");
				Element card_issled = new Element("issled");
				Element card_osmotri = new Element("osmotri");
				
				String vrach_f=rs.getString("vrach_f"); //ФИО врача
				String vrach_l=rs.getString("vrach_l");
				String vrach_m=rs.getString("vrach_m");
				String pid = rs.getString("pid");
				if (commonNumber==null || commonNumber.equals("")) {
					badCards.append(card_id).append(":").append(patientInfo).append(":").append(diagnosis).append(":").append("У пациента не заполено поле RZ").append("#");
					continue;
				}
				if (passID == null || (!passID.equals("3") && !passID.equals("14"))) {
					badCards.append(card_id).append(":").append(patientInfo).append(":").append(diagnosis).append(":").append("Неправильный тип документа УЛ ("+passID+")(должен быть либо паспорт, либо свид. о рождении)").append("#");
					continue;
				}
				if (!diagnosis.startsWith("Z") && health_G.equals("1")) { //Если 1 группа здоровья и диагноз !=Z (система выбросит)
					badCards.append(card_id).append(":").append(patientInfo).append(":").append(diagnosis).append(":").append("Расхождение группы здоровья и диагноза").append("#");
					continue;
				}
				String InsuranceCompany = "0";
				
				switch (smoCode) {
	            case 30002:	InsuranceCompany = "115"; //smocode=30002, omccode=15 makc
	            	break;
	            case 30004:	InsuranceCompany = "103"; //smocode=30004, omccode=7 sogaz
	            	break;	            
	            case 30005:	InsuranceCompany = "3690"; //smocode=30005, omccode=? maksimus
	    			break;
	    			
	            default:
	    			break;
				}
				
				if (InsuranceCompany.equals("0")){
					badCards.append(card_id).append(":").append(patientInfo).append(":").append(diagnosis).append(":").append("У пациента нет действующего полиса ОМС, СМО-").append(smoCode).append("#");
					continue;
				}
				
				
				numRight++;
				
				
				switch (Integer.parseInt(rs.getString("socCode"))) { //Определяем тип ДД (socCode: 4-профосмотр, 5-Предварительные, 6-Периодические)
		            case 5:  card_idType = 3; //Предварительные
		            	break;
		            case 4:  card_idType = 2;//профосмотр
		             	break;
		            case 6:  card_idType = 4;//Периодические
		            	break;
		            case 9999:  card_idType = 2; //сироты+опекаемые (на всяк. случай)
		            	break;
		            default: card_idType = 0;
			            badCards.append(card_id).append(":").append(patientInfo).append(":").append(diagnosis).append(":").append(" Обратитесь к разработчику!!!Ошибка - код_ДД-").append(rs.getString("dispType_id")).append("#");
						break;
				}
				String s_card_idType = Integer.toString(card_idType);
				
				int monthLet = Integer.parseInt(rs.getString("fullage"));
				
				
				statement = dbh.createStatement();
				
				
				//Ищем все исследования
				{
				String SQLissled = "SELECT distinct edc.servicedate as date "
						+ ", vedc.orphcode as iss_id "
						+ "FROM extdispservice edc "
						+ "left join vocextdispservice vedc on vedc.id=edc.servicetype_id "
						+ "where edc.card_id = " + card_id + " and edc.dtype='ExtDispExam' "
						+ "and edc.servicedate is not null and vedc.orphcode is not null "; //Есть ли даты у услуг
								
				ResultSet rs_issled = statement.executeQuery(SQLissled);
					while (rs_issled.next())
					{	
					card_basic.addContent(new Element("record")
						.addContent(new Element("id").addContent(rs_issled.getString("iss_id")))
							.addContent(new Element("date").addContent(rs_issled.getString("date")))
							.addContent(new Element("result").addContent(card_issl_result))
						);
					}
				} //Заканчивается поиск услуг
				
				card_issled.addContent(card_basic);
					
				{  //Запускаем поиск осмотров специалистами
					
				String SQLosmotri = "SELECT distinct edc.servicedate as date "
					+ ", vedc.orphcode as iss_id "
					+ "FROM extdispservice edc "
					+ "left join vocextdispservice vedc on vedc.id=edc.servicetype_id "
					+ "where edc.card_id = " + card_id + " and edc.dtype='ExtDispVisit' "
					+ "and edc.servicedate is not null and vedc.orphcode is not null ";
       			
				ResultSet rs_osmotri = statement.executeQuery(SQLosmotri);
					while (rs_osmotri.next())
					{
					card_osmotri.addContent(new Element("record")
						.addContent(new Element("id").addContent(rs_osmotri.getString("iss_id")))
							.addContent(new Element("date").addContent(rs_osmotri.getString("date")))
						);
					}
				} //Заканчивается поиск осмотров специалистами
				
				Element currPat = new Element ("child")
				.addContent(new Element("idInternal").addContent(rs.getString("pid")))
				.addContent(new Element("idType").addContent(p_idType)) 
				.addContent(new Element("name")
					.addContent(new Element("last").addContent(rs.getString("lastname")))
					.addContent(new Element("first").addContent(rs.getString("firstname")))
					.addContent(new Element("middle").addContent(rs.getString("middlename"))))
				.addContent(new Element("idSex").addContent(sex))
				.addContent(new Element("dateOfBirth").addContent(toDate(rs.getString("birthday"))))
				.addContent(new Element("idCategory").addContent(p_idCategory))
				.addContent(new Element("idDocument").addContent(passID))
				.addContent(new Element("documentSer").addContent(rs.getString("passSer")))
				.addContent(new Element("documentNum").addContent(rs.getString("passNum")))
				//.addContent(new Element("snils").addContent("000-000-000-00"))//rs.getString("snils")))
				//.addContent(new Element("polisSer").addContent(""))
				.addContent(new Element("polisNum").addContent(commonNumber))
				.addContent(new Element("idInsuranceCompany").addContent(InsuranceCompany))
				.addContent(new Element("medSanName").addContent(rs.getString("lpuName")))
				.addContent(new Element("medSanAddress").addContent(rs.getString("lpuAddress")))
				.addContent(new Element("address"));
				String fullAddress = rs.getString("fullAddress");
				if (fullAddress!=null&&!fullAddress.equals("0")&&!fullAddress.equals("")) {
					String[] arrAddress = fullAddress.split(":"); //postIndex:kladrNP:kladrStr:house:building:appartment
					boolean allOk = false;
					if (arrAddress[0].trim().length()==6&&arrAddress[2].trim().length()==17&&arrAddress[1].trim().length()==13) {allOk=true;}
					if (allOk) {
						currPat.getChild("address").addContent(new Element("index").addContent(arrAddress[0].trim()));
						currPat.getChild("address").addContent(new Element("kladrNP").addContent(arrAddress[1].trim()));
						currPat.getChild("address").addContent(new Element("kladrStreet").addContent(arrAddress[2].trim())); //Код улицы по KLADR
						if (arrAddress.length>3&&arrAddress[3]!=null&&!arrAddress[3].trim().equals("")) {
							currPat.getChild("address").addContent(new Element("house").addContent(arrAddress[3].trim())); //номер дома
						}
						if (arrAddress.length>4&&arrAddress[4]!=null&&!arrAddress[4].trim().equals("")) {
							currPat.getChild("address").addContent(new Element("building").addContent(arrAddress[4].trim())); //Корпус		
						}
						if (arrAddress.length>5&&arrAddress[5]!=null&&!arrAddress[5].trim().equals("")) {
							currPat.getChild("address").addContent(new Element("appartment").addContent(arrAddress[5].trim())); //Квартира	
						}
						
					} else {
						currPat.getChild("address").addContent(new Element("kladrNP").addContent("3000000100000")); //Код нас. пункта по KLADR (г. Астрахань)
					}
				} else {
					currPat.getChild("address").addContent(new Element("kladrNP").addContent("3000000100000")); //Код нас. пункта по KLADR (г. Астрахань)
				}
				//);
			//	.addContent(new Element("idEducationOrg").addContent(""))
			//	.addContent(new Element("idOrphHabitation").addContent("")) //Место текущего нахождения
			//	.addContent(new Element("dateOrphHabitation")) //Дата поступление в место текущего нахождения - для сироты - расскоментить
			//	.addContent(new Element("idStacOrg").addContent(""));	//Справочный идентификатор стационарного учреждения	
				Element cards = new Element("cards");
				
				Element card = new Element("card")
							.addContent(new Element("idInternal").addContent(card_id))
							.addContent(new Element("dateOfObsled").addContent(toDate(rs.getString("edcBeginDate")))) //Дата начала обследования
						//	.addContent(new Element("ageObsled").addContent("")) //Возраст ребёнка в месяцах на момент проведения обследования (необязательно)
							.addContent(new Element("idType").addContent(s_card_idType)) 
							.addContent(new Element("height").addContent(card_height))
							.addContent(new Element("weight").addContent(card_weight))
							.addContent(new Element("headSize").addContent(card_headSize));
//							.addContent(new Element("healthProblems")
//								.addContent(new Element("problem").addContent(""))
//								);
//							
				if (monthLet<fiveLet)
						{
							card.addContent(new Element("pshycDevelopment") //Оценка возраста психического развития для детей от 0 до 4 лет в месяцах (по умолчанию - кол-во месяцев)
									.addContent(new Element("poznav").addContent(String.valueOf(monthLet*12))) // познавательная функция
									.addContent(new Element("motor").addContent(String.valueOf(monthLet*12))) //моторная функция
									.addContent(new Element("emot").addContent(String.valueOf(monthLet*12)))	//эмоциональная и социальная (контакт с окружающим миром) функции		
									.addContent(new Element("rech").addContent(String.valueOf(monthLet*12))) //предречевое и речевое развитие
								);
						}
					else
						{
							card.addContent(new Element("pshycState") //Оценка состояния психического развития для детей от 5 лет (0 - в норме)
									.addContent(new Element("psihmot").addContent("0")) //Психомоторная сфера
									.addContent(new Element("intel").addContent("0")) // Интеллект
									.addContent(new Element("emotveg").addContent("0")) //Эмоционально-вегетативная сфера
								);
						}
					if (monthLet>=tenLet)
						{
							if (sex.equals("1"))
							{
							card.addContent(new Element("sexFormulaMale")  //Половая формула (муж.) старше 10 лет
								.addContent(new Element("P").addContent("1"))
								.addContent(new Element("Ax").addContent("1"))
								.addContent(new Element("Fa").addContent("1"))
							);
							}
							else 
							{
							card.addContent(new Element("sexFormulaFemale") // Половая формула (жен.)
								.addContent(new Element("P").addContent("1"))
								.addContent(new Element("Ma").addContent("1"))
								.addContent(new Element("Ax").addContent("1"))
								.addContent(new Element("Me").addContent("1"))
							);
							card.addContent(new Element("menses") //  Менструальная функция
								.addContent(new Element("menarhe").addContent("144"))
								.addContent(new Element("characters")
									.addContent(new Element("char").addContent("1"))
								)
							);
							}
						}
								card.addContent(new Element("healthGroupBefore").addContent(health_G)) //Группа здоровья до проведения обследования (числом)
								.addContent(new Element("fizkultGroupBefore").addContent(fizkult_G)) // Медицинская группа для занятий физической культурой
								.addContent(new Element("diagnosisBefore")
									.addContent(new Element("diagnosis")
										.addContent(new Element("mkb").addContent(rs.getString("mkbcode")))
										.addContent(new Element("dispNablud").addContent("3")) //Диспансерное наблюдение (1-ранее, 2 - впервые, 3 - не установлено)
								//		.addContent(new Element("lechen") //Лечение назначено
										//	.addContent(new Element("condition").addContent("1")) //условия (1,2,3)1 - амбулаторно
										//	.addContent(new Element("organ").addContent("2")) //организация (2 - ГБУЗ)
										//	.addContent(new Element("notDone").addContent("") //Причина невыполнения
										//		.addContent(new Element("reason").addContent(""))
										//		.addContent(new Element("reasonOther").addContent(""))
										//	)
								//		)
//										.addContent(new Element("reabil").addContent("") //Лечение назначено
//											.addContent(new Element("condition").addContent("1")) //условия стационар?(1,2,3)
//											.addContent(new Element("organ").addContent("2")) //организация (2 - ГБУЗ)
//											.addContent(new Element("notDone").addContent("") //Причина невыполнения
//												.addContent(new Element("reason").addContent(""))
//												.addContent(new Element("reasonOther").addContent(""))
//											)
//										)
										.addContent(new Element("vmp").addContent("0")) //ВМП рекоменд. и оказана (1), рек+не ок(2), не рек(0)
									));
									if (health_G.equals("1"))
									{
										card.addContent(new Element("healthyMKB").addContent(rs.getString("mkbcode"))); //Код осмотра, если ребёнок здоров (Z00-Z99)
									}
									else
									{
									card.addContent(new Element("diagnosisAfter").addContent("")
											.addContent(new Element("diagnosis").addContent("")
												.addContent(new Element("mkb").addContent(rs.getString("mkbcode")))
												.addContent(new Element("firstTime").addContent("0")) //выявлен впервые? 1,0
												.addContent(new Element("dispNablud").addContent("0")) //Диспансерное наблюдение (0 - не установлено, 1 - ранее, 2 - впервые)
										//		.addContent(new Element("lechen")
										//			.addContent(new Element("condition").addContent("1")) //условия стационар?(1-амб,2,3)
										//			.addContent(new Element("organ").addContent("2")) //организация (2 - ГБУЗ)
										//		)
//												.addContent(new Element("reabil")
//													.addContent(new Element("condition").addContent("")) //условия стационар?(1,2,3)
//													.addContent(new Element("organ").addContent("")) //организация (2 - ГБУЗ)
//												)
//												.addContent(new Element("consul") //Дополнительные консультации и исследования назначены
//													.addContent(new Element("condition").addContent("")) //условия стационар?(1,2,3)
//													.addContent(new Element("organ").addContent("")) //организация (2 - ГБУЗ)
//													.addContent(new Element("state").addContent("")) //Консультации выполнены или не выполнены (0,1,2).
//												)
												.addContent(new Element("needVMP").addContent("0")) //Рекомендована ВМП (0-1)
												.addContent(new Element("needSMP").addContent("0")) //Рекомендована СМП (0-1)
												.addContent(new Element("needSKL").addContent("0")) //Рекомендовано СКЛ (0-1)
												.addContent(new Element("recommendNext").addContent(card_reccomend)) //Рекомендации по диспансерному наблюдению, лечению, 
											)
									);
									}
//									.addContent(new Element("invalid").addContent("")
//											.addContent(new Element("type").addContent("")) //  Вид инвалидности ( с рождения/нет 1-2)
//											.addContent(new Element("dateFirstDetected").addContent("")) //Дата первого освидетельствования
//											.addContent(new Element("dateLastConfirmed").addContent("")) //Дата последнего освидетельствования
//											.addContent(new Element("illnesses").addContent("") // Заболевания, обусловившие возникновение инвалидности
//													.addContent(new Element("illness").addContent(""))
//											)
//											.addContent(new Element("defects").addContent("") // Виды нарушений в состоянии здоровья 1-9
//													.addContent(new Element("defect").addContent(""))
//											)
//									);
							
									card.addContent(card_issled);
									card.addContent(new Element("healthGroup").addContent(health_G));
									card.addContent(new Element("fizkultGroup").addContent(fizkult_G)); // группа занятия физкультурой
									card.addContent(new Element("zakluchDate").addContent(toDate(rs.getString("edcFinishDate"))));
									card.addContent(new Element("zakluchVrachName")
											.addContent(new Element("last").addContent(vrach_l))
											.addContent(new Element("first").addContent(vrach_f))
											.addContent(new Element("middle").addContent(vrach_m))
									);
									card.addContent(card_osmotri);
									cards.addContent(card);
									currPat.addContent(cards);
									card.addContent(new Element("recommendZOZH").addContent(card_recommendZOZH));
//									card.addContent(new Element("reabilitation")
//											.addContent(new Element("date"))
//											.addContent(new Element("state"))
//									);
									card.addContent(new Element("privivki")
											.addContent(new Element("state").addContent("1")) // Привит по возрасту
//											.addContent(new Element("privs")
//													.addContent(new Element("priv")))
								);
									card.addContent(new Element("oms").addContent("0"));

									rootElement.addContent(currPat);
					if (divideNumber!=0 && numRight%divideNumber==0){ 
						//System.out.println("-------------------ExtDispServiceBean, Пришло время разделяться!!!");
						createFile(rootElement);
						rootElement = new Element("children");
					}
				}
			dbh.close();
			if (!rootElement.getChildren().isEmpty()) {
				createFile(rootElement);	
			}
			System.out.println("ExtDispExport: Всего записей = " + numAll);
			System.out.println("ExtDispExport: Всего записей без ошибок= " + numRight);
//			System.out.println();
			System.out.println("ExtDispExport: ErrorText= "+badCards.toString());
	//		return theArchiveFileName;
			}
			
		catch (SQLException e)
			{
			if (CAN_DEBUG) LOG.debug("Find data = ERROR");
	        System.out.println(e.getMessage());
	        System.out.println(badCards.toString());
	        rootElement.addContent(new Element("ERROR!!!!"+e.getMessage()));
			//return rootElement;
	        }	
	//		return theArchiveFileName;
		}
	
	public void createFile(Element aElement) {
		
		try {
			
			org.jdom.output.XMLOutputter outputter = new org.jdom.output.XMLOutputter();
			String fileName="orph-"+theFileSuffix+theFinishDate+"_"+aElement.hashCode()+".xml";
			EjbEcomConfig config = EjbEcomConfig.getInstance() ;
			String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
	    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
			String outputFile=workDir+"/"+fileName;
			FileWriter fwrt = new FileWriter(outputFile);
			Document pat = new Document(aElement);
			outputter.setFormat(org.jdom.output.Format.getPrettyFormat().setEncoding("UTF-8"));
			outputter.output(pat, fwrt);
			fwrt.close();
			aFileNames+=fileName+":";
		}
	
		 catch (Exception ex) {
			 System.out.println(ex.getMessage());
			 System.out.println("Someshing happened strange!!!");
		}	
	}
	public static String toDate(String args)throws ParseException
	{
		SimpleDateFormat nDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat nDate2 = new SimpleDateFormat("dd.MM.yyyy");
		
		return nDate.format(nDate2.parse(args)).toString();
	}
	

	private @PersistenceContext EntityManager theManager;
    private final static Logger LOG = Logger.getLogger(ExtDispServiceBean.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
    public String setOrphCodes() throws NamingException {
    	DataSource ds = findDataSource();
    	String SQLReq = "select veds.code, veds.id from vocextdispservice veds ";
		Statement statement;
		HashMap <String, String> codeMap = new HashMap<String, String>();
		codeMap.put("N1_021","1"); //Анализ крови
		codeMap.put("N1_022","2"); //Анали мочи
		codeMap.put("N1_025","3"); //анализ кала
		codeMap.put("N1_024","4"); //глюкоза
		codeMap.put("N1_027","5"); //уровень гормонов
		codeMap.put("N1_015","6"); //УЗИ брюшной полости
		codeMap.put("N1_016","7"); //УЗИ сердца
		codeMap.put("N1_017","8"); //УЗИ щитовидной железы
		codeMap.put("N1_018","9"); //УЗИ органов репр. сферы
		codeMap.put("N1_019","10"); //УЗИ тазобедр. суставов
		codeMap.put("N1_020","11"); //Нейросонография
		codeMap.put("N1_026","12"); //Флюрография
		codeMap.put("N1_023","13"); //ЭКГ
		codeMap.put("N1_028","14"); //Неонатальный скрининг на врожденный гипотериоз
		codeMap.put("N1_013","14"); //Неонатальный скрининг
		codeMap.put("N1_029","14"); //Неонатальный скрининг на фенилкетонурию
		codeMap.put("N1_030","14"); //Неонатальный скрининг на адреногенимальный синдром
		codeMap.put("N1_031","14"); //Неонатальный скрининг на муковисцедоз
		codeMap.put("N1_032","14"); //Неонатальный скрининг на галактоземию
		codeMap.put("N1_014","15"); //Аудиологический скрининг
									//16 = Анализ кала на яйца глистов
		codeMap.put("N1_033","17"); //Анализ оскиуглерода
									//18 - УЗИ почек
									//19 - УЗИ печени
		codeMap.put("N1_001","1"); //Педиатр
		codeMap.put("N1_002","2"); //Невролог
		codeMap.put("N1_004","3"); //Офтальмолог
		codeMap.put("N1_003","4"); //Детский хирург
		codeMap.put("N1_007","5"); //ЛОР 
		codeMap.put("N1_005","6"); //Травматолог-ортопед
		codeMap.put("N1_008","7"); //Психиатр до 14 лет
		codeMap.put("N1_012","7"); //Психиатр с 14 лет
		codeMap.put("N1_006","8"); //Детский стоматолог
		codeMap.put("N1_011","9"); //Эндокринолог
		codeMap.put("N1_010","10"); //Уролог-андролог
		codeMap.put("N1_009","11"); //Акушер-гинеколог

		
		
		try {
			Connection dbh = ds.getConnection();
			statement = dbh.createStatement();
			Statement statement2 = null;
			ResultSet rs = statement.executeQuery(SQLReq);
			
			while (rs.next()) {
				if (codeMap.get(rs.getString(1))!=null) {
				 statement2 = dbh.createStatement();
					String sqlReq="update vocextdispservice set orphcode='"+codeMap.get(rs.getString(1))+"' where code = '"+rs.getString(1) +"' ";
//					System.out.println(sqlReq);
					statement2.executeUpdate(sqlReq);
				} 
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Произошла ошибка";
		}
		return "Коды для экспорта успешно добавлены.";
	}

}	
		
