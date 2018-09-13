package ru.ecom.mis.web.action.patient;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

public class DispensaryJournalForm extends BaseValidatorForm {

    /** Дата начала периода */
    @Comment("Дата начала периода")
    @DateString
    @DoDateString
    @Required
    public String getStartDate() {return theStartDate;}
    public void setStartDate(String aStartDate) {theStartDate = aStartDate;}

    /** Дата окончания периода */
    @Comment("Дата окончания периода")
    @DateString @DoDateString @Required
    public String getFinishDate() {return theFinishDate;}
    public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}

    /** Дата окончания периода */
    private String theFinishDate;
    /** Дата начала периода */
    private String theStartDate;
    
    /** Измененные с  */
    @Comment("Измененные с ")
    @DateString @DoDateString
    public String getChangedDateFrom() {return theChangedDateFrom;}
    public void setChangedDateFrom(String aChangedDateFrom) {theChangedDateFrom = aChangedDateFrom;}
    /** Измененные с  */
    private String theChangedDateFrom ;
    
    /** Имя файла */
    @Comment("Имя файла")
    public String getFilename() {return theFilename;}
    public void setFilename(String aFilename) {theFilename = aFilename;}
    /** Имя файла */
    private String theFilename ;
}
