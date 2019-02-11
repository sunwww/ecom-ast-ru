package ru.ecom.mis.ejb.service.sync.lpuattachment;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.mis.ejb.domain.patient.LpuAttachedByDepartment;
import ru.ecom.mis.ejb.domain.patient.LpuAttachmentFomcDefect;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.service.synclpufond.ISyncLpuFondService;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.StringReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author VTsybulin 01.12.2014
 * Импорт дефектов прикрепленного населения из фонда
 *
 */
@Stateless
@Local(ISyncAttachmentDefectService.class)
@Remote(ISyncAttachmentDefectService.class)

public class SyncAttachmentDefectServiceBean implements ISyncAttachmentDefectService {
	private static final Logger LOG = Logger.getLogger(SyncAttachmentDefectServiceBean.class);
	
	public String changeAttachmentArea (Long aOldAreaId, Long aNewLpuId, Long aNewAreaId) {
		String sql = "update LpuAttachedByDepartment set area_id="+aNewAreaId+", lpu_id="+aNewLpuId+" where area_id="+aOldAreaId;
		String sql2 = "update LpuAreaAddressText set area_id="+aNewAreaId+" where area_id="+aOldAreaId;
		try {
			theManager.createNativeQuery(sql2).executeUpdate();
			return "Изменено записей: "+theManager.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "Ошибка при перекреплении: "+e.toString();
		}
		}
	
	/*
	 * 1. Находим человека в базе
	 * Есть человек найден, ищем его прикрепление по типу и по дате.
	 * Если нашлось - добавляем в поля период дефекта, текст дефекта.
	 * 		Меняем пользователя, дату и время редактирования записи.
	 */
	private @PersistenceContext EntityManager theManager;
	private @EJB ISyncLpuFondService theSyncService ;
	private @EJB ILocalMonitorService theMonitorService;

