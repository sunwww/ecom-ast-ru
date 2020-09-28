package ru.ecom.expomc.ejb.services.exportformat.driver;

import ru.nuzmsh.util.voc.VocValue;

import javax.persistence.EntityManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ikouzmin 14.03.2007 14:16:59
 */
public class VocCheckExportDriver extends VocExportDriver {

    public VocCheckExportDriver(EntityManager theManager, String params) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(theManager,"AllowedChecksAllValues");
    }

    public void saveVocValue(StringBuffer s, VocValue vocValue) {
        String comment =vocValue.getName();
        String className = "";
        Matcher matcher = Pattern.compile("^(.*)\\((.*)\\)$").matcher(comment);

        s.append("\t<row id='").append(vocValue.getId()).append("'>");
        if (matcher.matches()) {
            className = matcher.group(2);
            comment = matcher.group(1);
        }

        s.append("<class>").append(className.trim()).append("</class>\n\t\t\t");
        s.append("<comment>").append(comment.trim()).append("</comment>");
        s.append("</row>\n");
    }
}
