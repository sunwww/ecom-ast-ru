package ru.ecom.mis.ejb.domain.equipment;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.equipment.voc.VocOKOF;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author  vtsybulin
 */
@Entity
@Comment("Медицинское оборудование")
@Getter
@Setter
public class MedicalEquipment extends Equipment {
	
	/** Количество */
	private Float amount;

	/** ИД в системе Парус */
	private String parusCode;

	/** Группа бухгалтерии */
	private String groupName;
	
	/** ОКОФ-текст */
	private String okofText;

	/** Износ */
	private BigDecimal wearout;
	
	/** Остаточная стоимость */
	private BigDecimal residualValue;
	
	/** Начальный износ */
	private BigDecimal startWearout;
	
	/** ОКОФ */
	@Comment("ОКОФ")
	@OneToOne
	public VocOKOF getOkof() {return okof;}
	private VocOKOF okof;

	/** Инвентарный номер */
	private String inventoryNumber;

	/** В рамках какой програмы (мероприятия) было поставлено */
	private String projectName;
	
	/** Источник финансирования */
	@Comment("Источник финансирования")
	@OneToOne
	public VocServiceStream getFundingStream() {return fundingStream;}
	private VocServiceStream fundingStream;
	
	/** Цена */
	private BigDecimal price;
	
	/** Дата ввода в эксплуатацию */
	private Date startDate;
 
}
