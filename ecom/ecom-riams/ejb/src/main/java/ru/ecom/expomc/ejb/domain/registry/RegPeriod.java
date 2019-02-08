package ru.ecom.expomc.ejb.domain.registry;

import ru.ecom.ejb.domain.simple.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Период реестра
 */
@Entity
@Table(name="EOPERIOD",schema="SQLUser")
@Deprecated
public class RegPeriod extends BaseEntity {

    /** Дата начала перода */
    public Date getDateFrom() { return theDateFrom ; }
    public void setDateFrom(Date aDateFrom) { theDateFrom = aDateFrom ; }

    /** Дата окончания периода */
    public Date getDateTo() { return theDateTo ; }
    public void setDateTo(Date aDateTo) { theDateTo = aDateTo ; }

    /** Строки реестра */
//    @OneToMany(mappedBy = "period", cascade=ALL)
//    public Collection<RegistryEntry> getEntries() { return theEntries ; }
//    public void setEntries(Collection<RegistryEntry> aEntries) { theEntries = aEntries ; }

    /** Строки реестра */
//    private Collection<RegistryEntry> theEntries ;
    /** Дата окончания периода */
    private Date theDateTo ;
    /** Дата начала перода */
    private Date theDateFrom ;
}
