package ru.ecom.mis.web.action.worker;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.jaas.web.action.JaasUtil;
import ru.ecom.mis.ejb.service.worker.IWorkCalendarService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class WorkCalendarAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
        long lpuId = Long.parseLong(aRequest.getParameter("lpuId")!=null?aRequest.getParameter("lpuId"):"0") ;
        //Long pattern = Long.parseLong(aRequest.getParameter("pattern")) ;
        String beginDateS = aRequest.getParameter("beginDate") ;
        String finishDateS = aRequest.getParameter("finishDate") ;
        String functionJournal = aRequest.getParameter("functionJournal") ;
        Date beginDate = DateFormat.parseSqlDate(beginDateS) ;
        Date finishDate = DateFormat.parseSqlDate(finishDateS) ;
        if (functionJournal!=null && !functionJournal.equals("")) {
        	if  (functionJournal.equals("autogenerate")) {
        		service.autoGenerateCalendar() ;
        		 return new ActionForward("/entityParentList-mis_lpu.do?id=-1&");
        	} else if  (functionJournal.equals("generate")) {
        		serviceGenerateDo(service, lpuId, JaasUtil.convertToLongs(aRequest.getParameterValues("id")), beginDate, finishDate) ;
        	} else if (functionJournal.equals("addBusyPattern")) {
        		Long pattern = Long.parseLong(aRequest.getParameter("pattern")) ;
        		serviceAddBusyPatternDo(service,lpuId, JaasUtil.convertToLongs(aRequest.getParameterValues("id")),beginDate,finishDate,pattern);
        	} else if (functionJournal.equals("deleteFreeTime")) {
        		serviceDeleteFreeTimeDo(service, lpuId, JaasUtil.convertToLongs(aRequest.getParameterValues("id")), beginDate, finishDate) ;
        	} else if (functionJournal.equals("addNotWorking")) {
        		Long busy = Long.parseLong(aRequest.getParameter("busy")) ;
        		serviceAddNotBusyPatternDo(service,lpuId, JaasUtil.convertToLongs(aRequest.getParameterValues("id")),beginDate,finishDate,busy);
        	} else if (functionJournal.equals("deleteNoAppearance")) {
        		serviceDeleteNoAppearanceDo(service,lpuId, JaasUtil.convertToLongs(aRequest.getParameterValues("id")),beginDate,finishDate);
        	} else if (functionJournal.equals("moveDate")) {
        		java.sql.Date dateCur = new java.sql.Date(new java.util.Date().getTime()) ;
        		if (dateCur.getTime()>beginDate.getTime()) throw new IllegalArgumentException("дата начала должна быть больше текущей") ;
        		if (dateCur.getTime()>finishDate.getTime()) throw new IllegalArgumentException("дата окончания должна быть больше текущей") ;
        		serviceMoveDateDo(service, lpuId, JaasUtil.convertToLongs(aRequest.getParameterValues("id")), beginDate, finishDate) ;
        	}
        }
        //serviceDo(service,lpuId, JaasUtil.convertToLongs(aRequest.getParameterValues("id")),beginDate,finishDate,pattern);
        return new ActionForward(aMapping.findForward("success").getPath()
        		+"?id="+lpuId+"&beginDate="+beginDateS+"&finishDate="+finishDateS+"&tmp="+Math.random(), true) ;
    }

    void serviceAddNotBusyPatternDo(IWorkCalendarService aService, long aLpuId, long[] aFunctions,Date aBeginDate,Date aFinishDate, Long aReason) {
    	for (long func:aFunctions) {
    		aService.addNotBusyPattern(func, aBeginDate, aFinishDate,aReason);
    	}
    }
    void serviceMoveDateDo(IWorkCalendarService aService, long aLpuId, long[] aFunctions,Date aBeginDate,Date aFinishDate) {
    	for (long func:aFunctions) {
    		aService.moveDate(func, aBeginDate, aFinishDate);
    	}
    }
    void serviceDeleteFreeTimeDo(IWorkCalendarService aService, long aLpuId, long[] aFunctions,Date aBeginDate,Date aFinishDate) {
    	for (long func:aFunctions) {
    		aService.deleteEmptyCalendarDays(func, aBeginDate, aFinishDate);
    	}
    }
    void serviceDeleteNoAppearanceDo(IWorkCalendarService aService, long aLpuId, long[] aFunctions,Date aBeginDate,Date aFinishDate) {
    	for (long func:aFunctions) {
    		//TODO доделать удаление не явок
    		//aService.deleteEmptyCalendarDays(func, aBeginDate, aFinishDate);
    	}
    }
    void serviceGenerateDo(IWorkCalendarService aService, long aLpuId, long[] aFunctions,Date aBeginDate,Date aFinishDate) {
    	for (long func:aFunctions) {
    		aService.generateCalendarByWorkFunction(func, aBeginDate, aFinishDate);
    	}
    }
    void serviceAddBusyPatternDo(IWorkCalendarService aService, long aLpuId, long[] aFunctions,Date aBeginDate,Date aFinishDate, Long aPattern) {
    	for (long func:aFunctions) {
    		aService.addBusyPatternByWorkFunction(func, aBeginDate, aFinishDate, aPattern);
    	}
    }
}