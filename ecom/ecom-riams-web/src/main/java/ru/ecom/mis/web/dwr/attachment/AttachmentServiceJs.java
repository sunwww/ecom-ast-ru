package ru.ecom.mis.web.dwr.attachment;

import java.io.IOException;
import java.text.ParseException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.jdom.JDOMException;

import ru.ecom.mis.ejb.service.extdisp.IExtDispService;
import ru.ecom.mis.ejb.service.sync.lpuattachment.ISyncAttachmentDefectService;
import ru.ecom.web.util.Injection;

/**
 * 
 * @author VTsybulin 28.01.2015
 * 
 *
 */
public class AttachmentServiceJs {
	public String importDefectsFromXML(String aFileName, HttpServletRequest aRequest) throws NamingException {
		ISyncAttachmentDefectService service = Injection.find(aRequest).getService(ISyncAttachmentDefectService.class) ;
		try {
			return service.importDefectFromXML(aFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
