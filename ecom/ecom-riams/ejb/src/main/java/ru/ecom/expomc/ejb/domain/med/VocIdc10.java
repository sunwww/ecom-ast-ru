package ru.ecom.expomc.ejb.domain.med;

import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.voc.VocSexPermission;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Comment("МКБ 10")
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties={"code"}),
	@AIndex(properties={"code","name"})
})
public class VocIdc10 extends VocIdCodeName {
	
	/** Разрешен по полу */
	@Comment("Разрешен по полу")
	@OneToOne
	public VocSexPermission getSexPermission() {
		return theSexPermission;
	}

	public void setSexPermission(VocSexPermission aSexPermission) {
		theSexPermission = aSexPermission;
	}

	/** Разрешен по полу */
	private VocSexPermission theSexPermission;
	
	/** Разрешен в ОМС */
	@Comment("Разрешен в ОМС")
	public Boolean getOmcPermission() {
		return theOmcPermission;
	}

	public void setOmcPermission(Boolean aOmcPermission) {
		theOmcPermission = aOmcPermission;
	}

	/** Разрешен в ОМС */
	private Boolean theOmcPermission;

	/** Начальный возраст */
	@Comment("Начальный возраст")
	public Double getAgeFrom() {
		return theAgeFrom;
	}

	public void setAgeFrom(Double aAgeFrom) {
		theAgeFrom = aAgeFrom;
	}

	/** Начальный возраст */
	private Double theAgeFrom;
	
	/** Конечный возраст */
	@Comment("Конечный возраст")
	public Double getAgeTo() {
		return theAgeTo;
	}

	public void setAgeTo(Double aAgeTo) {
		theAgeTo = aAgeTo;
	}

	/** Конечный возраст */
	private Double theAgeTo;
	
	/** Экстренность */
	@Comment("Экстренность")
	public Boolean getEmergency() {
		return theEmergency;
	}

	public void setEmergency(Boolean aEmergency) {
		theEmergency = aEmergency;
	}

	/** Экстренность */
	private Boolean theEmergency;
	
	/** Неактуаленный */
	@Comment("Неактуаленный")
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNoActuality) {theNoActuality = aNoActuality;}

	/** Неактуаленный */
	private Boolean theNoActuality;
	
	/** Используется в ОМС */
	@Comment("Используется в ОМС")
	public Boolean getUseOmc() {return theUseOmc;}
	public void setUseOmc(Boolean aUseOmc) {theUseOmc = aUseOmc;}

	/** Класс или блок */
	@Comment("Класс или блок")
	public Boolean getIsBlock() {return theIsBlock;}
	public void setIsBlock(Boolean aIsBlock) {theIsBlock = aIsBlock;}

	/** Родитель */
	@Comment("Родитель")
	@OneToOne
	public VocIdc10 getParent() {return theParent;}
	public void setParent(VocIdc10 aParent) {theParent = aParent;}

	/** Родитель */
	private VocIdc10 theParent;
	/** Класс или блок */
	private Boolean theIsBlock;
	/** Используется в ОМС */
	private Boolean theUseOmc;

	/**Код в промеде**/
	private String promedCode;
	@Comment("Код в промеде")
	public String getPromedCode() {
		return promedCode;
	}
	public void setPromedCode(String promedCode) {
		this.promedCode = promedCode;
	}
	
	/** Разрешено использовать без уточнения */
	@Comment("Разрешено использовать без уточнения")
	public Boolean getIsPermitWithoutDot() {return theIsPermitWithoutDot;}
	public void setIsPermitWithoutDot(Boolean aIsPermitWithoutDot) {theIsPermitWithoutDot = aIsPermitWithoutDot;}
	/** Разрешено использовать без уточнения */
	private Boolean theIsPermitWithoutDot ;

	/** Дата начала актуальности */
	@Comment("Дата начала актуальности")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}
	/** Дата начала актуальности */
	private Date theDateFrom ;

	/** Дата окончания актуальности */
	@Comment("Дата окончания актуальности")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}
	/** Дата окончания актуальности */
	private Date theDateTo ;

	/** Резрешен для COVID-19 */
	@Comment("Резрешен для COVID-19")
	public Boolean getAllowCovid() {return theAllowCovid;}
	public void setAllowCovid(Boolean aAllowCovid) {theAllowCovid = aAllowCovid;}
	private Boolean theAllowCovid ;
}
