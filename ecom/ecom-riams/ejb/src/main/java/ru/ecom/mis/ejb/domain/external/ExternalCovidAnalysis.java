package ru.ecom.mis.ejb.domain.external;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
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

    @PrePersist
    private void prePersist() {
        long currentTime = System.currentTimeMillis();
        setCreateDate(new Date(currentTime));
        setCreateTime(new Time(currentTime));
        setUniqNumber(lastname+firstname+middlename+birthday+"#"+dateDirect);
    }
}
