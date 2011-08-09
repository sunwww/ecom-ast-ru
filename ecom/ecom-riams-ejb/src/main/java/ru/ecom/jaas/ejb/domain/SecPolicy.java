package ru.ecom.jaas.ejb.domain;

import static javax.persistence.CascadeType.ALL;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Политика безопасности
 */
@Entity
@AIndexes({
    @AIndex(properties= ("key"))
  , @AIndex(unique = false, properties= {"parentSecPolicy","key"})
  ,@AIndex(properties={"parentSecPolicy"})
  ,@AIndex(properties={"parentSecPolicy","name"})
})
@Table(schema="SQLUser")
public class SecPolicy extends BaseEntity {

    //public static final long ROOT_POLICY_ID = 1 ; 


    /** Ключ */
    public String getKey() { return theKey ; }
    public void setKey(String aKey) { theKey = aKey ; }

    /** Название */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Комментарий */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Родитель */
    @ManyToOne
    public SecPolicy getParentSecPolicy() { return theParentSecPolicy ; }
    public void setParentSecPolicy(SecPolicy aParentSecPolicy) { theParentSecPolicy = aParentSecPolicy ; }

    /** Вложенные политики */
    @OneToMany(cascade = ALL, mappedBy = "parentSecPolicy")
    @OrderBy("name")
    public Collection<SecPolicy> getChildsSecPolicies() { return theChildsSecPolicies ; }
    public void setChildsSecPolicies(Collection<SecPolicy> aChildsSecPolicies) { theChildsSecPolicies = aChildsSecPolicies ; }

    /** Вложенные политики */
    private Collection<SecPolicy> theChildsSecPolicies ;
    /** Родитель */
    private SecPolicy theParentSecPolicy ;
    /** Комментарий */
    private String theComment ;
    /** Название */
    private String theName ;
    /** Ключ */
    private String theKey ;
    
    /** Вычисленный ключ */
	@Comment("Вычисленный ключ")
	public Integer getCalckey() {
		return theCalckey;
	}

	public void setCalckey(Integer aCalckey) {
		theCalckey = aCalckey;
	}

	/** Вычисленный ключ */
	private Integer theCalckey;

}
