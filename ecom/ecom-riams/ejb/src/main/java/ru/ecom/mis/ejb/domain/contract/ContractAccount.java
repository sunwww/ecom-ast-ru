package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.uc.privilege.domain.Privilege;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
	/**
	 * Договорной счет
	 */
	@Comment("Договорной счет")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties = {"servedPerson"})
	,@AIndex(properties = {"contract"})
})
	@Getter
	@Setter
	@EntityListeners(DeleteListener.class)
@UnDeletable
public class ContractAccount extends BaseEntity{

	private Privilege privilege;

	@Comment("Льгота")
	@OneToOne
	public Privilege getPrivilege() {
		return privilege;
	}

	/** Признак удаленной записи */
	private Boolean isDeleted ;

	@OneToMany(mappedBy="account", cascade=CascadeType.ALL)
	public List<ContractAccountOperation> getOperations() {return operations;}
	/**
	 * Операции по счету
	 */
	private List<ContractAccountOperation> operations;
	/** Сумма баланса */
	private BigDecimal balanceSum;
	
	/** Резервированная сумма */
	private BigDecimal reservationSum;
	/** Дата открытия */
	private Date dateFrom;
	/** Дата закрытия */
	private Date dateTo;
	/** Блокирован */
	private Boolean block;
	
	@Comment("Мед. услуги")
	@OneToMany(mappedBy="account", cascade=CascadeType.ALL)
	public List<ContractAccountMedService> getMedService() {return medService;}

	/**
	 * Мед. услуги
	 */
	private List<ContractAccountMedService> medService;

	/** Пользователь, последний изменивший запись */
	private String editUsername;
	/** Время, последнего изменения */
	private Time editTime;
	/** Дата последнего изменения */
	private Date editDate;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Время создания */
	private Time createTime;
	/** Дата создания */
	private Date createDate;
	
	@Transient
	public String getInfo() {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
		StringBuilder res=new StringBuilder().append(dateFrom!=null?format.format(dateFrom):"нет даты открытия") ;
		if (dateFrom!=null) {
			res.append("-").append(dateTo!=null?format.format(dateTo):"нет даты окончания") ;
		} 
		if (block!=null && block) res.append("(счет заблокирован");
		return res.toString();
	}
	
	/** Мед.договор */
	@Comment("Мед.договор")
	@OneToOne
	public MedContract getContract() {return contract;}

	/** Мед.договор */
	private MedContract contract;
	
	/** Скидка по умолчанию */
	private BigDecimal discountDefault;
	
	/** Номер счета */
	private String accountNumber;
	
	/** Оплачен */
	private Boolean isFinished;
	
	/** Период по */
	private Date periodTo;
	/** Период с */
	private Date periodFrom;
}
