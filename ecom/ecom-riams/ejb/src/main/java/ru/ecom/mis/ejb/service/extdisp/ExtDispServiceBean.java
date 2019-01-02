package ru.ecom.mis.ejb.service.extdisp;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.ejb.services.util.ApplicationDataSourceHelper;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispCard;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispExam;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispService;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispVisit;
import ru.nuzmsh.util.StringUtil;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private @PersistenceContext EntityManager theManager;
	private static final Logger LOG = Logger.getLogger(ExtDispServiceBean.class);

	/*Упаковка в архив файлов */
	private String createArchive(String archiveName, List<String> aFileNames) {
			if (!aFileNames.isEmpty()) {
				EjbEcomConfig config = EjbEcomConfig.getInstance() ;
				String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
				StringBuilder sb = new StringBuilder();
				sb.append("zip -r -j -9 ").append(workDir).append("/").append(archiveName).append(" ") ;
				for (String fileName : aFileNames) {
					sb.append(workDir).append("/").append(fileName).append(" ");
				}
				try {
					Runtime.getRuntime().exec(sb.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
					LOG.error("Нет файлов для архивации");
			}
		return archiveName;
	}
	
	public String exportOrph(Date aStartDate, Date aFinishDate,String aFileNameSuffix, String aSqlAdd, int aFizGroup, int aHeight,
			int aWeight, int aHeadSize, String aAnalysesText,String aZOJReccomend, String aReccomend, int divideNum, Long aLpu) {
		if (aStartDate == null || aFinishDate == null) {
			LOG.error("Не заполнены дата начала либо дата окончания ДД");
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		String aLpuSqlAdd = (aLpu!=null && aLpu>0) ? "and edc.lpu_id="+aLpu : "";
		/** Сделано:
		 * Проверка на пасп. данные, номер полиса.
		 * Проверка на наличие даты исследования.
		 * Проверку на номер полиса (RZ)
		 * Тип документа должен быть только 3 или 14
		 * Проверка на 1 группу здоровья и диагноз, отличный от Z**.*
		*/
		
		String sql="select distinct edc.id as did, p.id as pid,p.lastname, p.firstname, p.middlename, p.patientinfo, vs.omccode as sex, p.snils as snils "
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
				+"where edc.finishDate between to_date('"+format.format(aStartDate)+"','dd.mm.yyyy') and to_date('"+format.format(aFinishDate)+"','dd.mm.yyyy') "
				+aLpuSqlAdd
				+" and ved.code='CHILD_PROF_1' "
				+"and cast(to_char(edc.finishDate,'yyyy')as int)-cast(to_char(p.birthday,'yyyy')as int)+ "
				+"case when ((cast(to_char(edc.finishDate,'MM')as int))-cast(to_char(p.birthday,'MM')as int)<0) or "
				+"((cast(to_char(edc.finishDate,'MM')as int))-cast(to_char(p.birthday,'MM')as int)=0 "
				+ "and ((cast(to_char(edc.finishDate,'dd')as int))-cast(to_char(p.birthday,'dd')as int)<0)) then -1 else 0 end <18 " 
				+aSqlAdd
				+"order by p.lastname,p.firstname,p.middlename ";
				
		LOG.info("Выгрузка PRPH, sql="+sql);
		String fileName="orph-"+aFileNameSuffix+format.format(aFinishDate);
		try {
			return findData(sql, aFizGroup, aHeight,aWeight, aHeadSize, aAnalysesText,aZOJReccomend, aReccomend, divideNum,fileName);
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}

		private JSONObject createBadCardObject(String aCardId, String aPatientInfo, String aDiagnosis, String aErrorName) {
			return new JSONObject().put("cardId",aCardId).put("patientInfo", aPatientInfo).put("diagnosis",aDiagnosis).put("errorName",aErrorName);
		}
	private String findData(String aSqlString, int aFizGroup, int aHeight, int aWeight, int aHeadSize, String aAnalysesText,
			String aZOJReccomend, String aReccomend, int divideNum, String xmlFilename) throws NamingException {

		Element rootElement = new Element("children");
		JSONArray errorCards = new JSONArray();
		List<String> xmlFilenames = new ArrayList<>();
		ExtDispCard extDispCard;
		DataSource ds = ApplicationDataSourceHelper.getInstance().findDataSource();
		JSONObject ret = new JSONObject();
		try (Connection dbh = ds.getConnection();
			 Statement statement = dbh.createStatement();
			 ResultSet rs = statement.executeQuery(aSqlString)){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			int numRight = 0;
			// --------------Значения по умолчания для всех записей.
			 
			String patIdType="3"; //3 - несовершеннолетний. Если сирота - то "1"
			String patIdCategory = "4"; //Категория ребенка. 4 - без категории. 1 - сирота, 2 - ТЖС, 3 - опекаемый
			String cardIdType ="2"; //1 - ДД сирот, 2-Профосмотры, 3-Предварительные, 4 - Периодические
			
			String cardReccomend = aReccomend; //"_";
			String cardRecommendZOZH = aZOJReccomend; //"Режим дня и отдыха - по возрасту, рациональное питание"
				//	+ ", закаливание, профилактика вредных привычек."; //Рекомендации
			String fizkultG = aFizGroup>0?aFizGroup+"":"1"; //"1"; //Группа здоровья для физкультуры
			String cardIsslResult = aAnalysesText;//"Без патологий"; // Результат анализов
			Date currentDate = new Date(System.currentTimeMillis());
			String astrakhanFias = "83009239-25CB-4561-AF8E-7EE111B1CB73";

			while (rs.next()) {
				String diagnosis = rs.getString("mkbcode");
				String healthG = rs.getString("vedhgcode");
				String cardId = rs.getString("did");
				extDispCard = theManager.find(ExtDispCard.class,Long.valueOf(cardId));
				String passId = rs.getString("passID");
				String commonNumber = rs.getString("RZ");
				String patientInfo = rs.getString("patientinfo");
				String sex = rs.getString("sex");
				String snils = rs.getString("snils");
				int smoCode = rs.getInt("smoCode");
				

				Element cardOsmotri = new Element("osmotri");
				
				String vrachF=rs.getString("vrach_f"); //ФИО врача
				String vrachL=rs.getString("vrach_l");
				String vrachM=rs.getString("vrach_m");
				String lpuName = rs.getString("lpuName");
				String lpuAddress = rs.getString("lpuAddress");
				
				if (StringUtil.isNullOrEmpty(commonNumber)) {
					errorCards.put(createBadCardObject(cardId,patientInfo,diagnosis,"Не заполнено поле RZ"));
					continue;
				}
				if (StringUtil.isNullOrEmpty(lpuName)) {
					errorCards.put(createBadCardObject(cardId,patientInfo,diagnosis,"Наименование ЛПУ не указано"));
					continue;
				}
				
				if (StringUtil.isNullOrEmpty(lpuAddress)) {
					errorCards.put(createBadCardObject(cardId,patientInfo,diagnosis,"У ЛПУ не заполнено поле - адрес. ЛПУ - "+lpuName));
					continue;
				}
				if (!"3".equals(passId) && !"14".equals(passId)) {
					errorCards.put(createBadCardObject(cardId,patientInfo,diagnosis,"Неправильный тип документа УЛ ("+passId+")(должен быть либо паспорт, либо свид. о рождении)"));
					continue;
				}
				if (!diagnosis.startsWith("Z") && "1".equals(healthG)) { //Если 1 группа здоровья и диагноз !=Z (система выбросит)
					errorCards.put(createBadCardObject(cardId,patientInfo,diagnosis,"Расхождение группы здоровья и диагноза"));
					continue;
				}
				if (StringUtil.isNullOrEmpty(snils)) {
					errorCards.put(createBadCardObject(cardId,patientInfo,diagnosis,"Не указан СНИЛС пациената!"));
					continue;
				}
				String insuranceCompany ;
				
				switch (smoCode) {
	            case 30002:	insuranceCompany = "115"; //smocode=30002, omccode=15 makc
	            	break;
	            case 30004:	insuranceCompany = "103"; //smocode=30004, omccode=7 sogaz
	            	break;	            
	            case 30005:	insuranceCompany = "3690"; //smocode=30005, omccode=? maksimus
	    			break;
	            default:
					errorCards.put(createBadCardObject(cardId,patientInfo,diagnosis,"У пациента нет действующего полиса ОМС, СМО-"+smoCode));
					continue;
				}

				if ("4".equals(rs.getString("socCode"))) { //Определяем тип ДД (socCode: 4-профосмотр, 5-Предварительные, 6-Периодические)
					cardIdType = "2";//профосмотр
				} else {
					LOG.error("с 18 года выгружаются только профосмотры");
					errorCards.put(createBadCardObject(cardId,patientInfo,diagnosis," выгружаются только профосмотры!!!Ошибка - код_ДД-"+rs.getString("dispType_id")));
				}
				numRight++;
				int monthLet = Integer.parseInt(rs.getString("fullage"));
				List<ExtDispService> serviceList = extDispCard.getServices();
				Element cardBasic = new Element("basic");
				Element cardIssled = new Element("issled");
				for (ExtDispService service:serviceList) {
					String orphCode = service.getServiceType().getOrphCode();
					if (!StringUtil.isNullOrEmpty(orphCode) && service.getServiceDate()!=null) {
						Element record = new Element("record");
						record.addContent(new Element("id").addContent(orphCode))
								.addContent(new Element("date").addContent(format.format(service.getServiceDate())));
						if (service instanceof ExtDispExam ) {
									record.addContent(new Element("result").addContent(cardIsslResult));
							cardBasic.addContent(record);
						} else if (service instanceof ExtDispVisit) {
							cardOsmotri.addContent(record);
						}
					} else {
						LOG.warn("Услуга не будет выгружена. Карта №"+cardId+", услуга №"+service.getId());
					}
				}
				if (!cardBasic.getChildren().isEmpty()) {
					cardIssled.addContent(cardBasic);
				}
				if (cardOsmotri.getChildren().isEmpty()) {
					errorCards.put(createBadCardObject(cardId,patientInfo,diagnosis,"У пациента нет ни одного осмотра врачом!"));
					continue;
				}
				
				Element currPat = new Element ("child")
					.addContent(new Element("idInternal").addContent(rs.getString("pid")))
					.addContent(new Element("idType").addContent(patIdType))
					.addContent(new Element("name")
					.addContent(new Element("last").addContent(rs.getString("lastname")))
					.addContent(new Element("first").addContent(rs.getString("firstname")))
					.addContent(new Element("middle").addContent(rs.getString("middlename"))))
					.addContent(new Element("idSex").addContent(sex))
					.addContent(new Element("dateOfBirth").addContent(toDate(rs.getString("birthday"))))
					.addContent(new Element("idCategory").addContent(patIdCategory))
					.addContent(new Element("idDocument").addContent(passId))
					.addContent(new Element("documentSer").addContent(rs.getString("passSer")))
					.addContent(new Element("documentNum").addContent(rs.getString("passNum")))
					.addContent(new Element("snils").addContent(snils))
					.addContent(new Element("polisNum").addContent(commonNumber))
					.addContent(new Element("idInsuranceCompany").addContent(insuranceCompany))
					.addContent(new Element("medSanName").addContent(lpuName))
					.addContent(new Element("medSanAddress").addContent(lpuAddress));
					//.addContent(new Element("address"));
				Element address = new Element("address");
				address.addContent(new Element("regionCode").setText(astrakhanFias));
				currPat.addContent(address);
				//String fullAddress = rs.getString("fullAddress");
				//Element address =currPat.getChild("address");
				/*
				if (fullAddress!=null && !fullAddress.equals("0") && !fullAddress.equals("")) {
					String[] arrAddress = fullAddress.split(":"); //postIndex:kladrNP:kladrStr:house:building:appartment
					boolean allOk = false;
					if (arrAddress[0].trim().length()==6 && arrAddress[2].trim().length()==17 && arrAddress[1].trim().length()==13) {allOk=true;}
					if (allOk) {
						address.addContent(new Element("index").addContent(arrAddress[0].trim()));
						address.addContent(new Element("kladrNP").addContent(arrAddress[1].trim()));
						address.addContent(new Element("kladrStreet").addContent(arrAddress[2].trim())); //Код улицы по KLADR
						if (arrAddress.length>3 && !StringUtil.isNullOrEmpty(arrAddress[3])) {
							address.addContent(new Element("house").addContent(arrAddress[3].trim())); //номер дома
						}
						if (arrAddress.length>4 && !StringUtil.isNullOrEmpty(arrAddress[4])) {
							address.addContent(new Element("building").addContent(arrAddress[4].trim())); //Корпус
						}
						if (arrAddress.length>5 && StringUtil.isNullOrEmpty(arrAddress[5])) {
							address.addContent(new Element("appartment").addContent(arrAddress[5].trim())); //Квартира
						}
					} else {
						address.addContent(new Element("kladrNP").addContent("3000000100000")); //Код нас. пункта по KLADR (г. Астрахань)
					}
				} else {
					address.addContent(new Element("kladrNP").addContent("3000000100000")); //Код нас. пункта по KLADR (г. Астрахань)
				}

				*/
				Element cards = new Element("cards");
				
				Element card = new Element("card")
							.addContent(new Element("idInternal").addContent(cardId))
							.addContent(new Element("dateOfObsled").addContent(toDate(rs.getString("edcBeginDate")))) //Дата начала обследования
						//	.addContent(new Element("ageObsled").addContent("")) //Возраст ребёнка в месяцах на момент проведения обследования (необязательно)
							.addContent(new Element("idType").addContent(cardIdType))
							.addContent(new Element("height").addContent(String.valueOf(aHeight)))
							.addContent(new Element("weight").addContent(String.valueOf(aWeight)))
							.addContent(new Element("headSize").addContent(String.valueOf(aHeadSize)));
				if (monthLet<5) {
					String defaultAge = String.valueOf(monthLet*12);
					card.addContent(new Element("pshycDevelopment") //Оценка возраста психического развития для детей от 0 до 4 лет в месяцах (по умолчанию - кол-во месяцев)
						.addContent(new Element("poznav").addContent(defaultAge)) // познавательная функция
						.addContent(new Element("motor").addContent(defaultAge)) //моторная функция
						.addContent(new Element("emot").addContent(defaultAge))	//эмоциональная и социальная (контакт с окружающим миром) функции
						.addContent(new Element("rech").addContent(defaultAge)) //предречевое и речевое развитие
					);
				} else {
					card.addContent(new Element("pshycState") //Оценка состояния психического развития для детей от 5 лет (0 - в норме)
						.addContent(new Element("psihmot").addContent("0")) //Психомоторная сфера
						.addContent(new Element("intel").addContent("0")) // Интеллект
						.addContent(new Element("emotveg").addContent("0")) //Эмоционально-вегетативная сфера
					);
				}
				if (monthLet>=10) {
					if (sex.equals("1")) {
						card.addContent(new Element("sexFormulaMale")  //Половая формула (муж.) старше 10 лет
							.addContent(new Element("P").addContent("1"))
							.addContent(new Element("Ax").addContent("1"))
							.addContent(new Element("Fa").addContent("1"))
						);
					} else {
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
				card.addContent(new Element("healthGroupBefore").addContent(healthG)) //Группа здоровья до проведения обследования (числом)
				.addContent(new Element("fizkultGroupBefore").addContent(fizkultG)) // Медицинская группа для занятий физической культурой
				.addContent(new Element("diagnosisBefore")
					.addContent(new Element("diagnosis")
						.addContent(new Element("mkb").addContent(diagnosis))
						.addContent(new Element("dispNablud").addContent("3")) //Диспансерное наблюдение (1-ранее, 2 - впервые, 3 - не установлено)
						.addContent(new Element("vmp").addContent("0")) //ВМП рекоменд. и оказана (1), рек+не ок(2), не рек(0)
					));
					if (healthG.equals("1")) {
						card.addContent(new Element("healthyMKB").addContent(diagnosis)); //Код осмотра, если ребёнок здоров (Z00-Z99)
					} else {
						card.addContent(new Element("diagnosisAfter").addContent("")
							.addContent(new Element("diagnosis").addContent("")
								.addContent(new Element("mkb").addContent(diagnosis))
								.addContent(new Element("firstTime").addContent("0")) //выявлен впервые? 1,0
								.addContent(new Element("dispNablud").addContent("0")) //Диспансерное наблюдение (0 - не установлено, 1 - ранее, 2 - впервые)
								.addContent(new Element("needVMP").addContent("0")) //Рекомендована ВМП (0-1)
								.addContent(new Element("needSMP").addContent("0")) //Рекомендована СМП (0-1)
								.addContent(new Element("needSKL").addContent("0")) //Рекомендовано СКЛ (0-1)
								.addContent(new Element("recommendNext").addContent(cardReccomend)) //Рекомендации по диспансерному наблюдению, лечению,
							)
						);
					}

					card.addContent(cardIssled);
					card.addContent(new Element("healthGroup").addContent(healthG));
					card.addContent(new Element("fizkultGroup").addContent(fizkultG)); // группа занятия физкультурой
					card.addContent(new Element("zakluchDate").addContent(toDate(rs.getString("edcFinishDate"))));
					card.addContent(new Element("zakluchVrachName")
							.addContent(new Element("last").addContent(vrachL))
							.addContent(new Element("first").addContent(vrachF))
							.addContent(new Element("middle").addContent(vrachM))
					);
					card.addContent(cardOsmotri);
					cards.addContent(card);
					currPat.addContent(cards);
					card.addContent(new Element("recommendZOZH").addContent(cardRecommendZOZH));
					card.addContent(new Element("privivki")
							.addContent(new Element("state").addContent("1")) // Привит по возрасту
				);
					card.addContent(new Element("oms").addContent("0"));

				rootElement.addContent(currPat);
				if (divideNum>0 && numRight%divideNum == 0){
					xmlFilenames.add(createFile(rootElement, xmlFilename));
					rootElement = new Element("children");
				}
				extDispCard.setExportDate(currentDate);
				theManager.persist(extDispCard);
			}
			if (!rootElement.getChildren().isEmpty()) {
				xmlFilenames.add(createFile(rootElement, xmlFilename));
			}
			ret.put("status","ok");
			ret.put("errorCards",errorCards);
		} catch (Exception e) {
			ret.put("status","error");
			ret.put("errorName",e.getMessage());

			e.printStackTrace();
	        LOG.error(e);
	    }
	    LOG.info("Finished!");


	    if (!xmlFilenames.isEmpty()) {
	    	ret.put("archiveName",createArchive(xmlFilename+"_"+System.currentTimeMillis()+".zip", xmlFilenames));
		}
		return ret.toString();
	}

	/*Создание xml файла */
	private String createFile(Element aElement, String aFilename) {
		String fileName=aFilename+"_"+aElement.hashCode()+".xml";
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
		String outputFile=workDir+"/"+fileName;
		org.jdom.output.XMLOutputter outputter = new org.jdom.output.XMLOutputter();
		LOG.info(">>>>>"+outputFile);
		try (FileWriter fwrt = new FileWriter(outputFile)) {
			Document pat = new Document(aElement);
			outputter.setFormat(org.jdom.output.Format.getPrettyFormat().setEncoding("UTF-8"));
			outputter.output(pat, fwrt);
			LOG.info("created file = "+outputFile);
			return fileName;
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.error(ex.getMessage(),ex);
			return aFilename;
		}
	}
	private static String toDate(String args) {
		try {
			SimpleDateFormat nDate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat nDate2 = new SimpleDateFormat("dd.MM.yyyy");

			return nDate.format(nDate2.parse(args));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    public String setOrphCodes()  {
		HashMap <String, String> codeMap = new HashMap<>();
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
		codeMap.put("N1_034","18"); //18 - УЗИ почек
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

		for (Map.Entry<String, String> map: codeMap.entrySet()) {
			theManager.createNativeQuery("update VocExtDispService set orphCode=:orphCode where code=:code").setParameter("orphCode",map.getValue()).setParameter("code",map.getKey()).executeUpdate();
		}
		return "Коды для экспорта успешно добавлены.";
	}
}