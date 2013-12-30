package ru.ecom.mis.ejb.service.addresspoint;

import java.io.File;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.address.ejb.service.AddressPointCheck;
import ru.ecom.address.ejb.service.AddressPointCheckHelper;
import ru.ecom.address.ejb.service.IAddressService;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.service.SecUserServiceBean;
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressPoint;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressText;
import ru.ecom.mis.ejb.service.patient.QueryClauseBuilder;
import ru.ecom.report.util.XmlDocument;
import ru.nuzmsh.util.StringUtil;

/**
 * @author  azviagin
 */
@Stateless
@Local(IAddressPointService.class)
@Remote(IAddressPointService.class)
public class AddressPointServiceBean implements IAddressPointService {

    private final static Logger LOG = Logger.getLogger(AddressPointServiceBean.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();

    public String export(boolean aLpuCheck, Long aLpu, String aDateFrom, String aDateTo, String aNReestr, String aNPackage) throws ParserConfigurationException, TransformerException {
    	EjbEcomConfig config = EjbEcomConfig.getInstance() ;
    	Map<SecPolicy, String> hash = new HashMap<SecPolicy,String>() ;
    	String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
    	workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
    	String filename = "P"+aNReestr+"_"+aDateTo+aNPackage+".xml" ;
    	File outFile = new File(workDir+"/"+filename) ;
    	XmlDocument xmlDoc = new XmlDocument() ;
    	Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select p.id,p.lastname,p.firstname,p.middlename,to_char(p.birthday,'yyyy-mm-dd') as birthday") ;
    	sql.append(" ,p.snils, vic.omcCode as passportType, p.passportSeries,p.passportNumber,p.commonNumber") ;
    	sql.append(" ,case when lp.id is null then '1' else '2' end as spprik") ;
    	sql.append(" ,case when lp.id is null then '2013-01-01' else '2013-04-01' end as tprik") ;
    	sql.append(" from Patient p") ;
    	sql.append(" left join LpuAttachedByDepartment lp on lp.patient_id=p.id") ;
    	sql.append(" left join VocIdentityCard vic on vic.id=p.passportType_id") ;
    	sql.append(" where ") ;
    	if (aLpuCheck) sql.append(" (p.lpu_id='").append(aLpu).append("' or lp.lpu_id='").append(aLpu).append("') and ") ;
    	sql.append(" (p.noActuality='0' or p.noActuality is null)");
    	sql.append(" group by p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.snils, vic.omcCode,p.passportSeries,p.passportNumber,p.commonNumber,lp.id") ;
    	sql.append(" order by p.lastname,p.firstname,p.middlename,p.birthday") ;
    	
    	List<Object[]> listPat = theManager.createNativeQuery(sql.toString()).setMaxResults(50000).getResultList() ;
    	Element title = xmlDoc.newElement(root, "ZGLV", null);
    	xmlDoc.newElement(title, "PERIOD", aDateTo.substring(2,4));
    	xmlDoc.newElement(title, "N_REESTR", aNReestr);
    	xmlDoc.newElement(title, "FILENAME", "P"+aNReestr+"_"+aDateTo+aNPackage);
    	int i=0 ;
    	for (Object[] pat:listPat) {
    		Element zap = xmlDoc.newElement(root, "ZAP", null);
    		xmlDoc.newElement(zap, "IDCASE", getStringValue(++i)) ;
    		xmlDoc.newElement(zap, "FAM", getStringValue(pat[1])) ;
    		xmlDoc.newElement(zap, "IM", getStringValue(pat[2])) ;
    		xmlDoc.newElement(zap, "OT", getStringValue(pat[3])) ;
    		xmlDoc.newElement(zap, "DR", getStringValue(pat[4])) ;
    		xmlDoc.newElement(zap, "SNILS", getStringValue(pat[5])) ;
    		xmlDoc.newElement(zap, "DOCTYPE", getStringValue(pat[6])) ;
    		xmlDoc.newElement(zap, "DOCSER", getStringValue(pat[7])) ;
    		xmlDoc.newElement(zap, "DOCNUM", getStringValue(pat[8])) ;
    		xmlDoc.newElement(zap, "RZ", getStringValue(pat[9])) ;
    		xmlDoc.newElement(zap, "SP_PRIK", getStringValue(pat[10])) ; // 1-территориал, 2-заявление
    		xmlDoc.newElement(zap, "T_PRIK", getStringValue(1)) ; // 1-прикрепление, 2-открепление
    		xmlDoc.newElement(zap, "DATE_1", getStringValue(pat[11])) ;
    		xmlDoc.newElement(zap, "REFREASON", "") ;
    	}
    	xmlDoc.saveDocument(outFile) ;
    	return filename;
    }
    public String exportNoAddress(boolean aLpuCheck, Long aLpu, String aDateFrom, String aDateTo, String aNReestr, String aNPackage) throws ParserConfigurationException, TransformerException {
        EjbEcomConfig config = EjbEcomConfig.getInstance() ;
        Map<SecPolicy, String> hash = new HashMap<SecPolicy,String>() ;
        String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
        workDir = config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf") ;
        String filename = "P"+aNReestr+"_"+aDateTo+aNPackage+".xml" ;
        File outFile = new File(workDir+"/"+filename) ;
        XmlDocument xmlDoc = new XmlDocument() ;
        Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
        StringBuilder sql = new StringBuilder() ;
        sql.append("select p.id,p.lastname,p.firstname,p.middlename,to_char(p.birthday,'yyyy-mm-dd') as birthday") ;
        sql.append(" ,p.snils, vic.omcCode as passportType, p.passportSeries,p.passportNumber,p.commonNumber") ;
        sql.append(" ,case when lp.id is null then '1' else '2' end as spprik") ;
        sql.append(" ,case when lp.id is null then '2013-01-01' else '2013-04-01' end as tprik") ;
        sql.append(" from Patient p") ;
        sql.append(" left join LpuAttachedByDepartment lp on lp.patient_id=p.id") ;
        sql.append(" left join VocIdentityCard vic on vic.id=p.passportType_id") ;
        sql.append(" where ") ;
        sql.append(" p.address_addressid is null and ") ;
        sql.append(" (p.noActuality='0' or p.noActuality is null)");
        sql.append(" group by p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.snils, vic.omcCode,p.passportSeries,p.passportNumber,p.commonNumber,lp.id") ;
        sql.append(" order by p.lastname,p.firstname,p.middlename,p.birthday") ;
        
        List<Object[]> listPat = theManager.createNativeQuery(sql.toString()).setMaxResults(50000).getResultList() ;
		Element title = xmlDoc.newElement(root, "ZGLV", null);
		xmlDoc.newElement(title, "PERIOD", aDateTo.substring(2,4));
		xmlDoc.newElement(title, "N_REESTR", aNReestr);
		xmlDoc.newElement(title, "FILENAME", "P_without_address"+aNReestr+"_"+aDateTo+aNPackage);
		int i=0 ;
		for (Object[] pat:listPat) {
			Element zap = xmlDoc.newElement(root, "ZAP", null);
			xmlDoc.newElement(zap, "IDCASE", getStringValue(++i)) ;
			xmlDoc.newElement(zap, "FAM", getStringValue(pat[1])) ;
			xmlDoc.newElement(zap, "IM", getStringValue(pat[2])) ;
			xmlDoc.newElement(zap, "OT", getStringValue(pat[3])) ;
			xmlDoc.newElement(zap, "DR", getStringValue(pat[4])) ;
			xmlDoc.newElement(zap, "SNILS", getStringValue(pat[5])) ;
			xmlDoc.newElement(zap, "DOCTYPE", getStringValue(pat[6])) ;
			xmlDoc.newElement(zap, "DOCSER", getStringValue(pat[7])) ;
			xmlDoc.newElement(zap, "DOCNUM", getStringValue(pat[8])) ;
			xmlDoc.newElement(zap, "RZ", getStringValue(pat[9])) ;
			xmlDoc.newElement(zap, "SP_PRIK", getStringValue(pat[10])) ; // 1-территориал, 2-заявление
			xmlDoc.newElement(zap, "T_PRIK", getStringValue(1)) ; // 1-прикрепление, 2-открепление
			xmlDoc.newElement(zap, "DATE_1", getStringValue(pat[11])) ;
			xmlDoc.newElement(zap, "REFREASON", "") ;
		}
		xmlDoc.saveDocument(outFile) ;
		return filename;
	}
    private String getStringValue(Object aValue) {
    	return aValue!=null?""+aValue:"" ;
    }
    public void onRemove(LpuAreaAddressText aLpuAreaAddressText) {
        EntityManager manager = theManager ; //theFactory.createEntityManager() ;
        try {
        int count = manager.createQuery("delete from LpuAreaAddressPoint where lpuAreaAddressText = :lpuAreaAddressText")
                .setParameter("lpuAreaAddressText", aLpuAreaAddressText)
                .executeUpdate();
        if (CAN_DEBUG) LOG.debug("count deleted LpuAreaAddressPoint = " + count);

        // у пациента убрать по адресу участка привязку к ЛПУ и участку
        count = manager.createQuery("update Patient set lpu = null, lpuArea = null, lpuAreaAddressText = null where lpuAreaAddressText = :lpuAreaAddressText")
                .setParameter("lpuAreaAddressText", aLpuAreaAddressText)
                .executeUpdate();
        if (CAN_DEBUG) LOG.debug("count updated Patient = " + count);
        } finally {
            //manager.close() ;
        }
    }
    public void onPersist(LpuAreaAddressText aLpuAreaAddressText) {
        if (CAN_DEBUG) LOG.debug("aLpuAreaAddressText = " + aLpuAreaAddressText.getAddress());
        Address address = aLpuAreaAddressText.getAddress();
        EntityManager manager = theManager ; 
        // весь поселок
        if(address.getDomen()==5) {
            Query query = manager.createQuery("from Address where parent = :parent").setParameter("parent",address) ;
            Iterator<Address> iterator = QueryIteratorUtil.iterate(Address.class, query) ;
            // перебираем все улицы и прикрепляем
            while (iterator.hasNext()) {
                Address child = iterator.next();
                LpuAreaAddressPoint point = new LpuAreaAddressPoint();
                point.setLpuAreaAddressText(aLpuAreaAddressText);
                point.setAddress(child);
                manager.persist(point);
                updatePatients(aLpuAreaAddressText, child, null, null, null);
                manager.flush() ;
                manager.clear() ;
            }
        } else {
            List<AddressPointCheck> checks = thePointCheckHelper.parsePoints(aLpuAreaAddressText.getAddressString());
            // нет домов, прикрепляем по всех улице
            if (checks == null || checks.isEmpty()) {
                LpuAreaAddressPoint point = new LpuAreaAddressPoint();
                point.setLpuAreaAddressText(aLpuAreaAddressText);
                point.setAddress(address);
                manager.persist(point);
                updatePatients(aLpuAreaAddressText, address, null, null, null);
            } else {
            	// по домам , корпусам и квартирам
                for (AddressPointCheck check : checks) {
                    LpuAreaAddressPoint point = new LpuAreaAddressPoint();
                    point.setLpuAreaAddressText(aLpuAreaAddressText);
                    point.setAddress(address);
                    point.setHouseNumber(check.getNumber());
                    point.setHouseBuilding(check.getBuilding());
                    point.setFlat(check.getFlat());
                    manager.persist(point);
                    updatePatients(aLpuAreaAddressText, address, check.getNumber(), check.getBuilding(), check.getFlat());
                }
            }
        }
        manager.flush();
        manager.clear() ;
    }

    /**
     * Определение границы для детей
     * @return
     */
    public static Date getChildBoundaryDate() {
    	Calendar cal = Calendar.getInstance() ;
    	cal.setTime(new java.util.Date());
    	cal.add(Calendar.YEAR, -18);
    	java.util.Date utilDate = cal.getTime();
    	return new Date(utilDate.getTime());
    	
    }
    // todo обновить по адресу, дому и корпусу привязку пациентов
    private void updatePatients(LpuAreaAddressText aText, Address aAddress, String aNumber, String aBuilding, String aFlat) {
        if (CAN_DEBUG) LOG.debug("aText.getId() = " + aText.getId());
        LpuArea area = aText.getArea();
        if (CAN_DEBUG) LOG.debug("area.getId() = " + area.getId());
        StringBuilder sb = new StringBuilder(
                "update Patient set lpu = :lpu, lpuArea = :lpuArea, lpuAreaAddressText = :text " +
                " where attachedOmcPolicy_id is null and address = :address ");
        // дети или взрослые для терап. или педиатрического участков
        if(area.isPediatric()) {
        	sb.append(" and birthday > :childBoundaryDate") ;
        } else {
        	sb.append(" and birthday < :childBoundaryDate") ;
        }
        if (!StringUtil.isNullOrEmpty(aNumber)) {
            sb.append(" and houseNumber = :number ") ;
        }
        if(!StringUtil.isNullOrEmpty(aBuilding)) {
            sb.append(" and houseBuilding = :building") ;
        }
        if(!StringUtil.isNullOrEmpty(aFlat)) {
            sb.append(" and flatNumber = :flat") ;
        }

        EntityManager manager = theManager ;
        if (CAN_DEBUG) LOG.debug("sb = " + sb);
        Query query = manager.createQuery(sb.toString());
        if(!StringUtil.isNullOrEmpty(aNumber)) {
            query.setParameter("number", aNumber) ;
        }
        if(!StringUtil.isNullOrEmpty(aBuilding)) {
            query.setParameter("building", aBuilding) ;
        }
        if(!StringUtil.isNullOrEmpty(aFlat)) {
            query.setParameter("flat", aFlat) ;
        }
        query.setParameter("childBoundaryDate", getChildBoundaryDate());
        query.setParameter("address", aAddress) ;
        query.setParameter("lpu", area.getLpu().getId()) ;
        query.setParameter("lpuArea", area.getId()) ;
        query.setParameter("text", aText) ;

        int count = query.executeUpdate() ;
        if (CAN_DEBUG) LOG.debug("updated patients count = " + count);
    }

    @SuppressWarnings("unchecked")
	public void checkExists(long aLpuAreaId, Long aLpuAddressTextId, long aAddress, String aNumber, String aBuilding, String aFlat) {
        if(!StringUtil.isNullOrEmpty(aBuilding) && StringUtil.isNullOrEmpty(aNumber)) throw new IllegalArgumentException("При вводе корпуса необходимо указать номер дома") ;
        QueryClauseBuilder builder = new QueryClauseBuilder();
        builder.addIsNull("houseNumber",aNumber);
        builder.addIsNull("houseBuilding",aBuilding);
        builder.addIsNull("flat",aFlat);
        builder.addNot("lpuAreaAddressText_id",aLpuAddressTextId);
        builder.add("address_AddressId",aAddress);
        EntityManager manager = theManager ;
        Query query = builder.build(manager,"from LpuAreaAddressPoint where","") ;
        List<LpuAreaAddressPoint> list = query.getResultList();
        if(list!=null && !list.isEmpty()) {
            long areaTypeId = manager.find(LpuArea.class, aLpuAreaId).getType().getId() ;
            for (LpuAreaAddressPoint point : list) {
                if(point.getLpuAreaAddressText().getArea().getType().getId()==areaTypeId) {
                    String addressText = theAddressService.getAddressString(aAddress, aNumber, aBuilding, aFlat,null);
                    throw new IllegalStateException("Адрес " + addressText
                            + " уже существует у участка " + point.getLpuAreaAddressText().getArea().getName()
                            + " в ЛПУ " + point.getLpuAreaAddressText().getArea().getLpu().getName()
                            + " по адресу "+point.getLpuAreaAddressText().getAddressString());
                }
            }
        }

        // дом корпус кв
        if(!StringUtil.isNullOrEmpty(aNumber) && !StringUtil.isNullOrEmpty(aBuilding) && !StringUtil.isNullOrEmpty(aFlat)) {
            checkExists(aLpuAreaId, aLpuAddressTextId, aAddress, aNumber, aBuilding, null);
        // дом кв    
        } else if(!StringUtil.isNullOrEmpty(aNumber) && !StringUtil.isNullOrEmpty(aFlat)) {
            checkExists(aLpuAreaId, aLpuAddressTextId, aAddress, aNumber, null, null);
        // дом корпус    
        } else if(!StringUtil.isNullOrEmpty(aNumber) && !StringUtil.isNullOrEmpty(aBuilding)) {
            checkExists(aLpuAreaId, aLpuAddressTextId, aAddress, aNumber, null, null);
        // дом    
        } else if(!StringUtil.isNullOrEmpty(aNumber)) {
            checkExists(aLpuAreaId, aLpuAddressTextId, aAddress, null, null, null);
        }
    }

    // update Patient set lpuArea_id = null
    public void onUpdate(LpuAreaAddressText aLpuAreaAddressText) {
	//theManager.flush() ;
	//theManager.clear() ;
	
        onRemove(aLpuAreaAddressText);
        onPersist(aLpuAreaAddressText);
    }

    public void refresh() {
    	theManager.createQuery("delete from LpuAreaAddressPoint").executeUpdate() ;
    	Iterator<LpuAreaAddressText> texts = QueryIteratorUtil.iterate(LpuAreaAddressText.class, theManager.createQuery("from LpuAreaAddressText")) ;
    	while( texts.hasNext()) {
    		LpuAreaAddressText text = texts.next() ;
    		onUpdate(text);
    	}
    }

    private AddressPointCheckHelper thePointCheckHelper = new AddressPointCheckHelper();
    private @EJB IAddressService theAddressService;
    private @PersistenceContext EntityManager theManager;
//    private @PersistenceUnit EntityManagerFactory theFactory ;
}
