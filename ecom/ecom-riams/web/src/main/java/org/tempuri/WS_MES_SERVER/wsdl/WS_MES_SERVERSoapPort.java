/**
 * WS_MES_SERVERSoapPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri.WS_MES_SERVER.wsdl;

public interface WS_MES_SERVERSoapPort extends java.rmi.Remote {
    Object getPersonA(java.util.Calendar l_dr, String l_doc, String l_n_polis) throws java.rmi.RemoteException;
    Object get_RZ_from_POLIS(String l_s_polis, String l_n_polis, String l_comentsl) throws java.rmi.RemoteException;
    Object test2(String l_SocialNumber) throws java.rmi.RemoteException;
    Object get_RZ_from_SN_SCAO(String l_SocialNumber) throws java.rmi.RemoteException;
    Object get_SN_SCAO_from_RZ(String l_rz) throws java.rmi.RemoteException;
    Object get_DOCS_from_RZ(String l_rz, String l_comentsl) throws java.rmi.RemoteException;
    Object get_ADRES_from_RZ(String l_rz, String l_comentsl) throws java.rmi.RemoteException;
    Object get_RZ_from_SS(String l_ss, String l_comentsl) throws java.rmi.RemoteException;
    Object get_RZ_from_DOCS(String l_doc_t, String l_doc_s, String l_doc_n, String l_comentsl) throws java.rmi.RemoteException;
    Object get_POLIS_from_RZ(String l_rz, String l_comentsl) throws java.rmi.RemoteException;
    Object get_FIODR_from_RZ(String l_rz, String l_comentsl) throws java.rmi.RemoteException;
    Object get_RZ_from_FIODR(String l_f, String l_i, String l_o, String l_dr, String l_comentsl) throws java.rmi.RemoteException;
}
