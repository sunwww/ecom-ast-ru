package ru.ecom.expomc.ejb.services.form.format;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.expomc.ejb.domain.format.Field;
import ru.ecom.expomc.ejb.domain.format.Format;
import ru.ecom.expomc.ejb.services.check.checkers.ChangeRussianToLatin;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.LogicTokenizer;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

/**
 * @author esinev
 * Date: 22.08.2006
 * Time: 12:16:23
 */
@Stateless
@Remote(IFormatService.class)
public class FormatServiceBean implements IFormatService {

    public void removeFieldsWithEmptyProperty(long aFormatId) {
        Format format = theManager.find(Format.class, aFormatId);
        LinkedList<Long> removed = new LinkedList<Long>();

        for (Field field : format.getFields()) {
            if(StringUtil.isNullOrEmpty(field.getProperty())) {
                removed.add(field.getId()) ;
            }
        }
        theManager.clear();
        for (Long id : removed) {
            theManager.remove(theManager.find(Field.class, id)) ;
        }
    }
    public void addFieldFromWord(long aFormatId, String aText) {
        Format format = theManager.find(Format.class, aFormatId);


        StringTokenizer line = new StringTokenizer(aText, "\n\r");
        while (line.hasMoreTokens()) {
            LogicTokenizer st = new LogicTokenizer(line.nextToken(), '\t');
            String number = st.getNextString().trim();
            String name = st.getNextString().trim();
            String type = st.getNextString().trim();
            String comment = st.getNextString();
            if(comment!=null) comment = comment.trim() ;
            String description = st.getNextString();

            name = new ChangeRussianToLatin().transform(name) ;

            Field field = new Field();
            field.setName(name);
            field.setSerialNumber(Integer.parseInt(number.trim()));
            field.setDbfType(getType(type));
            field.setComment(comment);
            field.setDbfSize(getSize(type));
            field.setDescription(description);
            field.setFormat(format);
            try {
                PropertySuggest fieldSuggest = findPropertySuggest(name, format) ;
                if(fieldSuggest!=null) {
                    field.setProperty(fieldSuggest.getProperty());
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            theManager.persist(field);
        }

    }

    public PropertySuggest findPropertySuggest(String aFieldname, long aFormatId) throws ClassNotFoundException {
        Format format = theManager.find(Format.class, aFormatId) ;
        return findPropertySuggest(aFieldname, format) ;
    }

    private PropertySuggest findPropertySuggest(String aFieldname, Format aFormat) throws ClassNotFoundException {
        Class clazz = ClassLoaderHelper.getInstance().loadClass(aFormat.getDocument().getEntityClassName()) ;
        for (Method method : clazz.getMethods()) {
            AFormatFieldSuggest suggest = method.getAnnotation(AFormatFieldSuggest.class) ;
            if(suggest!=null) {
                for (String str : suggest.value()) {
                    if(str.toUpperCase().equals(aFieldname.toUpperCase())) {
                        Comment comment = method.getAnnotation(Comment.class) ;
                        return new PropertySuggest(PropertyUtil.getPropertyName(method), comment!=null ? comment.value() : "");
                    }
                }
            }
        }
        return null ;
    }

    public long getDocumentForFormat(long aFormatId) {
        Format format = theManager.find(Format.class, aFormatId) ;
        return format.getDocument().getId() ;
    }

    private int getType(String aType) {
        if (! StringUtil.isNullOrEmpty(aType)) {
            if (aType.startsWith("А")) {
                return Field.TEXT;
            }
            if (aType.startsWith("D")) {
                return Field.DATE;
            }
            if (aType.startsWith("N")) {
                return Field.NUMERIC;
            }
        }
        return Field.TEXT;
    }

    private int getSize(String aSize) {

        if (!StringUtil.isNullOrEmpty(aSize) && aSize.length() > 1) {
            aSize = aSize.replace("(","").replace(")","") ;
            StringTokenizer st = new StringTokenizer(aSize, ",.;");
            aSize = st.nextToken() ;
            return Integer.parseInt(aSize.toUpperCase().replace('O','0').replace('О','0').substring(1).trim());
        }
        return 0;
    }


    @PersistenceContext
    public EntityManager theManager;
}
