package ru.ecom.expomc.ejb.domain.registry;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;

/**
 * Реестр
 */
@Entity
@Table(name="EOREGISTRY",schema="SQLUser")
public class Registry extends BaseEntity {


    /** Страховая компания */
    @ManyToOne
    public RegInsuranceCompany getInsuranceCompany() { return theInsuranceCompany ; }
    public void setInsuranceCompany(RegInsuranceCompany aInsuranceCompany) { theInsuranceCompany = aInsuranceCompany ; }

    /** Страховая компания */
    private RegInsuranceCompany theInsuranceCompany ;
    /** Дата формаирование реестра */
    public Date getCreateDate() { return theCreateDate ; }
    public void setCreateDate(Date aCreateDate) { theCreateDate = aCreateDate ; }

    /** Номер счета */
    public String getAccountNumber() { return theAccountNumber ; }
    public void setAccountNumber(String aAccountNumber) { theAccountNumber = aAccountNumber ; }

    /** Дата счета */
    public String getAccountDate() { return theAccountDate ; }
    public void setAccountDate(String aAccountDate) { theAccountDate = aAccountDate ; }

    /** Номер реестра */
    public String getRegistryNumber() { return theRegistryNumber ; }
    public void setRegistryNumber(String aRegistryNumber) { theRegistryNumber = aRegistryNumber ; }

//    /** Отчетные записи */
//    @OneToMany (cascade= CascadeType.ALL, mappedBy="registry")
//    public Collection<RegistryEntry> getEntries() { return theEntries ; }
//    public void setEntries(Collection<RegistryEntry> aEntries) { theEntries = aEntries ; }

    /** Отчетные записи */
    private Collection<RegistryEntry> theEntries ;
    /** Номер реестра */
    private String theRegistryNumber ;
    /** Дата счета */
    private String theAccountDate ;
    /** Номер счета */
    private String theAccountNumber ;
    /** Дата формаирование реестра */
    private Date theCreateDate ;
}
