package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.sql.Date;

@Entity
@NamedQueries({
        @NamedQuery( name="Covid19Contact.getAllByPatient"
                , query="from Covid19Contact where card.patient=:patient" +
                " order by lastname, firstname, middlename ")
})
@Getter
@Setter
public class Covid19Contact extends BaseEntity {

    /** Карта коронавируса 19 */
    @Comment("Карта коронавируса 19")
    @ManyToOne
    public Covid19 getCard() {return card;}
    private Covid19 card ;

    /** Фамилия */
    private String lastname ;

    /** Имя */
    private String firstname ;

    /** Отчество */
    private String middlename ;

    /** Дата рождения */
    private Date birthDate ;

    /** Телефон */
    private String phone ;

    /** Адресс */
    private String address ;

}