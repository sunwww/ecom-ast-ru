package ru.ecom.mis.ejb.service.querycontstraint;

public interface IQueryConstraintService {

	String getQueryConstraint(String aKey);
	Boolean isConstrainted(String aKey);
	
}
