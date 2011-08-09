package ru.ecom.expomc.web.actions.importtime;

import ru.ecom.ejb.services.query.WebQueryResult;
import ru.nuzmsh.web.tags.decorator.ITableDecorator;

public class MessageByVidTableDecorator implements ITableDecorator {

	public String getId(Object aRow) {
		WebQueryResult result = (WebQueryResult) aRow ;
		return result.get1()!=null ? result.get1().toString() : "" ;
	}

	public String getRowCssClass(Object aRow) {
		WebQueryResult result = (WebQueryResult) aRow ;
		return "checkType"+result.get4() ;
	}
	

}
