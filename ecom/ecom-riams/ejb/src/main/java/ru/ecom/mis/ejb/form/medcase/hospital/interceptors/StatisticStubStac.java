package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.live.domain.journal.ChangeJournal;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.StatisticStubExist;
import ru.ecom.mis.ejb.domain.medcase.StatisticStubNew;
import ru.ecom.mis.ejb.domain.medcase.StatisticStubRestored;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPigeonHole;
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
	public StatisticStubStac(HospitalMedCase aMedCase, SessionContext aContext, EntityManager aManager) {
		//setRolesAlwaysStatNumber(aRolesAlwaysStatCardNumber);
		setMedCase(aMedCase);
		setEntityManager(aManager);
		VocPigeonHole pigeonHole =aMedCase!=null&&aMedCase.getDepartment()!=null?aMedCase.getDepartment().getPigeonHole():null ;
		setPigeonHole(pigeonHole) ;
		setContext(aContext);
		boolean isEmerPlan = false ;
    	if (pigeonHole!=null && pigeonHole.getIsStatStubEmerPlan()!=null) {
    		isEmerPlan = true ;
    	}
    	theIsEmergAndPlan=isEmerPlan ;
    	theIsEmergency=aMedCase.getEmergency()==null?false:aMedCase.getEmergency().booleanValue() ;
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
    	if (!theContext.isCallerInRole(AlwaysStatCardNumber)) {
    		ret = (theMedCase.getDeniedHospitalizating()==null	
                		&& (theMedCase.getAmbulanceTreatment()==null
                		|| !theMedCase.getAmbulanceTreatment())
                		) ;
    	} else {
    		ret = true;
    	}
        System.out.println("return isStatCardNumberMustCreate = " + ret);
        return ret ;
    }
    public boolean isStatCardMustDelete() {
    	boolean ret ;
    	if (!theContext.isCallerInRole(AlwaysStatCardNumber)) {
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
    public static boolean changeStatCardNumber(long aSlsId, String aNewStatCardNumber,
    		EntityManager aManager, SessionContext aContext) throws EJBException {
    	String oldStatCard="" ;
    	String username = aContext.getCallerPrincipal().toString() ;
    	if (aContext.isCallerInRole(ChangeStatCardNumber)) {
    		
        	HospitalMedCase medCase = aManager.find(HospitalMedCase.class, aSlsId);
            if (medCase == null) {
                throw new IllegalArgumentException("Нет ССЛ с ИД " + aSlsId);
            }
        	oldStatCard = medCase.getStatisticStub()!=null?medCase.getStatisticStub().getCode():"" ;
            
        	// если плановая и отказ в госпитализации, то можно убрать номер стат карты
            if (StringUtil.isNullOrEmpty(aNewStatCardNumber) && medCase.getStatisticStub()!=null && !medCase.getEmergency() && (medCase.getDeniedHospitalizating())!=null) {
            	StatisticStubRestored restored = new StatisticStubRestored();
            	//aManager.refresh(restored);
            	restored.setCode(medCase.getStatisticStub().getCode());
            	restored.setYear(medCase.getStatisticStub().getYear());
            	aManager.persist(restored);
                medCase.getStatisticStub().setCode(medCase.getStatisticStub().getCode() + "/DELETED");
                setChangeJournal(aSlsId, oldStatCard, aNewStatCardNumber, username, aManager);
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
                            	//aManager.refresh(restored);
                            	restored.setCode(medCase.getStatisticStub().getCode());
                            	restored.setYear(medCase.getStatisticStub().getYear());
                            	aManager.persist(restored);
                    		}
                        } else {
                        	
                        	StatisticStubExist exist = new StatisticStubExist();
                        	exist.setMedCase(medCase);
                        	exist.setYear(Long.valueOf(cal.get(Calendar.YEAR)));
                        	exist.setCode(aNewStatCardNumber) ;
                        	aManager.persist(exist);
                        	//aManager.refresh(exist);
                        	medCase.setStatisticStub(exist) ;
                        }
                    	StatisticStubRestored restorednum = isStatCardNumberRestored(aNewStatCardNumber, Long.valueOf(cal.get(Calendar.YEAR)), aManager);
                    	if (restorednum!=null) aManager.remove(restorednum);
                        medCase.getStatisticStub().setCode(aNewStatCardNumber);
                    }
                    setChangeJournal(aSlsId, oldStatCard, aNewStatCardNumber, username, aManager);
                    return true;
                } catch (Exception e) {
                    throw new IllegalStateException(new StringBuffer().append("Ошибка изменения номера стат. карты: ").append(e.getMessage()).toString());
                }
            }
        	
    	} else {
    		throw new IllegalStateException(new StringBuffer().append("Нет прав для изменения номера стат.карты!!!").toString());
    	}
    	
    	
    	
    }
    private static void setChangeJournal(Long aSlsId, String aOldStatCard,String aNewStatCardNumber
    		,String aUsername,EntityManager aManager) {
    	ChangeJournal jour = new ChangeJournal() ;
    	jour.setSerializationAfter(aOldStatCard) ;
    	jour.setSerializationBefore(aNewStatCardNumber) ;
		jour.setLoginName(aUsername) ;
		jour.setClassName(StatisticStubExist.class.getName()) ; 
		jour.setObjectId(String.valueOf(aSlsId)) ;
		
		Date date = new Date() ;
		
		jour.setChangeDate(new java.sql.Date(date.getTime())) ;
		jour.setChangeTime(new java.sql.Time(date.getTime())) ;
		jour.setStatus(Long.valueOf(0)) ;
		aManager.persist(jour) ;
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
    	return isStatCardNumberExists(theEntityManager, aStatCardNumber, Long.valueOf(aYear));
    }
    @SuppressWarnings("unchecked")
    public static boolean isStatCardNumberExists(EntityManager aManager,String aStatCardNumber, Long aYear) {
    	List<StatisticStubExist> states = aManager.createQuery("FROM StatisticStub WHERE code = :number AND DTYPE = 'StatisticStubExist' and year = :year")
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
    public static boolean isStacStardMustRemove(SessionContext aContext, HospitalMedCase aMedCase) {
    	System.out.println((!aContext.isCallerInRole(AlwaysStatCardNumber))) ;
    	System.out.println(aMedCase.getStatisticStub()) ;
    	System.out.println(aMedCase.getDeniedHospitalizating()) ;
        return 
        		aMedCase.getStatisticStub() != null 
        		&& (!aContext.isCallerInRole(AlwaysStatCardNumber))
        		&& (aMedCase.getDeniedHospitalizating() != null
                || (aMedCase.getAmbulanceTreatment()!=null
                && aMedCase.getAmbulanceTreatment().equals(Boolean.TRUE))
               ) ;

    }
    public void removeStatCardNumber() {
    	removeStatCardNumber(theEntityManager, theContext, theMedCase) ;
    }
    
    /**
     * Убираем номер стат. карты
     */
    public static void removeStatCardNumber(EntityManager aManager
    		, SessionContext aContext
    		, HospitalMedCase aMedCase) throws EJBException {
        if (!isStacStardMustRemove(aContext,aMedCase)) {
        	//throw new IllegalArgumentException("не надо удалять") ;
        	return ;
        }

        if (aMedCase.getIsAmbulanseDialis()) {
           LOG.debug(" Удаление номер стат. карты из диализа " + aMedCase);
           aManager.remove(aMedCase.getStatisticStub());
           aMedCase.setStatisticStub(null);
        	
        } else {
        	StatisticStubExist stubexist = aMedCase.getStatisticStub() ;
        	StatisticStubRestored stubrestored = new StatisticStubRestored();
        	stubrestored.setCode(stubexist.getCode());
        	stubrestored.setYear(stubexist.getYear());
        	stubrestored.setPigeonHole(stubexist.getPigeonHole()) ;
        	stubrestored.setIsEmergency(stubexist.getIsEmergency()) ;
        	stubrestored.setIsPlan(stubexist.getIsPlan()) ;
        	aMedCase.setStatisticStub(null);
        	aManager.persist(stubrestored);
        	//theEntityManager.refresh(stubrestored);
        	aManager.remove(stubexist);
        }
    }



    
    /**
     * Первый попавшийся восстановленный номер стат. карты
     *
     * @return -1 если нет восстановленных номеров
     */
    @SuppressWarnings("unchecked")
	private static StatisticStubRestored getRestoredStatCard(
    		EntityManager aEntityManager, VocPigeonHole aPigeonHole,boolean aIsEmergAndPlan
    		, boolean aIsEmergency ,  long aYear,boolean aIsRestored
    		) {
    	
    	List<StatisticStubRestored> states = null ;
    	if (aIsRestored) {
    		if (aPigeonHole!=null) {
    			if (aIsEmergAndPlan) {
    				String add = " and is"+(aIsEmergency?"Emergency":"Plan")+"='1'" ;
    				states = aEntityManager.createQuery("from StatisticStub where year = :year and DTYPE='StatisticStubRestored' and pigeonHole=:vhd"+add)
        				.setParameter("year",aYear).setParameter("vhd",aPigeonHole).setMaxResults(1).getResultList();
    			} else {
    				states = aEntityManager.createQuery("from StatisticStub where year = :year and DTYPE='StatisticStubRestored' and pigeonHole=:vhd")
    						.setParameter("vhd",aPigeonHole)
            				.setParameter("year",aYear).setMaxResults(1).getResultList();
    			}
    		} else {
        		states = aEntityManager.createQuery("from StatisticStub where year = :year and DTYPE='StatisticStubRestored'")
        				.setParameter("year",aYear).setMaxResults(1).getResultList();
    		}
    	}
    	return (states!=null && !states.isEmpty()) ? states.get(0) : null ; 
    }
    
    private static String prepareStatCardNumber(long aStatCardId,VocPigeonHole aPigeonHole,java.sql.Date aDateStart) {
    	if (aPigeonHole!=null) {
    		return (aPigeonHole.getPrefixBefore()!=null?aPigeonHole.getPrefixBefore():"") +aStatCardId +
    				(aPigeonHole.getPrefixAfter()!=null?aPigeonHole.getPrefixAfter():"") ;
    	} else {
    		String yy = new SimpleDateFormat("yy").format(aDateStart);
    		return aStatCardId + "-" + yy;
    	}
    }
    
    /**
    * Создание следующего порядкового номера статкарты и сохранение
    * Вызывать после сохранения всех полей
    */
    public static void createStacCardNumber(long aSlsId, String aStatCardNumberByHand , EntityManager aManager, SessionContext aContext) throws EJBException{
    	HospitalMedCase medCase = aManager.find(HospitalMedCase.class, aSlsId);
    	VocPigeonHole vph = medCase.getDepartment()!=null?medCase.getDepartment().getPigeonHole():null ;
    	boolean isEmerPlan = vph!=null && vph.getIsStatStubEmerPlan()!=null && vph.getIsStatStubEmerPlan() ? true:false ;
    	boolean emergency = medCase.getEmergency()==null?false:medCase.getEmergency().booleanValue() ;
    	if (medCase.getStatisticStub()!=null) {
    		changeStatCardNumber(aSlsId, aStatCardNumberByHand, aManager, aContext) ;
    		return ;
    	}
    	if (aContext.isCallerInRole(CreateStatCardNumberByHand) && (aStatCardNumberByHand!=null && !aStatCardNumberByHand.equals(""))) {
    		if (CAN_DEBUG) LOG.debug("  Создание номера стат карты вручную [ номер стат.карты = " + aStatCardNumberByHand + "]");
            Calendar cal = Calendar.getInstance();
            cal.setTime(medCase.getDateStart());
            Long year = Long.valueOf(cal.get(Calendar.YEAR)) ;
            if (StatisticStubStac.isStatCardNumberExists(aManager, aStatCardNumberByHand, year)) {
                throw new IllegalArgumentException("Номер стат. карты " +aStatCardNumberByHand + " уже существует за этот год");
            }
            StatisticStubExist statcard = new StatisticStubExist();
            statcard.setCode(aStatCardNumberByHand);
            statcard.setYear(year);
            statcard.setMedCase(medCase);
            medCase.setStatisticStub(statcard);
            aManager.persist(statcard) ;
            //StringTokenizer st = new StringTokenizer(aForm.getStatCardNumber()," -");
            //int number = Integer.parseInt(st.nextToken()) ;
//        StacSTATNPrimaryKey statPk = new StacSTATNPrimaryKey(cal.get(Calendar.YEAR), number);
//        StacSTATNUtil.getLocalHome().create(statPk) ;
        } else {
            if (CAN_DEBUG) LOG.debug("Создание номера стат. карты автоматически");
            //stub.createStacCardNumber();
	        
	    	boolean ret ;
	    	if (!aContext.isCallerInRole(AlwaysStatCardNumber)) {
	    		ret = (medCase.getDeniedHospitalizating()==null	
	                		&& (medCase.getAmbulanceTreatment()==null
	                		|| !medCase.getAmbulanceTreatment())
	                		) ;
	    	} else {
	    		ret = true;
	    	}
	    	if (!ret) return ;
	    	String nextStatCardNumber;
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy") ;
	        Long year = Long.valueOf(format.format(medCase.getDateStart())) ;
	        if (medCase.getIsAmbulanseDialis()) {
	            System.out.println(" dialis....") ;
	            nextStatCardNumber = new StringBuffer().append(medCase.getPatient().getId()).toString();
	            StatisticStubExist statstub = new StatisticStubExist();
	            statstub.setCode(nextStatCardNumber);
	            statstub.setYear(year);
	            statstub.setMedCase(medCase);
	            medCase.setStatisticStub(statstub);
	        } else {
	        	System.out.println(" начало создание....") ;
	            //Long year = getYear() ;
	            StatisticStubRestored restored =null ;
	            boolean next ;
	            do {
	            	System.out.println(" поиск номера стат карты в восстановленных номерах....") ;
	            	boolean isRestored = aContext.isCallerInRole(AllowRestoredStatCard) ;
	            	StatisticStubRestored restoredb = getRestoredStatCard(aManager,vph,isEmerPlan,emergency,year,isRestored) ;
	            	if (restoredb==null) break ;
	                boolean existnum = isStatCardNumberExists(aManager, restoredb.getCode(), restoredb.getYear());
	                next = false ;
	                if (existnum) {
	                	aManager.remove(restoredb);
	                    next =  true;
	                } else {
	                	next = false ;
	                	restored = restoredb ;
	                }
	            } while(next); 
	            
	            if (restored!=null) {
	            	//long restoredId=Long.valueOf(restored.getCode()) ;
	                if (CAN_DEBUG) LOG.debug("Берем номер из восстановленных");
	            	// берем номер из восстановленных
	            	//TODO доделать удаление из восстановленных номеров
	                StatisticStubExist stubexist = new StatisticStubExist();
	                aManager.persist(stubexist);
	                //theEntityManager.refresh(stubexist);
	                stubexist.setCode(restored.getCode());
	                stubexist.setYear(year);
	                stubexist.setMedCase(medCase);
	                stubexist.setPigeonHole(vph) ;
	                if (isEmerPlan) {
	                	stubexist.setIsEmergency(emergency) ;
	                	stubexist.setIsPlan(!emergency) ;
	                }
	                aManager.remove(restored);
	                medCase.setStatisticStub(stubexist);
	            } else {
	            	//создаем новый номер
	                if (CAN_DEBUG) LOG.debug("Создание новый номер");
	            	List<StatisticStubNew> rows = null;
	            	
	            		if (vph!=null) {
	            			if (isEmerPlan) {
	            				String add = " and is"+(emergency?"Emergency":"Plan")+"='1'" ;
	            				rows = aManager.createQuery("from StatisticStub where year = :year and DTYPE='StatisticStubNew' and pigeonHole=:vhd"+add)
	                				.setParameter("year",year).setParameter("vhd",vph).setMaxResults(1).getResultList();
	            			} else {
	            				rows = aManager.createQuery("from StatisticStub where year = :year and DTYPE='StatisticStubNew' and pigeonHole=:vhd")
	            						.setParameter("vhd",vph)
	                    				.setParameter("year",year).setMaxResults(1).getResultList();
	            			}
	            		} else {
	                		rows = aManager.createQuery("from StatisticStub where year = :year and DTYPE='StatisticStubNew'")
	                				.setParameter("year",year).setMaxResults(1).getResultList();
	            		}
	            	
	            	
	            	
	            	
	            	long nextId ;
	        		//int year_int = year.intValue() ;
	        		StatisticStubNew stubnew ;
	            	if (rows!=null && !rows.isEmpty()) {
	            		stubnew = rows.get(0);
	            		nextId = Long.valueOf(stubnew.getCode()) ;
	            	} else {
	            		nextId = Long.valueOf(isEmerPlan?(emergency?1:2):1) ;
	            		stubnew = new StatisticStubNew();
	            		stubnew.setYear(year);
	            		stubnew.setPigeonHole(vph) ;
		                if (isEmerPlan) {
		                	stubnew.setIsEmergency(emergency) ;
		                	stubnew.setIsPlan(!emergency) ;
		                }
	            		//theEntityManager.refresh(stubnew);
	            	}
	        		stubnew.setCode(""+(nextId+(isEmerPlan?2:1)));
	        		aManager.persist(stubnew);
	        		java.sql.Date dateStart = medCase.getDateStart() ;
	        		nextStatCardNumber = prepareStatCardNumber(nextId,vph,dateStart);
	            	if(CAN_DEBUG)LOG.debug("Просмотр");
	                while (isStatCardNumberExists(aManager,nextStatCardNumber,year)) {
	                    nextId=nextId+(isEmerPlan?2:1);
	            		stubnew.setCode(""+(nextId+(isEmerPlan?2:1)));
	                    nextStatCardNumber = prepareStatCardNumber(nextId,vph,dateStart) ;
	                }
	            	if(CAN_DEBUG)LOG.debug("Запись нового номера стат.карты = "+nextStatCardNumber);
	                StatisticStubExist stubexist = new StatisticStubExist();
	                stubexist.setPigeonHole(vph) ;
	                if (isEmerPlan) {
	                	if (emergency) {
	                		stubexist.setIsEmergency(true) ;
	                	} else {
	                		stubexist.setIsPlan(true) ;
	                	}
	                }
	                stubexist.setCode(nextStatCardNumber);
	                stubexist.setYear(year);
	                stubexist.setMedCase(medCase);
	                aManager.persist(stubexist);
	            	medCase.setStatisticStub(stubexist);
	            	
	            }
	        }
        }
    }

    /**
     * Создание следующего порядкового номера статкарты и сохранение
     * Вызывать после сохранения всех полей
     */
    @SuppressWarnings("unchecked")
	public void createStacCardNumber() throws EJBException {
    	VocPigeonHole pigeonHole= theMedCase.getDepartment()!=null ? theMedCase.getDepartment().getPigeonHole():null ;
        if (!isStatCardNumberMustCreate()) {
            throw new EJBException("Номер стат. карты создавать не нужно");
        }

        String nextStatCardNumber;
        if (theMedCase.getIsAmbulanseDialis()) {
            System.out.println(" dialis....") ;
            nextStatCardNumber = new StringBuffer().append(theMedCase.getPatient().getId()).toString();
            StatisticStubExist statstub = new StatisticStubExist();
            statstub.setCode(nextStatCardNumber);
            statstub.setYear(theYear);
            statstub.setMedCase(theMedCase);
            theMedCase.setStatisticStub(statstub);
        } else {
        	System.out.println(" начало создание....") ;
            Long year = getYear() ;
            StatisticStubRestored restored =null ;
            boolean next ;
            do {
            	System.out.println(" поиск номера стат карты в восстановленных номерах....") ;
            	StatisticStubRestored restoredb = StatisticStubStac.getRestoredStatCard(theEntityManager
            			,thePigeonHole,theIsEmergAndPlan,theIsEmergency
            			,theYear,theContext.isCallerInRole(AllowRestoredStatCard)) ;
            	if (restoredb==null) break ;
                boolean existnum = isStatCardNumberExists(theEntityManager, restoredb.getCode(), restoredb.getYear());
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
                //theEntityManager.refresh(stubexist);
                stubexist.setCode(restored.getCode());
                stubexist.setYear(year);
                stubexist.setMedCase(theMedCase);
                stubexist.setPigeonHole(thePigeonHole) ;
                if (theIsEmergAndPlan) {
                	stubexist.setIsEmergency(theIsEmergency) ;
                	stubexist.setIsPlan(!theIsEmergency) ;
                }
                theEntityManager.remove(restored);
            	theMedCase.setStatisticStub(stubexist);
            } else {
            	//создаем новый номер
                if (CAN_DEBUG) LOG.debug("Создание новый номер");
                List<StatisticStubNew> rows = null;
            	
        		if (thePigeonHole!=null) {
        			if (theIsEmergAndPlan) {
        				String add = " and is"+(theIsEmergency?"Emergency":"Plan")+"='1'" ;
        				rows = theEntityManager.createQuery("from StatisticStub where year = :year and DTYPE='StatisticStubNew' and pigeonHole=:vhd"+add)
            				.setParameter("year",year).setParameter("vhd",pigeonHole).setMaxResults(1).getResultList();
        			} else {
        				rows = theEntityManager.createQuery("from StatisticStub where year = :year and DTYPE='StatisticStubNew' and pigeonHole=:vhd")
        						.setParameter("vhd",pigeonHole)
                				.setParameter("year",year).setMaxResults(1).getResultList();
        			}
        		} else {
            		rows = theEntityManager.createQuery("from StatisticStub where year = :year and DTYPE='StatisticStubNew'")
            				.setParameter("year",year).setMaxResults(1).getResultList();
        		}
        	
        	
            	long nextId ;
        		//int year_int = year.intValue() ;
        		StatisticStubNew stubnew ;
            	if (rows!=null && !rows.isEmpty()) {
            		stubnew = rows.get(0);
            		nextId = Long.valueOf(stubnew.getCode()) ;
            	} else {
            		nextId = Long.valueOf(theIsEmergAndPlan?(theIsEmergency?1:2):1) ;
            		stubnew = new StatisticStubNew();
            		stubnew.setYear(year);
            		stubnew.setPigeonHole(thePigeonHole) ;
                    if (theIsEmergAndPlan) {
                    	stubnew.setIsEmergency(theIsEmergency) ;
                    	stubnew.setIsPlan(!theIsEmergency) ;
                    }
            		//theEntityManager.refresh(stubnew);
            	}
        		stubnew.setCode(""+(nextId+(theIsEmergAndPlan?2:1)));
        		theEntityManager.persist(stubnew);
        		java.sql.Date dateStart = theMedCase.getDateStart() ;
        		nextStatCardNumber = prepareStatCardNumber(nextId,pigeonHole,dateStart);
            	if(CAN_DEBUG)LOG.debug("Просмотр");
                while (isStatCardNumberExists(nextStatCardNumber,year.intValue())) {
                    nextId=nextId+(theIsEmergAndPlan?2:1);
            		stubnew.setCode(""+(nextId+(theIsEmergAndPlan?2:1)));
            		stubnew.setPigeonHole(thePigeonHole) ;
	                if (theIsEmergAndPlan) {
	                	stubnew.setIsEmergency(theIsEmergency) ;
	                	stubnew.setIsPlan(!theIsEmergency) ;
	                }
                    nextStatCardNumber = prepareStatCardNumber(nextId,pigeonHole,dateStart) ;
                }
            	if(CAN_DEBUG)LOG.debug("Запись нового номера стат.карты = "+nextStatCardNumber);
                StatisticStubExist stubexist = new StatisticStubExist();
                //
                //theEntityManager.refresh(stubexist);
                stubexist.setCode(nextStatCardNumber);
                stubexist.setYear(year);
                stubexist.setMedCase(theMedCase);
                stubexist.setPigeonHole(thePigeonHole) ;
                if (theIsEmergAndPlan) {
                	stubexist.setIsEmergency(theIsEmergency) ;
                	stubexist.setIsPlan(!theIsEmergency) ;
                }
                theEntityManager.persist(stubexist);
            	//theMedCase.setStatCardNumber(nextStatCardNumber);
                theMedCase.setStatisticStub(stubexist);
            	
            }
        	
        }
    }





	/** Случай стационарного лечения */
	public HospitalMedCase getMedCase() {return theMedCase;}
	public void setMedCase(HospitalMedCase aMedCase) {theMedCase = aMedCase;}

	/** Session context */
	public SessionContext getContext() {return theContext;	}
	public void setContext(SessionContext aContext) {theContext = aContext;}

	/** Приемник */
	public VocPigeonHole getPigeonHole() {
		return thePigeonHole;
	}

	public void setPigeonHole(VocPigeonHole aPigeonHole) {
		thePigeonHole = aPigeonHole;
	}

	/** Приемник */
	private VocPigeonHole thePigeonHole;
	/** Год создания случая стационарного лечения */
	public long getYear() {return theYear;}
	public void setYear(long aYear) {theYear = aYear;}

	/** EntityManager */
	public EntityManager getEntityManager() {return theEntityManager;}
	public void setEntityManager(EntityManager aEntityManager) {theEntityManager = aEntityManager;	}
	
	/** EntityContext */
	private EntityManager theEntityManager;
	/** Год создания случая стационарного лечения */
	private long theYear;
	/** Session context */
	private SessionContext theContext;
	/** Случай стационарного лечения */
	private HospitalMedCase theMedCase;
	public static String AllowRestoredStatCard = "/Policy/Mis/MedCase/Stac/Ssl/Admission/AllowRestoredStatCard" ;
	public static String AlwaysStatCardNumber = "/Policy/Mis/MedCase/Stac/Ssl/Admission/AlwaysCreateStatCardNumber" ;
	public static String CreateStatCardNumberByHand="/Policy/Mis/MedCase/Stac/Ssl/Admission/CreateStatCardNumberByHand" ;
	public static String CreateHour = "/Policy/Mis/MedCase/Stac/Ssl/Admission/CreateHour" ;
	public static String EditHour = "/Policy/Mis/MedCase/Stac/Ssl/Admission/EditHour" ;
	public static String ChangeStatCardNumber = "/Policy/Mis/MedCase/Stac/Ssl/Admission/ChangeStatCardNumber" ;
	public static String CreateStatCardBeforeDeniedByHand = "/Policy/Mis/MedCase/Stac/Ssl/Admission/CreateStatCardBeforeDeniedByHand" ;
	
	/** Экстренная госпитализация */
	private boolean theIsEmergency;
	/** Разбивка на экстренные и плановые госпитализации */
	private boolean theIsEmergAndPlan;
}
