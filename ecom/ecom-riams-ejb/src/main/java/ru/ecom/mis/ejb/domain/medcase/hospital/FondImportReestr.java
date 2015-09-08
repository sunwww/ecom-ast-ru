package ru.ecom.mis.ejb.domain.medcase.hospital;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Госпитализации данные фонда таблица импорта реестр")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = {
		@AIndex(properties = { "numberFond" }),
		@AIndex(properties = { "importType" })
		})
public class FondImportReestr extends BaseEntity{
	/** Номер направления */
	@Comment("Номер направления")
	public String getNumberFond() {return theNumberFond;}
	public void setNumberFond(String aNumberFond) {theNumberFond = aNumberFond;}

	/** Номер направления */
	private String theNumberFond;
	
	/** Импорт */
	@Comment("Импорт")
	@OneToOne
	public FondImport getImportType() {return theImportType;}
	public void setImportType(FondImport aImportType) {theImportType = aImportType;}

	/** Импорт */
	private FondImport theImportType;
	
	/** Результат */
	@Comment("Результат")
	public String getImportResult() {return theImportResult;}
	public void setImportResult(String aImportResult) {theImportResult = aImportResult;}

	/** Результат */
	private String theImportResult;
}
