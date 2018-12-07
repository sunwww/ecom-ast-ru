package ru.ecom.ejb.services.voc.allvalues;

import ru.ecom.ejb.services.voc.helper.ArrayAllValue;

public class YesNoAllValues extends ArrayAllValue {
	

	public YesNoAllValues() {
		addValue("", "");
		addValue("1", "Да");
		addValue("2", "Нет");
	}
}
