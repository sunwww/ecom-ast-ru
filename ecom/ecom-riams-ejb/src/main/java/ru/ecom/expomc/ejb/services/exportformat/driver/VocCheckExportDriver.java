package ru.ecom.expomc.ejb.services.exportformat.driver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import ru.nuzmsh.util.voc.VocValue;

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

        s.append("\t<row id='" +vocValue.getId()+"'>");
        if (matcher.matches()) {
            className = matcher.group(2);
            comment = matcher.group(1);
        }

        s.append("<class>"+className.trim()+"</class>\n\t\t\t");
        s.append("<comment>"+comment.trim()+"</comment>");
        s.append("</row>\n");
    }
}
