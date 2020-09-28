package ru.ecom.expomc.ejb.services.form.check;

import ru.ecom.expomc.ejb.domain.check.Check;
import ru.ecom.expomc.ejb.domain.check.CheckProperty;
import ru.ecom.expomc.ejb.domain.format.Field;
import ru.ecom.expomc.ejb.domain.format.Format;
import ru.ecom.expomc.ejb.services.voc.allvalues.AllowedChecksAllValues;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Method;
import java.util.*;

/**
 */
@Stateless
@Remote(IFormCheckService.class)
public class FormCheckServiceBean implements IFormCheckService {

    public LinkedList<Long> save(ChecksTableForm aForm, long aFormat) {
        Format format = theManager.find(Format.class, aFormat);
        LinkedList<Long> deleted = new LinkedList<Long>();
        for (ChecksTableFormRow row : aForm.getChecksTableFormRows()) {
            for (ChecksTableFormRowOn on : row.getOns()) {
                Long result = saveOn(on, row, format) ;
                if(result!=null) {
                    deleted.add(result) ;
                }
            }
        }
        return deleted;
    }

    public void deleteChecks(LinkedList<Long> aDeleted) {
        for (Long id : aDeleted) {
            Check check = theManager.find(Check.class, id) ;
            theManager.remove(check);
        }
    }

    private Long saveOn(ChecksTableFormRowOn on, ChecksTableFormRow row, Format format) {
        IsOn isOn = isOn(row.getProperty(), format, on.getCheckId()) ;
        Long ret = null  ;
        if(isOn.on) {
            if(!on.getValue()) {
                ret = isOn.check.getId();
                // delete
                //isOn.check.getDocument().getChecks().remove(isOn.check) ; //getProperties().clear() ;
                //theManager.persist(isOn.check.getDocument());
//                theManager.remove(isOn.check) ;
            }
        } else {

            if(on.getValue()) {
                Check check = new Check();
                check.setActualDateFrom(format.getActualDateFrom());
                check.setActualDateTo(format.getActualDateTo());
                check.setCheckId(on.getCheckId());

                Class clazz = theAllowed.getCheckClassById(on.getCheckId()) ;

                check.setCheckType(isChange(clazz) ? Check.TYPE_CHANGE : Check.TYPE_CRITICAL);

                check.setDocument(format.getDocument());
                check.setName(row.getProperty()+" : "+getComment(clazz));
                theManager.persist(check);

                CheckProperty property = new CheckProperty();
                property.setCheck(check);
                property.setProperty("property");
                property.setValue(row.getProperty());
                theManager.persist(property);
            }
        }
        return ret ;
    }

    private String getComment(Class aClass) {
        Comment comment = (Comment) aClass.getAnnotation(Comment.class) ;
        return comment!=null ? comment.value() : aClass.getSimpleName() ;
    }

    private boolean isChange(Class aClass) {
        return aClass.getSimpleName().startsWith("Change") ;
    }

    public ChecksTableForm loadFormChecksTableForm(long aFormatId) {
        Format format = theManager.find(Format.class, aFormatId);
        if(format==null) throw new IllegalArgumentException("Формата с id "+aFormatId+" нет ");
        ChecksTableForm form = new ChecksTableForm();
        ArrayList<ChecksTableFormRow> elms = new ArrayList<ChecksTableFormRow>();

        ArrayList<Field> fields =  new ArrayList(format.getFields()) ;
        Collections.sort(fields, new Comparator<Field>() {
            public int compare(Field o1, Field o2) {
                return o1.getSerialNumber() - o2.getSerialNumber() ;
            }
        });
        if(format.getFields()!=null) {
            for (Field field : fields) {
                ChecksTableFormRow row = new ChecksTableFormRow();
                row.setProperty(field.getProperty());
                row.setFormatFieldName(field.getName());
                row.setComment(field.getComment());
                row.setOns(createOns(field.getProperty(),format));
                row.setDbfInfo(field.getDbfInfo());
                elms.add(row);
            }
        }

        form.setChecksTableFormRows(elms);
        form.setFormat(format.getId());
        return form;
    }



    private Collection<ChecksTableFormRowOn> createOns(String aProperty, Format aFormat) {
        AllowedChecksAllValues allowed = new AllowedChecksAllValues();
        ArrayList<ChecksTableFormRowOn> ons = new ArrayList<ChecksTableFormRowOn>();

        for (Map.Entry<Long, Class> entry : allowed.getChecksEntries()) {
            Long key = entry.getKey();
            Class clazz = entry.getValue();
            if (hasOneProp(clazz)) {
                ChecksTableFormRowOn on1 = new ChecksTableFormRowOn();
                Comment comment = (Comment) clazz.getAnnotation(Comment.class);
                if (comment != null) {
                    on1.setName(comment.value());
                } else {
                    on1.setName(clazz.getSimpleName());
                }
                on1.setKey(key.toString());
                on1.setValue(isOn(aProperty, aFormat, key).on);
                on1.setCheckId(key);
                ons.add(on1);
            }
        }
        return ons;
    }


    private static class IsOn {
        private boolean on  = false ;
        private Check check ;
        private CheckProperty checkProperty ;
    }

    private IsOn isOn(String aProperty, Format aFormat, long aCheckTypeId) {
        //boolean canPrint = aProperty.equals("kodLpu") ;


        IsOn isOn  = new IsOn();
//        boolean on = false;
        for (Check check : aFormat.getDocument().getChecks()) {
            if(check.getCheckId()==aCheckTypeId) {
                for (CheckProperty property : check.getProperties()) {
                    if ("property".equals(property.getProperty()) && aProperty.equals(property.getValue())) {
                        isOn.on = true ;
                        isOn.check = check;
                        isOn.checkProperty = property;
                        break;
                    }
                }
            }
        }
        return isOn;
    }

    private boolean hasOneProp(Class aClass) {
        boolean findProperty = false;
        int count = 0;
        for (Method method : aClass.getMethods()) {
            if (method.getAnnotation(Comment.class) != null) {
                count++;
                if (method.getName().equals("getProperty")) {
                    findProperty = true;
                }
            }
        }
        return count == 1 && findProperty;
    }

    @PersistenceContext
    private EntityManager theManager;

    AllowedChecksAllValues theAllowed = new AllowedChecksAllValues();
}

//
//        String[] props = new String[elms.size()];
//        String[] fieldsNames = new String[elms.size()];
//        String[] comments = new String[elms.size()];
//        Long[] linkedDocs = new Long[elms.size()];
//        for(int i=0; i<elms.size(); i++) {
//            props[i] = elms.get(i).getProperty();
//            fieldsNames[i] = elms.get(i).getFormatFieldName();
//            comments[i] = elms.get(i).getComment();
//
//        }
//        form.setProperties(props);
//        form.setFormatFieldsNames(fieldsNames);
//        form.setComments(comments);
//        form.setLinkedDocs(linkedDocs);

