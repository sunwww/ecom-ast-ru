package ru.ecom.mis.ejb.uc.privilege.service;

import java.util.LinkedList;
import java.util.List;

public class GroupExcelConfig {

	/** Идентфикатор */
	public List<ColumnMapping> getGroupColumns() {
		return theGroupColumns;
	}

	/** Колонка с началом данных */
	public List<ColumnMapping> getDetailsColumns() {
		return theDetailsColumns;
	}

	/** Колонка с началом данных */
	private List<ColumnMapping> theDetailsColumns  = new LinkedList<ColumnMapping>();;
	/** Идентфикатор */
	private List<ColumnMapping> theGroupColumns = new LinkedList<ColumnMapping>();
}
