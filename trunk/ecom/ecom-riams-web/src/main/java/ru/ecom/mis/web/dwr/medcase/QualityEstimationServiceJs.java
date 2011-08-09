package ru.ecom.mis.web.dwr.medcase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import ru.ecom.mis.ejb.service.expert.IQualityEstimationService;
import ru.ecom.mis.ejb.service.expert.QualityEstimationRow;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class QualityEstimationServiceJs {
	public Boolean ableCreate(String aTypeSpecialist, HttpServletRequest aRequest) throws JspException {
		return RolesHelper.checkRoles(" /Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/Create,/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/"+aTypeSpecialist, aRequest) ;
	}
	public Long getIdQualityEstimationByType(String aType, Long aIdCard, HttpServletRequest aRequest) throws Exception {
		IQualityEstimationService service = Injection.find(aRequest).getService(IQualityEstimationService.class) ;
		return service.getIdQualityEstimationByType(aType, aIdCard) ;
	}
	public String getRowEdit(String aRequestParam, Long aCardId, String aTypeSpecialist, boolean aView, HttpServletRequest aRequest) throws Exception {
		IQualityEstimationService service = Injection.find(aRequest).getService(IQualityEstimationService.class) ;
		QualityEstimationRow row = service.getRow(IsFullExpertCardEdit(aRequest),aRequestParam, aCardId, aTypeSpecialist, aView) ;
		//res.append("<script type='text/javascript'>").append(row.getJavaScriptText()).append("</script>") ;
		return  row!=null?row.getTableColumn():"";
	}
	public String getRow( Long aCardId, String aTypeSpecialist, boolean aView, HttpServletRequest aRequest) throws Exception {
		IQualityEstimationService service = Injection.find(aRequest).getService(IQualityEstimationService.class) ;
		QualityEstimationRow row = service.getRow(aCardId, aTypeSpecialist, aView) ;
		//res.append("<script type='text/javascript'>").append(row.getJavaScriptText()).append("</script>") ;
		return  row!=null?row.getTableColumn():"";
	}
	public String getInfoBySmo(Long aSmo, HttpServletRequest aRequest) throws Exception {
		IQualityEstimationService service = Injection.find(aRequest).getService(IQualityEstimationService.class) ;
		return service.getInfoBySmo(aSmo);
		
	}
	public String getInfoByDep(Long aSmo, Long aDepartment, HttpServletRequest aRequest) throws Exception {
		IQualityEstimationService service = Injection.find(aRequest).getService(IQualityEstimationService.class) ;
		return service.getInfoByDep(aSmo, aDepartment);
		
	}
	public String getInfoBySlo(Long aSmo, Long aSlo, HttpServletRequest aRequest) throws Exception {
		IQualityEstimationService service = Injection.find(aRequest).getService(IQualityEstimationService.class) ;
		return service.getInfoBySlo(aSmo,aSlo);
	}
	
	public String getCountRow(Long aCardId, HttpServletRequest aRequest) throws Exception {
		IQualityEstimationService service = Injection.find(aRequest).getService(IQualityEstimationService.class) ;
		return service.getCountRow(aCardId);
		
	}
	private boolean IsFullExpertCardEdit(HttpServletRequest aRequest) throws JspException {
		return RolesHelper.checkRoles(" /Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/Create,/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/FullExpertCard", aRequest) ;
	}
}
