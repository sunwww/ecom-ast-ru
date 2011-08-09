package ru.ecom.diary.ejb.service.protocol;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.nuzmsh.util.voc.VocValue;

public class ParameterTypeValueVoc implements IAllValue{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public Collection<VocValue> listAll(AllValueContext aContext) {
		LinkedList<VocValue> ret = new LinkedList<VocValue>() ;
		ParameterServiceBean bean = new ParameterServiceBean() ;
		List<ParameterType> list = bean.loadParameterType() ;
		for (ParameterType param :list) {
			add(ret,param) ;
		}
		return ret;
	}
	private static void add(List<VocValue> aValues,  ParameterType aParam) {
		StringBuilder name = new StringBuilder() ;
		name.append(aParam.getName()).append(" (").append(aParam.getType()).append(")") ;
		aValues.add(new VocValue(String.valueOf(aParam.getId()), name.toString())) ;
	}

}
