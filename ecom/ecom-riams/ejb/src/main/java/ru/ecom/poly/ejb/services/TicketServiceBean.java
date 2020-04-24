package ru.ecom.poly.ejb.services;

import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;
import org.jdom.IllegalDataException;
import org.json.JSONException;
import org.json.JSONWriter;
import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.entityform.PersistList;
import ru.ecom.ejb.services.file.IJbossGetFileLocalService;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;
import ru.ecom.mis.ejb.domain.medcase.ShortMedCase;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.service.patient.QueryClauseBuilder;
import ru.ecom.poly.ejb.domain.Ticket;
import ru.ecom.poly.ejb.form.TicketForm;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.StringWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Талоны
 */
@Stateless
@Remote(ITicketService.class)
@Local(ITicketService.class)
@SecurityDomain("other")
public class TicketServiceBean implements ITicketService {

    private static final Logger LOG = Logger.getLogger(MedcardServiceBean.class);

    public Long createMedcase (String aType) {
    	if (aType==null) return null;
		switch (aType) {
			case "Visit": {
				Visit v = new Visit();
				theManager.persist(v);
				return v.getId();
			}
			case "PolyclinicMedCase": {
				PolyclinicMedCase v = new PolyclinicMedCase();
				theManager.persist(v);
				return v.getId();
			}
			case "ShortMedCase": {
				ShortMedCase v = new ShortMedCase();
				theManager.persist(v);
				return v.getId();
			}
			default:
				return null;
		}
    }
  
    
    public void unionSpos(Long aOldSpo,Long aNewSpo) {
    	List<ShortMedCase> list = theManager.createQuery("from ShortMedCase where parent_id=:oldSpo").setParameter("oldSpo",aOldSpo).getResultList() ;
    	PolyclinicMedCase spoOld = theManager.find(PolyclinicMedCase.class, aOldSpo) ;
    	
    	if (!list.isEmpty() && spoOld!=null) {
    		PolyclinicMedCase spoNew = theManager.find(PolyclinicMedCase.class, aNewSpo) ;
    		for (ShortMedCase vis:list) {
				if (spoNew==null) throw new IllegalDataException("Неправильно определен СПО") ;
	    		if (spoNew.getDateStart()!=null && spoNew.getDateStart().after(vis.getDateStart())) {
	    			spoNew.setDateStart(vis.getDateStart()) ;
	    			spoNew.setStartFunction(vis.getWorkFunctionExecute()) ;
	    		}
				vis.setParent(spoNew) ;
				theManager.persist(vis) ;
			}
			theManager.persist(spoNew) ;
			//spoOld.setChildMedCase(new ArrayList<MedCase>()) ;
			theManager.remove(spoOld) ;
    	}
    }
    public void moveVisitInOtherSpo(Long aVisit,Long aNewSpo) {
    	List<Object[]> list = theManager.createNativeQuery("select max(spo.id) as spo,count(allv.id) as allvid" +
				" from medcase v left join medcase spo on spo.id=v.parent_id left join medcase allv on allv.parent_id=spo.id" +
				" where v.id=:visitId and (allv.dtype='Visit' or allv.dtype='ShortMedCase')").setParameter("visitId",aVisit).setMaxResults(1).getResultList() ;
		ShortMedCase vis = theManager.find(ShortMedCase.class, aVisit) ;
    	
    	if (!list.isEmpty() && vis!=null) {
    		Object[] spoA = list.get(0) ;
    		Long spo = ConvertSql.parseLong(spoA[0]) ;
    		Long cnt = ConvertSql.parseLong(spoA[1]) ;
    		PolyclinicMedCase spoNew ;
    		if (aNewSpo!=null&&!aNewSpo.equals(0L)) {
    			spoNew = theManager.find(PolyclinicMedCase.class, aNewSpo) ;
    			if (spoNew==null) throw new IllegalDataException("Неправильно определен СПО") ;
    		} else {
    			spoNew = new PolyclinicMedCase() ;
    			spoNew.setPatient(vis.getPatient()) ;
    			spoNew.setDateStart(vis.getDateStart()) ;
    			spoNew.setStartFunction(vis.getWorkFunctionExecute()) ;
    			spoNew.setOwnerFunction(vis.getWorkFunctionExecute()) ;
    		}
    		if (spoNew.getDateStart().after(vis.getDateStart())) {
    			spoNew.setDateStart(vis.getDateStart()) ;
    		}
    		theManager.persist(spoNew) ;
    		vis.setParent(spoNew) ;
    		theManager.persist(vis) ;
    		LOG.info("спо="+spo+" кол-во визитов="+cnt) ;
    		if (spo!=null && vis.getParent().getId()!=spo  && (cnt==null ||cnt.intValue()<2)) {
    			LOG.info("удаление спо="+spo) ;
    			
    			PolyclinicMedCase spoOld = theManager.find(PolyclinicMedCase.class, spo) ;
    			//spoOld.setChildMedCase(new ArrayList<MedCase>()) ;
    			theManager.remove(spoOld) ;
    		}
    	}
    }
    
