package ru.ecom.diary.ejb.domain.protocol.parameter;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.diary.ejb.domain.protocol.parameter.user.UserDomain;
import ru.ecom.diary.ejb.domain.protocol.parameter.voc.VocMeasureUnit;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.List;

/**
 * Параметр
 * @author stkacheva
 */
@Entity
@Comment("Параметр")
@Table(schema="SQLUser")
@AIndexes({
    @AIndex(properties="group")
    }) 
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class Parameter extends BaseEntity{
	
	/** Внешний код */
	private String externalCode;
	/** Код */
	private String code;


	/** Единица измерения */
	@Comment("Единица измерения")
	@OneToOne
	public VocMeasureUnit getMeasureUnit() {return measureUnit;}

	/** Группа */
	@Comment("Группа")
	@ManyToOne
	public ParameterGroup getGroup() {return group;}

	/** Пользовательский справочник */
	@Comment("Пользовательский справочник")
	@OneToOne
	public UserDomain getValueDomain() {return valueDomain;}

	/** Тип параметра */
	private Long type;
	/** Пользовательский справочник */
	private UserDomain valueDomain;
	/** Группа */
	private ParameterGroup group;
	/** Короткое имя */
	private String shortName;
	/** Норма - минимальное значение */
	@Deprecated
	private Long normMinimum;
	/** Норма - максимальное значение */
	@Deprecated
	private Long normMaximum;
	/** Минимальное значение */
	@Deprecated
	private Long minimum;
	/** Максимальное значение */
	@Deprecated
	private Long maximum;
	
	/** Кол-во знаков после запятой */
	private Long cntDecimal;
	/** Минимальное значение */
	@Deprecated
	private String minimumBD;
	/** Максимальное значение */
	@Deprecated
	private String maximumBD;
	/** Норма - максимальное значение */
	@Deprecated
	private String normMaximumBD;
	/** Норма - минимальное значение */
	@Deprecated
	private String normMinimumBD;
	/** Единица измерения */
	private VocMeasureUnit measureUnit;
	/** Наименование */
	private String name;

	/** Значение по умолчанию для текстового поля */
	private String valueTextDefault;

	/** Список референтных значений */
	@Comment("Список референтных значений")
	@OneToMany (mappedBy = "parameter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<ParameterReferenceValue> getReferenceValues() {return referenceValues;}
	/** Список референтных значений */
	private List<ParameterReferenceValue> referenceValues ;

	/** Только для пола */
	@Comment("Только для пола")
	@OneToOne
	public VocSex getForSex() {return forSex;}
	/** Только для пола */
	private VocSex forSex ;
}
