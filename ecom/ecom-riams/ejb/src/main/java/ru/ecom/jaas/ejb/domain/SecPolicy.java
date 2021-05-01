package ru.ecom.jaas.ejb.domain;

import static javax.persistence.CascadeType.ALL;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Setter
@Getter
public class SecPolicy extends BaseEntity {

    /** Родитель */
    @ManyToOne
    public SecPolicy getParentSecPolicy() { return parentSecPolicy ; }

    /** Вложенные политики */
    @OneToMany(cascade = ALL, mappedBy = "parentSecPolicy")
    @OrderBy("name")
    public Collection<SecPolicy> getChildsSecPolicies() { return childsSecPolicies ; }

    /** Вложенные политики */
    private Collection<SecPolicy> childsSecPolicies ;
    /** Родитель */
    private SecPolicy parentSecPolicy ;
    /** Комментарий */
    private String comment ;
    /** Название */
    private String name ;
    /** Ключ */
    private String key ;
    
	/** Вычисленный ключ */
	private Integer calckey;

}
