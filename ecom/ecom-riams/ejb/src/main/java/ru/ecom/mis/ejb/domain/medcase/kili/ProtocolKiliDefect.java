
package ru.ecom.mis.ejb.domain.medcase.kili;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocKiliDefect;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class ProtocolKiliDefect extends BaseEntity {
    /**
     * Протокол КИЛИ
     */
    @Comment("Протокол КИЛИ")
    @ManyToOne
    public ProtocolKili getProtocol() {
        return protocol;
    }

    /**
     * Протокол КИЛИ
     */
    private ProtocolKili protocol;

    /**
     * Дефект
     */
    @Comment("Дефект")
    @OneToOne
    public VocKiliDefect getDefect() {
        return defect;
    }

    /**
     * Дефект
     */
    private VocKiliDefect defect;

    /**
     * Дефект обнаружен
     */
    private Boolean isDefectFound;

    /**
     * Описание дефекта
     */
    private String defectText;
}
