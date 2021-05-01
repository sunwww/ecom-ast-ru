package ru.ecom.expert2.domain.financeplan;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.VocE2VidSluch;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
/** Аггрегированная таблица с выполнением финансового плана*/
public class AggregateVolumesFinancePlan extends BaseEntity {

    /** Тип финансового плана */
    private String type ;
    /** Год */
    private Integer year ;

    /** Месяц */
    private Integer month ;

    /** Профиль медицинской помощи */
    private Long medHelpProfile ;

    /** Имя профиля мед. помощи */
    private String medHelpProfileName ;

    /** Отделение */
    private Long department ;

    /** Название отделения */
    private String departmentName ;

    /** Тип коек */
    private Long bedSubType ;

    /** Имя типа коек */
    private String bedSubTypeName ;

    /** КСГ */
    private Long ksg ;

    /** Группа КСГ */
    private Long ksgGroup ;

    /** Имя группы КСГ */
    private String ksgGroupName ;


    /** Имя КСГ */
    private String ksgName ;

    /** Количество по плану */
    private Long planCount ;

    /** Сумма по плану */
    private BigDecimal planCost ;

    /** Количество по факту */
    private Long factCount ;

    /** Сумма по факту */
    private BigDecimal factCost ;

    /** Вид случая */
    @Comment("Вид случая")
    @OneToOne
    public VocE2VidSluch getVidSluch() {return vidSluch;}
    private VocE2VidSluch vidSluch ;

    /** Метод ВМП */
    private String vmp ;

    /** Метод ВМП (название) */
    private String vmpName ;


}
