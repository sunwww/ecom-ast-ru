package ru.ecom.mis.web.action.worker;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.jaas.web.action.JaasUtil;
import ru.ecom.mis.ejb.service.worker.IWorkCalendarService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class WorkCalendarAddBusyPatternAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class) ;
        long lpuId = Long.parseLong(aRequest.getParameter("lpuId")) ;
        Long pattern = Long.parseLong(aRequest.getParameter("pattern")) ;
        String beginDateS = aRequest.getParameter("beginDate") ;
        String finishDateS = aRequest.getParameter("finishDate") ;
        Date beginDate = DateFormat.parseSqlDate(beginDateS) ;
        Date finishDate = DateFormat.parseSqlDate(finishDateS) ;
        serviceDo(service,lpuId, JaasUtil.convertToLongs(aRequest.getParameterValues("id")),beginDate,finishDate,pattern);
        return new ActionForward(aMapping.findForward("success").getPath()+"?id="+lpuId, true) ;
    }

    void serviceDo(IWorkCalendarService aService, long aLpuId, long[] aFunctions,Date aBeginDate,Date aFinishDate, Long aPattern) {
    	for (long func:aFunctions) {
    		aService.addBusyPatternByWorkFunction(func, aBeginDate, aFinishDate, aPattern);
    	}
        //service.addUsersToRole(aRoleId, aUsers);
    }
}