    public String getMedServiceBySpec(Long aSpec, String aDate) throws ParseException  {
    	StringBuilder sqlMain = new StringBuilder() ;
    	Date date =  DateFormat.parseSqlDate(aDate) ;
    	SimpleDateFormat FORMAT_1 = new SimpleDateFormat("yyyy-MM-dd");
    	String dat = FORMAT_1.format(date) ;
    	sqlMain.append("select ms.id, ms.code||' '||ms.name ")
    		.append(" from MedService ms")
    		.append(" left join WorkFunctionService wfs on wfs.medService_id=ms.id left join WorkFunction wf on wf.workFunction_id=wfs.vocWorkFunction_id ")
    		.append(" where wf.id='").append(aSpec)
    		.append("' and ((ms.startDate is null or to_date('").append(dat)
    		.append("','yyyy-mm-dd') >=ms.startDate) and (ms.finishDate is null or ms.finishDate>=to_date('")
    		.append(dat).append("','yyyy-mm-dd'))) and ms.vocMedService_id is not null and ms.isPoliclinic='1'") ;
    	LOG.info(sqlMain) ;
    	List<Object[]> list = theManager.createNativeQuery(sqlMain.toString())
    		.getResultList() ;
    	if (!list.isEmpty()) {
    		try {
				StringWriter out = new StringWriter();
				JSONWriter j = new JSONWriter(out);
				j.object();
				j.key("childs").array();
				Object[] child = list.get(0) ;
	    					//for (Object child[] : list) {
	    						j.object();
	    						j.key("value").value(PersistList.parseLong(child[0]));
	    						j.key("name").value(child[1]);
	    						j.endObject();
	    					//}
				j.endArray();
		
				j.endObject();
				LOG.info(out.toString()) ;
				return out.toString() ;
			} catch (JSONException e) {
				LOG.error(e.getMessage(),e);
			}
    	}
    	return "" ;
    }
    
