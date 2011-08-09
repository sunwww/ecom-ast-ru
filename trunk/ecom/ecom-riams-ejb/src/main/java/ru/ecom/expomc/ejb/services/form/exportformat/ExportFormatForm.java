/**
    Форма редактирования формата свойств экспорта
    IKO 070228 +++
 */

package ru.ecom.expomc.ejb.services.form.exportformat;

import java.sql.ResultSet;

import javax.persistence.Column;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expomc.ejb.domain.format.ExportFormat;
import ru.ecom.expomc.ejb.services.form.impdoc.ImportDocumentForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@Comment("Формат экспорта")
@EntityFormPersistance(clazz= ExportFormat.class)
@Parent(property = "document", parentForm= ImportDocumentForm.class)
@WebTrail(comment = "Формат", nameProperties={"comment"}, view="entityParentEdit-exp_exportFormat.do")
@EntityFormSecurityPrefix("/Policy/Exp/ExportFormat")

public class ExportFormatForm extends IdEntityForm {

    /** Дата с которой начинает действовать формат */
    @Persist
    @Required
    @DateString
    public String getActualDateFrom() { return theActualDateFrom ; }
    public void setActualDateFrom(String aActualDateFrom) { theActualDateFrom = aActualDateFrom ; }

    /** Дата, до которой формат действует */
    @Persist
    @DateString
    public String getActualDateTo() { return theActualDateTo ; }
    public void setActualDateTo(String aActualDateTo) { theActualDateTo = aActualDateTo ; }

    /** Комментарий к формату */
    @Persist
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Отключен */
    @Persist
    public boolean isDisabled() { return theDisabled ; }
    public void setDisabled(boolean aDisabled) { theDisabled = aDisabled ; }

    /** Документ */
    @Persist
    public long getDocument() { return theDocument ; }
    public void setDocument(long aDocument) { theDocument = aDocument ; }


    /** Запрос в формате SQL */
    @Persist
    public boolean isNative() { return theNative ; }
    public void setNative(boolean aNative) { theNative = aNative ; }



    /** HQL запрос к базе данных, на основании которого формируется отчет */
    @Persist
    @Column(length = 5000)
    public String getQuery() { return theQuery ; }
    public void setQuery(String aQuery) { theQuery = aQuery ; }


    /** XSLT преобразование над XML, сформмированным на базе запроса */
    @Persist
    @Column(length = 15000)
    public String getXslt() { return theXslt ; }
    public void setXslt(String aXslt) { theXslt = aXslt ; }


    /** Адаптер вывода в выходной формат: reserved */
    @Persist
    public String getOutputAdapter() { return theOutputAdapter ; }
    public void setOutputAdapter(String aOutputAdapter) { theOutputAdapter = aOutputAdapter ; }

    // Non-persistent

    /** Результат запроса */
    public ResultSet getResultSet() {
        ResultSet theResultSet ;
        theResultSet = null;

        //HibernateEntityManagerFactory factory=HibernateEntityManagerFactory.getSessionFactory();
        return theResultSet ;
    }
    //public void setResultSet(ResultSet aResultSet) { theResultSet = aResultSet ; }
    /** Результат запроса */
    //private ResultSet theResultSet ;



    /** Документ */
    private long theDocument ;
    /** Отключен */
    private boolean theDisabled ;
    /** Комментарий к формату */
    private String theComment ;
    /** Дата, до которой формат действует */
    private String theActualDateTo ;
    /** Дата с которой начинает действовать формат */
    private String theActualDateFrom ;

    /** Запрос в формате SQL */
    private boolean theNative;

    /** HQL запрос к базе данных, на основании которого формируется отчет */
    private String theQuery ;

    /** XSLT преобразование над XML, сформмированным на базе запроса */
    private String theXslt ;

    /** Адаптер вывода в выходной формат: reserved */
    private String theOutputAdapter ;



}
