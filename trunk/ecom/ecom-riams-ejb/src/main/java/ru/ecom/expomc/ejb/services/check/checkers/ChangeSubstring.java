package ru.ecom.expomc.ejb.services.check.checkers;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Замена подстроки
 */
@Comment("Замена подстроки")
public class ChangeSubstring extends AbstractChangeStringProperty {
    public String transform(String aStr) {
    	String replace = theReplaceTo ;
    	if(replace==null) replace = "" ;
    	if("\"\"".equals(replace)) replace ="" ;
    	return aStr!=null ? aStr.replace(theSearch, replace) : null ;
    }
    
    /** Искать */
	@Comment("Искать")
	public String getSearch() {
		return theSearch;
	}

	public void setSearch(String aSearch) {
		theSearch = aSearch;
	}

	/** Заменить на */
	@Comment("Заменить на")
	public String getReplaceTo() {
		return theReplaceTo;
	}

	public void setReplaceTo(String aReplaceTo) {
		theReplaceTo = aReplaceTo;
	}

	/** Заменить на */
	private String theReplaceTo;
	/** Искать */
	private String theSearch;
}
