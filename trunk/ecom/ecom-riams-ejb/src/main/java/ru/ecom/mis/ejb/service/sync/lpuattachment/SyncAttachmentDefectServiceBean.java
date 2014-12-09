package ru.ecom.mis.ejb.service.sync.lpuattachment;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.mis.ejb.domain.patient.LpuAttachedByDepartment;
import ru.ecom.mis.ejb.domain.patient.LpuAttachmentFomc;
import ru.ecom.mis.ejb.domain.patient.LpuAttachmentFomcDefect;
import ru.ecom.mis.ejb.domain.patient.LpuAttachmentFomcDetach;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.service.synclpufond.ISyncLpuFondService;

/**
 * 
 * @author VTsybulin 01.12.2014
 * Импорт дефектов прикрепленного населения из фонда
 *
 */
@Stateless
@Local(ISyncAttachmentDefectService.class)
public class SyncAttachmentDefectServiceBean implements ISyncAttachmentDefectService {
	/*
	 * 1. Находим человека в базе
	 * Есть человек найден, ищем его прикрепление по типу и по дате.
	 * Если нашлось - добавляем в поля период дефекта, текст дефекта.
	 * 		Меняем пользователя, дату и время редактирования записи.
	 */
	private @PersistenceContext EntityManager theManager;
	private @EJB ISyncLpuFondService theSyncService ;
	private @EJB ILocalMonitorService theMonitorService;
	IMonitor monitor = null; 
	public LpuAttachedByDepartment getAttachment (long aPatientId, Date aDate, String aMethodType) {
		
		LpuAttachedByDepartment list = (LpuAttachedByDepartment) theManager.createQuery("from LpuAttachedByDepartment where patient_id=:pat and dateFrom =:dateFrom and attachedType_id=:aTypeId ")
					.setParameter("pat", aPatientId)
					.setParameter("dateFrom", aDate)
					.setParameter("aTypeId", Integer.valueOf(aMethodType))
					.getSingleResult();
					
		return list;
	}
	public void sync(long aMonitorId, long aTimeId) {
		Patient patient;
		Long patientId;
		LpuAttachedByDepartment attachment;
		LpuAttachmentFomcDefect defect;
		
			Date current_date=new Date(new java.util.Date().getTime()) ;
			monitor = theMonitorService.startMonitor(aMonitorId, "Импорт дефектов прикрепленного населения", getCount(aTimeId));
			try{
			Query query = theManager.createQuery("from LpuAttachmentFomcDefect lafd where time = :time").setParameter("time", aTimeId);
			Iterator<LpuAttachmentFomcDefect> lafd = QueryIteratorUtil.iterate(LpuAttachmentFomcDefect.class, query);
			int i =0;
			while (lafd.hasNext()) {
				i++;
				if (monitor.isCancelled()) {
                    throw new IllegalMonitorStateException("Прервано пользователем");
                }
				defect =  lafd.next();
				patientId = theSyncService.findPatientId(defect.getLastname(), defect.getFirstname(), defect.getMiddlename(), defect.getBirthday());
				if (patientId!=null){ 
					patient = theManager.find(Patient.class, patientId);
					attachment=getAttachment(patientId, defect.getAttachDate(), defect.getMethodType());
					if (attachment!=null) {
						attachment.setDefectText(defect.getRefreason());
						attachment.setDefectPeriod(""); // Изменить !!! 
						attachment.setEditUsername("fond_base");
						attachment.setEditDate(current_date);
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
			throw new IllegalMonitorStateException("Ошибка: "+e);
		}
	}
	 private Long getCount(long aTimeId) {
	    	return (Long) theManager.createQuery("select count(*) from LpuAttachmentFomc where time = :time")
	    			.setParameter("time", aTimeId).getSingleResult() ;
	    }
	
}
