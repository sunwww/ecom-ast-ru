package ru.ecom.expert2.domain.voc;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
/** Поправочные коэффициенты для поликлиники*/
public class VocE2PolyclinicCoefficient extends VocCoefficient {

    /** Тип случая */
    @Comment("Тип случая")
    @OneToOne
    @Deprecated
    public VocE2EntrySubType getEntryType() {return theEntryType;}
    public void setEntryType(VocE2EntrySubType aEntryType) {theEntryType = aEntryType;}
    /** Тип случая */
    private VocE2EntrySubType theEntryType ;

    /** Коэффициент для мобильной поликлиники */
    @Comment("Коэффициент для мобильной поликлиники")
    public Boolean getIsMobilePolyclinic() {return theIsMobilePolyclinic;}
    public void setIsMobilePolyclinic(Boolean aIsMobilePolyclinic) {theIsMobilePolyclinic = aIsMobilePolyclinic;}
    /** Коэффициент для мобильной поликлиники */
    private Boolean theIsMobilePolyclinic ;

    /** Для консультативного приема */
    @Comment("Для консультативного приема")
    public Boolean getIsConsultation() {return theIsConsultation;}
    public void setIsConsultation(Boolean aIsConsultation) {theIsConsultation = aIsConsultation;}
    /** Для консультативного приема */
    private Boolean theIsConsultation ;

    /** Для консультативно-диагностического обращений */
    @Comment("Для консультативно-диагностического обращений")
    public Boolean getIsDiagnosticSpo() {return theIsDiagnosticSpo;}
    public void setIsDiagnosticSpo(Boolean aIsDiagnosticSpo) {theIsDiagnosticSpo = aIsDiagnosticSpo;}
    /** Для консультативно-диагностического обращений */
    private Boolean theIsDiagnosticSpo ;

}
