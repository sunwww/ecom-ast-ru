package ru.ecom.mis.ejb.service.extdisp;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
 * Версия формата 201403 ?
 *
 */
public class ExtDispServiceBean implements IExtDispService {
	StringBuilder error_text=new StringBuilder();
	StringBuilder badCards =new StringBuilder();
	public String getBadCards () {
		if (badCards.length()>0) return badCards.toString();
		else return null;
		
	}
	
	public String getErrorText () {
		if (error_text.length()>0)return error_text.toString(); 
		else return null;
	}
	
	public String exportOrph(String aStartDate, String aFinishDate,
			String aFileNameSuffix, String aSqlAdd, String aFizGroup, String aHeight,
			String aWeight, String aHeadSize, String aAnalysesText,
			String aZOJReccomend, String aReccomend) throws ParseException,
			NamingException {
		if (aStartDate==null || aStartDate.equals("")||aFinishDate==null || aFinishDate.equals("")) {
			return null;
		}
		  
	/** Сделано:
	 * Проверка на пасп. данные, номер полиса.
	 * Проверка на наличие даты исследования.	
	 * Проверку на номер полиса (RZ)
	 * Тип документа должен быть только 3 или 14
	 * Проверка на 1 группу здоровья и диагноз, отличный от Z**.*
	*/	
		
	String SQLreq ="select distinct edc.id as did, p.id as pid,p.lastname, p.firstname, p.middlename, p.sex_id as sex, p.snils "
				+", to_char(p.birthday,'dd.mm.yyyy') as birthday "
				+",to_char(edc.startDate,'dd.mm.yyyy') as edcBeginDate "
				+",to_char(edc.finishDate,'dd.mm.yyyy') as edcFinishDate "
				+",vedsg.code as socCode "
				+",mkb.code as mkbcode ,vedag.name as vedagname ,vedsg.name as vedsgname ,vedhg.code as vedhgcode "
				+",mp.company_id "
				+",edc.isObservation as cntDispM "
				+",edc.isTreatment as cntLechM "
				+",edc.isDiagnostics as cntDiagM "
				+",p.commonnumber as RZ "
				+",cast(to_char(edc.finishDate,'yyyy')as int)-cast(to_char(p.birthday,'yyyy')as int)+ "
				+ "case when ((cast(to_char(edc.finishDate,'MM')as int))-cast(to_char(p.birthday,'MM')as int)<0) or "
				+ "((cast(to_char(edc.finishDate,'MM')as int))-cast(to_char(p.birthday,'MM')as int)=0 "
				+ "and ((cast(to_char(edc.finishDate,'dd')as int))-cast(to_char(p.birthday,'dd')as int)<0)) then -1 else 0 end as fullage "
				+",case when (p.passportseries is not null and p.passportseries!='') then p.passportseries else '12 00' end as passSer "
				+",case when (p.passportnumber is not null and p.passportnumber !='') then p.passportnumber else '123456' end as passNum "
				+",vic.code as passID "
				+",pwr.lastname as vrach_l " 
				+",pwr.firstname as vrach_f "
				+",pwr.middlename as vrach_m "
				+",edc.isServiceIndication as cntIsServiceIndication " 
				+",lpu2.name as lpuName " 
				+",lpu2.printAddress as lpuAddress "
				+"from ExtDispCard edc " 
				+"left join mislpu lpu2 on lpu2.id=edc.lpu_id "
				+"left join WorkFunction wf on wf.id=edc.workFunction_id "
				+"left join Worker w on w.id=wf.worker_id "
				+"left join Patient pwr on pwr.id=w.person_id "
				+"left join MisLpu lpu on lpu.id=w.lpu_id "
				+"left join Patient p on p.id=edc.patient_id "
				+"left join vocidentitycard vic on vic.id=p.passporttype_id "
				+"left join VocExtDisp ved on ved.id=edc.dispType_id "
				+"left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id "
				+"left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id "
				+"left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id "
				+"left join ExtDispRisk edr on edr.card_id=edc.id "
				+"left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id "
				+"left join VocIdc10 mkb on mkb.id=edc.idcMain_id "
				+"left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null "
				+"left join (select company_id,patient_id from medpolicy limit 1) mp on mp.patient_id = p.id "
				+"where edc.finishDate between to_date('"+aStartDate+"','dd.mm.yyyy') " 
				+"and to_date('"+aFinishDate+"','dd.mm.yyyy') "
	//			+"and vedsg.code ='4' " //Выбираем тип ДД (4-проф, 5 - предварительные, 6 - периодические)
				+"and vic.code in ('3','14') " //Только паспорт и свидетельства о рождении!
				+"and (p.commonnumber is not null and p.commonnumber!='') " 
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
			Element root = find_data(SQLreq, aFizGroup, aHeight,
					aWeight, aHeadSize, aAnalysesText,
					aZOJReccomend, aReccomend);
			if (root==null) {
				System.out.println("Document is empty");
				return null;
			}
			Document pat = new Document(root);
			EjbEcomConfig config = EjbEcomConfig.getInstance() ;
			String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
			workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
			String filename = "orph-"+aFileNameSuffix+aFinishDate+".xml";
			String outputFile = workDir+"/"+filename;
					
			try {
				
				org.jdom.output.XMLOutputter outputter = new org.jdom.output.XMLOutputter();
				FileWriter fwrt = new FileWriter(outputFile);
				
				outputter.setFormat(org.jdom.output.Format.getPrettyFormat().setEncoding("UTF-8"));
				outputter.output(pat, fwrt);
				fwrt.close();
				return filename;
			}
		
			 catch (Exception ex) {
				 System.out.println(ex.getMessage());
			}
			System.out.println("Someshing happened strange!!!");
			return null;
			}
	
