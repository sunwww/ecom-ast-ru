package ru.ecom.expomc.ejb.domain.registry;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.ecom.expomc.ejb.domain.impdoc.IUrlEditable;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

/**
 * Запись в реестре
 */
@Entity
//@Table(name="EOENTRY")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Comment("Запись в реестре")
//@AIndexes({
//@AIndex(properties = {"time", "InsuranceCompany", "sgroup"})
//        , @AIndex(properties = {"time", "insuranceCompany"})
//        , @AIndex(properties = {"time"})
//        , @AIndex(name="timePolis", properties={"time", "insuranceCompany", "SPolis", "NPolis"})
//        })

@NamedQueries({
@NamedQuery(name = "registryEntry.byGroupWorking"
        , query = "from RegistryEntry where time=:time and insuranceCompany=:company and sgroup='10'"
)
        , @NamedQuery(name = "registryEntry.byGroupPens"
        , query = "from RegistryEntry where time=:time and insuranceCompany=:company and (sgroup='20' or sgroup ='40')"
)
        , @NamedQuery(name = "registryEntry.byGroupChilds"
        , query = "from RegistryEntry where time=:time and insuranceCompany=:company and (sgroup='50' or sgroup ='55')"
)
        , @NamedQuery(name = "registryEntry.byGroupChilds50"
        , query = "from RegistryEntry where time=:time and insuranceCompany=:company and sgroup='50'"
)
        , @NamedQuery(name = "registryEntry.byGroupChilds55"
        , query = "from RegistryEntry where time=:time and insuranceCompany=:company and sgroup='55'"
)
        , @NamedQuery(name = "registryEntry.byGroupOther"
        , query = "from RegistryEntry where time=:time and insuranceCompany=:company and " +
        "(sgroup<>'10' and sgroup<>'20' and sgroup<>'40' and sgroup<>'50' and sgroup<>'55')"
)
        , @NamedQuery(name = "registryEntry.byCompany"
        	, query = "from RegistryEntry where time=:time and insuranceCompany=:company and billNumber=:bill")
        })
@Table(schema="SQLUser")
public class RegistryEntry extends LpuFond implements IImportData, IUrlEditable{

	@Transient
	public String getFio() {
		StringBuilder sb = new StringBuilder() ;
		sb.append(!StringUtil.isNullOrEmpty(getLastname())?getLastname():"") ;
		sb.append(" ") ;
		sb.append(!StringUtil.isNullOrEmpty(getFirstname())?getFirstname():"") ;
		sb.append(" ") ;
		sb.append(!StringUtil.isNullOrEmpty(getMiddlename())?getMiddlename():"") ;
		return sb.toString() ;
	}
	
	@Transient
	public String getPolicySeriesNumber() {
		StringBuilder sb = new StringBuilder() ;
		sb.append(!StringUtil.isNullOrEmpty(getSPolis())?getSPolis():"") ;
		sb.append(" ") ;
		sb.append(!StringUtil.isNullOrEmpty(getNPolis())?getNPolis():"") ;
		return sb.toString() ;
	}
    /** Тариф */
	@Comment("Тариф")
    public BigDecimal getTariff() { return theTariff ; }
    public void setTariff(BigDecimal aTariff) { theTariff = aTariff ; }

    /** Количество койко-дней, принятое для расчета */
    @Comment("Количество койко-дней, принятое для расчета")
    public BigDecimal getCalcBedDays() { return theCalcBedDays ; }
    public void setCalcBedDays(BigDecimal aCalcBedDays) { theCalcBedDays = aCalcBedDays ; }

    /** Количество койко-дней, принятое для расчета */
    private BigDecimal theCalcBedDays ;
    /** Тариф */
    private BigDecimal theTariff ;
    /**
     * Импорт
     */
    public long getTime() {
        return theTime;
    }

    public void setTime(long aTime) {
        theTime = aTime;
    }
    
    /** URL для редактирования */
    @Comment("URL для редактирования") 
	public String getUrlEdit() {
		return theUrlEdit;
	}

	public void setUrlEdit(String aUrlEdit) {
		theUrlEdit = aUrlEdit;
	}

	/** Внутренний код отделения */
	@Comment("Внутренний код отделения")
	public String getInternalDepCode() {
		return theInternalDepCode;
	}

	public void setInternalDepCode(String aInternalDepCode) {
		theInternalDepCode = aInternalDepCode;
	}

	/** Внутренний код отделения */
	private String theInternalDepCode;
	/** URL для редактирования */
	private String theUrlEdit;

    /**
     * Импорт
     */
    private long theTime;

	/** Количество выписанных льготных рецептов */
	@Comment("Количество выписанных льготных рецептов")
	public Integer getPrivilegeRecipeAmount() {
		return thePrivilegeRecipeAmount;
	}

	public void setPrivilegeRecipeAmount(Integer aPrivilegeRecipeAmount) {
		thePrivilegeRecipeAmount = aPrivilegeRecipeAmount;
	}

	/** Количество выписанных льготных рецептов */
	private Integer thePrivilegeRecipeAmount;
	
	/** Признак ДТП */
	@Comment("Признак ДТП")
	public Integer getRoadTrafficInjury() {
		return theRoadTrafficInjury;
	}

	public void setRoadTrafficInjury(Integer aRoadTrafficInjury) {
		theRoadTrafficInjury = aRoadTrafficInjury;
	}

	/** Признак ДТП */
	private Integer theRoadTrafficInjury;

	/** Идентификатор случая */
	@Comment("Идентификатор случая")
	public Integer getMedcase() {
		return theMedcase;
	}

	public void setMedcase(Integer aMedcase) {
		theMedcase = aMedcase;
	}

	/** Идентификатор случая */
	private Integer theMedcase;

}
