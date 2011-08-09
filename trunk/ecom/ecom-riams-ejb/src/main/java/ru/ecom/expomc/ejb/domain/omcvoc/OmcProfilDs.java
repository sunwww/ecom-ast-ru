package ru.ecom.expomc.ejb.domain.omcvoc;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Среднее кол-во дней для дневного стационара
 */
@Comment("Среднее кол-во дней для дневного стационара")
@Entity
@Table(name = "OMC_PROFIL_DS",schema="SQLUser")
@NamedQueries(
		@NamedQuery(name="OmcProfilDs.findByTimeAndCode", query="from OmcProfilDs where time=:time and code = :code")
) 

public class OmcProfilDs extends OmcAbstractVoc {

//  /** Среднее количество кой-ко дней для взрослого*/
//  @Comment("Среднее количество кой-ко дней для взрослого ")
//  @AFormatFieldSuggest("V_D")
//  public BigDecimal getAdult() { return theAdult ; }
//  public void setAdult1(BigDecimal aAdult) { theAdult = aAdult ; }
//  /** В1 */
//  private BigDecimal theAdult ;
//  
//	/** Среднее количество кой-ко дней для ребенка по 3 уровню */
//	@Comment("Среднее количество кой-ко дней для ребенка по 3 уровню")
//	@AFormatFieldSuggest("D_D")
//	public BigDecimal getChild() { return theChild ; }
//	public void setChild(BigDecimal aChild) { theChild = aChild ; }
//	
//	/** Среднее количество кой-ко дней для ребенка*/
//	private BigDecimal theChild ;
//  

    /** Среднее количество кой-ко дней для взрослого по 1 уровню */
    @Comment("Среднее количество кой-ко дней для взрослого по 1 уровню")
    @AFormatFieldSuggest("V_D1")
    public BigDecimal getAdult1() { return theAdult1 ; }
    public void setAdult1(BigDecimal aAdult1) { theAdult1 = aAdult1 ; }

    /** Среднее количество кой-ко дней для взрослого по 2 уровню */
    @Comment("Среднее количество кой-ко дней для взрослого по 2 уровню")
    @AFormatFieldSuggest("V_D2")
    public BigDecimal getAdult2() { return theAdult2 ; }
    public void setAdult2(BigDecimal aAdult2) { theAdult2 = aAdult2 ; }

    /** Среднее количество кой-ко дней для взрослого по 3 уровню */
    @Comment("Среднее количество кой-ко дней для взрослого по 3 уровню")
    @AFormatFieldSuggest("V_D3")
    public BigDecimal getAdult3() { return theAdult3 ; }
    public void setAdult3(BigDecimal aAdult3) { theAdult3 = aAdult3 ; }

    /** Среднее количество кой-ко дней для ребенка по 1 уровню */
    @Comment("Среднее количество кой-ко дней для ребенка по 1 уровню")
    @AFormatFieldSuggest("D_D1")
    public BigDecimal getChild1() { return theChild1 ; }
    public void setChild1(BigDecimal aChild1) { theChild1 = aChild1 ; }

    /** Среднее количество кой-ко дней для ребенка по 2 уровню */
    @Comment("Среднее количество кой-ко дней для ребенка по 2 уровню")
    @AFormatFieldSuggest("D_D2")
    public BigDecimal getChild2() { return theChild2 ; }
    public void setChild2(BigDecimal aChild2) { theChild2 = aChild2 ; }

    /** Среднее количество кой-ко дней для ребенка по 3 уровню */
    @Comment("Среднее количество кой-ко дней для ребенка по 3 уровню")
    @AFormatFieldSuggest("D_D3")
    public BigDecimal getChild3() { return theChild3 ; }
    public void setChild3(BigDecimal aChild3) { theChild3 = aChild3 ; }

    /** Среднее количество кой-ко дней для ребенка по 3 уровню */
    private BigDecimal theChild3 ;
    /** Среднее количество кой-ко дней для ребенка по 2 уровню */
    private BigDecimal theChild2 ;
    /** Среднее количество кой-ко дней для ребенка по 1 уровню */
    private BigDecimal theChild1 ;
    /** Среднее количество кой-ко дней для взрослого по 3 уровню */
    private BigDecimal theAdult3 ;
    /** Среднее количество кой-ко дней для взрослого по 2 уровню */
    private BigDecimal theAdult2 ;
    /** В1 */
    private BigDecimal theAdult1 ;
}
