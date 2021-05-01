package ru.ecom.expomc.ejb.domain.omcvoc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.NoLiveBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * Справочник: код + значение
 */
//@Comment("Справочник: код + значение")
@SuppressWarnings("serial")
@MappedSuperclass    
@Table(schema="SQLUser")
@AIndexes
({
@AIndex(properties = {"code"}),
@AIndex(properties = {"time", "code"}),
@AIndex(properties = {"time", "name"}),
@AIndex(properties = {"time", "code", "name"})
        })
@Getter
@Setter
public class OmcAbstractVoc extends NoLiveBaseEntity implements Serializable, IImportData {

    /** Код */
    @Comment("Код")
    @AFormatFieldSuggest({"PROF_LPU","KOD","VID_LPU","RES_G"
    	,"C_PRVD","AS","KL","Q_Z", "TYPS", "CASUS", "EXPERT"
            , "TCOD", "KOD_FOMS", "RNUMBER","COD","CODE","MCOD" })
    @Column(name="voc_code")
    public String getCode() { return code; }

    /** Название района */
    @Comment("Наименование")
    @AFormatFieldSuggest({"NAME","DESCRIPTIO", "DISCRIPTIO"
    	,"N_PRVD","NAME_Z","NAZV", "NAZV_PRE","NAME_MOP"})
    public String getName() { return name; }

    /** Время импорта */
    @Column(name="voc_time")
    public long getTime() { return time; }


    /** Название района */
    private String name;
    /** Код */
    private String code;
    /** Время импорта */
    private long time;

}
