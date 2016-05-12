/**
 *
 * @author ikouzmin 01.03.2007 9:21:04
 */

package ru.ecom.expomc.ejb.domain.format;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;


@Entity
@Table(name="EFEXPORTFORMAT",schema="SQLUser")
@SuppressWarnings("serial")
public class ExportFormat  extends BaseEntity {

    /** Дата с которой начинает действовать формат */
    public Date getActualDateFrom() { return theActualDateFrom ; }
    public void setActualDateFrom(Date aActualDateFrom) { theActualDateFrom = aActualDateFrom ; }

    /** Дата, до которой формат действует */
    public Date getActualDateTo() { return theActualDateTo ; }
    public void setActualDateTo(Date aActualDateTo) { theActualDateTo = aActualDateTo ; }

    /** Комментарий к формату */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Отключен */
    public boolean isDisabled() { return theDisabled ; }
//    public boolean getDisabled() { return theDisabled ; }
    public void setDisabled(boolean aDisabled) { theDisabled = aDisabled ; }

    /** Документ импорта */
    @ManyToOne
    public ImportDocument getDocument() { return theDocument ; }
    public void setDocument(ImportDocument aDocument) { theDocument = aDocument ; }


    /** Запрос в формате SQL */
    public boolean isNative() { return theNative ; }
//    public boolean getNative() { return theNative ; }
    public void setNative(boolean aNative) { theNative = aNative ; }


    /** HQL запрос к базе данных, на основании которого формируется отчет */
    @Column(length = 5000)
    public String getQuery() { return theQuery ; }
    public void setQuery(String aQuery) { theQuery = aQuery ; }


    /** XSLT преобразование над XML, сформмированным на базе запроса */
    @Column(length = 15000)
    public String getXslt() { return theXslt ; }
    public void setXslt(String aXslt) { theXslt = aXslt ; }


    /** Адаптер вывода в выходной формат: reserved */
    public String getOutputAdapter() { return theOutputAdapter ; }
    public void setOutputAdapter(String aOutputAdapter) { theOutputAdapter = aOutputAdapter ; }


      /** Документ импорта */
    private ImportDocument theDocument ;
    /** Отключен */
    private boolean theDisabled ;

    /** Комментарий к формату */
    private String theComment ;
    /** Дата, до которой формат действует */
    private Date theActualDateTo ;
    /** Дата с которой начинает действовать формат */
    private Date theActualDateFrom ;

    /** Запрос в формате SQL */
    private boolean theNative;

    /** HQL запрос к базе данных, на основании которого формируется отчет */
    private String theQuery ;

    /** XSLT преобразование над XML, сформмированным на базе запроса */
    private String theXslt ;

    /** Адаптер вывода в выходной формат: reserved */
    private String theOutputAdapter ;

}
