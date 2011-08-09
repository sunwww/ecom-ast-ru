package ru.ecom.mis.web.dwr.medcase;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.mis.ejb.service.medcase.IPolyclinicMedCaseService;
import ru.ecom.mis.ejb.service.worker.IWorkCalendarService;
import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.mis.ejb.service.worker.TableTimeBySpecialists;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;

public class WorkCalendarServiceJs {
	public String generateBySpecialist(Long aWorkFunction, HttpServletRequest aRequest) throws NamingException {
		IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
		java.util.Date date = new java.util.Date() ;
		Calendar cal = Calendar.getInstance() ;
		cal.set(Calendar.DAY_OF_MONTH, 50) ;
		service.generateCalendarByWorkFunction(aWorkFunction, new Date(date.getTime()), new Date(cal.getTime().getTime())) ;
		return "Сгенерировано";
		
	}
	
	
	public String getInfoDay(String aDate, HttpServletRequest aRequest) throws NamingException {
		IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class) ;
		return aDate ;
		
	}
	
	public Long getSecUser(HttpServletRequest aRequest) throws NamingException {
		IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class) ;
		return service.getSecUser() ;
	}
	
	public Long getWorkFunction(HttpServletRequest aRequest) throws NamingException {
		IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class) ;
		return service.getWorkFunction() ;
	}
	public Long getWorkFunctionBySecUser(Long aSecUser, HttpServletRequest aRequest) throws NamingException {
		IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class) ;
		return service.getWorkFunction(aSecUser) ;
	}
	public Long getWorkCalendar(Long aWorkFunction, HttpServletRequest aRequest) throws NamingException {
		IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class) ;
		return service.getWorkCalendar(aWorkFunction);
		
	}
	public String getWorkCalendarDay(Long aWorkCalendar,Long aWorkFucntion,String aCalendarDate, HttpServletRequest aRequest) throws NamingException, ParseException {
		IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class) ;
		return service.getWorkCalendarDay(aWorkCalendar,aWorkFucntion, aCalendarDate) ;
		
	}
	public Long getCurrentCalendarDay(HttpServletRequest aRequest) {
		Long curCalDay = (Long)aRequest.getSession().getAttribute("smo_visit.currentCalendarDay") ;
		return curCalDay ;
	}
	
	public String getTableHeaderByDayAndFunction(String aDateStart, String aDateFinish,Long aWidthSpec, Long aWidthDate, HttpServletRequest aRequest) throws Exception {
		Date dateStart = DateFormat.parseSqlDate(aDateStart) ;
		Date dateFinish = DateFormat.parseSqlDate(aDateFinish) ;
		StringBuilder tr  = new StringBuilder() ;
		SimpleDateFormat FORMAT_1 = new SimpleDateFormat("dd.MM.yyyy");
		Calendar calnext = Calendar.getInstance() ;
		calnext.setTime(dateStart) ;
		Calendar calstop = Calendar.getInstance() ;
		calstop.setTime(dateFinish) ;
		calstop.add(Calendar.DATE, 1) ;
		StringBuilder div = new StringBuilder() ;
		tr.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr class=\"x-grid-hd-row x-grid-header\">");
		tr.append("<td class=\"x-grid-hd grid-td-id\"><div class='x-grid-hd-spec'>") ;
		tr.append("Специалист") ;
		tr.append("</div></td>") ;
		 Long width = aWidthSpec+3  ;
		div.append("<div class='x-grid-split x-grid-split-spec' unselectable='on' style='left:")
			.append(width)
			.append("px'> </div>");
		calnext.setTime(dateStart) ;
		int i = 0;
		while (
					calnext.getTime().getTime()<calstop.getTime().getTime()
					) {
			i++ ;
			width = width+aWidthDate + 6;
			tr.append("<td class=\"x-grid-hd \"><div class='x-grid-hd-date'>");
			tr.append(FORMAT_1.format(calnext.getTime())) ;
			tr.append("</div></td>");
			div.append("<div class='x-grid-split x-grid-split-date' unselectable='on' style='left:")
			.append(width)
			.append("px'> </div>");
			calnext.add(Calendar.DATE, 1) ;
		}
		tr.append("</tr></table>") ;
		tr.append(div) ;
		return tr.toString();		
	}
	@SuppressWarnings("deprecation")
	public String getTableBodyByDayAndFunction(String aDateStart, String aDateFinish, Long aVocWorkFunctionId,String aMethodName, HttpServletRequest aRequest) throws Exception {
		IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class) ;
		Date dateStart = DateFormat.parseSqlDate(aDateStart) ;
		Date dateFinish = DateFormat.parseSqlDate(aDateFinish) ;
		//System.out.println("Загрузка данных... VWF = "+aVocWorkFunctionId + " период="+aDateStart+"-"+aDateFinish) ;
		List<TableTimeBySpecialists> listSpec  = service.getTableByDayAndFunction(dateStart, dateFinish, aVocWorkFunctionId);
		//System.out.println("Обработка данных... построение таблицы") ;
		Calendar calnext = Calendar.getInstance() ;
		calnext.setTime(dateStart) ;
		Calendar calstop = Calendar.getInstance() ;
		calstop.setTime(dateFinish) ;
		calstop.add(Calendar.DATE, 1) ;
		Calendar calcurrent = Calendar.getInstance() ;
		//Date datestop = dateFinish.setDate(dateFinish.getDay()+1) ;
		Long idspec = null ;
		String spec = "" ;
		StringBuilder tr  = new StringBuilder() ;
		SimpleDateFormat FORMAT_1 = new SimpleDateFormat("dd.MM.yyyy");
		//SimpleDateFormat FORMAT_2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat FORMAT_2 = new SimpleDateFormat("dd.MM.yyyy");
		calnext.setTime(dateStart) ;
		int j =0;

		for (int i = 0 ; i< listSpec.size(); i++) {
			TableTimeBySpecialists row = listSpec.get(i) ;
			if (idspec==null) {
				spec = service.getWorkFunctionInfoById(row.getSpecialistId()) ;
				calnext.setTime(dateStart) ;
				tr.append("<tr") ;
				tr.append(" class=\"x-grid-row x-grid-row-alt\">") ;
				tr.append("<td class=\"x-grid-col \"><div class='x-grid-hd-spec'>") ;
				tr.append(spec) ;
				tr.append("</div></td>") ;
				j=1;
			}
			if (idspec!=null &&!row.getSpecialistId().equals(idspec)) {
				spec = service.getWorkFunctionInfoById(row.getSpecialistId()) ;
				// новая строка
				while (
						calnext.getTime().getTime()<calstop.getTime().getTime()
							)  {
					 tr.append("<td >")  
					 	.append("<div class='x-grid-col x-grid-cell-inner x-grid-hd-date'>")
					 	.append("-")
					 	.append("</div>")
					 	.append("</td>");
				
//tr.append("<td class=\"x-grid-hd-date\"><div class='x-grid-col x-grid-cell-inner'>-</div></td>");
					calnext.add(Calendar.DATE, 1) ;
				}
				
					if (j==1) {
						j=2 ;
					} else{
						j=1 ;
					}
			
			
				calnext.setTime(dateStart) ;
				tr.append("</tr><tr");
				if (j==1)  {
					tr.append(" class=\"x-grid-row x-grid-row-alt\"") ;
				} else {
					tr.append(" class=\"x-grid-row\"");
				}
				tr.append(">") ;
				tr.append("<td class=\"x-grid-col \"><div class='x-grid-hd-spec'>") ;
				//tr.append(row.getSpecialist()) ;
				tr.append(spec) ;
				tr.append("</div></td>") ;
				
			} else {
				// продолжается текущая строка
			}
			calcurrent.setTime(row.getDate()) ;
			
			if (calcurrent.getTime().getTime()==calnext.getTime().getTime()) {	
				 tr.append("<td >")  
				 .append("<div class='x-grid-col x-grid-cell-inner x-grid-hd-date'>")
				    .append("<a href='javascript:")
				 	.append(aMethodName)
				 	.append("(\"").append(row.getSpecialistId())	.append("\"")
				 	.append(",\"").append(row.getCalendarDayId()).append("\"")
				 	.append(",\"").append(row.getTimeMin()).append("\"")
				 	//.append(",\"").append(row.getSpecialist()).append("\"")
				 	.append(",\"").append(spec).append("\"")
				 	.append(",\"").append(FORMAT_2.format(row.getDate().getTime())).append("\"")
				 	.append(",\"").append(1).append("\"")
				 	.append(")")
				 	.append("'>") 
				 	.append(row.getTimeMin())
				 	.append("</a>-")
				 	.append("<a href='javascript:")
				 	.append(aMethodName)
				 	.append("(\"").append(row.getSpecialistId())	.append("\"")
				 	.append(",\"").append(row.getCalendarDayId()).append("\"")
				 	.append(",\"").append(row.getTimeMax()).append("\"")
				 	//.append(",\"").append(row.getSpecialist()).append("\"")
				 	.append(",\"").append(spec).append("\"")
				 	.append(",\"").append(FORMAT_2.format(row.getDate().getTime())).append("\"")
				 	.append(",\"").append(0).append("\"")
				 	.append(")")
				 	.append("'>") 
				 	.append(row.getTimeMax())
				 	.append("</a>")
				 	.append("</div>")
				 	.append("</td>") ;
			} else {
				while (
						calcurrent.getTime().getTime()!=calnext.getTime().getTime() 
							&& calcurrent.getTime().getTime()<calstop.getTime().getTime()
							) {
					 tr.append("<td >")  
					 	.append("<div class='x-grid-col x-grid-cell-inner x-grid-hd-date'>")
					 	.append("-")
					 	.append("</div>")
					 	.append("</td>");
//tr.append("<td class=\"x-grid-hd-date\"><div class='x-grid-col x-grid-cell-inner'>-</div></td>");
					calnext.add(Calendar.DATE, 1) ;
				}
				if (calcurrent.getTime().getTime()==calnext.getTime().getTime()) {	
					 tr.append("<td><div class='x-grid-col x-grid-col-id x-grid-cell-inner x-grid-hd-date'>")  
				 	.append("<a href='javascript:")
				 	.append(aMethodName)
				 	.append("(\"").append(row.getSpecialistId())	.append("\"")
				 	.append(",\"").append(row.getCalendarDayId()).append("\"")
				 	.append(",\"").append(row.getTimeMin()).append("\"")
				 	//.append(",\"").append(row.getSpecialist()).append("\"")
				 	.append(",\"").append(spec).append("\"")
				 	.append(",\"").append(FORMAT_2.format(row.getDate().getTime())).append("\"")
				 	.append(",\"").append(1).append("\"")
				 	.append(")")
				 	.append("'>") 
				 	.append(row.getTimeMin())
				 	.append("</a>- ")
				 	.append("<a href='javascript:")
				 	.append(aMethodName)
				 	.append("(\"").append(row.getSpecialistId())	.append("\"")
				 	.append(",\"").append(row.getCalendarDayId()).append("\"")
				 	.append(",\"").append(row.getTimeMax()).append("\"")
				 	//.append(",\"").append(row.getSpecialist()).append("\"")
				 	.append(",\"").append(spec).append("\"")
				 	.append(",\"").append(FORMAT_2.format(row.getDate().getTime())).append("\"")
				 	.append(",\"").append(0).append("\"")
				 	.append(")")
				 	.append("'>") 
				 	.append(row.getTimeMax())
				 	.append("</a>")
				 	.append("</div></td>") ;
				}				
			}
			
			calnext.add(Calendar.DATE, 1);
			idspec = row.getSpecialistId() ;
		}
		if (listSpec.size()>0) {
			
				while (
				calnext.getTime().getTime()<calstop.getTime().getTime()
					)  {
			 tr.append("<td >")  
			 	.append("<div class='x-grid-col x-grid-cell-inner x-grid-hd-date'>")
			 	.append("-")
			 	.append("</div>")
			 	.append("</td>");
		
//tr.append("<td class=\"x-grid-hd-date\"><div class='x-grid-col x-grid-cell-inner'>-</div></td>");
			calnext.add(Calendar.DATE, 1) ;
				}
		} else {
			tr.append("<td>Нет данных</td>") ;
		}
		return tr.toString();
	}
	public String getCalendarTimeId(Long aCalendarDay, String aCalendarTime, Long aMinIs, HttpServletRequest aRequest) throws Exception {
		IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class) ;
		Time time = DateFormat.parseSqlTime(aCalendarTime) ;
		String ret = service.getCalendarTimeId(aCalendarDay, time, aMinIs) ;
		if (ret==null) {
			throw new IllegalArgumentException("На это число нет свободного времени") ;
		}
		return ret ;
	}
	public String getDefaultDate(Long aFuncId, HttpServletRequest aRequest) throws Exception {
		IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class) ;
		return service.getDayBySpec(aFuncId);
		
	}
}
