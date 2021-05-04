package ru.ecom.mis.ejb.service.sync.shubinok;

import org.apache.log4j.Logger;
import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.address.ejb.service.ILocalAddressService;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcOksm;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.mis.ejb.domain.external.PatientInfoShubinok;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.hospital.FondImport;
import ru.ecom.mis.ejb.domain.medcase.hospital.FondImportReestr;
import ru.ecom.mis.ejb.domain.patient.MedPolicyOmc;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.PatientAttachedImport;
import ru.ecom.mis.ejb.domain.patient.voc.*;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.mis.ejb.service.synclpufond.ISyncLpuFondService;
import ru.ecom.poly.ejb.domain.Medcard;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Синхронизация с базой П.Г.Шибинка
 */

@Stateless
@Local(ISyncShubinokService.class)
public class SyncShubinokServiceBean implements ISyncShubinokService {

    private static final Logger LOG = Logger.getLogger(SyncShubinokServiceBean.class) ;
    private static final String SYNCERRORTEXT = "Ошибка синхронизации";
    private static final String OMCCODE = "omcCode";

    public void sync(long aMonitorId, long aTimeId) {
    	IMonitor monitor = null;
    	try {
    		monitor = monitorService.startMonitor(aMonitorId, "Синхронизация персон", getCount(aTimeId));
    		Query query = manager.createQuery("from PatientInfoShubinok where time = :time")
    				.setParameter("time", aTimeId) ;
    		Iterator<PatientInfoShubinok> pats = QueryIteratorUtil.iterate(PatientInfoShubinok.class, query);
    		int i = 0;
    		while (pats.hasNext()) {
    			PatientInfoShubinok pat = pats.next();
    			if (monitor.isCancelled()) {
    				throw new IllegalMonitorStateException("Прервано пользователем");
    			}
    			i++;
    			Long medPolicyId = syncService.findMedPolicyId(pat.getPolicySeries(), pat.getPolicyNumber()) ;
    			MedPolicyOmc medPolicy = medPolicyId!=null ? manager.find(MedPolicyOmc.class, medPolicyId) : null ;
    			
    			Patient patient ;
    			if(medPolicy==null) {
    				Long patientId = syncService.findPatientId(pat.getLastnameGood()
    						, pat.getFirstname(), pat.getMiddlename(), pat.getBirthdate()) ;
    				patient = patientId!=null ? manager.find(Patient.class, patientId) : null ;
    			} else {
    				patient = medPolicy.getPatient() ;
    			}
    			
    			if(i % 100 == 0) {
    				monitor.advice(100);
    				monitor.setText(pat.getLastnameGood());
    			}
    			syncPatient(pat, patient, medPolicy) ;
    		}
            monitor.finish(aTimeId + "");
    	} catch (Exception e) {
    		monitor.error(SYNCERRORTEXT,e);
    		LOG.error(SYNCERRORTEXT,e);
    	}
    }

