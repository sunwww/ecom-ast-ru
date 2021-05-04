/**
    Форма редактирования формата свойств экспорта
    IKO 070228 +++
 */

package ru.ecom.expomc.ejb.services.form.exportformat;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expomc.ejb.domain.format.ExportFormat;
import ru.ecom.expomc.ejb.services.form.impdoc.ImportDocumentForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

import javax.persistence.Column;
import java.sql.ResultSet;

@EntityForm
@Comment("Формат экспорта")
@EntityFormPersistance(clazz= ExportFormat.class)
@Parent(property = "document", parentForm= ImportDocumentForm.class)
@WebTrail(comment = "Формат", nameProperties={"comment"}, view="entityParentEdit-exp_exportFormat.do")
@EntityFormSecurityPrefix("/Policy/Exp/ExportFormat")
@Getter
@Setter
public class ExportFormatForm extends IdEntityForm {

    /** Дата с которой начинает действовать формат */
    @Persist
    @Required
    @DateString
    public String getActualDateFrom() { return actualDateFrom ; }

    /** Дата, до которой формат действует */
    @Persist
    @DateString
    public String getActualDateTo() { return actualDateTo ; }

    /** Комментарий к формату */
    @Persist
    public String getComment() { return comment ; }

    /** Отключен */
    @Persist
    public boolean isDisabled() { return disabled ; }

    /** Документ */
    @Persist
    public long getDocument() { return document ; }


    /** Запрос в формате SQL */
    @Persist
    public boolean isNative() { return theNative ; }
    public void setNative(boolean aNative) { theNative = aNative ; }



    /** HQL запрос к базе данных, на основании которого формируется отчет */
    @Persist
    @Column(length = 5000)
    public String getQuery() { return query ; }


    /** XSLT преобразование над XML, сформмированным на базе запроса */
    @Persist
    @Column(length = 15000)
    public String getXslt() { return xslt ; }


    /** Адаптер вывода в выходной формат: reserved */
    @Persist
    public String getOutputAdapter() { return outputAdapter ; }

    // Non-persistent

    /** Результат запроса */
    public ResultSet getResultSet() {
        return null ;
    }
    //public void setResultSet(ResultSet aResultSet) { resultSet = aResultSet ; }
    /** Результат запроса */
    //private ResultSet resultSet ;



    /** Документ */
    private long document ;
    /** Отключен */
    private boolean disabled ;
    /** Комментарий к формату */
    private String comment ;
    /** Дата, до которой формат действует */
    private String actualDateTo ;
    /** Дата с которой начинает действовать формат */
    private String actualDateFrom ;

    /** Запрос в формате SQL */
    private boolean theNative;

    /** HQL запрос к базе данных, на основании которого формируется отчет */
    private String query ;

    /** XSLT преобразование над XML, сформмированным на базе запроса */
    private String xslt ;

    /** Адаптер вывода в выходной формат: reserved */
    private String outputAdapter ;



}
