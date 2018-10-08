package ru.ecom.expert2.domain.financeplan;

import ru.ecom.mis.ejb.domain.medcase.voc.VocMethodHighCare;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class VmpFinancePlan extends FinancePlan {

    /** Метод ВМП */
    @Comment("Метод ВМП")
    @OneToOne
    public VocMethodHighCare getMethod() {return theMethod;}
    public void setMethod(VocMethodHighCare aMethod) {theMethod = aMethod;}
    /** Метод ВМП */
    private VocMethodHighCare theMethod ;
}
