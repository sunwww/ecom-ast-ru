package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.WorkBook;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz= WorkBook.class)
@Comment("Должность")
@WebTrail(comment = "Должность", nameProperties= "namePost", view="entityView-mis_workBook.do")
@Parent(property="worker", parentForm=WorkerForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkBook")
public class WorkBookForm extends IdEntityForm {
    /** Сотрудник */
    @Persist
    @Comment("Сотрудник")
    public Long getWorker() {    return theWorker ;}
    public void setWorker(Long aWorker ) {  theWorker = aWorker ; }

    /** Номер трудовой книжки */
    @Persist
    @Comment("Номер трудовой книжки")
    @Required
    public Integer getNumberBook() {    return theNumberBook ;}
    public void setNumberBook(Integer aNumberBook ) {  theNumberBook = aNumberBook ; }
    
    /** Серия трудовой книжки */
	@Comment("Серия трудовой книжки")
	@Persist
	@Required
	public String getSeriesBook() {
		return theSeriesBook;
	}

	public void setSeriesBook(String aSeriesBook) {
		theSeriesBook = aSeriesBook;
	}

	/** Серия трудовой книжки */
	private String theSeriesBook;

    /** Дата заведения */
    @Persist
    @Comment("Дата заведения")
    @DateString @DoDateString
    public String getDateGet() {    return theDateGet ;}
    public void setDateGet(String aDateGet ) {  theDateGet = aDateGet ; }

    /** ФИО заводившего */
    @Persist
    @Comment("ФИО заводившего")
    public String getGetter() {    return theGetter ;}
    public void setGetter(String aGetter ) {  theGetter = aGetter ; }

    /** Text */
    @Persist
    @Comment("Text")
    public String getText() {    return theText ;}
    public void setText(String aText ) {  theText = aText ; }

    /** Text */
    private String theText ;
    /** ФИО заводившего */
    private String theGetter ;
    /** Дата заведения */
    private String theDateGet ;
    /** Номер трудовой книжки */
    private Integer theNumberBook ;
    /** Сотрудник */
    private Long theWorker ;

    /** Должность */
    @Persist
    @Comment("Должность")
    @Required
    public Long getPost() {    return thePost ;}
    public void setPost(Long aPost ) {  thePost = aPost ; }

    /** Наименование должности */
    @Persist
    @Comment("Наименование должности")
    public String getNamePost() {    return theNamePost ;}
    public void setNamePost(String aNamePost ) {  theNamePost = aNamePost ; }

    /** Дата принятия */
    @Persist
    @Comment("Дата принятия")
    @DateString @DoDateString
    @Required
    public String getDateOn() {    return theDateOn ;}
    public void setDateOn(String aDateOn ) {  theDateOn = aDateOn ; }

    /** Дата увольнения */
    @Persist
    @Comment("Дата увольнения")
    @DateString @DoDateString
    public String getDateOff() {    return theDateOff ;}
    public void setDateOff(String aDateOff ) {  theDateOff = aDateOff ; }

    /** На основании чего внесена запись о приеме (документ, его дата и номер) */
    @Persist
    @Comment("На основании чего внесена запись о приеме (документ, его дата и номер)")
    public String getBaseForCome() {    return theBaseForCome ;}
    public void setBaseForCome(String aBaseForCome ) {  theBaseForCome = aBaseForCome ; }

    /** Причина увольнения */
    @Persist
    @Comment("Причина увольнения")
    public Long getLeave() {    return theLeave ;}
    public void setLeave(Long aLeave ) {  theLeave = aLeave ; }

    /** На основании чего внесена запись об увольнении (документ, его дата и номер) */
    @Persist
    @Comment("На основании чего внесена запись об увольнении (документ, его дата и номер)")
    public String getBaseFroLeave() {    return theBaseFroLeave ;}
    public void setBaseFroLeave(String aBaseFroLeave ) {  theBaseFroLeave = aBaseFroLeave ; }

    /** Совместительство */
    @Persist
    @Comment("Совместительство")
    public Long getCombo() {    return theCombo ;}
    public void setCombo(Long aCombo ) {  theCombo = aCombo ; }

    /** Совместительство */
    @Persist
    @Comment("Совместительство")
    public String getNameCombo() {    return theNameCombo ;}
    public void setNameCombo(String aNameCombo ) {  theNameCombo = aNameCombo ; }

    /** Количество всего ставок */
    @Persist
    @Comment("Количество всего ставок")
    public Integer getFullRate() {    return theFullRate ;}
    public void setFullRate(Integer aFullRate ) {  theFullRate = aFullRate ; }

    /** Количество бюджетных ставок */
    @Persist
    @Comment("Количество бюджетных ставок")
    @Required
    public Integer getBudjetRate() {    return theBudjetRate ;}
    public void setBudjetRate(Integer aBudjetRate ) {  theBudjetRate = aBudjetRate ; }

    /** Количество внебюджетных ставок */
    @Persist
    @Comment("Количество внебюджетных ставок")
    public Integer getOffBudjetRate() {    return theOffBudjetRate ;}
    public void setOffBudjetRate(Integer aOffBudjetRate ) {  theOffBudjetRate = aOffBudjetRate ; }

    /** Количество внебюджетных ставок */
    private Integer theOffBudjetRate ;
    /** Количество бюджетных ставок */
    private Integer theBudjetRate ;
    /** Количество всего ставок */
    private Integer theFullRate ;
    /** Совместительство */
    private String theNameCombo ;
    /** Совместительство */
    private Long theCombo ;
    /** На основании чего внесена запись об увольнении (документ, его дата и номер) */
    private String theBaseFroLeave ;
    /** Причина увольнения */
    private Long theLeave ;
    /** На основании чего внесена запись о приеме (документ, его дата и номер) */
    private String theBaseForCome ;
    /** Дата увольнения */
    private String theDateOff ;
    /** Дата принятия */
    private String theDateOn ;
    /** Наименование должности */
    private String theNamePost ;
    /** Должность */
    private Long thePost ;
}
