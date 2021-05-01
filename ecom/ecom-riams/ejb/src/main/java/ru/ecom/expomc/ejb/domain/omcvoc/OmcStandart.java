package ru.ecom.expomc.ejb.domain.omcvoc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OMC_STANDART", schema = "SQLUser")
@AIndexes({
        @AIndex(properties = "model")
        , @AIndex(properties = "idcList")
})
@Getter
@Setter
public class OmcStandart extends VocBaseEntity {
    /**
     * Idc list
     */
    @Comment("Idc list")
    @AFormatFieldSuggest("IdcList")
    public String getIdcList() {
        return idcList;
    }

    /**
     * Фаза
     */
    @Comment("Фаза")
    @AFormatFieldSuggest("phase")
    public String getPhase() {
        return phase;
    }

    /**
     * stage
     */
    @Comment("stage")
    @AFormatFieldSuggest("stage")
    public String getStage() {
        return stage;
    }

    /**
     * complicatioC
     */
    @Comment("complicatioC")
    @AFormatFieldSuggest("complicatio")
    public String getComplication() {
        return complication;
    }

    /**
     * payer
     */
    @Comment("payer")
    @AFormatFieldSuggest("payer")
    public String getPayer() {
        return payer;
    }

    /**
     * model
     */
    @Comment("model")
    @AFormatFieldSuggest("model")
    public String getModel() {
        return model;
    }

    /**
     * Не действует
     */
    private Boolean deprecated;

    /**
     * model
     */
    private String model;
    /**
     * payer
     */
    private String payer;
    /**
     * complicatioC
     */
    private String complication;
    /**
     * stage
     */
    private String stage;
    /**
     * Фаза
     */
    private String phase;

    /**
     * Idc list
     */
    private String idcList;

}
