package ru.ecom.expomc.ejb.services.check.checkers;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Замена подстроки
 */
@Comment("Замена подстроки")
public class ChangeSubstring extends AbstractChangeStringProperty {
    public String transform(String aStr) {
    	String replace = replaceTo ;
    	if(replace==null) replace = "" ;
    	if("\"\"".equals(replace)) replace ="" ;
    	return aStr!=null ? aStr.replace(search, replace) : null ;
    }
    
    /** Искать */
	@Comment("Искать")
	public String getSearch() {
		return search;
	}

	public void setSearch(String aSearch) {
		search = aSearch;
	}

	/** Заменить на */
	@Comment("Заменить на")
	public String getReplaceTo() {
		return replaceTo;
	}

	public void setReplaceTo(String aReplaceTo) {
		replaceTo = aReplaceTo;
	}

	/** Заменить на */
	private String replaceTo;
	/** Искать */
	private String search;
}
