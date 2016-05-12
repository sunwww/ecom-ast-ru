package ru.ecom.mis.ejb.domain.lpu;

import static javax.persistence.CascadeType.ALL;

import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.util.StringUtil;

/**
 * @author  esinev  Date: 09.11.2006  Time: 11:41:37
 */
@Entity
@EntityListeners(AddressTextListener.class)
@Table(schema="SQLUser")
public class LpuAreaAddressText extends BaseEntity {
 

    /** Улица */
    public String getStreetName() { return theStreetName ; }
    public void setStreetName(String aStreetName) { theStreetName = aStreetName ; }

    /** Строка адреса */
    public String getAddressString() { return theAddressString ; }
    public void setAddressString(String aAddressString) { theAddressString = aAddressString ; }

    /** Терапевтический участок */
    @ManyToOne
    public LpuArea getArea() { return theArea ; }
    public void setArea(LpuArea aArea) { theArea = aArea ; }

    /** Список домов */
    @OneToMany(mappedBy = "lpuAreaAddressText", cascade = ALL)
    public List<LpuAreaAddressPoint> getLpuAreaAddressPoints() { return theLpuAreaAddressPoints ; }
    public void setLpuAreaAddressPoints(List<LpuAreaAddressPoint> aLpuAreaAddressPoints) { theLpuAreaAddressPoints = aLpuAreaAddressPoints ; }

    /** Адрес */
    @Transient
    public String getAddressText() {
        StringBuilder sb = new StringBuilder();
        Address a = theAddress ;
        //boolean isFirstPassed = false ;
        while(a!=null) {

            sb.insert(0, a.getName()) ;
            sb.insert(0, " ") ;
            sb.insert(0, a.getType()!=null ? a.getType().getShortName() : "") ;

            long oldId = a.getId() ;
            a = a.getParent() ;
            if(a!=null && a.getId()==oldId) {
                a = null ;
            }
            if(a!=null) sb.insert(0, ", ") ;
        }
//        sb.append(theAddress!=null ? theAddress.getName() : "") ;
        if(!StringUtil.isNullOrEmpty(theAddressString)) {
            sb.append(" ( ") ;
            StringTokenizer st = new StringTokenizer(theAddressString," ,.;:");
            boolean isFirstPassed = false ;
            while(st.hasMoreTokens()) {
                if(isFirstPassed) sb.append(", ") ; else isFirstPassed = true ;
                sb.append(st.nextToken()) ;
            }
//            sb.append(theAddressString) ;
            sb.append(" )") ;
        }
        return sb.toString() ;
    }
    public void setAddressText(String aAddressText) {}

    /** Адрес */
    @OneToOne
    public Address getAddress() { return theAddress ; }
    public void setAddress(Address aAddress) { theAddress = aAddress ; }

    /** Адрес */
    private Address theAddress ;

    /** Терапевтический участок */
    private LpuArea theArea ;
    /** Строка адреса */
    private String theAddressString ;
    /** Улица */
    private String theStreetName ;

    /** Список домов */
    private List<LpuAreaAddressPoint> theLpuAreaAddressPoints ;

}