    /**
     * Поиск 
     */
    public String findDoubleBySpecAndDate(Long aId, Long aMedcard, Long aSpecialist, String aDate) {
    	Date date = null;
    	if(!StringUtil.isNullOrEmpty(aDate)) {
            try {
                date = DateFormat.parseSqlDate(aDate);
            } catch (Exception e) {
                LOG.warn("Ошибка преобразования даты "+aDate, e);
            }
        }
    	LOG.info("id="+aId) ;
    	LOG.info("medcard="+aMedcard) ;
    	LOG.info("spec="+aSpecialist) ;
    	LOG.info("date="+aDate) ;
    	
        StringBuilder sql = new StringBuilder() ;
        sql.append("select t.id,p.lastname||' ' || p.firstname||' '||p.middlename|| ' '||to_char(p.birthday,'dd.mm.yyyy'),to_char(coalesce(t.dateStart,t.dateFinish),'dd.mm.yyyy'),t.createTime,vwf.name|| ' ' || wp.lastname|| ' ' || wp.firstname|| ' ' || wp.middlename")
        	.append(" from MedCase as t ")
        	.append(" left join medcard as m on m.id=t.medcard_id")
			.append(" left join patient as p on m.person_id=p.id")
			.append(" left join workfunction as wf on wf.id=t.workfunctionExecute_id")
			.append(" left join VocWorkFunction as vwf on vwf.id=wf.workFunction_id")
			.append(" left join worker as w on wf.worker_id=w.id")
			.append(" left join patient as wp on wp.id = w.person_id")
			.append(" where t.dtype='ShortMedCase' and t.medcard_id=:medcard and t.workFunctionExecute_id=:workFunction and coalesce(t.dateStart,t.dateFinish)=:date and (t.istalk is null or t.istalk='0')") ;
        if (aId!=null) sql.append(" and t.id!=").append(aId);

        List<Object[]> doubles = theManager.createNativeQuery(sql.toString())
        	.setParameter("medcard", aMedcard)
        	.setParameter("workFunction", aSpecialist)
        	.setParameter("date", date)
        	.getResultList();
        if (!doubles.isEmpty()) {
        	StringBuilder ret = new StringBuilder() ;
			ret.append("<br/><ol>") ;
			for (Object[] res:doubles) {
				ret.append("<li>")
				.append("<a href='entityParentView-poly_ticket.do?id=").append(res[0]).append("'>пациент: ")
				.append(res[1])
				.append(" дата приема ").append(res[2]).append(" ").append(res[3]).append(" ")
				.append(" специал. ").append(res[4])
				.append("</a>")
				.append("</li>") ;
			}
			ret.append("</ol><br/>") ;
			return ret.toString() ;
        } 
        return null ;
        
        
    	
    }
    /** Поиск открытых талонов по мед. карте */
    public List<TicketForm> findActiveMedcardTickets(Long aMedcard) {
        QueryClauseBuilder builder = new QueryClauseBuilder();
        builder.add("medcard_id", aMedcard);
        Query query = builder.build(theManager, "from Ticket where status="+Ticket.STATUS_OPEN, " order by date, time");
        return createList(query);
    }

    public List<TicketForm> findTicketBySpecialistByDate(String aTypePat, String aDate, String aSpecialist) {
    	Date date = null;
    	if(!StringUtil.isNullOrEmpty(aDate)) {
    		try {
    			date = DateFormat.parseSqlDate(aDate);
    		} catch (Exception e) {
    			LOG.warn("Ошибка преобразования даты "+aDate, e);
    		}
    	}
    	if (date == null){
    		throw new IllegalDataException("Неправильная дата") ;
    	}
    	String add ;
    	if (aTypePat.equals("2")) {
    		add=" and $$isForeignPatient^ZExpCheck(m.person_id,t.date)>0" ;
    	} else if (aTypePat.equals("1")){
    		add=" and $$isForeignPatient^ZExpCheck(m.person_id,t.date)=0" ;
    	} else {
    		add= " " ;
    	}
    	Query query = theManager.createNativeQuery("select t.id from Ticket t  left join medcard m on m.id=t.medcard_id " +
				"where t.date=:tdate  and t.workFunction_id=:workFunction"+add);
    	query.setParameter("tdate", date).setParameter("workFunction",aSpecialist) ;
    	LOG.info(query.toString()) ;
    	return createNativeList(query);    	
    }
    
