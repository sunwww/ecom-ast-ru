package ru.ecom.mis.ejb.form.licence;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.licence.Document;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = Document.class)
@Comment("Документы")
@WebTrail(comment = "Документы", nameProperties = "id",view = "entitySubclassView-doc_document.do"
		,shortView="entityShortSubclassView-doc_document.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@Subclasses(value = { DischargeDocumentForm.class
		,DirectionDocumentForm.class ,BaseMedicalExaminationForm.class
		,DirectionToMicrobiologAnalysisForm.class
		,DischargeDiagnostDocumentForm.class
		,RequitDirectionDocumentForm.class
		,ExternalDocumentForm.class})
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Document")
@Setter
public class DocumentForm extends IdEntityForm{

    /** Серия документа */
	@Persist
    public String getSeriaDoc() { return seriaDoc ; }

    /** Номер документа */
    @Persist
    public String getNumberDoc() { return numberDoc ; }

    /** Дата выдачи */
	@Comment("Дата выдачи")
	@Persist @DoDateString @DateString
	public String getDateIssued() {return dateIssued;}

	/** Кем выдан */
	@Comment("Кем выдан")
	@Persist
	public String getWhomIssued() {return whomIssued;}

    /** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return createDate;}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return createUsername;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DoDateString @DateString
	public String getEditDate() {return editDate;}

	/** Пользователь, последний редактировавший запись */
	@Comment("Пользователь, последний редактировавший запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {return medCase;}
	/** СМО */
	private Long medCase;
	/** Пользователь, последний редактировавший запись */
	private String editUsername;
	/** Дата редактирования */
	private String editDate;
	/** Пользователь, создавший запись */
	private String createUsername;
	/** Дата создания */
	private String createDate;
	/** Кем выдан */
	private String whomIssued;
	/** Дата выдачи */
	private String dateIssued;
  
    /** Серия документа */
    private String seriaDoc ;
    /** Номер документа */
    private String numberDoc ;
	/** Время создания */
	@Comment("Время создания")
	@DoTimeString @TimeString @Persist
	public String getCreateTime() {return createTime;}

	/** Время создания */
	private String createTime;
	

}
