package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/*
 * Госзаказ по услугам ЛПУ
 */

@Comment("Госзаказ по услугам ЛПУ")
@Entity
@AIndexes({
		@AIndex(name="lpurender", unique = true, properties = {"lpuCode", "renderCode"} )
})
@Table(schema="SQLUser")
public class OmcLpuGovOrder extends BaseEntity{
	
	/** Код ЛПУ ОМС */
	@Comment("Код ЛПУ ОМС")
	public String getLpuCode() {
		return theLpuCode;
	}

	public void setLpuCode(String aLpuCode) {
		theLpuCode = aLpuCode;
	}

	/** Код ЛПУ ОМС */
	private String theLpuCode;
	

	
	/** Услуга */
	@Comment("Услуга")
	public String getRenderCode() {
		return theRenderCode;
	}

	public void setRenderCode(String aRenderCode) {
		theRenderCode = aRenderCode;
	}

	/** Услуга */
	private String theRenderCode;
	
	/** Разрешение */
	@Comment("Разрешение")
	public Boolean getPermission() {
		return thePermission;
	}

	public void setPermission(Boolean aPermission) {
		thePermission = aPermission;
	}

	/** Разрешение */
	private Boolean thePermission;

}
