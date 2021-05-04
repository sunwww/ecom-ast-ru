package ru.ecom.mis.ejb.domain.worker;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

/**
 * Групповая рабочая функция
 *
 * @author stkacheva, azviagin
 */
@Entity
@AIndexes({
        @AIndex(properties = "groupName", table = "WorkFunction")
})
@Getter
@Setter
public class GroupWorkFunction extends WorkFunction {

    /**
     * Направлять анализы по умолчанию в этот кабинет
     */
    private Boolean isDefaultLabCabinet;


    /**
     * Есть обслуживающий персонал
     */
    private Boolean hasServiceStuff;

    @Transient
    @Comment("Информация")
    public String getWorkFunctionInfo() {
        return getName() + " " + groupName;
    }

    @Transient
    public String getWorkerLpuInfo() {
        return getLpu() != null ? getLpu().getFullname() : "";
    }

    /**
     * Рабочие функции входящие в состав группы
     */
    @Comment("Рабочие функции входящие в состав группы")
    @OneToMany(mappedBy = "group")
    public List<PersonalWorkFunction> getFunctions() {
        return functions;
    }

    @Transient
    public MisLpu getLpuRegister() {
        return getLpu();
    }

    @Transient
    public String getWorkerInfo() {
        return groupName;
    }

    /**
     * Название группы
     */
    private String groupName;

    @Transient
    public String getInfo() {
        return "ГРУППОВАЯ: " + getGroupName();
    }

    /**
     * Рабочие функции входящие в состав группы
     */
    private List<PersonalWorkFunction> functions;

    /**
     * Разрешено создавать направление без указания услуг
     */
    private Boolean isCreateDIrectionWithoutService = false;

}