    //импорт населения с фонда
    public void syncPatientByFond(long aMonitorId, long aTimeId) {
    	syncPatientByFond(aMonitorId, aTimeId, true);
    }
    public void syncPatientByFond(long aMonitorId, long aTimeId, boolean forceUpdateAttachment) {
        IMonitor monitor = null;
        FondImport fi = new FondImport();
        long currentDateLong = System.currentTimeMillis();
		Date currentDate=new Date(currentDateLong) ;
        fi.setImportDate(new java.sql.Date(currentDateLong));
        fi.setImportTime(new java.sql.Time(currentDateLong));
        fi.setImportTable("PatientAttachedImport");
        fi.setImportNumber(String.valueOf(aTimeId));
        manager.persist(fi);
        try {
            monitor = monitorService.startMonitor(aMonitorId, "Синхронизация персон", getCountFond(aTimeId));
            long adrAstr = 0 ;
            List<Object> lAdr = manager.createNativeQuery("select a.addressid from Address2 a where a.kladr like '30000001%' and domen=4").setMaxResults(2).getResultList() ;
    		if (lAdr.size()==1) {
    			adrAstr = ConvertSql.parseLong(lAdr.get(0)) ;
    		}
            int i = 0;
            long id=0 ;
            while (true) {
            	List<PatientAttachedImport> paiList= (List<PatientAttachedImport>)manager.createQuery("from PatientAttachedImport where time = :time and id > :id order by id")
                		.setMaxResults(500)
                        .setParameter("time", aTimeId)
                        .setParameter("id", id).getResultList();
            	if (paiList.isEmpty()) {break;}
	            for (PatientAttachedImport patImp: paiList) {
	            	i++;
	                if (i%100==0 && monitor.isCancelled()) {
	                    throw new IllegalMonitorStateException("Прервано пользователем");
	                }
	                String aPolicyNumber = patImp.getPolicyNumber();
	                String aPolicySeries = patImp.getPolicySeries();
	                id=patImp.getId() ;
					RegInsuranceCompany insComp = findEntity(RegInsuranceCompany.class, "smocode", patImp.getInsCompName()) ;
	                patImp.setInsuranceCompany(insComp) ;
	                MedPolicyOmc medPolicy = (MedPolicyOmc) patImp.getMedPolicy() ;
	            	if (medPolicy==null && insComp!=null && aPolicyNumber!=null || !aPolicyNumber.trim().equals("")) {
		                if (aPolicySeries==null || aPolicySeries.trim().equals("") ) {
		                	if (aPolicyNumber.length()>10) {
		                		aPolicySeries = aPolicyNumber.substring(0,2)+" "+aPolicyNumber.substring(2,4) ;
		        				aPolicyNumber = aPolicyNumber.substring(4) ;
		        			} else  if (aPolicyNumber.trim().length() > 3){
		        				aPolicySeries = aPolicyNumber.substring(0,3) ;
		        				aPolicyNumber = aPolicyNumber.substring(3) ;
		        			}
		                }
		                patImp.setPolicySeriesEdit(aPolicySeries) ;
		                patImp.setPolicyNumberEdit(aPolicyNumber) ;
		                Long medPolicyId = syncService.findMedPolicyId(aPolicySeries, aPolicyNumber) ;
		                medPolicy = medPolicyId!=null ? manager.find(MedPolicyOmc.class, medPolicyId) : null ;
	            	}
	                Long patientId1 = patImp.getPatient();
	                if(medPolicy==null) {
	                	if (patientId1==null) {
		                    Long patientId = syncService.findPatientId(patImp.getLastname()
		                            , patImp.getFirstname(), patImp.getMiddlename(), patImp.getBirthday()) ;
		                    Patient patient = patientId!=null ? manager.find(Patient.class, patientId) : null ;
		                    if (patient!=null)patientId1 = patient.getId() ;
	                	}
	                } else {
	                    patientId1 = medPolicy.getPatient().getId() ;
	                }
	            	Address adr = patImp.getAddressRegistration() ;
	            	VocRayon vr = findOrCreateRayon(patImp.getRayonName(),patImp.getRegion()) ;
	            	patImp.setRayon(vr) ;
	            	if (adr==null) {
	            		String aStreet = patImp.getStreet();
	            		String adrStr = patientService.getAddressByOkato(patImp.getRn(), aStreet);
	            		Long adrId = adrStr!=null?Long.valueOf(adrStr):null;
	            		if (adrId==null) {
	            			adrId=getKladrByRayon(patImp.getRegion(), vr!=null?vr.getKladr():"", patImp.getCity(), patImp.getNp(), aStreet, patImp.getIndex(), adrAstr) ;
	            		}
	            		if (adrId!=null) adr=manager.find(Address.class, adrId) ;
	            	}
	            	if (adr!=null) { 
	            		patImp.setAddressRegistration(adr) ; 
	            	}
	                if (i%100==0) {
	                    monitor.advice(100);
	                    monitor.setText(i+". "+patImp.getLastname());
	                }
	                syncPatient(patImp, patientId1, medPolicy,currentDate, forceUpdateAttachment, fi) ;
	            }
            }
            monitor.finish(aTimeId + "");
        }  catch (Exception e) {
            monitor.error(SYNCERRORTEXT,e);
            LOG.error(SYNCERRORTEXT,e);
        }
    }

