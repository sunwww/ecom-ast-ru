package ru.ecom.diary.ejb.domain.protocol.parameter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.diary.ejb.domain.protocol.parameter.user.UserDomain;
import ru.ecom.diary.ejb.domain.protocol.parameter.voc.VocMeasureUnit;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Параметр
 * @author stkacheva
 */
@Entity
@Comment("Параметр")
@Table(schema="SQLUser")
public class Parameter extends BaseEntity{
	/** Наименование */
	@Comment("Наименование")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Единица измерения */
	@Comment("Единица измерения")
	@OneToOne
	public VocMeasureUnit getMeasureUnit() {return theMeasureUnit;}
	public void setMeasureUnit(VocMeasureUnit aMeasureUnit) {theMeasureUnit = aMeasureUnit;}

	/** Максимальное значение */
	@Comment("Максимальное значение")
	public Long getMaximum() {return theMaximum;}
	public void setMaximum(Long aMaximum) {theMaximum = aMaximum;}

	/** Минимальное значение */
	@Comment("Минимальное значение")
	public Long getMinimum() {return theMinimum;}
	public void setMinimum(Long aMinimum) {theMinimum = aMinimum;}

	/** Норма - максимальное значение */
	@Comment("Норма - максимальное значение")
	public Long getNormMaximum() {return theNormMaximum;}
	public void setNormMaximum(Long aNormMaximum) {theNormMaximum = aNormMaximum;}

	/** Норма - минимальное значение */
	@Comment("Норма - минимальное значение")
	public Long getNormMinimum() {return theNormMinimum;}
	public void setNormMinimum(Long aNormMinimum) {theNormMinimum = aNormMinimum;}

	/** Короткое имя */
	@Comment("Короткое имя")
	public String getShortName() {return theShortName;}
	public void setShortName(String aShortName) {theShortName = aShortName;}

	/** Группа */
	@Comment("Группа")
	@ManyToOne
	public ParameterGroup getGroup() {return theGroup;}
	public void setGroup(ParameterGroup aGroup) {theGroup = aGroup;}

	/** Пользовательский справочник */
	@Comment("Пользовательский справочник")
	@OneToOne
	public UserDomain getValueDomain() {return theValueDomain;}
	public void setValueDomain(UserDomain aValueDomain) {theValueDomain = aValueDomain;}

	/** Тип параметра */
	@Comment("Тип параметра")
	public Long getType() {return theType;}
	public void setType(Long aType) {theType = aType;}

	/** Тип параметра */
	private Long theType;
	/** Пользовательский справочник */
	private UserDomain theValueDomain;
	/** Группа */
	private ParameterGroup theGroup;
	/** Короткое имя */
	private String theShortName;
	/** Норма - минимальное значение */
	private Long theNormMinimum;
	/** Норма - максимальное значение */
	private Long theNormMaximum;
	/** Минимальное значение */
	private Long theMinimum;
	/** Максимальное значение */
	private Long theMaximum;
	/** Единица измерения */
	private VocMeasureUnit theMeasureUnit;
	/** Наименование */
	private String theName;
}
