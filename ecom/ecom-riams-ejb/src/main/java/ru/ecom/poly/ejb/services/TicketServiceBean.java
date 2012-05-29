package ru.ecom.poly.ejb.services;

import java.io.File;
import java.io.StringWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
import ru.ecom.ejb.services.util.JBossConfigUtil;
import ru.ecom.mis.ejb.service.patient.QueryClauseBuilder;
import ru.ecom.poly.ejb.domain.Ticket;
import ru.ecom.poly.ejb.form.TicketForm;
import ru.ecom.report.rtf.RtfPrintServiceHelper;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

/**
 * Талоны
 */
@Stateless
@Remote(ITicketService.class)
@Local(ITicketService.class)
@SecurityDomain("other")
public class TicketServiceBean implements ITicketService {

    private final static Logger LOG = Logger.getLogger(MedcardServiceBean.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    
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
    		.append(dat).append("','yyyy-mm-dd'))) and ms.vocMedService_id is not null") ;
    	System.out.println(sqlMain) ;
    	List<Object[]> list = theManager.createNativeQuery(sqlMain.toString())
//    		.setParameter("date", )
    		.getResultList() ;
    	if (list.size()>0) {
    		try {
				StringWriter out = new StringWriter();
				JSONWriter j = new JSONWriter(out);
				j.object();
				j.key("childs").array();
				Object child[] = list.get(0) ;
	    					//for (Object child[] : list) {
	    						j.object();
	    						j.key("value").value(PersistList.parseLong(child[0]));
	    						j.key("name").value((String)child[1]);
	    						j.endObject();
	    					//}
				j.endArray();
		
				j.endObject();
				System.out.println(out.toString()) ;
				return out.toString() ;
			} catch (JSONException e) {
				e.printStackTrace();
			}
    	}
    	return "" ;
    }
    