	public String cleanDefect(long aAttachmentId) {
		try{
			LpuAttachedByDepartment att = theManager.find(LpuAttachedByDepartment.class, aAttachmentId);
			if (att!=null) {
				att.setDefectText("");
				return "Дефект очищен!";
			} else {
				return "Прикрепление не найдено";
			}			 
		} catch (Exception e) {
			e.printStackTrace();
			return "Ошибка: "+e;
		}
		
	}
	public LpuAttachedByDepartment getAttachment (long aPatientId, Date aDate, String aMethod, String aType) {
		LpuAttachedByDepartment lpu = null;
		String aDateType = "2".equals(aType)?"dateTo":"dateFrom";
		try{
			String req = "Select id from LpuAttachedByDepartment where patient_id="+aPatientId+" and "+aDateType+" =to_date('"+aDate+"','yyyy-MM-dd') and attachedType_id=(select id from vocattachedtype where code='"+aMethod+"') ";
			List<Object> list = theManager.createNativeQuery(req).getResultList();
			if (!list.isEmpty()) {
				lpu = theManager.find(LpuAttachedByDepartment.class, Long.valueOf(list.get(0).toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return lpu;
	}
	public String importDefectFromXML (String aFileName) {
		try {
			if (aFileName!=null&&aFileName!="") {
				LOG.info("in ImportDefect, start. hashcode="+aFileName);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat formatOutput = new SimpleDateFormat("dd.MM.yyyy");
				SAXBuilder saxBuilder = new SAXBuilder();
				Document xdoc = saxBuilder.build(new StringReader(aFileName));
				org.jdom.Element rootElement = xdoc.getRootElement();
				List<org.jdom.Element> elements =rootElement.getChildren("ZAP"); 
				int i=0;
				StringBuilder sb = new StringBuilder();
				for (org.jdom.Element el: elements) {
					i++;
					StringBuilder refrSB = new StringBuilder();
					List<org.jdom.Element> refresions =el.getChildren("REFREASON");	
					for (org.jdom.Element r: refresions) {
						if (r.getText().equalsIgnoreCase("включен в регистр")){
							refrSB.setLength(0); break;
						}
						if (r.getText()==null||r.getText().equals("")||r.getText().equals("3")||r.getText().equals("4")||r.getText().equals("5")||r.getText().equals("6")) {
							
						} else {
							refrSB.append(r.getText()).append(",");
						}
						
					}
							String refreason =refrSB.length()>0?refrSB.substring(0,refrSB.length()-1):"";
							String lastname = el.getChildText("FAM");
							String firstname = el.getChildText("IM");
							String middlename = el.getChildText("OT");
							String birthday = el.getChildText("DR");
							String spPrik= el.getChildText("SP_PRIK");
							String tPrik= el.getChildText("T_PRIK");
							String datePrik= el.getChildText("DATE_1");
							String prikName = "Прикрепление";
							if ("2".equals(tPrik)) { //При дефекте случая, поданного на открепление, не учитываем дефект(МАКС-М - 14 -открепление неправомерно, когда пациент уже откреплен)
								refreason="";
								prikName = "Открепление"; 
							}
							String birthday2 = formatOutput.format(format.parse(birthday))+" г.р.";
							Long patientId = theSyncService.findPatientId(lastname, firstname, middlename, new java.sql.Date(format.parse(birthday).getTime()));
							if (patientId!=null&&patientId!=0) {
								LpuAttachedByDepartment att = getAttachment(patientId, new java.sql.Date(format.parse(datePrik).getTime()), spPrik,tPrik);
								if (refreason!=null&&!refreason.equals("")) { //Дефект
					    			if (att!=null) {
										sb.append("red:"+i+":"+patientId+":"+att.getId()+":"+prikName+" пациента '"+lastname+" "+firstname+" "+middlename+" "+birthday2+"'обновлено. Дефект='"+refreason+"'#");
					    			} else {
					    				sb.append("blue:"+i+":"+patientId+"::"+prikName+" не найдено в базе. Данные пациента= '"+lastname+" "+firstname+" "+middlename+" "+birthday2+"'#");
					    			}									 
								} else { //Не дефект
									if (att!=null) {
										sb.append("green:"+i+":"+patientId+"::"+prikName+" принято без дефектов. Данные пациента= '"+lastname+" "+firstname+" "+middlename+" "+birthday2+"'#");
									}								
								}
								if (att!=null) {
			    					att.setDefectText(refreason);
			    					att.setDefectPeriod(formatOutput.format(new Date(new java.util.Date().getTime())));  
									theManager.persist(att);
								}
							} else {
								sb.append("black:"+i+":::Пациент не найден в базе. Данные пациента= '"+lastname+" "+firstname+" "+middlename+" "+birthday2+"'#");
							}
				}
				return sb.toString();
			} else {
				return "Нет такого файла";
			}
		} catch (Exception e) {
			LOG.error("------------"+aFileName);
			e.printStackTrace();
			return "ERROR";
		}
		
		
	}
	//Работает неправильно, вместо синхронизации использовать импорт дефектов!
	public void sync(long aMonitorId, long aTimeId) {

		Patient patient;
		Long patientId;
		LpuAttachedByDepartment attachment;
		LpuAttachmentFomcDefect defect;
		SimpleDateFormat formatOutput = new SimpleDateFormat("dd.MM.yyyy");
		IMonitor monitor = theMonitorService.startMonitor(aMonitorId, "Импорт дефектов прикрепленного населения", getCount(aTimeId));
		try {
			Query query = theManager.createQuery("from LpuAttachmentFomcDefect lafd where time = :time").setParameter("time", aTimeId);
			Iterator<LpuAttachmentFomcDefect> lafd = QueryIteratorUtil.iterate(LpuAttachmentFomcDefect.class, query);
			int i =0;
			while (lafd.hasNext()) {
				i++;
				if (monitor.isCancelled()) {
                    LOG.warn("Прервано пользователем");
                    return;
                }
				defect =  lafd.next();
				patientId = theSyncService.findPatientId(defect.getLastname(), defect.getFirstname(), defect.getMiddlename(), defect.getBirthday());
				if (patientId!=null){ 
					patient = theManager.find(Patient.class, patientId);
					attachment=getAttachment(patientId, defect.getAttachDate(), defect.getMethodType(), "1");
					if (attachment!=null) {
						attachment.setDefectText(defect.getRefreason());
						attachment.setDefectPeriod(formatOutput.format(new Date(System.currentTimeMillis()))); // Изменить !!!
						attachment.setEditUsername("fond_base");
						theManager.persist(attachment);
						monitor.setText(i+" Запись обновлена, пациент= "+patient.getPatientInfo()+", код дефекта = "+attachment.getDefectText());
					} else {
						monitor.setText(i+" Не найдено прикреплений, изменений не произведено, пациент = "+patient.getPatientInfo());
					}
				}
			}
			monitor.finish(""+aTimeId);
		} catch (Exception e) {
			monitor.error("Ошибка при импорте открепленных: ", e);
			monitor.finish(""+aTimeId);
			LOG.error(e);
		}
	}
	 private Long getCount(long aTimeId) {
	    	return (Long) theManager.createQuery("select count(*) from LpuAttachmentFomc where time = :time")
	    			.setParameter("time", aTimeId).getSingleResult() ;
	    }
}