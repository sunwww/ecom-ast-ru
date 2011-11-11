package ru.ecom.mis.ejb.service.disability;

import java.util.List;
import ru.ecom.mis.ejb.form.disability.DisabilityDocumentForm;
import ru.ecom.poly.ejb.services.GroupByDate;

public interface IDisabilityService {
	public String closeDisabilityDocument(Long aDocumentId, Long aReasonId,String aSeries,String aNumber) ;
	public List<DisabilityDocumentForm> findDocumentBySeriesAndNumber(String aFind) ;
	public List<DisabilityDocumentForm> findOpenTicketByDate(String aDate) ;
	public List<DisabilityDocumentForm> findCloseTicketByDate(String aDate,String aType) ;
	public List<GroupByDate> findOpenDocumentGroupByDate() ;
	public List<GroupByDate> findCloseDocumentGroupByDate(String aDateFrom, String aDateTo) ;
	public String getDataByClose(Long aDocumentId) ;
	public Long createDuplicateDocument(Long aDocId,Long aReasonId, String aSeries, String aNumber,Long aWorkFuntion2,String aJob,Boolean aUpdateJob) ;
	public Long createWorkComboDocument(Long aDocId,String aJob, String aSeries, String aNumber) ;
	
}
