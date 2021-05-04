package ru.ecom.mis.ejb.service.vocabulary;

import org.w3c.dom.Element;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.jaas.ejb.domain.SystemVocabulary;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispPlan;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispPlanService;
import ru.ecom.mis.ejb.domain.extdisp.voc.*;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.ecom.report.util.XmlDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;



@Stateless
@Remote(IVocabularyService.class)
public class VocabularyServiceBean {
	public Collection<VocEntityInfo> listVocEntities() {
		Collection<Class> entities = entityHelper.listAllEntities() ;
		ArrayList<VocEntityInfo> ret = new ArrayList<>(entities.size()) ;
		for(Class clazz : entities) {
			if(entityHelper.isVocEntity(clazz) && !clazz.isAnnotationPresent(Deprecated.class)) {
				//if (theContext.isCallerInRole("/Policy/Voc/"+clazz.getSimpleName())) {
					ret.add(getInfo(clazz));
				//}
			}
		}
		return ret;
	}
	
	/**
	 * Получить информацию по справочнику
	 * @param aVocEntityClass
	 * @return объект VocEntityInfo
	 */
	private VocEntityInfo getInfo(Class aVocEntityClass) {
		Comment comment = (Comment) aVocEntityClass.getAnnotation(Comment.class) ;
		String commentName = comment!=null?comment.value():aVocEntityClass.getSimpleName();
		SystemVocabulary systemIs = (SystemVocabulary)aVocEntityClass.getAnnotation(SystemVocabulary.class) ;

		return new VocEntityInfo(aVocEntityClass.getName(),entityHelper.getEntityName(aVocEntityClass), commentName, 0,systemIs!=null);
		/*for(Method m : aVocEntityClass.getMethods()) {
			if(! m.getName().equals("getId")
					&& m.getName().startsWith("get")
					&& !m.getName().equals("getClass")
					&& entityHelper.hasSimpleType(m)) {
				String name = PropertyUtil.getPropertyName(m);
				Comment commentMethodAnnotation = m.getAnnotation(Comment.class) ;
				String propComment = commentMethodAnnotation!=null ? commentMethodAnnotation.value()  : name ;
				VocEntityPropertyInfo propInfo = new VocEntityPropertyInfo(name, propComment) ;
				info.getProperties().add(propInfo);
			}
		}*/

	}

