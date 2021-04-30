package ru.ecom.expomc.ejb.services.form.impdoc;

import org.apache.log4j.Logger;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoTrimString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.util.StringUtil;

import javax.persistence.Id;

/**
 * @author esinev 21.08.2006 0:15:05
 */
@EntityForm
@EntityFormPersistance(clazz= ImportDocument.class)
@Comment("Документ")
@WebTrail(comment = "Документ", nameProperties="comment", view="entityView-exp_importdocument.do")
@EntityFormSecurityPrefix("/Policy/Exp/Document")
public class ImportDocumentForm extends IdEntityForm {

    private static final Logger LOG = Logger.getLogger(ImportDocumentForm.class) ;
    private static final boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    /** Идентификатор */
    @Id
    public long getId() { return id ; }
    public void setId(long aId) { id = aId ; }

    /** Ключ импорта */
    @Persist
    @DoUpperCase @DoTrimString
    public String getKeyName() { return keyName ; }
    public void setKeyName(String aKeyName) { keyName = aKeyName ; }

    /** Комментарий */
    @Persist
    public String getComment() { return comment ; }
    public void setComment(String aComment) { comment = aComment ; }

    /** Клас для сохранения */
    @Persist
    public String getEntityClassName() { return entityClassName ; }
    public void setEntityClassName(String aEntityClassName) { entityClassName = aEntityClassName ; }

    /** Поддержка импорта по времени */
    public boolean getImportTimeSupports() {
        try {
            Class clazz = ClassLoaderHelper.getInstance().loadClass(getEntityClassName()) ;
            return isTimeSupports(clazz) ;
        } catch (Exception e) {
            LOG.error(e) ;
        }
        return false ;
    }

    private boolean isTimeSupports(Class aClass) {
        if (CAN_DEBUG) LOG.debug("aClass = " + aClass);
        if(aClass==null) return false ;
        for (Class interfaze : aClass.getInterfaces()) {
            if (CAN_DEBUG) LOG.debug(" interfaze = " + interfaze);
            if(interfaze.equals(IImportData.class)) {
                return true ;
            }
        }
        return isTimeSupports(aClass.getSuperclass()) ;
    }
//    public void setImportTimeSupports(boolean aImportTimeSupports) {
//        importTimeSupports = aImportTimeSupports ;
//    }

    public String getPrettyClassName() {
        String ret ;
        if(!StringUtil.isNullOrEmpty(entityClassName)) {
            int pos = entityClassName.indexOf("domain.") ;
            if(pos>=0) {
                ret = entityClassName.substring(pos+"domain.".length()) ;
            } else {
                ret = entityClassName ;
            }

        } else ret = null ;
            return ret ;
    }
    /** Клас для сохранения */
    private String entityClassName ;
    /** Комментарий */
    private String comment ;
    /** Ключ импорта */
    private String keyName ;
    /** Идентификатор */
    private long id ;
}
