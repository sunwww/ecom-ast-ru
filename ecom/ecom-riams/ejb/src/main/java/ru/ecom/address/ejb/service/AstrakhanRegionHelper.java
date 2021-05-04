package ru.ecom.address.ejb.service;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.address.ejb.domain.kladr.Kladr;
import ru.ecom.address.ejb.domain.kladr.KladrDoma;
import ru.ecom.ejb.services.util.QueryResultUtil;
import ru.nuzmsh.util.StringUtil;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;

public class AstrakhanRegionHelper {
	public AstrakhanRegionHelper() {
		// заполнение ОКАТО
		okatoHash.put("12401367", "К") ; // Киров
		okatoHash.put("12401372", "Л") ; // Ленинс
		okatoHash.put("12401381", "С") ; // Советс
		okatoHash.put("12401383", "Т") ; // Трусов
		
		// заполнение КЛАДР
		kladrHash.put("3000200000000","А") ; // Ахтубинский
		kladrHash.put("3000300000000","В") ; // Воло
		kladrHash.put("3000400000000","Е") ; // Енота
		kladrHash.put("3000500000000","ИК") ; // Икрян
		kladrHash.put("3000600000000","КА") ; // Камыз
		kladrHash.put("3000700000000","КР") ; // Красноя
		kladrHash.put("3000800000000","ЛМ") ; // Лиманский
		kladrHash.put("3000900000000","НР") ; // Нариман
		kladrHash.put("3001000000000","ПР") ; // Приволж
		kladrHash.put("3001100000000","ХР") ; // Харабали
		kladrHash.put("3001200000000","Ч") ; // Черноярский
		kladrHash.put("3000000200000","КЯ") ; // Знаменск
		
		nameHash.put("К","Кировский") ;
		nameHash.put("Л","Ленинский") ;
		nameHash.put("С","Советский") ;
		nameHash.put("Т","Трусовский") ;
		nameHash.put("А","Ахтубинский") ;
		nameHash.put("В","Володаровский") ;
		nameHash.put("Е","Енотаевский") ;
		nameHash.put("ИК","Икрянинский") ;
		nameHash.put("КА","Камызяцкий") ;
		nameHash.put("КР","Красноярский") ;
		nameHash.put("ЛМ","Лиманский") ;
		nameHash.put("НР","Наримановский") ;
		nameHash.put("ПР","Приволжский") ;
		nameHash.put("ХР","Харабалинский") ;
		nameHash.put("Ч","Черноярский") ;
		nameHash.put("КЯ","Знаменский") ;
	}
	
	/**
	 * Район фонда
	 */
	public String getOmcRayon(Address aAddress, String aHouse, EntityManager aManager) {
		if(aAddress==null) return null ;
		String ret = findByKladr(aAddress) ;
		if(ret==null) {
			ret = findBySoato(aAddress, aManager) ;
		}
		if(StringUtil.isNullOrEmpty(ret)) {
			ret = findByKladrDoma(aManager, aAddress, aHouse);
		}
		return ret ;
	}
	
	public String getOmcRayonName(Address aAddress, String aHouse, EntityManager aManager) {
		String key = getOmcRayon(aAddress, aHouse, aManager);
		return key!=null ? nameHash.get(key) : null ;
	}	
	public String getOmcRayonNameKey(Address aAddress, String aHouse, EntityManager aManager) {
		return getOmcRayon(aAddress, aHouse, aManager);
	}	
	
	private String findBySoato(Address address, EntityManager aManager) {
		if(address.getKladr()==null) return null;
		Kladr kladr = QueryResultUtil.getFirst(Kladr.class, aManager
				.createNamedQuery("kladr.findByKladrcode")
				.setParameter("code", address.getKladr())
		) ;
		
		if(kladr!=null) {
			String okato = kladr.getOkatd() ;
			return getRayonCodeBySoato(okato);
		}
		return null ;
	}

	private String getRayonCodeBySoato(String okato) {
		if(!StringUtil.isNullOrEmpty(okato) && okato.length()>7) {
			okato = okato.substring(0,8);
			if(okatoHash.containsKey(okato)) {
				return okatoHash.get(okato) ; 
			}
		}
		return null ;
	}
	
	
	private String findByKladrDoma(EntityManager aManager, Address aAddress, String aHouse) {
		if(aAddress==null) return null ;
		if(StringUtil.isNullOrEmpty(aAddress.getKladr())) return null ;
		if(StringUtil.isNullOrEmpty(aHouse)) return null ;

		KladrDoma doma = QueryResultUtil.getFirst(KladrDoma.class
					,aManager.createNamedQuery("KladrDoma.findByKladrCode")
			.setParameter("code", aAddress.getKladr()));
		String ret = null;
		if(doma!=null && !StringUtil.isNullOrEmpty(doma.getName())) {

			List<AddressPointCheck> checks = pointHelper.parsePoints(doma.getName()) ;
			if(!checks.isEmpty()) {
				for(AddressPointCheck check : checks) {
					if(check.getNumber()!=null && check.getNumber().equals(aHouse)) {
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
		if(address.getKladr()!=null && kladrHash.containsKey(address.getKladr())) {
			return kladrHash.get(address.getKladr()) ;
		} else {
			return address.getParent()!=null ? findByKladr(address.getParent()) : null;
		}
	}

	private final HashMap<String, String> okatoHash = new HashMap<>();
	private final HashMap<String, String> kladrHash = new HashMap<>();
	private final HashMap<String, String> nameHash = new HashMap<>();
	private final AddressPointCheckHelper pointHelper = new AddressPointCheckHelper() ;
}
