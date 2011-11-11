package ru.ecom.mis.ejb.service.disability;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jdom.IllegalDataException;

import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.mis.ejb.domain.disability.DisabilityDocument;
import ru.ecom.mis.ejb.domain.disability.DisabilityRecord;
import ru.ecom.mis.ejb.domain.disability.RegimeViolationRecord;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityDocumentCloseReason;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityDocumentPrimarity;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityStatus;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.disability.DisabilityDocumentForm;
import ru.ecom.mis.ejb.service.patient.QueryClauseBuilder;
import ru.ecom.mis.ejb.service.worker.WorkerServiceBean;
import ru.ecom.poly.ejb.services.GroupByDate;
import ru.nuzmsh.util.StringUtil;
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
    			VocOrg org= pat.getWorks() ;
    			org.setCode(aJob) ;
    			theManager.persist(org) ;
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
			result.setCnt(WorkerServiceBean.parseLong(row[1])) ;
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
	        	Long iddoc = WorkerServiceBean.parseLong(obj) ;
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
	        	Long iddoc = WorkerServiceBean.parseLong(obj) ;
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
