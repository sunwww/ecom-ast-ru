package ru.ecom.expert2.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@AIndexes({
        @AIndex(properties = {"medService"})
})
public class EntryMedServiceMedImplant extends BaseEntity {

    private EntryMedService medService;

    @ManyToOne
    public EntryMedService getMedService() {
        return medService;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getTypeCode() {
        return typeCode;
    }

    private String serialNumber;
    private String typeCode;
}
