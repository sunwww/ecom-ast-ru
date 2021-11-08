package ru.ecom.mis.ejb.service.addresspoint.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class PatientAttachmentDto {
    public final static String[] HEADERS = {"Тип_ДПФС", "ИД_полиса", "ЕНП", "Фамилия", "Имя", "Отчество", "Дата_рождения"
            , "Место_рождения", "Тип_УДЛ", "", "Дата_УДЛ", "Орган_УДЛ", "СНИЛС", "ИД_МО","Способ_прикрепления","Тип_прикрепления"
    ,"Дата_прикрепления","Дата_открепления","ОИД_МО","Код_подразделения","Код_участка","СНИЛС_врача","Категория_врача"};

    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "Действие")
    String actionType="Р";
    @CsvBindByName(column = "Тип_ДПФС")
    @CsvBindByPosition(position = 1)
    String medPolicyType;
    @CsvBindByName(column = "ИД_полиса")
    @CsvBindByPosition(position = 2)
    String medPolicyNumber; //Серия и номер полиса ОМС старого образца (серия отделяется от номера последовательностью знаков «пробел», «№», «пробел») или номер временного свидетельства.
    @CsvBindByName(column = "ЕНП")
    @CsvBindByPosition(position = 3)
    String commonNumber;
    @CsvBindByName(column = "Фамилия")
    @CsvBindByPosition(position = 4)
    String lastname;
    @CsvBindByName(column = "Имя")
    @CsvBindByPosition(position = 5)
    String firstname;
    @CsvBindByName(column = "Отчество")
    @CsvBindByPosition(position = 6)
    String middlename;
    @CsvBindByName(column = "Дата_рождения")
    @CsvBindByPosition(position = 7)
    @CsvDate("yyyyMMdd")
    Date birthDate;
    @CsvBindByName(column = "Место_рождения")
    @CsvBindByPosition(position = 8)
    String birthPlace = null;
    @CsvBindByName(column = "Тип_УДЛ")
    @CsvBindByPosition(position = 9)
    String passportType;
    @CsvBindByPosition(position = 10)
    String passportNumber = null;
    @CsvBindByName(column = " Дата_УДЛ")
    @CsvBindByPosition(position = 11)
    @CsvDate("yyyyMMdd")
    Date passportDate = null;
    @CsvBindByName(column = "Орган_УДЛ")
    @CsvBindByPosition(position = 12)
    String passportWhom = null;
    @CsvBindByName(column = "СНИЛС")
    @CsvBindByPosition(position = 13)
    String snils = null;
    @CsvBindByName(column = "ИД_МО")
    @CsvBindByPosition(position = 14)
    String lpuCode;
    @CsvBindByName(column = "Способ_прикрепления")
    @CsvBindByPosition(position = 15)
    String attachedMethod;
    @CsvBindByName(column = "Тип_прикрепления")
    @CsvBindByPosition(position = 16)
    String attachedType;
    @CsvBindByName(column = "Дата_прикрепления")
    @CsvBindByPosition(position = 17)
    @CsvDate("yyyyMMdd")
    Date attachedDate;
    @CsvBindByName(column = "Дата_открепления")
    @CsvBindByPosition(position = 18)
    @CsvDate("yyyyMMdd")
    Date dettachedDate;
    @CsvBindByName(column = "ОИД_МО")
    @CsvBindByPosition(position = 19)
    String lpuOid;
    @CsvBindByName(column = "Код_подразделения")
    @CsvBindByPosition(position = 20)
    String departmentCode;
    @CsvBindByName(column = "Код_участка")
    @CsvBindByPosition(position = 21)
    String areaNumber;
    @CsvBindByName(column = "СНИЛС_врача")
    @CsvBindByPosition(position = 22)
    String doctorSnils;
    @CsvBindByName(column = "Категория_врача")
    @CsvBindByPosition(position = 23)
    String doctorType = "1"; //врач

}
