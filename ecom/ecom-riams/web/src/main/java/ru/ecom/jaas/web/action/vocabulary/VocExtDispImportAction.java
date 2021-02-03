package ru.ecom.jaas.web.action.vocabulary;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.jaas.web.action.service.ServiceImportRolesForm;
import ru.ecom.mis.ejb.service.vocabulary.IVocabularyService;
import ru.ecom.web.actions.monitor.MonitorActionForward;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class VocExtDispImportAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	final boolean clear  ;
        final IVocabularyService service = Injection.find(aRequest).getService(IVocabularyService.class) ;
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        final List<WebQueryResult> listServices = new LinkedList<>() ;
        final List<WebQueryResult> listRisks = new LinkedList<>() ;
        final List<WebQueryResult> listRoles = new LinkedList<>() ;
        if (aForm!=null) {
        	ServiceImportRolesForm form = (ServiceImportRolesForm)aForm ;
        	clear = form.getIsClear()!=null && form.getIsClear() ;
			try (InputStream in = form.getFile().getInputStream()) {
				Document doc = new SAXBuilder().build(in);
				Element parConfigElement = doc.getRootElement();
				for (Object o : parConfigElement.getChildren()) {
					Element parElement = (Element) o;
					if ("services".equals(parElement.getName())) {
						for (Object parPol : parElement.getChildren()) {
							Element polElement = (Element) parPol;
							if ("service".equals(polElement.getName())) {
								WebQueryResult wqr = new WebQueryResult();
								String code = polElement.getAttributeValue("code");
								wqr.set1(code);
								wqr.set2(polElement.getAttributeValue("name"));
								wqr.set3(polElement.getAttributeValue("isVisit"));
								wqr.set4(polElement.getAttributeValue("workFunctionCode"));
								wqr.set5(polElement.getAttributeValue("orphCode"));
								listServices.add(wqr);
							}
						}
					} else if ("risks".equals(parElement.getName())) {
						for (Object parPol : parElement.getChildren()) {
							Element polElement = (Element) parPol;
							if ("risk".equals(polElement.getName())) {
								WebQueryResult wqr = new WebQueryResult();
								String code = polElement.getAttributeValue("code");
								wqr.set1(code);
								wqr.set2(polElement.getAttributeValue("name"));
								listRisks.add(wqr);
							}
						}
					} else if ("vocExtDisps".equals(parElement.getName())) {
						for (Object vedObj : parElement.getChildren()) {
							Element vedElement = (Element) vedObj;
							WebQueryResult wqrVed = new WebQueryResult();
							String codeVed = vedElement.getAttributeValue("code");
							wqrVed.set1(codeVed);
							wqrVed.set2(vedElement.getAttributeValue("name"));
							wqrVed.set3(vedElement.getAttributeValue("internalCode"));

							final List<WebQueryResult> listAgeReport = new LinkedList<>();
							final List<WebQueryResult> listAge = new LinkedList<>();
							final List<WebQueryResult> listHealth = new LinkedList<>();
							final List<WebQueryResult> listPlan = new LinkedList<>();
							for (Object parPol : vedElement.getChildren()) {
								Element polElement = (Element) parPol;
								if ("ageGroupReports".equals(polElement.getName())) {
									for (Object parGroup : polElement.getChildren()) {
										Element groupElement = (Element) parGroup;
										if ("ageGroup".equals(groupElement.getName())) {
											WebQueryResult wqr = new WebQueryResult();
											String code = groupElement.getAttributeValue("code");
											wqr.set1(code);
											wqr.set2(groupElement.getAttributeValue("name"));
											listAgeReport.add(wqr);
										}
									}
								} else if ("ageGroups".equals(polElement.getName())) {
									for (Object parGroup : polElement.getChildren()) {
										Element groupElement = (Element) parGroup;
										if ("ageGroup".equals(groupElement.getName())) {
											WebQueryResult wqr = new WebQueryResult();
											String code = groupElement.getAttributeValue("code");
											wqr.set1(code);
											wqr.set2(groupElement.getAttributeValue("name"));
											wqr.set3(groupElement.getAttributeValue("reportGroup"));
											listAge.add(wqr);
										}
									}
								} else if ("healthGroups".equals(polElement.getName())) {
									for (Object parGroup : polElement.getChildren()) {
										Element groupElement = (Element) parGroup;
										if ("healthGroup".equals(groupElement.getName())) {
											WebQueryResult wqr = new WebQueryResult();
											String code = groupElement.getAttributeValue("code");
											wqr.set1(code);
											wqr.set2(groupElement.getAttributeValue("name"));
											listHealth.add(wqr);
										}
									}
								} else if ("plan".equals(polElement.getName())) {
									for (Object parGroup : polElement.getChildren()) {
										Element groupElement = (Element) parGroup;
										if ("str".equals(groupElement.getName())) {
											WebQueryResult wqr = new WebQueryResult();
											String code = groupElement.getAttributeValue("service");
											wqr.set1(code);
											wqr.set2(groupElement.getAttributeValue("sex"));
											wqr.set3(groupElement.getAttributeValue("age"));
											listPlan.add(wqr);
										}
									}
								}
							}
							wqrVed.set4(listAgeReport);
							wqrVed.set5(listAge);
							wqrVed.set6(listHealth);
							wqrVed.set7(listPlan);
							listRoles.add(wqrVed);
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
    	} else {
    		clear = false ;
    	}
    	
    	final long monitorId = monitorService.createMonitor() ;
        new Thread() {
            public void run() {
            	service.importVocExtDisp(monitorId, clear, listServices, listRisks, listRoles) ;
            }
        }.start() ;
        
        return new MonitorActionForward(monitorId, aMapping.findForward(SUCCESS)) ;
    }
}
