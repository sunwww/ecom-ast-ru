package ru.ecom.mis.ejb.uc.privilege.service;

import lombok.Getter;
import lombok.Setter;

/**
 * @author  azviagin
 */
@Getter
@Setter
public class ColumnMapping {
	/** Размер поля */
	private int length = 10;
	/** Поле */
	private String field;
	/** Колонка */
	private int column;
	/** Обязательное поле */
	private boolean required;
	/** Если найдено */
	private String matched;
	
	public static ColumnMapping createGroupColumn(int aColumn, boolean aRequired, String aFieldName, String aMatched, int aSize) {
		ColumnMapping m = new ColumnMapping() ;
		m.setColumn(aColumn);
		m.setRequired(aRequired);
		m.setField(aFieldName);
		m.setMatched(aMatched) ;
		m.setLength(aSize);
		return m;
	}

	public static ColumnMapping createGroupColumn(int aColumn, boolean aRequired, String aFieldName, String aMatched, ICellFunction aFunction, int aSize) {
		ColumnMapping m = new ColumnMapping() ;
		m.setColumn(aColumn);
		m.setRequired(aRequired);
		m.setField(aFieldName);
		m.setMatched(aMatched) ;
		m.setCellFunction(aFunction);
		m.setLength(aSize);
		return m;
	}
	
	public static ColumnMapping createDetailsColumn(int aColumn, boolean aRequired, String aFieldName, int aSize) {
		ColumnMapping m = new ColumnMapping() ;
		m.setColumn(aColumn);
		m.setRequired(aRequired);
		m.setField(aFieldName);
		m.setLength(aSize);
		return m;
	}
	public static ColumnMapping createDetailsColumn(int aColumn, String aFieldName, int aSize) {
		ColumnMapping m = new ColumnMapping() ;
		m.setColumn(aColumn);
		m.setField(aFieldName);
		m.setLength(aSize);
		return m;
	}

	public static ColumnMapping createDetailsColumn(int aColumn, String aFieldName, ICellFunction aFunction, int aSize) {
		ColumnMapping m = new ColumnMapping() ;
		m.setColumn(aColumn);
		m.setField(aFieldName);
		m.setCellFunction(aFunction);
		m.setLength(aSize);
		return m;
	}
	
	/** Функция преобразования */
	private ICellFunction cellFunction;
	
}
