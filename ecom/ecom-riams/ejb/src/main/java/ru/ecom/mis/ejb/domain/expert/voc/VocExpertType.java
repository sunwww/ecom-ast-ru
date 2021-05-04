package ru.ecom.mis.ejb.domain.expert.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@Comment("Предмет экспертизы")
@Getter
@Setter
public class VocExpertType extends VocBaseEntity{
    /** Шаблон даты для формирования номера протокола */
    private String numberGenerateTemplate ;


}
