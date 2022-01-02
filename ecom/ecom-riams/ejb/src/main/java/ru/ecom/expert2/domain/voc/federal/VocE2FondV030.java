package ru.ecom.expert2.domain.voc.federal;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
/**
 * Схемы лечения заболевания COVID-19
 */
@Table(name = "VocE2FondV030")
@Setter
public class VocE2FondV030 extends VocBaseFederal {
    /**
     * Дозволенные для применения группы препаратов
     */

    private Set<VocE2FondV032> groups;
    /**
     * Степень тяжести
     */
    private String degreeSeverity;

    public String getDegreeSeverity() {
        return degreeSeverity;
    }

    @OneToMany(mappedBy = "schema", fetch = FetchType.LAZY)
    public Set<VocE2FondV032> getGroups() {
        return groups;
    }
}
