package ru.ecom.mis.ejb.service.sync.lpuattachment;

import java.sql.Date;
import java.util.Iterator;

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
import ru.ecom.mis.ejb.domain.patient.LpuAttachmentFomcDetach;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.service.synclpufond.ISyncLpuFondService;

/**
 * 
 * @author VTsybulin 01.12.2014
 * Открепление пациентов от ЛПУ
 *
 */
@Stateless
@Local(ISyncAttachmentDetachService.class)
public class SyncAttachmentDetachServiceBean implements ISyncAttachmentDetachService {
	/*
	 * 1. Берем ФИО человека, находим его в базе. 
	 * 2. По ИД человека ищем, есть ли у него актуальное прикрепление (ищем самое молодое прикрепление).
	 * 3. Если прикрепление есть, обновляем его, добавляя дату открепления и ЛПУ открепления.
	 * 4. Меняет дату и пользователя.
	 * 
	 */
	private @PersistenceContext EntityManager theManager;
	private @EJB ISyncLpuFondService theSyncService ;
	private @EJB ILocalMonitorService theMonitorService;
	IMonitor monitor = null; 
	public LpuAttachedByDepartment getAttachment (long aPatientId) {
		LpuAttachedByDepartment list = (LpuAttachedByDepartment) theManager.createQuery("from LpuAttachedByDepartment where patient_id=:pat and dateTo is null order by datefrom desc ").setParameter("pat", aPatientId).getSingleResult();
		return list;
	}
	public void sync(long aMonitorId, long aTimeId) {
		Patient patient;
		Long patientId;
		LpuAttachedByDepartment attachment;
		LpuAttachmentFomcDetach dettach;
		
			Date current_date=new Date(new java.util.Date().getTime()) ;
			monitor = theMonitorService.startMonitor(aMonitorId, "Запуск импорта открепленных", getCount(aTimeId));
			Query query = theManager.createQuery("from LpuAttachmentFomcDetach lafd where time = :time").setParameter("time", aTimeId);
		
			Iterator<LpuAttachmentFomcDetach> lafd = QueryIteratorUtil.iterate(LpuAttachmentFomcDetach.class, query);
			int i =0;
			try{
			while (lafd.hasNext()) {
				i++;
				monitor.setText("Working, i="+i);
				if (monitor.isCancelled()) {
                    throw new IllegalMonitorStateException("Прервано пользователем");
                }
				dettach = lafd.next();
				monitor.setText("Working, detach="+dettach.toString());
				patientId = theSyncService.findPatientId(dettach.getLastname(), dettach.getFirstname(), dettach.getMiddlename(), dettach.getBirthday());
				patient = theManager.find(Patient.class, patientId);
				if (patientId!=null){ 
					attachment=getAttachment(patientId);
					if (attachment!=null) {
						attachment.setDateTo(dettach.getAttachDate());
						attachment.setLpuTo(dettach.getAttachLpu());
						attachment.setEditUsername("fond_base");
						attachment.setEditDate(current_date);
						theManager.persist(attachment);
						monitor.setText(i+" Запись обновлена, пациент= "+patient.getPatientInfo()+", дата открепления = "+attachment.getDateTo());
					} else {
						monitor.setText(i+" Не найдено прикреплений, изменений не произведено, пациент = "+patient.getPatientInfo());
					}
				}
			}
			monitor.finish(""+aTimeId);
		} catch (Exception e) {
			monitor.setText("Ошибка при импорте открепленных:, error="+ e);//.getMessage());
			e.printStackTrace();
			throw new IllegalMonitorStateException("Ошибка: "+e);
			//monitor.error("Ошибка при импорте открепленных: ", e);
		}
	}
	 private Long getCount(long aTimeId) {
	    	return (Long) theManager.createQuery("select count(*) from LpuAttachmentFomc where time = :time")
	    			.setParameter("time", aTimeId).getSingleResult() ;
	    }
	
}
