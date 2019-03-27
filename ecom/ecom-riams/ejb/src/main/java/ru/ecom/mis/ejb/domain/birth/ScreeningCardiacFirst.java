package ru.ecom.mis.ejb.domain.birth;/**
 * Created by Milamesher on 23.01.2019.
 */

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Comment("Скрининг новорождённых на наличие врождённых пороков сердца I этап")
@Entity
public class ScreeningCardiacFirst extends ScreeningCardiac {
}