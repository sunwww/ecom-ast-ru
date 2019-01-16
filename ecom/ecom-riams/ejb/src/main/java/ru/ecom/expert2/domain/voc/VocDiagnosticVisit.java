package ru.ecom.expert2.domain.voc;

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

/** Справочник консультативно-диагностических посещений */
@Entity
public class VocDiagnosticVisit extends VocBaseFederal {

    /** Цена  */
    @Comment("Цена")
    public BigDecimal getCost() {return theCost;}
    public void setCost(BigDecimal aCost) {theCost = aCost;}
    /** Цена */
    private BigDecimal theCost ;

    /** Услуги */
    @Comment("Услуги")
    @OneToMany(mappedBy = "visit",fetch = FetchType.LAZY)
    public List<VocDiagnosticVisitMedService> getMedServices() {return theMedServices;}
    public void setMedServices(List<VocDiagnosticVisitMedService> aMedServices) {theMedServices = aMedServices;}
    /** Услуги */
    private List<VocDiagnosticVisitMedService> theMedServices ;
}
