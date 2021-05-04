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
@AIndexes(
	{
			@AIndex(properties = {"copyingEquipment"})
	}
)
@Getter
@Setter
public class TemplateByCopyingEquipment extends BaseEntity {

	/** Копировальное оборудование */
	@Comment("Копировальное оборудование")
	@OneToOne
	public CopyingEquipment getCopyingEquipment() {return copyingEquipment;}

	/** Печать в txt файл */
	private Boolean isTxtFile;
	/** Копировальное оборудование */
	private CopyingEquipment copyingEquipment;
	/** Не отображать документ */
	private Boolean isNotViewDisplay;
	/** Новое название */
	private String newNameTemplate;
	/** Название шаблона */
	private String nameTemplate;
}
