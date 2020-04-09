package ru.ecom.ejb.services.voc.allvalues;

import ru.ecom.ejb.services.voc.helper.ArrayAllValue;

public class CovidResultAllValues extends ArrayAllValue {


	public CovidResultAllValues() {
		addValue("1", "Выздоровление");
		addValue("2", "Умер");
		addValue("3", "Заболевание не подтверждено");
	}
}
