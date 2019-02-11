package ru.ecom.mis.ejb.domain.birth;/**
 * Created by Milamesher on 23.01.2019.
 */

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Comment("Скрининг новорождённых на наличие врождённых пороков сердца I этап")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = {
        @AIndex(properties = { "medCase" })
}
)
public class ScreeningCardiacFirst extends ScreeningCardiac {

}