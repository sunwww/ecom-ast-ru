/**
 * WS_MES_SERVERSoapPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri.WS_MES_SERVER.wsdl;

public interface WS_MES_SERVERSoapPort extends java.rmi.Remote {
    public java.lang.Object get_RZ_from_POLIS(java.lang.String l_s_polis, java.lang.String l_n_polis, java.lang.String l_comentsl) throws java.rmi.RemoteException;
    public java.lang.Object get_RZ_from_SN_SCAO(java.lang.String l_SocialNumber) throws java.rmi.RemoteException;
    public java.lang.Object get_SN_SCAO_from_RZ(java.lang.String l_rz) throws java.rmi.RemoteException;
    public java.lang.Object get_DOCS_from_RZ(java.lang.String l_rz, java.lang.String l_comentsl) throws java.rmi.RemoteException;
    public java.lang.Object get_ADRES_from_RZ(java.lang.String l_rz, java.lang.String l_comentsl) throws java.rmi.RemoteException;
    public java.lang.Object get_RZ_from_SS(java.lang.String l_ss, java.lang.String l_comentsl) throws java.rmi.RemoteException;
    public java.lang.Object get_RZ_from_DOCS(java.lang.String l_doc_t, java.lang.String l_doc_s, java.lang.String l_doc_n, java.lang.String l_comentsl) throws java.rmi.RemoteException;
    public java.lang.Object get_POLIS_from_RZ2(java.lang.String l_rz, java.lang.String l_comentsl) throws java.rmi.RemoteException;
    public java.lang.Object get_POLIS_from_RZ(java.lang.String l_rz, java.lang.String l_comentsl) throws java.rmi.RemoteException;
    public java.lang.Object get_FIODR_from_RZ(java.lang.String l_rz, java.lang.String l_comentsl) throws java.rmi.RemoteException;
    public java.lang.Object get_RZ_from_FIODR(java.lang.String l_f, java.lang.String l_i, java.lang.String l_o, java.lang.String l_dr, java.lang.String l_comentsl) throws java.rmi.RemoteException;
}