    private void syncPatient(PatientAttachedImport aEntity, Long aPatientId, MedPolicyOmc aMedPolicy,Date aCurrentDate, boolean updateAttachment, FondImport fi) {
    	VocIdentityCard passportType=findOrCreateIdentity(aEntity.getDocType()) ;
    	OmcOksm nat = findOrCreateNationality(aEntity.getCountry()) ;
    	boolean isNew ;
    	StringBuilder firRecord = new StringBuilder();
    	Patient patient ;
    	if (aPatientId==null || aPatientId.equals(0L)) {
    		isNew=true;
    		patient = new Patient();
    		patient.setCreateUsername("fond_base") ;
    		patient.setCreateDate(aCurrentDate) ;
    		aEntity.setIsCreateNewPatient(true) ;
        	patient.setLastname(aEntity.getLastname());
        	patient.setFirstname(aEntity.getFirstname());
        	patient.setMiddlename(aEntity.getMiddlename());
        	patient.setBirthday(aEntity.getBirthday());
        	patient.setPassportSeries(aEntity.getDocSeries()) ;
        	patient.setPassportNumber(aEntity.getDocNumber()) ;
        	patient.setPassportDateIssued(aEntity.getDocDateIssued()) ;
        	patient.setPassportType(passportType) ;
        	patient.setPassportWhomIssued(aEntity.getDocWhom()!=null?aEntity.getDocWhom():"") ;
        	patient.setBirthPlace(aEntity.getBirthPlace()!=null?aEntity.getBirthPlace():"") ;
        	//Гражданство
        	patient.setNationality(nat) ;
			firRecord.append("Создан новый пациент: ").append(patient.getPatientInfo()).append(".");
    	} else {
    		isNew = false;
    		patient = manager.find(Patient.class,aPatientId) ;
    		firRecord.append("Пациент ").append(patient.getPatientInfo()).append(" найден в базе. ");
    		aEntity.setIsUpdatePatient(true) ;
    		if (!StringUtil.isNullOrEmpty(patient.getBirthPlace())) {
    			patient.setBirthPlace(aEntity.getBirthPlace()) ;
    		}
    		if (patient.getPassportDateIssued()==null || (aEntity.getDocDateIssued()!=null && aEntity.getDocDateIssued().getTime() > patient.getPassportDateIssued().getTime())){
    			//Обновляем, если паспорт пациента не заполнен, либо дата выдачи паспорта по фонду больше даты выдачи паспорта пациента
    			patient.setPassportSeries(aEntity.getDocSeries()) ;
            	patient.setPassportNumber(aEntity.getDocNumber()) ;
            	patient.setPassportDateIssued(aEntity.getDocDateIssued()) ;
            	patient.setPassportType(passportType) ;
    		}
        	//Гражданство
    		if (patient.getNationality()==null) {
    			patient.setNationality(nat) ;
    		}
    	}
    	
    	if (patient.getRayon()==null) {
    		patient.setRayon(aEntity.getRayon()) ;
    	}
		patient.setEditUsername("fond_base") ;
		patient.setEditDate(aCurrentDate) ;
    	patient.setSnils(aEntity.getSnils());
    	patient.setCommonNumber(aEntity.getCommonNumber());
    	//aPatient.setBirthPlace(aEntity.getBirthPlace()) ;
    	
    	if (aEntity.getAddressRegistration()!=null) {
    		patient.setAddress(aEntity.getAddressRegistration());
    	} else {
    		patient.setNotice((patient.getNotice()!=null?patient.getNotice():"")+" "+aEntity.getCity()+" "+aEntity.getStreet()) ;
    	}
    	patient.setHouseNumber(aEntity.getHouse());
    	patient.setFlatNumber(aEntity.getApartment());
    	patient.setHouseBuilding(aEntity.getHousing());
    	manager.persist(patient);
    	//Район
    	if (!StringUtil.isNullOrEmpty(aEntity.getSex())) patient.setSex(findEntity(VocSex.class,OMCCODE,aEntity.getSex()));
    	
    	if (aEntity.getInsuranceCompany()!=null) {
	    	if(aMedPolicy==null) {
	    		aMedPolicy = new MedPolicyOmc() ;
	    		aMedPolicy.setPatient(patient);
	    	}
	    	aMedPolicy.setType(findEntity(VocMedPolicyOmc.class, "code", aEntity.getPolicyType())) ;
	        aMedPolicy.setCommonNumber(aEntity.getCommonNumber());
	    	aMedPolicy.setLastname(aEntity.getLastname());
	    	aMedPolicy.setFirstname(aEntity.getFirstname());
	    	aMedPolicy.setMiddlename(aEntity.getMiddlename());
	    	aMedPolicy.setBirthday(aEntity.getBirthday());
	    	aMedPolicy.setSeries(aEntity.getPolicySeriesEdit());
	    	aMedPolicy.setPolNumber(aEntity.getPolicyNumberEdit());
	    	aMedPolicy.setCompany(aEntity.getInsuranceCompany());
	    	if (aEntity.getPolicyDateFrom()!=null) {
	    		aMedPolicy.setActualDateFrom(aEntity.getPolicyDateFrom()) ;
	    	} else if (aMedPolicy.getActualDateFrom()==null) {	    		
	    			aMedPolicy.setActualDateFrom(aCurrentDate);	    			    		
	    	}
	    	if (aEntity.getPolicyDateTo()!=null) {
	    		aMedPolicy.setActualDateTo(aEntity.getPolicyDateTo()) ;
	    	}
	    	
	    	manager.persist(aMedPolicy);
    	}
    	aEntity.setPatient(patient.getId()) ;
    	aEntity.setMedPolicy(aMedPolicy) ;
    	
    	manager.merge(aEntity);
    	
    	//manager.persist(aEntity);
    	String patientSync = "Ф"+patient.getId() ;
    	if (isNew) {
    		Medcard medcard = new Medcard() ;
    		medcard.setNumber(patientSync) ;
    		MisLpu lpu = findEntity(MisLpu.class,"codef",aEntity.getLpu()) ;
    		medcard.setLpu(lpu) ;
    		medcard.setPerson(patient) ;
    		manager.persist(medcard);
    	}
    	//Обновляем прикрепления
    		if (aEntity.getLpuauto()!=null && !aEntity.getLpuauto().equals("") &&!aEntity.getLpuauto().equals("0")) {
    			firRecord.append(patientService.updateOrCreateAttachment(patient.getId(), aEntity.getInsCompName(), aEntity.getLpu()
        				, aEntity.getLpuauto(), DateFormat.formatToDate(aEntity.getLpuDateFrom()), aEntity.getDoctorSnils(), updateAttachment, true));
        	}

		if (patient.getPatientSync()==null || patient.getPatientSync().equals("")) {
			patient.setPatientSync(patientSync) ;
	    	manager.persist(patient);
		}
		try {

			FondImportReestr fir = new FondImportReestr();
			fir.setImportType(fi);
			fir.setImportResult(firRecord.toString());
			fir.setNumberFond(String.valueOf(aEntity.getId()));
			manager.persist(fir);	
		} catch (Exception e) {
			e.printStackTrace();
		}
    	manager.flush() ;
    	manager.clear() ;

    }
    private void syncPatient(PatientInfoShubinok aEntry, Patient aPatient, MedPolicyOmc aMedPolicy) {
        if(aPatient==null) {
            aPatient = new Patient();
        }
        aPatient.setLastname(aEntry.getLastnameGood());
        aPatient.setFirstname(aEntry.getFirstname());
        aPatient.setMiddlename(aEntry.getMiddlename());
        aPatient.setBirthday(aEntry.getBirthdate());
        aPatient.setSnils(aEntry.getSnils());
        aPatient.setAddress(addressService.findAddressByKladr(aEntry.getKladr()));
        aPatient.setHouseNumber(aEntry.getHouseNumber());
        aPatient.setFlatNumber(aEntry.getFlatNumber());
        aPatient.setHouseBuilding(aEntry.getBuildingNumber());
        aPatient.setSex(findEntity(VocSex.class,OMCCODE,aEntry.getSex()));
        aPatient.setSocialStatus(findEntity(VocSocialStatus.class,OMCCODE,aEntry.getInsuranceType()));

        if(aMedPolicy==null) {
            aMedPolicy = new MedPolicyOmc() ;
            aMedPolicy.setPatient(aPatient);
        }
        aMedPolicy.setSeries(aEntry.getPolicySeries());
        aMedPolicy.setPolNumber(aEntry.getPolicyNumber());
        aMedPolicy.setCompany(findEntity(RegInsuranceCompany.class, OMCCODE, aEntry.getInsuranceCompanyCode()));

        manager.persist(aPatient);
        manager.persist(aMedPolicy);
        manager.flush();
        manager.clear() ;
    }

