package ru.ecom.mis.ejb.form.disability;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.disability.DisabilityBlanks;
import ru.ecom.mis.ejb.domain.disability.DisabilityCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance (clazz = DisabilityBlanks.class)
@Comment("Бланки документов НТ")
@WebTrail(comment = "Бланки документов НТ", nameProperties= "id"
			, view="entityView-dis_blanks.do",
			 shortView="entityShortView-dis_blanks.do"
		)
//@Parent(property="patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Disability/Blanks")
public class DisabilityBlanksForm extends IdEntityForm {
	
	/** Дата получения */
	@Comment("Дата получения")
	@Persist @DateString @DoDateString
	public String getReceiptDate() {return theReceiptDate;}
	public void setReceiptDate(String aReceiptDate) {theReceiptDate = aReceiptDate;}
	
	/** Дата расходования */
	@Comment("Дата расходования")
	@Persist @DateString @DoDateString
	public String getExpenditureDate() {return theExpenditureDate;}
	public void setExpenditureDate(String aExpenditureDate) {theExpenditureDate = aExpenditureDate;}
	
	
	/** Номер */
	@Comment("С номера")
	@Persist @Required
	public String getNumberFrom() {return theNumberFrom;}
	public void setNumberFrom(String aNumber) {theNumberFrom = aNumber;}
	
	/** По номер */
	@Comment("По номер")
	@Persist @Required
	public String getNumberTo() {return theNumberTo;}
	public void setNumberTo(String aNumberTo) {theNumberTo = aNumberTo;}


	/** Специалист */
	@Comment("Специалист получил")
	@Persist
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Специалист */
	private Long theWorkFunction;
	/** По номер */
	private String theNumberTo;
	/** Номер */
	private String theNumberFrom;
	/** Дата расходования */
	private String theExpenditureDate;
	/** Дата получения */
	private String theReceiptDate;
	/** Количество */
	@Comment("Количество")
	@Persist
	public Long getBlanksCount() {return theBlanksCount;}
	public void setBlanksCount(Long aBlankCount) {theBlanksCount = aBlankCount;}

	/** Отступ сверху */
	@Comment("Отступ сверху")
	@Persist
	public Long getSpaceBefore() {return theSpaceBefore;}
	public void setSpaceBefore(Long aSpaceBefore) {theSpaceBefore = aSpaceBefore;}

	/** Отступ слева */
	@Comment("Отступ слева")
	@Persist
	public Long getLeftIndent() {return theLeftIndent;}
	public void setLeftIndent(Long aLeftIndent) {theLeftIndent = aLeftIndent;}

	/** Отступ слева */
	private Long theLeftIndent;
	/** Отступ сверху */
	private Long theSpaceBefore;
	/** Количество */
	private Long theBlanksCount;
}
