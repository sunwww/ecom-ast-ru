package ru.ecom.expert2.domain.voc;

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV021;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Справочник консультативно-диагностических посещений */
@Entity
@NamedQuery(name = "VocDiagnosticVisit.getActualKdps" , query = "from VocDiagnosticVisit where finishDate is null")
@Deprecated //неактуально с 2020 года
public class VocDiagnosticVisit extends VocBaseFederal {

    /** Цена  */
    @Comment("Цена")
    @Column( precision = 10, scale = 5 )
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

    /** Список подходящих услуг */
    @Comment("Список подходящих услуг")
    public String getMedServicesList() {return theMedServicesList;}
    public void setMedServicesList(String aMedServicesList) {theMedServicesList = aMedServicesList;}
    /** Список подходящих услуг */
    private String theMedServicesList ;

    /** Профиль основной мед. специальности */
    @Comment("Профиль основной мед. специальности")
    @OneToOne
    public VocE2FondV021 getSpeciality() {return theSpeciality;}
    public void setSpeciality(VocE2FondV021 aSpeciality) {theSpeciality = aSpeciality;}
    /**  */
    private VocE2FondV021 theSpeciality ;

    /** Список кодов услуг по КДП */
    @Comment("Список кодов услуг по КДП")
    @Transient
    public List<String> getListCodes() {
        List<String> list = new ArrayList<>();
        for (VocDiagnosticVisitMedService medService : getMedServices()) {
            list.add(medService.getMedService().getCode());
        }
        Collections.sort(list);
        return list;
    }
}