package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
@Entity
@Table(schema="SQLUser")
@AIndexes({
    @AIndex(properties="parent")
    })
@Getter
@Setter
public class CopyingEquipment extends BaseEntity {

	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return lpu;}
	/** Комментарии */
	private String comment;
	/** Название */
	private String name;
	/** ЛПУ */
	private MisLpu lpu;
	/** Модель */
	private String model;
	/** Серийный номер */
	private String serialNumber;
	/** Учетный номер */
	private String accountNumber;
	/** IP адрес */
	private String ipaddress;
	/** Печать в txt файл */
	private Boolean isTxtFile;

	/** Команда */
	private String commandPrintTxt;

	/** Маска файла */
	private String maskFiles;
	
	/** Основной принтер */
	@Comment("Основной принтер")
	@OneToOne
	public CopyingEquipment getParent() {return parent;}

	/** Основной принтер */
	private CopyingEquipment parent;
}
