package ru.ecom.mis.ejb.service.sync.lpuattachment;

import java.io.IOException;
import java.text.ParseException;

import org.jdom.JDOMException;

public interface ISyncAttachmentDefectService {
    public void sync(long aMonitorId, long aTimeId) ;
    public String importDefectFromXML(String aFileName);
    public String cleanDefect(long aAttachmentId);
}
