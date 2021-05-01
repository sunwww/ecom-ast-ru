package ru.ecom.expert2.domain.financeplan;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMethodHighCare;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class VmpFinancePlan extends FinancePlan {

    /** Метод ВМП */
    @Comment("Метод ВМП")
    @OneToOne
    public VocMethodHighCare getMethod() {return method;}
    private VocMethodHighCare method;
}
