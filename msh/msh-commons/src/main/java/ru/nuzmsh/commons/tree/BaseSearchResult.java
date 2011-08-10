package ru.nuzmsh.commons.tree;

/**
 * Результат поиска
 */
public class BaseSearchResult implements ISearchResult {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BaseSearchResult(boolean aIsSearchComplete, Object aId) {
        theIsSearchComplete = aIsSearchComplete;
        theId = aId ;
    }

    public boolean isSearchComplete() {
        return theIsSearchComplete;
    }

    public Object getId() {
        return theId;
    }

    private final boolean theIsSearchComplete ;
    private final Object theId ;

}
