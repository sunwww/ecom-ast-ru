package ru.ecom.mis.ejb.domain.licence;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Date;

@Entity
@Comment("Акт в военкомат")
@Getter
@Setter
public class RequitDirectionDocument extends InternalDocuments {
	/** Номер направления */
	private String orderNumber;
	
	/** Дата направления */
	private Date orderDate;
	
	/** Направитель */
	private String orderOffice;
	
	/** Данные объективного исследования */
	@Comment("Данные объективного исследования")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getResearch() {return research;}
	/** Данные объективного исследования */
	private String research;
	
	/** Результаты диагностических исследований */
	@Comment("Результаты диагностических исследований")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getLabResearch() {return labResearch;}
	/** Результаты диагностических исследований */
	private String labResearch;
	
	/** Жалобы */
	@Comment("Жалобы")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getAbuses() {return abuses;}
	/** Жалобы */
	private String abuses;
}
