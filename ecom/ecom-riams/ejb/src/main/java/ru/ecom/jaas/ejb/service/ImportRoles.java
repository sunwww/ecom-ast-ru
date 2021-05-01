package ru.ecom.jaas.ejb.service;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Setter
@Getter
public class ImportRoles implements Serializable{

	/** Политики */
	private List<PolicyForm> policies;
	/** Роль */
	private PolicyForm role;

}
