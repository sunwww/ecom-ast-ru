package ru.ecom.mis.ejb.domain.identification.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Таблица сравнения периодов
 * @author azviagin
 *
 */

@Entity
@AIndexes({
	@AIndex (name="FullName", unique = true, properties = {"fullName"})
	,	@AIndex (name="Comparing", unique = true, properties = {"s1s2","e1e2","s1e2","e1s2"})
	})
	@Table(schema="SQLUser")
public class PeriodsComparing extends BaseEntity{
	
	/** Тип результата */
	@Comment("Тип результата")
	public String getResultType() {
		return theResultType;
	}

	public void setResultType(String aResultType) {
		theResultType = aResultType;
	}

	/** Тип результата */
	private String theResultType;
	
	/** Разница VTE1-VTS2 */
	@Comment("Разница VTE1-VTS2")
	public String getE1s2() {
		return theE1s2;
	}

	public void setE1s2(String aE1s2) {
		theE1s2 = aE1s2;
	}

	/** Разница VTE1-VTS2 */
	private String theE1s2;
	
	/** РазницаVTS1-VTE2 */
	@Comment("РазницаVTS1-VTE2")
	public String getS1e2() {
		return theS1e2;
	}

	public void setS1e2(String aS1e2) {
		theS1e2 = aS1e2;
	}

	/** РазницаVTS1-VTE2 */
	private String theS1e2;
	
	/** Разница VTE1-VTE2 */
	@Comment("Разница VTE1-VTE2")
	public String getE1e2() {
		return theE1e2;
	}

	public void setE1e2(String aE1e2) {
		theE1e2 = aE1e2;
	}

	/** Разница VTE1-VTE2 */
	private String theE1e2;
	
	/** Разница VTS1-VTS2 */
	@Comment("Разница VTS1-VTS2")
	public String getS1s2() {
		return theS1s2;
	}

	public void setS1s2(String aS1s2) {
		theS1s2 = aS1s2;
	}

	/** Разница VTS1-VTS2 */
	private String theS1s2;
	
	/** Короткое название */
	@Comment("Короткое название")
	public String getShortName() {
		return theShortName;
	}

	public void setShortName(String aShortName) {
		theShortName = aShortName;
	}

	/** Короткое название */
	private String theShortName;
	
	/** Полное название */
	@Comment("Полное название")
	public String getFullName() {
		return theFullName;
	}

	public void setFullName(String aFullName) {
		theFullName = aFullName;
	}

	/** Полное название */
	private String theFullName;

}
