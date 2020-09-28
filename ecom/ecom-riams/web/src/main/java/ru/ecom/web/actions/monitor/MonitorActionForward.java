package ru.ecom.web.actions.monitor;

import org.apache.struts.action.ActionForward;
import ru.nuzmsh.web.util.StringSafeEncode;

/**
 */
public class MonitorActionForward extends ActionForward {

    public MonitorActionForward(long aMonitorId, ActionForward aNextAction) {
        String nextAction = aNextAction.getPath().substring(1) ;
        StringSafeEncode theStringSafeEncode = new StringSafeEncode();
        setPath("/ecom_monitor.do?id="+aMonitorId+"&nextUrl="+ theStringSafeEncode.encode(nextAction));
        setRedirect(true);
    }

}
