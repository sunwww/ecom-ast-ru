package ru.ecom.mis.ejb.service.vocabulary;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Element;

import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.domain.SecRole;
import ru.ecom.jaas.ejb.domain.SystemVocabulary;
import ru.ecom.jaas.ejb.service.CreateReplaceMapHelper;
import ru.ecom.jaas.ejb.service.SecUserServiceBean;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispPlan;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispPlanService;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDisp;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispAgeGroup;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispAgeReportGroup;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispHealthGroup;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispRisk;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispService;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispServiceFunction;
import ru.ecom.mis.ejb.domain.patient.voc.VocIdentityCard;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.ecom.report.util.XmlDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;



@Stateless
@Remote(IVocabularyService.class)
public class VocabularyServiceBean {
	public Collection<VocEntityInfo> listVocEntities() {
		Collection<Class> entities = theEntityHelper.listAllEntities() ;
		ArrayList<VocEntityInfo> ret = new ArrayList<VocEntityInfo>(entities.size()) ; 
		for(Class clazz : entities) {
			if(theEntityHelper.isVocEntity(clazz) && !clazz.isAnnotationPresent(Deprecated.class)) {
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
		
		VocEntityInfo info = new VocEntityInfo(aVocEntityClass.getName(),theEntityHelper.getEntityName(aVocEntityClass), commentName, 0,systemIs!=null?true:false);
		/*for(Method m : aVocEntityClass.getMethods()) {
			if(! m.getName().equals("getId")
					&& m.getName().startsWith("get")
					&& !m.getName().equals("getClass")
					&& theEntityHelper.hasSimpleType(m)) {
				String name = PropertyUtil.getPropertyName(m);
				Comment commentMethodAnnotation = m.getAnnotation(Comment.class) ;
				String propComment = commentMethodAnnotation!=null ? commentMethodAnnotation.value()  : name ;
				VocEntityPropertyInfo propInfo = new VocEntityPropertyInfo(name, propComment) ;
				info.getProperties().add(propInfo);
			}
		}*/
		return info ;
	}
	
	private int getCount(Class clazz) {
		Long totalCount = (Long)theManager.createQuery(new StringBuilder().append("select count(*) from ").append(
				theEntityHelper.getEntityName(clazz)).toString()).getSingleResult() ;
		return totalCount.intValue() ;
	}
	public int getCount(String clazz) {
		Long totalCount = (Long)theManager.createQuery(new StringBuilder().append("select count(*) from ").append(
				clazz).toString()).getSingleResult() ;
		return totalCount.intValue() ;
	}
    public void importVocExtDisp(long aMonitorId,boolean aClear, List<WebQueryResult> aService, List<WebQueryResult> aRisks, List<WebQueryResult> aExtDisps) {
    	IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Подготовка к импорту") ;
    	try {
    		monitor = theMonitorService.startMonitor(aMonitorId, "Импорт типов диспансеризаций", aService.size()+aRisks.size()+aExtDisps.size()) ;
    		for(WebQueryResult wqr : aService) {
    			if(monitor.isCancelled()) throw new IllegalStateException("Прервано пользователем") ;
    			monitor.advice(1) ;
    			monitor.setText("Импортируется  услуга: "+wqr.get1());
    			String code = wqr.get1()!=null?""+wqr.get1():"" ;
    			List<VocExtDispService> list = theManager.createQuery("from VocExtDispService where code='"+code+"'").getResultList() ;
    			if (list.size()>0) {
    				VocExtDispService service = list.get(0);
    				
    				if (wqr.get1()!=null) service.setCode(""+wqr.get1()) ;
    				if (wqr.get2()!=null) service.setName(""+wqr.get2()) ;
    				service.setIsVisit(wqr.get3()!=null&&(""+wqr.get3()).equals("1")?true:false) ;
    				service.setWorkFunctionCode(""+wqr.get4());
    				String wfCode = wqr.get4()!=null?""+wqr.get4():null ;
    				wfCode=wfCode.trim() ;
    				if (wfCode!=null &&wfCode.equals("")) wfCode=null ;
    				if (wfCode==null) {
    					theManager.createNativeQuery("delete from VocExtDispServiceFunction where name='"+code.trim()+"'").executeUpdate() ;
    					
    				} else {
    					theManager.createNativeQuery("delete from VocExtDispServiceFunction where name='"+code+"' and code not in ('"+wfCode.replaceAll(",","','")+"')").executeUpdate() ;
    					String[] wf = wfCode.split(",") ;
    					for (String w:wf) {
    						List<Object> l=theManager.createNativeQuery("select code from VocExtDispServiceFunction where name='"+code+"' and code='"+w+"'").setMaxResults(1).getResultList();
								if (l.isEmpty()) {
									VocExtDispServiceFunction vedsf = new VocExtDispServiceFunction() ;
									vedsf.setCode(w) ;
									vedsf.setName(code) ;
									theManager.persist(vedsf) ;
								}
    					}
    				}
    				service.setOrphCode(""+wqr.get5());
    				theManager.persist(service) ;
    				theHashService.put(""+wqr.get1(), service) ;
    				
    			} else {
    				VocExtDispService service = new VocExtDispService() ;
    				service.setCode(""+wqr.get1()) ;
    				service.setName(""+wqr.get2()) ;
    				service.setIsVisit(wqr.get3()!=null&&(""+wqr.get3()).equals("1")?true:false) ;
    				service.setWorkFunctionCode(""+wqr.get4());
    				service.setOrphCode(""+wqr.get5());
    				theManager.persist(service) ;
    				theHashService.put(""+wqr.get1(), service) ;
    			}
    		}
    		for(WebQueryResult wqr : aRisks) {
    			if(monitor.isCancelled()) throw new IllegalStateException("Прервано пользователем") ;
    			monitor.advice(1) ;
    			monitor.setText("Импортируется  риск: "+wqr.get1());
    			List<VocExtDispService> list = theManager.createQuery("from VocExtDispRisk where code='"+wqr.get1()+"'").getResultList() ;
    			if (list.size()>0) {
    			} else {
    				VocExtDispRisk service = new VocExtDispRisk() ;
    				service.setCode(""+wqr.get1()) ;
    				service.setName(""+wqr.get2()) ;
    				theManager.persist(service) ;
    			}
    		}
        	for(WebQueryResult wqr : aExtDisps) {
        		if(monitor.isCancelled()) throw new IllegalStateException("Прервано пользователем") ;
        		monitor.advice(1) ;
        		monitor.setText("Импортируется  диспансеризация: "+wqr.get1());
        		List<VocExtDisp> list = theManager.createQuery("from VocExtDisp where code='"+wqr.get1()+"'").getResultList() ;
        		VocExtDisp dispType = null;
        		ExtDispPlan plan = null;
        		if (list.size()>0) {
        			dispType=list.get(0) ;
        			List<ExtDispPlan> listPlan = theManager.createQuery("from ExtDispPlan where dispType_id='"+dispType.getId()+"'").getResultList() ;
        			if (listPlan.size()>0) {
        				plan = listPlan.get(0) ;
        				if (aClear) {
                		theManager.createNativeQuery("delete from ExtDispPlanService where plan_id='"+plan.getId()+"'").executeUpdate();	
                		}
        			} 
        		} else {
        			dispType = new VocExtDisp() ;
        			dispType.setCode(""+wqr.get1()) ;
        			dispType.setName(""+wqr.get2()) ;
        			theManager.persist(dispType) ;
        		}
        		if (plan==null) {
        			plan = new ExtDispPlan() ;
        			plan.setDispType(dispType) ;
        			theManager.persist(plan) ;
        		}
        		
        		List<WebQueryResult> listHealthGroup = (List<WebQueryResult>)wqr.get6() ;
        		for (WebQueryResult wqrHG:listHealthGroup) {
        			String code =  ""+wqrHG.get1() ;
        			List<VocExtDispHealthGroup> listPlan = theManager.createQuery("from VocExtDispHealthGroup where dispType_id='"+dispType.getId()+"' and code='"+code+"'").getResultList() ;
        			if (listPlan.size()==0) {
        				VocExtDispHealthGroup hg = new VocExtDispHealthGroup() ;
        				hg.setDispType(dispType) ;
        				hg.setCode(code) ;
        				hg.setName(""+wqrHG.get2()) ;
        				theManager.persist(hg) ;
        			}
        		}
        		List<WebQueryResult> listAgeGroupReport = (List<WebQueryResult>)wqr.get4() ;
        		HashMap<String, VocExtDispAgeReportGroup> hashAgeRepGroup = new HashMap<String, VocExtDispAgeReportGroup>();
        		HashMap<String, VocExtDispAgeGroup> hashAgeGroup = new HashMap<String, VocExtDispAgeGroup>();
        		for (WebQueryResult wqrHG:listAgeGroupReport) {
        			String code = ""+wqrHG.get1() ;
        			List<VocExtDispAgeReportGroup> listPlan = theManager.createQuery("from VocExtDispAgeReportGroup where dispType_id='"+dispType.getId()+"' and code='"+code+"'").getResultList() ;
        			if (listPlan.size()==0) {
        				VocExtDispAgeReportGroup hg = new VocExtDispAgeReportGroup() ;
        				hg.setDispType(dispType) ;
        				hg.setCode(code) ;
        				hg.setName(""+wqrHG.get2()) ;
        				theManager.persist(hg) ;
        				hashAgeRepGroup.put(code, hg) ;
        			} else {
        				hashAgeRepGroup.put(code, listPlan.get(0)) ;
        			}
        		}
        		List<WebQueryResult> listAgeGroup = (List<WebQueryResult>)wqr.get5() ;
        		if (aClear) {
        			List<VocExtDispAgeGroup> listPlans = theManager.createQuery("from VocExtDispAgeGroup where dispType_id='"+dispType.getId()+"'").getResultList() ;
            		for (VocExtDispAgeGroup vedag:listPlans ) {
            			vedag.setIsArchival(true);
            		}
        		}
        		
        		for (WebQueryResult wqrHG:listAgeGroup) {
        			String code=""+wqrHG.get1() ;
        			List<VocExtDispAgeGroup> listPlan = theManager.createQuery("from VocExtDispAgeGroup where dispType_id='"+dispType.getId()+"' and code='"+code+"'").getResultList() ;
        			if (listPlan.size()==0) {
        				VocExtDispAgeGroup hg = new VocExtDispAgeGroup() ;
        				hg.setDispType(dispType) ;
        				hg.setCode(code) ;
        				hg.setName(""+wqrHG.get2()) ;
        				hg.setReportGroup(hashAgeRepGroup.get(""+wqrHG.get3())) ;
        				hg.setIsArchival(false);
        				theManager.persist(hg) ;
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
        			List<Object[]> listPlan = theManager
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
        			
        			if (listPlan.size()==0) {
        				VocSex vs = findSex(codeSex) ;
        				ExtDispPlanService edps = new ExtDispPlanService() ;
        				edps.setPlan(plan) ;
        				edps.setAgeGroup(hashAgeGroup.get(codeAge)) ;
        				edps.setSex(vs) ;
        				edps.setServiceType(theHashService.get(codeService)) ;
        				theManager.persist(edps) ;
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
    	List<VocExtDispService> listService = theManager.createQuery("from VocExtDispService").getResultList() ;
    	List<VocExtDispRisk> listRisk = theManager.createQuery("from VocExtDispRisk").getResultList() ;
    	Element servicesEl = xmlDoc.newElement(root, "services", null);
    	for (VocExtDispService serv : listService) {
    		Element el = xmlDoc.newElement(servicesEl, "service", null);
    		xmlDoc.newAttribute(el, "code", serv.getCode());
    		xmlDoc.newAttribute(el, "isVisit", serv.getIsVisit()!=null && serv.getIsVisit()?"1":"0");
    		xmlDoc.newAttribute(el, "name", serv.getName());
    		List<Object> wf = theManager.createNativeQuery("select list(code) from VocExtDispServiceFunction where name='"+serv.getCode()+"' and code is not null and code!=''").getResultList() ;
    		if (!wf.isEmpty()) {
    			String wfc = (""+wf.get(0)).replaceAll(" ", "") ;
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
    		VocExtDisp ved = theManager.find(VocExtDisp.class, vedId) ;
    		List<VocExtDispAgeReportGroup> listAgeReportGroup = theManager.createQuery("from VocExtDispAgeReportGroup where dispType=:dispType").setParameter("dispType", ved).getResultList() ;
    		List<VocExtDispAgeGroup> listAgeGroup = theManager.createQuery("from VocExtDispAgeGroup where dispType=:dispType and (isArchival is null or isArchival='0')").setParameter("dispType", ved).getResultList() ;
    		List<VocExtDispHealthGroup> listHealthGroup = theManager.createQuery("from VocExtDispHealthGroup where dispType=:dispType").setParameter("dispType", ved).getResultList() ;
    		
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
    		StringBuilder sqlPlan = new StringBuilder() ;
    		sqlPlan.append("select veds.code as vedscode,vs.omcCode as vsomcCode,vedag.code as vedagcode")
				.append(" from ExtDispPlanService edps")
				.append(" left join ExtDispPlan edp on edp.id=edps.plan_id")
				.append(" left join VocSex vs on vs.id=edps.sex_id")
				.append(" left join VocExtDispService veds on veds.id=edps.serviceType_id")
				.append(" left join VocExtDispAgeGroup vedag on vedag.id=edps.ageGroup_id")
				.append(" where edp.dispType_id='").append(ved.getId()).append("' and (isArchival is null or isArchival='0')")
				.append(" group by veds.code,vs.omcCode,vedag.code")
				.append(" order by veds.code") ;
    		List<Object[]> listPlan = theManager.createNativeQuery(sqlPlan.toString()).getResultList() ;
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
		VocSex vs = theHashSex.get(aCode) ;
		if (vs==null) {
			List<VocSex> list = theManager.createQuery("from VocSex where omcCode='"+aCode+"'").getResultList() ;
			if (list.size()>0) {
				vs = list.get(0) ;
				theHashSex.put(aCode,vs) ; 
			}
		}
		return vs ;
	}
	private @PersistenceContext EntityManager theManager ;
	private final EntityHelper theEntityHelper = EntityHelper.getInstance();
	@Resource SessionContext theContext ;
    private @EJB ILocalMonitorService theMonitorService ;
    private HashMap<String, VocExtDispService> theHashService = new HashMap<String, VocExtDispService>();
    private HashMap<String, VocSex> theHashSex = new HashMap<String, VocSex>();
}
