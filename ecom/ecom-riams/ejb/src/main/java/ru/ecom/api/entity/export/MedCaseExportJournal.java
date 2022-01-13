package ru.ecom.api.entity.export;

import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.util.ColumnConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@Entity
@Setter
public class MedCaseExportJournal extends BaseEntity {

    private Long medCase;
    private String packetGuid; //some problems with uuid
    private Timestamp exportDate;
    private ExportType exportType;
    private String errorText;

    @Column(columnDefinition = "int8 not null")
    public Long getMedCase() {
        return medCase;
    }

    public String getPacketGuid() {
        return packetGuid;
    }

    public Timestamp getExportDate() {
        return exportDate;
    }

    @Enumerated(EnumType.STRING)
    public ExportType getExportType() {
        return exportType;
    }

    @Column(length = ColumnConstants.TEXT_MAXLENGHT)
    public String getErrorText() {
        return errorText;
    }

    public MedCaseExportJournal() {
        this.exportDate = new Timestamp(System.currentTimeMillis());
    }

}
