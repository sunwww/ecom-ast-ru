package ru.ecom.diary.ejb.domain.protocol.template;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 19.01.2007
 * Time: 11:53:16
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(schema="SQLUser")
public class TemplateProtocolCategory extends BaseEntity {
 
    /** Классификатор */
    @OneToOne
    public TemplateClassif getClassif() { return theClassif ; }
    public void setClassif(TemplateClassif aClassif) { theClassif = aClassif ; }

    /** Категория классификатора */
    public Long getCategory() { return theCategory ; }
    public void setCategory(Long aCategory) { theCategory = aCategory ; }

    /** Шаблон протокола */
    @ManyToOne
    public TemplateProtocol getTemplateProtocol() { return theTemplateProtocol ; }
    public void setTemplateProtocol(TemplateProtocol aTemplateProtocol) { theTemplateProtocol = aTemplateProtocol ; }

  /** Название классификаора */
    public String getClassifName() {
      String classifName = "" ;
      if (theClassif!= null) {
          classifName = theClassif.getName() ;
      }
      return classifName ;
  }
    public void setClassifName(String aClassifName) { }

    /** Название категории */
    public String getCategoryName() {
        String categoryName = "" ;
        if (theClassif != null && theCategory != null) {
            try {
//                categoryName = VocHelperDelegate.locateVocHelper().getNameById(null,theCategory.toString(),theClassif.getClazz()) ;
//                IVocService voc = new EntityVocService() ;
//                categoryName = voc.getNameById(theCategory.toString(), theClassif.getClazz(), null) ;
                categoryName = theCategory.toString() + theClassif.getClazz() ;
            } catch (Exception e) {
                categoryName = theCategory.toString() + theClassif.getClazz() ;
            }
        }
//        return theCategory.toString() ;
        return categoryName ;

    }
    public void setCategoryName(String aCategoryName) {}

    /** Шаблон протокола */
    private TemplateProtocol theTemplateProtocol ;

    /** Категория классификатора */
    private Long theCategory ;

    /** Классификатор */
    private TemplateClassif theClassif ;

}
