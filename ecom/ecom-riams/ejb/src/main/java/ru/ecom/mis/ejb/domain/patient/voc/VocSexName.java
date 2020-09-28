package ru.ecom.mis.ejb.domain.patient.voc;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
/**Справочник полов и имен*/
@AIndexes({
        @AIndex(unique = true, properties = {"name"})
})
@NamedQueries({
        @NamedQuery( name="VocSexName.getByName"
                , query="from VocSexName where name=:name")
})
public class VocSexName extends BaseEntity {
    private VocSex sex;
    private String name;

    @OneToOne
    public VocSex getSex() {
        return sex;
    }

    public void setSex(VocSex sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
