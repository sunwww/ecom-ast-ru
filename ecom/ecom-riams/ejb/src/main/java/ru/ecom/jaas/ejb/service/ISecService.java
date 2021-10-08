package ru.ecom.jaas.ejb.service;

import ru.ecom.jaas.ejb.form.SecRoleForm;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.List;

public interface ISecService {
    String exportPolicy(long[] aPolicies) throws ParserConfigurationException, TransformerException;

    String exportRoles(long[] aRoles) throws ParserConfigurationException, TransformerException;

    void importPolicies(long aMonitorId, List<PolicyForm> aPolicies);

    List<SecRoleForm> listRoles();

    void importRoles(long aMonitorId, boolean aClearRole, List<ImportRoles> aListRoles);

    Long findRole(PolicyForm aRole);
}
