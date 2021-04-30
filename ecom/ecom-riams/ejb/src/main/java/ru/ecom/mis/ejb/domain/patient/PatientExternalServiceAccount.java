package ru.ecom.mis.ejb.domain.patient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;

/**
 * Created by vtsybulin on 23.05.2017.
 */
@Entity
@Getter
@Setter
public class PatientExternalServiceAccount extends BaseEntity {

   @OneToOne
   /** Пациент */
        @Comment("Пациент")
        public Patient getPatient() {return patient;}
        /** Пациент */
        private Patient patient ;

        /** Идентификатор во внешней системе */
        private String externalCode ;

        /** Дата получения согласия на передачу данных */
        private Date dateStart ;

        /** Дата отзыва согласия на передачу данных */
        private Date dateTo ;

        /** Номер мобильного телефона */
        private String phoneNumber ;

		/** Адрес электронной почты */
		private String email ;

    /** Создатель */
    private String username ;

    /** Дата создания */
    private Date createDate ;

    /** Пользователь, редактировавший запись */
    private String editUsername ;

    /** Дата редактирования записи */
    private Date editDate ;

    /** Разрешить выгрузку всей истории болезни */
    private Boolean exportAllHistory ;

    /** Отправлять сообщения о поступивших заявках (для работников ЛПУ */
    private Boolean newClaimNotification ;
}
