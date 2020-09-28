/**
 * WSLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri.WS_MES_SERVER.wsdl;

public class WSLocator extends org.apache.axis.client.Service implements org.tempuri.WS_MES_SERVER.wsdl.WS {

    public WSLocator() {
    }


    public WSLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WS_MES_SERVERSoapPort
    private java.lang.String WS_MES_SERVERSoapPort_address = "http://SRV-KIR/ws/WS.WSDL";

    public java.lang.String getWS_MES_SERVERSoapPortAddress() {
        return WS_MES_SERVERSoapPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WS_MES_SERVERSoapPortWSDDServiceName = "WS_MES_SERVERSoapPort";

    public java.lang.String getWS_MES_SERVERSoapPortWSDDServiceName() {
        return WS_MES_SERVERSoapPortWSDDServiceName;
    }

    public void setWS_MES_SERVERSoapPortWSDDServiceName(java.lang.String name) {
        WS_MES_SERVERSoapPortWSDDServiceName = name;
    }

    public org.tempuri.WS_MES_SERVER.wsdl.WS_MES_SERVERSoapPort getWS_MES_SERVERSoapPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WS_MES_SERVERSoapPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWS_MES_SERVERSoapPort(endpoint);
    }

    public org.tempuri.WS_MES_SERVER.wsdl.WS_MES_SERVERSoapPort getWS_MES_SERVERSoapPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.WS_MES_SERVER.wsdl.WS_MES_SERVERSoapBindingStub _stub = new org.tempuri.WS_MES_SERVER.wsdl.WS_MES_SERVERSoapBindingStub(portAddress, this);
            _stub.setPortName(getWS_MES_SERVERSoapPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWS_MES_SERVERSoapPortEndpointAddress(java.lang.String address) {
        WS_MES_SERVERSoapPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.WS_MES_SERVER.wsdl.WS_MES_SERVERSoapPort.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.WS_MES_SERVER.wsdl.WS_MES_SERVERSoapBindingStub _stub = new org.tempuri.WS_MES_SERVER.wsdl.WS_MES_SERVERSoapBindingStub(new java.net.URL(WS_MES_SERVERSoapPort_address), this);
                _stub.setPortName(getWS_MES_SERVERSoapPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + serviceEndpointInterface.getName());
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WS_MES_SERVERSoapPort".equals(inputPortName)) {
            return getWS_MES_SERVERSoapPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/WS_MES_SERVER/wsdl/", "WS");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/WS_MES_SERVER/wsdl/", "WS_MES_SERVERSoapPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WS_MES_SERVERSoapPort".equals(portName)) {
            setWS_MES_SERVERSoapPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
