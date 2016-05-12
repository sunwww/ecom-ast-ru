package ru.ecom.expomc.web.actions.importtime;

import org.apache.struts.action.ActionForward;

import ru.ecom.ejb.services.monitor.MonitorId;
import ru.nuzmsh.web.util.StringSafeEncode;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 14.11.2006
 * Time: 22:16:51
 * To change this template use File | Settings | File Templates.
 */
public class MonitorActionForward extends ActionForward {

    public MonitorActionForward(MonitorId aMonitorId, ActionForward aNextAction) {
    	this(aMonitorId.getMonitorId(), aNextAction);
    }
    
    public MonitorActionForward(long aMonitorId, ActionForward aNextAction) {
        String nextAction = aNextAction.getPath().substring(1) ;
        setPath("/ecom_monitor.do?id="+aMonitorId+"&nextUrl="+theStringSafeEncode.encode(nextAction));
        setRedirect(true);
    }

    private final StringSafeEncode theStringSafeEncode = new StringSafeEncode();
}
