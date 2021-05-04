/**
 * @author ikouzmin 01.03.2007 9:21:04
 */

package ru.ecom.expomc.ejb.domain.format;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;


@Entity
@Table(name = "EFEXPORTFORMAT", schema = "SQLUser")
@SuppressWarnings("serial")
@Getter
@Setter
public class ExportFormat extends BaseEntity {


    /**
     * Документ импорта
     */
    @ManyToOne
    public ImportDocument getDocument() {
        return document;
    }


    /**
     * HQL запрос к базе данных, на основании которого формируется отчет
     */
    @Column(length = 5000)
    public String getQuery() {
        return query;
    }

    /**
     * XSLT преобразование над XML, сформмированным на базе запроса
     */
    @Column(length = 15000)
    public String getXslt() {
        return xslt;
    }


    /**
     * Документ импорта
     */
    private ImportDocument document;
    /**
     * Отключен
     */
    private boolean disabled;

    /**
     * Комментарий к формату
     */
    private String comment;
    /**
     * Дата, до которой формат действует
     */
    private Date actualDateTo;
    /**
     * Дата с которой начинает действовать формат
     */
    private Date actualDateFrom;

    /**
     * Запрос в формате SQL
     */
    private boolean theNative;

    public boolean isNative() {
        return theNative;
    }

    public void setNative(boolean aNative) {
        theNative = aNative;
    }

    /**
     * HQL запрос к базе данных, на основании которого формируется отчет
     */
    private String query;

    /**
     * XSLT преобразование над XML, сформмированным на базе запроса
     */
    private String xslt;

    /**
     * Адаптер вывода в выходной формат: reserved
     */
    private String outputAdapter;

}
