package ru.ecom.mis.ejb.domain.disability;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityDocumentExportStatus;
import ru.ecom.mis.ejb.domain.disability.voc.VocAnnulReason;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by vtsybulin on 30.05.2017.
 */
@Comment("Сведения о номере больничного листа")
@Entity
@Getter
@Setter
public class ElectronicDisabilityDocumentNumber extends BaseEntity {

    /** Номер электронного больничного листа */
    private String number ;

    /** Документ нетрудоспособности */
    @Comment("Документ нетрудоспособности")
    @OneToOne
    public DisabilityDocument getDisabilityDocument() {return disabilityDocument;}
    /** Документ нетрудоспособности */
    private DisabilityDocument disabilityDocument ;

    /** Пользователь, занявший номер ЭЛН */
    private String username ;

    /** Статус экспорта */
    @Comment("Статус экспорта")
    @ManyToOne
    public VocDisabilityDocumentExportStatus getStatus() {return status;}
    /** Статус экспорта */
    private VocDisabilityDocumentExportStatus status ;

    /** Дата последнего экспорта */
    private Date exportDate ;
    
    /** Время последнего экспорта */
    private Time exportTime;


    /** Дата резерва */
    private Date reserveDate ;

    /** Время резерва */
    private Time reserveTime ;

    /** Дата получения номера от ФСС */
    private Date createDate ;

    /** Последний ХЕШ ЭЛН */
    private String lastHash ;

    /** Дата аннулирования документа */
    private Date annulDate ;

    /** Комментарий */
    private String comment ;

    /** Причина аннулирования */
    @Comment("Причина аннулирования")
    @OneToOne
    public VocAnnulReason getAnnulReason() {return annulReason;}
    /** Причина аннулирования */
    private VocAnnulReason annulReason ;
}
