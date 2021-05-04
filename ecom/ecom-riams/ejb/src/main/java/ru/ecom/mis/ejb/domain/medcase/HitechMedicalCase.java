package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.voc.VocKindHighCare;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMethodHighCare;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Случай оказания высокотехнологичной мед. помощи (ВМП)")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="medCase")
})
@Getter
@Setter
public class HitechMedicalCase extends BaseEntity {

	/** Случай оказания мед. помощи */
	@Comment("Случай оказания мед. помощи")
	@ManyToOne
	public MedCase getMedCase() {return medCase;}
	private MedCase medCase;
	
	/** Вид ВМП */
	@Comment("Вид ВМП")
	@OneToOne
	public VocKindHighCare getKind() {return kind;}
	private VocKindHighCare kind;
	
	/** Метод ВМП */
	@Comment("Метод ВМП")
	@OneToOne
	public VocMethodHighCare getMethod() {return method;}
	private VocMethodHighCare method;
	
	/** Дата выдачи талона нв ВМП */
	private Date ticketDate;
	
	/** Дата планируемой госпитализации */
	private Date planHospDate;
	
	/** Дата создания */
	private Date createDate;
	
	/** Дата редактирования */
	private Date editDate;
	
	/** Пользователь, создавший запись */
	private String createUsername;
	
	/** Пользователь, редактировавший запись */
	private String editUsername;

	/** Количество установленных стентов */
	private Long stantAmount ;

	/** Номер талона ВМП */
	private String ticketNumber ;
}
