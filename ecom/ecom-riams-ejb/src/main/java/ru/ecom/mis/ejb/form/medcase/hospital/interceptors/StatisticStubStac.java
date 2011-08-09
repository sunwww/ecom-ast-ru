package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.StatisticStubExist;
import ru.ecom.mis.ejb.domain.medcase.StatisticStubNew;
import ru.ecom.mis.ejb.domain.medcase.StatisticStubRestored;
import ru.nuzmsh.util.StringUtil;

/**
 * Стат карта
 * @author stkacheva
 * Роли:
 *   /Policy/Mis/MedCase/Stac/Ssl/Admission/AlwaysCreateStatCardNumber  - всегда создавать номер стат карты
 *   /Policy/Mis/MedCase/Stac/Ssl/Admission/CreateHour                               - можно создавать случаи в течении 1 дня
 *   /Policy/Mis/MedCase/Stac/Ssl/Admission/EditHour                                    - можно редактировать случаи в течении 1 дня
 *   /Policy/Mis/MedCase/Stac/Ssl/Admission/CreateStatCardNumberByHand  - можно ли создавать номер стат. карты пользователю
 *   /Policy/Mis/MedCase/Stac/Ssl/Admission/ChangeStatCardNumber            - можно ли изменять номер стат. карты пользователю
 *   /Policy/Mis/MedCase/Stac/Ssl/Admission/AllowRestoredStatCard              - использовать восстановленные номера стат.карт
 *   /Policy/Mis/MedCase/Stac/Ssl/Discharge/OnlyCurrentDay                        - выписку можно осуществлять только на текущую дату  
 *   
 */
public class StatisticStubStac {
	
