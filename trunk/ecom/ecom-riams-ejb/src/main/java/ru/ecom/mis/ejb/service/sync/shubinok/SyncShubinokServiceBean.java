package ru.ecom.mis.ejb.service.sync.shubinok;

import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
import ru.ecom.mis.ejb.domain.patient.MedPolicyOmc;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.PatientAttachedImport;
import ru.ecom.mis.ejb.domain.patient.voc.VocIdentityCard;
import ru.ecom.mis.ejb.domain.patient.voc.VocMedPolicyOmc;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.ecom.mis.ejb.domain.patient.voc.VocRayon;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.ecom.mis.ejb.domain.patient.voc.VocSocialStatus;
import ru.ecom.mis.ejb.service.synclpufond.ISyncLpuFondService;

/**
 * Синхронизация с базой П.Г.Шибинка
 */

@Stateless
@Local(ISyncShubinokService.class)
public class SyncShubinokServiceBean implements ISyncShubinokService {

    private final static Logger LOG = Logger.getLogger(SyncShubinokServiceBean.class) ;
//    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    public void sync(long aMonitorId, long aTimeId) {
    	IMonitor monitor = null;
    	try {
    		monitor = theMonitorService.startMonitor(aMonitorId, "Синхронизация персон", getCount(aTimeId));
    		Query query = theManager.createQuery("from PatientInfoShubinok where time = :time")
    				.setParameter("time", aTimeId) ;
    		Iterator<PatientInfoShubinok> pats = QueryIteratorUtil.iterate(PatientInfoShubinok.class, query);
    		int i = 0;
    		while (pats.hasNext()) {
    			PatientInfoShubinok pat = pats.next();
    			if (monitor.isCancelled()) {
    				throw new IllegalMonitorStateException("Прервано пользователем");
    			}
    			i++;
    			Long medPolicyId = theSyncService.findMedPolicyId(pat.getPolicySeries(), pat.getPolicyNumber()) ;
    			MedPolicyOmc medPolicy = medPolicyId!=null ? theManager.find(MedPolicyOmc.class, medPolicyId) : null ;
    			
    			Patient patient ;
    			if(medPolicy==null) {
    				Long patientId = theSyncService.findPatientId(pat.getLastnameGood()
    						, pat.getFirstname(), pat.getMiddlename(), pat.getBirthdate()) ;
    				patient = patientId!=null ? theManager.find(Patient.class, patientId) : null ;
    			} else {
    				patient = (Patient) medPolicy.getPatient() ;
    			}
    			
    			if(i%100==0) {
    				monitor.advice(100);
    				monitor.setText(pat.getLastnameGood());
    			}
    			syncPatient(pat, patient, medPolicy) ;
    		}
            monitor.finish(aTimeId + "");
    	} catch (IllegalMonitorStateException e) {
    		throw e ;
    	} catch (Exception e) {
    		monitor.error("Ошибка синхронизации",e);
    		LOG.error("Ошибка синхронизации",e);
    	}
    }
    public void syncPatientByFond(long aMonitorId, long aTimeId) {
        IMonitor monitor = null;
        try {
            monitor = theMonitorService.startMonitor(aMonitorId, "Синхронизация персон", getCountFond(aTimeId));
            long adrAstr = 0 ;
            List<Object> lAdr = theManager.createNativeQuery("select a.addressid from Address2 a where a.kladr like '30000001%' and domen=4").setMaxResults(2).getResultList() ;
    		if (lAdr.size()==1) {
    			adrAstr = ConvertSql.parseLong(lAdr.get(0)) ;
    		}
    		Date current_date=new Date(new java.util.Date().getTime()) ;

            int i = 0;
            boolean nextData = true ;
            long id=0 ;
            while (nextData) {
            	Query query = theManager.createQuery("from PatientAttachedImport where time = :time and id > :id order by id")
                		.setMaxResults(500)
                        .setParameter("time", aTimeId)
                        .setParameter("id", id);
                Iterator<PatientAttachedImport> pats = QueryIteratorUtil.iterate(PatientAttachedImport.class, query);
            	nextData = pats.hasNext() ;
            	//nextData=false ;
	            while (pats.hasNext()) {
	            	PatientAttachedImport patImp = pats.next();
	                if (monitor.isCancelled()) {
	                    throw new IllegalMonitorStateException("Прервано пользователем");
	                }
	                i++;
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
		        			} else  if (aPolicyNumber!=null && aPolicyNumber.trim().length()>3){
		        				aPolicySeries = aPolicyNumber.substring(0,3) ;
		        				aPolicyNumber = aPolicyNumber.substring(3) ;
		        			}
		                }
		                patImp.setPolicySeriesEdit(aPolicySeries) ;
		                patImp.setPolicyNumberEdit(aPolicyNumber) ;
		                Long medPolicyId = theSyncService.findMedPolicyId(aPolicySeries, aPolicyNumber) ;
		                medPolicy = medPolicyId!=null ? theManager.find(MedPolicyOmc.class, medPolicyId) : null ;
	            	}
	                Patient patient = patImp.getPatient();
	                if(medPolicy==null) {
	                	if (patient==null) {
		                    Long patientId = theSyncService.findPatientId(patImp.getLastname()
		                            , patImp.getFirstname(), patImp.getMiddlename(), patImp.getBirthday()) ;
		                    patient = patientId!=null ? theManager.find(Patient.class, patientId) : null ;
	                	}
	                } else {
	                    patient = (Patient) medPolicy.getPatient() ;
	                }
	            	Address adr = patImp.getAddressRegistration() ;
	            	VocRayon vr = findOrCreateRayon(patImp.getRayonName(),patImp.getRegion()) ;
	            	patImp.setRayon(vr) ;
	            	if (adr==null) {
	            		Long adrId=getKladrByRayon(patImp.getRegion(), vr!=null?vr.getKladr():"", patImp.getCity(), patImp.getNp(), patImp.getStreet(), patImp.getIndex(), adrAstr) ;
	            		if (adrId!=null) adr=theManager.find(Address.class, adrId) ;
	            	}
	            	patImp.setAddressRegistration(adr) ;
	                if (i%100==0) {
	                    monitor.advice(100);
	                    monitor.setText(i+". "+patImp.getLastname());
	                }
	                syncPatient(patImp, patient, medPolicy,current_date) ;
	            }
            }
            monitor.finish(aTimeId + "");
        } catch (IllegalMonitorStateException e) {
            throw e ;
        } catch (Exception e) {
            monitor.error("Ошибка синхронизации",e);
            LOG.error("Ошибка синхронизации",e);
        }
    }

    private void syncPatient(PatientAttachedImport aEntity, Patient aPatient, MedPolicyOmc aMedPolicy,Date aCurrentDate) {
    	VocIdentityCard passportType=findOrCreateIdentity(aEntity.getDocType()) ;
    	OmcOksm nat = findOrCreateNationality(aEntity.getCountry()) ;
    	if(aPatient==null) {
    		aPatient = new Patient();
    		aPatient.setCreateUsername("fond_base") ;
    		aPatient.setCreateDate(aCurrentDate) ;
    		aEntity.setIsCreateNewPatient(true) ;
        	aPatient.setLastname(aEntity.getLastname());
        	aPatient.setFirstname(aEntity.getFirstname());
        	aPatient.setMiddlename(aEntity.getMiddlename());
        	aPatient.setBirthday(aEntity.getBirthday());
        	aPatient.setPassportSeries(aEntity.getDocSeries()) ;
        	aPatient.setPassportNumber(aEntity.getDocNumber()) ;
        	aPatient.setPassportDateIssued(aEntity.getDocDateIssued()) ;
        	aPatient.setPassportType(passportType) ;
        	aPatient.setBirthPlace(aEntity.getBirthPlace()!=null?aEntity.getBirthPlace():"") ;
        	//Гражданство
        	aPatient.setNationality(nat) ;
    	} else {
    		aEntity.setIsUpdatePatient(true) ;
    		if ((aPatient.getBirthPlace()==null||aPatient.getBirthPlace().equals(""))&&aEntity.getBirthPlace()!=null) {
    			aPatient.setBirthPlace(aEntity.getBirthPlace()) ;
    		}
    		if ((aPatient.getPassportSeries()==null||aPatient.getPassportSeries().equals(""))&&aEntity.getDocSeries()!=null) {
    			aPatient.setPassportSeries(aEntity.getDocSeries()) ;
            	aPatient.setPassportNumber(aEntity.getDocNumber()) ;
            	aPatient.setPassportDateIssued(aEntity.getDocDateIssued()) ;
            	aPatient.setPassportType(passportType) ;
    		}
        	//Гражданство
    		if (aPatient.getNationality()==null) {
    			aPatient.setNationality(nat) ;
    		}
    	}
    	
    	if (aPatient.getRayon()==null) {
    		aPatient.setRayon(aEntity.getRayon()) ;
    	}
		aPatient.setEditUsername("fond_base") ;
		aPatient.setEditDate(aCurrentDate) ;
    	aPatient.setSnils(aEntity.getSnils());
    	aPatient.setCommonNumber(aEntity.getCommonNumber());
    	//aPatient.setBirthPlace(aEntity.getBirthPlace()) ;
    	if (aEntity.getAddressRegistration()!=null) {
    		aPatient.setAddress(aEntity.getAddressRegistration());
    	} else {
    		aPatient.setNotice((aPatient.getNotice()!=null?aPatient.getNotice():"")+" "+aEntity.getCity()+" "+aEntity.getStreet()) ;
    	}
    	aPatient.setHouseNumber(aEntity.getHouse());
    	aPatient.setFlatNumber(aEntity.getApartment());
    	aPatient.setHouseBuilding(aEntity.getHousing());
    	//Район
    	if (aEntity.getSex()!=null&&!aEntity.getSex().equals("")) aPatient.setSex(findEntity(VocSex.class,"omcCode",aEntity.getSex().equals("Ж")?"2":"1"));
    	
    	if (aEntity.getInsuranceCompany()!=null) {
	    	if(aMedPolicy==null) {
	    		aMedPolicy = new MedPolicyOmc() ;
	    		aMedPolicy.setPatient(aPatient);
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
	    	
	    	aMedPolicy.setActualDateFrom(aEntity.getPolicyDateFrom()) ;
	    	aMedPolicy.setActualDateTo(aEntity.getPolicyDateTo()) ;
	    	theManager.persist(aMedPolicy);
    	}
    	aEntity.setPatient(aPatient) ;
    	aEntity.setMedPolicy(aMedPolicy) ;
    	theManager.persist(aPatient);
    	theManager.persist(aEntity);
		if (aPatient.getPatientSync()==null || aPatient.getPatientSync().equals("")) {
			aPatient.setPatientSync(new StringBuilder().append("Ф").append(aPatient.getId()).toString()) ;
	    	theManager.persist(aPatient);
		}
    	theManager.flush() ;
    	theManager.clear() ;

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
        aPatient.setAddress(theAddressService.findAddressByKladr(aEntry.getKladr()));
        aPatient.setHouseNumber(aEntry.getHouseNumber());
        aPatient.setFlatNumber(aEntry.getFlatNumber());
        aPatient.setHouseBuilding(aEntry.getBuildingNumber());
        aPatient.setSex(findEntity(VocSex.class,"omcCode",aEntry.getSex()));
        aPatient.setSocialStatus(findEntity(VocSocialStatus.class,"omcCode",aEntry.getInsuranceType()));

        VocOrg org = findEntity(VocOrg.class, "fondNumber", aEntry.getOrgCodeNew()) ;
        aPatient.setWorks(org);

        if(aMedPolicy==null) {
            aMedPolicy = new MedPolicyOmc() ;
            aMedPolicy.setPatient(aPatient);
        }
        aMedPolicy.setSeries(aEntry.getPolicySeries());
        aMedPolicy.setPolNumber(aEntry.getPolicyNumber());
        aMedPolicy.setOrg(org);
        aMedPolicy.setCompany(findEntity(RegInsuranceCompany.class, "omcCode", aEntry.getInsuranceCompanyCode()));

        theManager.persist(aPatient);
        theManager.persist(aMedPolicy);
        theManager.flush();
        theManager.clear() ;
    }

    @SuppressWarnings("unchecked")
	private <T> T findEntity(Class<T> aEntity, String aCodeField, String aValue) {
        String entityName = theEntityHelper.getEntityName(aEntity);
        List<T> list = theManager.createQuery(
                "from "+entityName+" where "+aCodeField+" = :code"
        ).setParameter("code",aValue).getResultList();
        return list!=null && list.size()==1 ? list.iterator().next() : null ;
    }

    private Long getCount(long aTimeId) {
    	return (Long) theManager.createQuery("select count(*) from PatientInfoShubinok where time = :time")
    			.setParameter("time", aTimeId).getSingleResult() ;
    }
    private Long getCountFond(long aTimeId) {
        return (Long) theManager.createQuery("select count(*) from PatientAttachedImport where time = :time")
                .setParameter("time", aTimeId).getSingleResult() ;
    }


    private @EJB ILocalMonitorService theMonitorService;
    private @PersistenceContext EntityManager theManager;
    private @EJB ISyncLpuFondService theSyncService ;
    private @EJB ILocalAddressService theAddressService ;
    private EntityHelper theEntityHelper = EntityHelper.getInstance();
    private VocRayon findOrCreateRayon(String aRayon, String aRegion) {
    	aRayon = aRayon.toUpperCase() ;
    	StringBuilder ind = new StringBuilder().append(aRayon).append("#").append(aRegion) ;
    	if (theHashRayon.get(ind.toString())==null) {
    		if (aRayon!=null && !aRayon.trim().equals("") && (aRayon.contains("ИНОГ") || aRegion!=null&&aRegion.equals("30"))) {
    			VocRayon vr  = null ;
    			List<VocRayon> list_vr = theManager.createQuery("from VocRayon where upper(name) like '%"+aRayon+"%'").setMaxResults(2).getResultList() ;
    			if (list_vr.size()==1) vr=list_vr.get(0) ;
    			theHashRayon.put(ind.toString(), vr);
    		}
    	} 
    	return theHashRayon.get(ind.toString());
    }
    private OmcOksm findOrCreateNationality(String aCode) {
    	if (aCode==null || aCode.trim().equals("")) aCode="RUS" ;
    	aCode = aCode.toUpperCase().trim() ;
    	if (theHashNationality.get(aCode)==null) {
    		theHashNationality.put(aCode, findEntity(OmcOksm.class,"alfa2",aCode)) ;
    	} 
    	return theHashNationality.get(aCode) ;
    }
	private VocIdentityCard findOrCreateIdentity(String aCode) {
		aCode = aCode.toUpperCase() ;
        if (theHashIdentity.get(aCode)==null&&aCode!=null &&!aCode.equals("")) {
        	theHashIdentity.put(aCode, findEntity(VocIdentityCard.class,"omcCode",aCode)) ;
        } 
        return theHashIdentity.get(aCode);
    }
	
	
	
	
	//address
	
	
	private Long getKladrByRayon(String aRegion, String aKladrRayon, String aCity, String aNp, String aStreet, String aIndex,long adrAstr) {
		Long adr = null ;
		StringBuilder sql = new StringBuilder() ;
		StringBuilder res = new StringBuilder() ;
		List<Object[]> list ;
		 if (aCity!=null && aCity.toUpperCase().contains("АСТРАХАН")
     			||	aNp!=null&&aNp.toUpperCase().contains("АСТРАХАН")
     			) {
     		adr =adrAstr ;
     		list = theManager.createNativeQuery("select a.addressid from Address2 a where a.parent_addressid='"+adrAstr+"' and UPPER(a.name)=:street").setParameter("street", aStreet.toUpperCase()).setMaxResults(2).getResultList() ;
     		if (list.size()==1) {
     			adr = ConvertSql.parseLong(list.get(0)) ;
     		} else if (list.size()>1 && aIndex!=null && !aIndex.trim().equals("")) {
     			list.clear() ;
     			list = theManager.createNativeQuery("select a.addressid from Address2 a where a.parent_addressid='"+adrAstr+"' and UPPER(a.name)=:street and postIndex='"+aIndex+"'").setParameter("street", aStreet.toUpperCase()).setMaxResults(2).getResultList() ;
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
			else if (aCity.startsWith("ПОС.")) aCity = aCity.substring(4) ;
			else if (aCity.startsWith("ГОР ")) aCity = aCity.substring(4) ;
			else if (aCity.startsWith("ПОС ")) aCity = aCity.substring(4) ;
			aCity=aCity.trim().toUpperCase().replaceAll("-", "").replaceAll(" ", "").replaceAll("№", "N") ;
			sql.append("select addressid,kladr from Address2 where kladr like '").append(aKladrRayon).append("%' and domen <6 and UPPER(replace(replace(name,'-',''),' ',''))='").append(aCity).append("' " ) ;
			list = theManager.createNativeQuery(sql.toString()).setMaxResults(1).getResultList() ;
			if (list.size()>0) {
				adr=ConvertSql.parseLong(list.get(0)[0]) ;
				aKladrRayon = ""+list.get(0)[1] ;
				String lastKl = aKladrRayon.substring(aKladrRayon.length()-1, aKladrRayon.length()) ;
				while (lastKl!=null&&lastKl.equals("0")) {
					aKladrRayon = aKladrRayon.substring(0,aKladrRayon.length()-1) ;
					lastKl = aKladrRayon.length()>1?aKladrRayon.substring(aKladrRayon.length()-1, aKladrRayon.length()):null ;
				}
				sql = new StringBuilder() ;
				sql.append("select addressid,kladr from Address2 where parent_addressid = '").append(adr).append("' and UPPER(name)='").append(aStreet.toUpperCase()).append("'" ) ;
				list.clear() ;
				list = theManager.createNativeQuery(sql.toString()).setMaxResults(2).getResultList() ;
				if (list.size()==1) {
					adr=ConvertSql.parseLong(list.get(0)[0]) ;
				} else if (list.size()>0 && aIndex!=null && !aIndex.trim().equals("")) {
					sql.append(" and postIndex='"+aIndex+"'") ;
					list.clear() ;
					list = theManager.createNativeQuery(sql.toString()).setMaxResults(2).getResultList() ;
					if (list.size()==1) {
						adr=ConvertSql.parseLong(list.get(0)[0]) ;
					}
				}
			}
		 }
		return adr ;
	}

	private HashMap<String, VocIdentityCard> theHashIdentity = new HashMap<String, VocIdentityCard>();
	private HashMap<String, VocRayon> theHashRayon = new HashMap<String, VocRayon>();
    private HashMap<String, OmcOksm> theHashNationality = new HashMap<String, OmcOksm>();
}
