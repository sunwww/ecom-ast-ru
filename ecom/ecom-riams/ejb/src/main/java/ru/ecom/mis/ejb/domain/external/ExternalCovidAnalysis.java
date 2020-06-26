package ru.ecom.mis.ejb.domain.external;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import java.sql.Date;
import java.sql.Time;

@Entity
@AIndexes(value = {
        @AIndex(properties={"uniqNumber"})
})
//TODO Убрать после изчезновения ковида
public class ExternalCovidAnalysis extends BaseEntity {
    @CsvBindByName(column = "firstname")
    String firstname;
    @CsvBindByName(column = "lastname")
    String lastname;
    @CsvBindByName(column = "middlename")
    String middlename;
    @CsvBindByName(column = "birthday")
    @CsvDate("dd.MM.yyyy")
    Date birthday;
    @CsvBindByName(column = "resultText")
    String resultText;
    @CsvBindByName(column = "dateDirect")
    @CsvDate("dd.MM.yyyy")
    Date dateDirect;
    @CsvBindByName(column = "dateResult")
    @CsvDate("dd.MM.yyyy")
    Date dateResult;
    @CsvBindByName(column = "protocolNumber")
    String protocolNumber;
    @CsvBindByName(column = "dateProtocol")
    @CsvDate("dd.MM.yyyy")
    Date dateProtocol;
    @CsvBindByName(column = "laboratory")
    String laboratory;

    Date createDate;
    String createUsername;
    Time createTime;
    String uniqNumber;

    public String getUniqNumber() {
        return uniqNumber;
    }

    public void setUniqNumber(String uniqNumber) {
        this.uniqNumber = uniqNumber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public Time getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Time createTime) {
        this.createTime = createTime;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getResultText() {
        return resultText;
    }

    public Date getDateDirect() {
        return dateDirect;
    }

    public Date getDateResult() {
        return dateResult;
    }

    public String getProtocolNumber() {
        return protocolNumber;
    }

    public Date getDateProtocol() {
        return dateProtocol;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public void setDateDirect(Date dateDirect) {
        this.dateDirect = dateDirect;
    }

    public void setDateResult(Date dateResult) {
        this.dateResult = dateResult;
    }

    public void setProtocolNumber(String protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    public void setDateProtocol(Date dateProtocol) {
        this.dateProtocol = dateProtocol;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public String getLaboratory() {
        return laboratory;
    }

    @PrePersist
    private void prePersist() {
        long currentTime = System.currentTimeMillis();
        setCreateDate(new Date(currentTime));
        setCreateTime(new Time(currentTime));
        setUniqNumber(lastname+firstname+middlename+birthday+"#"+dateDirect);
    }
}