	public int getCount(String clazz) {
		Long totalCount = (Long)manager.createQuery("select count(*) from " +
				clazz).getSingleResult() ;
		return totalCount.intValue() ;
	}
    public void importVocExtDisp(long aMonitorId,boolean aClear, List<WebQueryResult> aService, List<WebQueryResult> aRisks, List<WebQueryResult> aExtDisps) {
    	IMonitor monitor = monitorService.acceptMonitor(aMonitorId, "Подготовка к импорту") ;
    	try {
    		monitor = monitorService.startMonitor(aMonitorId, "Импорт типов диспансеризаций", Double.valueOf(aService.size()+aRisks.size()+aExtDisps.size())) ;
    		for(WebQueryResult wqr : aService) {
    			if(monitor.isCancelled()) throw new IllegalStateException("Прервано пользователем") ;
    			monitor.advice(1) ;
    			monitor.setText("Импортируется  услуга: "+wqr.get1());
    			String code = wqr.get1()!=null?""+wqr.get1():"" ;
    			List<VocExtDispService> list = manager.createQuery("from VocExtDispService where code='"+code+"'").getResultList() ;
    			if (!list.isEmpty()) {
    				VocExtDispService service = list.get(0);
    				
    				if (wqr.get1()!=null) service.setCode(""+wqr.get1()) ;
    				if (wqr.get2()!=null) service.setName(""+wqr.get2()) ;
    				service.setIsVisit(wqr.get3()!=null && (""+wqr.get3()).equals("1") ) ;
    				service.setWorkFunctionCode(""+wqr.get4());
    				String wfCode = wqr.get4()!=null?(""+wqr.get4()).trim():null ;
    				if (wfCode!=null &&wfCode.equals("")) wfCode=null ;
    				if (wfCode==null) {
    					manager.createNativeQuery("delete from VocExtDispServiceFunction where name='"+code.trim()+"'").executeUpdate() ;
    					
    				} else {
						manager.createNativeQuery("delete from VocExtDispServiceFunction where name='" + code + "' and code not in ('" + wfCode.replace(",", "','") + "')").executeUpdate();
    					String[] wf = wfCode.split(",") ;
    					for (String w:wf) {
    						List<Object> l=manager.createNativeQuery("select code from VocExtDispServiceFunction where name='"+code+"' and code='"+w+"'").setMaxResults(1).getResultList();
								if (l.isEmpty()) {
									VocExtDispServiceFunction vedsf = new VocExtDispServiceFunction() ;
									vedsf.setCode(w) ;
									vedsf.setName(code) ;
									manager.persist(vedsf) ;
								}
    					}
    				}
    				service.setOrphCode(""+wqr.get5());
    				manager.persist(service) ;
    				hashService.put(""+wqr.get1(), service) ;
    				
    			} else {
    				VocExtDispService service = new VocExtDispService() ;
    				service.setCode(""+wqr.get1()) ;
    				service.setName(""+wqr.get2()) ;
    				service.setIsVisit(wqr.get3()!=null && (""+wqr.get3()).equals("1")) ;
    				service.setWorkFunctionCode(""+wqr.get4());
    				service.setOrphCode(""+wqr.get5());
    				manager.persist(service) ;
    				hashService.put(""+wqr.get1(), service) ;
    			}
    		}
    		for(WebQueryResult wqr : aRisks) {
    			if(monitor.isCancelled()) throw new IllegalStateException("Прервано пользователем") ;
    			monitor.advice(1) ;
    			monitor.setText("Импортируется  риск: "+wqr.get1());
    			List<VocExtDispService> list = manager.createQuery("from VocExtDispRisk where code='"+wqr.get1()+"'").getResultList() ;
    			if (list.isEmpty()) {
    				VocExtDispRisk service = new VocExtDispRisk() ;
    				service.setCode(""+wqr.get1()) ;
    				service.setName(""+wqr.get2()) ;
    				manager.persist(service) ;
    			}
    		}
        	for(WebQueryResult wqr : aExtDisps) {
        		if(monitor.isCancelled()) throw new IllegalStateException("Прервано пользователем") ;
        		monitor.advice(1) ;
        		monitor.setText("Импортируется  диспансеризация: "+wqr.get1());
        		List<VocExtDisp> list = manager.createQuery("from VocExtDisp where code='"+wqr.get1()+"'").getResultList() ;
        		VocExtDisp dispType ;
        		ExtDispPlan plan = null;
        		if (!list.isEmpty()) {
        			dispType=list.get(0) ;
        			List<ExtDispPlan> listPlan = manager.createQuery("from ExtDispPlan where dispType_id='"+dispType.getId()+"'").getResultList() ;
        			if (!listPlan.isEmpty()) {
        				plan = listPlan.get(0) ;
        				if (aClear) {
                		manager.createNativeQuery("delete from ExtDispPlanService where plan_id='"+plan.getId()+"'").executeUpdate();	
                		}
        			} 
        		} else {
        			dispType = new VocExtDisp() ;
        			dispType.setCode(""+wqr.get1()) ;
        			dispType.setName(""+wqr.get2()) ;
        			manager.persist(dispType) ;
        		}
        		if (plan==null) {
        			plan = new ExtDispPlan() ;
        			plan.setDispType(dispType) ;
        			manager.persist(plan) ;
        		}
        		
        		List<WebQueryResult> listHealthGroup = (List<WebQueryResult>)wqr.get6() ;
        		for (WebQueryResult wqrHG:listHealthGroup) {
        			String code =  ""+wqrHG.get1() ;
        			List<VocExtDispHealthGroup> listPlan = manager.createQuery("from VocExtDispHealthGroup where dispType_id='"+dispType.getId()+"' and code='"+code+"'").getResultList() ;
        			if (listPlan.isEmpty()) {
        				VocExtDispHealthGroup hg = new VocExtDispHealthGroup() ;
        				hg.setDispType(dispType) ;
        				hg.setCode(code) ;
        				hg.setName(""+wqrHG.get2()) ;
        				manager.persist(hg) ;
        			}
        		}
        		List<WebQueryResult> listAgeGroupReport = (List<WebQueryResult>)wqr.get4() ;
        		HashMap<String, VocExtDispAgeReportGroup> hashAgeRepGroup = new HashMap<>();
        		HashMap<String, VocExtDispAgeGroup> hashAgeGroup = new HashMap<>();
        		for (WebQueryResult wqrHG:listAgeGroupReport) {
        			String code = ""+wqrHG.get1() ;
        			List<VocExtDispAgeReportGroup> listPlan = manager.createQuery("from VocExtDispAgeReportGroup where dispType_id='"+dispType.getId()+"' and code='"+code+"'").getResultList() ;
        			if (listPlan.isEmpty()) {
        				VocExtDispAgeReportGroup hg = new VocExtDispAgeReportGroup() ;
        				hg.setDispType(dispType) ;
        				hg.setCode(code) ;
        				hg.setName(""+wqrHG.get2()) ;
        				manager.persist(hg) ;
        				hashAgeRepGroup.put(code, hg) ;
        			} else {
        				hashAgeRepGroup.put(code, listPlan.get(0)) ;
        			}
        		}
        		List<WebQueryResult> listAgeGroup = (List<WebQueryResult>)wqr.get5() ;
        		if (aClear) {
        			List<VocExtDispAgeGroup> listPlans = manager.createQuery("from VocExtDispAgeGroup where dispType_id='"+dispType.getId()+"'").getResultList() ;
            		for (VocExtDispAgeGroup vedag:listPlans ) {
            			vedag.setIsArchival(true);
            		}
        		}
        		
        		for (WebQueryResult wqrHG:listAgeGroup) {
        			String code=""+wqrHG.get1() ;
        			List<VocExtDispAgeGroup> listPlan = manager.createQuery("from VocExtDispAgeGroup where dispType_id='"+dispType.getId()+"' and code='"+code+"'").getResultList() ;
        			if (listPlan.isEmpty()) {
        				VocExtDispAgeGroup hg = new VocExtDispAgeGroup() ;
        				hg.setDispType(dispType) ;
        				hg.setCode(code) ;
        				hg.setName(""+wqrHG.get2()) ;
        				hg.setReportGroup(hashAgeRepGroup.get(""+wqrHG.get3())) ;
        				hg.setIsArchival(false);
        				manager.persist(hg) ;
        				hashAgeGroup.put(code, hg) ;
        			} else {
        				if (aClear) {
        					listPlan.get(0).setIsArchival(false);
        				}
        				hashAgeGroup.put(code, listPlan.get(0)) ;
        			}
        		}
        		List<WebQueryResult> listPlanService = (List<WebQueryResult>)wqr.get7() ;
        		for (WebQueryResult wqrHG:listPlanService) {
        			String codeService=""+wqrHG.get1() ;
        			String codeSex=""+wqrHG.get2() ;
        			String codeAge=""+wqrHG.get3() ;
        			List<Object[]> listPlan = manager
        					.createNativeQuery(new StringBuilder().append("select edps.id,vs.id as vsid from ExtDispPlanService edps  ")
        							.append(" left join VocSex vs on vs.id=edps.sex_id")
        							.append(" left join VocExtDispService veds on veds.id=edps.serviceType_id")
        							.append(" left join VocExtDispAgeGroup vedag on vedag.id=edps.ageGroup_id")
        							.append(" where edps.plan_id='").append(plan.getId())
        							.append("' and veds.code='").append(codeService)
        							.append("' and vedag.code='").append(codeAge)
        							.append("' and (vs.omcCode is null or vs.omcCode='")
        							.append(codeSex).append("')").toString())
        					.getResultList() ;
        			
        			if (listPlan.isEmpty()) {
        				VocSex vs = findSex(codeSex) ;
        				ExtDispPlanService edps = new ExtDispPlanService() ;
        				edps.setPlan(plan) ;
        				edps.setAgeGroup(hashAgeGroup.get(codeAge)) ;
        				edps.setSex(vs) ;
        				edps.setServiceType(hashService.get(codeService)) ;
        				manager.persist(edps) ;
        			}
        		}
        	}
        	monitor.finish("") ;
    	} catch (Exception e) {
    		monitor.error(e.getMessage(), e) ;
    	}
    	
    }
	
