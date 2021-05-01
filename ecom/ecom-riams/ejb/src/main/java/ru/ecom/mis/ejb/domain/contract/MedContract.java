package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.contract.voc.VocContractLabel;
import ru.ecom.mis.ejb.domain.contract.voc.VocContractRulesProcessing;
import ru.ecom.mis.ejb.domain.contract.voc.VocContractTerm;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.uc.privilege.domain.Privilege;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Медицинский договор
 */
@Comment("Медицинский договор")
@Entity
@Table(schema = "SQLUser")
@AIndexes(
        {
                @AIndex(properties = {"parent"})
                , @AIndex(properties = {"lpu"})
                , @AIndex(properties = {"priceList"})
                , @AIndex(properties = {"customer"})
        }
)
@EntityListeners(DeleteListener.class)
@UnDeletable
@Getter
@Setter
public class MedContract extends BaseEntity {


    private Privilege privilege;

    @Comment("Льгота")
    @OneToOne
    public Privilege getPrivilege() {
        return privilege;
    }

    /**
     * Признак удаленной записи
     */
    private Boolean isDeleted;

    /**
     * ЛПУ
     */
    @Comment("ЛПУ")
    @OneToOne
    public MisLpu getLpu() {
        return lpu;
    }

    /**
     * ЛПУ
     */
    private MisLpu lpu;

    /**
     * Заказчик
     */
    @Comment("Заказчик")
    @OneToOne
    public ContractPerson getCustomer() {
        return customer;
    }

    /**
     * Заказчик
     */
    private ContractPerson customer;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    public List<ServedPerson> getServedPersons() {
        return servedPersons;
    }

    /**
     * Обслуживаемые персоны
     */
    private List<ServedPerson> servedPersons;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    public List<MedContract> getChildren() {
        return children;
    }

    /**
     * Потомки
     */
    private List<MedContract> children;

    /**
     * Родитель
     */
    @Comment("Родитель")
    @ManyToOne
    public MedContract getParent() {
        return parent;
    }

    /**
     * Родитель
     */
    private MedContract parent;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    public List<ContractGuarantee> getGuarantees() {
        return guarantees;
    }

    /**
     * Гарантийные документы
     */
    private List<ContractGuarantee> guarantees;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    public List<ContractRule> getRules() {
        return rules;
    }

    /**
     * Договорные правила
     */
    private List<ContractRule> rules;

    /**
     * Дата начала действия
     */
    private Date dateFrom;

    /**
     * Дата окончания действия
     */
    private Date dateTo;

    /**
     * Описание
     */
    private String comment;

    /**
     * Номер договора
     */
    private String contractNumber;

    /**
     * Обработка правил
     */
    @Comment("Обработка правил")
    @OneToOne
    public VocContractRulesProcessing getRulesProcessing() {
        return rulesProcessing;
    }

    /**
     * Обработка правил
     */
    private VocContractRulesProcessing rulesProcessing;

    /**
     * Прейскурант
     */
    @Comment("Прейскурант")
    @OneToOne
    public PriceList getPriceList() {
        return priceList;
    }

    /**
     * Прейскурант
     */
    private PriceList priceList;

    /**
     * Информация
     */
    @Comment("Информация")
    @Transient
    public String getInfo() {
        return (parent == null ? "основной" : "поддоговор") +
                " №" + contractNumber;
    }

    /**
     * Пользователь, последний изменивший запись
     */
    private String editUsername;
    /**
     * Время, последнего изменения
     */
    private Time editTime;
    /**
     * Дата последнего изменения
     */
    private Date editDate;
    /**
     * Пользователь, создавший запись
     */
    private String createUsername;
    /**
     * Время создания
     */
    private Time createTime;
    /**
     * Дата создания
     */
    private Date createDate;

    /**
     * Лимит денег
     */
    private BigDecimal limitMoney;

    /**
     * Обязательно гарантийный документ
     */
    private Boolean isRequiredGuaratee;

    /**
     * Поток обслуживания
     */
    @Comment("Поток обслуживания")
    @OneToOne
    public VocServiceStream getServiceStream() {
        return serviceStream;
    }

    /**
     * Поток обслуживания
     */
    private VocServiceStream serviceStream;

    /**
     * Метка договора
     */
    @Comment("Метка договора")
    @OneToOne
    public VocContractLabel getContractLabel() {
        return contractLabel;
    }

    /**
     * Метка договора
     */
    private VocContractLabel contractLabel;

    /**
     * Срок договора
     */
    @Comment("Срок договора")
    @OneToOne
    public VocContractTerm getContractTerm() {
        return contractTerm;
    }

    /**
     * Срок договора
     */
    private VocContractTerm contractTerm;

    /**
     * Скидка по умолчанию
     */
    private BigDecimal discountDefault;

    /**
     * Оплачен
     */
    private Boolean isFinished;
}
