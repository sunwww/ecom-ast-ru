package ru.ecom.mis.ejb.domain.disability;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Подпись нетрудоспособности
 */

@Comment("Подпись нетрудостпособности")
@Entity
@Table(schema = "SQLUser")
public class DisabilitySign extends BaseEntity {

    private DisabilityDocument disabilityDocumentId;
    private Long externalId;
    private String code;
    private String counter;
    private String elnNumber;
    private String digestValue;
    private String signatureValue;
    private String certificate;
    private Boolean isExport = false;
    private Boolean isNoactual = false;
    private Timestamp dateCreate;
    private String сreateUsername;
    private String signatureType;

    public DisabilitySign() {
        this.dateCreate = new Timestamp(System.currentTimeMillis());
    }

    @Comment("Ссылка на документ")
    @OneToOne
    public DisabilityDocument getDisabilityDocumentId() {
        return disabilityDocumentId;
    }

    public void setDisabilityDocumentId(DisabilityDocument disabilityDocumentId) {
        this.disabilityDocumentId = disabilityDocumentId;
    }

    @Comment("Ссылка на блок")
    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    @Comment("Врач или ВК")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Comment("Порядковый номер подписи")
    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    @Comment("Номер элн")
    public String getElnNumber() {
        return elnNumber;
    }

    public void setElnNumber(String elnNumber) {
        this.elnNumber = elnNumber;
    }

    @Column(name = "digestValue", length = 1000)
    public String getDigestValue() {
        return digestValue;
    }

    public void setDigestValue(String digestValue) {
        this.digestValue = digestValue;
    }

    @Column(name = "signatureValue", length = 1000)
    public String getSignatureValue() {
        return signatureValue;
    }

    public void setSignatureValue(String signatureValue) {
        this.signatureValue = signatureValue;
    }

    @Column(name = "certificate", length = 5000)
    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    @Comment("Экспортировано")
    public Boolean getExport() {
        return isExport;
    }

    public void setExport(Boolean export) {
        isExport = export;
    }

    @Comment("Удалено")
    public Boolean getNoactual() {
        return isNoactual;
    }

    public void setNoactual(Boolean noactual) {
        isNoactual = noactual;
    }

    @Comment("Время создания")
    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Comment("Пользователь создавший запись")
    public String getСreateUsername() {
        return сreateUsername;
    }

    public void setСreateUsername(String сreateUsername) {
        this.сreateUsername = сreateUsername;
    }

    @Comment("Тип ГОСТа ключа шифрования")
    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }
}
