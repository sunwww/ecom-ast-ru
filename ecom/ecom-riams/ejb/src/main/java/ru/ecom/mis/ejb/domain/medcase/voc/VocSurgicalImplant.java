package ru.ecom.mis.ejb.domain.medcase.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Comment("Виды медицинских изделий 1.2.643.5.1.13.13.11.1079")
@Entity
public class VocSurgicalImplant extends VocBaseEntity {

    private Boolean actual;

    public Boolean getActual() {
        return actual;
    }

    public void setActual(Boolean actual) {
        this.actual = actual;
    }
}
