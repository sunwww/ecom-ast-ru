/**
 * WS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri.WS_MES_SERVER.wsdl;

public interface WS extends javax.xml.rpc.Service {
    public java.lang.String getWS_MES_SERVERSoapPortAddress();

    public org.tempuri.WS_MES_SERVER.wsdl.WS_MES_SERVERSoapPort getWS_MES_SERVERSoapPort() throws javax.xml.rpc.ServiceException;

    public org.tempuri.WS_MES_SERVER.wsdl.WS_MES_SERVERSoapPort getWS_MES_SERVERSoapPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
