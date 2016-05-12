package ru.ecom.mis.web.action.patient;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.diary.ejb.service.protocol.IKdlDiaryService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.jaas.web.action.policy.ExportPolicyForm;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageWriterSpi;

public class UnionDoublePatientAction extends BaseAction{

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
		// TODO Auto-generated method stub
		IWebQueryService wqservice = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select pat.lastname,pat.firstname,pat.middlename,to_char(pat.birthday,'dd.mm.yyyy')")
			.append("\n from patient pat")
			.append("\n group by pat.lastname,pat.firstname,pat.middlename,pat.birthday")
			.append("\n having count(*)>1")
			.append("\n and count(distinct coalesce(''||pat.address_addressid,'-'))=1")
			.append("\n and count(distinct case when pat.snils is null or trim(pat.snils)='' then '-' else pat.snils end)=1") ;
    	Collection<WebQueryResult> list=wqservice.executeNativeSql(sql.toString()) ;
    	return aMapping.findForward("success") ;
    }
}