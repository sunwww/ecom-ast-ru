package ru.ecom.mis.web.dwr.extdisp;

import java.text.ParseException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.mis.ejb.service.extdisp.IExtDispService;
import ru.ecom.web.util.Injection;

/**
 * 
 * @author VTsybulin 22.12.2014
 *
 */
public class ExtDispServiceJs {

	public String setOrphCodes(HttpServletRequest aRequest) throws NamingException {
		IExtDispService service = Injection.find(aRequest).getService(IExtDispService.class) ;
		return service.setOrphCodes();
	}
	public String exportOrph(String aStartDate, String aFinishDate,
			String aFileNameSuffix, String aSqlAdd, String aFizGroup, String aHeight,
			String aWeight, String aHeadSize, String aAnalysesText,
			String aZOJReccomend, String aReccomend, HttpServletRequest aRequest) throws NamingException, ParseException {
		IExtDispService service = Injection.find(aRequest).getService(IExtDispService.class) ;
		return service.exportOrph(aStartDate, aFinishDate,
			aFileNameSuffix, aSqlAdd, aFizGroup, aHeight,
			aWeight, aHeadSize, aAnalysesText,
			aZOJReccomend, aReccomend);
	}
}
