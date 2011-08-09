package ru.ecom.expomc.ejb.domain.omcvoc;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Index;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Разрешение ОМС по МКБ
 * @author azviagin
 *
 */

@Comment("Разрешение ОМС по МКБ")
@Entity
@AIndexes({
	@AIndex(name="IdcDates", properties={"idcCode","VTE","VTS"})
	
})
@Table(name="OmcIdcPermission",schema="SQLUser")
@org.hibernate.annotations.Table(appliesTo="OmcIdcPermission", indexes = { 
		@org.hibernate.annotations.Index(name="OmcIdcPermissionDates", columnNames={"VTS","VTE"} ) 
		} )
public class OmcIdcPermission extends BaseEntity{
	
	/** Код МКБ */
	@Comment("Код МКБ")
	@Index(name="OmcIdcPermisionidc")
	public String getIdcCode() {
		return theIdcCode;
	}

	public void setIdcCode(String aIdcCode) {
		theIdcCode = aIdcCode;
	}

	/** Код МКБ */
	private String theIdcCode;
	
	/** Разрешено в поликлинике */
	@Comment("Разрешено в поликлинике")
	public Boolean getPolyclinicEnable() {
		return thePolyclinicEnable;
	}

	public void setPolyclinicEnable(Boolean aPolyclinicEnable) {
		thePolyclinicEnable = aPolyclinicEnable;
	}

	/** Разрешено в поликлинике */
	private Boolean thePolyclinicEnable;
	
	/** Разрешено в стационаре */
	@Comment("Разрешено в стационаре")
	public Boolean getHospitalEnable() {
		return theHospitalEnable;
	}

	public void setHospitalEnable(Boolean aHospitalEnable) {
		theHospitalEnable = aHospitalEnable;
	}

	/** Разрешено в стационаре */
	private Boolean theHospitalEnable;
	
	/** Дата начала действия */
	@Comment("Дата начала действия")
	public Timestamp getVTS() {
		return theVTS;
	}

	public void setVTS(Timestamp aVTS) {
		theVTS = aVTS;
	}

	/** Дата начала действия */
	private Timestamp theVTS;
	
	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	public Timestamp getVTE() {
		return theVTE;
	}

	public void setVTE(Timestamp aVTE) {
		theVTE = aVTE;
	}

	/** Дата окончания действия */
	private Timestamp theVTE;
	

}
