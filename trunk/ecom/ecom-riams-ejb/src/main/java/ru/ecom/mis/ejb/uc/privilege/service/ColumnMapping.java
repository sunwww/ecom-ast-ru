package ru.ecom.mis.ejb.uc.privilege.service;

/**
 * @author  azviagin
 */
public class ColumnMapping {

	/** Размер поля */
	public int getLength() {
		return theLength;
	}

	public void setLength(int aLength) {
		theLength = aLength;
	}

	/** Размер поля */
	private int theLength = 10;
	/** Функция преобразования */
	public ICellFunction getCellFunction() {
		return theCellFunction;
	}

	public void setCellFunction(ICellFunction aCellFunction) {
		theCellFunction = aCellFunction;
	}

	/** Если найдено */
	public String getMatched() {
		return theMatched;
	}

	public void setMatched(String aMatched) {
		theMatched = aMatched;
	}

	/** Обязательное поле */
	public boolean getRequired() {
		return theRequired;
	}

	public void setRequired(boolean aRequired) {
		theRequired = aRequired;
	}

	/** Колонка */
	public int getColumn() {
		return theColumn;
	}

	public void setColumn(int aColumn) {
		theColumn = aColumn;
	}

	/** Поле */
	public String getField() {
		return theField;
	}

	public void setField(String aField) {
		theField = aField;
	}

	/** Поле */
	private String theField;
	/** Колонка */
	private int theColumn;
	/** Обязательное поле */
	private boolean theRequired;
	/** Если найдено */
	private String theMatched;
	
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
	private ICellFunction theCellFunction;
	
}
