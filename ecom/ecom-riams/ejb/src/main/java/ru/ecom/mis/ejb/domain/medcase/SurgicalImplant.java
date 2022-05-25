package ru.ecom.mis.ejb.domain.medcase;

import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.voc.VocSurgicalImplant;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Comment("Медицинское изделие в операции")
@Setter
@AIndexes({@AIndex(properties = "operation")})
public class SurgicalImplant extends BaseEntity {

    private SurgicalOperation operation;
    private String serialNumber;
    private VocSurgicalImplant type;

    @ManyToOne
    @Comment("Операция")
    public SurgicalOperation getOperation() {
        return operation;
    }

    @ManyToOne
    @Comment("Вид медицинского изделия")
    public VocSurgicalImplant getType() {
        return type;
    }
}