    @SuppressWarnings("unchecked")
	private <T> T findEntity(Class<T> aEntity, String aCodeField, String aValue) {
        String entityName = entityHelper.getEntityName(aEntity);
        List<T> list = manager.createQuery(
                "from "+entityName+" where "+aCodeField+" = :code"
        ).setParameter("code",aValue).getResultList();
        return list!=null && list.size()==1 ? list.iterator().next() : null ;
    }

    private Long getCount(long aTimeId) {
    	return (Long) manager.createQuery("select count(*) from PatientInfoShubinok where time = :time")
    			.setParameter("time", aTimeId).getSingleResult() ;
    }
    private Long getCountFond(long aTimeId) {
        return (Long) manager.createQuery("select count(*) from PatientAttachedImport where time = :time")
                .setParameter("time", aTimeId).getSingleResult() ;
    }

    private @EJB IPatientService patientService;
    private @EJB ILocalMonitorService monitorService;
    private @PersistenceContext EntityManager manager;
    private @EJB ISyncLpuFondService syncService ;
    private @EJB ILocalAddressService addressService ;
    private EntityHelper entityHelper = EntityHelper.getInstance();
    private VocRayon findOrCreateRayon(String aRayon, String aRegion) {
    	aRayon = aRayon.toUpperCase() ;
    	StringBuilder ind = new StringBuilder().append(aRayon).append("#").append(aRegion) ;
    	if (hashRayon.get(ind.toString())==null) {
    		if (!aRayon.trim().equals("") && (aRayon.contains("ИНОГ") || aRegion != null && aRegion.equals("30"))) {
    			VocRayon vr  = null ;
    			List<VocRayon> listVr = manager.createQuery("from VocRayon where upper(name) like '%"+aRayon+"%'").setMaxResults(2).getResultList() ;
    			if (listVr.size()==1) vr=listVr.get(0) ;
    			hashRayon.put(ind.toString(), vr);
    		}
    	} 
    	return hashRayon.get(ind.toString());
    }
    private OmcOksm findOrCreateNationality(String aCode) {
    	if (StringUtil.isNullOrEmpty(aCode)) aCode="RUS" ;
    	aCode = aCode.toUpperCase().trim() ;
    	return hashNationality.get(aCode);//,k->findEntity(OmcOksm.class,"alfa2",k))  ;
    }
	private VocIdentityCard findOrCreateIdentity(String aCode) {
		aCode = aCode.toUpperCase() ;
        if (hashIdentity.get(aCode) == null && !aCode.equals("")) {
        	hashIdentity.put(aCode, findEntity(VocIdentityCard.class,"omcCode",aCode)) ;
        } 
        return hashIdentity.get(aCode);
    }