	private DataSource findDataSource() throws NamingException {
		return ApplicationDataSourceHelper.getInstance().findDataSource();
	}
	
	public Element find_data(String SQLReq,
			String aFizGroup, String aHeight,
			String aWeight, String aHeadSize, String aAnalysesText,
			String aZOJReccomend, String aReccomend) throws ParseException,
			NamingException {
			
		Statement statement = null;
		Element rootElement = new Element("chlidren"); 
			
			/*
			Выбрать все исследования, где card_number - card_num
			*/
			
		try
		{
			DataSource ds = findDataSource();
			Connection dbh = ds.getConnection();
			statement = dbh.createStatement();
			
			ResultSet rs = statement.executeQuery(SQLReq);
			int numAll = 0;
			// --------------Значения по умолчания для всех записей.
			String sex = "1"; // Пол (1=2, 2=1) 
			String p_idType="3"; //3 - несовершеннолетний. Если сирота - то "1"
			String p_idCategory = "4"; //Категория ребенка. 4 - без категории. 1 - сирота, 2 - ТЖС, 3 - опекаемый
			int card_idType; //1 - ДД сирот, 2-Профосмотры, 3-Предварительные, 4 - Периодические
			
			String card_height = String.valueOf(aHeight); //"150"; //Рост (в см)
			String card_weight = String.valueOf(aWeight); //"40"; //Вес (в см)
			String card_headSize = String.valueOf(aHeadSize); //"30";  //Окружность головы (в см)
			String card_reccomend = aReccomend; //"_";
			String card_recommendZOZH = aZOJReccomend; //"Режим дня и отдыха - по возрасту, рациональное питание"
				//	+ ", закаливание, профилактика вредных привычек."; //Рекомендации
			String fizkult_G = String.valueOf(aFizGroup); //"1"; //Группа здоровья для физкультуры
			String card_issl_result = aAnalysesText;//"Без патологий"; // Результат анализов
			int fiveLet = 5;
			int tenLet = 10;
			
			while (rs.next()) {
				numAll++;
				if (numAll%70==0){ //TODO: кол-во карт в выгрузке
					
				}
				String diagnosis = rs.getString("mkbcode");
				String health_G = rs.getString("vedhgcode");
				String card_id = rs.getString("did");
				String passID = rs.getString("passID");
				String commonNumber = rs.getString("RZ");
				
				if (commonNumber==null || commonNumber.equals("")) {
					badCards.append(card_id).append(":").append("У пациента не заполено поле RZ").append("#");
					continue;
				}
				if (passID == null || passID.equals("")) {
					badCards.append(card_id).append(":").append("Неправильный тип документа УЛ (должен быть либо паспорт, либо свид. о рождении)").append("#");
					continue;
				}
				if (!diagnosis.startsWith("Z") && health_G.equals("1")) { //Если 1 группа здоровья и диагноз !=Z (система выбросит)
					error_text.append(" |Расхождение группы здоровья и диагноза, ребенок=").append(rs.getString("lastname")).append(" ").append(rs.getString("firstname")).append(" ").append(rs.getString("middlename"));
					badCards.append(rs.getString(card_id)).append(":").append("Расхождение группы здоровья и диагноза").append("#");
					continue;
				}
				if (rs.getString("sex").equals("1")) sex = "2"; else sex="1";
				
				
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
		            error_text.append(" !Ошибка - код_ДД-").append(rs.getString("dispType_id"));
		            	break;
				}
				String s_card_idType = Integer.toString(card_idType);
				Element card_basic = new Element("basic");
				Element card_issled = new Element("issled");
				Element card_osmotri = new Element("osmotri");
				
				String vrach_f=rs.getString("vrach_f"); //ФИО врача
				String vrach_l=rs.getString("vrach_l");
				String vrach_m=rs.getString("vrach_m");
				String pid = rs.getString("pid");
				
				int monthLet = Integer.parseInt(rs.getString("fullage"));
				
				String InsuranceCompany = getOMCNumber(pid,dbh);
				if (InsuranceCompany.equals("0")){
					badCards.append(card_id).append(":").append("У пациента нет действующей страховой компании").append("#");
					continue;
				}
				statement = dbh.createStatement();
				
				
				//Ищем все исследования
				{
				String SQLissled = "SELECT servicedate as date "
						+ ", servicetype_id as iss_id "
						+ "FROM extdispservice "
						+ "where card_id = " + card_id + " and dtype='ExtDispExam' "
						+ "and servicedate is not null "; //Есть ли даты у услуг
								
				ResultSet rs_issled = statement.executeQuery(SQLissled);
					while (rs_issled.next())
					{
						int iss_id = Integer.parseInt(rs_issled.getString("iss_id"));
						switch (iss_id) {
			            case 133:  iss_id = 1;
			            	break;
			            case 134:  iss_id = 2;
			            	break;
			            case 137:  iss_id = 3;
			            	break;
			            case 136:  iss_id = 4;
			            	break;
			            case 139:  iss_id = 5;
			            	break;
			            case 127:  iss_id = 6;
			            	break;
			            case 128:  iss_id = 7;
			            	break;
			            case 129:  iss_id = 8;
	                     	break;
			            case 130:  iss_id = 9;
			            	break;
			            case 131:  iss_id = 10;
			            	break;
			            case 132:  iss_id = 11;
			            	break;
			            case 138:  iss_id = 12;
			            	break;
			            case 135:  iss_id = 13;
			            	break;
			            case 140:  iss_id = 14;
			            	break;
			            case 126:  iss_id = 15;
			            	break;
			            case 145:  iss_id = 17;
			            	break;
			            default: iss_id = 0;
			            error_text.append(" !Ошибка - код исследования-").append(rs_issled.getString("iss_id"));
			                     break;
						}
					card_basic.addContent(new Element("record")
						.addContent(new Element("id").addContent(Integer.toString(iss_id)))
							.addContent(new Element("date").addContent(rs_issled.getString("date")))
							.addContent(new Element("result").addContent(card_issl_result))
						);
					}
				} //Заканчивается поиск услуг
				
					card_issled.addContent(card_basic);
					
					{  //Запускаем поиск осмотров специалистами
					
					String SQLosmotri = "SELECT servicedate as date "
							+ ", servicetype_id as iss_id "
							+ "FROM extdispservice "
							+ "where card_id = " + card_id + " and dtype='ExtDispVisit'";
	       			
					
					ResultSet rs_osmotri = statement.executeQuery(SQLosmotri);
						while (rs_osmotri.next())
						{
							int iss_id = Integer.parseInt(rs_osmotri.getString("iss_id"));
							switch (iss_id) {
				            case 113:  iss_id = 1;
				            	break;
				            case 114:  iss_id = 2;
		                    	break;
				            case 116:  iss_id = 3;
				            	break;
				            case 115:  iss_id = 4;
				            	break;
				            case 119:  iss_id = 5;
				            	break;
				            case 117:  iss_id = 6;
				             	break;
				            case 120:  iss_id = 7;
				            	break;
				            case 118:  iss_id = 8;
				            	break;
				            case 123:  iss_id = 9;
				            	break;
				            case 122:  iss_id = 10;
				            	break;
				            case 121:  iss_id = 11;
				            	break;
				            case 124: iss_id = 7;
				            	break;
				            default: iss_id = 0;
				            	error_text.append(" !Ошибка - код осмотра-").append(rs_osmotri.getString("iss_id"));
				            	break;
							}
						card_osmotri.addContent(new Element("record")
							.addContent(new Element("id").addContent(Integer.toString(iss_id)))
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
				.addContent(new Element("address")
				//	.addContent(new Element("index").addContent("414000"))
					.addContent(new Element("kladrNP").addContent("3000000100000")) //Код нас. пункта по KLADR (г. Астрахань)
				//	.addContent(new Element("kladrStreet").addContent("00000000000000000")) //Код улицы по KLADR
				//	.addContent(new Element("house").addContent("1")) //номер дома
				//	.addContent(new Element("building").addContent("1")) //Корпус
				//	.addContent(new Element("appartment").addContent("1")) //Квартира
				);
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
				}
			
			dbh.close();
			System.out.println("Всего записей = " + numAll);
			System.out.println();
			System.out.println("ErrorText= "+error_text);
			return rootElement;
			}
			
		catch (SQLException e)
			{
			if (CAN_DEBUG) LOG.debug("Find data = ERROR");
	        System.out.println(e.getMessage());
	        System.out.println(badCards.toString());
	        rootElement.addContent(new Element("ERROR!!!!"+e.getMessage()));
			//return rootElement;
	        }	
			return rootElement;
		}
	public static String getOMCNumber(String patient, Connection dbh)
	{
		String ret = "115";
		String SQLReq = "SELECT ri.smocode FROM reg_ic ri left join medpolicy mp on mp.company_id=ri.id where mp.patient_id = "+ patient + " order by actualdatefrom desc limit 1";
		System.out.println("GetOMCNumber sql = "+SQLReq);
		try{
			
			
			Statement statement = dbh.createStatement();
			ResultSet rs = statement.executeQuery(SQLReq);
			while(rs.next()) {
				int sk = rs.getInt(1);
				switch (sk) {
	            case 30002:	ret = "115";
	            	break;
	            case 30004:	ret = "103";
	    			break;
	            default:  	ret = "115";
	            System.out.println("Нет актуального полиса!!!, СК по умолчанию");
	    			break;
				}
			}
			return ret;

		}
		
		catch (SQLException e)
		{
			System.out.println("in getOMCNumber error="+e.getMessage());
		}
		return ret;
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
	

}	
		
