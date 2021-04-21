package ru.ecom.expert2.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class VocDentalUetCoefficient extends BaseEntity {

    private String diagnosis;
    private String dopDiagnosis;
    private BigDecimal shortCaseValue;
    private BigDecimal longCaseValue;

}