	//address

	private Long getKladrByRayon(String aRegion, String aKladrRayon, String aCity, String aNp, String aStreet, String aIndex,long adrAstr) {
		Long adr = null ;
		StringBuilder sql ;
		List<Object[]> list ;
		 if (aCity!=null && aCity.toUpperCase().contains("АСТРАХАН")
     			||	aNp!=null&&aNp.toUpperCase().contains("АСТРАХАН")
     			) {
     		adr =adrAstr ;
     		list = manager.createNativeQuery("select a.addressid from Address2 a where a.parent_addressid='"+adrAstr+"' and UPPER(a.name)=:street").setParameter("street", aStreet.toUpperCase()).setMaxResults(2).getResultList() ;
     		if (list.size()==1) {
     			adr = ConvertSql.parseLong(list.get(0)) ;
     		} else if (list.size()>1 && aIndex!=null && !aIndex.trim().equals("")) {
     			list.clear() ;
     			list = manager.createNativeQuery("select a.addressid from Address2 a where a.parent_addressid='"+adrAstr+"' and UPPER(a.name)=:street and postIndex='"+aIndex+"'").setParameter("street", aStreet.toUpperCase()).setMaxResults(2).getResultList() ;
         		if (list.size()==1) {
         			adr = ConvertSql.parseLong(list.get(0)) ;
         		}
     		}
		 } else {
			
			sql = new StringBuilder() ;
			if (aNp!=null && !aNp.trim().equals("")) aCity=aNp ;
			if (aCity.endsWith(" С")
					||aCity.endsWith(" П")
					||aCity.endsWith(" Г")
					||aCity.endsWith("Г.")
					||aCity.endsWith("П.")
					||aCity.endsWith("С.")
					) aCity = aCity.substring(0,aCity.length()-2) ;
			else if (aCity.endsWith("ГОР.")
					||aCity.endsWith(" ГОР")
					||aCity.endsWith(" ПОС")
					||aCity.endsWith("ПОС.")
					) aCity = aCity.substring(0,aCity.length()-4) ;
			else if (aCity.startsWith("С.")||aCity.startsWith("С ")) aCity = aCity.substring(2) ;
			else if (aCity.startsWith("Г.")||aCity.startsWith("Г ")) aCity = aCity.substring(2) ;
			else if (aCity.startsWith("П.")||aCity.startsWith("П ")) aCity = aCity.substring(2) ;
			else if (aCity.startsWith("ГОР.")) aCity = aCity.substring(4) ;
			else if (aCity.endsWith(" РП")) aCity = aCity.substring(0,aCity.length()-2);
			else if (aCity.startsWith("ПОС.")) aCity = aCity.substring(4) ;
			else if (aCity.startsWith("ГОР ")) aCity = aCity.substring(4) ;
			else if (aCity.startsWith("ПОС ")) aCity = aCity.substring(4) ;
			aCity=aCity.trim().toUpperCase().replaceAll("-", "").replaceAll(" ", "").replaceAll("№", "N") ;
			sql.append("select addressid,kladr from Address2 where kladr like '").append(aKladrRayon).append("%' and domen <6 and UPPER(replace(replace(name,'-',''),' ',''))='").append(aCity).append("' " ) ;
			list = manager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			if (!list.isEmpty()) {
				adr=ConvertSql.parseLong(list.get(0)[0]) ;
				aKladrRayon = ""+list.get(0)[1] ;
				String lastKl = aKladrRayon.substring(aKladrRayon.length()-1) ;
				while (lastKl!=null&&lastKl.equals("0")) {
					aKladrRayon = aKladrRayon.substring(0,aKladrRayon.length()-1) ;
					lastKl = aKladrRayon.length()>1?aKladrRayon.substring(aKladrRayon.length()-1):null ;
				}
				sql = new StringBuilder() ;
				sql.append("select addressid,kladr from Address2 where parent_addressid = '").append(adr).append("' and UPPER(name)='").append(aStreet.toUpperCase()).append("'" ) ;
				list.clear() ;
				list = manager.createNativeQuery(sql.toString()).setMaxResults(2).getResultList() ;
				if (list.size()==1) {
					adr=ConvertSql.parseLong(list.get(0)[0]) ;
				} else if (!list.isEmpty() && aIndex!=null && !aIndex.trim().equals("")) {
					sql.append(" and postIndex='").append(aIndex).append("'");
					list.clear() ;
					list = manager.createNativeQuery(sql.toString()).setMaxResults(2).getResultList() ;
					if (list.size()==1) {
						adr=ConvertSql.parseLong(list.get(0)[0]) ;
					}
				}
			}
		 }
		return adr ;
	}

	private HashMap<String, VocIdentityCard> hashIdentity = new HashMap<>();
	private HashMap<String, VocRayon> hashRayon = new HashMap<>();
    private HashMap<String, OmcOksm> hashNationality = new HashMap<>();
}
