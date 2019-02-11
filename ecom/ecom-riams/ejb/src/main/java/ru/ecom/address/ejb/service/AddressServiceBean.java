package ru.ecom.address.ejb.service;

import org.apache.log4j.Logger;
import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKodTer;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcQnp;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcStreetT;
import ru.nuzmsh.util.StringUtil;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Сервис для работы с адресами
 */
@Stateless
@Remote(IAddressService.class)
@Local(ILocalAddressService.class)
public class AddressServiceBean implements IAddressService, ILocalAddressService {

	private static final Logger LOG = Logger
			.getLogger(AddressServiceBean.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();

    @SuppressWarnings("unchecked")
	public Address findAddressByKladr(String aKladrCode) {
        List<Address> list = theEntityManager.createQuery("from Address where kladr = :kladr").setParameter("kladr",aKladrCode).getResultList();
        return list!=null && !list.isEmpty() ? list.iterator().next() : null ;
    }
    public String getRayon(Long aAddressId, String aHouse) {
    	Address adr = theEntityManager.find(Address.class, aAddressId);
    	String rayon =theAstrkhanReginHelper.getOmcRayonNameKey(adr, aHouse, theEntityManager) ;
    	if (rayon!=null) {
	    	StringBuilder sql = new StringBuilder() ;
	    	sql.append("select id,code||' '||name from VocRayon where code=:code") ;
	    	
	    	List<Object[]> list = theEntityManager.createNativeQuery(sql.toString()).setParameter("code",rayon).setMaxResults(1).getResultList() ;
	    	if (!list.isEmpty()) {
	    		Object[] obj = list.get(0) ;
	    		StringBuilder res = new StringBuilder() ;
	    		res.append(obj[0]).append("#").append(obj[1]) ;
	    		return res.toString() ;
	    	}
    	}
    	return "" ;
    }
    
    public String getZipcode(Long aAddress5, Long aAddress6) {
    	Address adr6 = theEntityManager.find(Address.class, aAddress6) ;
    	Address adr5 = theEntityManager.find(Address.class, aAddress6) ;
    	String zipcode ;
    	zipcode = adr6!=null?adr6.getPostIndex():"" ;
    	if (zipcode==null || zipcode.equals("")) zipcode = adr5!=null?adr5.getPostIndex():"" ;
    	return zipcode==null?"":zipcode ;
    }
    
    public String getAddressNonresidentString(Long aTerritory, String aRegion
        	, Long aTypeSettlement
        	, String aSettlement
        	, Long aTypeStreet
        	, String aStreet
        	, String aHouseNumber
        	, String aHouseBuilding, String aFlatNumber, String aZipCode) {
    	StringBuilder ret = new StringBuilder() ;
    	OmcKodTer territory = theEntityManager.find(OmcKodTer.class, aTerritory) ;
    	OmcQnp typeSettlement = theEntityManager.find(OmcQnp.class, aTypeSettlement) ;
    	OmcStreetT street = theEntityManager.find(OmcStreetT.class, aTypeStreet) ;
    	if (aZipCode!=null && !aZipCode.equals("")) ret.append("Индекс ").append(aZipCode).append(", ") ;
    	if (territory!=null) {
    		//ret.append(" код тер.регистр. ") ;
    		ret.append(territory.getName()).append(", ") ;
    	}
    	//add(aRegion,"район проживания", ret) ;
    	add(aRegion," ", ret) ;
    	if (typeSettlement!=null) {
    		//ret.append(" код вида насел.пункт ") ;
    		ret.append(typeSettlement.getName()).append(", ") ;
    	}
    	//add(aSettlement,"насел.пункт",ret) ;
    	add(aSettlement," ",ret) ;
    	if (street!=null) {
    		ret.append(street.getName()).append(", ") ;
    	}
    	add(aStreet,"",ret) ;
        add(aHouseNumber, "д.",ret) ;
        add(aHouseBuilding, "корпус",ret) ;
        add(aFlatNumber, "кв.",ret) ;
    	return ret.toString() ;
    	
    }

    public String getAddressString(long aAddressPk, String aHouse, String aCorpus, String aFlat,String aZipCode) {
    	if (CAN_DEBUG) LOG.debug("getAddressString: aAddressPk = " + aAddressPk);

    	String sql = "select a.fullname,a.name,at1.shortName,a.parent_addressid,a.addressid from Address2 a left join AddressType at1 on at1.id=a.type_id  where a.addressid=" ;
    	List<Object[]> list = theEntityManager.createNativeQuery(sql+aAddressPk) 
    			.setMaxResults(1).getResultList() ;
        //String rayon = theAstrkhanReginHelper.getOmcRayonName(address, aHouse, theEntityManager) ; 
        StringBuilder sb = new StringBuilder();
        if (!list.isEmpty()) {
        	Object[] obj = list.get(0) ;
        	String fullname = ""+(obj[0]==null?"":obj[0]) ;
	        if (!fullname.equals("")) {
	        	sb.append(fullname) ;
	        } else {
				sb.append(obj[2]!=null?obj[2]:"") ;
				sb.append(" ") ;
				sb.append(obj[1]!=null?obj[1]:"") ;
				sb.append(", ") ;
	        }
        }
    	if (aZipCode!=null && !aZipCode.equals("")) sb.insert(0,", ").insert(0,aZipCode).insert(0,"Индекс ") ;
        add(aHouse, " д.",sb) ;
        add(aCorpus, " корп.",sb) ;
        add(aFlat, " кв.",sb) ;
        //add(rayon, "РАЙОН:",sb) ;
        return sb.toString();
    }

    public Address getAddressForLevel(long aDomain, Address aAddress) {
    	if(aAddress==null) return null ;
    	Long id = getIdForLevel(aDomain, aAddress.getId());
    	if(id!=null) {
    		return theEntityManager.find(Address.class, id) ;
    	} else {
    		return null ;
    	}
    }

    public Long getIdForLevel(long aDomain, Long aAddressId) {
        Long ret = null ;
        if(aAddressId!=null) {
            Address a = theEntityManager.find(Address.class, aAddressId) ;
            while(a!=null) {
                if(a.getDomen()==aDomain) {
                    ret = a.getId() ;
                    break ;
                }
                long oldId = a.getId() ;
                a = a.getParent() ;
                if(a==null || a.getId()==oldId) a = null ;
            }
        }
        return ret ;
    }

    private static void add(String aStr, String aStr2, StringBuilder sb) {
        if(!StringUtil.isNullOrEmpty(aStr)) {
            sb.append(aStr2) ;
            sb.append(" ") ;
            sb.append(aStr) ;
            sb.append(", ") ;
        }
    }

    private static final AstrkhanReginHelper theAstrkhanReginHelper = new AstrkhanReginHelper();
    @PersistenceContext EntityManager theEntityManager ;
}
