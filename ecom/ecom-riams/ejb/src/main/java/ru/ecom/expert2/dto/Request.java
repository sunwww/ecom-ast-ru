package ru.ecom.expert2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@Data
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Request")
@XmlType(name = "", propOrder = {
        "token",
        "hospLeave",
        "hosp"
})
public class Request {
    @XmlElement(name = "token")
    private String token;

    /**
     * Выписка из стационара
     */
    @XmlElement(name = "HospLeave")
    private HospLeave hospLeave;

    /**
     * Госпитализация
     */
    @XmlElement(name = "Hosp")
    private Hosp hosp;
}
