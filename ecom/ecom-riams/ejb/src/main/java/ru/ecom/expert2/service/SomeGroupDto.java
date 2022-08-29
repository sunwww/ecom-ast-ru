package ru.ecom.expert2.service;

import lombok.Data;
import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;

@Data //todo refactor & rename
public class SomeGroupDto {
    private Long externalPatientId;
    private VocE2MedHelpProfile medHelpProfile;
    private String serviceStream;

    private SomeGroupDto() {
    }

    ;

    public SomeGroupDto(E2Entry entry) {
        this.externalPatientId = entry.getExternalPatientId();
        this.medHelpProfile = entry.getMedHelpProfile();
        this.serviceStream = entry.getServiceStream();
    }
}