	public String exportVocExtDisp(long[] aVocExpDisps) throws TransformerException, ParserConfigurationException {
    	EjbEcomConfig config = EjbEcomConfig.getInstance() ;
    	String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
    	String filename = "export-voc_ext_disp-"+System.currentTimeMillis()+".xml" ;
    	File outFile = new File(workDir+"/"+filename) ;
    	XmlDocument xmlDoc = new XmlDocument() ;
    	Element root = xmlDoc.newElement(xmlDoc.getDocument(), "extdisp", null) ;
    	
    	//SecUserServiceBean userbean = new SecUserServiceBean() ;
    	List<VocExtDispService> listService = manager.createQuery("from VocExtDispService").getResultList() ;
    	List<VocExtDispRisk> listRisk = manager.createQuery("from VocExtDispRisk").getResultList() ;
    	Element servicesEl = xmlDoc.newElement(root, "services", null);
    	for (VocExtDispService serv : listService) {
    		Element el = xmlDoc.newElement(servicesEl, "service", null);
    		xmlDoc.newAttribute(el, "code", serv.getCode());
    		xmlDoc.newAttribute(el, "isVisit", serv.getIsVisit()!=null && serv.getIsVisit()?"1":"0");
    		xmlDoc.newAttribute(el, "name", serv.getName());
    		List<Object> wf = manager.createNativeQuery("select list(code) from VocExtDispServiceFunction where name='"+serv.getCode()+"' and code is not null and code!=''").getResultList() ;
    		if (!wf.isEmpty()) {
				String wfc = ("" + wf.get(0)).replace(" ", "");
    			xmlDoc.newAttribute(el, "workFunctionCode", wfc);
    		} else {
    			xmlDoc.newAttribute(el, "workFunctionCode", "");
    		}
    		xmlDoc.newAttribute(el, "orphCode", serv.getOrphCode());
    		
    	}
    	Element risksEl = xmlDoc.newElement(root, "risks", null);
    	for (VocExtDispRisk risk : listRisk) {
    		Element el = xmlDoc.newElement(risksEl, "risk", null);
    		xmlDoc.newAttribute(el, "code", risk.getCode());
    		xmlDoc.newAttribute(el, "name", risk.getName());
    	}
    	
    	Element vedMainEl = xmlDoc.newElement(root, "vocExtDisps", null);
    	for (Long vedId:aVocExpDisps) {
    		VocExtDisp ved = manager.find(VocExtDisp.class, vedId) ;
    		List<VocExtDispAgeReportGroup> listAgeReportGroup = manager.createQuery("from VocExtDispAgeReportGroup where dispType=:dispType").setParameter("dispType", ved).getResultList() ;
    		List<VocExtDispAgeGroup> listAgeGroup = manager.createQuery("from VocExtDispAgeGroup where dispType=:dispType and (isArchival is null or isArchival='0')").setParameter("dispType", ved).getResultList() ;
    		List<VocExtDispHealthGroup> listHealthGroup = manager.createQuery("from VocExtDispHealthGroup where dispType=:dispType").setParameter("dispType", ved).getResultList() ;
    		
    		Element vedEl = xmlDoc.newElement(vedMainEl, "vocExtDisp", null);
    		xmlDoc.newAttribute(vedEl, "name", ved.getName());
    		xmlDoc.newAttribute(vedEl, "code", ved.getCode());
    		xmlDoc.newAttribute(vedEl, "internalCode", ved.getInternalCode());
    		Element ageGroupReportsEl = xmlDoc.newElement(vedEl, "ageGroupReports", null);
    		for (VocExtDispAgeReportGroup ageGroup : listAgeReportGroup) {
    			Element pol = xmlDoc.newElement(ageGroupReportsEl, "ageGroup", null);
    			xmlDoc.newAttribute(pol, "code", ageGroup.getCode());
    			xmlDoc.newAttribute(pol, "name", ageGroup.getName());
    		}
    		Element ageGroupsEl = xmlDoc.newElement(vedEl, "ageGroups", null);
    		for (VocExtDispAgeGroup ageGroup : listAgeGroup) {
    			Element pol = xmlDoc.newElement(ageGroupsEl, "ageGroup", null);
    			xmlDoc.newAttribute(pol, "code", ageGroup.getCode());
    			xmlDoc.newAttribute(pol, "dispCode", ageGroup.getDispCode());
    			xmlDoc.newAttribute(pol, "name", ageGroup.getName());
    			xmlDoc.newAttribute(pol, "reportGroup", ageGroup.getReportGroup()!=null?ageGroup.getReportGroup().getCode():"");
    		}
    		Element healthGroupsEl = xmlDoc.newElement(vedEl, "healthGroups", null);
    		for (VocExtDispHealthGroup healthGroup : listHealthGroup) {
        		Element pol = xmlDoc.newElement(healthGroupsEl, "healthGroup", null);
        		xmlDoc.newAttribute(pol, "code", healthGroup.getCode());
        		xmlDoc.newAttribute(pol, "dispCode", healthGroup.getDispCode());
        		xmlDoc.newAttribute(pol, "name", healthGroup.getName());
    		}
			String sqlPlan = "select veds.code as vedscode,vs.omcCode as vsomcCode,vedag.code as vedagcode" +
					" from ExtDispPlanService edps" +
					" left join ExtDispPlan edp on edp.id=edps.plan_id" +
					" left join VocSex vs on vs.id=edps.sex_id" +
					" left join VocExtDispService veds on veds.id=edps.serviceType_id" +
					" left join VocExtDispAgeGroup vedag on vedag.id=edps.ageGroup_id" +
					" where edp.dispType_id='" + ved.getId() + "' and (isArchival is null or isArchival='0')" +
					" group by veds.code,vs.omcCode,vedag.code" +
					" order by veds.code";
			List<Object[]> listPlan = manager.createNativeQuery(sqlPlan).getResultList() ;
    		Element planEl = xmlDoc.newElement(vedEl, "plan", null);
    		for (Object[] obj : listPlan) {
    			Element pol = xmlDoc.newElement(planEl, "str", null);
    			xmlDoc.newAttribute(pol, "service", obj[0]!=null?""+obj[0]:"");
    			xmlDoc.newAttribute(pol, "sex", obj[1]!=null?""+obj[1]:"");
        		xmlDoc.newAttribute(pol, "age", obj[2]!=null?""+obj[2]:"");
    		}
    	}
    	xmlDoc.saveDocument(outFile) ;
    	return filename;
	}
	public VocSex findSex(String aCode) {
		VocSex vs = hashSex.get(aCode) ;
		if (vs==null) {
			List<VocSex> list = manager.createQuery("from VocSex where omcCode='"+aCode+"'").getResultList() ;
			if (!list.isEmpty()) {
				vs = list.get(0) ;
				hashSex.put(aCode,vs) ;
			}
		}
		return vs ;
	}
	private @PersistenceContext EntityManager manager ;
	private final EntityHelper entityHelper = EntityHelper.getInstance();
	@Resource SessionContext context ;
    private @EJB ILocalMonitorService monitorService ;
    private final HashMap<String, VocExtDispService> hashService = new HashMap<>();
    private final HashMap<String, VocSex> hashSex = new HashMap<>();
}
