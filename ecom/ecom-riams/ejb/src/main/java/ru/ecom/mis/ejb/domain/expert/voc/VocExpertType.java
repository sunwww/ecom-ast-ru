package ru.ecom.mis.ejb.domain.expert.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@Comment("Предмет экспертизы")
public class VocExpertType extends VocBaseEntity{
    /** Шаблон даты для формирования номера протокола (использовать значения вида 'yyyy','dd.MM.yyyy')*/
    @Comment("Шаблон даты для формирования номера протокола")
    public String getNumberGenerateTemplate() {return theNumberGenerateTemplate;}
    public void setNumberGenerateTemplate(String aNumberGenerateTemplate) {theNumberGenerateTemplate = aNumberGenerateTemplate;}
    /** Шаблон даты для формирования номера протокола */
    private String theNumberGenerateTemplate ;


}