    public String findProvReason() {
    	List<Object[]> list = theManager.createNativeQuery("select id,name from VocReason where code='PROFYLACTIC' order by id").setMaxResults(1).getResultList() ;
    	StringBuilder ret = new StringBuilder() ;
    	if (list.size()>0) {
    		Object[] obj = list.get(0) ;
    		ret.append(obj[0]).append("#").append(obj[1]) ;
    	}
    	return ret.toString() ;
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
    	System.out.println("id="+aId) ;
    	System.out.println("medcard="+aMedcard) ;
    	System.out.println("spec="+aSpecialist) ;
    	System.out.println("date="+aDate) ;
    	
        StringBuilder sql = new StringBuilder() ;
        sql.append("select t.id,p.lastname||' ' || p.firstname||' '||p.middlename|| ' '||to_char(p.birthday,'dd.mm.yyyy'),to_char(t.date,'dd.mm.yyyy'),t.recordTime,vwf.name|| ' ' || wp.lastname|| ' ' || wp.firstname|| ' ' || wp.middlename")
        	.append(" from Ticket as t ")
        	.append(" left join medcard as m on m.id=t.medcard_id")
			.append(" left join patient as p on m.person_id=p.id")
			.append(" left join workfunction as wf on wf.id=t.workfunction_id")
			.append(" left join VocWorkFunction as vwf on vwf.id=wf.workFunction_id")
			.append(" left join worker as w on wf.worker_id=w.id")
			.append(" left join patient as wp on wp.id = w.person_id")
			.append(" where t.medcard_id=:medcard and t.workFunction_id=:workFunction and t.date=:date and (t.talk is null or cast(t.talk as integer)=0)") ;
        String add ="" ;
        if (aId!=null) sql.append(" and t.id!=").append(aId);

        List<Object[]> doubles = theManager.createNativeQuery(sql.toString())
        	.setParameter("medcard", aMedcard)
        	.setParameter("workFunction", aSpecialist)
        	.setParameter("date", date)
        	.getResultList();
        if (doubles.size()>0) {
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
        if (CAN_DEBUG) {
            LOG.debug("findActiveMedcardTickets() aMedcard = " + aMedcard);
        }
        QueryClauseBuilder builder = new QueryClauseBuilder();
        builder.add("medcard_id", aMedcard);

        Query query = builder.build(theManager, "from Ticket where status="+Ticket.STATUS_OPEN, " order by date, time");

        return createList(query);
    }
    /*
    public List<TicketForm> findTicketByNonresident(String aTypePat, String aDate,String aDateTo) {
        QueryClauseBuilder builder = new QueryClauseBuilder();
        Date date = null;
        Date dateTo = null;
        if(!StringUtil.isNullOrEmpty(aDate)) {
            try {
                date = DateFormat.parseSqlDate(aDate);
            } catch (Exception e) {
                LOG.warn("Ошибка преобразования даты "+aDate, e);
            }
        }
        if(!StringUtil.isNullOrEmpty(aDateTo)) {
            try {
                dateTo = DateFormat.parseSqlDate(aDateTo);
            } catch (Exception e) {
                LOG.warn("Ошибка преобразования даты "+aDate, e);
            }
        }
        if (date != null){
        	builder.addBetween("t.date", date,dateTo);
        } else {
        	throw new IllegalDataException("Неправильная дата") ;
        }
        String add ;
		if (aTypePat.equals("2")) {
			add="where $$isForeignPatient^ZExpCheck(m.person_id,t.date)>0" ;
		} else if (aTypePat.equals("1")){
			add="where $$isForeignPatient^ZExpCheck(m.person_id,t.date)=0" ;
		} else {
			add= "" ;
		}

        Query query = builder.build(theManager, "from Ticket as t left join Medcard as m on m.id=t.medcard_id "+add, " order by t.date,t.time, t.status");
       
        //System.out.println("Запрос по ticket: from Ticket where "+aDateInfo
        //		+" =:date and usernameCreate='"+aUsername+"' order by date,time, status");
        System.out.println(query.toString()) ;
        return createList(query);    	
    }*/
    public List<TicketForm> findTicketBySpecialistByDate(String aTypePat, String aDate, String aSpecialist) {
    	//QueryClauseBuilder builder = new QueryClauseBuilder();
    	Date date = null;
    	if(!StringUtil.isNullOrEmpty(aDate)) {
    		try {
    			date = DateFormat.parseSqlDate(aDate);
    		} catch (Exception e) {
    			LOG.warn("Ошибка преобразования даты "+aDate, e);
    		}
    	}
    	if (date != null){
    		//builder.add("t.date", date);
    	} else {
    		throw new IllegalDataException("Неправильная дата") ;
    	}
    	String add ;
    	if (aTypePat.equals("2")) {
    		add=" and $$isForeignPatient^ZExpCheck(m.person_id,t.date)>0" ;
    	} else if (aTypePat.equals("1")){
    		add=" and $$isForeignPatient^ZExpCheck(m.person_id,t.date)=0" ;
    	} else {
    		add= " " ;
    		//Query query = theManager.createQuery("from Ticket t  where t.date=:tdate  "+add);
    		//query.setParameter("tdate", date) ;
    		//return createList(query) ;
    	}
    	Query query = theManager.createNativeQuery("select t.id from Ticket t  left join medcard m on m.id=t.medcard_id where t.date=:tdate  and t.workFunction_id='"+aSpecialist+"' "+add);
    	query.setParameter("tdate", date) ;
    	
    	//System.out.println("Запрос по ticket: from Ticket where "+aDateInfo
    	//		+" =:date and usernameCreate='"+aUsername+"' order by date,time, status");
    	System.out.println(query.toString()) ;
    	return createNativeList(query);    	
    }
    /*
    public List<TicketForm> findTicketByNonresidentByDate(String aTypePat, String aDate) {
        //QueryClauseBuilder builder = new QueryClauseBuilder();
        Date date = null;
        if(!StringUtil.isNullOrEmpty(aDate)) {
            try {
                date = DateFormat.parseSqlDate(aDate);
            } catch (Exception e) {
                LOG.warn("Ошибка преобразования даты "+aDate, e);
            }
        }
        if (date != null){
        	//builder.add("t.date", date);
        } else {
        	throw new IllegalDataException("Неправильная дата") ;
        }
        String add ;
		if (aTypePat.equals("2")) {
			add=" and $$isForeignPatient^ZExpCheck(m.person_id,t.date)>0" ;
		} else if (aTypePat.equals("1")){
			add=" and $$isForeignPatient^ZExpCheck(m.person_id,t.date)=0" ;
		} else {
			add= " " ;
	        Query query = theManager.createQuery("from Ticket t  where t.date=:tdate  "+add);
	        query.setParameter("tdate", date) ;
	        return createList(query) ;
	    }
        Query query = theManager.createNativeQuery("select t.id from Ticket t  left join medcard m on m.id=t.medcard_id where t.date=:tdate  "+add);
        query.setParameter("tdate", date) ;

        //System.out.println("Запрос по ticket: from Ticket where "+aDateInfo
        //		+" =:date and usernameCreate='"+aUsername+"' order by date,time, status");
        System.out.println(query.toString()) ;
        return createNativeList(query);    	
    }*/
    
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
        //System.out.println("Запрос по ticket: from Ticket where "+aDateInfo
        //		+" =:date and usernameCreate='"+aUsername+"' order by date,time, status");
        System.out.println(query.toString()) ;
        return createList(query);
    }
    
