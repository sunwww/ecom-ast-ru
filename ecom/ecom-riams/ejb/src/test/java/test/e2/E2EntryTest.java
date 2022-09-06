package test.e2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ecom.expert2.domain.E2Entry;
import ru.nuzmsh.util.format.DateFormat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class E2EntryTest {

    @Test
    @DisplayName("Тестирование  сравнивателя")
    void compareTest() throws ParseException {
        E2Entry young = new E2Entry();
        young.setStartDate(DateFormat.parseSqlDate("31.12.2021"));
        young.setStartTime(DateFormat.parseSqlTime("23:59"));

        E2Entry old = new E2Entry();
        old.setStartDate(DateFormat.parseSqlDate("01.01.2022"));
        young.setStartTime(DateFormat.parseSqlTime("23:59"));
        assertTrue(E2Entry.oldFirst.compare(old,young) > 0);
    }

    @Test
    @DisplayName("Тестирование  сравнивателя (первый элемент = самый молодой, последний - старый)")
    void compareList() throws ParseException {
        E2Entry second = ce("31.12.2021", "00:59", 2L);
        E2Entry third = ce("23.02.2020", "23:59", 3L);
        E2Entry fourth = ce("23.02.2020", "00:59", 4L);
        E2Entry first = ce("01.02.2023", "20:59", 1L);
        List<E2Entry> list = new ArrayList<>();
        list.add(second);
        list.add(first);
        list.add(fourth);
        list.add(third);
        list.sort(E2Entry.oldFirst.reversed());
        assertEquals(first.getId(), list.get(0).getId());
        assertEquals(second.getId(), list.get(1).getId());
        assertEquals(third.getId(), list.get(2).getId());
        assertEquals(fourth.getId(), list.get(3).getId());
    }

    E2Entry ce(String date, String time, Long id) throws ParseException {
        E2Entry e = new E2Entry();
        e.setId(id);
        e.setStartDate(DateFormat.parseSqlDate(date));
        e.setStartTime(DateFormat.parseSqlTime(time));
        return e;
    }

    E2Entry ce(String date, String time) throws ParseException {
        return ce(date, time, null);
    }
}
