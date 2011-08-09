package ru.ecom.mis.ejb.form.licence;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.licence.Licence;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.MinDate;
import ru.nuzmsh.forms.validator.validators.Required;


/*
Лицензирование
*/

@EntityForm
@EntityFormPersistance(clazz = Licence.class)
@Comment("Лицензия")
@WebTrail(comment = "Лицензия №", nameProperties = "numberDoc", view = "entityView-mis_licence.do")
@Parent(property = "lpu", parentForm = MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Licence")
public class LicenceForm extends IdEntityForm  {
    /** Номер документа */
    @Persist
    @Comment("Номер документа")
    @Required
    public String getNumberDoc() {    return theNumberDoc ;}

    /** Основание комисия */
    @Persist
    @Comment("Основание комисия")
    public Long getOsnovanieCom() {    return theOsnovanieCom ;}

    /** Наименование комисия */
    @Persist
    @Comment("Наименование комисия")
    public String getNameOsnovanieCom() {    return theNameOsnovanieCom ;}

    /** Кто выдал */
    @Persist
    @Comment("Кто выдал")
    public String getVidal() {    return theVidal ;}
    //public Long getVidal() {    return theVidal ;}

//    /** Наименование Кто выдал */
//    @Persist
//    @Comment("Наименование Кто выдал")
//    public String getNameVidal() {    return theNameVidal ;}

    /** Кто получил юр. лицо */
    @Persist
    @Comment("Кто получил юр. лицо")
    public Long getLpu() {    return theLpu ;}

    /** Наименование Кто получил */
    @Persist
    @Comment("Наименование Кто получил")
    public String getNameLpu() {    return theNameLpu ;}

    /** Дата выдачи */
    @Persist
    @Comment("Дата выдачи")
    @Required
    @DateString @DoDateString
    @MinDate(value = "01.01.1997")
    public String getDateVidal() {    return theDateVidal ;}

    /** Дата с */
    @Persist
    @Comment("Дата с")
    @DateString @DoDateString
    @Required
    @MinDate(value = "01.01.1997")
    public String getDateStart() {    return theDateStart ;}

    /** Дата по */
    @Persist
    @Comment("Дата по")
    @DateString @DoDateString
    @Required
//    @MinDate(value = theDateStart)
    public String getDateFinish() {    return theDateFinish ;}

    /** Дата продления */
    @Persist
    @Comment("Дата продления")
    @DateString @DoDateString
    public String getDateProlong() {    return theDateProlong ;}

    /** Дата приостановки */
    @Persist
    @Comment("Дата приостановки")
    @DateString @DoDateString
    public String getDateStop() {    return theDateStop ;}

    /** Дата прекращения */
    @Persist
    @Comment("Дата прекращения")
    @DateString @DoDateString
    public String getDateBreak() {    return theDateBreak ;}

    /** Приложения */
   //   @PersistOneToManyOneProperty(valueProperty="typeWork", parentProperty="lpu")
//    @Persist
//    @Comment("Приложения")
//    public Long getPrilogenie() {    return thePrilogenie ;}

    /** Вид деятельности */
    @Persist
    @Comment("Вид деятельности")
    @Required
    public Long getTypeWork() {    return theTypeWork ;}

    /** Наименование деятельности */
    @Persist
    @Comment("Наименование деятельности")
    public String getNameTypeWork() {    return theNameTypeWork ;}

    /** Статус */
    @Persist
    @Comment("Статус")
    public Long getStatus() {    return theStatus ;}

    /** Наименование статус */
    @Persist
    @Comment("Наименование статус")
    public String getNameStatus() {    return theNameStatus ;}

//    /** Действует = 1 Не действует = 0 */
//    @Persist
//    @Comment("Действует = 1 Не действует = 0")
//    public long getUse() {    return theUse ;}

//    /** Действует = 1 Не действует = 0 */
//    public void setUse(long aUse ) {  theUse = aUse ; }
//
//    /** Действует = 1 Не действует = 0 */
//    private long theUse ;

    /** Наименование статус */
    public void setNameStatus(String aNameStatus ) {  theNameStatus = aNameStatus ; }

    /** Наименование статус */
    private String theNameStatus ;

    /** Статус */
    public void setStatus(Long aStatus ) {  theStatus = aStatus ; }

    /** Статус */
    private Long theStatus ;

    /** Наименование деятельности */
    public void setNameTypeWork(String aNameTypeWork ) {  theNameTypeWork = aNameTypeWork ; }

    /** Наименование деятельности */
    private String theNameTypeWork ;

    /** Вид деятельности */
    public void setTypeWork(Long aTypeWork ) {  theTypeWork = aTypeWork ; }

    /** Вид деятельности */
    private Long theTypeWork ;

//    /** Приложения */
//    public void setPrilogenie(Long aPrilogenie ) {  thePrilogenie = aPrilogenie ; }
//
//    /** Приложения */
//    private Long thePrilogenie ;

    /** Дата прекращения */
    public void setDateBreak(String aDateBreak ) {  theDateBreak = aDateBreak ; }

    /** Дата прекращения */
    private String theDateBreak ;

    /** Дата приостановки */
    public void setDateStop(String aDateStop ) {  theDateStop = aDateStop ; }

    /** Дата приостановки */
    private String theDateStop ;

    /** Дата продления */
    public void setDateProlong(String aDateProlong ) {  theDateProlong = aDateProlong ; }

    /** Дата продления */
    private String theDateProlong ;

    /** Дата по */
    public void setDateFinish(String aDateFinish ) {  theDateFinish = aDateFinish ; }

    /** Дата по */
    private String theDateFinish ;

    /** Дата с */
    public void setDateStart(String aDateStart ) {  theDateStart = aDateStart ; }

    /** Дата с */
    private String theDateStart ;

    /** Дата выдачи */
    public void setDateVidal(String aDateVidal ) {  theDateVidal = aDateVidal ; }

    /** Дата выдачи */
    private String theDateVidal ;

    /** Наименование Кто получил */
    public void setNameLpu(String aNameLpu ) {  theNameLpu = aNameLpu ; }

    /** Наименование Кто получил */
    private String theNameLpu ;

    /** Кто получил юр. лицо */
    public void setLpu(Long aLpu ) {  theLpu = aLpu ; }

    /** Кто получил юр. лицо */
    private Long theLpu ;

//    /** Наименование Кто выдал */
//    public void setNameVidal(String aNameVidal ) {  theNameVidal = aNameVidal ; }

    /** Наименование Кто выдал */
//    private String theNameVidal ;

    /** Кто выдал */
    public void setVidal(String aVidal ) {  theVidal = aVidal ; }
//    public void setVidal(Long aVidal ) {  theVidal = aVidal ; }

    /** Кто выдал */
    private String theVidal ;
//    private Long theVidal ;

    /** Наименование комисия */
    public void setNameOsnovanieCom(String aNameOsnovanieCom ) {  theNameOsnovanieCom = aNameOsnovanieCom ; }

    /** Наименование комисия */
    private String theNameOsnovanieCom ;

    /** Основание комисия */
    public void setOsnovanieCom(Long aOsnovanieCom ) {  theOsnovanieCom = aOsnovanieCom ; }

    /** Основание комисия */
    private Long theOsnovanieCom ;

    /** Номер документа */
    public void setNumberDoc(String aNumberDoc ) {  theNumberDoc = aNumberDoc ; }

    /** Номер документа */
    private String theNumberDoc ;

}