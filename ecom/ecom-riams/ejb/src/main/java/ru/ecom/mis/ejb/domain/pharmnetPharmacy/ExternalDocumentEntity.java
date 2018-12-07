package ru.ecom.mis.ejb.domain.pharmnetPharmacy;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by rkurbanov on 07.02.2018.
 */
@Comment("Внешний документ.Заголовок")
@Entity
@Table(name = "externalDocument", schema="pharmnet")
public class ExternalDocumentEntity extends BaseEntity {

    private Integer typeoperationId;//+
    private Long naklTitleId; //Внутренний номер документа. Целое число (8 байт).+
    private Boolean isDisable;//;// Признак удаления. Логическое.+
    private Integer docTypeId;//;// Код типа документа. (DocumentTypeEntity)+
    private String docType;// Тип документа. Строка (50).+
    private Integer branchId;// Код филиала. (BranchEntity)+
    private Integer distrId;// Код поставщика. Целое число (4 байта).+
    private String distr;// Поставщик. Строка (250).+
    private Date docDate;// Дата документа поставщика. Дата. Формат yyyy-MM-dd.+
    private Timestamp createDate;// Дата проводки. Дата/Время. Формат yyyy-MM-dd HH;//mm;//ss.SSS.+
    private String docNumber;// № документа поставщика. Строка (20).+
    private Boolean inBuh;// Признак выгрузки в бухгалтерию. Логическое.+
    private Float sumOptNoNDS;// Сумма оптовая (закупочная) без НДС. Число с плавающей точкой (точность 15,2).+
    private Float sumOptWNDS;// Сумма оптовая (закупочная) с НДС. Число с плавающей точкой (точность 15,2).+
    private Float sumRoznWNDS;// Сумма розничная (отпускная) с НДС. Число с плавающей точкой (точность 15,2).+
    private Float sumRoznWNDSvsDiscount;// Сумма розничная (отпускная) с НДС, со скидкой. Число с плавающей точкой (точность 15,2).+
    private Long naklTitleRId;// Внутренний номер парного расходного документа. Целое число (8 байт).+
    private Timestamp dateModify;// Дата последнего изменения документа. Дата/Время. Формат yyyy-MM-dd HH;//mm;//ss.SSS+
    private Integer statusId; // Статус (DocumentStatusEntity)+

    private Integer deviceId;// Номер ККМ.+
    private Integer numberSession;// Номер смены. Целое число (4 байта).+
    private Integer userId;// Код кассира. Целое число (4 байта).+
    private String userName;// Ф.И.О. кассира. Строка (50).+
    private Integer individualDiscountId;// Код дисконта. Целое число (4 байта).+
    private String individualDiscount;// Номер дисконта. Строка (50).+
    private Boolean orderForPerson;// Признак персонального заказа. Логическое.+
    private Float sumDiscount;// Сумма скидки. Число с плавающей точкой (точность 15,2).+

    private String сodeLPUForNLSID;// Код ЛПУ. Гуид.+
    private String comment;// Комментарий к акту списания. Строка(150).++
    private Integer madeToOrderTitleId;// Идентифкатор индивидуального заказа. Целое число (4 байта).+
    private Boolean isEnded;//+

    @Basic
    @Column(name = "typeoperationId")
    public Integer getTypeoperationId() {
        return typeoperationId;
    }
    public void setTypeoperationId(Integer typeoperationId) {
        this.typeoperationId = typeoperationId;
    }

    @Column(name = "naklTitleId")
    public Long getNaklTitleId() {
        return naklTitleId;
    }
    public void setNaklTitleId(Long naklTitleId) {
        this.naklTitleId = naklTitleId;
    }

    @Column(name = "disable")
    public Boolean getDisable() {
        return isDisable;
    }
    public void setDisable(Boolean disable) {
        isDisable = disable;
    }

    @Column(name = "docTypeId")
    public Integer getDocTypeId() {
        return docTypeId;
    }
    public void setDocTypeId(Integer docTypeId) {
        this.docTypeId = docTypeId;
    }

    @Column(name = "docType")
    public String getDocType() {
        return docType;
    }
    public void setDocType(String docType) {
        this.docType = docType;
    }

    @Column(name = "branchId")
    public Integer getBranchId() {
        return branchId;
    }
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    @Column(name = "distrId")
    public Integer getDistrId() {
        return distrId;
    }
    public void setDistrId(Integer distrId) {
        this.distrId = distrId;
    }

