package ru.ecom.address.ejb.service;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.address.ejb.domain.kladr.Kladr;
import ru.ecom.address.ejb.domain.kladr.KladrDoma;
import ru.ecom.ejb.services.util.QueryResultUtil;
import ru.nuzmsh.util.StringUtil;

public class AstrkhanReginHelper {

	private final static Logger LOG = Logger
			.getLogger(AstrkhanReginHelper.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public AstrkhanReginHelper() {
		// заполнение ОКАТО
		theOkatoHash.put("12401367", "К") ; // Киров
		theOkatoHash.put("12401372", "Л") ; // Ленинс
		theOkatoHash.put("12401381", "С") ; // Советс
		theOkatoHash.put("12401383", "Т") ; // Трусов
		
		// заполнение КЛАДР
		theKladrHash.put("3000200000000","А") ; // Ахтубинский
		theKladrHash.put("3000300000000","В") ; // Воло
		theKladrHash.put("3000400000000","Е") ; // Енота
		theKladrHash.put("3000500000000","ИК") ; // Икрян
		theKladrHash.put("3000600000000","КА") ; // Камыз
		theKladrHash.put("3000700000000","КР") ; // Красноя
		theKladrHash.put("3000800000000","ЛМ") ; // Лиманский
		theKladrHash.put("3000900000000","НР") ; // Нариман
		theKladrHash.put("3000100000000","ПР") ; // Приволж
		theKladrHash.put("3000110000000","ХР") ; // Харабали
		theKladrHash.put("3000120000000","Ч") ; // Черноярский
		theKladrHash.put("3000000200000","КЯ") ; // Знаменск
		
		theNameHash.put("К","Кировский") ;
		theNameHash.put("Л","Ленинский") ;
		theNameHash.put("С","Советский") ;
		theNameHash.put("Т","Трусовский") ;
		theNameHash.put("А","Ахтубинский") ;
		theNameHash.put("В","Володаровский") ;
		theNameHash.put("Е","Енотаевский") ;
		theNameHash.put("ИК","Икрянинский") ;
		theNameHash.put("КА","Камызяцкий") ;
		theNameHash.put("КР","Красноярский") ;
		theNameHash.put("ЛМ","Лиманский") ;
		theNameHash.put("НР","Наримановский") ;
		theNameHash.put("ПР","Приволжский") ;
		theNameHash.put("ХР","Харабалинский") ;
		theNameHash.put("Ч","Черноярский") ;
		theNameHash.put("КЯ","Знаменский") ;
	}
	
	/**
	 * Район фонда
	 */
	public String getOmcRayon(Address aAddress, String aHouse, EntityManager aManager) {
		if(aAddress==null) return null ;
		if (CAN_DEBUG)
			LOG.debug("getOmcRayon: aAddress = " + aAddress); 

		String ret = findByKladr(aAddress) ;
		if (CAN_DEBUG)
			LOG.debug("getOmcRayon: findByAddress = " + ret); 

		if(ret==null) {
			if (CAN_DEBUG)
				LOG.debug("getOmcRayon: findBySoato = " + ret); 
			ret = findBySoato(aAddress, aManager) ;
		}
		if(StringUtil.isNullOrEmpty(ret)) {
			ret = findByKladrDoma(aManager, aAddress, aHouse);
		}
		return ret ;
	}
	
	public String getOmcRayonName(Address aAddress, String aHouse, EntityManager aManager) {
		String key = getOmcRayon(aAddress, aHouse, aManager);
		return key!=null ? theNameHash.get(key) : key ;
	}	
	
	private String findBySoato(Address address, EntityManager aManager) {
		if(address.getKladr()==null) return null;
		
		if (CAN_DEBUG)
			LOG.debug("findBySoato: address.getKladr() = " + address.getKladr()); 

		Kladr kladr = QueryResultUtil.getFirst(Kladr.class, aManager
				.createNamedQuery("kladr.findByKladrcode")
				.setParameter("code", address.getKladr())
		) ;
		
		if(kladr!=null) {
			String okato = kladr.getOkatd() ;
			if (CAN_DEBUG)
				LOG.debug("findBySoato: okato = " + okato); 

			return getRayonCodeBySoato(okato);
		}
		return null ;
	}

	private String getRayonCodeBySoato(String okato) {
		if(!StringUtil.isNullOrEmpty(okato) && okato.length()>7) {
			okato = okato.substring(0,8);
			if (CAN_DEBUG)
				LOG.debug("findBySoato: okatoStripped = " + okato); 

			if(theOkatoHash.containsKey(okato)) {
				return theOkatoHash.get(okato) ; 
			}
		}
		return null ;
	}
	
	
	private String findByKladrDoma(EntityManager aManager, Address aAddress, String aHouse) {
		if(aAddress==null) return null ;
		if(StringUtil.isNullOrEmpty(aAddress.getKladr())) return null ;
		if(StringUtil.isNullOrEmpty(aHouse)) return null ;
		if (CAN_DEBUG)
			LOG.debug("findByKladrDoma: aAddress.getKladr() = " + aAddress.getKladr()); 

		KladrDoma doma = QueryResultUtil.getFirst(KladrDoma.class
					,aManager.createNamedQuery("KladrDoma.findByKladrCode")
			.setParameter("code", aAddress.getKladr()));
		String ret = null;
		if(doma!=null && !StringUtil.isNullOrEmpty(doma.getName())) {
			if (CAN_DEBUG)
				LOG.debug("findByKladrDoma: doma.getName() = " + doma.getName()); 

			List<AddressPointCheck> checks = thePointHelper.parsePoints(doma.getName()) ;
			if (CAN_DEBUG)
				LOG.debug("findByKladrDoma: checks = " + checks); 
			if(checks!=null && !checks.isEmpty()) {
				for(AddressPointCheck check : checks) {
					if(check.getNumber()!=null && check.getNumber().equals(aHouse)) {
						if (CAN_DEBUG)
							LOG.debug("findByKladrDoma: check.getNumber() = " + check.getNumber()); 

						String rayon = getRayonCodeBySoato(doma.getOcatd());
						if(rayon!=null) {
							ret = rayon ;
							break;
						}
					}
				}
			}
		}
		return ret ;
		
	}
	
	private String findByKladr(Address address) {
		
		if (CAN_DEBUG)
			LOG.debug("findByKladr: address.getKladr() = " + address.getKladr()); 

		if(address.getKladr()!=null && theKladrHash.containsKey(address.getKladr())) {
			return theKladrHash.get(address.getKladr()) ;
		} else {
			if(address.getParent()!=null) {
				return findByKladr(address.getParent());
			} else {
				return null ;
			}
		}
	}

	private final HashMap<String, String> theOkatoHash = new HashMap<String, String>(); 
	private final HashMap<String, String> theKladrHash = new HashMap<String, String>(); 
	private final HashMap<String, String> theNameHash = new HashMap<String, String>();
	private final AddressPointCheckHelper thePointHelper = new AddressPointCheckHelper() ;
}
