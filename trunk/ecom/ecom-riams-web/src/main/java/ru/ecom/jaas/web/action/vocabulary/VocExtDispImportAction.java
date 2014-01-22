package ru.ecom.jaas.web.action.vocabulary;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.crypto.spec.IvParameterSpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.jaas.ejb.service.ISecPolicyImportService;
import ru.ecom.jaas.ejb.service.ISecService;
import ru.ecom.jaas.ejb.service.ImportRoles;
import ru.ecom.jaas.ejb.service.PolicyForm;
import ru.ecom.jaas.web.action.policy.ExportPolicyForm;
import ru.ecom.jaas.web.action.role.RolePoliciesSaveAction;
import ru.ecom.jaas.web.action.service.ServiceImportPolicesListForm;
import ru.ecom.jaas.web.action.service.ServiceImportRolesForm;
import ru.ecom.mis.ejb.service.vocabulary.IVocabularyService;
import ru.ecom.web.actions.monitor.MonitorActionForward;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class VocExtDispImportAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	final boolean clear  ;
        final IVocabularyService service = Injection.find(aRequest).getService(IVocabularyService.class) ;
        IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
        final List<WebQueryResult> listServices = new LinkedList<WebQueryResult>() ;
        final List<WebQueryResult> listRisks = new LinkedList<WebQueryResult>() ;
        final List<WebQueryResult> listRoles = new LinkedList<WebQueryResult>() ;
        if (aForm!=null) {
        	ServiceImportRolesForm form = (ServiceImportRolesForm)aForm ;
        	clear = form.getIsClear()!=null &&form.getIsClear()==true?true:false ;
        	InputStream in = null;
    		System.out.println("file="+form.getFile().getContentType()+" "+form.getFile().getInputStream()) ;
            try {
            	in =form.getFile().getInputStream() ;
               	Document doc = new SAXBuilder().build(in);
                Element parConfigElement = doc.getRootElement();
                System.out.println(new StringBuilder().append("		root=").append(parConfigElement).toString());
                Long i =Long.valueOf(1) ;
                for (Object o : parConfigElement.getChildren()) {
                    Element parElement = (Element) o;
                    if("services".equals(parElement.getName())) {
                    	System.out.println("Услуги") ;
                    	for (Object parPol: parElement.getChildren()) {
                    		Element polElement = (Element) parPol ;
                    		if("service".equals(polElement.getName())) {
                    			WebQueryResult wqr = new WebQueryResult() ;
                    			String code = polElement.getAttributeValue("code") ;
                    			wqr.set1(code);
                    			wqr.set2(polElement.getAttributeValue("name"));
                    			wqr.set3(polElement.getAttributeValue("isVisit"));
                    			listServices.add(wqr) ;
                    			System.out.println("\t услуга ["+code+"]") ;
                    		}
                    	}
                    } else if("risks".equals(parElement.getName())) {
                    	System.out.println("Риски") ;
                    	for (Object parPol: parElement.getChildren()) {
                    		Element polElement = (Element) parPol ;
                    		if("risk".equals(polElement.getName())) {
                    			WebQueryResult wqr = new WebQueryResult() ;
                    			String code = polElement.getAttributeValue("code") ;
                    			wqr.set1(code);
                    			wqr.set2(polElement.getAttributeValue("name"));
                    			listRisks.add(wqr) ;
                    			System.out.println("\t риск ["+code+"]") ;
                    		}
                    	}
                    } else if("vocExtDisps".equals(parElement.getName())) {
                    	System.out.println("Виды диспансеризации") ;
                    	for (Object vedObj : parElement.getChildren()) {
                    		Element vedElement = (Element) vedObj ;
	                    	WebQueryResult wqrVed = new WebQueryResult() ;
	                    	String codeVed =  vedElement.getAttributeValue("code");
	                    	wqrVed.set1(codeVed) ;
	                    	wqrVed.set2(vedElement.getAttributeValue("name")) ;
	                    	wqrVed.set3(vedElement.getAttributeValue("internalCode")) ;
	                    	
	                    	final List<WebQueryResult> listAgeReport = new LinkedList<WebQueryResult> () ;
	                    	final List<WebQueryResult> listAge = new LinkedList<WebQueryResult> () ;
	                    	final List<WebQueryResult> listHealth = new LinkedList<WebQueryResult> () ;
	                    	final List<WebQueryResult> listPlan = new LinkedList<WebQueryResult> () ;
	                    	System.out.println("ved [code"+codeVed+"]") ;
	                    	for (Object parPol: vedElement.getChildren()) {
	                    		Element polElement = (Element) parPol ;
	                            if("ageGroupReports".equals(polElement.getName())) {
	                            	System.out.println("Возрастные группы для отчета") ;
	                            	for (Object parGroup: polElement.getChildren()) {
	                            		Element groupElement = (Element) parGroup ;
	                            		if("ageGroup".equals(groupElement.getName())) {
	                            			WebQueryResult wqr = new WebQueryResult() ;
	                            			String code = groupElement.getAttributeValue("code") ;
	                            			wqr.set1(code);
	                            			wqr.set2(groupElement.getAttributeValue("name"));
	                            			listAgeReport.add(wqr) ;
	                            			System.out.println("\t услуга ["+code+"]") ;
	                            		}
	                            	}
	                            } else if("ageGroups".equals(polElement.getName())) {
	                            	System.out.println("Возрастные группы") ;
	                            	for (Object parGroup: polElement.getChildren()) {
	                            		Element groupElement = (Element) parGroup ;
	                            		if("ageGroup".equals(groupElement.getName())) {
	                            			WebQueryResult wqr = new WebQueryResult() ;
	                            			String code = groupElement.getAttributeValue("code") ;
	                            			wqr.set1(code);
	                            			wqr.set2(groupElement.getAttributeValue("name"));
	                            			wqr.set3(groupElement.getAttributeValue("reportGroup"));
	                            			listAge.add(wqr) ;
	                            			System.out.println("\t  возрастная группа ["+code+"]") ;
	                            		}
	                            	}
	                            } else if("healthGroups".equals(polElement.getName())) {
	                            	System.out.println("Группы здоровья") ;
	                            	for (Object parGroup: polElement.getChildren()) {
	                            		Element groupElement = (Element) parGroup ;
	                            		if("healthGroup".equals(groupElement.getName())) {
	                            			WebQueryResult wqr = new WebQueryResult() ;
	                            			String code = groupElement.getAttributeValue("code") ;
	                            			wqr.set1(code);
	                            			wqr.set2(groupElement.getAttributeValue("name"));
	                            			listHealth.add(wqr) ;
	                            			System.out.println("\t  группа здоровья ["+code+"]") ;
	                            		}
	                            	}
	                            } else if("plan".equals(polElement.getName())) {
	                            	System.out.println("План по услугам") ;
	                            	for (Object parGroup: polElement.getChildren()) {
	                            		Element groupElement = (Element) parGroup ;
	                            		if("str".equals(groupElement.getName())) {
	                            			WebQueryResult wqr = new WebQueryResult() ;
	                            			String code = groupElement.getAttributeValue("service") ;
	                            			wqr.set1(code);
	                            			wqr.set2(groupElement.getAttributeValue("sex"));
	                            			wqr.set3(groupElement.getAttributeValue("age"));
	                            			listPlan.add(wqr) ;
	                            			System.out.println("\t  план ["+code+"]") ;
	                            		}
	                            	}
	                            }
	                    	}
	                    	wqrVed.set4(listAgeReport) ;
	                    	wqrVed.set5(listAge) ;
	                    	wqrVed.set6(listHealth) ;
	                    	wqrVed.set7(listPlan) ;
	                    	listRoles.add(wqrVed) ;
                    	}
                    } 
                }
            }catch(Exception e) {
            	System.out.println(e.getMessage());
            } 
            finally {
                in.close();
            }
            in.close() ;
    	} else {
    		clear = false ;
    	}
    	
    	final long monitorId = monitorService.createMonitor() ;
        new Thread() {
            public void run() {
            	service.importVocExtDisp(monitorId, clear, listServices, listRisks, listRoles) ;
            }
        }.start() ;
        
        return new MonitorActionForward(monitorId, aMapping.findForward("success")) ;
    }
}
