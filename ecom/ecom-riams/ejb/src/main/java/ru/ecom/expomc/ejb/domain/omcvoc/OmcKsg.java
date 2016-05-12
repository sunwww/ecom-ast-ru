package ru.ecom.expomc.ejb.domain.omcvoc;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Таблица КСГ
 */
@Entity
@Table(name="OMC_KSG",schema="SQLUser")
@NamedQueries(@NamedQuery(name = "OmcKsg.findByTimeAndCode", query = "from OmcKsg where time=:time and code=:code"))

public class OmcKsg extends OmcAbstractVoc {

	public String toString() {
		StringBuilder sb = new StringBuilder() ;
		sb.append(getClass().getName()) ;
		sb.append(":") ;
		sb.append(getId()) ;
		sb.append(" [") ;
		sb.append(" adultLevel=").append(getAdultLevel()) ;
		sb.append(", adultDays=").append(getAdultDays()) ;
		sb.append(", childLevel=").append(getChildLevel()) ;
		sb.append(", childDays=").append(getChildDays()) ;
		sb.append(" ]") ;
		return sb.toString() ;
	}
    /** Уровень для взрослых */
    @Comment("Уровень для взрослых")
    @AFormatFieldSuggest("VUR")
    public BigDecimal getAdultLevel() { return theAdultLevel ; }
    public void setAdultLevel(BigDecimal aAdultLevel) { theAdultLevel = aAdultLevel ; }

    /** Ср. дидельность лечения */
    @Comment("Ср. длидельность лечения")
    @AFormatFieldSuggest("VDL")
    public BigDecimal getAdultDays() { return theAdultDays ; }
    public void setAdultDays(BigDecimal aAdultDays) { theAdultDays = aAdultDays ; }

    /** Уровень для детей */
    @Comment("Уровень для детей")
    @AFormatFieldSuggest("DUR")
    public BigDecimal getChildLevel() { return theChildLevel ; }
    public void setChildLevel(BigDecimal aChildLevel) { theChildLevel = aChildLevel ; }

    /** Ср. длительность для детей */
    @Comment("Ср. длительность для детей")
    @AFormatFieldSuggest("DDL")
    public BigDecimal getChildDays() { return theChildDays ; }
    public void setChildDays(BigDecimal aChildDays) { theChildDays = aChildDays ; }

    /** Ср. длительность для детей */
    private BigDecimal theChildDays ;
    /** Уровень для детей */
    private BigDecimal theChildLevel ;
    /** Ср. дидельность лечения */
    private BigDecimal theAdultDays ;
    /** Уровень для взрослых */
    private BigDecimal theAdultLevel ;
}
