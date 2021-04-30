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

import lombok.Getter;
import lombok.Setter;
import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.util.StringUtil;

/**
 * @author  esinev  Date: 09.11.2006  Time: 11:41:37
 */
@Entity
@EntityListeners(AddressTextListener.class)
@Table(schema="SQLUser")
@Getter
@Setter
public class LpuAreaAddressText extends BaseEntity {
 

    /** Терапевтический участок */
    @ManyToOne
    public LpuArea getArea() { return area ; }

    /** Список домов */
    @OneToMany(mappedBy = "lpuAreaAddressText", cascade = ALL)
    public List<LpuAreaAddressPoint> getLpuAreaAddressPoints() { return lpuAreaAddressPoints ; }

    /** Адрес */
    @Transient
    public String getAddressText() {
        StringBuilder sb = new StringBuilder();
        Address a = address ;
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
        if(!StringUtil.isNullOrEmpty(addressString)) {
            sb.append(" ( ") ;
            StringTokenizer st = new StringTokenizer(addressString," ,.;:");
            boolean isFirstPassed = false ;
            while(st.hasMoreTokens()) {
                if(isFirstPassed) sb.append(", ") ; else isFirstPassed = true ;
                sb.append(st.nextToken()) ;
            }
            sb.append(" )") ;
        }
        return sb.toString() ;
    }
    public void setAddressText(String aAddressText) {}

    /** Адрес */
    @OneToOne
    public Address getAddress() { return address ; }

    /** Адрес */
    private Address address ;

    /** Терапевтический участок */
    private LpuArea area ;
    /** Строка адреса */
    private String addressString ;
    /** Улица */
    private String streetName ;

    /** Список домов */
    private List<LpuAreaAddressPoint> lpuAreaAddressPoints ;

}