    private final static Logger LOG = Logger.getLogger(StatisticStubStac.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
    private final static String theDiscargeOnlyCurrentDay = "/Policy/Mis/MedCase/Stac/Ssl/Discharge/OnlyCurrentDay" ;
	public StatisticStubStac(HospitalMedCase aMedCase, SessionContext aContext, EntityManager aManager, String aRolesAlwaysStatCardNumber) {
		setRolesAlwaysStatNumber(aRolesAlwaysStatCardNumber);
		setMedCase(aMedCase);
		setEntityManager(aManager);
		setContext(aContext);
		if (aMedCase!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(aMedCase.getDateStart());
			setYear(Long.valueOf(calendar.get(Calendar.YEAR)));
		} else {
			setYear(-1);
		}
		if (CAN_DEBUG) LOG.debug("Создание StatisticStubStac...");
		if (CAN_DEBUG) LOG.debug(new StringBuffer().append("theMedCaseId=").append(theMedCase.getId()).toString());
		if (CAN_DEBUG) LOG.debug(new StringBuffer().append("theYear=").append(theYear).toString());
	}
	/**
	 * Если ли ограничение на выписку
	 */
	public static boolean isDischargeOnlyCurrentDay(SessionContext aContext) {
		if(aContext.isCallerInRole(theDiscargeOnlyCurrentDay)) {
			return true ;
		}
		return false ;
	}
    /** Нужно ли создавать новый номер стат. карты
     */
    public boolean isStatCardNumberMustCreate() {
    	boolean ret ;
    	if (!theContext.isCallerInRole(theRolesAlwaysStatCardNumber)) {
    		ret = StringUtil.isNullOrEmpty(theMedCase.getStatCardNumber())
                ||   ( StringUtil.isNullOrEmpty(theMedCase.getStatCardNumber()) && theContext.isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/AlwaysCreateStatCardNumber"))
                ||   ( StringUtil.isNullOrEmpty(theMedCase.getStatCardNumber()) && theMedCase.getDeniedHospitalizating()==null	&& (theMedCase.getIsAmbulanseDialis()|| !theMedCase.getAmbulanceTreatment())) ;
    	} else {
    		ret = true;
    	}
        System.out.println("return isStatCardNumberMustCreate = " + ret);
        return ret ;
    }
    public boolean isStatCardMustDelete() {
    	boolean ret ;
    	if (!theContext.isCallerInRole(theRolesAlwaysStatCardNumber)) {
    		ret = theMedCase.getDeniedHospitalizating()!=null && theMedCase.getStatisticStub()!=null ;
    	} else {
    		ret = false;
    	}
        System.out.println("return isStatCardNumberMustDelete = " + ret);
        return ret ;
    }
    
    /**
     * Изменение номера стат карты
     *
     * @param aSlsId             ид CЛС
     * @param aNewStatCardNumber новый  номер стат. карты
     * @return новый  номер стат. карты
     * @ejb.permission role-name = "/Policy/Mis/MedCase/Stac/Ssl/Admission/ChangeStatCardNumber"
     */
    public static boolean changeStatCardNumber(long aSlsId, String aNewStatCardNumber, String aRoleChange,
    		EntityManager aManager, SessionContext aContext) throws EJBException {
    	//if (!aContext.isCallerInRole(aRoleChange)) {
        	HospitalMedCase medCase = aManager.find(HospitalMedCase.class, aSlsId);
            if (medCase == null) {
                throw new IllegalArgumentException("Нет ССЛ с ИД " + aSlsId);
            }
            // если плановая и отказ в госпитализации, то можно убрать номер стат карты
            if (StringUtil.isNullOrEmpty(aNewStatCardNumber) && medCase.getStatisticStub()!=null && !medCase.getEmergency() && (medCase.getDeniedHospitalizating())!=null) {
            	StatisticStubRestored restored = new StatisticStubRestored();
            	aManager.persist(restored);
            	aManager.refresh(restored);
            	restored.setCode(medCase.getStatisticStub().getCode());
            	restored.setYear(medCase.getStatisticStub().getYear());
                medCase.getStatisticStub().setCode(medCase.getStatisticStub().getCode() + "/DELETED");
                return true;
            } else {
                if (StringUtil.isNullOrEmpty(aNewStatCardNumber)) {
                    throw new IllegalArgumentException("Нет нового номера стат карты.");
                }

                Calendar cal = Calendar.getInstance();
                cal.setTime(medCase.getDateStart());

                try {
                	
                    if (StatisticStubStac.isStatCardNumberExists(aNewStatCardNumber, cal.get(Calendar.YEAR), aManager)) {
                        throw new IllegalArgumentException("Номер стат. карты " + aNewStatCardNumber + " уже используется");
                    } else {
                    	if (medCase.getStatisticStub()!=null) {
                    		StatisticStubRestored restorednum = isStatCardNumberRestored(medCase.getStatisticStub().getCode(), medCase.getStatisticStub().getYear(), aManager) ; 
                    		if (restorednum==null) {
                            	StatisticStubRestored restored = new StatisticStubRestored();
                            	aManager.persist(restored);
                            	aManager.refresh(restored);
                            	restored.setCode(medCase.getStatisticStub().getCode());
                            	restored.setYear(medCase.getStatisticStub().getYear());
                    		}
                        } else {
                        	
                        	StatisticStubExist exist = new StatisticStubExist();
                        	exist.setMedCase(medCase);
                        	exist.setYear(Long.valueOf(cal.get(Calendar.YEAR)));
                        	exist.setCode(aNewStatCardNumber) ;
                        	aManager.persist(exist);
                        	aManager.refresh(exist);
                        	medCase.setStatisticStub(exist) ;
                        }
                    	StatisticStubRestored restorednum = isStatCardNumberRestored(aNewStatCardNumber, Long.valueOf(cal.get(Calendar.YEAR)), aManager);
                    	if (restorednum!=null) aManager.remove(restorednum);
                        medCase.getStatisticStub().setCode(aNewStatCardNumber);
                    }
                    return true;
                } catch (Exception e) {
                    throw new IllegalStateException(new StringBuffer().append("Ошибка изменения номера стат. карты: ").append(e.getMessage()).toString());
                }
            }
    	//}
         //  return false ; 

    }
    
    
    /**
     * * Есть ли номер такой карты по году
     *
     * @param aStatCardNumber номер стат. карты
     * @param aYear           год
     * @return true - если есть
     */
    public boolean isStatCardNumberExists(String aStatCardNumber, int aYear) {
        //private boolean isStatCardNumberExists(String aStatCardNumber, int aYear, boolean aIsPlan) {
//        return ret;
    	return isStatCardNumberExists(aStatCardNumber, Long.valueOf(aYear));
    }
    @SuppressWarnings("unchecked")
	public boolean isStatCardNumberExists(String aStatCardNumber, Long aYear) {
    	List<StatisticStubExist> states = theEntityManager.createQuery("FROM StatisticStub WHERE year = :year AND code = :number AND DTYPE = 'StatisticStubExist'")
		.setParameter("number",aStatCardNumber)
		.setParameter("year", aYear).setMaxResults(1).getResultList();
    	boolean ret =(states==null||states.isEmpty())? false:true ; 
    LOG.debug(new StringBuffer().append("Exist: ")
    		.append(aStatCardNumber).append(" =").append(ret));
    return ret;
    	
    }
    
    /**
     * * Есть ли номер такой карты по году
     *
     * @param aStatCardNumber номер стат. карты
     * @param aYear           год
     * @return true - если есть
     */
    @SuppressWarnings("unchecked")
	public static boolean isStatCardNumberExists(String aStatCardNumber, int aYear, EntityManager aManager) {
        //private boolean isStatCardNumberExists(String aStatCardNumber, int aYear, boolean aIsPlan) {
    	List<StatisticStubExist> states = aManager.createQuery("FROM StatisticStub WHERE year = :year AND code = :number AND DTYPE = 'StatisticStubExist'")
    		.setParameter("number",aStatCardNumber)
    		.setParameter("year", Long.valueOf(aYear)).setMaxResults(1).getResultList();
        return (states==null||states.isEmpty())? false:true;
    }
	
    /**
     * * Есть ли восстановленный номер такой карты по году
     *
     * @param aStatCardNumber номер стат. карты
     * @param aYear           год
     * @return true - если есть
     */
    @SuppressWarnings("unchecked")
	public static StatisticStubRestored isStatCardNumberRestored(String aStatCardNumber, Long aYear, EntityManager aManager) {
        //private boolean isStatCardNumberExists(String aStatCardNumber, int aYear, boolean aIsPlan) {
    	List<StatisticStubRestored> states = aManager.createQuery("FROM StatisticStub WHERE year = :year AND code = :number AND DTYPE = 'StatisticStubRestored'")
    		.setParameter("number",aStatCardNumber)
    		.setParameter("year", aYear).setMaxResults(1).getResultList();
        return (states==null || states.isEmpty())? null : states.get(0);
    }
    /**
     * Нужно ли убирать номер стат.карты
     *
     * Нужно убирать при следующем условии:
     * Отказ в госпитализации и/или амбулаторное лечение (но не диализ)
     * , а также наличие номера
     * стат карты.
     */
    public boolean isStacStardMustRemove() {
        return !theContext.isCallerInRole("/Policy/Stac/Admission/AlwaysCreateStatCardNumber")
         && (theMedCase.getStatisticStub() != null
            && ( theMedCase.getDeniedHospitalizating() != null
                || (theMedCase.getAmbulanceTreatment()!=null && theMedCase.getAmbulanceTreatment().equals(Boolean.TRUE))
               )) ;

    }
    
    /**
     * Убираем номер стат. карты
     */
    public void removeStatCardNumber() throws EJBException {
        if (!isStacStardMustRemove()) {
            return ;
        }

        if (theMedCase.getIsAmbulanseDialis()) {
           LOG.debug(" Удаление номер стат. карты из диализа " + theMedCase);
           theEntityManager.remove(theMedCase.getStatisticStub());
           theMedCase.setStatisticStub(null);
        	//StatisticStubExist stubexist = theMedCase.getStatisticStub() ;
        	/*
        	StatisticStubRestored stubrestored = new StatisticStubRestored();
        	stubrestored.setCode(stubexist.getCode());
        	stubrestored.setYear(stubexist.getYear());
        	theEntityManager.persist(stubrestored);
        	theEntityManager.refresh(stubrestored);
        	*/
            //theMedCase.setStatisticStub(null);
        } else {
        	StatisticStubExist stubexist = theMedCase.getStatisticStub() ;
        	StatisticStubRestored stubrestored = new StatisticStubRestored();
        	stubrestored.setCode(stubexist.getCode());
        	stubrestored.setYear(stubexist.getYear());
        	theMedCase.setStatisticStub(null);
        	theEntityManager.persist(stubrestored);
        	theEntityManager.refresh(stubrestored);
        	theEntityManager.remove(stubexist);
        }
            //LOG.debug(" Удаление номер стат. карты " + getStatCardNumber());
            /*String statCardNumber = theMedCase.getStatCardNumber();
            StringTokenizer st = new StringTokenizer(statCardNumber, "-");
            //long id = Long.parseLong(st.nextToken());
            if(!st.hasMoreTokens()) {
                //theMedCase.setStatCardNumber(null);
            } else {
                //LOG.debug("id = " + id);
                //long year = getYear() ;
                //theMedCase.setStatCardNumber(null);
                //StatisticStubRestored stubres = new StatisticStubRestored();
                //stubres.setYear(year);
                //stubres.setCode(new StringBuffer().append(id).toString());
                /*ILogic logic = null;
                try {
                    logic = LogicPool.getLogic(theEntityContext());
                        LOG.debug("year = " + year);
                        logic.setString("StacSTATREPN", id + "", "Kod", "1", "%God:" + year);
                } catch (Exception e) {
                    LOG.debug("e = " + e, e);
                    throw new EJBException("Ошибка получения последнего номера стат. карты" + e);
                } finally {
                    LogicPool.closeEjb(logic);
                }*/
            
        
    }



    /**
     * Первый попавшийся восстановленный номер стат. карты
     *
     * @return -1 если нет восстановленных номеров
     */
    @SuppressWarnings("unchecked")
	private StatisticStubRestored getRestoredStatCard() {
    	List<StatisticStubRestored> states = null ;
    	if (theContext.isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/AllowRestoredStatCard")) {
    		states = theEntityManager.createQuery("from StatisticStub where year = :year and DTYPE='StatisticStubRestored'")
    			.setParameter("year",getYear()).setMaxResults(1).getResultList();
    	}
    	return (states!=null && !states.isEmpty()) ? states.get(0) : null ; 
    }
    
    private String prepareStatCardNumber(long aStatCardId) {
        String yy = new SimpleDateFormat("yy").format(theMedCase.getDateStart());
        return aStatCardId + "-" + yy;
    }

    /**
     * Создание следующего порядкового номера статкарты и сохранение
     * Вызывать после сохранения всех полей
     */
    @SuppressWarnings("unchecked")
	public void createStacCardNumber() throws EJBException {
        if (!isStatCardNumberMustCreate()) {
            throw new EJBException("Номер стат. карты создавать не нужно");
        }

        String nextStatCardNumber;
        if (theMedCase.getIsAmbulanseDialis()) {
            // сохранение
            nextStatCardNumber = new StringBuffer().append(theMedCase.getPatient().getId()).toString();
            StatisticStubExist statstub = new StatisticStubExist();
            statstub.setCode(nextStatCardNumber);
            statstub.setYear(theYear);
            statstub.setMedCase(theMedCase);
            theMedCase.setStatisticStub(statstub);
        } else {
            Long year = getYear() ;
            StatisticStubRestored restored =null ;
            boolean next ;
            do {
            	StatisticStubRestored restoredb = getRestoredStatCard() ;
            	if (restoredb==null) break ;
                boolean existnum = isStatCardNumberExists(restoredb.getCode(), restoredb.getYear());
                next = false ;
                if (existnum) {
                	theEntityManager.remove(restoredb);
                    next =  true;
                    
                } else {
                	next = false ;
                	restored = restoredb ;
                	//break;
                }
            }while(next); 
            if (restored!=null) {
            	//long restoredId=Long.valueOf(restored.getCode()) ;
                if (CAN_DEBUG) LOG.debug("Берем номер из восстановленных");
            	// берем номер из восстановленных
            	//TODO доделать удаление из восстановленных номеров
                StatisticStubExist stubexist = new StatisticStubExist();
                theEntityManager.persist(stubexist);
                theEntityManager.refresh(stubexist);
                stubexist.setCode(restored.getCode());
                stubexist.setYear(year);
                stubexist.setMedCase(theMedCase);
                theEntityManager.remove(restored);
            	theMedCase.setStatisticStub(stubexist);
            } else {
            	//создаем новый номер
                if (CAN_DEBUG) LOG.debug("Создание новый номер");
            	List<StatisticStubNew> rows = theEntityManager.createQuery("from StatisticStub where year=:year and DTYPE='StatisticStubNew'")
            		.setParameter("year", year).setMaxResults(1).getResultList();
            	long nextId ;
        		//int year_int = year.intValue() ;
        		StatisticStubNew stubnew ;
            	if (rows!=null && !rows.isEmpty()) {
            		stubnew = rows.get(0);
            		nextId = Long.valueOf(stubnew.getCode()) ;
            	} else {
            		nextId = Long.valueOf(1) ;
            		stubnew = new StatisticStubNew();
            		theEntityManager.persist(stubnew);
            		theEntityManager.refresh(stubnew);
            		stubnew.setYear(year);
            	}
        		stubnew.setCode(""+(nextId+1));
        		nextStatCardNumber = prepareStatCardNumber(nextId);
            	if(CAN_DEBUG)LOG.debug("Просмотр");
                while (!isStatCardNumberFree(nextStatCardNumber)) {
                    nextId++;
            		stubnew.setCode(""+(nextId+1));
                    nextStatCardNumber = prepareStatCardNumber(nextId) ;
                }
            	if(CAN_DEBUG)LOG.debug("Запись нового номера стат.карты = "+nextStatCardNumber);
                StatisticStubExist stubexist = new StatisticStubExist();
                theEntityManager.persist(stubexist);
                theEntityManager.refresh(stubexist);
                stubexist.setCode(nextStatCardNumber);
                stubexist.setYear(year);
                stubexist.setMedCase(theMedCase);
            	//theMedCase.setStatCardNumber(nextStatCardNumber);
                theMedCase.setStatisticStub(stubexist);
            	
            }
        	/*
        	 
        	 ILogic logic = null;
            try {
                long year = getAdmissionYear();
                logic = LogicPool.getLogic(getEntityContext());

                synchronized (SYNC_CREATE_STAT_CARD_NUMBER) {
                    long restoredId = getRestoredStatCardId();
                    if (CAN_DEBUG) LOG.debug("restoredId = " + restoredId);
                    if (restoredId != -1) { // берем номер из восстановленных
                        logic.delete("StacSTATREPN", restoredId+"", "%God:"+getAdmissionYear()) ;

                        setStatCardNumber(prepareStatCardNumber(restoredId));

                    } else { // создаем новый номер
                        String last = logic.getLastId("StacSTATN", "%God:" + year);
                        LOG.debug("last = " + last);
//                        System.out.println("last = " + last);
                        long lastId = last != null ? Long.parseLong(last) : 0;
                        long nextId = lastId + 1;

                        nextStatCardNumber = prepareStatCardNumber(nextId) ;
                        logic.setString("StacSTATN", nextId + "", "Kod", "1", "%God:" + year);
                        // сохранение
                        while (!isStatCardNumberFree(nextStatCardNumber)) {
                            logic.setString("StacSTATN", nextId + "", "Kod", "1", "%God:" + year);
                            nextId++;
                            nextStatCardNumber = prepareStatCardNumber(nextId) ;
                        }
                        setStatCardNumber(nextStatCardNumber);
                    }
                }
            } catch (Exception e) {
                LOG.debug("e = " + e, e);
                throw new EJBException("Ошибка получения последнего номера стат. карты" + e);
            } finally {
                LogicPool.closeEjb(logic);
            }
            */
        }
    }

    // Свободный номер ли?
	@SuppressWarnings("unchecked")
	private boolean isStatCardNumberFree(String aStatCardNumber)  {
        //Collection col = StacSLSUtil.getLocalHome().findByKowQuery("SLSidkart:" + aStatCardNumber);
    	//return col.isEmpty();
    	List<StatisticStubExist> states = theEntityManager.createQuery("from StatisticStub where code = :number and DTYPE='StatisticStubExist'").setParameter("number",aStatCardNumber).getResultList();       
    	return (states==null || states.isEmpty());
    }



	/** Случай стационарного лечения */
	public HospitalMedCase getMedCase() {return theMedCase;}
	public void setMedCase(HospitalMedCase aMedCase) {theMedCase = aMedCase;}

	/** Session context */
	public SessionContext getContext() {return theContext;	}
	public void setContext(SessionContext aContext) {theContext = aContext;}

	/** Год создания случая стационарного лечения */
	public long getYear() {return theYear;}
	public void setYear(long aYear) {theYear = aYear;}

	/** EntityManager */
	public EntityManager getEntityManager() {return theEntityManager;}
	public void setEntityManager(EntityManager aEntityManager) {theEntityManager = aEntityManager;	}
	

	/** Роль: всегда создавать номер стат.карты */
	public String getRolesAlwaysStatNumber() {
		return theRolesAlwaysStatCardNumber;
	}

	public void setRolesAlwaysStatNumber(String aRolesAlwaysStatCardNumber) {
		theRolesAlwaysStatCardNumber = aRolesAlwaysStatCardNumber;
	}

	/** Роль: всегда создавать номер стат.карты */
	private String theRolesAlwaysStatCardNumber;
	/** EntityContext */
	private EntityManager theEntityManager;
	/** Год создания случая стационарного лечения */
	private long theYear;
	/** Session context */
	private SessionContext theContext;
	/** Случай стационарного лечения */
	private HospitalMedCase theMedCase;
}
