package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.contract.voc.VocPositionType;
import ru.ecom.mis.ejb.domain.contract.voc.VocVat;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
	 * Позиция прейскуранта  
	 */
	@Comment("Позиция прейскуранта")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties = {"name"})
	,@AIndex(properties = {"priceList"})
	,@AIndex(properties = {"parent"})
})
	@Getter
	@Setter
public class PricePosition extends BaseEntity{
	/**
	 * Прайс-лист
	 */
	@Comment("Прайс-лист")
	@ManyToOne
	public PriceList getPriceList() {return priceList;}
	/**
	 * Прайс-лист
	 */
	private PriceList priceList;
	@OneToMany(mappedBy="pricePosition", cascade=CascadeType.ALL)
	public List<PriceMedService> getMedServices() {return medServices;}
	/**
	 * Медицинские услуги
	 */
	private List<PriceMedService> medServices;
	/**
	 * Название
	 */
	private String name;
	/**
	 * Код
	 */
	private String code;
	/**
	 * Цена
	 */
	private BigDecimal cost;
	/**
	 * Дата начала действия
	 */
	private Date dateFrom;
	/**
	 * Дата окончания действия
	 */
	private Date dateTo;


	/** Группа */
	@Comment("Группа")
	@OneToOne
	public PricePosition getParent() {return parent;}


	/** Комментарий */
	private String comment;
	/** Группа */
	private PricePosition parent;
	/** Код экспорта */
	private String expParentCode;
	/** Ед.измерения */
	private String expUnit;
	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private Time editTime;
	/** Время создания */
	private Time createTime;
	/** Дата редактирования */
	private Date editDate;
	/** Дата создания */
	private Date createDate;
	/** С НДС */
	private Boolean isVat;
	/** Отображать на инфомате */
	private Boolean isViewInfomat;
	/** Тип услуги */
	@Comment("Тип услуги")
	@OneToOne
	public VocPositionType getPositionType() {return positionType;}

	/** Тип услуги */
	private VocPositionType positionType;
	/**
	 * НДС
	 */
	private BigDecimal costVat;
	
	/** Примечание для печати */
	@Comment("Примечание для печати")
	@Column(length= 1000)
	public String getPrintComment() {return printComment;}

	/** Примечание для печати */
	private String printComment;
	
	/** Ставка налога */
	@Comment("Ставка налога")
	@OneToOne
	public VocVat getTax() {return tax;}
	/** Ставка налога */
	private VocVat tax;

	}
