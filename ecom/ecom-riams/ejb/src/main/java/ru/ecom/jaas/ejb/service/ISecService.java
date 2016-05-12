package ru.ecom.jaas.ejb.service;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import ru.ecom.jaas.ejb.form.SecRoleForm;

public interface ISecService {
	public String exportPolicy(long[] aPolicies) throws ParserConfigurationException, TransformerException ;
	public String exportRoles(long[] aRoles) throws ParserConfigurationException, TransformerException;
	public void importPolicies(long aMonitorId, List<PolicyForm> aPolicies) ;
	//public void importPoliciesByRole(long aMonitorId,boolean aClearRole, PolicyForm aRole, List<PolicyForm> aPolicies) ;
	public List<SecRoleForm> listRoles() ;
	public void importRoles(long aMonitorId, boolean aClearRole, List<ImportRoles> aListRoles) ;
	public Long findRole(PolicyForm aRole) ;
}
