package ru.nuzmsh.util.format;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateConverterTest {

    @Test
    @DisplayName("Тестируем даты")
    void getDateFromGlobale() throws ParseException {
        String stringDate = "24.12.1986";
        Date date = new SimpleDateFormat("dd.MM.yyyy").parse(stringDate);
        assertEquals(date, DateConverter.getDateFromGlobale(stringDate));
    }

    @Test
    @DisplayName("Создание даты и времени из строк")
    void createDateTimeTest() throws ParseException {
        String date = "24.12.1986", time = "11:11";
        Date date1 = DateConverter.createDateTime(date, time);
        assertEquals( new SimpleDateFormat("dd.MM.yyyyHH:mm").parse(date+time).getTime(), date1.getTime());
    }

    @Test
    void hello() {
        assertEquals(1,1);
    }


}
