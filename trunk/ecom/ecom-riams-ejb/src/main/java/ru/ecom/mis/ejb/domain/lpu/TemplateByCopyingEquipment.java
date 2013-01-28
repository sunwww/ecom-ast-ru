package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@AIndexes(
	{
			@AIndex(unique= false, properties = {"copyingEquipment"})
	}
)
public class TemplateByCopyingEquipment extends BaseEntity {
	/** Название шаблона */
	@Comment("Название шаблона")
	public String getNameTemplate() {return theNameTemplate;}
	public void setNameTemplate(String aNameTemplate) {theNameTemplate = aNameTemplate;}

	/** Новое название */
	@Comment("Новое название")
	public String getNewNameTemplate() {return theNewNameTemplate;}
	public void setNewNameTemplate(String aNewNameTemplate) {theNewNameTemplate = aNewNameTemplate;}

	/** Не отображать документ */
	@Comment("Не отображать документ")
	public Boolean getIsNotViewDisplay() {return theIsNotViewDisplay;}
	public void setIsNotViewDisplay(Boolean aNotViewDisplay) {theIsNotViewDisplay = aNotViewDisplay;}

	/** Копировальное оборудование */
	@Comment("Копировальное оборудование")
	@OneToOne
	public CopyingEquipment getCopyingEquipment() {return theCopyingEquipment;}
	public void setCopyingEquipment(CopyingEquipment aCopyingEquipment) {theCopyingEquipment = aCopyingEquipment;}

	/** Печать в txt файл */
	@Comment("Печать в txt файл")
	public Boolean getIsTxtFile() {return theIsTxtFile;}
	public void setIsTxtFile(Boolean aIsTxtFile) {theIsTxtFile = aIsTxtFile;}

	/** Печать в txt файл */
	private Boolean theIsTxtFile;
	/** Копировальное оборудование */
	private CopyingEquipment theCopyingEquipment;
	/** Не отображать документ */
	private Boolean theIsNotViewDisplay;
	/** Новое название */
	private String theNewNameTemplate;
	/** Название шаблона */
	private String theNameTemplate;
}
