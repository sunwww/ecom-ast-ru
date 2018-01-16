package ru.ecom.mis.ejb.domain.patient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;

/**
 * Created by vtsybulin on 23.05.2017.
 */
@Entity
public class PatientExternalServiceAccount extends BaseEntity {

   @OneToOne
   /** Пациент */
        @Comment("Пациент")
        public Patient getPatient() {return thePatient;}
        public void setPatient(Patient aPatient) {thePatient = aPatient;}
        /** Пациент */
        private Patient thePatient ;

    /** Идентификатор во внешней системе */
        @Comment("Идентификатор во внешней системе")
        public String getExternalCode() {return theExternalCode;}
        public void setExternalCode(String aExternalCode) {theExternalCode = aExternalCode;}
        /** Идентификатор во внешней системе */
        private String theExternalCode ;

    /** Дата получения согласия на передачу данных */
        @Comment("Дата получения согласия на передачу данных")
        public Date getDateStart() {return theDateStart;}
        public void setDateStart(Date aDateStart) {theDateStart = aDateStart;}
        /** Дата получения согласия на передачу данных */
        private Date theDateStart ;

    /** Дата отзыва согласия на передачу данных */
        @Comment("Дата отзыва согласия на передачу данных")
        public Date getDateTo() {return theDateTo;}
        public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}
        /** Дата отзыва согласия на передачу данных */
        private Date theDateTo ;

    /** Номер мобильного телефона */
        @Comment("Номер мобильного телефона")
        public String getPhoneNumber() {return thePhoneNumber;}
        public void setPhoneNumber(String aPhoneNumber) {thePhoneNumber = aPhoneNumber;}
        /** Номер мобильного телефона */
        private String thePhoneNumber ;

	/** Адрес электронной почты */
		@Comment("Адрес электронной почты")
		public String getEmail() {return theEmail;}
		public void setEmail(String aEmail) {theEmail = aEmail;}
		/** Адрес электронной почты */
		private String theEmail ;

    /** Создатель */
    @Comment("Создатель")
    public String getUsername() {return theUsername;}
    public void setUsername(String aUsername) {theUsername = aUsername;}
    /** Создатель */
    private String theUsername ;

    /** Дата создания */
    @Comment("Дата создания")
    public Date getCreateDate() {return theCreateDate;}
    public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата создания */
    private Date theCreateDate ;

    /** Пользователь, редактировавший запись */
    @Comment("Пользователь, редактировавший запись")
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
    /** Пользователь, редактировавший запись */
    private String theEditUsername ;

    /** Дата редактирования записи */
    @Comment("Дата редактирования записи")
    public Date getEditDate() {return theEditDate;}
    public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
    /** Дата редактирования записи */
    private Date theEditDate ;

    /** Разрешить выгрузку всей истории болезни */
    @Comment("Разрешить выгрузку всей истории болезни")
    public Boolean getExportAllHistory() {return theExportAllHistory;}
    public void setExportAllHistory(Boolean aExportAllHistory) {theExportAllHistory = aExportAllHistory;}
    /** Разрешить выгрузку всей истории болезни */
    private Boolean theExportAllHistory ;

    /** Отправлять сообщения о поступивших заявках (для работников ЛПУ */
    @Comment("Отправлять сообщения о поступивших заявках (для работников ЛПУ")
    public Boolean getNewClaimNotification() {return theNewClaimNotification;}
    public void setNewClaimNotification(Boolean aNewClaimNotification) {theNewClaimNotification = aNewClaimNotification;}
    /** Отправлять сообщения о поступивших заявках (для работников ЛПУ */
    private Boolean theNewClaimNotification ;
}