	public List<GroupByDate> findOpenTicketGroupByDate() {
		StringBuilder sql = new StringBuilder();
		sql.append("select t.date,count(t.id) from Ticket as t where t.status<> ").append(Ticket.STATUS_CLOSED).append(" group by t.date") ;
		List<Object[]> list = theManager.createNativeQuery(sql.toString())
				.getResultList() ;
		LinkedList<GroupByDate> ret = new LinkedList<GroupByDate>() ;
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
	        System.out.println("Запрос по ticket: ");
	        System.out.println(query.toString()) ;
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
        if (CAN_DEBUG) {
            LOG.debug("findAllMedcardTickets() aMedcard = " + aMedcard);
        }
        QueryClauseBuilder builder = new QueryClauseBuilder();
        builder.add("medcard_id", aMedcard);
        Query query = builder.build(theManager, "from Ticket where", " order by date");

        return createList(query);
    }

    /**
     * Талоны по специалисту
     */
    public List<TicketForm> findAllSpecialistTickets(Long aSpecialist) {
        if (CAN_DEBUG) {
            LOG.debug("findAllSpecialistTickets() aSpecialist = " + aSpecialist);
        }
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
    	StringBuilder sql = new StringBuilder().append("select list(wf1.id) from WorkFunction wf left join Worker w on w.id=wf.worker_id left join WorkFunction wf1 on wf1.worker_id=wf.worker_id where wf.id=").append(aSpecialist) ;
    	Object obj = theManager.createNativeQuery(sql.toString()).getSingleResult() ;
    	// IKO 070424 ***
    	//builder.add("workFunction_id", aSpecialist);
    	// ==============
    	
    	Date date = null;
    	if(!StringUtil.isNullOrEmpty(aDate)) {
    		try {
    			date = DateFormat.parseSqlDate(aDate);
    		} catch (Exception e) {
    			LOG.warn("Ошибка преобразования даты "+aDate, e);
    		}
    	}
    	if (date != null) builder.add("date", date);
    	String stat = "" ;
    	if (aStatus>0) {
    		stat = " and status=2" ;
    	} else {
    		stat = " and (status is null or status<2)" ;
    	}
    	Query query = builder.build(theManager, new StringBuilder().append("from Ticket where workFunction_id in (").append(obj).append(") ").append(stat).toString(), " order by date,time, status");
    	System.out.println("Запрос по ticket: ");
    	System.out.println(query.toString()) ;
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

    	String stat = "" ;
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
        System.out.println("Запрос по ticket: ");
        System.out.println(query.toString()) ;
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
    	List<TicketForm> ret = new LinkedList<TicketForm>();
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
        List<Object> list_id = aQuery.getResultList() ;
        List<TicketForm> ret = new LinkedList<TicketForm>();
        if (list_id.size()>0) {
        	StringBuilder ids = new StringBuilder() ;
        	StringBuilder sql = new StringBuilder() ;
	        for (Object obj:list_id) {
	        	//Long iddoc = ConvertSql.parseLong(obj) ;
	        	ids.append(",").append(obj) ;
	        }
	        ids.substring(1) ;
	        System.out.println(ids.substring(1)) ;
	        sql.append("from Ticket where id in (").append(ids.substring(1)).append(")") ;
	        System.out.println(sql.toString()) ;
	    
	    List<Ticket> list =theManager.createQuery(sql.toString()).getResultList() ;
        
        for (Ticket ticket : list) {
            try {
                ret.add(theEntityFormService.loadForm(TicketForm.class, ticket));
            } catch (EntityFormException e) {
                throw new IllegalStateException(e);
            }
        }}
        return ret ;
    }


    /**
     * Печать талонов
     */
    public void printTicket(long aMonitorId, long aTicketId, long aFileId) {

        IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Подготовка к экспорту талона");
        Ticket ticket = theManager.find(Ticket.class, aTicketId);

        File file = theJbossGetFileLocalService.createFile(aFileId, "ticket.rtf");

        try {
            monitor = theMonitorService.startMonitor(aMonitorId, "Экспорт стат. талона", 1);

            File template = JBossConfigUtil.getDataFile("ticket.rtf");
            TicketValueInit tvi = new TicketValueInit(ticket);
            RtfPrintServiceHelper service = new RtfPrintServiceHelper();
            //service.print(template, file,  tvi) ;

            monitor.finish(aMonitorId + "");
        } catch (IllegalMonitorStateException e) {
            throw e;
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
