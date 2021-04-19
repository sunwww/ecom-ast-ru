package ru.ecom.expert2.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        public LocalDate unmarshal(String v) throws Exception {
            return LocalDate.parse(v);
        }

        public String marshal(LocalDate v) throws Exception {
            return format.format(v);
        }
}