    public List<TicketForm> findStatTicketByDateAndUsername(String aDateInfo, String aDate,String aUsername) {
        QueryClauseBuilder builder = new QueryClauseBuilder();
        Date date = null;
        if(!StringUtil.isNullOrEmpty(aDate)) {
            try {
                date = DateFormat.parseSqlDate(aDate);
            } catch (Exception e) {
                LOG.warn("Ошибка преобразования даты "+aDate, e);
            }
        }
        if (date != null){
        	builder.add(aDateInfo, date);
        } else {
        	throw new IllegalDataException("Неправильная дата") ;
        }
        if (aUsername.equals("")) {
        	aUsername = " is null" ;
        } else {
        	aUsername = "='"+aUsername+"'" ;
        }
        Query query = builder.build(theManager, "from Ticket where "
        		+"usernameCreate"+aUsername, " order by date,time, status");
        LOG.info(query.toString()) ;
        return createList(query);
    }
    
	public List<GroupByDate> findOpenTicketGroupByDate() {
		String sql ="select t.date,count(t.id) from Ticket as t where t.status<> "+Ticket.STATUS_CLOSED+" group by t.date" ;
		List<Object[]> list = theManager.createNativeQuery(sql).getResultList() ;
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

	public List<TicketForm> findOpenTicketByDate(String aDate) {
	       QueryClauseBuilder builder = new QueryClauseBuilder();
	        Date date = null;
	        if(!StringUtil.isNullOrEmpty(aDate)) {
	            try {
	                date = DateFormat.parseSqlDate(aDate);
	            } catch (Exception e) {
	                LOG.warn("Ошибка преобразования даты "+aDate, e);
	            }
	        }
	        if (date != null){
	        	builder.add("date", date);
	        } else {
	        	throw new IllegalDataException("Неправильная дата") ;
	        }
	        Query query = builder.build(theManager, "from Ticket where status<>"+Ticket.STATUS_CLOSED, " order by date,time, status");
	        LOG.info("Запрос по ticket: ");
	        LOG.info(query.toString()) ;
	        return createList(query);
	}
	
	public List<TicketForm> findTicketsByNumber(String aNumber) {
        QueryClauseBuilder builder = new QueryClauseBuilder();
        if (aNumber==null || aNumber.equals("")) aNumber="0" ;
	    Query query = builder.build(theManager, "from Ticket where id ='"+aNumber+"'"," order by date, time");
        return createList(query);
	}

    /**
     * Все талоны по мед. карте
     */
    public List<TicketForm> findAllMedcardTickets(Long aMedcard) {
        QueryClauseBuilder builder = new QueryClauseBuilder();
        builder.add("medcard_id", aMedcard);
        Query query = builder.build(theManager, "from Ticket where", " order by date");
        return createList(query);
    }

    /**
     * Талоны по специалисту
     */
    public List<TicketForm> findAllSpecialistTickets(Long aSpecialist) {
        QueryClauseBuilder builder = new QueryClauseBuilder();
        builder.add("workFunction_id", aSpecialist);
        Query query = builder.build(theManager, "from Ticket where", " order by date, status");
        return createList(query);
    }

    /**
     * Талоны по специалисту за определенную дату
     */
    public List<TicketForm> findAllWorkerTickets(Long aSpecialist, String aDate, int aStatus) {
    	QueryClauseBuilder builder = new QueryClauseBuilder();
		Object obj = theManager.createNativeQuery("select list(wf1.id) from WorkFunction wf left join Worker w on w.id=wf.worker_id left join WorkFunction wf1 on wf1.worker_id=wf.worker_id where wf.id=" + aSpecialist).getSingleResult() ;

    	Date date = null;
    	if(!StringUtil.isNullOrEmpty(aDate)) {
    		try {
    			date = DateFormat.parseSqlDate(aDate);
    		} catch (Exception e) {
    			LOG.warn("Ошибка преобразования даты "+aDate, e);
    		}
    	}
    	if (date != null) builder.add("date", date);
    	String stat;
    	if (aStatus>0) {
    		stat = " and status=2" ;
    	} else {
    		stat = " and (status is null or status<2)" ;
    	}
    	Query query = builder.build(theManager, "from Ticket where workFunction_id in (" + obj + ") " + stat, " order by date,time, status");
    	return createList(query);
    }
    /**
     * Талоны по специалисту за определенную дату
     */
    public List<TicketForm> findAllSpecialistTickets(Long aSpecialist, String aDate,int aStatus) {
        QueryClauseBuilder builder = new QueryClauseBuilder();

        // IKO 070424 ***
        builder.add("workFunction_id", aSpecialist);
        // ==============

    	String stat;
    	if (aStatus>0) {
    		stat = " status=2" ;
    	} else {
    		stat = " (status is null or status<2)" ;
    	}
        Date date = null;
        if(!StringUtil.isNullOrEmpty(aDate)) {
            try {
                date = DateFormat.parseSqlDate(aDate);
            } catch (Exception e) {
                LOG.warn("Ошибка преобразования даты "+aDate, e);
            }
        }
        if (date != null) builder.add("date", date);
        Query query = builder.build(theManager, "from Ticket where "+stat, " order by date,time, status");
        LOG.info("Запрос по ticket: ");
        LOG.info(query.toString()) ;
        return createList(query);
    }

    /**
     * Закрыть талон
     */
    public void closeTicket(Long aTicketId) {
        Ticket ticket = theManager.find(Ticket.class, aTicketId);
        if(ticket.getIdc10()==null) throw new IllegalStateException("Необходимо сначала заполнить талон") ; 
        if(ticket.getStatus()==Ticket.STATUS_CLOSED) throw new IllegalStateException("Талон уже закрыт") ; 
        ticket.setStatus(Ticket.STATUS_CLOSED);
    }

    private List<TicketForm> createList(Query aQuery) {
    	List<Ticket> list = aQuery.getResultList();
    	List<TicketForm> ret = new LinkedList<>();
    	for (Ticket ticket : list) {
    		try {
    			ret.add(theEntityFormService.loadForm(TicketForm.class, ticket));
    		} catch (EntityFormException e) {
    			throw new IllegalStateException(e);
    		}
    	}
    	return ret;
    }
    
    
     private List<TicketForm> createNativeList(Query aQuery) {
        List<Object> listId = aQuery.getResultList() ;
        List<TicketForm> ret = new LinkedList<>();
        if (!listId.isEmpty()) {
        	StringBuilder ids = new StringBuilder() ;
			for (Object obj:listId) {
	        	//Long iddoc = ConvertSql.parseLong(obj) ;
	        	ids.append(",").append(obj) ;
	        }
			List<Ticket> list =theManager.createQuery("from Ticket where id in (" + ids.substring(1) + ")").getResultList() ;
			for (Ticket ticket : list) {
				try {
					ret.add(theEntityFormService.loadForm(TicketForm.class, ticket));
				} catch (EntityFormException e) {
					throw new IllegalStateException(e);
				}
			}
        }
        return ret ;
    }


    /**
     * Печать талонов (ничего на самом деле не печатает)
     */
    @Deprecated
    public void printTicket(long aMonitorId, long aTicketId, long aFileId) {

        IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Подготовка к экспорту талона");
       // Ticket ticket = theManager.find(Ticket.class, aTicketId);

        theJbossGetFileLocalService.createFile(aFileId, "ticket.rtf");

        try {
            monitor = theMonitorService.startMonitor(aMonitorId, "Экспорт стат. талона", 1);

         //   File template = JBossConfigUtil.getDataFile("ticket.rtf");
         //   TicketValueInit tvi = new TicketValueInit(ticket);
          //  RtfPrintServiceHelper service = new RtfPrintServiceHelper();
            //service.print(template, file,  tvi) ;

            monitor.finish(aMonitorId + "");
        } catch (Exception e) {
            monitor.error("Ошибка экспорта", e);
            throw new IllegalArgumentException(e);
        }

    }

    private @EJB ILocalEntityFormService theEntityFormService;
    private @EJB IJbossGetFileLocalService theJbossGetFileLocalService;
    private @EJB ILocalMonitorService theMonitorService;
    private @PersistenceContext EntityManager theManager;

}
