package ru.ecom.expomc.ejb.domain.omcvoc;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тариф
 */
@Comment("Тариф")
@Entity
@Table(name = "OMC_TARIFF",schema="SQLUser")
@NamedQueries(
		@NamedQuery(name="OmcTariff.findByTimeAndCode", query="from OmcTariff where time=:time and code = :code")
		) 

public class OmcTariff extends OmcAbstractVoc {
	
	/** Вид ЛПУ */
	@Comment("Вид ЛПУ")
	public String getLpuKind() {
		return theLpuKind;
	}

	public void setLpuKind(String aLpuKind) {
		theLpuKind = aLpuKind;
	}

	/** Вид ЛПУ */
	private String theLpuKind;
	
	/** Профиль медицинской услуги */
	@Comment("Профиль медицинской услуги")
	public String getProfile() {
		return theProfile;
	}

	public void setProfile(String aProfile) {
		theProfile = aProfile;
	}

	/** Профиль медицинской услуги */
	private String theProfile;
	
	/** Цена ДГП1 */
	@Comment("Цена ДГП1")
	public BigDecimal getDgp1() {
		return theDgp1;
	}

	public void setDgp1(BigDecimal aDgp1) {
		theDgp1 = aDgp1;
	}

	/** Цена ДГП1 */
	private BigDecimal theDgp1;
	
	/** Цена ГКБ4 */
	@Comment("Цена ГКБ4")
	public BigDecimal getGkb4() {
		return theGkb4;
	}

	public void setGkb4(BigDecimal aGkb4) {
		theGkb4 = aGkb4;
	}

	/** Цена ГКБ4 */
	private BigDecimal theGkb4;
	

    /** Цена 1 категории */
    @Comment("Цена 1 категории")
    public BigDecimal getX1() { return theX1 ; }
    public void setX1(BigDecimal aX1) { theX1 = aX1 ; }

    /** Цена 2 категории */
    @Comment("Цена 2 категории")
    public BigDecimal getX2() { return theX2 ; }
    public void setX2(BigDecimal aX2) { theX2 = aX2 ; }

    /** Цена 3 категории */
    @Comment("Цена 3 категории")
    public BigDecimal getX3() { return theX3 ; }
    public void setX3(BigDecimal aX3) { theX3 = aX3 ; }

    /** Цена 4 категории */
    @Comment("Цена 4 категории")
    public BigDecimal getX4() { return theX4 ; }
    public void setX4(BigDecimal aX4) { theX4 = aX4 ; }

    /** Цена ОКБ1 */
    @Comment("Цена ОКБ1")
    public BigDecimal getOkb1() { return theOkb1 ; }
    public void setOkb1(BigDecimal aOkb1) { theOkb1 = aOkb1 ; }

    /** Цена ОКБ 2 */
    @Comment("Цена ОКБ 2")
    public BigDecimal getOkb2() { return theOkb2 ; }
    public void setOkb2(BigDecimal aOkb2) { theOkb2 = aOkb2 ; }

    /** Цена ОДКБ */
    @Comment("Цена ОДКБ")
    public BigDecimal getOdkb() { return theOdkb ; }
    public void setOdkb(BigDecimal aOdkb) { theOdkb = aOdkb ; }

    /** Цена ОКВД */
    @Comment("Цена ОКВД")
    public BigDecimal getOkvd() { return theOkvd ; }
    public void setOkvd(BigDecimal aOkvd) { theOkvd = aOkvd ; }

    /** Цена ООД */
    @Comment("Цена ООД")
    public BigDecimal getOod() { return theOod ; }
    public void setOod(BigDecimal aOod) { theOod = aOod ; }

    /** Описание */
    @Comment("Описание")
    public String getDescription() { return theDescription ; }
    public void setDescription(String aDescription) { theDescription = aDescription ; }

    /** Взрослые */
    @Comment("Взрослые")
    public String getAdult() { return theAdult ; }
    public void setAdult(String aAdult) { theAdult = aAdult ; }

    /** Тариф */
    @Comment("Тариф")
    public String getTariff() { return theTariff ; }
    public void setTariff(String aTariff) { theTariff = aTariff ; }
    
    /** Дата начала действия тарифа */
    @Comment("Дата начала действия тарифа")
    public Date getDateStart() { return theDateStart ; }
    public void setDateStart(Date aDateStart) { theDateStart = aDateStart ; }
    
    /** Дата окончания действия тарифа */
    @Comment("Дата окончания действия тарифа")
    public Date getDateEnd() { return theDateEnd ; }
    public void setDateEnd(Date aDateEnd) { theDateEnd = aDateEnd ; }


    /** Тариф */
    private String theTariff ;
    /** Взрослые */
    private String theAdult ;
    /** Описание */
    private String theDescription ;
    /** Цена ООД */
    private BigDecimal theOod ;
    /** Цена ОКВД */
    private BigDecimal theOkvd ;
    /** Цена ОДКБ */
    private BigDecimal theOdkb ;
    /** Цена ОКБ 2 */
    private BigDecimal theOkb2 ;
    /** Цена ОКБ1 */
    private BigDecimal theOkb1 ;
    /** Цена 4 категории */
    private BigDecimal theX4 ;
    /** Цена 3 категории */
    private BigDecimal theX3 ;
    /** Цена 2 категории */
    private BigDecimal theX2 ;
    /** Цена 1 категории */
    private BigDecimal theX1 ;
    /** Дата начала действия тарифа */
    private Date theDateStart ;
    /** Дата окончания действия тарифа */
    private Date theDateEnd ;
}