    @Column(name = "distr")
    public String getDistr() {
        return distr;
    }
    public void setDistr(String distr) {
        this.distr = distr;
    }

    @Column(name = "docDate")
    public Date getDocDate() {
        return docDate;
    }
    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    @Column(name = "createDate")
    public Timestamp getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Column(name = "docNumber")
    public String getDocNumber() {
        return docNumber;
    }
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    @Column(name = "inBuh")
    public Boolean getInBuh() {
        return inBuh;
    }
    public void setInBuh(Boolean inBuh) {
        this.inBuh = inBuh;
    }

    @Column(name = "sumOptNoNDS")
    public Float getSumOptNoNDS() {
        return sumOptNoNDS;
    }
    public void setSumOptNoNDS(Float sumOptNoNDS) {
        this.sumOptNoNDS = sumOptNoNDS;
    }

    @Column(name = "sumOptWNDS")
    public Float getSumOptWNDS() {
        return sumOptWNDS;
    }
    public void setSumOptWNDS(Float sumOptWNDS) {
        this.sumOptWNDS = sumOptWNDS;
    }

    @Column(name = "sumRoznWNDS")
    public Float getSumRoznWNDS() {
        return sumRoznWNDS;
    }
    public void setSumRoznWNDS(Float sumRoznWNDS) {
        this.sumRoznWNDS = sumRoznWNDS;
    }

    @Column(name = "sumRoznWNDSvsDiscount")
    public Float getSumRoznWNDSvsDiscount() {
        return sumRoznWNDSvsDiscount;
    }
    public void setSumRoznWNDSvsDiscount(Float sumRoznWNDSvsDiscount) {
        this.sumRoznWNDSvsDiscount = sumRoznWNDSvsDiscount;
    }

    @Column(name = "naklTitleRId")
    public Long getNaklTitleRId() {
        return naklTitleRId;
    }
    public void setNaklTitleRId(Long naklTitleRId) {
        this.naklTitleRId = naklTitleRId;
    }

    @Column(name = "dateModify")
    public Timestamp getDateModify() {
        return dateModify;
    }
    public void setDateModify(Timestamp dateModify) {
        this.dateModify = dateModify;
    }

    @Column(name = "statusId")
    public Integer getStatusId() {
        return statusId;
    }
    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    @Column(name = "deviceId")
    public Integer getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    @Column(name = "numberSession")
    public Integer getNumberSession() {
        return numberSession;
    }
    public void setNumberSession(Integer numberSession) {
        this.numberSession = numberSession;
    }

    @Column(name = "userId")
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "userName")
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "individualDiscountId")
    public Integer getIndividualDiscountId() {
        return individualDiscountId;
    }
    public void setIndividualDiscountId(Integer individualDiscountId) {
        this.individualDiscountId = individualDiscountId;
    }

    @Column(name = "individualDiscount")
    public String getIndividualDiscount() {
        return individualDiscount;
    }
    public void setIndividualDiscount(String individualDiscount) {
        this.individualDiscount = individualDiscount;
    }

    @Column(name = "orderForPerson")
    public Boolean getOrderForPerson() {
        return orderForPerson;
    }
    public void setOrderForPerson(Boolean orderForPerson) {
        this.orderForPerson = orderForPerson;
    }

    @Column(name = "sumDiscount")
    public Float getSumDiscount() {
        return sumDiscount;
    }
    public void setSumDiscount(Float sumDiscount) {
        this.sumDiscount = sumDiscount;
    }

    @Column(name = "сodeLPUForNLSID")
    public String getСodeLPUForNLSID() {
        return сodeLPUForNLSID;
    }
    public void setСodeLPUForNLSID(String сodeLPUForNLSID) {
        this.сodeLPUForNLSID = сodeLPUForNLSID;
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "madeToOrderTitleId")
    public Integer getMadeToOrderTitleId() {
        return madeToOrderTitleId;
    }
    public void setMadeToOrderTitleId(Integer madeToOrderTitleId) {
        this.madeToOrderTitleId = madeToOrderTitleId;
    }

    @Basic
    @Column(name = "isEnded")
    public Boolean getEnded() {
        return isEnded;
    }
    public void setEnded(Boolean ended) {
        isEnded = ended;
    }
}
