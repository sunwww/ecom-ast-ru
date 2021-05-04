package ru.ecom.mis.ejb.domain.lpu;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity(name = "FreeHospBed")
@Table
@Getter
@Setter
public class FreeHospBed  extends BaseEntity {
    /** ЛПУ */
    @Comment("ЛПУ")
    public Long getLpu() { return lpu ; }

    private Long lpu;

    /** Мужские, с кислородом */
    private Long menO2;
    
    /** Мужские, без кислорода */
    private Long menNoO2;

    /** Женские, с кислородом */
    private Long womenO2;

    /** Женские, без кислорода */
    private Long womenNoO2;

    /** Дата редактирования */
    private Date editDate;

    /** Время редактрования */
    private Time editTime;

    /** Пользователь, который последний редактировал запись */
    private String editUsername;
}