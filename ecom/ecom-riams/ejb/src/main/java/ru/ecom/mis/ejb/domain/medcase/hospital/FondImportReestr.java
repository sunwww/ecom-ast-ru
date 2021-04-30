package ru.ecom.mis.ejb.domain.medcase.hospital;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class FondImportReestr extends BaseEntity{
	/** Номер направления */
	private String numberFond;
	
	/** Импорт */
	@Comment("Импорт")
	@OneToOne
	public FondImport getImportType() {return importType;}

	/** Импорт */
	private FondImport importType;
	
	/** Результат */
	private String importResult;
}
